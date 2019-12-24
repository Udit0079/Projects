package com.cbs.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class PostfixEvaluator {

    public enum Operation {

        ADD, SUB, MUL, DIV, POW
    };
    public static HashMap<String, Operation> operators = new HashMap<String, Operation>();

    static {
        operators.put("+", Operation.ADD);
        operators.put("-", Operation.SUB);
        operators.put("*", Operation.MUL);
        operators.put("/", Operation.DIV);
        operators.put("^", Operation.POW);
    }
    private static Stack<BigDecimal> stack;

    public PostfixEvaluator() {
		stack = new Stack<BigDecimal>();	
    }

    public static BigDecimal evaluate(String postfixExp) {
        stack = new Stack<BigDecimal>();
        LinkedList<Object> tokens = tokenize(postfixExp);
        for (Object item : tokens) {
            if (item instanceof Operation) {
                stack.push(operateSingle(stack.pop(), stack.pop(), (Operation) item));
            } else {
                stack.push(new BigDecimal(item.toString()));
            }
        }
        return stack.pop();
    }

    private static LinkedList<Object> tokenize(String input) {
        Scanner tokens = new Scanner(input);
        LinkedList<Object> list = new LinkedList<Object>();
        String token;
        while (tokens.hasNext()) {
            token = tokens.next();
            if (operators.containsKey(token)) {
                list.add(operators.get(token));
            } else {
                list.add(Double.valueOf(token));
            }
        }
        return list;
    }

    private static BigDecimal operateSingle(BigDecimal op2, BigDecimal op1, Operation operation) {
        switch (operation) {
            case ADD:
                return op1.add(op2);
            case SUB:
                return op1.subtract(op2);
            case MUL:
                return op1.multiply(op2);
            case DIV:
                if(op2.equals(new BigDecimal("0.0"))||op2.equals(new BigDecimal("0"))||op2.equals(new BigDecimal("0.00"))){
                    op2 = new BigDecimal("1");
                }                
                return op1.divide(op2, 6, RoundingMode.HALF_UP);
            case POW:
                return new BigDecimal(Math.pow(op1.doubleValue(), op2.doubleValue()));
        }
        return null;
    }    
}