package com.xplusj;

public interface Expression {

    double eval();

    double eval(VariableContext variableContext);
}
