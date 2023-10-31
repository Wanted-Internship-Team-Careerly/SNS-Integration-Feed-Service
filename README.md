# ❤️ 소셜 미디어 통합 Feed 서비스

## 🌟 프로젝트 소개

- 본 서비스는 유저 계정의 해시태그를 기반으로 인스타그램, 스레드, 페이스북, 트위터 등 복수의 SNS에 게시된 게시물 중 유저의 해시태그가 포함된 게시물들을 하나의 서비스에서 확인할 수 있는 통합 Feed
  어플리케이션 입니다.

## 💻 기술 스택
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <br>
  
## 🖼 ERD

![Wanted-Assignment-1](https://github.com/Wanted-Internship-Team-Careerly/SNS-Integration-Feed-Service/assets/46531692/678f1f27-70e4-4127-8f90-382b94098337)

## 📐 아키텍처

![Blank board](https://github.com/Wanted-Internship-Team-Careerly/SNS-Integration-Feed-Service/assets/46531692/06ae2bc7-621f-4a9d-bdb9-837022f694bc)

## 🗂 API 명세서

### 오류

<details>
<summary>더보기</summary>
예외가 발생했을 때, 본문에 해당 문제를 기술한 JSON 객체가 담겨있습니다.

| Path            | Type     | Description |
|-----------------|----------|-------------|
| `statusCode`    | `int`    | 상태 코드       |
| `statusMessage` | `String` | 상태 메세지      |

예를 들어, 이미 가입된 계정이 존재할 경우 다음과 같은 응답을 받게 됩니다.

``` http request
{
  "statusCode": "400",
  "statusMessage": "이미 존재하는 사용자입니다."
}
```

</details>

### 사용자

<details>
<summary>더보기</summary>

> 사용자 리소스는 회원 가입, 로그인을 할 때 사용됩니다.

### 가입

`POST` 요청을 사용해서 새 계정을 등록할 수 있습니다.

#### Request fields

| Path       | Type     | Description |
|------------|----------|-------------|
| `account`  | `String` | 계정          |
| `email`    | `String` | 이메일         |
| `password` | `String` | 비밀번호        |

#### Example request

``` http request
{
  "account": "test",
  "email": "test@test.com",
  "password": "test1234"
}
```

#### Response fields

| Path            | Type     | Description |
|-----------------|----------|-------------|
| `statusCode`    | `int`    | 상태 코드       |
| `statusMessage` | `String` | 상태 메세지      |

#### Example response

``` http request
{
  "statusCode": "200",
  "statusMessage": "회원 가입 완료"
}
```

</details>

### 게시물

<details>
<summary>더보기</summary>

> 게시물 리소스는 목록, 상세보기, 좋아요, 공유, 통계를 확인 할 때 사용됩니다.

### 목록

`GET` 요청을 사용해서 게시물 목록을 확인할 수 있습니다.

#### Parameter fields

| Path         | Type     | Description |
|--------------|----------|-------------|
| `hashtag`    | `String` | 해시태그        |
| `type`       | `String` | 타입          |
| `order_by`   | `String` | 정렬 기준       |
| `sort_by`    | `String` | 정렬 순서       |
| `search_by`  | `String` | 검색 기준       |
| `search`     | `String` | 검색 키워드      |
| `page_count` | `int`    | 페이지당 게시물 갯수 |
| `page`       | `int`    | 조회 하려는 페이지  |

#### Example request

> /api/posts/search_by=title&search=제목

#### Response fields

| Path         | Type            | Description |
|--------------|-----------------|-------------|
| `postId`     | `String`        | 게시물 고유 인식값  |
| `type`       | `String`        | 게시물 게시 유형   |
| `title`      | `String`        | 게시물 제목      |
| `content`    | `String`        | 게시물 내용      |
| `hashtag`    | `String`        | 게시물 태그      |
| `viewCount`  | `Long`          | 게시글 조회 수    |
| `likeCount`  | `Long`          | 게시글 좋아요 수   |
| `shareCount` | `Long`          | 게시글 공유 수    |
| `createdAt`  | `LocalDateTime` | 게시글 생성일자    |
| `updatedAt`  | `LocalDateTime` | 게시글 수정일자    |

#### Example response

``` http request
{
  "data": [
    {
      "postId": "12345",
      "type": "facebook",
      "title": "맛집 추천",
      "content": "성수동에 위치한 최고의...",
      "hashtag": "맛집",
      "viewCount": 500,
      "likeCount": 100,
      "shareCount": 50,
      "createdAt": "2023-10-25 12:00:00",
      "updatedAt": "2023-10-25 12:00:00"
    }
  ],
  "page_count": 10,
  "page": 0
}
```

### 상세보기

`GET` 요청을 사용해서 게시물을 상세하게 확인할 수 있습니다.

#### PathVariable fields

| Path     | Type     | Description |
|----------|----------|-------------|
| `postId` | `String` | 게시물 고유 인식값  |

#### Example request

> /api/post/{postId}

#### Response fields

| Path         | Type            | Description |
|--------------|-----------------|-------------|
| `postId`     | `String`        | 게시물 고유 인식값  |
| `type`       | `String`        | 게시물 게시 유형   |
| `title`      | `String`        | 게시물 제목      |
| `content`    | `String`        | 게시물 내용      |
| `hashtag`    | `String`        | 게시물 태그      |
| `viewCount`  | `Long`          | 게시글 조회 수    |
| `likeCount`  | `Long`          | 게시글 좋아요 수   |
| `shareCount` | `Long`          | 게시글 공유 수    |
| `createdAt`  | `LocalDateTime` | 게시글 생성일자    |
| `updatedAt`  | `LocalDateTime` | 게시글 수정일자    |

#### Example response

``` http request
{
  "postId": "12345",
  "type": "facebook",
  "title": "맛집 추천",
  "content": "성수동에 위치한 최고의...",
  "hashtag": "맛집",
  "viewCount": 500,
  "likeCount": 100,
  "shareCount": 50,
  "createdAt": "2023-10-25 12:00:00",
  "updatedAt": "2023-10-25 12:00:00"
}
```

### 좋아요

`POST` 요청을 사용해서 게시물 좋아요를 할 수 있습니다.

#### Pathvariable fields

| Path     | Type     | Description |
|----------|----------|-------------|
| `postId` | `String` | 게시물 고유 인식값  |

#### Example request

> /api/post/like/{postId}

#### Response fields

| Path      | Type     | Description |
|-----------|----------|-------------|
| `message` | `String` | 응답 메세지      |

#### Example response

``` http request
{
  "message": "페이스북 게시물 좋아요 완료"
}
```

### 공유

`POST` 요청을 사용해서 게시물 공유 할 수 있습니다.

#### Pathvariable fields

| Path     | Type     | Description |
|----------|----------|-------------|
| `postId` | `String` | 게시물 고유 인식값  |

#### Example request

> /api/post/share/{postId}

#### Response fields

| Path      | Type     | Description |
|-----------|----------|-------------|
| `message` | `String` | 응답 메세지      |

#### Example response

``` http request
{
  "message": "페이스북 게시물 공유 완료"
}
```

### 통계

`GET` 요청을 사용해서 게시물 통계를 확인할 수 있습니다.

#### Parameter fields

| Path      | Type     | Description                                |
|-----------|----------|--------------------------------------------|
| `hashtag` | `String` | 해시태그                                       |
| `type`    | `String` | date, hour                                 |
| `start`   | `Date`   | 조회 기준 시작일                                  |
| `end`     | `Date`   | 정렬 기준 마지막일                                 |
| `value`   | `String` | count, view_count, like_count, share_count |

#### Example request

> /api/posts/statics?hashtag=사과&type=date

#### Example response

``` http request
{
  "header": {
    "code": 200,
    "message": "SUCCESS"
  },
  "body": {
    "count" : {
      "2023-10-01":0, 
      "2023-10-02":5
    }
  }
}
```

</details>

## 😵‍💫 트러블 슈팅

## 🔥 Careerly

| 이름  | Github                      |
|-----|-----------------------------|
| 김수환 | https://github.com/9898s    |
| 김정석 | https://github.com/dyori04  |
| 이종훈 | https://github.com/rivkode  |
| 표지수 | https://github.com/JisooPyo |
