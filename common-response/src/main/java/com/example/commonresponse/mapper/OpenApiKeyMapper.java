package com.example.commonresponse.mapper;

import com.example.commonresponse.dto.OpenApiKeyDto;
import com.example.commonresponse.entity.UserOpenApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OpenApiKeyMapper {

    OpenApiKeyDto.Response response(UserOpenApiKey userOpenApiKey);

    OpenApiKeyDto.GetAllResponse GetAllResponse(UserOpenApiKey userOpenApiKey);
}
