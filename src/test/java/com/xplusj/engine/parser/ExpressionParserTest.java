package com.xplusj.engine.parser;

import com.xplusj.core.GlobalContext;
import com.xplusj.core.operator.*;
import com.xplusj.engine.stack.Stack;
import com.xplusj.operator.AbstractOperator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionParserTest {

    private static final BOperator PLUS = new BOperator('+', Precedence.low());
    private static final UOperator UPLUS = new UOperator('+', Precedence.highest());
    private static final BOperator MINUS = new BOperator('-', Precedence.low());
    private static final UOperator UMINUS = new UOperator('-', Precedence.highest());
    private static final BOperator MULT = new BOperator('*', Precedence.higherThan(Precedence.low()));
    private static final BOperator DIV = new BOperator('/', Precedence.higherThan(Precedence.low()));

    @Mock
    private GlobalContext context;

    private InstructionLogger instructionLogger;

    @Before
    public void setUp(){
        instructionLogger = new InstructionLogger();

        when(context.hasBinaryOperator('+')).thenReturn(true);
        when(context.getBinaryOperator('+')).thenReturn(PLUS);
        when(context.hasBinaryOperator('-')).thenReturn(true);
        when(context.getBinaryOperator('-')).thenReturn(MINUS);
        when(context.hasBinaryOperator('*')).thenReturn(true);
        when(context.getBinaryOperator('*')).thenReturn(MULT);
        when(context.hasBinaryOperator('/')).thenReturn(true);
        when(context.getBinaryOperator('/')).thenReturn(DIV);

        when(context.hasUnaryOperator('+')).thenReturn(true);
        when(context.getUnaryOperator('+')).thenReturn(UPLUS);
        when(context.hasUnaryOperator('-')).thenReturn(true);
        when(context.getUnaryOperator('-')).thenReturn(UMINUS);

        when(context.hasFunction("sum")).thenReturn(true);
        when(context.getFunction("sum")).thenReturn(new Func("sum","a","b"));

    }

    @Test
    public void test1(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1+1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,"pv1.0;po+;pv1.0;co+;", instructionLogger.getLog());
    }

    @Test
    public void test2(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1+1-25.678";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,"pv1.0;po+;pv1.0;co+;po-;pv25.678;co-;", instructionLogger.getLog());
    }

    @Test
    public void testParentheses1(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1+(1-25)";

        parser.eval(exp, instructionLogger);

        assertEquals(exp, "pv1.0;po+;pv1.0;po-;pv25.0;co-;co+;", instructionLogger.getLog());
    }

    @Test
    public void testParentheses2(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1/(1*(25-1))-1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp, "pv1.0;po/;pv1.0;po*;pv25.0;po-;pv1.0;co-;co*;co/;po-;pv1.0;co-;",
                instructionLogger.getLog());
    }

    @Test
    public void testParentheses3(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1-(1*(25-1))/1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp, "pv1.0;po-;pv1.0;po*;pv25.0;po-;pv1.0;co-;co*;po/;pv1.0;co/;co-;",
                instructionLogger.getLog());
    }

    @Test
    public void testUnaryOperator1(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "+1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,"po+;pv1.0;co+;",
                instructionLogger.getLog());
    }

    @Test
    public void testUnaryOperator2(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1+(-1)";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,"pv1.0;po+;po-;pv1.0;co-;co+;",
                instructionLogger.getLog());
    }

    @Test
    public void testUnaryOperator3(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1+-1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,"pv1.0;po+;po-;pv1.0;co-;co+;",
                instructionLogger.getLog());
    }

    @Test
    public void testFunction(){
        ExpressionParser parser = new ExpressionParser(context);
        String exp = "1*sum(2,1+3)";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,"pv1.0;po*;posum(a,b);pv2.0;pv1.0;po+;pv3.0;co+;cosum(a,b);co*;",
                instructionLogger.getLog());
    }

    private static class InstructionLogger implements ExpressionParser.InstructionHandler{

        private Stack<Operator<?>> opStack = Stack.defaultStack();
        private StringBuilder log = new StringBuilder();

        @Override
        public void pushValue(double value) {
            log.append("pv").append(value).append(';');
        }

        @Override
        public void pushConstant(String name) {
            log.append("pc").append(name).append(';');
        }

        @Override
        public void pushOperator(Operator<?> operator) {
            log.append("po").append(operator).append(';');
            opStack.push(operator);
        }

        @Override
        public void callOperator() {
            log.append("co").append(opStack.pull()).append(';');
        }

        @Override
        public Operator<?> peekOperator() {
            return opStack.peek();
        }

        public String getLog(){
            return log.toString();
        }
    }

    private static abstract class OperatorTest<T extends OperatorContext> extends AbstractOperator<T> {

        public OperatorTest(char symbol, Precedence precedence) {
            super(null, symbol, precedence, c->0d);
        }

        @Override
        public String toString() {
            return ""+super.getSymbol();
        }

        @Override
        public int getParamsLength() {
            return 0;
        }
    }

    private static class BOperator extends OperatorTest<BinaryOperatorContext>
            implements com.xplusj.core.operator.BinaryOperator{

        public BOperator(char symbol, Precedence precedence) {
            super(symbol, precedence);
        }

        @Override
        public double execute(BinaryOperatorContext context) {
            return 0;
        }
    }

    private static class UOperator extends OperatorTest<UnaryOperatorContext>
            implements com.xplusj.core.operator.UnaryOperator{

        public UOperator(char symbol, Precedence precedence) {
            super(symbol, precedence);
        }

        @Override
        public double execute(UnaryOperatorContext context) {
            return 0;
        }
    }

    private static class Func extends com.xplusj.operator.FunctionOperator{
        private final String[] params;

        public Func(String name, String...params) {
            super(name, new HashSet<>(Arrays.asList(params)), ctx->0d);
            this.params = params;
        }

        @Override
        public String toString() {
            return format("%s(%s)",super.getName(),String.join(",",params));
        }
    }
}