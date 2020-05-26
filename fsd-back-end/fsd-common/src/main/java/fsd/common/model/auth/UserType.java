package fsd.common.model.auth;

import lombok.Getter;

public enum UserType {
    Buyer("0"),
    Seller("1"),
    Admin("2");

    @Getter
    private String type;

    private UserType(String type) {
        this.type = type;
    }
}