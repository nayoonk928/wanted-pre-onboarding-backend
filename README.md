# wanted-pre-onboarding-backend

# 🧑‍💻Tech Stack
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

# API doc
https://documenter.getpostman.com/view/27585524/2s9YR85Yza

# Requirements
### 1. 채용공고 등록
```
{
  "message": "채용공고가 등록되었습니다.",
  "object": {
    "companyId": 1,
    "position": "백엔드 주니어 개발자",
    "reward": 100000,
    "description": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
    "skill": "Python"
  }
}
```
* @PostMapping("/api/post")
* 모든 필드는 필수로 입력하도록 설정

### 2. 채용공고 수정
```
{
  "message": "채용공고가 수정되었습니다.",
  "object": {
    "postId": 1,
    "position": "백엔드 주니어 개발자",
    "reward": 150000,
    "description": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
    "skill": "Java"
  }
}
```
* @PutMapping("/api/post)
* 해당 공고가 없을 경우 예외처리(POST_NOT_FOUND)
* id를 변경하려는 경우 예외처리(INVALID_POST_ID)

### 3. 채용공고 삭제
`채용공고가 삭제되었습니다.`
* @DeleteMapping("/api/post/{postId}")
* 물리적으로 DB에서 삭제
* 해당 공고가 없을 경우 예외처리(POST_NOT_FOUND)

### 4-1. 채용공고 목록 조회
```
[
  {
    "postId": 1,
    "companyName": "원티드",
    "country": "한국",
    "region": "서울",
    "position": "백엔드 주니어 개발자",
    "reward": 100000,
    "skill": "Python"
  },
  {
    "postId": 2,
    "companyName": "원티드",
    "country": "한국",
    "region": "서울",
    "position": "Django 백엔드 개발자",
    "reward": 100000,
    "skill": "Django"
  }
]
```
* @GetMapping("/api/post")
* Post 엔티티를 PostListDto로 변환하여 반환

### 4-2. 채용공고 검색
```
GET http://localhost:8080/api/post/search/Python
[
  {
    "postId": 1,
    "companyName": "원티드",
    "country": "한국",
    "region": "서울",
    "position": "백엔드 주니어 개발자",
    "reward": 100000,
    "skill": "Python"
  }
]
```
* @GetMapping("/api/post/search/{keyword}")
* 키워드와 관련된 Post 엔티티를 검색하고 PostListDto로 변환하여 반환
* @Query 어노테이션을 사용하여 JPQL 쿼리를 정의하고 이를 통해 Post 엔티티 검색

### 5. 채용 상세 페이지
```
{
  "postId": 1,
  "companyName": "원티드",
  "country": "한국",
  "region": "서울",
  "position": "백엔드 주니어 개발자",
  "reward": 100000,
  "description": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
  "skill": "Python",
  "otherPostIds": [
    2
  ]
}
```
* @GetMapping("/api/post/{postId}")
* Post 엔티티와 같은 회사의 공고 목록을 사용하여 PostDetailDto 생성하여 반환

### 6. 채용공고 지원
```
{
  "message": "지원이 완료되었습니다.",
  "object": {
    "postId": 1,
    "userId": 1
  }
}

or

{
  "message": "이미 이 채용 공고에 지원하셨습니다.",
  "object": null
}
```
* @PostMapping("/api/apply")
* 해당 사용자/채용공고가 없으면 예외처리(USER_NOT_FOUND/POST_NOT_FOUND)
* 이미 해당 공고에 지원했다면 지원 불가