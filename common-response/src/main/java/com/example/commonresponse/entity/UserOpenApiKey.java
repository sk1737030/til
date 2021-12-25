package com.example.commonresponse.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_open_api_key")
public class UserOpenApiKey {

    @Id
    private String accessKey; // pk
    private String secretKey;
    private Long userId; // idx
    private String permission;
    private String name;
    private String cidr;
    private boolean enabled = true; // idx
    private LocalDateTime expireAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public UserOpenApiKey(Long userId, String name, String permission, String cidr) {
        this.userId = userId;
        this.name = name;
        this.permission = permission;
        this.cidr = cidr; // not supported yet.
        this.secretKey = generateKey();
        this.enabled = true;
        this.accessKey = generateKey();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.expireAt = createdAt.plusMonths(1L);
    }

    public void changeEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String generateKey() {

        StringBuilder sb = new StringBuilder();
        StringBuilder hexString = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        byte[] initBytes = new byte[256];

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            secureRandom.nextBytes(initBytes);
            md.update(initBytes);

            byte[] byteData = md.digest();

            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }

            for (byte byteDatum : byteData) {
                String hex = Integer.toHexString(0xff & byteDatum);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
