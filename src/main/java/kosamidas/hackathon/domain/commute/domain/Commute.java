package kosamidas.hackathon.domain.commute.domain;

import kosamidas.hackathon.domain.commute.domain.type.Where;
import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.commute.domain.type.WalkWhether;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
@Entity
public class Commute {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 출근날짜
    @NotNull
    private LocalDate officeWentDate;

    // 출근 주차(ex: 2주차)
    @NotNull
    private Integer week;

    // 출근시간
    @NotNull
    private LocalDateTime officeWentAt;

    // 퇴근시간
    @NotNull
    private LocalDateTime quitedTime;

    // 일을 하고 있는 상태인가/퇴근한 상태인가
    @NotNull
    @Enumerated(EnumType.STRING)
    private WalkWhether walkWhether;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @NotNull
    @Column(length = 12)
    @Enumerated(STRING)
    private Where wheres; // 회사 또는 집

    @Builder
    public Commute(LocalDate officeWentDate, Integer week, LocalDateTime officeWentAt, LocalDateTime quitedTime, WalkWhether walkWhether, User user, Where wheres) {
        this.officeWentDate = officeWentDate;
        this.week = week;
        this.officeWentAt = officeWentAt;
        this.quitedTime = quitedTime;
        this.walkWhether = walkWhether;
        this.user = user;
        this.wheres = wheres;
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

    public void updateWalkingWhether() {
        this.walkWhether = WalkWhether.WALKING;
    }

    public void updateQuitedWhether() {
        this.walkWhether = WalkWhether.QUITED;
    }

    // 연관관계 편의 메서드
    public void confirmUser(User user) {
        this.user = user;
    }

}
