package org.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || authentication.getPrincipal().equals("anonymousUser")) {
                return Optional.empty();
            }

            // user principal에서 ID 추출 로직
            // SecurityContext의 principal이 어떤 타입인지에 따라 다를 수 있음.
            // 보통 UserDetails 구현체이거나 JWT sub(String)일 수 있음.
            // 여기서는 principal name을 Long으로 파싱 시도하거나, 커스텀 UserDetails에서 id를 가져와야 함.
            // 일단 principal.toString()을 파싱 시도 (JWT subject가 userId라고 가정)

            try {
                String name = authentication.getName();
                return Optional.of(Long.parseLong(name));
            } catch (NumberFormatException e) {
                // 숫자가 아니면 (예: 이메일이 principal인 경우) null 반환하거나 처리 필요
                // 여기서는 안전하게 empty 반환
                return Optional.empty();
            }
        };
    }
}
