package com.kooixiuhong.astparser.api.parser.services.implementations;

import com.kooixiuhong.astparser.api.exceptions.ParseException;
import com.kooixiuhong.astparser.api.parser.constants.ParserConstants;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.ASTNode;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.BinaryOperator;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.Operator;
import com.kooixiuhong.astparser.api.parser.services.ParsingService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class ParsingServiceImpl implements ParsingService {

    private static final String OPENING_PAR = "(";
    private static final String CLOSING_PAR = ")";


    @Override
    public ASTNode parseToAST(String expression, Map<String, Operator> operators) {

        Stack<String> operatorStack = new Stack<>();
        Stack<ASTNode> nodes = new Stack<>();

        String[] tokens = this.tokenize(expression, operators);

        if (containsReserveWords(operators.keySet()))
            throw new ParseException("Invalid operators, operators has reserved words.");

        if (!verifyTerm(tokens, operators))
            throw new ParseException("Syntax Error");


        for (String token : tokens) {
            if (token.equals(OPENING_PAR)) {
                operatorStack.push(OPENING_PAR);
            } else if (token.equals(CLOSING_PAR)) {
                boolean foundOpen = false;
                while (!operatorStack.isEmpty()) {
                    String currentOp = operatorStack.pop();
                    if (currentOp.equals(OPENING_PAR)) {
                        foundOpen = true;
                        break;
                    } else {
                        this.addNodeToStack(nodes, currentOp, operators);
                    }
                }
                if (!foundOpen)
                    throw new ParseException("Mismatch parenthesis");
            } else {
                if (operators.containsKey(token)) {

                    Operator current = operators.get(token);
                    while (!operatorStack.isEmpty()) {
                        Operator last = operators.get(operatorStack.peek());

                        //if the last op is opening parenthesis
                        if (last == null)
                            break;

                        //add all the operators that are lower or same precedence as current
                        if (current instanceof BinaryOperator && (current.comparePrecedence(last) == 0 || current.comparePrecedence(last) == -1)) {
                            operatorStack.pop();
                            addNodeToStack(nodes, last.getSymbol(), operators);
                        } else {
                            break;
                        }
                    }
                    operatorStack.push(token);
                } else {
                    nodes.push(new ASTNode(token, null, null));
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            String currentOp = operatorStack.pop();
            //there should be no parenthesis left
            if (currentOp.equals(OPENING_PAR))
                throw new ParseException("Mismatch parenthesis");
            addNodeToStack(nodes, currentOp, operators);
        }

        return nodes.peek();
    }

    /**
     * Tokenize the expression string with regards to a set of operators
     *
     * @param expression
     * @param operators
     * @return
     */
    @Override
    public String[] tokenize(String expression, Map<String, Operator> operators) {
        //remove white spaces from the input
        expression = expression.replaceAll("\\s+", "");

        //build the regex
        StringBuilder regex = new StringBuilder();
        for (String token : operators.keySet()) {
            if (isMetaCharacter(token))
                regex.append("(\\" + token + ")|"); //escape the special character
            else
                regex.append("(" + token + ")|");
        }
        regex.append("(\\()|");
        regex.append("(\\))");

        //using java string split and look ahead and look behind regex,
        //we can split the string while preserving the delim(operators)
        return expression.split("((?<=(" + regex.toString() + "))|(?=(" + regex.toString() + ")))");

    }


    /**
     *
     * Add an operator node to the stack by taking the first two element and assign them as children
     *
     * @param nodes
     * @param opNode
     * @param ops
     */
    private void addNodeToStack(Stack<ASTNode> nodes, String opNode, Map<String, Operator> ops) {
        if (!ops.containsKey(opNode)) {
            throw new ParseException("Trying to assign child to non operator node");
        }

        ASTNode right = null;
        ASTNode left = null;

        if (!nodes.isEmpty())
            right = nodes.pop();
        if (!nodes.isEmpty())
            left = nodes.pop();

        Operator o = ops.get(opNode);
        if (o instanceof BinaryOperator)
            nodes.push(new ASTNode(opNode, left, right));
        else {

            //if it is a unary operator then just pop one operands
            if (left != null)
                nodes.push(left);
            nodes.push(new ASTNode(opNode, null, right));
        }

    }

    /**
     * Check that if an expression is well formed in regards to it's operator
     *
     * @param tokens
     * @param ops
     * @return
     */
    private boolean verifyTerm(String[] tokens, Map<String, Operator> ops) {

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (ops.containsKey(token)) {
                Operator operator = ops.get(token);
                if (operator instanceof BinaryOperator) {
                    return !(i == 0 || i == tokens.length - 1 || ops.containsKey(tokens[i - 1]) ||
                            tokens[i - 1].equals("(") || tokens[i + 1].equals(")") ||
                            (ops.containsKey(tokens[i + 1]) && ops.get(tokens[i + 1]) instanceof BinaryOperator));

                }
            }
        }
        return true;
    }

    /**
     * Check that the current token is a Regex meta character
     *
     * @param s
     * @return
     */
    private boolean isMetaCharacter(String s) {
        if (s.length() > 1)
            return false;
        return ParserConstants.getMetaCharacter().contains(s.charAt(0));

    }

    /**
     *
     * Check if operators set contains reserved word
     *
     *
     * @param opSymbol
     * @return
     */
    private boolean containsReserveWords(Set<String> opSymbol) {
        for (String symbol : opSymbol) {
            if (ParserConstants.getReserveOperators().contains(symbol))
                return true;
        }
        return false;
    }
}
