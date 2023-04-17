package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
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
 * Class that represents a Choice Segment Path
 */
public class ChoiceSegmentPath extends State implements IChoiceSegmentPath {
    protected IState endState;
    protected IState initialState;
    protected boolean isOptionalToStart = false;
    protected boolean isOptionalToEnd = false;
    protected final ICompatibilityDictionary<String, IState> containedStates = new CompatibilityDictionary<String, IState>();
    protected IChoiceSegment segment;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ChoiceSegmentPath";

    /**
     * Constructor that creates a new empty instance of the Choice Segment Path class
     *
     * @param behavior
     */
    public ChoiceSegmentPath(ISubjectBehavior behavior) {
        super(behavior);
    }


    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ChoiceSegmentPath();
    }

    protected ChoiceSegmentPath() {
    }

    public ChoiceSegmentPath(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                             IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                             Set<IState> containedStates, IState endState,
                             IInitialStateOfChoiceSegmentPath initialStateOfChoiceSegmentPath, boolean isOptionalToEndChoiceSegmentPath,
                             boolean isOptionalToStartChoiceSegmentPath, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
        setEndState(endState);
        setInitialState(initialStateOfChoiceSegmentPath);
        setContainedStates(containedStates);
        setIsOptionalToEndChoiceSegmentPath(isOptionalToEndChoiceSegmentPath);
        setIsOptionalToStartChoiceSegmentPath(isOptionalToStartChoiceSegmentPath);
    }

//TODO: out-Parameter
    public ChoiceSegmentPath(IChoiceSegment choiceSegment, String labelForID, IGuardBehavior guardBehavior,
                             IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                             Set<IState> containedStates, IState endState,
                             IInitialStateOfChoiceSegmentPath initialStateOfChoiceSegmentPath, boolean isOptionalToEndChoiceSegmentPath,
                             boolean isOptionalToStartChoiceSegmentPath, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(choiceSegment == null ? null : choiceSegment.getContainedBy(out ISubjectBehavior behavior) ? behavior : null, labelForID, guardBehavior,
                functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
        setEndState(endState);
        setInitialState(initialStateOfChoiceSegmentPath);
        setContainedStates(containedStates);
        setIsOptionalToEndChoiceSegmentPath(isOptionalToEndChoiceSegmentPath);
        setIsOptionalToStartChoiceSegmentPath(isOptionalToStartChoiceSegmentPath);
        setContainedBy(choiceSegment);
    }

    public ChoiceSegmentPath(IChoiceSegment choiceSegment) {
        super(choiceSegment == null ? null : choiceSegment.getContainedBy(out ISubjectBehavior behavior) ? behavior : null);
        setEndState(null);
        setInitialState(null);
        setContainedStates(null);
        setIsOptionalToEndChoiceSegmentPath(false);
        setIsOptionalToStartChoiceSegmentPath(false);
        setContainedBy(choiceSegment);
    }

    public void setContainedStates(Set<IState> containedStates, int removeCascadeDepth) {
        for (IState state : getContainedStates().values()) {
            removeContainedState(state.getModelComponentID(), removeCascadeDepth);
        }
        if (containedStates == null) return;
        for (IState state : containedStates) {
            addContainedState(state);
        }
    }

    public void setContainedStates(Set<IState> containedStates) {
        for (IState state : getContainedStates().values()) {
            removeContainedState(state.getModelComponentID());
        }
        if (containedStates == null) return;
        for (IState state : containedStates) {
            addContainedState(state);
        }
    }

    public void addContainedState(IState containedState) {
        if (containedState == null) {
            return;
        }
        if (containedStates.tryAdd(containedState.getModelComponentID(), containedState)) {
            publishElementAdded(containedState);
            containedState.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, containedState.getUriModelComponentID()));
        }
    }

    public Map<String, IState> getContainedStates() {
        return new HashMap<String, IState>(containedStates);
    }

    //TODO: out-Parameter
    public void removeContainedState(String id, int removeCascadeDepth) {
        if (id == null) return;
        if (containedStates.getOrDefault(id, out IState state)) {
            containedStates.remove(id);
            state.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, state.getUriModelComponentID()));
            if (state.equals(getInitialState())) setInitialState(null, removeCascadeDepth);
            if (state.equals(getEndState())) setEndState(null, removeCascadeDepth);
        }
    }
    //TODO: out-Parameter
    public void removeContainedState(String id) {
        if (id == null) return;
        if (containedStates.getOrDefault(id, out IState state)) {
            containedStates.remove(id);
            state.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, state.getUriModelComponentID()));
            if (state.equals(getInitialState())) setInitialState(null, 0);
            if (state.equals(getEndState())) setEndState(null, 0);
        }
    }

    public void setEndState(IState state, int removeCascadeDepth) {
        IState oldState = this.endState;
        // Might set it to null
        endState = state;

        if (oldState != null) {
            if (oldState.equals(state)) return;
            removeTriple(new IncompleteTriple(OWLTags.stdHasEndState, oldState.getUriModelComponentID()));
            removeContainedState(oldState.getModelComponentID(), removeCascadeDepth);
        }

        if (!(state == null)) {
            addTriple(new IncompleteTriple(OWLTags.stdHasEndState, state.getUriModelComponentID()));
            addContainedState(state);
        }
    }

    public void setEndState(IState state) {
        IState oldState = this.endState;
        // Might set it to null
        endState = state;

        if (oldState != null) {
            if (oldState.equals(state)) return;
            removeTriple(new IncompleteTriple(OWLTags.stdHasEndState, oldState.getUriModelComponentID()));
            removeContainedState(oldState.getModelComponentID(), 0);
        }

        if (!(state == null)) {
            addTriple(new IncompleteTriple(OWLTags.stdHasEndState, state.getUriModelComponentID()));
            addContainedState(state);
        }
    }


    public IState getEndState() {
        return endState;
    }


    public void setInitialState(IState state, int removeCascadeDepth) {
        IState oldState = this.initialState;
        // Might set it to null
        initialState = state;

        if (oldState != null) {
            if (oldState.equals(state)) return;
            removeTriple(new IncompleteTriple(OWLTags.stdHasInitialState, oldState.getUriModelComponentID()));
            removeContainedState(oldState.getModelComponentID(), removeCascadeDepth);
        }

        if (!(state == null)) {
            addTriple(new IncompleteTriple(OWLTags.stdHasInitialState, state.getUriModelComponentID()));
            addContainedState(state);
        }
    }

    public void setInitialState(IState state) {
        IState oldState = this.initialState;
        // Might set it to null
        initialState = state;

        if (oldState != null) {
            if (oldState.equals(state)) return;
            removeTriple(new IncompleteTriple(OWLTags.stdHasInitialState, oldState.getUriModelComponentID()));
            removeContainedState(oldState.getModelComponentID(), 0);
        }

        if (!(state == null)) {
            addTriple(new IncompleteTriple(OWLTags.stdHasInitialState, state.getUriModelComponentID()));
            addContainedState(state);
        }
    }


    public IState getInitialState() {
        return initialState;
    }


    public void setIsOptionalToEndChoiceSegmentPath(boolean endChoice) {
        isOptionalToEnd = endChoice;
    }


    public boolean getIsOptionalToEndChoiceSegmentPath() {
        return isOptionalToEnd;
    }


    public void setIsOptionalToStartChoiceSegmentPath(boolean startChoice) {
        isOptionalToStart = startChoice;
    }


    public boolean getIsOptionalToStartChoiceSegmentPath() {
        return isOptionalToStart;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (element instanceof IState state) {

                if (predicate.contains(OWLTags.hasEndState)) {
                    setEndState(state);
                    return true;
                } else if (predicate.contains(OWLTags.hasInitialState)) {
                    setInitialState(state);
                    return true;
                } else if (predicate.contains(OWLTags.ccontains)) {
                    addContainedState(state);
                    return true;
                }

            } else if (predicate.contains(OWLTags.belongsTo) && element instanceof IChoiceSegment segment) {
                setContainedBy(segment);
                return true;
            }
        }
        if (predicate.contains(OWLTags.isOptionalToEndChoiceSegmentPath)) {
            boolean isOptional = (objectContent.toLowerCase().contains("true")) ? true : false;
            setIsOptionalToEndChoiceSegmentPath(isOptional);
        }
        if (predicate.contains(OWLTags.isOptionalToStartChoiceSegmentPath)) {
            boolean isOptional = (objectContent.toLowerCase().contains("true")) ? true : false;
            setIsOptionalToEndChoiceSegmentPath(isOptional);
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }
//TODO: out-Parameter
    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IState component : getContainedStates().values())
            baseElements.add(component);
        if (getContainedBy(out IChoiceSegment segment))
            baseElements.add(segment);
        if (getEndState() != null)
            baseElements.add(getEndState());
        if (getInitialState() != null)
            baseElements.add(getInitialState());
        return baseElements;
    }
//TODO: out-Parameter
    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IState state) {
                if (state.equals(getEndState()))
                    setEndState(null, removeCascadeDepth);
                if (state.equals(getInitialState()))
                    setInitialState(null, removeCascadeDepth);
                else removeContainedState(state.getModelComponentID(), removeCascadeDepth);
            }
            getContainedBy(out IChoiceSegment localSegment);
            if (update instanceof IChoiceSegment segment && segment.equals(localSegment))
                setContainedBy(null);
        }
    }
//TODO: out-Parameter
    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IState state) {
                if (state.equals(getEndState()))
                    setEndState(null, 0);
                if (state.equals(getInitialState()))
                    setInitialState(null, 0);
                else removeContainedState(state.getModelComponentID(), 0);
            }
            getContainedBy(out IChoiceSegment localSegment);
            if (update instanceof IChoiceSegment segment && segment.equals(localSegment))
                setContainedBy(null);
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (containedStates.containsKey(oldID)) {
            IState element = containedStates.get(oldID);
            containedStates.remove(oldID);
            containedStates.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

    public void setContainedBy(IChoiceSegment container) {
        IChoiceSegment oldSegment = this.segment;
        // Might set it to null
        this.segment = container;

        if (oldSegment != null) {
            if (oldSegment.equals(container)) return;
            oldSegment.unregister(this);
            removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldSegment.getUriModelComponentID()));
        }

        if (!(container == null)) {
            publishElementAdded(container);
            container.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, container.getUriModelComponentID()));
        }
    }


    public boolean getContainedBy(IChoiceSegment choiceSegment) {
        choiceSegment = segment;
        return segment != null;
    }

    public void removeFromContainer() {
        if (segment != null)
            segment.removeChoiceSegmentPath(getModelComponentID());
        segment = null;
    }
}

