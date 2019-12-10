package com.baidu.bjf.remoting.protobuf.code.test;

import com.baidu.bjf.remoting.protobuf.code.AbstractCodeGenerator;
import com.baidu.bjf.remoting.protobuf.code.Code;

import java.util.Set;

public class testCodeGenerator extends AbstractCodeGenerator {


    /**
     * Instantiates a new abstract code generator.
     *
     * @param cls the cls
     */
    public testCodeGenerator(Class<?> cls) {
        super(cls);
    }

    @Override
    public Code getCode() {
        return null;
    }

    @Override
    public Set<Class<?>> getRelativeProxyClasses() {
        return null;
    }
}
