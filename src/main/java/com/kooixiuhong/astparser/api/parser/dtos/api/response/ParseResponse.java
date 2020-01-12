package com.kooixiuhong.astparser.api.parser.dtos.api.response;

import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.ASTNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParseResponse {
    private String status;
    private ASTNode root;
}
