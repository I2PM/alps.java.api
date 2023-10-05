package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
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
 * Class that represents an action. This is a construct used in the ontology, but is only implemented here to guarantee a correct standard.
 * A user should not create own actions, they will be created automatically when creating a state.
 * They are only used for export.
 * However, when imported, the correct actions should be loaded and parsed correctly.
 * This class ensures that.
 */
public class Action extends BehaviorDescribingComponent implements IAction {
    protected IState state;
    protected ICompatibilityDictionary<String, ITransition> transitions = new CompatibilityDictionary<String, ITransition>();

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "Action";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new Action();
    }

    public Action() {
    }

    /**
     * Constructor that creates a new fully specified instance of the action class
     *
     * @param state
     * @param labelForID
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public Action(IState state, String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super((state != null) ? state.getContainedBy() : null, labelForID, comment, additionalLabel, additionalAttribute);
        setContainsState(state, false);
    }

    /**
     * Constructor that creates a new fully specified instance of the action class
     *
     * @param state
     */

    public Action(IState state) {
        super((state != null) ? state.getContainedBy() : null,
                null, null, null, null);
        setContainsState(state, false);
    }

    /**
     * Constructor that creates a new fully specified instance of the action class
     *
     * @param state
     * @param labelForID
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    ISubjectBehavior behavior = null;

    public Action(IState state, String labelForID) {
        super((state != null) ? state.getContainedBy() : null,
                labelForID, null, null, null);
        setContainsState(state, false);
    }

    /**
     * Sets the corresponding state.
     * Not public (explanation in class xml)
     *
     * @param state  the state
     * @param parsed should express whether this method was called while parsing or not
     */
    protected void setContainsState(IState state, boolean parsed) {
        IState oldState = this.state;
        // Might set it to null
        this.state = state;


        if (oldState != null) {
            if (oldState.equals(state)) return;
            oldState.unregister(this);
            //oldState.replaceGeneratedActionWithParsed(null);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, oldState.getUriModelComponentID()));
        }

        if (!(state == null)) {
            publishElementAdded(state);
            state.register(this);

            // Get all outgoing transitions from the state
            updateContainedTransitions();

            // Only if this action was parsed (and not created automatically),
            // overwrite the (previously automatically created) action of the state
            //if (state.getAction() is null)
            //if (parsed)
            //state.replaceGeneratedActionWithParsed(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, state.getUriModelComponentID()));
        }
    }

    protected void setContainsState(IState state) {
        IState oldState = this.state;
        // Might set it to null
        this.state = state;


        if (oldState != null) {
            if (oldState.equals(state)) return;
            oldState.unregister(this);
            //oldState.replaceGeneratedActionWithParsed(null);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, oldState.getUriModelComponentID()));
        }

        if (!(state == null)) {
            publishElementAdded(state);
            state.register(this);

            // Get all outgoing transitions from the state
            updateContainedTransitions();

            // Only if this action was parsed (and not created automatically),
            // overwrite the (previously automatically created) action of the state
            //if (state.getAction() is null)
            //if (parsed)
            //state.replaceGeneratedActionWithParsed(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, state.getUriModelComponentID()));
        }
    }


    public IState getState() {
        return state;
    }

    /**
     * Sets the corresponding transitions.
     * Not public (explanation in class xml)
     */
    protected void updateContainedTransitions() {
        for (ITransition transition : getContainedTransitions().values()) {
            removeContainedTransition(transition.getModelComponentID());
        }
        if (state == null) return;
        for (ITransition transition : state.getOutgoingTransitions().values()) {
            addContainsTransition(transition);
        }
    }

    /**
     * Adds a corresponding transition.
     * Not public (explanation in class xml)
     *
     * @param containedTransition
     */
    protected void addContainsTransition(ITransition containedTransition) {
        if (containedTransition == null) {
            return;
        }
        if (transitions.tryAdd(containedTransition.getModelComponentID(), containedTransition)) {
            publishElementAdded(containedTransition);
            containedTransition.register(this);
            if (containedTransition.getSourceState() != null && state != null) {
                if (containedTransition.getSourceState().equals(state))
                    state.addOutgoingTransition(containedTransition);
            }
            addTriple(new IncompleteTriple(OWLTags.stdContains, containedTransition.getUriModelComponentID()));
        }
    }

    public Map<String, ITransition> getContainedTransitions() {
        return new HashMap<String, ITransition>(transitions);
    }

    /**
     * Removes a contained transition.
     * Not public (explanation in class xml)
     *
     * @param id the id of the transition
     */
    protected void removeContainedTransition(String id) {
        if (id == null) return;
        ITransition transition = transitions.get(id);
        if (transition != null) {
            transitions.remove(id);
            transition.unregister(this);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, transition.getUriModelComponentID()));
        }
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getState() != null)
            baseElements.add(getState());

        if (specification == ConnectedElementsSetSpecification.TO_ADD || specification == ConnectedElementsSetSpecification.ALL)
            for (ITransition transition : getContainedTransitions().values())
                baseElements.add(transition);
        return baseElements;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.ccontains)) {
                if (element instanceof IState state) {
                    setContainsState(state, true);
                    return true;
                } else if (element instanceof ITransition transition) {
                    addContainsTransition(transition);
                    return true;
                }
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (transitions.containsKey(oldID)) {
            ITransition element = transitions.get(oldID);
            transitions.remove(oldID);
            transitions.put(element.getModelComponentID(), element);
        }

        super.notifyModelComponentIDChanged(oldID, newID);
    }

    @Override
    public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateAdded(update, caller);
        if (update == null) return;

        // Only relevant for parsing:
        // When a registered transition calls a change -> has a new state that might have been null before (not parsed)
        // -> check if the state matches our belonging state and add the transition to the state
        if (caller instanceof ITransition transition && getContainedTransitions().containsKey(transition.getModelComponentID())) {
            IState localState = getState();
            if (update.equals(localState) && localState != null) {
                if (transition.getSourceState() != null && transition.getSourceState().equals(localState))
                    localState.addOutgoingTransition(transition);
            }
        }
        // When a registered state calls a change -> might have a new transition
        // -> re-set our transitions
        if (caller instanceof IState state && getState() != null && getState().equals(state)) {
            if (update instanceof ITransition) {
                updateContainedTransitions();
            }
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update.equals(getState()))
                removeFromEverything();

            // When a registered state calls a change -> might have deleted a transition
            // -> re-set our transitions
            if (caller instanceof IState state && getState() != null && getState().equals(state)) {
                if (update instanceof ITransition) {
                    updateContainedTransitions();
                }
            }
        }

    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update.equals(getState()))
                removeFromEverything();

            // When a registered state calls a change -> might have deleted a transition
            // -> re-set our transitions
            if (caller instanceof IState state && getState() != null && getState().equals(state)) {
                if (update instanceof ITransition) {
                    updateContainedTransitions();
                }
            }
        }

    }
}
