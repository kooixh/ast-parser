package com.kooixiuhong.astparser.api.parser.dtos.api.request;

import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParseRequest {
    private String expression;
    private Set<OperatorRequest> operators;
}
