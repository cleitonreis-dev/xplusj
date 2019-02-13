package com.xplusj;

import java.util.Map;

public interface ExpressionEvaluator {

    double eval();

    double eval(Map<String,Double> variables);
}
