package ControllerPerso;

import java.lang.reflect.Method;

public class MappingInfo {
    private final String className;
    private final Method method;

    public MappingInfo(String className, Method method) {
        this.className = className;
        this.method = method;
    }

    public String getClassName() { return className; }
    public Method getMethod() { return method; }
}