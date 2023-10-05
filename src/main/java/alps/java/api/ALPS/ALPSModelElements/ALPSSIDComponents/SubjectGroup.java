package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.IFullySpecifiedSubject;
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
public class SubjectGroup extends Subject implements ISubjectGroup {


        private final ICompatibilityDictionary<String, ISubject> containedSubjects
            = new CompatibilityDictionary<String, ISubject>();

    /**
     * Name of the class, needed for parsing
     */
        private final String CLASS_NAME = "SubjectGroup";
    @Override
        public String getClassName()
        {
            return CLASS_NAME;
        }
        @Override
        public IParseablePASSProcessModelElement getParsedInstance()
        {
            return new SubjectGroup();
        }

    public SubjectGroup() { }

    /**
     *
     * @param layer The layer this subject should be placed onto
     * @param labelForId
     * @param incomingMessageExchange
     * @param containedSubjects
     * @param outgoingMessageExchange
     * @param maxSubjectInstanceRestriction
     * @param referencedSubject If the InterfaceSubject is referencing another FullySpecifiedSubject, this can be passed here
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
        public SubjectGroup(IModelLayer layer, String labelForId, Set<IMessageExchange> incomingMessageExchange,
                            Set<ISubject> containedSubjects, Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction,
                            IFullySpecifiedSubject referencedSubject, String comment, String additionalLabel,
                            List<IIncompleteTriple> additionalAttribute){
            super(layer, labelForId, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction,
            comment, additionalLabel, additionalAttribute);
            setSubjects(containedSubjects);
        }

    /**
     *
     * @param layer The layer this subject should be placed onto
     */
    public SubjectGroup(IModelLayer layer){
        super(layer, null, null, null, 1,
                null, null, null);
        setSubjects(null);
    }

        public boolean addSubject(ISubject subject)
        {
            if (subject == null) { return false; }

            if (!containedSubjects.tryAdd(subject.getModelComponentID(), subject)) return false;

            publishElementAdded(subject);
            subject.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, subject.getUriModelComponentID()));
            return true;
        }



        public void setSubjects(Set<ISubject> subjects, int removeCascadeDepth)
        {
            for(ISubject mySubject: this.getContainedSubjects().values())
            {
                removeSubject(mySubject.getModelComponentID(), removeCascadeDepth);
            }
            if (subjects == null) return;
            for(ISubject subject: subjects)
            {
                addSubject(subject);
            }
        }
    public void setSubjects(Set<ISubject> subjects)
    {
        for(ISubject mySubject: this.getContainedSubjects().values())
        {
            removeSubject(mySubject.getModelComponentID(), 0);
        }
        if (subjects == null) return;
        for(ISubject subject: subjects)
        {
            addSubject(subject);
        }
    }
        public boolean removeSubject(String id, int removeCascadeDepth)
        {
            if (id == null) return false;
            ISubject subject = containedSubjects.get(id);
            if(subject == null) return false;

            containedSubjects.remove(id);
            subject.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, subject.getUriModelComponentID()));
            return true;
        }

    public boolean removeSubject(String id)
    {
        if (id == null) return false;
        ISubject subject = containedSubjects.get(id);
        if(subject == null) return false;

        containedSubjects.remove(id);
        subject.unregister(this,0);
        removeTriple(new IncompleteTriple(OWLTags.stdContains, subject.getUriModelComponentID()));
        return true;
    }
        public Map<String, ISubject> getContainedSubjects()
        {
            return new HashMap<String, ISubject>(containedSubjects);
        }

@Override
        protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {

            //Console.WriteLine("parsging in an Subject Group: " + this.getModelComponentID() );
            if (element instanceof ISubject subj && predicate.contains(OWLTags.ccontains))
            {
                addSubject(subj);
                return true;
            }

            return super.parseAttribute(predicate, objectContent, lang, dataType, element);
        }



    }