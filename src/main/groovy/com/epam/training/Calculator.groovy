package com.epam.training

import org.slf4j.LoggerFactory

class Calculator {

    private static final char PLUS = '+'
    private static final char MINUS = '-'
    private static final char MULTIPLY = '*'
    private static final char DIVIDE = '/'

    def static log = LoggerFactory.getLogger(Calculator.class)

    def static calculate(String expression) {
        def numbers = new LinkedList<>()
        def operations = new LinkedList<>()

        for (def i = 0; i < expression.length(); i++) {
            char sign = expression.&charAt(i)

            if (sign == '(') {
                operations.add('(' as char)
            } else if (sign == ')') {
                while (operations.getLast() != '(') {
                    processOperator(numbers, operations.removeLast())
                }
                operations.removeLast()
            } else if (isOperator(sign)) {
                while (!operations.isEmpty() && priority(operations.getLast()) >= priority(sign)) {
                    processOperator(numbers, operations.removeLast())
                }
                operations.add(sign)
            } else {
                StringBuilder number = new StringBuilder()
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    number.append(expression.charAt(i++))
                }
                i--
                numbers.add(number.toBigInteger())
            }


        }

        while (!operations.isEmpty()) {
            processOperator(numbers, operations.removeLast())
        }

        def result = { "Result - [ ${it} ]" }
     //   println result(numbers.get(0) ?: '~')

        log.info("result="+result);
        numbers.get(0)
    }

    private static boolean isOperator(char operation) {
        return [PLUS, MINUS, MULTIPLY, DIVIDE].contains(operation)
    }

    private static BigInteger priority(char operation) {

        switch (operation) {
            case PLUS:
            case MINUS:
                return 1
            case MULTIPLY:
            case DIVIDE:
                return 2
            default:
                return -1
        }
    }

    private static void processOperator(def list, char operation) {
        def num1 = list.removeLast()
        def num2 = list.removeLast()

        switch (operation) {
            case PLUS:
                list.add(num2 + num1)
                break
            case MINUS:
                list.add(num2 - num1)
                break
            case MULTIPLY:
                list.add(num2 * num1)
                break
            case DIVIDE:
                list.add(num2 / num1)
                break
        }

    }

}