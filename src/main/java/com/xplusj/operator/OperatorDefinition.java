package com.xplusj.operator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@Getter
@ToString(of = {"type", "identifier", "precedence"})
@EqualsAndHashCode(of = {"type", "identifier"})
public class OperatorDefinition {
    private final OperatorType type;
    private final char identifier;
    private final OperatorPrecedence precedence;
    private final Function<OperatorRuntimeContext,Double> operator;
}
