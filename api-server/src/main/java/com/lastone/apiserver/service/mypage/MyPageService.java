package com.lastone.apiserver.service.mypage;

import com.lastone.core.dto.mypage.MyPageDto;
import com.lastone.core.dto.mypage.MyPageUpdateDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface MyPageService {

    MyPageDto getMyPage(Long memberId);

    void updateMyPage(Long memberId, MyPageUpdateDto myPageUpdateDto, MultipartFile profileImg) throws IOException;
}
