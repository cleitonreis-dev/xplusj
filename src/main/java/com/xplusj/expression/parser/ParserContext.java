package com.xplusj.expression.parser;

import com.xplusj.Environment;
import com.xplusj.operation.Operation;
import com.xplusj.operation.OperationVisitor;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class ParserContext {

    public final Stack<Operation<?>> opStack;
    public final Stack<Double> valueStack;
    public final Environment env;
    public final Map<String,Double> vars;
    public final OperationVisitor visitor;
}
