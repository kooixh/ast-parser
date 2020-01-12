package com.kooixiuhong.astparser.api.parser.controllers;

import com.kooixiuhong.astparser.api.exceptions.ParseException;
import com.kooixiuhong.astparser.api.parser.constants.ParserConstants;
import com.kooixiuhong.astparser.api.parser.dtos.api.request.OperatorRequest;
import com.kooixiuhong.astparser.api.parser.dtos.api.request.ParseRequest;
import com.kooixiuhong.astparser.api.parser.dtos.api.response.ParseResponse;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.ASTNode;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.BinaryOperator;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.Operator;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.UnaryOperator;
import com.kooixiuhong.astparser.api.parser.services.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ParserController {

    @Autowired
    private ParsingService parsingService;

    @PostMapping(path = ParserConstants.PARSE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParseResponse parseAST(@Valid @RequestBody ParseRequest parseRequest) {

        Map<String, Operator> operatorMap = new HashMap<>();

        for (OperatorRequest operatorRequest : parseRequest.getOperators()) {
            if (operatorMap.containsKey(operatorRequest.getSymbol()))
                throw new ParseException("Duplicated operators");

            if (operatorRequest.getType().equals("binary")) {
                operatorMap.put(operatorRequest.getSymbol(), new BinaryOperator(operatorRequest.getSymbol(), operatorRequest.getPrecedence()));
            } else if (operatorRequest.getType().equals("unary")){
                operatorMap.put(operatorRequest.getSymbol(), new UnaryOperator(operatorRequest.getSymbol(), operatorRequest.getPrecedence()));
            } else {
                throw new ParseException("Unknown operator type");
            }
        }
        ASTNode rootNode = parsingService.parseToAST(parseRequest.getExpression(), operatorMap);
        return new ParseResponse("success", rootNode);

    }
}
