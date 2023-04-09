package alps.java.api.FunctionalityCapsules;

import alps.java.api.parsing.*;

public interface IFunctionalityCapsule<T> {
    boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element);
}