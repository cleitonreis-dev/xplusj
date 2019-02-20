package com.xplusj;

import java.util.Map;

public interface FormulaExpression {

    double eval(Map<String,Double> variables);
}
