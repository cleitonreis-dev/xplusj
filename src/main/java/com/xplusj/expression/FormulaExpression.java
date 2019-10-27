package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.interpreter.ExpressionInterpreterProcessor;
import com.xplusj.interpreter.ExpressionParser;
import com.xplusj.interpreter.stack.Stack;

import java.util.List;
import java.util.function.Consumer;

public class FormulaExpression implements Expression {
    private final String formula;
    private final GlobalContext context;
    private final ExpressionParser parser;
    private List<Consumer<ExpressionInterpreterProcessor>> instructions;

    public FormulaExpression(final String formula, final GlobalContext context, final ExpressionParser parser) {
        this.formula = formula;
        this.context = context;
        this.parser = parser;
    }

    @Override
    public double eval() {
        return eval(VariableContext.EMPTY);
    }

    @Override
    public double eval(VariableContext variableContext) {
        if(formula.trim().isEmpty())
            return 0;

        if(instructions == null)
            initialize();

        TwoStackBasedInterpreter interpreter = TwoStackBasedInterpreter.create(
            context,
            variableContext,
            Stack.defaultStack(),
            Stack.defaultStack()
        );

        instructions.forEach(instruction->instruction.accept(interpreter));

        return interpreter.getCalculatedResult();
    }

    private synchronized void initialize() {
        if(instructions == null) {
            InstructionListInterpreter interpreter = new InstructionListInterpreter();
            parser.eval(formula, interpreter);
            instructions = interpreter.getInstructions();
        }
    }
}
