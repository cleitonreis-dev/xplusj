package com.xplusj.operation;

public interface OperationVisitable {

    double accept(OperationVisitor visitor);
}
