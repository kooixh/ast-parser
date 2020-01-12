package com.kooixiuhong.astparser.api.parser.dtos.syntaxtree;

public class BinaryOperator extends Operator {

    public BinaryOperator(String symbol, int precedence) {
        super(symbol, precedence);
    }

    @Override
    public int comparePrecedence(Operator o) {
        if(o instanceof BinaryOperator) {
            return Integer.compare(this.precedence, o.getPrecedence());
        } else {
            return -1;
        }
    }
}
