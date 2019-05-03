package com.xplusj.core;

public interface Expression {

    double eval();

    double eval(VariableContext variableContext);
}
