package com.xplusj.functions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "totalOfParams"})
public class FunctionDefinition {
    public final String name;
    public final int totalOfParams;
    public final Function<RuntimeContext, Double> function;
}
