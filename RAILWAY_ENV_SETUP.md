# 🚀 Railway 배포 환경변수 설정 가이드

Railway Dashboard에서 다음 환경변수를 설정하세요:

## 데이터베이스 (Supabase)
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME=postgres.uawglogocejtyieiaxbr
SPRING_DATASOURCE_PASSWORD=Iamsubeen2!!
```

## CORS 설정
```bash
# Vercel 도메인 허용 (실제 도메인으로 변경)
CORS_ALLOWED_ORIGINS=https://review-admin-phi.vercel.app,https://*.vercel.app,http://localhost:*
```

## JWT 설정
```bash
# 프로덕션용 강력한 시크릿으로 변경 필요!
JWT_SECRET=very-very-secret-key-that-is-at-least-32-bytes
```

## Redis (선택사항)
```bash
# Railway Redis 추가 시 자동 설정됨
# 또는 외부 Redis 사용 시:
# REDIS_HOST=your-redis-host
# REDIS_PORT=6379
```

## 이메일 (선택사항)
```bash
# Gmail SMTP 사용 시
# MAIL_USERNAME=your-email@gmail.com
# MAIL_PASSWORD=your-app-password
```

---

## ⚠️ 중요 사항

1. **JWT_SECRET 변경 필수!**
   ```bash
   # 강력한 시크릿 생성
   openssl rand -base64 32
   ```

2. **CORS_ALLOWED_ORIGINS 정확히 설정**
   - Vercel 실제 도메인 포함
   - 와일드카드 `https://*.vercel.app` 포함 (Preview 배포용)

3. **환경변수 변경 후 재배포 필요**
   - Railway Dashboard → Deployments → Redeploy

---

## 📋 설정 체크리스트

- [ ] SPRING_DATASOURCE_* 설정 완료
- [ ] CORS_ALLOWED_ORIGINS에 Vercel 도메인 포함
- [ ] JWT_SECRET 프로덕션용으로 변경
- [ ] 환경변수 저장 후 재배포
- [ ] Health Check 확인: https://reviewback-production.up.railway.app/auth/ping
