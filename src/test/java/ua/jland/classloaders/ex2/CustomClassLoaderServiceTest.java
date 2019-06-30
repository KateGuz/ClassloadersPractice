package ua.jland.classloaders.ex2;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class CustomClassLoaderServiceTest {
    private ClassLoader classLoader1;
    private ClassLoader classLoader2;

    @Before
    public void before() {
        classLoader1 = new CustomClassLoaderService().getClassLoader("src/test/resources/ex2/test-classloader-1");
        classLoader2 = new CustomClassLoaderService().getClassLoader("src/test/resources/ex2/test-classloader-2");
    }

    @Test
    public void testClassLoaderLoadClass() throws ClassNotFoundException {
        assertNotNull(classLoader1.loadClass("ua.jland.classloaders.ex2.Person"));
    }

    @Test
    public void testClassLoaderLoadLib() throws ClassNotFoundException {
        assertNotNull(classLoader1.loadClass("com.fasterxml.jackson.databind.ObjectMapper"));
    }

    @Test
    public void testClassesUnderDifferentClassLoaders() throws ClassNotFoundException {
        Class<?> class1 = classLoader1.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
        Class<?> class2 = classLoader2.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
        assertNotNull(class1);
        assertNotNull(class2);
        assertNotEquals(class1, class2);
    }

    @Test(expected = ClassNotFoundException.class)
    public void testClassLoaderDoesntLoadClass() throws ClassNotFoundException {
        Class.forName("ua.jland.classloaders.ex2.Person");
    }

    @Test(expected = ClassNotFoundException.class)
    public void testClassLoaderDoesntLoadClassFromLib() throws ClassNotFoundException {
        Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
    }

    @Test
    public void testClassFromCustomClassLoader() throws Exception {
        // prepare
        Class<?> clazz = classLoader1.loadClass("com.fasterxml.jackson.databind.ObjectMapper");
        Object instance = clazz.newInstance();
        Method writeValueAsStringMethod = clazz.getMethod("writeValueAsString", Object.class);

        TestUser testUser = new TestUser("John", 27);

        Object result = writeValueAsStringMethod.invoke(instance, testUser);

        assertNotNull(result);
        assertEquals(String.class, result.getClass());
        assertEquals("{\"name\":\"John\",\"age\":27}", result);
    }

}