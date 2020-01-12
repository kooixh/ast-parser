package com.kooixiuhong.astparser.api.parser.dtos.syntaxtree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Operator {

    protected String symbol;
    protected int precedence;

    public abstract int comparePrecedence(Operator op);

}
