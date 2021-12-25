package com.example.commonresponse.service;

import com.example.commonresponse.dto.OpenApiKeyDto;
import com.example.commonresponse.entity.UserOpenApiKey;
import com.example.commonresponse.exception.ErrorCode;
import com.example.commonresponse.exception.openapi.AlreadyOpenApiKeyException;
import com.example.commonresponse.exception.openapi.NotFoundOpenApiKeyException;
import com.example.commonresponse.repository.OpenApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OpenApiKeyService {

    private final OpenApiKeyRepository openApiKeyRepository;

    public UserOpenApiKey add(OpenApiKeyDto.Create create) {
        long userId = 1L;

        openApiKeyRepository.findById(String.valueOf(userId)).ifPresent(userOpenApiKey -> {
            throw new AlreadyOpenApiKeyException(ErrorCode.OPEN_API_ALREADY_REGISTERED);
        });

        UserOpenApiKey openApiKey = UserOpenApiKey.builder()
            .userId(userId)
            .name(create.getName())
            .permission(create.getPermission())
            .cidr(create.getCidr())
            .build();

        return openApiKeyRepository.save(openApiKey);
    }


    public void delete(String accessKey) {
        long userID = 1L;

        openApiKeyRepository.findByUserIdAndAccessKey(userID, accessKey).orElseThrow(() -> {
            throw new NotFoundOpenApiKeyException(ErrorCode.NOT_FOUND_OPEN_API_KEY);
        });

        openApiKeyRepository.deleteById(accessKey);
    }

    public List<UserOpenApiKey> getAll() {
        long userId = 1L;
        return openApiKeyRepository.findAllByUserId(userId);
    }
}
