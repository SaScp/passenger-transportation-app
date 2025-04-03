package org.service.entity;

public record Tokens(String accessToken, String accessTokenExp, String refreshToken, String refreshTokenExp) {
}
