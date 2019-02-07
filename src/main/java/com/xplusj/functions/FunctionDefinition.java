package com.xplusj.functions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "totalOfParams"})
@Getter
public class FunctionDefinition {
    private final String name;
    private final int totalOfParams;
    private final Function<RuntimeContext, Double> function;
}
