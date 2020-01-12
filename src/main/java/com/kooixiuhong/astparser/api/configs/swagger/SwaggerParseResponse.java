package com.kooixiuhong.astparser.api.configs.swagger;

import lombok.Data;

@Data
public class SwaggerParseResponse {
    private String status;
    private SwaggerInnerNode root;

    @Data
    private class SwaggerInnerNode {
        String value;
        Object left;
        Object right;
    }

}



