package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IInterfaceSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.InterfaceSubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the SystemInterfaceSubject owl class defined in the abstract pass ont.
 * A SystemInterfaceSubject is an InterfaceSubject which can contain other InterfaceSubjects.
 */
public class SystemInterfaceSubject extends InterfaceSubject implements ISystemInterfaceSubject
        {
private final ICompatibilityDictionary<String, IInterfaceSubject> containedInterfaceSubjects
        = new CompatibilityDictionary<String, IInterfaceSubject>();

/**
 * Name of the class, needed for parsing
 */

private final String CLASS_NAME = "SystemInterfaceSubject";
@Override
public String getClassName()
        {
        return CLASS_NAME;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new SystemInterfaceSubject();
        }

protected SystemInterfaceSubject() { }

                /**
                 *
                 * @param layer The layer this subject should be placed onto
                 * @param labelForId
                 * @param incomingMessageExchange
                 * @param containedInterfaceSubjects
                 * @param outgoingMessageExchange
                 * @param maxSubjectInstanceRestriction
                 * @param referencedSubject If the InterfaceSubject is referencing another FullySpecifiedSubject, this can be passed here
                 * @param comment
                 * @param additionalLabel
                 * @param additionalAttribute
                 */
                //TODO: Konstruktor überladen
                public SystemInterfaceSubject(IModelLayer layer, String labelForId, Set<IMessageExchange> incomingMessageExchange,
                                              Set<IInterfaceSubject> containedInterfaceSubjects, Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction,
                                              IFullySpecifiedSubject referencedSubject, String comment, String additionalLabel,
                                              List<IIncompleteTriple> additionalAttribute){
        super(layer, labelForId, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction, referencedSubject,
        comment, additionalLabel, additionalAttribute);
        setInterfaceSubjects(containedInterfaceSubjects);
        }


public boolean addInterfaceSubject(IInterfaceSubject subject)
        {
        if (subject == null) { return false; }

        if (!containedInterfaceSubjects.tryAdd(subject.getModelComponentID(), subject)) return false;

        publishElementAdded(subject);
        subject.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdContains, subject.getUriModelComponentID()));
        return true;
        }



public void setInterfaceSubjects(Set<IInterfaceSubject> subjects, int removeCascadeDepth)
        {
        for (IInterfaceSubject interfaceSubject: this.getInterfaceSubjects().values())
        {
        removeInterfaceSubject(interfaceSubject.getModelComponentID(), removeCascadeDepth);
        }
        if (subjects == null) return;
        for(IInterfaceSubject subject: subjects)
        {
        addInterfaceSubject(subject);
        }
        }
                public void setInterfaceSubjects(Set<IInterfaceSubject> subjects)
                {
                        for (IInterfaceSubject interfaceSubject: this.getInterfaceSubjects().values())
                        {
                                removeInterfaceSubject(interfaceSubject.getModelComponentID(), 0);
                        }
                        if (subjects == null) return;
                        for(IInterfaceSubject subject: subjects)
                        {
                                addInterfaceSubject(subject);
                        }
                }
//TODO: out..
public boolean removeInterfaceSubject(String id, int removeCascadeDepth)
        {
        if (id == null) return false;
        if (!containedInterfaceSubjects.tryGetValue(id, out IInterfaceSubject subject)) return false;

        containedInterfaceSubjects.remove(id);
        subject.unregister(this, removeCascadeDepth);
        removeTriple(new IncompleteTriple(OWLTags.stdContains, subject.getUriModelComponentID()));
        return true;
        }

                public boolean removeInterfaceSubject(String id)
                {
                        if (id == null) return false;
                        if (!containedInterfaceSubjects.tryGetValue(id, out IInterfaceSubject subject)) return false;

                        containedInterfaceSubjects.remove(id);
                        subject.unregister(this, 0);
                        removeTriple(new IncompleteTriple(OWLTags.stdContains, subject.getUriModelComponentID()));
                        return true;
                }


public Map<String, IInterfaceSubject> getInterfaceSubjects()
        {
        return new HashMap<String, IInterfaceSubject>(containedInterfaceSubjects);
        }

@Override
protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {
        if (element instanceof IInterfaceSubject interfaceSubj && predicate.contains(OWLTags.ccontains))
        {
        addInterfaceSubject(interfaceSubj);
        return true;
        }

        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
        }



        }