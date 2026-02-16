package com.gearx.feature.security.util;

import java.io.IOException;

import jakarta.servlet.http.*;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.gearx.common.exception.ErrorCode;
import com.gearx.common.response.ResponseWriter;

@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest req,
            HttpServletResponse res,
            org.springframework.security.access.AccessDeniedException ex)
            throws IOException {
        ResponseWriter.writeJsonError(
                res, ErrorCode.FORBIDDEN, HttpServletResponse.SC_FORBIDDEN, true);
    }
}
