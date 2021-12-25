package com.example.commonresponse.repository;

import com.example.commonresponse.entity.UserOpenApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OpenApiKeyRepository extends JpaRepository<UserOpenApiKey, String> {

    Optional<UserOpenApiKey> findByUserIdAndAccessKey(Long userId, String accessKey);

    List<UserOpenApiKey> findAllByUserId(Long userId);
}
