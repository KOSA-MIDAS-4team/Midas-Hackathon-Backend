package kosamidas.hackathon.domain.user.domain;

import kosamidas.hackathon.domain.commute.domain.Commute;
import kosamidas.hackathon.domain.user.domain.type.Authority;
import kosamidas.hackathon.domain.user.domain.type.Department;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import kosamidas.hackathon.domain.user.exception.NotMatchedPassword;
import kosamidas.hackathon.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "user_uk",
                columnNames = {"authId"}
        )
})
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 32)
    private String authId;

    @NotNull
    @Size(max = 64)
    private String password;

    @NotNull
    @Size(max = 4)
    private String name;

    @Size(max = 256)
    private String imgPath;

    @Size(max = 512)
    private String imgUrl;

    @NotNull
    @Column(length = 16)
    @Enumerated(STRING)
    private Department department; // 부서

    @NotNull
    @Column(length = 16)
    @Enumerated(STRING)
    private Authority authority; // 권한

    @NotNull
    @Column(length = 8)
    @Enumerated(STRING)
    private SignupStatus signupStatus; // 회원가입 상태(대기, 수락, 거절)

    @OneToMany(mappedBy = "user", cascade = ALL)
    private final List<Commute> commutes = new ArrayList<>();

    @NotNull
    private LocalTime startedCoreAt;

    @NotNull
    private LocalTime endedCoreAt;

    @Builder
    public User(String authId, String password, String name, String imgPath, String imgUrl, Department department, Authority authority, SignupStatus signupStatus) {
        this.authId = authId;
        this.password = password;
        this.name = name;
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
        this.department = department;
        this.authority = authority;
        this.signupStatus = signupStatus;
    }

    // auth TODO : Validate 클래스로 분리하고 싶음
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void matchedPassword(PasswordEncoder passwordEncoder, User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw NotMatchedPassword.EXCEPTION;
        }
    }

    // update
    public void updateSignupStatus(String signupStatus) {
        this.signupStatus = SignupStatus.valueOf(signupStatus);
    }

    public void updateAuthority(String authority) {
        this.authority = Authority.valueOf(authority);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateFile(String imgPath, String imgUrl) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
    }

    public void updateCoreTime(LocalTime startedCoreAt, LocalTime endedCoreAt) {
        this.startedCoreAt = startedCoreAt;
        this.endedCoreAt = endedCoreAt;
    }
}