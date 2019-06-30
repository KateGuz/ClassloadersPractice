package ua.jland.classloaders.ex2;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
* Loads classes from .jar's and folders
* */
public class CustomClassLoader extends ClassLoader {

    private String[] paths;

    public CustomClassLoader(String[] paths) {
        this.paths = paths;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("Load class:" + name);
        String packagePathToClass = name.replace('.', '/').concat(".class");
        try {
            for (String path : paths) {
                if (path.endsWith(".jar")) {
                    JarFile jarFile = new JarFile(path);
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                            continue;
                        }

                        if (packagePathToClass.equals(jarEntry.getName())) {
                            try (InputStream inputStream = jarFile.getInputStream(jarEntry)) {
                                System.out.println("Found in jar:" + path);
                                return getClass(name, inputStream);
                            }
                        }
                    }
                } else {
                    try (InputStream inputStream = new FileInputStream(new File(path, packagePathToClass))) {
                        return getClass(name, inputStream);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception occurred while trying to load class " + name);
        }
        return super.findClass(name);
    }

    private Class getClass(String name, InputStream inputStream) throws IOException {
        byte[] array = IOUtils.toByteArray(inputStream);
        Class clazz = defineClass(name, array, 0, array.length);
        resolveClass(clazz);
        return clazz;
    }

}
