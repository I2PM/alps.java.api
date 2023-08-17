package alps.java.api.util;
import alps.java.api.util.priv.ClassGraphHelper;

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

    private static Set<ClassLoader> additionalClassLoaders = new HashSet<>();

    public static <T> List<T> getEnumerableOfType(T element) {
        Class<?> typeOfBase = element.getClass();
        if (!typeOfBase.isInstance(element)) {
            return null;
        }
        List<T> objects = new ArrayList<>();

        // Add the class loader of T to the set if not contained already
        additionalClassLoaders.add(typeOfBase.getClassLoader());

        // Go through all the types that are in registered class loaders
        for (ClassLoader classLoader : additionalClassLoaders) {
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
    }

    private static Class<?>[] getClassArrayFromLoader(ClassLoader loader) {
        if (loader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) loader;
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
        } else {
            // Handle other class loader types
            return new Class<?>[]{};
        }
    }


    private static <T> T createInstance(Class<?> type) {
        try {
            return (T) type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
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

    public static void addClassToCheckForTypes(Class<?> cl) {
        if (additionalClasses == null) {
            additionalClasses = new HashSet<>();
        }
        additionalClasses.add(cl);
    }

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



