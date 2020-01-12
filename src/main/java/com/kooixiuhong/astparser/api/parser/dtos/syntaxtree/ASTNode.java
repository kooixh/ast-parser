package com.kooixiuhong.astparser.api.parser.dtos.syntaxtree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ASTNode {
    private String value;
    private ASTNode left;
    private ASTNode right;
}
