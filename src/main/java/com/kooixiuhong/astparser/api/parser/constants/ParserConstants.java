package com.kooixiuhong.astparser.api.parser.constants;

import com.kooixiuhong.astparser.api.constants.APIConstant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ParserConstants {


    public static final String PARSE_PATH = APIConstant.BASE_PATH + "/parser/parse";


    private static final Character[] META_CHAR_ARR = { ')', '(', '[', ']', '{', '}', '\\', '^', '$', '|', '?', '*', '+',  '.', '<',
        '>', '-', '=', '!' };
    private static final Set<Character> META_CHARACTER = new HashSet<>(Arrays.asList(META_CHAR_ARR));


    private static final String[] RES_OPS_ARR = {"(", ")", "[", "]"};
    private static final Set<String> RESERVE_OPERATORS = new HashSet<>(Arrays.asList(RES_OPS_ARR));


    public static Set<Character> getMetaCharacter() {
        return META_CHARACTER;
    }

    public static Set<String> getReserveOperators() {
        return RESERVE_OPERATORS;
    }
}
