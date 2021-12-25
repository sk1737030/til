package com.example.commonresponse.controller;

import com.example.commonresponse.common.BaseControllerTest;
import com.example.commonresponse.dto.OpenApiKeyDto;
import com.example.commonresponse.entity.UserOpenApiKey;
import com.example.commonresponse.exception.ErrorCode;
import com.example.commonresponse.repository.OpenApiKeyRepository;
import com.example.commonresponse.service.OpenApiKeyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class OpenAPIKeyControllerTest extends BaseControllerTest {

    @Autowired
    private OpenApiKeyService openApiKeyService;

    @Autowired
    private OpenApiKeyRepository openApiKeyRepository;

    @DisplayName("Open APi Key Save")
    @Test
    void saveOpenAPiKey() throws Exception {

        OpenApiKeyDto.Create fixture = OpenApiKeyDto.Create.builder()
            .cidr("192.160.0.1/24")
            .permission("order")
            .name("order API")
            .build();

        ResultActions resultActions = mockMvc.perform(post("/api")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fixture)));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("success").value(Matchers.equalTo(true)))
            .andExpect(jsonPath("result.accessKey").exists())
            .andExpect(jsonPath("result.secretKey").exists())
            .andExpect(jsonPath("result.name").exists())
            .andExpect(jsonPath("result.cidr").exists())
            .andExpect(jsonPath("result.permission").exists())
            .andExpect(jsonPath("result.createdAt").exists())
            .andExpect(jsonPath("result.expireAt").exists())
            .andDo(document("open-api-create", resource("Create a openApi"),
                requestFields(
                    fieldWithPath("name").description("key Name"),
                    fieldWithPath("permission").description("permission"),
                    fieldWithPath("cidr").description("cidr")
                ),
                responseFields(
                    fieldWithPath("success").description("api success"),
                    fieldWithPath("result").description("result"),
                    fieldWithPath("result.accessKey").description("access Key"),
                    fieldWithPath("result.secretKey").description("secret Key"),
                    fieldWithPath("result.cidr").description("cidr"),
                    fieldWithPath("result.permission").description("permission"),
                    fieldWithPath("result.name").description("name"),
                    fieldWithPath("result.createdAt").description("create At"),
                    fieldWithPath("result.expireAt").description("expire At")
                )))
            .andDo(print());
    }

    @DisplayName("Delete Open API Key")
    @Test
    void deleteOpenApiKey() throws Exception {
        UserOpenApiKey fixture = UserOpenApiKey.builder().userId(1L).build();
        String givenAccessKey = fixture.getAccessKey();

        openApiKeyRepository.save(fixture);

        ResultActions resultActions = mockMvc.perform(delete("/api")
            .param("accessKey", givenAccessKey));

        resultActions.andExpect(status().isOk())
            .andDo(document("open-api-delete", resource("Delete a openApi"),
                requestParameters(
                    parameterWithName("accessKey").description("Registered Access Key")
                )))
            .andDo(print());
    }

    @DisplayName("Delete Open API Key With User Id Not Found Exception")
    @Test
    void deleteOpenApiKeyThrowNotFoundOpenApiKeyException() throws Exception {
        UserOpenApiKey fixture = UserOpenApiKey.builder().userId(1L).build();
        String givenAccessKey = fixture.getAccessKey();

        ResultActions resultActions = mockMvc.perform(delete("/api")
            .param("accessKey", givenAccessKey));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("success").value(Matchers.equalTo(false)))
            .andExpect(jsonPath("error.code").value(Matchers.equalTo(ErrorCode.NOT_FOUND_OPEN_API_KEY.getCode())))
            .andExpect(jsonPath("error.message").value(Matchers.equalTo(ErrorCode.NOT_FOUND_OPEN_API_KEY.getMessage())))
            .andDo(document("open-api-delete", resource("Delete a openApi"),
                requestParameters(
                    parameterWithName("accessKey").description("Registered Access Key")
                ),
                responseFields(
                    fieldWithPath("success").description("api success"),
                    fieldWithPath("error").description("Error"),
                    fieldWithPath("error.code").description("Error code"),
                    fieldWithPath("error.message").description("Error message")
                )))
            .andDo(print());
    }


    @DisplayName("Get All Open Api keys")
    @Test
    void getAllOpenApiKeys() throws Exception {
        UserOpenApiKey fixture = UserOpenApiKey.builder().userId(1L).name("test").permission("order").cidr("192.168.0.1/32").build();
        openApiKeyRepository.save(fixture);

        ResultActions resultActions = mockMvc.perform(get("api"));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("success").value(Matchers.equalTo(true)))
            .andExpect(jsonPath("result[0].accessKey").value(Matchers.is(fixture.getAccessKey())))
            .andExpect(jsonPath("result[0].name").value(Matchers.is(fixture.getName())))
            .andExpect(jsonPath("result[0].cidr").value(Matchers.is(fixture.getCidr())))
            .andExpect(jsonPath("result[0].permission").value(Matchers.is(fixture.getPermission())))
            .andExpect(jsonPath("result[0].createdAt").value(Matchers.equalTo(fixture.getCreatedAt().toString())))
            .andExpect(jsonPath("result[0].expireAt").value(Matchers.equalTo(fixture.getExpireAt().toString())))
            .andDo(document("open-api-delete", resource("Delete a openApi"),
                responseFields(
                    fieldWithPath("success").description("api success"),
                    fieldWithPath("result").description("result"),
                    fieldWithPath("result[].accessKey").description("access Key"),
                    fieldWithPath("result[].cidr").description("cidr"),
                    fieldWithPath("result[].permission").description("permission"),
                    fieldWithPath("result[].name").description("name"),
                    fieldWithPath("result[].createdAt").description("create At"),
                    fieldWithPath("result[].expireAt").description("expire At")
                )))
            .andDo(print());
    }

}