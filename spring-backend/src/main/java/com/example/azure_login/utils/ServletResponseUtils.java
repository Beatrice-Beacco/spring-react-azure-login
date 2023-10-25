package com.example.azure_login.utils;

import com.example.azure_login.models.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletResponseUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String getErrorMessageJson(int statusCode, String message) throws JsonProcessingException {
        BaseResponse errorResponse = BaseResponse.builder()
                .message(message)
                .statusCode(statusCode)
                .success(false)
                .build();
        return objectMapper.writeValueAsString(errorResponse);
    }
    public static void setUnauthorizedResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        try {
            String errorResponseJson = getErrorMessageJson(HttpServletResponse.SC_UNAUTHORIZED, message);
            response.getWriter().write(errorResponseJson);
        } catch (Exception e) {
            log.error("Error writing response: {}", e.getMessage());
        }
    }

    public static void setForbiddenResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        try {
            String errorResponseJson = getErrorMessageJson(HttpServletResponse.SC_FORBIDDEN, message);
            response.getWriter().write(errorResponseJson);
        } catch (Exception e) {
            log.error("Error writing response: {}", e.getMessage());
        }
    }

    public static void setInternalServerErrorResponse(HttpServletResponse response, String message) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        try {
            String errorResponseJson = getErrorMessageJson(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
            response.getWriter().write(errorResponseJson);
        } catch (Exception e) {
            log.error("Error writing response: {}", e.getMessage());
        }
    }
}
