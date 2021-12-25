package com.example.commonresponse.controller;

import com.example.commonresponse.dto.OpenApiKeyDto;
import com.example.commonresponse.entity.UserOpenApiKey;
import com.example.commonresponse.mapper.OpenApiKeyMapper;
import com.example.commonresponse.service.OpenApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OpenAPIKeyController {

    private final OpenApiKeyService openApiKeyService;
    private final OpenApiKeyMapper openApiKeyMapper;

    @PostMapping("api")
    public ResponseEntity<OpenApiKeyDto.Response> add(@RequestBody OpenApiKeyDto.Create create) {
        UserOpenApiKey userOpenApiKey = openApiKeyService.add(create);
        OpenApiKeyDto.Response response = openApiKeyMapper.response(userOpenApiKey);
        return ResponseEntity.ok(response);
    }

    @GetMapping("api")
    public ResponseEntity<List<OpenApiKeyDto.GetAllResponse>> getAll() {
        List<OpenApiKeyDto.GetAllResponse> response = openApiKeyService.getAll().stream()
            .map(openApiKeyMapper::GetAllResponse)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("api")
    public ResponseEntity<Void> delete(@RequestParam String accessKey) {
        openApiKeyService.delete(accessKey);
        return ResponseEntity.ok().build();
    }

}
