package com.syemon.usersystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Getter
public class User {

    public static final int CALCULATION_SCALE = 2;

    @Builder
    private User(UserId id, String login, String type, String avatarUrl, long followersCount, long repositoriesCount, LocalDateTime createdAt) {
        this.id = id;
        this.login = login;
        this.type = type;
        this.avatarUrl = avatarUrl;
        this.followersCount = followersCount;
        this.repositoriesCount = repositoriesCount;
        this.createdAt = createdAt;
    }

    private final static BigDecimal USER_DIVIDE_CONSTANT = BigDecimal.valueOf(6L);
    private final static BigDecimal USER_ADDITION_CONSTANT = BigDecimal.valueOf(2L);
    private UserId id;
    private String login;
    private String type;
    private String avatarUrl;
    private long followersCount;
    private long repositoriesCount;
    private LocalDateTime createdAt;
    private BigDecimal calculations;

    public Optional<BigDecimal> calculate() {
        if (followersCount == 0) {
            log.info("Calculations are not possible with followersCount = 0");
            return Optional.empty();
        }
        else if (followersCount < 0 || repositoriesCount < 0) {
            log.error("Received incorrect values. Followers count and repositories count cannot be a negative number");
            throw new UserDomainException("Received incorrect values. Followers count and repositories count cannot be a negative number");
        }

        return Optional.of(
                    USER_DIVIDE_CONSTANT
                            .divide(BigDecimal.valueOf(followersCount), CALCULATION_SCALE, RoundingMode.HALF_UP)
                            .multiply(
                                    USER_ADDITION_CONSTANT
                                    .add(BigDecimal.valueOf(repositoriesCount)))
        );
    }
}
