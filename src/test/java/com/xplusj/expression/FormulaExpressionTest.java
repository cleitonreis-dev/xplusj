package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.VariableContext;
import com.xplusj.operator.BinaryOperator;
import com.xplusj.operator.Precedence;
import com.xplusj.parser.ExpressionParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
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
    private Environment env;

    @Mock
    private ExpressionParser parser;

    @Mock
    private TwoStackBasedProcessor twoStackBasedProcessor;

    @Mock
    private InstructionListProcessor instructionListProcessor;

    @Mock
    private Function<VariableContext,TwoStackBasedProcessor> twoStackProcessorFactory;

    @Mock
    private Supplier<InstructionListProcessor> instructionListProcessorFactory;

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
        BinaryOperator p = BinaryOperator.create('+', Precedence.low(), (ctx)->ctx.getFirstValue() + ctx.getSecondValue());

        when(twoStackBasedProcessor.getCalculatedResult()).thenReturn(2D);
        when(instructionListProcessor.getInstructions()).thenReturn(Arrays.asList(
                (processor)->processor.addValue(1D),
                (processor)->processor.addOperator(p),
                (processor)->processor.addValue(1D)
        ));

        double value = FormulaExpression.create(exp,parser,twoStackProcessorFactory,instructionListProcessorFactory).eval();

        verify(twoStackProcessorFactory).apply(VariableContext.EMPTY);
        verify(parser).eval(exp,instructionListProcessor);
        verify(twoStackBasedProcessor).getCalculatedResult();
        verify(instructionListProcessorFactory).get();
        verify(instructionListProcessor).getInstructions();

        verify(twoStackBasedProcessor,times(2)).addValue(1D);
        verify(twoStackBasedProcessor,times(1)).addOperator(p);

        assertEquals(2D, value,0.0000000000000001);
    }

    @Test
    public void testExpressionWitVars(){
        String exp = "1+b";
        VariableContext vctx = VariableContext.builder().add("b", 1).build();
        BinaryOperator p = BinaryOperator.create('+', Precedence.low(), (ctx)->ctx.getFirstValue() + ctx.getSecondValue());

        when(twoStackBasedProcessor.getCalculatedResult()).thenReturn(2D);

        when(instructionListProcessor.getInstructions()).thenReturn(Arrays.asList(
                (processor)->processor.addValue(1D),
                (processor)->processor.addOperator(p),
                (processor)->processor.addValue(1D)
        ));

        double value = FormulaExpression.create(exp,parser,twoStackProcessorFactory,instructionListProcessorFactory).eval(vctx);

        verify(twoStackProcessorFactory).apply(vctx);
        verify(parser).eval(exp,instructionListProcessor);
        verify(twoStackBasedProcessor).getCalculatedResult();
        verify(instructionListProcessorFactory).get();
        verify(instructionListProcessor).getInstructions();

        verify(twoStackBasedProcessor,times(2)).addValue(1D);
        verify(twoStackBasedProcessor,times(1)).addOperator(p);

        assertEquals(2D, value,0.0000000000000001);
    }
}