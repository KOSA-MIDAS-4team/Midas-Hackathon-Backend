package kosamidas.hackathon.domain.commute.domain;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
@Entity
public class Commute extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 출근날짜
    private LocalDate officeWentDate;

    // 출근시간
    private LocalDateTime officeWentAt;

    // 퇴근시간
    private LocalDateTime quitedTime;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Commute(LocalDate officeWentDate, LocalDateTime officeWentAt, LocalDateTime quitedTime) {
        this.officeWentDate = officeWentDate;
        this.officeWentAt = officeWentAt;
        this.quitedTime = quitedTime;
    }

    // update
    public void updateOfficeWentDate(LocalDate officeWentDate) {
        this.officeWentDate = officeWentDate;
    }

    public void updateOfficeWentAt(LocalDateTime officeWentAt) {
        this.officeWentAt = officeWentAt;
    }

    public void updateQuitedTime(LocalDateTime quitedTime) {
        this.quitedTime = quitedTime;
    }

    // 연관관계 편의 메서드
    public void confirmUser(User user) {
        this.user = user;
    }

}
