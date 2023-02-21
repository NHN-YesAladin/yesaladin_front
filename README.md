# yesaladin_front
YesAladin Front는 사용자의 웹 애플리케이션 이용에 필요한 정보를 각 Server에 요청하고, 응답 받은 정보를 UI를 통해 제공합니다.

## Getting Started

```bash
./mvnw spring-boot:run
```

## Project Architecture

// 이미지 추가할 것
![]()

## Features

### [@송학현](https://github.com/alanhakhyeonsong)

### [@이수정](https://github.com/sujeong68)

- 장바구니 
  - 장바구니 내 조회
  - 장바구니 상품 추가/삭제 및 담은 개수 변경
  - 주문 완료 시 장바구니 내 상품 삭제
- 파일 
  - 파일 업로드 요청 및 url 반환
  - 파일 다운로드
- 상품
  - 상품 등록/수정/삭제
  - `ToastUI Editor` 사용 및 `addImageBlobHook` 시 오브젝트 스토리지에 이미지 업로드
  - 상품 상세조회
  - 관리자용, 전체이용자용 `Paging` 조회
  - 상품 연관관계 조회/등록/삭제
  - 상품 수량변경, 강제품절여부, 노출여부, 판매여부 변경
  - 상품유형 조회 / 상품 유형별 `Paging` 조회
- 출판사, 출판
  - 출판사 관리자용 `Paging` 조회
  - 출판사 등록/수정
- 태그, 태그관계
  - 태그 관리자용 `Paging` 조회
  - 태그 등록/수정
- 저자, 집필
  - 저자 관리자용 `Paging` 조회
  - 저자 등록/수정
- 매출 통계
  - 지정한 기간동안의 매출 통계 집계하여 관리자 메인 홈에 노출
- 메인페이지 베스트셀러
  - 1년간의 베스트셀러 12권을 노출
- 관리자 홈, 메인페이지 UI 

### [@최예린](https://github.com/Yellin36)

### [@배수한](https://github.com/shbaeNhnacademy)

### [@김홍대](https://github.com/mongmeo-dev)

### [@서민지](https://github.com/narangd0)

### [@김선홍](https://github.com/ssun4098)

## Technical Issue
### Web Socket (가제)
### Object Storage (가제)
### Spring Cache (가제)
### 인증/인가 (가제)

## Tech Stack

### Languages

![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white)

### Frameworks

![SpringBoot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![SpringCloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F?style=flat&logo=Spring&logoColor=white)
![Spring Security](https://img.shields.io/static/v1?style=flat-square&message=Spring+Security&color=6DB33F&logo=Spring+Security&logoColor=FFFFFF&label=)

### Template Engine

![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white)

### Database
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white)

### Build Tool

![ApacheMaven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=ApacheMaven&logoColor=white)

### DevOps

![NHN Cloud](https://img.shields.io/badge/-NHN%20Cloud-blue?style=flat&logo=iCloud&logoColor=white)
![Jenkins](http://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=Jenkins&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-4E98CD?style=flat&logo=SonarQube&logoColor=white)

### Web Server

![NGINX](https://img.shields.io/badge/NGINX-009639?style=flat&logo=NGINX&logoColor=white)

### 형상 관리 전략

![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white)

- Git Flow 전략을 사용하여 Branch를 관리하며 Main/Develop Branch로 Pull Request 시 코드 리뷰 진행 후 merge 합니다.
  ![image](https://user-images.githubusercontent.com/60968342/219870689-9b9d709c-aa55-47db-a356-d1186b434b4a.png)
- Main: 배포시 사용
- Develop: 개발 단계가 끝난 부분에 대해 Merge 내용 포함
- Feature: 기능 개발 단계
- Hot-Fix: Merge 후 발생한 버그 및 수정 사항 반영 시 사용

## Contributors

<a href="https://github.com/NHN-YesAladin/yesaladin_front/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=NHN-YesAladin/yesaladin_front" />
</a>