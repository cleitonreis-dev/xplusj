package com.xplusj.interpreter.parser;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;
import com.xplusj.expression.Stack;
import com.xplusj.operator.*;
import com.xplusj.operator.function.FunctionIdentifier;
import com.xplusj.parser.DefaultExpressionParser;
import com.xplusj.parser.ExpressionParseException;
import com.xplusj.parser.ExpressionParserProcessor;
import com.xplusj.tokenizer.DefaultExpressionTokenizer;
import com.xplusj.tokenizer.ExpressionTokenizer;
import org.junit.Before;
import org.junit.Ignore;
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
public class ExpressionParserTest {

    private static final BOperator PLUS = new BOperator('+', Precedence.low());
    private static final UOperator UPLUS = new UOperator('+', Precedence.highest());
    private static final BOperator MINUS = new BOperator('-', Precedence.low());
    private static final UOperator UMINUS = new UOperator('-', Precedence.highest());
    private static final BOperator MULT = new BOperator('*', Precedence.higherThan(Precedence.low()));
    private static final BOperator DIV = new BOperator('/', Precedence.higherThan(Precedence.low()));

    @Mock
    private GlobalContext context;

    @Mock
    private Environment env;

    private InstructionLogger instructionLogger;

    ExpressionTokenizer tokenizer = DefaultExpressionTokenizer.create(env);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        instructionLogger = new InstructionLogger();

        when(env.getContext()).thenReturn(context);
        when(env.getParser()).thenReturn(DefaultExpressionParser.create(context, tokenizer));

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
    public void test_1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test_2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "ab";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new StackLog().pushVar("ab"), instructionLogger.log);
    }

    @Test
    public void test_3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "sum(1,2)";
        StackLog log = new StackLog()
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
        StackLog log = new StackLog()
                .pushOperator("sum(a,b)")
                .pushVar("x")
                .pushVar("y")
                .callOperator("sum(a,b)");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, log, instructionLogger.log);
    }

    @Test
    public void test1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+1";
        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        assertEquals(exp,new StackLog().pushOperator("+").pushValue(1).callOperator("+"), instructionLogger.log);
    }

    @Test
    public void testUnaryOperator2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1+(-1)";

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
                .pushValue(1).pushOperator("*").pushVar("v").callOperator("*");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "v/a";

        StackLog expectedStack = new StackLog()
                .pushVar("v").pushOperator("/").pushVar("a").callOperator("/");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "1*sum(2,1+ab)-var_1";

        StackLog expectedStack = new StackLog()
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

        StackLog expectedStack = new StackLog()
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

    @Test @Ignore
    public void testParenthesis1(){
        thrown.expect(ExpressionParseException.class);
        DefaultExpressionParser parser = DefaultExpressionParser.create(context, tokenizer);
        String exp = "((1*2)";

        parser.eval(exp, instructionLogger);
    }

    private static class StackLog{
        private StringBuilder log = new StringBuilder();

        public StackLog pushValue(double value) {
            log.append("pv").append(value).append(';');
            return this;
        }

        public StackLog pushVar(String value) {
            log.append("px").append(value).append(';');
            return this;
        }

        public StackLog pushConstant(String name) {
            log.append("pc").append(name).append(';');
            return this;
        }

        public StackLog pushOperator(String operator) {
            log.append("po").append(operator).append(';');
            return this;
        }

        public StackLog callOperator(String operator) {
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
            StackLog stackLog = (StackLog) o;
            return log.toString().equals(stackLog.log.toString());
        }

        @Override
        public int hashCode() {
            return Objects.hash(log);
        }
    }

    private static class InstructionLogger implements ExpressionParserProcessor {
        private Stack<Operator<?>> opStack = Stack.instance();
        private StackLog log = new StackLog();

        @Override
        public void addValue(double value) {
            log.pushValue(value);
            //System.out.println(log);
        }

        @Override
        public void addVar(String value) {
            log.pushVar(value);
            //System.out.println(log);
        }

        @Override
        public void addConstant(String name) {
            log.pushConstant(name);
            //System.out.println(log);
        }

        @Override
        public void addOperator(Operator<?> operator) {
            log.pushOperator(operator.toString());
            opStack.push(operator);
            //System.out.println(log);
        }

        @Override
        public void callLastOperatorAndAddResult() {
            log.callOperator(opStack.pull().toString());
            //System.out.println(log);
        }

        @Override
        public Operator<?> getLastOperator() {
            //System.out.println(log);
            return opStack.peek();
        }
    }

    private static class BOperator extends BinaryOperator{

        public BOperator(char symbol, Precedence precedence) {
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

    private static class UOperator extends UnaryOperator{

        public UOperator(char symbol, Precedence precedence) {
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

        public Func(String name, String...params) {
            super(new FunctionIdentifier(name, Arrays.asList(params)), ctx->0d);
            this.params = params;
        }

        @Override
        public String toString() {
            return format("%s(%s)",super.getName(),String.join(",",params));
        }
    }
}