package com.lastone.chat.service;

import com.lastone.chat.dto.ChatRoomDetailDto;
import com.lastone.chat.dto.ChatRoomFindDto;
import com.lastone.chat.dto.ChatRoomResDto;
import com.lastone.chat.dto.NewMessageResponseDto;
import com.lastone.chat.exception.CannotFoundChatMember;
import com.lastone.chat.exception.CannotFountChatRoom;
import com.lastone.chat.persistence.ChatMessage;
import com.lastone.chat.persistence.MessageColumn;
import com.lastone.chat.persistence.RoomColumn;
import com.lastone.chat.exception.ChatException;
import com.lastone.chat.exception.NotParticipantChatRoom;
import com.lastone.chat.persistence.ChatRoom;
import com.lastone.chat.repository.ChatMessageRepository;
import com.lastone.chat.repository.ChatRoomRepository;
import com.lastone.core.domain.chat.ChatStatus;
import com.lastone.core.domain.member.Member;
import com.lastone.core.dto.chatroom.ChatRoomCreateReqDto;
import com.lastone.core.common.response.ErrorCode;
import com.lastone.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;
    private final MongoTemplate mongoTemplate;

    /**
     * 채팅방을 생성하기 전, 이미 해당 참여자가 있는지 확인
     * 없다면 채팅방 생성
     * 있다면 해당 채팅방의 번호 반환
     */
    @Override
    @Transactional
    public String createRoom(Long userId, ChatRoomCreateReqDto createReqDto) {
        List<Long> userIds = userIdSort(userId, createReqDto.getParticipationId());
        Long hostId = userIds.get(0);
        Long participationId = userIds.get(1);

        Query query = Query.query(
                        Criteria.where(RoomColumn.PARTICIPATIONS.getWord())
                        .all(userIds));
        Optional<ChatRoom> chatRoomOptional = Optional.ofNullable(mongoTemplate.findOne(query, ChatRoom.class));
        if(chatRoomOptional.isEmpty()) {
            ChatRoom createChatRoom = new ChatRoom(hostId, participationId);
            ChatRoom save = mongoTemplate.save(createChatRoom);
            return save.getId();
        }else {
            isRoomValidation(chatRoomOptional.get().getStatus());
        }
        return chatRoomOptional.get().getId();
    }

    @Override
    @Transactional
    public void deleteRoom(String roomId, Long userId) {
        Optional<ChatRoom> chatRoomOptional = Optional.ofNullable(mongoTemplate.findById(roomId, ChatRoom.class));

        if(chatRoomOptional.isEmpty()) {
            throw new ChatException(ErrorCode.NOT_FOUNT_ROOM);
        }
        ChatRoom chatRoom = chatRoomOptional.get();

        if(chatRoom.getStatus().equals(ChatStatus.DELETED)) {
            return;
        }
        isRoomValidation(chatRoom.getStatus());

        chatRoom.getParticipations().stream()
                .filter(paticipant -> paticipant == userId )
                .findFirst().orElseThrow(NotParticipantChatRoom::new);
        chatRoom.delete();
        mongoTemplate.save(chatRoomOptional.get());
    }
    private Aggregation makeRoomSearchAggregation(Long userId, Pageable pageable) {
        String JOIN_AS = "room";
        String senderId = MessageColumn.SENDERID.getWord();
        String receiverId = MessageColumn.RECEIVERID.getWord();
        String roomId = MessageColumn.ROOMID.getWord();
        String content = MessageColumn.CONTENT.getWord();
        String createdAt = MessageColumn.CREATEDAT.getWord();
        String isRead = MessageColumn.ISREAD.getWord();
        Long elementsToSkip = Long.valueOf(pageable.getPageSize()) * Long.valueOf(pageable.getPageNumber());

        AddFieldsOperation objectRoomIdToString = AddFieldsOperation.builder().addField(roomId)
                .withValue(
                        ConvertOperators.ToObjectId.toObjectId("$" + roomId)
                ).build();
        MatchOperation messageMatch = Aggregation.match(
                new Criteria()
                        .orOperator(new Criteria(senderId).is(userId), new Criteria(receiverId).is(userId))
        );
        LookupOperation lookupOperation = Aggregation.lookup(
                                            RoomColumn.COLLECTION_NAME.getWord(),
                                            MessageColumn.ROOMID.getWord(),
                                            RoomColumn.ID.getWord(), JOIN_AS
                                        );
        MatchOperation roomMatch = Aggregation.match(
                new Criteria(JOIN_AS + ".status").is(ChatStatus.NORMAL.name())
                        .and(JOIN_AS + "." + RoomColumn.PARTICIPATIONS.getWord())
                        .is(userId)
        );
        GroupOperation messageGrouping = group(roomId)
                                            .last(JOIN_AS + "." + RoomColumn.PARTICIPATIONS.getWord()).as("other")
                                            .last(content).as(content)
                                            .last(createdAt).as(createdAt)
                                            .sum(ArithmeticOperators.Subtract.valueOf(1)
                                                    .subtract(ConvertOperators.ToLong.toLong("$"+ isRead))
                                            ).as("notReadCount");
        SortOperation latestSort = Aggregation.sort(Sort.Direction.DESC, createdAt);
        SkipOperation skipItem = Aggregation.skip(elementsToSkip);
        LimitOperation itemLimit = new LimitOperation(pageable.getPageSize());

        return Aggregation.newAggregation(
                objectRoomIdToString, messageMatch, lookupOperation
                , roomMatch, messageGrouping, latestSort
                , skipItem, itemLimit
        );
    }
    @Override
    @Transactional(readOnly = true)
    public List<ChatRoomResDto> getList(Long userId, Pageable pageable) {
        List<ChatRoomResDto> resDtos = new ArrayList<>();
        Aggregation roomSearchAggregation = makeRoomSearchAggregation(userId, pageable);

        List<ChatRoomFindDto> roomFindDtos =
                mongoTemplate.aggregate(
                    roomSearchAggregation,
                    MessageColumn.COLLECTION_NAME.getWord(),
                    ChatRoomFindDto.class
                ).getMappedResults();

        for (ChatRoomFindDto roomFindDto : roomFindDtos) {
            Long otherUserId = roomFindDto.getOther().get(0).stream()
                    .filter(membersId -> userId != membersId)
                    .findFirst().get();
            if(otherUserId == null) continue;
            Optional<Member> otherUserInfo = memberRepository.findById(otherUserId);
            ChatRoomResDto roomResDto;
            if(otherUserInfo.isPresent()) {
                roomResDto = new ChatRoomResDto(roomFindDto, otherUserInfo.get());
                resDtos.add(roomResDto);
            }else {
                /**
                 * Todo
                 * 회원 가입 전 테스트용 코드
                 * 배포 때에는 else 분기 없앨 예정
                 */
                Member testMember = Member.builder()
                        .id(otherUserId)
                        .email("테스트 Email" + otherUserId)
                        .gender("남성")
                        .nickname("테스트 닉네임" + otherUserId)
                        .build();
                roomResDto = new ChatRoomResDto(roomFindDto, testMember);
                resDtos.add(roomResDto);
//                continue;
            }
        }
        return resDtos;
    }

    /**
     * 채팅방 상세에 입장할 때의 정보
     * 회원 로그인이 완성된다면 변경
     * 실제 회원이 없기 때문에 테스트 멤버 객체를 담아 줌
     * @param roomId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public ChatRoomDetailDto getOne(String roomId, Long userId) {
        ChatRoom chatRoom = roomRepository.findById(roomId).orElseThrow(CannotFountChatRoom::new);
        isRoomValidation(chatRoom.getStatus());
        if(chatRoom.getStatus().equals(ChatStatus.DELETED)) throw new ChatException(ErrorCode.NOT_FOUNT_ROOM);

        Long otherUserId = chatRoom.getParticipations().stream().filter(participationId -> participationId != userId).findFirst().get();
        Member otherUser = memberRepository.findById(otherUserId).orElseThrow(CannotFoundChatMember::new);

        List<ChatMessage> messages = messageRepository.findByRoomId(roomId);

        Query query = new Query();
        query.addCriteria(
                Criteria.where(MessageColumn.ROOMID.getWord()).is(roomId)
                    .and(MessageColumn.SENDERID.getWord()).is(otherUserId)
        );

        mongoTemplate.updateMulti(
                query, Update.update(MessageColumn.ISREAD.getWord(), true),
                MessageColumn.COLLECTION_NAME.getWord()
        );
        return new ChatRoomDetailDto(messages, otherUser);
    }

    /**
     * Todo - 배포시에 지우기
     * 서버측에서 채팅을 실행할 때 쓰는
     */
    @Override
    @Transactional
    public ChatRoomDetailDto testGetOne(String roomId, Long userId) {
        ChatRoom chatRoom = roomRepository.findById(roomId).orElseThrow(CannotFountChatRoom::new);
        isRoomValidation(chatRoom.getStatus());
        if(chatRoom.getStatus().equals(ChatStatus.DELETED)) throw new ChatException(ErrorCode.NOT_FOUNT_ROOM);

        Long otherUserId = chatRoom.getParticipations().stream().filter(participationId -> participationId != userId).findFirst().get();
//        Member otherUser = memberRepository.findById(otherUserId).orElseThrow(CannotFoundChatMember::new);
        Member otherUser = Member.builder()
                .nickname("테스트유저"+otherUserId)
                .email("testUser"+otherUserId + "@gmail.com")
                .id(otherUserId)
                .profileUrl("테스트유저프로필URL")
                .gender("남성")
                .build();

        List<ChatMessage> messages = messageRepository.findByRoomId(roomId);

        Query query = new Query();
        query.addCriteria(
                Criteria.where(MessageColumn.ROOMID.getWord()).is(roomId)
                        .and(MessageColumn.SENDERID.getWord()).is(otherUserId)
        );

        mongoTemplate.updateMulti(
                query, Update.update(MessageColumn.ISREAD.getWord(), true),
                MessageColumn.COLLECTION_NAME.getWord()
        );
        return new ChatRoomDetailDto(messages, otherUser);
    }

    /**
     * userId가 참여자가 맞는지 검증 후 참여자의 번호 가져와
     * 상대방의 정보를 가져와 메시지와 함께 반환
     */
    @Override
    public NewMessageResponseDto getChatRoomInfoByRoomId(String chatRoomId, Long userId) {
        ChatRoom chatRoom = mongoTemplate.findById(chatRoomId, ChatRoom.class);

        chatRoom.getParticipations().stream()
            .filter(participantId -> userId == participantId)
            .findFirst()
            .orElseThrow(NotParticipantChatRoom::new);
        Long otherUserId = chatRoom.getParticipations().stream()
                .filter(participantId -> userId != participantId)
                .findFirst()
                .orElseThrow(CannotFoundChatMember::new);
        Member member = memberRepository.findById(otherUserId)
                            .orElse(
                                Member.builder()
                                    .id(otherUserId)
                                    .email("테스트유저이메일"+otherUserId)
                                    .nickname("테스트유저닉네임"+otherUserId)
                                    .gender("남성")
                                    .build());
//        Member member = memberRepository.findById(otherUserId).orElseThrow(CannotFoundChatMember::new);

        return new NewMessageResponseDto(member);
    }

    /**
     * 차단된 채팅방일 때 예외 발생
     * @param roomStatus
     */
    private void isRoomValidation(ChatStatus roomStatus) {
        if(roomStatus.equals(ChatStatus.BLOCKED)) {
            throw new ChatException(ErrorCode.BLOCKED_CHAT_ROOM);
        }
    }

    /**
     *
     * @param loginedMemberId
     * @param otherMemberId
     * @return
     */
    private List<Long> userIdSort(Long loginedMemberId, Long otherMemberId) {
        List<Long> userMap = new LinkedList<>();
        userMap.add(loginedMemberId);
        userMap.add(otherMemberId);
        return userMap;
    }

}
