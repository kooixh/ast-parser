package com.kooixiuhong.astparser.api.parser.controllers;

import com.kooixiuhong.astparser.api.configs.swagger.SwaggerParseResponse;
import com.kooixiuhong.astparser.api.exceptions.ErrorCodes;
import com.kooixiuhong.astparser.api.exceptions.ErrorResponse;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(value="AST Parser", tags = "parser")
@RestController
public class ParserController {

    @Autowired
    private ParsingService parsingService;

    private static final Logger logger = LoggerFactory.getLogger(ParserController.class);

    @ApiOperation(value = "parse expression and operators", notes = "" +
            "Create an abstract syntax tree for an expression using a set of operators. Provide the symbol, precedence and type of the operators (Unary or binary)." +
            "Return value is an Abstract Syntax Tree in JSON form. Operator cannot be ( or ) as they are reserved to override precedence (Like in mathematics)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SwaggerParseResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
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
