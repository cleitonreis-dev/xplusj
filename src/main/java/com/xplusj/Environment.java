package com.xplusj;

import com.xplusj.operation.BuiltinOperations;
import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operation.operator.Operator;
import com.xplusj.operation.operator.UnaryOperatorRuntimeContext;

public interface Environment {

    Environment BUILTIN_CONTEXT = new DefaultContext(
        BuiltinOperations.functions(),
        BuiltinOperations.binaryOperators(),
        BuiltinOperations.unaryOperators(),
        BuiltinOperations.constants()
    );

    boolean hasFunction(String name);

    boolean hasBinaryOperator(char symbol);

    boolean hasUnaryOperator(char symbol);

    boolean hasConstant(String name);

    Operator<BinaryOperatorRuntimeContext> getBinaryOperator(char symbol);

    Operator<UnaryOperatorRuntimeContext> getUnaryOperator(char symbol);

    ExpressionFunction getFunction(String name);

    Double getConstant(String name);

    static DefaultEnvironment.Builder defaultEnv(){
        return DefaultEnvironment.builder();
    }

    static Environment context(){
        return RootContext.instance();
    }

    /*
      GlobalContext context = GlobalContext.default();

      ContextAppender context2 = ContextAppender.of(
                                    GlobalContext.default(),
                                    GlobalContext.builder()
                                        .functions(ExpressionFunctions.builder())

    */
}
