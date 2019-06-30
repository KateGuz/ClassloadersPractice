package ua.jland.classloaders.ex3.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

public class ClassLogger implements ClassFileTransformer {
    private String path;

    public ClassLogger(String path) {
        this.path = path;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        String normalizedClassName = className.replaceAll("/", ".");
        System.out.println("Processing class " + normalizedClassName);

        if (normalizedClassName.startsWith("ua.jland")) {

            int lastIndexOfSlash = className.lastIndexOf("/");
            String directoryPath = path + className.substring(0, lastIndexOfSlash);

            String fileName = path + className + ".class";
            System.out.println("Saving bytecode to " + fileName);
            try {
                Path path = Paths.get(directoryPath);
                Files.createDirectories(path);
                Path filePath = Paths.get(fileName);
                Files.write(filePath, classfileBuffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ClassPool classPool = ClassPool.getDefault();
            try {
                CtClass ctClass = classPool.get(normalizedClassName);
                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                System.out.println("Declared methods:");
                for (CtMethod declaredMethod : declaredMethods) {
                    System.out.print(declaredMethod.getReturnType().getName() + " ");
                    System.out.print(declaredMethod.getName() + "()");
                }

            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return classfileBuffer;
    }

}
