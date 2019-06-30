package ua.jland.classloaders.ex1;

import com.sun.javafx.util.Logging;

import java.util.ArrayList;

public class ClassLoaderHierarchy {
    public static void main(String[] args) {

        System.out.println("Classloader of this class: "
                + ClassLoaderHierarchy.class.getClassLoader().getClass());

        System.out.println("Classloader of Logging: "
                + Logging.class.getClassLoader());

        System.out.println("Classloader of ArrayList: "
                + ArrayList.class.getClassLoader());

    }
}
