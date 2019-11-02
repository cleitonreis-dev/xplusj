package com.xplusj.parser;

import com.xplusj.GlobalContext;
import com.xplusj.expression.Stack;
import com.xplusj.operator.*;
import com.xplusj.operator.function.FunctionIdentifier;
import com.xplusj.tokenizer.DefaultExpressionTokenizer;
import com.xplusj.tokenizer.ExpressionTokenizer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultExpressionParserTest {

    private static final DefaultExpressionParserTest.BOperator PLUS = new DefaultExpressionParserTest.BOperator('+', Precedence.low());
    private static final DefaultExpressionParserTest.UOperator UPLUS = new DefaultExpressionParserTest.UOperator('+', Precedence.highest());
    private static final DefaultExpressionParserTest.BOperator MINUS = new DefaultExpressionParserTest.BOperator('-', Precedence.low());
    private static final DefaultExpressionParserTest.UOperator UMINUS = new DefaultExpressionParserTest.UOperator('-', Precedence.highest());
    private static final DefaultExpressionParserTest.BOperator MULT = new DefaultExpressionParserTest.BOperator('*', Precedence.higherThan(Precedence.low()));
    private static final DefaultExpressionParserTest.BOperator DIV = new DefaultExpressionParserTest.BOperator('/', Precedence.higherThan(Precedence.low()));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private GlobalContext context;

    private DefaultExpressionParserTest.InstructionLogger instructionLogger;

    private ExpressionTokenizer tokenizer;

    @Before
    public void setUp(){
        instructionLogger = new DefaultExpressionParserTest.InstructionLogger();

        tokenizer = DefaultExpressionTokenizer.create(context);

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
        when(context.getFunction("sum")).thenReturn(new DefaultExpressionParserTest.Func("sum","a","b"));

        when(context.hasConstant("PI")).thenReturn(Boolean.TRUE);
        when(context.getConstant("PI")).thenReturn(Math.PI);

        when(context.hasBinaryOperator('&')).thenReturn(Boolean.TRUE,Boolean.FALSE);
        when(context.getBinaryOperator('&')).thenReturn(null);

        when(context.hasFunction("plus")).thenReturn(false);
    }

    @Test
    public void test_1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test_2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "ab";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushVar("ab"), instructionLogger.log);
    }

    @Test
    public void test_3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "sum(1,2)";
        DefaultExpressionParserTest.StackLog log = new DefaultExpressionParserTest.StackLog()
                .pushOperator("sum(a,b)")
                .pushValue(1)
                .pushValue(2)
                .callOperator("sum(a,b)");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, log, instructionLogger.log);
    }

    @Test
    public void test_4(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "sum(x,y)";
        DefaultExpressionParserTest.StackLog log = new DefaultExpressionParserTest.StackLog()
                .pushOperator("sum(a,b)")
                .pushVar("x")
                .pushVar("y")
                .callOperator("sum(a,b)");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, log, instructionLogger.log);
    }

    @Test
    public void test_5(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "(1)";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test_6(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "((1))";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+1";
        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1)
                .pushOperator("+")
                .pushValue(1)
                .callOperator("+");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void test2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+1-25.678";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1)
                .pushOperator("+")
                .pushValue(1)
                .callOperator("+")
                .pushOperator("-")
                .pushValue(25.678)
                .callOperator("-");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testParentheses1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+(1-25)";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1)
                .pushOperator("+")
                .pushValue(1)
                .pushOperator("-")
                .pushValue(25)
                .callOperator("-")
                .callOperator("+");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, expectedStack, instructionLogger.log);
    }

    @Test
    public void testParentheses2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1/(1*(25-1))-1";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("/")
                .pushValue(1).pushOperator("*")
                .pushValue(25).pushOperator("-").pushValue(1)
                .callOperator("-")
                .callOperator("*")
                .callOperator("/")
                .pushOperator("-").pushValue(1)
                .callOperator("-");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, expectedStack, instructionLogger.log);
    }

    @Test
    public void testParentheses3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1-(1*(25-1))/1";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("-")
                .pushValue(1).pushOperator("*")
                .pushValue(25).pushOperator("-").pushValue(1).callOperator("-")
                .callOperator("*")
                .pushOperator("/")
                .pushValue(1)
                .callOperator("/")
                .callOperator("-");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, expectedStack, instructionLogger.log);
    }

    @Test
    public void testParentheses4(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "sum(1-(1*(25-1))/1,((-1)))";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushOperator("sum(a,b)")
                .pushValue(1).pushOperator("-")
                .pushValue(1).pushOperator("*")
                .pushValue(25).pushOperator("-").pushValue(1).callOperator("-")
                .callOperator("*")
                .pushOperator("/")
                .pushValue(1)
                .callOperator("/")
                .callOperator("-")
                .pushOperator("-")
                .pushValue(1)
                .callOperator("-")
                .callOperator("sum(a,b)");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, expectedStack, instructionLogger.log);
    }

    @Test
    public void testUnaryOperator1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "+1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushOperator("+").pushValue(1).callOperator("+"), instructionLogger.log);
    }

    @Test
    public void testUnaryOperator2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+(-1)";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("+")
                .pushOperator("-").pushValue(1).callOperator("-")
                .callOperator("+");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack,instructionLogger.log);
    }

    @Test
    public void testUnaryOperator3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+-1";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("+")
                .pushOperator("-").pushValue(1).callOperator("-")
                .callOperator("+");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack,instructionLogger.log);
    }

    @Test
    public void testFunction(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1*sum(2,1+3)";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("*")
                .pushOperator("sum(a,b)").pushValue(2)
                .pushValue(1).pushOperator("+").pushValue(3).callOperator("+")
                .callOperator("sum(a,b)")
                .callOperator("*");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1*v";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("*").pushVar("v").callOperator("*");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "v/a";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushVar("v").pushOperator("/").pushVar("a").callOperator("/");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1*sum(2,1+ab)-var_1";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("*")
                .pushOperator("sum(a,b)").pushValue(2)
                .pushValue(1).pushOperator("+").pushVar("ab").callOperator("+")
                .callOperator("sum(a,b)")
                .callOperator("*")
                .pushOperator("-")
                .pushVar("var_1")
                .callOperator("-");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar4(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1*sum(2,(1+ab))-var_1";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("*")
                .pushOperator("sum(a,b)").pushValue(2)
                .pushValue(1).pushOperator("+").pushVar("ab").callOperator("+")
                .callOperator("sum(a,b)")
                .callOperator("*")
                .pushOperator("-")
                .pushVar("var_1")
                .callOperator("-");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testConst(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "PI*2";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushConstant("PI")
                .pushOperator("*")
                .pushValue(2)
                .callOperator("*");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testConst2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "PI*sum(2,(1+PI))-var_1+PI";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushConstant("PI")
                .pushOperator("*")
                .pushOperator("sum(a,b)")
                .pushValue(2)
                .pushValue(1)
                .pushOperator("+")
                .pushConstant("PI")
                .callOperator("+")
                .callOperator("sum(a,b)")
                .callOperator("*")
                .pushOperator("-")
                .pushVar("var_1")
                .callOperator("-")
                .pushOperator("+")
                .pushConstant("PI")
                .callOperator("+");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testUnexpectedEnd(){
        String exp = "1*2+";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,3,"Unexpected end of expression").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testUnexpectedEnd1(){
        String exp = "1*2+(";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,4,"Unclosed parenthesis at index %s",4).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testUnexpectedEnd2(){
        String exp = "1*2+sum(1,)";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 10, "invalid identifier '%s' at index %s", ")", 10).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testParenthesis1(){
        String exp = "((1*2)";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,0,"Unclosed parenthesis at index %s",0).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testParenthesis2(){
        String exp = "((1*2)-1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,0,"Unclosed parenthesis at index %s",0).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testParenthesis3(){
        String exp = "1*((2-1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,2,"Unclosed parenthesis at index %s",2).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testCommaOutOfContext(){
        String exp = "1,1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 1, "invalid identifier '%s' at index %s", ",", 1).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testClosingParenthesisOutOfContext(){
        String exp = "1+1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 3, "invalid identifier '%s' at index %s", ")", 3).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator(){
        String exp = "1&1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 1, "Binary operator '%s' not found", "&").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator2(){
        String exp = "*1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 0, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator3(){
        String exp = "(*1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 1, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator4(){
        String exp = "sum(1,*1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 6, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator5(){
        String exp = "1+*1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 2, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionNotFound(){
        String exp = "plus(1,1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 0, "Function '%s' not found", "plus").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionUnclosed(){
        String exp = "sum(";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 3, "Unclosed parenthesis at index %s", 3).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam(){
        String exp = "sum(1,,3)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 6, "invalid identifier '%s' at index %s", ',', 6).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam2(){
        String exp = "1+sum(1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 2, "Function requires %s parameters", 2).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam3(){
        String exp = "sum(1,3";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 6, "Function not closed properly").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam4(){
        String exp = "sum(1,3,4)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 7, "Function not closed properly").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        parser.eval(exp, instructionLogger);
    }


    private static class StackLog{
        private StringBuilder log = new StringBuilder();

        DefaultExpressionParserTest.StackLog pushValue(double value) {
            log.append("pv").append(value).append(';');
            return this;
        }

        DefaultExpressionParserTest.StackLog pushVar(String value) {
            log.append("px").append(value).append(';');
            return this;
        }

        DefaultExpressionParserTest.StackLog pushConstant(String name) {
            log.append("pc").append(name).append(';');
            return this;
        }

        DefaultExpressionParserTest.StackLog pushOperator(String operator) {
            log.append("po").append(operator).append(';');
            return this;
        }

        DefaultExpressionParserTest.StackLog callOperator(String operator) {
            log.append("co").append(operator).append(';');
            return this;
        }

        @Override
        public String toString() {
            return log.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DefaultExpressionParserTest.StackLog stackLog = (DefaultExpressionParserTest.StackLog) o;
            return log.toString().equals(stackLog.log.toString());
        }

        @Override
        public int hashCode() {
            return Objects.hash(log);
        }
    }

    private static class InstructionLogger implements ExpressionParserProcessor {
        private Stack<Operator<?>> opStack = Stack.instance();
        private DefaultExpressionParserTest.StackLog log = new DefaultExpressionParserTest.StackLog();

        @Override
        public void addValue(double value) {
            log.pushValue(value);
            System.out.println(log);
        }

        @Override
        public void addVar(String value) {
            log.pushVar(value);
            System.out.println(log);
        }

        @Override
        public void addConstant(String name) {
            log.pushConstant(name);
            System.out.println(log);
        }

        @Override
        public void addOperator(Operator<?> operator) {
            log.pushOperator(operator.toString());
            opStack.push(operator);
            System.out.println(log);
        }

        @Override
        public void callLastOperatorAndAddResult() {
            log.callOperator(opStack.pull().toString());
            System.out.println(log);
        }

        @Override
        public Operator<?> getLastOperator() {
            System.out.println(log);
            return opStack.peek();
        }
    }

    private static class BOperator extends BinaryOperator {

        BOperator(char symbol, Precedence precedence) {
            super(symbol, precedence, (ctx)->null);
        }

        @Override
        public double execute(BinaryOperatorContext context) {
            return 0;
        }

        @Override
        public String toString() {
            return ""+super.getSymbol();
        }
    }

    private static class UOperator extends UnaryOperator {

        UOperator(char symbol, Precedence precedence) {
            super(symbol, precedence, (ctx)->null);
        }

        @Override
        public double execute(UnaryOperatorContext context) {
            return 0;
        }

        @Override
        public String toString() {
            return ""+super.getSymbol();
        }
    }

    private static class Func extends FunctionOperator{
        private final String[] params;

        Func(String name, String...params) {
            super(new FunctionIdentifier(name, Arrays.asList(params)), ctx->0d);
            this.params = params;
        }

        @Override
        public String toString() {
            return format("%s(%s)",super.getName(),String.join(",",params));
        }
    }
}