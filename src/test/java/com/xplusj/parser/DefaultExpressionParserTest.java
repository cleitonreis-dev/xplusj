package com.xplusj.parser;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.expression.Stack;
import com.xplusj.factory.ExpressionTokenizerFactory;
import com.xplusj.operator.Constant;
import com.xplusj.operator.Operator;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
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

    private static final BOperatorExecutor PLUS = new BOperatorExecutor("+", Precedence.low());
    private static final UOperatorExecutor UPLUS = new UOperatorExecutor("+", Precedence.highest());
    private static final BOperatorExecutor MINUS = new BOperatorExecutor("-", Precedence.low());
    private static final UOperatorExecutor UMINUS = new UOperatorExecutor("-", Precedence.highest());
    private static final BOperatorExecutor MULT = new BOperatorExecutor("*", Precedence.higherThan(Precedence.low()));
    private static final BOperatorExecutor DIV = new BOperatorExecutor("/", Precedence.higherThan(Precedence.low()));
    private static final BOperatorExecutor EQ = new BOperatorExecutor("==", Precedence.higherThan(Precedence.low()));
    private static final BOperatorExecutor GT = new BOperatorExecutor(">", Precedence.higherThan(Precedence.low()));
    private static final BOperatorExecutor GE = new BOperatorExecutor(">=", Precedence.higherThan(Precedence.low()));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ExpressionOperatorDefinitions definitions;

    private DefaultExpressionParserTest.InstructionLogger instructionLogger;

    private ExpressionTokenizer tokenizer;

    @Before
    public void setUp(){
        instructionLogger = new DefaultExpressionParserTest.InstructionLogger();

        tokenizer = ExpressionTokenizerFactory.defaultFactory().create(definitions);

        when(definitions.hasBinaryOperator("+")).thenReturn(true);
        when(definitions.getBinaryOperator("+")).thenReturn(PLUS);
        when(definitions.hasBinaryOperator("-")).thenReturn(true);
        when(definitions.getBinaryOperator("-")).thenReturn(MINUS);
        when(definitions.hasBinaryOperator("*")).thenReturn(true);
        when(definitions.getBinaryOperator("*")).thenReturn(MULT);
        when(definitions.hasBinaryOperator("/")).thenReturn(true);
        when(definitions.getBinaryOperator("/")).thenReturn(DIV);

        when(definitions.hasUnaryOperator("+")).thenReturn(true);
        when(definitions.getUnaryOperator("+")).thenReturn(UPLUS);
        when(definitions.hasUnaryOperator("-")).thenReturn(true);
        when(definitions.getUnaryOperator("-")).thenReturn(UMINUS);

        when(definitions.hasBinaryOperator("==")).thenReturn(true);
        when(definitions.getBinaryOperator("==")).thenReturn(EQ);
        when(definitions.hasBinaryOperator(">")).thenReturn(true);
        when(definitions.getBinaryOperator(">")).thenReturn(GT);
        when(definitions.hasBinaryOperator(">=")).thenReturn(true);
        when(definitions.getBinaryOperator(">=")).thenReturn(GE);

        when(definitions.hasFunction("sum")).thenReturn(true);
        when(definitions.getFunction("sum")).thenReturn(new DefaultExpressionParserTest.Func("sum","a","b"));

        when(definitions.hasFunction("max")).thenReturn(true);
        when(definitions.getFunction("max")).thenReturn(new DefaultExpressionParserTest.Func("max","a","b","..."));

        when(definitions.hasConstant("PI")).thenReturn(Boolean.TRUE);
        when(definitions.getConstant("PI")).thenReturn(Constant.newConst("PI", Math.PI));

        when(definitions.hasBinaryOperator("&")).thenReturn(Boolean.TRUE,Boolean.FALSE);
        when(definitions.getBinaryOperator("&")).thenReturn(null);

        when(definitions.hasFunction("plus")).thenReturn(false);


    }

    @Test
    public void test_1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test_2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "ab";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushVar("ab"), instructionLogger.log);
    }

    @Test
    public void test_3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "(1)";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test_6(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "((1))";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushValue(1), instructionLogger.log);
    }

    @Test
    public void test1(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "+1";

        parser.eval(exp, instructionLogger);

        assertEquals(exp,new DefaultExpressionParserTest.StackLog().pushOperator("+").pushValue(1).callOperator("+"), instructionLogger.log);
    }

    @Test
    public void testUnaryOperator2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
    public void testUnaryOperator4(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "+sum(2,3)";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushOperator("+")
                .pushOperator("sum(a,b)")
                .pushValue(2).pushValue(3)
                .callOperator("sum(a,b)")
                .callOperator("+");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack,instructionLogger.log);
    }

    @Test
    public void testVar(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "1*v";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1).pushOperator("*").pushVar("v").callOperator("*");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "v/a";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushVar("v").pushOperator("/").pushVar("a").callOperator("/");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVar3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testUnexpectedEnd1(){
        String exp = "1*2+(";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,4,"Unclosed parenthesis at index %s",4).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testUnexpectedEnd2(){
        String exp = "1*2+sum(1,)";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 10, "invalid identifier '%s' at index %s", ")", 10).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testParenthesis1(){
        String exp = "((1*2)";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,0,"Unclosed parenthesis at index %s",0).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testParenthesis2(){
        String exp = "((1*2)-1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,0,"Unclosed parenthesis at index %s",0).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testParenthesis3(){
        String exp = "1*((2-1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp,2,"Unclosed parenthesis at index %s",2).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testCommaOutOfContext(){
        String exp = "1,1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 1, "invalid identifier '%s' at index %s", ",", 1).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testClosingParenthesisOutOfContext(){
        String exp = "1+1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 3, "invalid identifier '%s' at index %s", ")", 3).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator(){
        String exp = "1&1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 1, "Binary operator '%s' not found", "&").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator2(){
        String exp = "*1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 0, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator3(){
        String exp = "(*1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 1, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator4(){
        String exp = "sum(1,*1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 6, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testInvalidOperator5(){
        String exp = "1+*1";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 2, "Unary operator '%s' not found", "*").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionNotFound(){
        String exp = "plus(1,1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 0, "Function '%s' not found", "plus").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionUnclosed(){
        String exp = "sum(";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 3, "Unclosed parenthesis at index %s", 3).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam(){
        String exp = "sum(1,,3)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 6, "invalid identifier '%s' at index %s", ',', 6).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam2(){
        String exp = "1+sum(1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 2, "Function requires %s parameters", 2).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam3(){
        String exp = "sum(1,3";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 6, "Function not closed properly").getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testFunctionInvalidParam4(){
        String exp = "sum(1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 0, "Function requires %s parameters", 2).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        parser.eval(exp, instructionLogger);
    }

    @Test
    public void testOperatorLongIdentifier(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "1==2";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1)
                .pushOperator("==")
                .pushValue(2)
                .callOperator("==");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testOperatorLongIdentifier2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "1>2";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1)
                .pushOperator(">")
                .pushValue(2)
                .callOperator(">");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testOperatorLongIdentifier3(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "1>=2";

        DefaultExpressionParserTest.StackLog expectedStack = new DefaultExpressionParserTest.StackLog()
                .pushValue(1)
                .pushOperator(">=")
                .pushValue(2)
                .callOperator(">=");

        parser.eval(exp, instructionLogger);

        assertEquals(exp,expectedStack, instructionLogger.log);
    }

    @Test
    public void testVarArgs(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "max(1,2)";
        DefaultExpressionParserTest.StackLog log = new DefaultExpressionParserTest.StackLog()
                .pushOperator("max(a,b,...)")
                .pushValue(1)
                .pushValue(2)
                .callOperator("max(a,b,...)");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, log, instructionLogger.log);
    }

    @Test
    public void testVarArgs2(){
        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
        String exp = "max(1,2,3)";
        DefaultExpressionParserTest.StackLog log = new DefaultExpressionParserTest.StackLog()
                .pushOperator("max(a,b,...)")
                .pushValue(1)
                .pushValue(2)
                .pushValue(3)
                .callOperator("max(a,b,...)");

        parser.eval(exp, instructionLogger);

        assertEquals(exp, log, instructionLogger.log);
    }

    @Test
    public void testVarArgs3(){
        String exp = "max(1)";
        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(exp, 0, "Function requires at least %s parameters", 2).getMessage());

        DefaultExpressionParser parser = DefaultExpressionParser.create(definitions, tokenizer);
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

    private static class InstructionLogger implements ExpressionParserProcessor<DefaultExpressionParserTest.StackLog> {
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
        public void callLastOperatorAndAddResult(int totalOfParamsToRead) {
            log.callOperator(opStack.pull().toString());
            System.out.println(log);
        }

        @Override
        public Operator<?> getLastOperator() {
            System.out.println(log);
            return opStack.peek();
        }

        @Override
        public StackLog getResult() {
            return log;
        }
    }

    private static class BOperatorExecutor extends BinaryOperator {

        BOperatorExecutor(String symbol, Precedence precedence) {
            super(symbol, precedence, (ctx)->null);
        }



        @Override
        public String toString() {
            return ""+super.getIdentifier();
        }
    }

    private static class UOperatorExecutor extends UnaryOperator {

        UOperatorExecutor(String symbol, Precedence precedence) {
            super(symbol, precedence, (ctx)->null);
        }



        @Override
        public String toString() {
            return ""+super.getIdentifier();
        }
    }

    private static class Func extends FunctionOperator {
        private final String[] params;

        Func(String name, String...params) {
            super(name,Arrays.asList(params), Arrays.asList(params).contains("..."), ctx->0d);
            this.params = params;
        }

        @Override
        public String toString() {
            return format("%s(%s)",super.getIdentifier(),String.join(",",params));
        }
    }
}