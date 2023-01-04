package alps.net.api.api.util;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class ReflectiveEnumerator {
    private static Set<Class> additionalAssemblies;

    static {
        additionalAssemblies = new HashSet<>();
    }

    private ReflectiveEnumerator() {}

    public static <T> List<T> getEnumerableOfType(Class<T> baseType) {
        List<T> objects = new ArrayList<>();

        // Add the assembly containing T to the set if not contained already
        addAssemblyToCheckForTypes(baseType);

        // Go through all the types that are in the same assembly as T
        // And through all the types that are in registered assemblies
        for (Class<?> cls : additionalAssemblies) {
            for (Class<?> type : cls.getClasses()) {
                if (type.isInterface() && !(Modifier.isAbstract(type.getModifiers())) && baseType.isAssignableFrom(type)) {
                    T createdObject = (T) createInstance(type);
                    if (createdObject != null) {
                        objects.add(createdObject);
                    }
                }
            }
        }
        return objects;
    }


    public static void addAssemblyToCheckForTypes(Class cls) {
        if (additionalAssemblies == null) {
            additionalAssemblies = new HashSet<>();
        }
        if (cls == null) {
            return;
        }
        additionalAssemblies.add(cls);
    }

    public static <T> T createInstance(Class<T> type) {
        T finalInstance = null;
        Object[] args;
        try {
            Constructor<T> ctor = (Constructor<T>)type.getConstructors()[0];
            Parameter[] params = ctor.getParameters();
            args = new Object[params.length];
            int count = 0;
            for (Parameter param : params) {
                if (param.getType().isPrimitive()) {
                    args[count] = param.getType().getConstructor().newInstance();
                } else {
                    args[count] = null;
                }
                count++;
            }
            finalInstance = ctor.newInstance(args);
        } catch (Exception e) {
            // handle the exception
        }

        return finalInstance;
    }
}
