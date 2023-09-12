# 🥇 LastOne 프로젝트

<div align="center">
  
![서비스로고](https://github.com/Sinchone/.github/assets/96610382/80e67ae2-aaea-49f8-a20e-ab4317d1600a)


  ### 빠르게 찾는 나만의 헬스 파트너, 라스트 원


</div>
  

<br/>
<br/>

#### - 프로젝트 명 : LastOne(라스트원)
#### - 프로젝트 기간 : 2023.02.15 ~ 2023.05.15
#### - 수정 & 리팩토리 기간 : 2023.05.16 ~ (진행중)
#### - [배포 링크 바로가기](https://health-partner-last1.netlify.app/)

<br/>
<br/>

## :information_desk_person: 소개

- 운동 메이트 없이 혼자 헬스하기 지겨우신가요?
- 운동 보조가 있으면 근성장이 될 것 같은 생각이 드시나요?
- 그렇다면 LastOne에서 나만의 헬스 파트너를 찾아보세요!


<br/>
<br/>


## 🔧 기술 스택

<img width="100%" alt="backend" src="https://github.com/Sinchone/.github/assets/96610382/f3adffb4-1239-44d5-b869-3efddb697b19">
<img width="100%" alt="frontend" src="https://github.com/Sinchone/.github/assets/96610382/7c06319c-2116-466c-82fb-c9a5aa9a21bd">

<br/>
<br/>
<br/>


## 💻  주요 기능 미리보기
  
| 카카오 로그인 |  마이페이지 |
|:-:	|:-:	|
| <img src="https://github.com/Sinchone/.github/assets/96610382/dd6f0ebe-a4d9-4ee9-8952-145d002c4176" width="50%" > |  <img src="https://github.com/Sinchone/.github/assets/96610382/9e145bc0-2401-4540-9923-be16988330a5" width="50%"> 	|

  
| 파트너 목록 페이지 |  파트너 검색 페이지 |
|:-:	|:-:	|
| <img src="https://github.com/Sinchone/.github/assets/96610382/d303dcfb-5152-484a-a1ff-c6416ab86dd0" width="50%" height="100%" >  |   <img src="https://github.com/Sinchone/.github/assets/96610382/dc250172-3433-4d0e-9b4b-69ccef27bd53" width="50%" height="100%" > |




</br>
</br>

## 👨‍👨‍👧‍👧 팀원 


|                                           Backend                                           |                                           Backend                                           |                                          Designer                                          |                                           Frontend                                         |                                                          Frontend                                                         |                                                          Frontend                                                          |
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/Jeongho0805/demotest/blob/main/img/jeongho.png" width=400px /> | <img src="https://github.com/Jeongho0805/demotest/blob/main/img/Kyuhyun.png" width=400px /> | <img src="https://github.com/Jeongho0805/demotest/blob/main/img/gyuwon.png" width=400px /> | <img src="https://github.com/Jeongho0805/demotest/blob/main/img/beomsu.png" width=400px /> |  <img src="https://github.com/Jeongho0805/demotest/blob/main/img/haram.png" width=400px /> | <img src="https://github.com/Jeongho0805/demotest/blob/main/img/taeyoung.png" width=400px /> | 
|                            [박정호](https://github.com/Jeongho0805)                            |                             [황규현](https://github.com/beetrbgus)                        |                      [조규원](https://www.behance.net/ku_oni/moodboards)                    |                              [배범수](https://github.com/Bsfla)                            |                                             [이하람](https://github.com/HalamLee)                                           |                                             [김태영](https://github.com/overtae)                                           |





</br>
</br>

## 👨‍💻 아키텍처

<img src="https://github.com/Jeongho0805/demotest/blob/main/img/architecture.jpg" />

<br/>
<br/>

## 📃 API 명세서 [링크](https://six-skirt-6ca.notion.site/19f4bc4659d840a1a295bb571da01b7f?v=e3b86639da3143fd96d74c89b500e8df&pvs=4)

<div align="center">
  
  <img src = "https://github.com/Jeongho0805/demotest/blob/main/img/api.png" height=600px />

</div>


<br/>
<br/>

## 📈 쿼리 성능 개선 목록

<br/>

### ⭐ 기본 10 ~ 100만 건 가량의 더미데이터를 생성 후 쿼리 성능 점검 및 개선 작업을 수행

<br/>

### ✔️ 마이페이지 조회 

**쿼리 성능점검 결과 0.01sec 소요** [상세히 보기](https://tough-echinacea-3e3.notion.site/fc6ea7fc87b14e77b340184e3fd59f27?pvs=4)

<br/>

### ✔️ 모집글 목록조회

**조건이 없는 경우 : (3.959sec ➡ 0.141sec) 3초 단축** (FetchJoin 및 BatchSize 활용) [상세히 보기](https://tough-echinacea-3e3.notion.site/0d76058a9a674e3ab861653b932bb828?pvs=4)

**조건이 있는 경우 : (6.240sec ➡ 0.22sec) 6초 단축** (날짜 역순 인덱스 및 ngram 방식 Full Text Index 활용) [상세히 보기](https://tough-echinacea-3e3.notion.site/01310350921244e5a4339e4d9f3505ff?pvs=4)

<br/>

### ✔️ 모집글 상세 조회

**(0.03sec ➡️ 0.002sec) 0.028초 단축** (DTO 조회 방식에서 Entity 조회 방식으로 변경, Fetch Join 활용) [상세히 보기](https://tough-echinacea-3e3.notion.site/b6df147dd39248a0935fa2e04b063f5c?pvs=4)

<br/>

### ✔️ 받은 신청 리스트 조회

**(0.064sec ➡️ 0.010sec) 0.05초 단축** [상세히 보기](https://tough-echinacea-3e3.notion.site/607af5f3e8ee4ecca1b87256867d4919?pvs=4)

<br/>

### ✔️ 요청 신청 리스트 조회

**(0.020sec ➡️ 0.007sec) 0.013초 단축** [상세히 보기](https://tough-echinacea-3e3.notion.site/bee7e2b1061c4d8893191bf544c09d10?pvs=4)



