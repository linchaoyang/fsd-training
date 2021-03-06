package fsd.common.model.auth;

import lombok.Getter;

public enum UserStatus {
    Normal(0), Locked(1), Disabled(2);

    @Getter
    private int status;

    private UserStatus(int status) {
        this.status = status;
    }
}