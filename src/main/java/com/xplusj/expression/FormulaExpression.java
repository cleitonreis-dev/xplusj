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
    private final Function<VariableContext,ExpressionParserProcessor<Double>> processorFactory;
    private final Supplier<ExpressionParserProcessor<List<Consumer<ExpressionParserProcessor>>>> instructionListProcessorFactory;

    private List<Consumer<ExpressionParserProcessor>> instructions;

    private FormulaExpression(final String formula,
                            final ExpressionParser parser,
                            final Function<VariableContext,ExpressionParserProcessor<Double>> processorFactory,
                            final Supplier<ExpressionParserProcessor<List<Consumer<ExpressionParserProcessor>>>> instructionListProcessorFactory) {
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

        ExpressionParserProcessor<Double> processor = this.processorFactory.apply(variableContext);
        instructions.forEach(instruction->instruction.accept(processor));
        return processor.getResult();
    }

    private synchronized void initialize() {
        if(instructions == null) {
            ExpressionParserProcessor<List<Consumer<ExpressionParserProcessor>>> processor = instructionListProcessorFactory.get();
            instructions = parser.eval(formula, processor);
        }
    }

    public static FormulaExpression create(final String formula,
                                           final ExpressionParser parser,
                                           final Function<VariableContext,ExpressionParserProcessor<Double>> processorFactory,
                                           final Supplier<ExpressionParserProcessor<List<Consumer<ExpressionParserProcessor>>>> instructionListProcessorFactory){
        if(formula == null)
            throw new ExpressionException("Invalid expression: expression null");

        if(formula.trim().isEmpty())
            throw new ExpressionException("Invalid expression: expression empty");

        return new FormulaExpression(formula, parser, processorFactory, instructionListProcessorFactory);
    }
}
