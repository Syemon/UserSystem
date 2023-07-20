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
    private User(UserId id, UserLogin login, String name, String type, String avatarUrl, long followersCount, long repositoriesCount, LocalDateTime createdAt) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.type = type;
        this.avatarUrl = avatarUrl;
        this.followersCount = followersCount;
        this.repositoriesCount = repositoriesCount;
        this.createdAt = createdAt;
        this.calculations = Optional.empty();
    }

    private final static BigDecimal USER_DIVIDE_CONSTANT = BigDecimal.valueOf(6L);
    private final static BigDecimal USER_ADDITION_CONSTANT = BigDecimal.valueOf(2L);
    private UserId id;
    private UserLogin login;
    private String name;
    private String type;
    private String avatarUrl;
    private long followersCount;
    private long repositoriesCount;
    private LocalDateTime createdAt;
    private Optional<BigDecimal> calculations;

    public void updateWithCalculation() {
        if (followersCount == 0) {
            log.info("Calculations are not possible with followersCount = 0");
            return;
        }
        else if (followersCount < 0 || repositoriesCount < 0) {
            log.error("Received incorrect values. Followers count and repositories count cannot be a negative number");
            throw new UserCorruptedDataException("Received incorrect values. Followers count and repositories count cannot be a negative number");
        }

        this.calculations = Optional.of(
                    USER_DIVIDE_CONSTANT
                            .divide(BigDecimal.valueOf(followersCount), CALCULATION_SCALE, RoundingMode.HALF_UP)
                            .multiply(
                                    USER_ADDITION_CONSTANT
                                    .add(BigDecimal.valueOf(repositoriesCount)))
        );
    }

    public void validateInitialUser() {
        if (calculations.isPresent()) {
            log.error("Calculations should not be set for initial user");
            throw new UserDomainException("User is not valid");
        }
    }
}
