package com.kooixiuhong.astparser.api.parser.controllers;

import com.kooixiuhong.astparser.api.exceptions.ErrorCodes;
import com.kooixiuhong.astparser.api.exceptions.ParseException;
import com.kooixiuhong.astparser.api.interceptor.RequestInterceptor;
import com.kooixiuhong.astparser.api.parser.constants.ParserConstants;
import com.kooixiuhong.astparser.api.parser.dtos.api.request.OperatorRequest;
import com.kooixiuhong.astparser.api.parser.dtos.api.request.ParseRequest;
import com.kooixiuhong.astparser.api.parser.dtos.api.response.ParseResponse;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.ASTNode;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.BinaryOperator;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.Operator;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.UnaryOperator;
import com.kooixiuhong.astparser.api.parser.services.ParsingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ParserController {

    @Autowired
    private ParsingService parsingService;

    private static final Logger logger = LoggerFactory.getLogger(ParserController.class);

    @PostMapping(path = ParserConstants.PARSE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParseResponse parseAST(@Valid @RequestBody ParseRequest parseRequest, HttpServletRequest request) {

        logger.debug("request uuid {} with payload {} ", request.getAttribute(RequestInterceptor.UUID_HEADER), parseRequest);

        Map<String, Operator> operatorMap = new HashMap<>();

        for (OperatorRequest operatorRequest : parseRequest.getOperators()) {
            if (operatorMap.containsKey(operatorRequest.getSymbol()))
                throw new ParseException(ErrorCodes.DUPLICATED_OPERATOR, "Duplicated operators");

            if (operatorRequest.getType().equals("binary")) {
                operatorMap.put(operatorRequest.getSymbol(), new BinaryOperator(operatorRequest.getSymbol(), operatorRequest.getPrecedence()));
            } else if (operatorRequest.getType().equals("unary")){
                operatorMap.put(operatorRequest.getSymbol(), new UnaryOperator(operatorRequest.getSymbol(), operatorRequest.getPrecedence()));
            } else {
                throw new ParseException(ErrorCodes.INVALID_OPERATOR_TYPE, "Unknown operator type");
            }
        }
        ASTNode rootNode = parsingService.parseToAST(parseRequest.getExpression(), operatorMap);
        return new ParseResponse("success", rootNode);

    }
}
