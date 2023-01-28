package alps.java.api.util;
import java.lang.reflect.Modifier;
import java.util.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

    public class ReflectiveEnumerator {
        static{

        }
        private static Set<ClassLoader> additionalAssemblies;

        static {
            additionalAssemblies = new HashSet<>();
        }
        public static <T> Iterable<Class<? extends T>> getEnumerableOfType(Class<T> element)
        {
            // This is the "parent"-type, when want to find all types that extend this one
                Class typeOfBase = element.getClass();
            if (!(element instanceof T)) return null;
            List<T> objects = new LinkedList<T>();

            // Add the assembly containing T to the set if not contained already
            //TODO: Die Zeile umschreiben
            ClassLoader sameAsContainingT = ClassLoader.getAssembly(typeof(T));
            addAssemblyToCheckForTypes(sameAsContainingT);

            // Go through all the types that are in the same assembly as T
            // And through all the types that are in registered assemblies
            for(ClassLoader assembly: additionalAssemblies)
                //TODO: Das umschreiben
            for(Class<?> type: assembly.getClass()
                    .Where(myType => myType.IsClass && !myType.IsAbstract && myType.BaseType == typeOfBase))
            {
                T createdObject = createInstance<T>(type);
                // created Object is null
                if (createdObject != null) objects.add(createdObject);

            }
            return objects;
        }

        public static void addAssemblyToCheckForTypes(ClassLoader assembly)
        {
            if (additionalAssemblies == null)
            additionalAssemblies = new HashSet<ClassLoader>();
            if (assembly == null) return;
            additionalAssemblies.add(assembly);
        }

        /*public static ITreeNode<T> getTreeForType<T>(Type baseType)
        {
            Type typeOfBase = element.GetType();
            if (!(element is T)) return null;
            List<T> objects = new List<T>();

            // Case baseType is a class type or abstract class type -> the type might be a BaseType to other (abstract) classes
            if (!baseType.IsInterface)
                foreach (Type subType in
                    Assembly.GetAssembly(typeof(T)).GetTypes()
                    .Where(myType => myType.IsClass && !myType.IsAbstract && myType.BaseType == typeOfBase))
                {
                    T createdObject = createInstance<T>(subType, constructorArgs);
                    // created Object is null
                    if (createdObject != null) objects.Add(createdObject);

                }
            foreach (Type type in
                Assembly.GetAssembly(typeof(T)).GetTypes()
                .Where(myType => myType.IsInterface && checkInterfacesSame(myType, typeOfBase)))
            {
                foreach (Type innerType in
                Assembly.GetAssembly(typeof(T)).GetTypes()
                .Where(myType => myType.IsClass && !myType.IsAbstract && checkDirectlyImplementsInterface(type, myType)))
                {
                    T createdObject = createInstance<T>(type, constructorArgs);
                    // created Object is null
                    if (createdObject != null) objects.Add(createdObject);

                }

            }
            return objects;
        }*/
        // Baum hier erstellen, Baum aus Interfaces und Klassen erzeugen
        // Weiterhin kinder der klassen checken, aber auch direkte kinder von interfaces

        public static <T> T createInstance(Class type)
        {
            T finalInstance = null;
            Object[] args;
            try
            {
                args = new Object[type.getConstructors()[0].getParameters().length];
                int count = 0;
                for(Parameter info: type.getConstructors()[0].getParameters())
                {
                    args[count] = (info.hasDefaultValue) ? info.DefaultValue : null;
                    count++;
                }
                finalInstance = (T)Constructor.createInstance(type, args);
            }
            catch(Exception e)
        {
            boolean worked = false;
            int num = 0;
            do
            {
                try
                {
                    finalInstance = (T)Activator.createInstance(type);
                    worked = true;
                }
                catch (Exception ee)
                {
                    num++;
                    args = new Object[num];
                    for (int i = 0; i < num; i++)
                    {
                        args[i] = null;
                    }
                }
            } while (!worked && num < 15);
        }

        return finalInstance;
        }

    }


}
