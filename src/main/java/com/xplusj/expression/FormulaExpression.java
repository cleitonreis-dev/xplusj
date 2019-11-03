package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.parser.ExpressionParserProcessor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FormulaExpression implements Expression {
    private final String formula;
    private final ExpressionParser parser;
    private final Function<VariableContext,TwoStackBasedProcessor> processorFactory;
    private final Supplier<InstructionListProcessor> instructionListProcessorFactory;

    private List<Consumer<ExpressionParserProcessor>> instructions;

    private FormulaExpression(final String formula,
                            final ExpressionParser parser,
                            final Function<VariableContext,TwoStackBasedProcessor> processorFactory,
                            final Supplier<InstructionListProcessor> instructionListProcessorFactory) {
        this.formula = formula;
        this.parser = parser;
        this.processorFactory = processorFactory;
        this.instructionListProcessorFactory = instructionListProcessorFactory;
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

        TwoStackBasedProcessor interpreter = this.processorFactory.apply(variableContext);

        instructions.forEach(instruction->instruction.accept(interpreter));

        return interpreter.getCalculatedResult();
    }

    private synchronized void initialize() {
        if(instructions == null) {
            InstructionListProcessor interpreter = instructionListProcessorFactory.get();
            parser.eval(formula, interpreter);
            instructions = interpreter.getInstructions();
        }
    }

    public static FormulaExpression create(final String formula,
                                           final ExpressionParser parser,
                                           final Function<VariableContext,TwoStackBasedProcessor> processorFactory,
                                           final Supplier<InstructionListProcessor> instructionListProcessorFactory){
        return new FormulaExpression(formula, parser, processorFactory, instructionListProcessorFactory);
    }
}
