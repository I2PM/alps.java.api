package alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IStateReference;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that represents a subject base behavior class
 * According to standard pass 1.1.0
 */
public class SubjectBaseBehavior extends SubjectBehavior implements ISubjectBaseBehavior {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SubjectBaseBehavior";

    protected Map<String, IState> endStates = new HashMap<String, IState>();

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SubjectBaseBehavior();
    }

    protected SubjectBaseBehavior() {
    }

    public SubjectBaseBehavior(IModelLayer layer, String labelForID, ISubject subject, Set<IBehaviorDescribingComponent> components,
                               Set<IState> endStates, IState initialStateOfBehavior, int priorityNumber, String comment, String additionalLabel,
                               List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, subject, components, initialStateOfBehavior, priorityNumber, comment, additionalLabel, additionalAttribute);
        if (endStates != null)
            for (IState state : endStates) {
                addBehaviorDescribingComponent(state);
                state.setIsStateType(IState.StateType.EndState);
            }
    }

    public SubjectBaseBehavior(IModelLayer layer) {
        super(layer, null, null, null, null, 0, null, null, null);
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            System.out.println("Parsing Attribut: " + predicate);
            if (predicate.contains(OWLTags.hasEndState) && element instanceof IState endState) {
                System.out.println(" - found End State: "+ endState.getModelComponentID());
                addBehaviorDescribingComponent(endState);
                endState.setIsStateType(IState.StateType.EndState);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    public Map<String, IState> getEndStates() {
        Map<String, IState> endStates = getBehaviorDescribingComponents().values().stream()
                .filter(component -> component instanceof IState)
                .map(component -> (IState) component)
                .filter(state -> state.isStateType(IState.StateType.EndState))
                .collect(Collectors.toMap(IState::getModelComponentID, state -> state));

        return new HashMap<>(endStates);
    }


    @Override
    public boolean addBehaviorDescribingComponent(IBehaviorDescribingComponent component) {
        // As described in standard, no state references might be contained
        if (component instanceof IStateReference) return false;
        return super.addBehaviorDescribingComponent(component);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IState endState : getEndStates().values()) baseElements.add(endState);
        return baseElements;
    }

    public void setEndStates(Set<IState> endStates, int removeCascadeDepth) {
        for (IState state : endStates) {
            registerEndState(state);
        }
    }

    public void setEndStates(Set<IState> endStates) {
        for (IState state : endStates) {
            registerEndState(state);
        }
    }

    public void registerEndState(IState state) {
        addBehaviorDescribingComponent(state);
        state.setIsStateType(IState.StateType.EndState);
        addTriple(new IncompleteTriple(OWLTags.stdHasEndState, state.getUriModelComponentID()));
    }

    public void unregisterEndState(String id, int removeCascadeDepth) {
        IBehaviorDescribingComponent component = getBehaviorDescribingComponents().get(id);
        if (component instanceof IState state) {
            state.removeStateType(IState.StateType.EndState);
            removeTriple(new IncompleteTriple(OWLTags.stdHasEndState, getUriModelComponentID()));
        }

    }

    public void unregisterEndState(String id) {
        IBehaviorDescribingComponent component = getBehaviorDescribingComponents().get(id);
        if (component instanceof IState state) {
            state.removeStateType(IState.StateType.EndState);
            removeTriple(new IncompleteTriple(OWLTags.stdHasEndState, getUriModelComponentID()));
        }

    }
}

