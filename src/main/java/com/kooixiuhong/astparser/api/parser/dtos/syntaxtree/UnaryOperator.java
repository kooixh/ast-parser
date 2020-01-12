package com.kooixiuhong.astparser.api.parser.dtos.syntaxtree;

public class UnaryOperator extends Operator {

    public UnaryOperator(String symbol, int precedence) {
        super(symbol, precedence);
    }

    /**
     *
     * Comparing the precedence of this operator with another. Unary operators always have
     * higher precedence compared to binary operator.
     *
     * @return 1 is current operator has higher precedence, 0 if equal, -1 is lower
     */
    @Override
    public int comparePrecedence(Operator op) {
        if(op instanceof BinaryOperator)
            return 1;

        return Integer.compare(this.precedence, op.getPrecedence());
    }
}
