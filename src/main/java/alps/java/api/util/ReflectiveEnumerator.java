package alps.java.api.util;
import alps.java.api.StandardPASS.PassProcessModelElements.PASSProcessModel;
import alps.java.api.util.priv.ClassGraphHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

import java.lang.reflect.*;
import java.util.*;

public class ReflectiveEnumerator {

    private static Set<Class<?>> additionalClassLoaders = new HashSet<>();

    public static List<Class<?>> getSubclasses(String packageName, Class<?> superClass)  {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = packageName.replace('.', '/');
        List<Class<?>> subclasses = new ArrayList<>();

        for (File file : new File(classLoader.getResource(path).getFile()).listFiles()) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                    subclasses.add(clazz);
                }
            }
        }

        return subclasses;
    }
    public static <T> List<T> getEnumerableOfType(T element) {
        Class<?> typeOfBase = element.getClass();
        if (!typeOfBase.isInstance(element)) {
            return null;
        }
        List<T> objects = new ArrayList<>();
       /** Class<T> clazz = T.class;
        Package packageOfClass = clazz.getPackage();
        String packageName = packageOfClass.getName();
        String className = clazz.getSimpleName();

        String containingTName = packageName + "." + className;
        Class<?> sameAsContainingT = null;
*/
        //sameAsContainingT = Class.forName(containingTName);
        addClassToCheckForTypes(PASSProcessModel.class);
        //Passt eigentlich, eigentlich ist PASSProcessModel.class.getClassloader() Instanz vom URLCLassLoader
        System.out.println(PASSProcessModel.class.getClassLoader().toString());
        // Go through all the types that are in registered class loaders
        for (Class<?> classLoader : additionalClassLoaders) {
            try {
                //TODO: es geht nicht in die for-Schleife rein
                //Class<?>[] classes = getClassArrayFromLoader(classLoader);
                for (Class<?> type : getClassArrayFromLoader(classLoader.getClassLoader())) {
                    if (type.isAssignableFrom(typeOfBase) && !Modifier.isAbstract(type.getModifiers())) {
                        T createdObject = createInstance(type);
                        if (createdObject != null) {
                            objects.add(createdObject);
                        }
                    }
                }
            } catch (Exception e) {
                // Handle exceptions
            }
        }
        return objects;
    }
    /*public static <T> List<T> getEnumerableOfType(T element) {
        Class<?> typeOfBase = element.getClass();
        if (!elementType.isInstance(element)) {
            return null;
        }

        List<T> objects = new ArrayList<>();
        Class<T> clazz = elementType; // Wir übergeben den Datentyp explizit als Argument
        Package packageOfClass = clazz.getPackage();
        String packageName = packageOfClass.getName();
        String className = clazz.getSimpleName();

        String containingTName = packageName + "." + className;
        Class<?> sameAsContainingT = null;

        try {
            sameAsContainingT = Class.forName(containingTName);
            addClassToCheckForTypes(sameAsContainingT);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    // Go through all the types that are in registered class loaders
        for (Class<?> classLoader : additionalClassLoaders) {
        try {
            Class<?>[] classes = getClassArrayFromLoader(classLoader);
            for (Class<?> type : classes) {
                if (type.isAssignableFrom(typeOfBase) && !Modifier.isAbstract(type.getModifiers())) {
                    T createdObject = createInstance(type);
                    if (createdObject != null) {
                        objects.add(createdObject);
                    }
                }
            }
        } catch (Exception e) {
            // Handle exceptions
        }
    }
        return objects;
    }*/
//TODO: if-Abfrage ist immer false, d.h. leeres Array wird zurückgegeben und es kann nicht durch die Klassen durchiteriert werden
    private static Class<?>[] getClassArrayFromLoader(ClassLoader cl) {
        //if (cl instanceof AppClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) cl;
            List<Class<?>> classes = new ArrayList<>();

            try {
                Method method = URLClassLoader.class.getDeclaredMethod("getLoadedClasses");
                method.setAccessible(true);
                Class<?>[] loadedClasses = (Class<?>[]) method.invoke(urlClassLoader);

                classes.addAll(Arrays.asList(loadedClasses));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return classes.toArray(new Class<?>[0]);
        /**} else {
            // Handle other class loader types
            return new Class<?>[]{};
        }*/
    }


    private static <T> T createInstance(Class<?> type) {
        try {
            return (T) type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
    public static void addClassToCheckForTypes(Class<?> cl) {
        if (additionalClassLoaders == null) {
            additionalClassLoaders = new HashSet<>();
        }
        additionalClassLoaders.add(cl);
    }
}


    /**public static <T> List<T> getEnumerableOfType(Class<T> baseType) {
        List<T> objects = new ArrayList<>();

        // Add the class containing T to the set if not contained already
        addClassToCheckForTypes(baseType);

        // Go through all the types that are in the same class as T
        // And through all the types that are in registered classes
        for (Class<?> cl : additionalClasses) {
            if (baseType.isAssignableFrom(cl) && !Modifier.isAbstract(cl.getModifiers())) {
                T createdObject = createInstance(baseType, cl);
                // created Object is null
                if (createdObject != null) objects.add(createdObject);
            }
        }
        return objects;
    }
     */


/**
    @SuppressWarnings("unchecked")
    public static <T> T createInstance(Class<T> baseType, Class<?> cl) {
        T finalInstance = null;
        int num = 0;

        while (finalInstance == null && num < 15) {
            try {
                Constructor<?>[] constructors = cl.getConstructors();
                if (constructors.length > 0) {
                    Constructor<?> constructor = constructors[0];
                    Object[] args = new Object[constructor.getParameterCount()];
                    finalInstance = (T) constructor.newInstance(args);
                }
                else {
                    finalInstance = (T) cl.newInstance();
                }
            } catch (Exception e) {
                num++;
            }
        }

        return finalInstance;
    }*/



