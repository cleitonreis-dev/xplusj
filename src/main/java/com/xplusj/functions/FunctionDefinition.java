package com.xplusj.functions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "totalOfParams"})
public class FunctionDefinition {
    public final String name;
    public final int totalOfParams;
    public final Function function;
}
