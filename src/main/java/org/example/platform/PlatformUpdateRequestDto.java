package org.example.platform;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlatformUpdateRequestDto {
    private String name;
    private Boolean rewardEnabled;
    private Long rewardPolicyId;
    private Boolean active;
}

