package fsd.model.user;

import lombok.Getter;

public enum UserStatus {
    Normal("0"), Locked("1"), Disabled("2");

    @Getter
    private String status;

    private UserStatus(String status) {
        this.status = status;
    }
}