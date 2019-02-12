package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.OperationPrecedence;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operator.Operator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class InlineExpressionTests {

    @Mock
    private Environment env;

    private InlineExpression expression;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        expression = new InlineExpression(env);

        when(env.hasOperator('+')).thenReturn(true);

        Operator<BinaryOperatorRuntimeContext> plus = Operator.binary('+', OperationPrecedence.low(), c->c.getFirstValue() + c.getSecondValue());
        when(env.getBinaryOperator('+')).thenReturn(plus);
    }


    @Test
    public void testPlus(){
        double result = expression.eval("1+1");
        assertEquals(2D, result, 0);
    }

    /*private class EnvironmentTest implements Environment{

        private Map<Character, Operator<? extends OperatorRutimeContext>> operatorMap = new HashMap<>();

        public<T extends OperatorRutimeContext> EnvironmentTest addOperator(Operator<T> operator){
            operatorMap.put(operator.getSymbol(), operator);
            return this;
        }

        @Override
        public boolean hasFunction(String name) {
            return false;
        }

        @Override
        public boolean hasOperator(char symbol) {
            return operatorMap.containsKey(symbol);
        }

        @Override
        public <T extends OperatorRutimeContext> Operator<T> getOperator(char symbol) {
            return operatorMap.get(symbol);
        }
    }*/
}