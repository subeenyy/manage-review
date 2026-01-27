# 🔧 Railway Healthcheck 실패 해결

## ❌ 문제

```
Healthcheck failed!
curl https://reviewback-production.up.railway.app/auth/ping
→ 404 Application not found
```

---

## 🔍 원인 분석

### 1. Redis 연결 실패
- 애플리케이션이 Redis 연결을 시도하지만 Railway에 Redis가 없음
- Spring Boot가 시작 시 Redis 연결 실패로 중단됨

### 2. Kafka 연결 실패
- Kafka도 마찬가지로 Railway에 없음
- 애플리케이션 시작 실패

### 3. JAR 파일 경로 문제
- `build/libs/*.jar` 와일드카드 사용
- `-plain.jar`와 실행 가능한 JAR 두 개 존재

---

## ✅ 해결 방법

### 1. 프로덕션 프로파일 생성

**파일**: [`application-prod.yml`](file:///Users/subeenhong/Desktop/review_admin/back/src/main/resources/application-prod.yml)

```yaml
spring:
  # Redis 비활성화
  data:
    redis:
      repositories:
        enabled: false
  cache:
    type: none  # 메모리 캐시 사용
  
  # Kafka 비활성화
  kafka:
    enabled: false
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
```

### 2. Railway 설정 수정

**파일**: [`railway.toml`](file:///Users/subeenhong/Desktop/review_admin/back/railway.toml)

```toml
[build]
buildCommand = "./gradlew clean bootJar -x test"

[deploy]
startCommand = "java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar build/libs/review-manage-0.0.1-SNAPSHOT.jar"
```

**변경사항**:
- ✅ `bootJar` 사용 (실행 가능한 JAR만 생성)
- ✅ 정확한 JAR 파일명 지정
- ✅ `-Dspring.profiles.active=prod` 추가

---

## 📋 배포 확인

### 1. Git Push 완료
```bash
✅ Committed: Fix Railway deployment
✅ Pushed to origin/main
```

### 2. Railway 자동 재배포
- Railway Dashboard에서 새 배포 시작됨
- 로그 확인 필요

### 3. 예상 로그
```
✅ Setting up JDK 17
✅ Building with Gradle
✅ BUILD SUCCESSFUL
✅ Starting application with prod profile
✅ Redis disabled
✅ Kafka disabled
✅ Application started on port $PORT
✅ Healthcheck passed: /auth/ping → 200 OK
```

---

## 🧪 테스트

배포 완료 후 (약 2-3분):

```bash
# Health Check
curl https://reviewback-production.up.railway.app/auth/ping
# 예상 응답: "pong"

# 상태 코드 확인
curl -I https://reviewback-production.up.railway.app/auth/ping
# 예상: HTTP/2 200
```

---

## 🚨 만약 여전히 실패한다면

### Railway 로그 확인
Railway Dashboard → Deployments → 최신 배포 → View Logs

**확인할 내용**:
1. JDK 17 설치 확인
2. Gradle 빌드 성공 확인
3. JAR 파일 생성 확인
4. 애플리케이션 시작 로그
5. 에러 메시지

### 추가 환경변수 필요 시

Railway Dashboard → Variables:

```bash
# 데이터베이스 연결 확인
SPRING_DATASOURCE_URL=jdbc:postgresql://...
SPRING_DATASOURCE_USERNAME=postgres.uawglogocejtyieiaxbr
SPRING_DATASOURCE_PASSWORD=Iamsubeen2!!

# CORS
CORS_ALLOWED_ORIGINS=https://review-admin-phi.vercel.app,https://*.vercel.app

# Spring Profile (자동 설정됨)
SPRING_PROFILES_ACTIVE=prod
```

---

## 💡 참고

### Redis/Kafka 나중에 추가하려면

1. **Railway에 Redis 추가**
   - Railway Dashboard → Add Service → Redis
   - 자동으로 환경변수 설정됨

2. **application-prod.yml 수정**
   ```yaml
   spring:
     data:
       redis:
         host: ${REDIS_HOST}
         port: ${REDIS_PORT}
   ```

3. **재배포**

---

## 🎯 다음 단계

1. ⏳ Railway 배포 완료 대기 (2-3분)
2. ⏳ Health Check 확인
3. ⏳ Vercel 환경변수 설정
4. ⏳ 통합 테스트
