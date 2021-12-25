package com.example.commonresponse.service;

import com.example.commonresponse.dto.OpenApiKeyDto;
import com.example.commonresponse.entity.UserOpenApiKey;
import com.example.commonresponse.repository.OpenApiKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class OpenApiKeyServiceTest {

    @InjectMocks
    private OpenApiKeyService openApiKeyService;

    @Mock
    private OpenApiKeyRepository openApiKeyRepository;

    @Test
    void saveUserOpenApiKey() throws Exception {
        // Given
        UserOpenApiKey userOpenApiKey = UserOpenApiKey.builder()
            .userId(1L)
            .name("test")
            .cidr("192.168.0.0/32")
            .permission("Order")
            .build();

        given(openApiKeyRepository.save(any())).willReturn(userOpenApiKey);

        // When
        UserOpenApiKey actual = openApiKeyService.add(OpenApiKeyDto.Create.builder()
            .name("test")
            .cidr("192.168.0.0/32")
            .permission("Order")
            .build());

        // Then
        assertEquals(userOpenApiKey, actual);
    }
}