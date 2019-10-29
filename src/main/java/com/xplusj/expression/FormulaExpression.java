package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParserProcessor;
import com.xplusj.parser.ExpressionParser;

import java.util.List;
import java.util.function.Consumer;

public class FormulaExpression implements Expression {
    private final String formula;
    private final Environment env;
    private final ExpressionParser parser;
    private List<Consumer<ExpressionParserProcessor>> instructions;

    public FormulaExpression(final String formula,
                             final Environment env) {
        this.formula = formula;
        this.env = env;
        this.parser = env.getParser();
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
            env,
            variableContext,
            Stack.instance(),
            Stack.instance()
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
