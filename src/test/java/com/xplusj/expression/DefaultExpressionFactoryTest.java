package com.xplusj.expression;

import com.xplusj.ExpressionContext;
import com.xplusj.parser.ExpressionParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DefaultExpression.class, FormulaExpression.class})
public class DefaultExpressionFactoryTest {

    @Mock
    private ExpressionContext env;

    @Mock
    ExpressionParser parser;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(DefaultExpression.class);
        PowerMockito.mockStatic(FormulaExpression.class);

        when(env.getParser()).thenReturn(parser);
    }

    @Test
    public void testCreateExpression(){
        String exp = "1+1";
        DefaultExpressionFactory.getInstance().expression(exp, env);
        PowerMockito.verifyStatic(DefaultExpression.class, VerificationModeFactory.times(1));
        DefaultExpression.create(eq(exp), eq(parser), Matchers.any());
    }

    @Test
    public void testCreateFormula(){
        String exp = "1+1";
        DefaultExpressionFactory.getInstance().formula(exp, env);
        PowerMockito.verifyStatic(FormulaExpression.class, VerificationModeFactory.times(1));
        FormulaExpression.create(eq(exp), eq(parser), Matchers.any(), Matchers.any());
    }
}