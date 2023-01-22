package alps.java.api.FunctionalityCapsules;

import alps.java.api.parsing.*;
import alps.java.api.StandardPASS.*;
import alps.java.api.util.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/// <summary>
/// This encapsulates the functionality for handling implements relations between elements.
/// Every element can hold a capsule, delegating all the incoming calls to this capsule.
/// </summary>
/// <typeparam name="T"></typeparam>



public class ImplementsFunctionalityCapsule<T extends IPASSProcessModelElement> implements IImplementsFunctionalityCapsule<T>
        {
protected final ICompatibilityDictionary<String, T> implementedInterfaces = new CompatibilityDictionary<String, T>();
protected final Set<String> implementedInterfacesIDs = new HashSet<String>();
protected final ICapsuleCallback callback;

public ImplementsFunctionalityCapsule(ICapsuleCallback callback)
        {
        this.callback = callback;
        }


public boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {
        if (predicate.contains(OWLTags.implements))
        {
        if (element instanceof T)
        {
            T fittingElement = (T) element;
        addImplementedInterface(fittingElement);
        return true;
        }
        else
        {
        addImplementedInterfaceIDReference(objectContent);
        return true;
        }
        }
        return false;
        }

public void setImplementedInterfaces(Set<T> implementedInterface, int removeCascadeDepth)
        {
            //TODO: values() richtig machen
        for(T implInterface: getImplementedInterfaces().values())
        {
        removeImplementedInterfaces(implInterface.getModelComponentID(), removeCascadeDepth);
        }
        if (implementedInterface == null) return;
        for (T implInterface: implementedInterface)
        {
        addImplementedInterface(implInterface);
        }
        }
//TODO: addTriple Methode
public void addImplementedInterface(T implementedInterface)
        {
        if (implementedInterface == null) { return; }
        if (implementedInterfaces.tryAdd(implementedInterface.getModelComponentID(), implementedInterface))
        {
        callback.publishElementAdded(implementedInterface);
        implementedInterface.register(callback);
        callback.addTriple(new IncompleteTriple(OWLTags.abstrImplements, implementedInterface.getUriModelComponentID()));
        }
        }

public void removeImplementedInterfaces(String id, int removeCascadeDepth
        {
        if (id == null) return;
        if (implementedInterfaces.tryGetValue(id, T implInterface))
        {
        implementedInterfaces.remove(id);
        implInterface.unregister(callback, removeCascadeDepth);
        callback.removeTriple(new IncompleteTriple(OWLTags.abstrImplements, implInterface.getUriModelComponentID()));
        }
        }

public Map<String, T> getImplementedInterfaces()
        {
        return new HashMap<String, T>(implementedInterfaces);
        }



public void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs)
        {
        implementedInterfacesIDs.clear();
        for (String implementedInterfaceID: implementedInterfacesIDs)
        implementedInterfacesIDs.add(implementedInterfaceID);
        }

public void addImplementedInterfaceIDReference(String implementedInterfaceID)
        {
        implementedInterfacesIDs.add(implementedInterfaceID);
        }

public void removeImplementedInterfacesIDReference(String implementedInterfaceID)
        {
        implementedInterfacesIDs.remove(implementedInterfaceID);
        }

public Set<String> getImplementedInterfacesIDReferences()
        {
        Set<String> ts = new HashSet<String>(implementedInterfacesIDs);
        for(String implementedInterfaceID: this.implementedInterfaces.keySet())
        ts.add(implementedInterfaceID);
        return ts;
        }


        }

