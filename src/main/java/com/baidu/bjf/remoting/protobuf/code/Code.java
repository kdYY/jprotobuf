package com.baidu.bjf.remoting.protobuf.code;

import com.baidu.bjf.remoting.protobuf.utils.ClassHelper;
import com.baidu.bjf.remoting.protobuf.utils.JDKCompilerHelper;
import com.baidu.bjf.remoting.protobuf.utils.compiler.Compiler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class Code {
    private final CodeType codeType;
    private final Object code;

    public Code(CodeType codeType, Object code) {
        this.codeType = codeType;
        this.code = code;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public Object getCode() {
        return code;
    }

    static Code stringCode(String code) {
        return new Code(CodeType.STRING_CODE, code);
    }

    static Code byteCode(byte[] code) {
        return new Code(CodeType.BYTE_CODE, code);
    }

    public Class<?> toClazz(ClassLoader loader, Compiler compiler, String className, long lastModifyTime, FileOutputStream fos) {
        return codeType.toClazz(loader, compiler, code, className, lastModifyTime, fos);
    }

    public byte[] getBytes(Charset charset) {
        return codeType.getBytes(code, charset);
    }

    public abstract static class CodeType<T> {

        public static final CodeType<String> STRING_CODE = new CodeType<String>() {
            @Override
            public Class<?> toClazz(ClassLoader classLoader, Compiler compiler, String code, String className, long lastModify,
                                    FileOutputStream fos) {
                if (compiler == null) {
                    compiler = JDKCompilerHelper.getJdkCompiler();
                }
                Class<?> newClass;

                try {
                    newClass = compiler.compile(className, code, classLoader, fos, lastModify);
                } catch (Exception e) {
                    compiler = JDKCompilerHelper.getJdkCompiler(classLoader);
                    newClass = compiler.compile(className, code, classLoader, fos, lastModify);
                }
                return newClass;
            }

            @Override
            public byte[] getBytes(String code, Charset charset) {
                return code.getBytes(charset);
            }
        };

        public static final CodeType<byte[]> BYTE_CODE = new CodeType<byte[]>() {
            @Override
            public Class<?> toClazz(ClassLoader classLoader, Compiler compiler, byte[] code, String className, long lastModify, FileOutputStream fos) {
                if (compiler == null) {
                    compiler = JDKCompilerHelper.getJdkCompiler();
                }
                if (fos != null) {
                    try {
                        fos.write(code);
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            public byte[] getBytes(byte[] code, Charset charset) {
                return code;
            }
        };

        public abstract Class<?> toClazz(ClassLoader classLoader, Compiler compiler, T code, String className, long lastModify,
                                         FileOutputStream fos);

        public abstract byte[] getBytes(T code, Charset charset);
    }
}
