package com.xplusj.expression;

import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.parser.ExpressionParserProcessor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static com.xplusj.VariableContext.vars;
import static com.xplusj.variable.Variable.var;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultExpressionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*@Mock
    private Environment env;*/

    @Mock
    private ExpressionParser parser;

    @Mock
    private TwoStackBasedProcessor twoStackBasedProcessor;

    @Mock
    private Function<VariableContext, ExpressionParserProcessor<Double>> twoStackProcessorFactory;

    @Before
    public void setUp(){
        when(twoStackProcessorFactory.apply(any(VariableContext.class))).thenReturn(twoStackBasedProcessor);
    }

    @Test
    public void testNullExpression(){
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Invalid expression: expression null");
        DefaultExpression.create(null,parser,twoStackProcessorFactory);
    }

    @Test
    public void testEmptyExpression(){
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Invalid expression: expression empty");
        DefaultExpression.create("",parser,twoStackProcessorFactory).eval();
    }

    @Test
    public void testEmptyExpression2(){
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Invalid expression: expression empty");
        DefaultExpression.create("",parser,twoStackProcessorFactory).eval();
    }

    @Test
    public void testExpressionWithoutVars(){
        String exp = "1+1";

        when(parser.eval(exp,twoStackBasedProcessor)).thenReturn(2D);

        double value = DefaultExpression.create(exp,parser,twoStackProcessorFactory).eval();

        verify(twoStackProcessorFactory).apply(VariableContext.EMPTY);
        verify(parser).eval(exp,twoStackBasedProcessor);

        assertEquals(2D, value,0.0000000000000001);
    }

    @Test
    public void testExpressionWitVars(){
        String exp = "1+b";
        VariableContext vctx = vars(var("b", 1));

        when(parser.eval(exp,twoStackBasedProcessor)).thenReturn(2D);

        double value = DefaultExpression.create(exp,parser,twoStackProcessorFactory).eval(vctx);

        verify(twoStackProcessorFactory).apply(vctx);
        verify(parser).eval(exp,twoStackBasedProcessor);

        assertEquals(2D, value,0.0000000000000001);
    }
}