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
        if(instructions == null)
            initialize();

        TwoStackBasedProcessor processor = this.processorFactory.apply(variableContext);
        instructions.forEach(instruction->instruction.accept(processor));
        return processor.getCalculatedResult();
    }

    private synchronized void initialize() {
        if(instructions == null) {
            InstructionListProcessor processor = instructionListProcessorFactory.get();
            parser.eval(formula, processor);
            instructions = processor.getInstructions();
        }
    }

    public static FormulaExpression create(final String formula,
                                           final ExpressionParser parser,
                                           final Function<VariableContext,TwoStackBasedProcessor> processorFactory,
                                           final Supplier<InstructionListProcessor> instructionListProcessorFactory){
        if(formula == null)
            throw new ExpressionException("Invalid expression: expression null");

        if(formula.trim().isEmpty())
            throw new ExpressionException("Invalid expression: expression empty");

        return new FormulaExpression(formula, parser, processorFactory, instructionListProcessorFactory);
    }
}
