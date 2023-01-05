package alps.net.api.FunctionalityCapsules;

import alps.net.api.parsing.*;
public interface IFunctionalityCapsule<T>
{
    boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element);
}