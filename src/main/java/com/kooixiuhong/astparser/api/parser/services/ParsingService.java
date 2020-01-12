package com.kooixiuhong.astparser.api.parser.services;

import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.ASTNode;
import com.kooixiuhong.astparser.api.parser.dtos.syntaxtree.Operator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ParsingService {
    ASTNode parseToAST(String expression, Map<String, Operator> operators);
    String[] tokenize(String expression, Map<String, Operator> operators);
}
