package alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.FunctionalityCapsules.IGuardsFunctionalityCapsule;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehavior;
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
 * Class that represents an GuardBehavior
 * According to standard pass 1.1.0
 */

public class GuardBehavior extends SubjectBehavior implements IGuardBehavior {
    protected ICompatibilityDictionary<String, ISubjectBehavior> subjectBehaviors = new CompatibilityDictionary<String, ISubjectBehavior>();
    protected ICompatibilityDictionary<String, IState> guardedStates = new CompatibilityDictionary<String, IState>();
    protected IGuardsFunctionalityCapsule<IState> stateGuardCapsule;
    protected IGuardsFunctionalityCapsule<ISubjectBehavior> behaviorGuardCapsule;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "GuardBehavior";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new GuardBehavior();
    }

    public GuardBehavior() {
    }

    public GuardBehavior(IModelLayer layer, String labelForID, ISubject subject, Set<IBehaviorDescribingComponent> components,
                         Set<ISubjectBehavior> guardedBehaviors, Set<IState> guardedStates, IState initialStateOfBehavior, int priorityNumber, String comment,
                         String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, subject, components, initialStateOfBehavior, priorityNumber, comment, additionalLabel, additionalAttribute);
        setGuardedBehaviors(guardedBehaviors);
        setGuardedStates(guardedStates);
    }

    public GuardBehavior(IModelLayer layer) {
        super(layer, null, null, null, null, 0, null, null, null);
        setGuardedBehaviors(null);
        setGuardedStates(null);
    }

    public void setGuardedBehaviors(Set<ISubjectBehavior> behaviors, int removeCascadeDepth) {
        for (ISubjectBehavior behavior : getGuardedBehaviors().values()) {
            removeGuardedBehavior(behavior.getModelComponentID(), removeCascadeDepth);
        }
        if (behaviors == null) return;
        for (ISubjectBehavior behavior : behaviors) {
            addGuardedBehavior(behavior);
        }
    }

    public void setGuardedBehaviors(Set<ISubjectBehavior> behaviors) {
        for (ISubjectBehavior behavior : getGuardedBehaviors().values()) {
            removeGuardedBehavior(behavior.getModelComponentID(), 0);
        }
        if (behaviors == null) return;
        for (ISubjectBehavior behavior : behaviors) {
            addGuardedBehavior(behavior);
        }
    }

    public void addGuardedBehavior(ISubjectBehavior behavior) {
        if (behavior == null) {
            return;
        }
        if (subjectBehaviors.getOrDefault(behavior.getModelComponentID(), behavior) != null) {
            publishElementAdded(behavior);
            behavior.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdGuardsBehavior, behavior.getUriModelComponentID()));
        }
    }

    public Map<String, ISubjectBehavior> getGuardedBehaviors() {
        return new HashMap<String, ISubjectBehavior>(subjectBehaviors);
    }

    public void removeGuardedBehavior(String id, int removeCascadeDepth) {
        if (id == null) return;
        ISubjectBehavior behavior = subjectBehaviors.get(id);
        if (behavior != null) {
            subjectBehaviors.remove(id);
            behavior.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdGuardsBehavior, behavior.getUriModelComponentID()));
        }
    }

    public void removeGuardedBehavior(String id) {
        if (id == null) return;
        ISubjectBehavior behavior = subjectBehaviors.get(id);
        if (behavior != null) {
            subjectBehaviors.remove(id);
            behavior.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdGuardsBehavior, behavior.getUriModelComponentID()));
        }
    }

    public void setGuardedStates(Set<IState> guardedStates, int removeCascadeDepth) {
        for (IState state : getGuardedStates().values()) {
            removeGuardedState(state.getModelComponentID(), removeCascadeDepth);
        }
        if (guardedStates == null) return;
        for (IState state : guardedStates) {
            addGuardedState(state);
        }
    }

    public void setGuardedStates(Set<IState> guardedStates) {
        for (IState state : getGuardedStates().values()) {
            removeGuardedState(state.getModelComponentID(), 0);
        }
        if (guardedStates == null) return;
        for (IState state : guardedStates) {
            addGuardedState(state);
        }
    }

    public void addGuardedState(IState state) {
        if (state == null) {
            return;
        }
        if (guardedStates.getOrDefault(state.getModelComponentID(), state) != null) {
            publishElementAdded(state);
            state.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdGuardsState, state.getUriModelComponentID()));
        }
    }

    public Map<String, IState> getGuardedStates() {
        return new HashMap<String, IState>(guardedStates);
    }

    public void removeGuardedState(String id, int removeCascadeDepth) {
        if (id == null) return;
        IState state = guardedStates.get(id);
        if (state != null) {
            guardedStates.remove(id);
            state.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdGuardsState, state.getUriModelComponentID()));
        }
    }

    public void removeGuardedState(String id) {
        if (id == null) return;
        IState state = guardedStates.get(id);
        if (state != null) {
            guardedStates.remove(id);
            state.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdGuardsState, state.getUriModelComponentID()));
        }
    }


    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.guardsBehavior) && element instanceof ISubjectBehavior behavior) {
                addGuardedBehavior(behavior);
                return true;
            } else if (predicate.contains(OWLTags.guardsState) && element instanceof IState state) {
                addGuardedState(state);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (ISubjectBehavior behavior : getGuardedBehaviors().values()) baseElements.add(behavior);
        for (IState state : getGuardedStates().values()) baseElements.add(state);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IState state)
                // Try to remove the state
                removeGuardedState(state.getModelComponentID(), removeCascadeDepth);
            else if (update instanceof ISubjectBehavior behavior)
                // Try to remove the behavior
                removeGuardedBehavior(behavior.getModelComponentID(), removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IState state)
                // Try to remove the state
                removeGuardedState(state.getModelComponentID(), 0);
            else if (update instanceof ISubjectBehavior behavior)
                // Try to remove the behavior
                removeGuardedBehavior(behavior.getModelComponentID(), 0);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (subjectBehaviors.containsKey(oldID)) {
            ISubjectBehavior element = subjectBehaviors.get(oldID);
            subjectBehaviors.remove(oldID);
            subjectBehaviors.put(element.getModelComponentID(), element);
        }
        if (guardedStates.containsKey(oldID)) {
            IState element = guardedStates.get(oldID);
            guardedStates.remove(oldID);
            guardedStates.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

}
