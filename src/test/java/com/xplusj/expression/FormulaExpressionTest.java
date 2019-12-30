package com.xplusj.expression;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.VariableContext;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.parser.ExpressionParserProcessor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FormulaExpressionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ExpressionContext env;

    @Mock
    private ExpressionOperatorDefinitions globalContext;

    @Mock
    private ExpressionParser parser;

    @Mock
    private TwoStackBasedProcessor twoStackBasedProcessor;

    @Mock
    private InstructionListProcessor instructionListProcessor;

    @Mock
    private Function<VariableContext, ExpressionParserProcessor<Double>> twoStackProcessorFactory;

    @Mock
    private Supplier<ExpressionParserProcessor<List<Consumer<ExpressionParserProcessor>>>> instructionListProcessorFactory;

    @Before
    public void setUp(){
        when(twoStackProcessorFactory.apply(any(VariableContext.class))).thenReturn(twoStackBasedProcessor);
        when(instructionListProcessorFactory.get()).thenReturn(instructionListProcessor);
    }

    @Test
    public void testNullExpression(){
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Invalid expression: expression null");
        FormulaExpression.create(null,parser,twoStackProcessorFactory,instructionListProcessorFactory).eval();
    }

    @Test
    public void testEmptyExpression(){
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Invalid expression: expression empty");
        FormulaExpression.create("",parser,twoStackProcessorFactory,instructionListProcessorFactory).eval();
    }

    @Test
    public void testEmptyExpression2(){
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Invalid expression: expression empty");
        FormulaExpression.create("",parser,twoStackProcessorFactory,instructionListProcessorFactory).eval();
    }

    @Test
    public void testExpressionWithoutVars(){
        String exp = "1+1";
        BinaryOperatorDefinition p = BinaryOperatorDefinition.create("+", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());
        BinaryOperator pp = BinaryOperator.create(env,p);

        when(twoStackBasedProcessor.getResult()).thenReturn(2D);
        when(parser.eval(exp, instructionListProcessor)).thenReturn(Arrays.asList(
                (processor)->processor.addValue(1D),
                (processor)->processor.addOperator(p),
                (processor)->processor.addValue(1D)
        ));

        double value = FormulaExpression.create(exp,parser,twoStackProcessorFactory,instructionListProcessorFactory).eval();

        verify(twoStackProcessorFactory).apply(VariableContext.EMPTY);
        verify(parser).eval(exp,instructionListProcessor);
        verify(twoStackBasedProcessor).getResult();
        verify(instructionListProcessorFactory).get();

        verify(twoStackBasedProcessor,times(2)).addValue(1D);
        verify(twoStackBasedProcessor,times(1)).addOperator(p);

        assertEquals(2D, value,0.0000000000000001);
    }

    @Test
    public void testExpressionWitVars(){
        String exp = "1+b";
        VariableContext vctx = VariableContext.builder().add("b", 1).build();
        BinaryOperatorDefinition p = BinaryOperatorDefinition.create("+", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());
        BinaryOperator pp = BinaryOperator.create(env,p);

        when(twoStackBasedProcessor.getResult()).thenReturn(2D);

        when(parser.eval(exp, instructionListProcessor)).thenReturn(Arrays.asList(
                (processor)->processor.addValue(1D),
                (processor)->processor.addOperator(p),
                (processor)->processor.addValue(1D)
        ));

        double value = FormulaExpression.create(exp,parser,twoStackProcessorFactory,instructionListProcessorFactory).eval(vctx);

        verify(twoStackProcessorFactory).apply(vctx);
        verify(parser).eval(exp,instructionListProcessor);
        verify(twoStackBasedProcessor).getResult();
        verify(instructionListProcessorFactory).get();

        verify(twoStackBasedProcessor,times(2)).addValue(1D);
        verify(twoStackBasedProcessor,times(1)).addOperator(p);

        assertEquals(2D, value,0.0000000000000001);
    }
}