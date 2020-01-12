package com.kooixiuhong.astparser.api.parser.dtos.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatorRequest {

    private String symbol;
    private int precedence;
    private String type;

}
