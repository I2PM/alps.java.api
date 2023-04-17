package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;
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
 * Class that represents an macro state
 */
public class MacroState extends State implements IMacroState {
    protected IMacroBehavior referenceMacroBehavior;
    protected final ICompatibilityDictionary<String, IStateReference> stateReferences = new CompatibilityDictionary<String, IStateReference>();
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "Macrostate";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MacroState();
    }

    protected MacroState() {
    }

    public MacroState(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                      IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                      Set<IStateReference> stateReferences, IMacroBehavior macroBehavior, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
        setReferencedMacroBehavior(macroBehavior);
        setStateReferences(stateReferences);
    }

    public MacroState(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);
        setReferencedMacroBehavior(null);
        setStateReferences(null);
    }

    public void setReferencedMacroBehavior(IMacroBehavior macroBehavior, int removeCascadeDepth) {
        IMacroBehavior oldBehavior = this.referenceMacroBehavior;
        // Might set it to null
        this.referenceMacroBehavior = macroBehavior;

        if (oldBehavior != null) {
            if (oldBehavior.equals(macroBehavior)) return;
            oldBehavior.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdReferencesMacroBehavior, oldBehavior.getUriModelComponentID()));
        }

        if (!(macroBehavior == null)) {
            publishElementAdded(macroBehavior);
            macroBehavior.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferencesMacroBehavior, macroBehavior.getUriModelComponentID()));
        }
    }

    public void setReferencedMacroBehavior(IMacroBehavior macroBehavior) {
        IMacroBehavior oldBehavior = this.referenceMacroBehavior;
        // Might set it to null
        this.referenceMacroBehavior = macroBehavior;

        if (oldBehavior != null) {
            if (oldBehavior.equals(macroBehavior)) return;
            oldBehavior.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdReferencesMacroBehavior, oldBehavior.getUriModelComponentID()));
        }

        if (!(macroBehavior == null)) {
            publishElementAdded(macroBehavior);
            macroBehavior.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferencesMacroBehavior, macroBehavior.getUriModelComponentID()));
        }
    }

    public IMacroBehavior getReferencedMacroBehavior() {
        return referenceMacroBehavior;
    }


    public void addStateReference(IStateReference stateReference) {
        if (stateReference == null) {
            return;
        }
        if (stateReferences.tryAdd(stateReference.getModelComponentID(), stateReference)) {
            publishElementAdded(stateReference);
            stateReference.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, stateReference.getUriModelComponentID()));
        }
    }

    //TODO: out-Parameter
    public void removeStateReference(String stateRefID, int removeCascadeDepth) {
        if (stateRefID == null) return;
        if (stateReferences.getOrDefault(stateRefID, out IStateReference reference)) {
            stateReferences.remove(stateRefID);
            reference.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, reference.getUriModelComponentID()));
        }
    }

    //TODO: out-Parameter
    public void removeStateReference(String stateRefID) {
        if (stateRefID == null) return;
        if (stateReferences.getOrDefault(stateRefID, out IStateReference reference)) {
            stateReferences.remove(stateRefID);
            reference.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, reference.getUriModelComponentID()));
        }
    }

    public void setStateReferences(Set<IStateReference> references, int removeCascadeDepth) {
        for (IStateReference reference : getStateReferences().values()) {
            removeStateReference(reference.getModelComponentID(), removeCascadeDepth);
        }
        if (references == null) return;
        for (IStateReference reference : references) {
            addStateReference(reference);
        }
    }

    public void setStateReferences(Set<IStateReference> references) {
        for (IStateReference reference : getStateReferences().values()) {
            removeStateReference(reference.getModelComponentID(), 0);
        }
        if (references == null) return;
        for (IStateReference reference : references) {
            addStateReference(reference);
        }
    }

    public Map<String, IStateReference> getStateReferences() {
        return new HashMap<String, IStateReference>(stateReferences);
    }


    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.referencesMacroBehavior) && element instanceof IMacroBehavior behavior) {
                setReferencedMacroBehavior(behavior);
                return true;
            }
            if (predicate.contains(OWLTags.ccontains) && element instanceof IStateReference reference) {
                addStateReference(reference);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IStateReference component : getStateReferences().values())
            baseElements.add(component);
        if (getReferencedMacroBehavior() != null)
            baseElements.add(getReferencedMacroBehavior());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IStateReference reference)
                removeStateReference(reference.getModelComponentID(), removeCascadeDepth);
            if (update instanceof IMacroBehavior behavior && behavior.equals(getReferencedMacroBehavior()))
                setReferencedMacroBehavior(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IStateReference reference)
                removeStateReference(reference.getModelComponentID(), 0);
            if (update instanceof IMacroBehavior behavior && behavior.equals(getReferencedMacroBehavior()))
                setReferencedMacroBehavior(null, 0);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (stateReferences.containsKey(oldID)) {
            IStateReference element = stateReferences.get(oldID);
            stateReferences.remove(oldID);
            stateReferences.put(element.getModelComponentID(), element);
        }

        super.notifyModelComponentIDChanged(oldID, newID);
    }
}
