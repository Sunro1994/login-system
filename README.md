Auth System - Spring Boot JWT 인증/인가 프로젝트

📖 프로젝트 개요

Spring Boot 기반으로 JWT 인증/인가 로직을 구현하고, 관리자 권한 부여 API를 포함한 사용자 인증 시스템을 구축한 프로젝트입니다. API 문서화(Swagger) 및 AWS EC2 배포까지 완료하였습니다.
-	회원가입 / 로그인 기능
-	JWT 기반 인증/인가 구현
-	Role 기반 접근 제어 (USER / ADMIN)
-	관리자 권한 부여 API
-	Swagger API 명세
-	AWS EC2 배포 및 서비스 운영



 프로젝트 실행 방법

1. 프로젝트 클론 및 빌드
```
git clone https://github.com/Sunro1994/login-system.git
cd auth-system
./gradlew build
```

2. 애플리케이션 실행
```
java -jar build/libs/auth-system-0.0.1-SNAPSHOT.jar
```
- 기본 주소: http://localhost:8080



🛠 API 명세 (Swagger)

Swagger UI를 통해 API를 확인할 수 있습니다.
URL: http://3.34.187.62:8080/swagger-ui/index.html



🧪 테스트 실행
```
./gradlew test
```
-	회원가입, 로그인, 권한 부여 API 등 모든 케이스에 대한 JUnit 테스트 포함
-	정상 응답 / 예외 응답 (VALIDATION_ERROR, INVALID_CREDENTIALS, USER_ALREADY_EXISTS 등) 테스트 완료



🌐 API 엔드포인트 요약

✅ 회원가입 (Sign Up)
-	POST /sign-up

Request:
{
"email": "user@email.com",
"password": "password",
"nickname": "nickname"
}

Response:
{
"email": "user@email.com",
"nickname": "nickname",
"role": "USER"
}

✅ 로그인 (Login)
-	POST /login

Request:
{
"email": "user@email.com",
"password": "password"
}

Response:
{
"token": "eyJhbGciOiJIUzUxMiJ9..."
}

✅ 관리자 권한 부여 (Promote to Admin)
-	PATCH /admin/users/{userId}/roles
-	헤더: Authorization: Bearer [ADMIN_TOKEN]

Response:
{
"email": "user@email.com",
"nickname": "nickname",
"role": "ADMIN"
}




⚙ AWS EC2 배포
-	EC2 인스턴스: Ubuntu 20.04 (t2.micro)
-	접근 URL: http://[EC2_IP]:8080
-	보안 그룹: 8080 포트 오픈
-	배포 방식: 로컬 빌드 JAR 업로드 후 java -jar 실행
-	(옵션) nginx reverse proxy 미적용



📝 기타 사항
-	데이터는 인메모리(H2 DB)에서 관리됩니다.
-	API 접근 보호를 위해 JWT 토큰 기반 인증 필터 적용 (JwtAuthenticationFilter)
-	ROLE 기반 접근 제한 적용 (관리자 전용 API)
-   Test를 위한 Repository이기 때문에 jwt.Secret을 환경변수로 분리하지 않았습니다.



🔗 제출 정보
-	GitHub Repository: https://github.com/your-repo/auth-system
-	Swagger UI: http://3.34.187.62:8080/swagger-ui/index.html
-	EC2 API Endpoint: http://3.34.187.62:8080

