package org.example.campaign;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CampaignEntityTest {

    @Test
    @DisplayName("상태가 DONE으로 변경될 때 completedAt이 설정된다")
    void setCompletedAtWhenStatusChangesToDone() {
        // given
        Campaign campaign = Campaign.builder()
                .status(Status.VISITED)
                .build();

        // when
        campaign.complete();

        // then
        assertThat(campaign.getStatus()).isEqualTo(Status.DONE);
        assertThat(campaign.getCompletedAt()).isNotNull();
        assertThat(campaign.getCompletedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("이미 completedAt이 있으면 덮어쓰지 않는다")
    void doNotOverwriteCompletedAt() {
        // given
        LocalDateTime originalTime = LocalDateTime.now().minusDays(1);
        Campaign campaign = Campaign.builder()
                .status(Status.DONE)
                .completedAt(originalTime)
                .build();

        // when
        campaign.changeStatus(Status.DONE);

        // then
        assertThat(campaign.getCompletedAt()).isEqualTo(originalTime);
    }

    @Test
    @DisplayName("completeReview 메서드 호출 시에도 completedAt이 설정된다")
    void setCompletedAtOnCompleteReview() {
        // given
        Campaign campaign = Campaign.builder()
                .status(Status.VISITED)
                .build();

        // when
        campaign.completeReview("http://test.com");

        // then
        assertThat(campaign.getStatus()).isEqualTo(Status.DONE);
        assertThat(campaign.getReviewUrl()).isEqualTo("http://test.com");
        assertThat(campaign.getCompletedAt()).isNotNull();
    }

    @Test
    @DisplayName("changeStatus(DONE) 호출 시에도 completedAt이 설정된다")
    void setCompletedAtOnChangeStatusToDone() {
        // given
        Campaign campaign = Campaign.builder()
                .status(Status.PENDING)
                .build();

        // when
        campaign.changeStatus(Status.DONE);

        // then
        assertThat(campaign.getStatus()).isEqualTo(Status.DONE);
        assertThat(campaign.getCompletedAt()).isNotNull();
    }
}
