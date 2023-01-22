package alps.java.api.FunctionalityCapsules;

import alps.java.api.parsing.*;
import alps.java.api.StandardPASS.*;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;
import java.util.Set;

/// <summary>
/// Encapsulates the extends behavior.
/// Elements can hold this capsule and delegate methods to it
/// </summary>
/// <typeparam name="T"></typeparam>

public class GuardsFunctionalityCapsule<T extends  IPASSProcessModelElement> implements IGuardsFunctionalityCapsule<T>
        {
public void addGuardedElement(T guardedElement)
        {
        throw new NotImplementedException();
        }

public void addGuardedElementIDReference(String guardedElementID)
        {
        throw new NotImplementedException();
        }

public Map<String, T> getGuardedElements()
        {
        throw new NotImplementedException();
        }

public Set<String> getGuardedElementsIDReferences()
        {
        throw new NotImplementedException();
        }

public boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {
        throw new NotImplementedException();
        }

public void removeGuardedElement(String id, int removeCascadeDepth)
        {
        throw new NotImplementedException();
        }

public void removeGuardedElementIDReference(String id)
        {
        throw new NotImplementedException();
        }

public void setGuardedElements(Set<T> guardedElements, int removeCascadeDepth)
        {
        throw new NotImplementedException();
        }

public void setGuardedElementsIDReferences(Set<String> guardedElementsIDs)
        {
        throw new NotImplementedException();
        }
        }

