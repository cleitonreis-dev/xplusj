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

    private FormulaExpression(final String formula,
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

        TwoStackBasedProcessor interpreter = TwoStackBasedProcessor.create(
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
            InstructionListProcessor interpreter = new InstructionListProcessor();
            parser.eval(formula, interpreter);
            instructions = interpreter.getInstructions();
        }
    }

    public static FormulaExpression create(final String formula, final Environment env){
        return new FormulaExpression(formula,env);
    }
}
