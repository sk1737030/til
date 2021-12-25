package com.example.commonresponse.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiKeyDto {

    @Builder
    @Getter
    public static class Create {

        private final String name;
        private final String cidr;
        private final String permission;
    }

    @Getter
    @Builder
    public static class Delete {

        private String accessKey;
    }

    @Data
    @Builder
    public static class Response {

        private String accessKey;
        private String secretKey;
        private String name;
        private String cidr;
        private String permission;
        private LocalDateTime createdAt;
        private LocalDateTime expireAt;
    }

    @Data
    @Builder
    public static class GetAllResponse {

        private String accessKey;
        private String name;
        private String cidr;
        private String permission;
        private LocalDateTime createdAt;
        private LocalDateTime expireAt;
    }
}
