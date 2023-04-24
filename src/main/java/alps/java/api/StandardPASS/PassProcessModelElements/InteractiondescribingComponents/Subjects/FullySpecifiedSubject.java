package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinitions.ISubjectDataDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.ISubjectBaseBehavior;
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
 * Class that represents an FullySpecifiedSubject
 */
public class FullySpecifiedSubject extends Subject implements IFullySpecifiedSubject {
    protected ISubjectBehavior subjectBaseBehavior;
    protected ICompatibilityDictionary<String, ISubjectBehavior> subjectBehaviors = new CompatibilityDictionary<String, ISubjectBehavior>();
    protected ISubjectDataDefinition subjectDataDefinition;
    protected ICompatibilityDictionary<String, IInputPoolConstraint> inputPoolConstraints = new CompatibilityDictionary<String, IInputPoolConstraint>();
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "FullySpecifiedSubject";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new FullySpecifiedSubject();
    }

    protected FullySpecifiedSubject() {
    }

    public FullySpecifiedSubject(IModelLayer layer, String labelForID, Set<IMessageExchange> incomingMessageExchange,
                                 ISubjectBaseBehavior subjectBaseBehavior, Set<ISubjectBehavior> subjectBehaviors,
                                 Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction, ISubjectDataDefinition subjectDataDefinition,
                                 Set<IInputPoolConstraint> inputPoolConstraints, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction, comment, additionalLabel, additionalAttribute);
        setDataDefintion(subjectDataDefinition);
        setInputPoolConstraints(inputPoolConstraints);
        if (subjectBehaviors != null)
            setBehaviors(subjectBehaviors);

        if (subjectBaseBehavior == null) {
            String label = (this.getModelComponentLabelsAsStrings().size() > 0) ? this.getModelComponentLabelsAsStrings().get(0) : getModelComponentID();
            setBaseBehavior(new SubjectBehavior(layer, "defaultBehavior", this, null, null, 0,
                    "This is the subject behavior to the fully specified subject: " + label, null, null));
        } else
            setBaseBehavior(subjectBaseBehavior);
    }

    public FullySpecifiedSubject(IModelLayer layer) {
        super(layer, null, null, null, 1, null, null, null);
        setDataDefintion(null);
        setInputPoolConstraints(null);

        String label = (this.getModelComponentLabelsAsStrings().size() > 0) ? this.getModelComponentLabelsAsStrings().get(0) : getModelComponentID();
        setBaseBehavior(new SubjectBehavior(layer, "defaultBehavior", this, null, null, 0,
                "This is the subject behavior to the fully specified subject: " + label, null, null));
    }

    public void setBaseBehavior(ISubjectBehavior subjectBaseBehavior, int removeCascadeDepth) {
        ISubjectBehavior oldBehavior = this.subjectBaseBehavior;
        // Might set it to null
        this.subjectBaseBehavior = subjectBaseBehavior;

        if (oldBehavior != null) {
            if (oldBehavior.equals(subjectBaseBehavior)) return;
            // We do only remove the triple for the old behavior, as it is still listed as normal behavior (just not as baseBehavior)
            removeTriple(new IncompleteTriple(OWLTags.stdContainsBaseBehavior, oldBehavior.getUriModelComponentID()));
        }

        if (!(subjectBaseBehavior == null)) {
            // NOT registering and publishing because we call addBehavior (happens there)
            addBehavior(subjectBaseBehavior);
            addTriple(new IncompleteTriple(OWLTags.stdContainsBaseBehavior, subjectBaseBehavior.getUriModelComponentID()));
        }
    }

    public void setBaseBehavior(ISubjectBehavior subjectBaseBehavior) {
        ISubjectBehavior oldBehavior = this.subjectBaseBehavior;
        // Might set it to null
        this.subjectBaseBehavior = subjectBaseBehavior;

        if (oldBehavior != null) {
            if (oldBehavior.equals(subjectBaseBehavior)) return;
            // We do only remove the triple for the old behavior, as it is still listed as normal behavior (just not as baseBehavior)
            removeTriple(new IncompleteTriple(OWLTags.stdContainsBaseBehavior, oldBehavior.getUriModelComponentID()));
        }

        if (!(subjectBaseBehavior == null)) {
            // NOT registering and publishing because we call addBehavior (happens there)
            addBehavior(subjectBaseBehavior);
            addTriple(new IncompleteTriple(OWLTags.stdContainsBaseBehavior, subjectBaseBehavior.getUriModelComponentID()));
        }
    }


    public boolean addBehavior(ISubjectBehavior behavior) {
        if (behavior == null) {
            return false;
        }
        if (subjectBehaviors.getOrDefault(behavior.getModelComponentID(), behavior) != null) {
            publishElementAdded(behavior);
            behavior.register(this);
            behavior.setSubject(this);
            addTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
            return true;
        }
        return false;
    }


    public void setBehaviors(Set<ISubjectBehavior> behaviors, int removeCascadeDepth) {
        for (ISubjectBehavior behavior : this.getBehaviors().values()) {
            removeBehavior(behavior.getModelComponentID(), removeCascadeDepth);
        }
        if (behaviors == null) return;
        for (ISubjectBehavior behavior : behaviors) {
            addBehavior(behavior);
        }
    }

    public void setBehaviors(Set<ISubjectBehavior> behaviors) {
        for (ISubjectBehavior behavior : this.getBehaviors().values()) {
            removeBehavior(behavior.getModelComponentID(), 0);
        }
        if (behaviors == null) return;
        for (ISubjectBehavior behavior : behaviors) {
            addBehavior(behavior);
        }
    }

    public boolean removeBehavior(String id, int removeCascadeDepth) {
        if (id == null) return false;
        ISubjectBehavior behavior = subjectBehaviors.get(id);
        if (behavior != null) {
            if (behavior.equals(getSubjectBaseBehavior()))
                setBaseBehavior(null, removeCascadeDepth);
            subjectBehaviors.remove(id);
            behavior.unregister(this, removeCascadeDepth);
            behavior.setSubject(null);
            removeTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
            return true;
        }
        return false;
    }

    public boolean removeBehavior(String id) {
        if (id == null) return false;
        ISubjectBehavior behavior = subjectBehaviors.get(id);
        if (behavior != null) {
            if (behavior.equals(getSubjectBaseBehavior()))
                setBaseBehavior(null, 0);
            subjectBehaviors.remove(id);
            behavior.unregister(this, 0);
            behavior.setSubject(null);
            removeTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
            return true;
        }
        return false;
    }

    public Map<String, ISubjectBehavior> getBehaviors() {
        return new HashMap<String, ISubjectBehavior>(subjectBehaviors);
    }


    public void setDataDefintion(ISubjectDataDefinition subjectDataDefinition, int removeCascadeDepth) {
        ISubjectDataDefinition oldDef = subjectDataDefinition;
        // Might set it to null
        this.subjectDataDefinition = subjectDataDefinition;

        if (oldDef != null) {
            if (oldDef.equals(subjectDataDefinition)) return;
            oldDef.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataDefintion, oldDef.getUriModelComponentID()));
        }


        if (!(subjectDataDefinition == null)) {
            publishElementAdded(subjectDataDefinition);
            subjectDataDefinition.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataDefintion, subjectDataDefinition.getUriModelComponentID()));
        }
    }

    public void setDataDefintion(ISubjectDataDefinition subjectDataDefinition) {
        ISubjectDataDefinition oldDef = subjectDataDefinition;
        // Might set it to null
        this.subjectDataDefinition = subjectDataDefinition;

        if (oldDef != null) {
            if (oldDef.equals(subjectDataDefinition)) return;
            oldDef.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasDataDefintion, oldDef.getUriModelComponentID()));
        }


        if (!(subjectDataDefinition == null)) {
            publishElementAdded(subjectDataDefinition);
            subjectDataDefinition.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasDataDefintion, subjectDataDefinition.getUriModelComponentID()));
        }
    }


    public boolean addInputPoolConstraint(IInputPoolConstraint constraint) {
        if (constraint == null) {
            return false;
        }
        if (inputPoolConstraints.getOrDefault(constraint.getModelComponentID(), constraint) != null) {
            publishElementAdded(constraint);
            constraint.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasInputPoolConstraint, constraint.getUriModelComponentID()));
            return true;
        }
        return false;
    }


    public void setInputPoolConstraints(Set<IInputPoolConstraint> constraints, int removeCascadeDepth) {
        for (IInputPoolConstraint constraint : this.getInputPoolConstraints().values()) {
            removeInputPoolConstraint(constraint.getModelComponentID(), removeCascadeDepth);
        }
        if (constraints == null) return;
        for (IInputPoolConstraint constraint : constraints) {
            addInputPoolConstraint(constraint);
        }
    }

    public void setInputPoolConstraints(Set<IInputPoolConstraint> constraints) {
        for (IInputPoolConstraint constraint : this.getInputPoolConstraints().values()) {
            removeInputPoolConstraint(constraint.getModelComponentID(), 0);
        }
        if (constraints == null) return;
        for (IInputPoolConstraint constraint : constraints) {
            addInputPoolConstraint(constraint);
        }
    }

    public boolean removeInputPoolConstraint(String id, int removeCascadeDepth) {
        if (id == null) return false;
        IInputPoolConstraint constraint = inputPoolConstraints.get(id);
        if (constraint != null) {
            inputPoolConstraints.remove(id);
            constraint.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasInputPoolConstraint, constraint.getUriModelComponentID()));
            return true;
        }
        return false;
    }

    public boolean removeInputPoolConstraint(String id) {
        if (id == null) return false;
        IInputPoolConstraint constraint = inputPoolConstraints.get(id);
        if (constraint != null) {
            inputPoolConstraints.remove(id);
            constraint.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasInputPoolConstraint, constraint.getUriModelComponentID()));
            return true;
        }
        return false;
    }

    public Map<String, IInputPoolConstraint> getInputPoolConstraints() {
        return new HashMap<String, IInputPoolConstraint>(inputPoolConstraints);
    }


    public ISubjectBehavior getSubjectBaseBehavior() {
        return subjectBaseBehavior;
    }


    public ISubjectDataDefinition getSubjectDataDefinition() {
        return subjectDataDefinition;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (element instanceof ISubjectBehavior subjectBehavior) {
                if (predicate.contains(OWLTags.containsBaseBehavior)) {
                    setBaseBehavior(subjectBehavior);
                    return true;
                } else if (predicate.contains(OWLTags.containsBehavior)) {
                    addBehavior(subjectBehavior);
                    return true;
                }
            } else if (predicate.contains(OWLTags.hasDataDefintion) && element instanceof ISubjectDataDefinition dataDefinition) {
                setDataDefintion(dataDefinition);
                return true;
            } else if (predicate.contains(OWLTags.hasInputPoolConstraint) && element instanceof IInputPoolConstraint poolConstraint) {
                addInputPoolConstraint(poolConstraint);
                return true;
            }

        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getSubjectBaseBehavior() != null)
            baseElements.add(getSubjectBaseBehavior());

        for (ISubjectBehavior behavior : getBehaviors().values())
            baseElements.add(behavior);

        if (getSubjectDataDefinition() != null)
            baseElements.add(getSubjectDataDefinition());

        for (IInputPoolConstraint constraint : getInputPoolConstraints().values())
            baseElements.add(constraint);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof ISubjectBehavior behavior) {
                if (behavior.equals(getSubjectBaseBehavior()))
                    setBaseBehavior(null, removeCascadeDepth);
                else removeBehavior(behavior.getModelComponentID(), removeCascadeDepth);
            }
            if (update instanceof ISubjectDataDefinition def && def.equals(getSubjectDataDefinition()))
                setDataDefintion(null, removeCascadeDepth);
            if (update instanceof IInputPoolConstraint constraint)
                removeInputPoolConstraint(constraint.getModelComponentID(), removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof ISubjectBehavior behavior) {
                if (behavior.equals(getSubjectBaseBehavior()))
                    setBaseBehavior(null, 0);
                else removeBehavior(behavior.getModelComponentID(), 0);
            }
            if (update instanceof ISubjectDataDefinition def && def.equals(getSubjectDataDefinition()))
                setDataDefintion(null, 0);
            if (update instanceof IInputPoolConstraint constraint)
                removeInputPoolConstraint(constraint.getModelComponentID(), 0);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (subjectBehaviors.containsKey(oldID)) {
            ISubjectBehavior element = subjectBehaviors.get(oldID);
            subjectBehaviors.remove(oldID);
            subjectBehaviors.put(element.getModelComponentID(), element);
        }
        if (inputPoolConstraints.containsKey(oldID)) {
            IInputPoolConstraint element = inputPoolConstraints.get(oldID);
            inputPoolConstraints.remove(oldID);
            inputPoolConstraints.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

}


