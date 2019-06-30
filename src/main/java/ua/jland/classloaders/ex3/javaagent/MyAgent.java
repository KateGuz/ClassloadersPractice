package ua.jland.classloaders.ex3.javaagent;

import java.lang.instrument.Instrumentation;

public class MyAgent {
    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassLogger(args));
    }
}
