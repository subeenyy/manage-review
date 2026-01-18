package org.example.platform;

import jakarta.persistence.*;
import lombok.*;
import org.example.common.entity.BaseEntity;

@Entity
@Table(name = "platform")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Platform extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long platformId;

    @Column(nullable = false, unique = true)
    private String code; // review_note, dinner_queen, our_platform

    @Column(nullable = false)
    private String name; // 리뷰노트, 디너의여왕, 자사플랫폼

    @Column(nullable = false)
    private boolean rewardEnabled;

    @Column(nullable = false)
    private boolean active;

    private Long rewardPolicyId;

    // Platform 엔티티
    public void update(PlatformUpdateRequestDto req) {
        if (req.getName() != null)
            this.name = req.getName();
        if (req.getRewardEnabled() != null)
            this.rewardEnabled = req.getRewardEnabled();
        if (req.getRewardPolicyId() != null)
            this.rewardPolicyId = req.getRewardPolicyId();
        if (req.getActive() != null)
            this.active = req.getActive();
    }

}
