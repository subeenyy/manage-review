# 🔧 Railway 배포 문제 해결 가이드

## ❌ 문제: Java 17 Toolchain을 찾을 수 없음

```
No matching toolchains found for requested specification: Java 17
No locally installed toolchains match
```

### 원인
- Railway 빌드 컨테이너에 JDK 17이 설치되지 않음
- Gradle이 `toolchain { languageVersion = JavaLanguageVersion.of(17) }` 설정으로 Java 17을 요구함

---

## ✅ 해결 방법

### 방법 1: Nixpacks 설정 파일 사용 (권장)

**파일 생성**: [`nixpacks.toml`](file:///Users/subeenhong/Desktop/review_admin/back/nixpacks.toml)

```toml
[phases.setup]
nixPkgs = ["jdk17"]

[phases.build]
cmds = ["./gradlew build -x test"]

[start]
cmd = "java -jar build/libs/*.jar"
```

이 파일을 프로젝트 루트에 추가하고 Git push하면 Railway가 자동으로 인식합니다.

---

### 방법 2: Railway 환경변수 설정

Railway Dashboard → Variables에서 추가:

```bash
NIXPACKS_JDK_VERSION=17
```

---

### 방법 3: build.gradle 수정 (임시 해결책)

`build.gradle`에서 toolchain 설정 제거:

```gradle
// 이 부분 주석 처리
// java {
//     toolchain {
//         languageVersion = JavaLanguageVersion.of(17)
//     }
// }

// 대신 sourceCompatibility 사용
java {
    sourceCompatibility = '17'
    targetCompatibility = '17'
}
```

⚠️ **주의**: 이 방법은 권장하지 않습니다. Nixpacks 설정이 더 깔끔합니다.

---

## 📋 배포 체크리스트

### 1. 파일 추가
- [x] `nixpacks.toml` 생성
- [x] `railway.toml` 생성 (선택사항)

### 2. Git 커밋 & 푸시
```bash
cd /Users/subeenhong/Desktop/review_admin/back
git add nixpacks.toml railway.toml
git commit -m "Add Railway deployment configuration for Java 17"
git push
```

### 3. Railway 자동 재배포
- Git push 후 자동으로 재배포됨
- Railway Dashboard → Deployments에서 로그 확인

### 4. 배포 확인
```bash
# Health Check
curl https://reviewback-production.up.railway.app/auth/ping

# 응답: 200 OK
```

---

## 🔍 Railway 로그 확인

### 빌드 로그에서 확인할 내용

✅ **성공 시**:
```
Setting up JDK 17
Building with Gradle
BUILD SUCCESSFUL
```

❌ **실패 시**:
```
No matching toolchains found
```

---

## 🚀 추가 최적화

### railway.toml 설정 설명

```toml
[build]
builder = "NIXPACKS"           # Nixpacks 사용
buildCommand = "./gradlew build -x test"  # 테스트 제외 빌드

[deploy]
startCommand = "java -Dserver.port=$PORT -jar build/libs/*.jar"
healthcheckPath = "/auth/ping"  # Health Check 엔드포인트
healthcheckTimeout = 100        # 타임아웃 (초)
restartPolicyType = "ON_FAILURE"  # 실패 시 재시작
```

### 환경변수 설정

Railway Dashboard → Variables:

```bash
# Java 버전 (선택사항, nixpacks.toml이 있으면 불필요)
NIXPACKS_JDK_VERSION=17

# Spring Boot 포트 (Railway가 자동 설정)
# PORT=자동할당

# 데이터베이스
SPRING_DATASOURCE_URL=jdbc:postgresql://...
SPRING_DATASOURCE_USERNAME=postgres.uawglogocejtyieiaxbr
SPRING_DATASOURCE_PASSWORD=Iamsubeen2!!

# CORS
CORS_ALLOWED_ORIGINS=https://review-admin-phi.vercel.app,https://*.vercel.app
```

---

## 💡 참고 자료

- [Railway Nixpacks 문서](https://docs.railway.app/deploy/builders/nixpacks)
- [Nixpacks Java 설정](https://nixpacks.com/docs/providers/java)
- [Railway 환경변수](https://docs.railway.app/develop/variables)

---

## 🎯 다음 단계

1. ✅ `nixpacks.toml` 파일 생성 완료
2. ✅ `railway.toml` 파일 생성 완료
3. ⏳ Git commit & push
4. ⏳ Railway 자동 재배포 대기
5. ⏳ Health Check 확인
