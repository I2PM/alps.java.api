package alps.java.api.util.priv;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassGraphHelper {
    public static List<Class<?>> getClassesOfType(Class<?> typeOfBase, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph()
                .overrideClassLoaders(classLoader)
                .enableAllInfo()
                .scan()) {

            ClassInfoList classInfoList = scanResult.getSubclasses(typeOfBase.getCanonicalName());
            for (String className : classInfoList.getNames()) {
                Class<?> clazz = Class.forName(className, false, classLoader);
                if (!Modifier.isAbstract(clazz.getModifiers()) && typeOfBase.isAssignableFrom(clazz)) {
                    classes.add(clazz);
                }
            }
        }

        return classes;
    }
}
