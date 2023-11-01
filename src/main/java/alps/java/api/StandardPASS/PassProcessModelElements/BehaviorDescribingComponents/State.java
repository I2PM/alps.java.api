package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IMacroState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IStateReference;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.ISubjectBaseBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Class that represents a state
 */

public class State extends BehaviorDescribingComponent implements IStateReference, IMacroState {
    protected final ICompatibilityDictionary<String, ITransition> incomingTransitions = new CompatibilityDictionary<String, ITransition>();
    protected final ICompatibilityDictionary<String, ITransition> outgoingTransitions = new CompatibilityDictionary<String, ITransition>();
    protected final IImplementsFunctionalityCapsule<IState> implCapsule;
    protected final Set<IState.StateType> stateTypes = new HashSet<IState.StateType>();
    protected IFunctionSpecification functionSpecification;
    protected IGuardBehavior guardBehavior;
    protected IAction action;
    protected IState referenceState;
    protected IMacroBehavior referenceMacroBehavior;
    protected final ICompatibilityDictionary<String, IStateReference> stateReferences = new CompatibilityDictionary<String, IStateReference>();

    private double has2DPageRatio=-1;
    private double hasRelative2D_Height=-1;
    private double hasRelative2D_Width=-1;
    private double hasRelative2D_PosX=-1;
    private double hasRelative2D_PosY=-1;
    Logger logger = Logger.getLogger(State.class.getName());

    public double get2DPageRatio() {
        return has2DPageRatio;
    }

    public void set2DPageRatio(double has2DPageRatio) {
        if (has2DPageRatio > 0) {
            this.has2DPageRatio = has2DPageRatio;
        }
        if (has2DPageRatio == 0) {
            this.has2DPageRatio = 1;
            logger.warning("found 2D page ratio of 0. This is impossible. changed it to 1");
        } else {
            this.has2DPageRatio = Math.abs(has2DPageRatio);
            logger.warning("found negative 2d page ratio. Changed it to positive value");
        }
    }

    public double getRelative2DHeight() {
        return hasRelative2D_Height;
    }

    public void setRelative2DHeight(double relative2DHeight) {
        if (relative2DHeight >= 0 && relative2DHeight <= 1) {
            hasRelative2D_Height = relative2DHeight;
        } else {
            if (relative2DHeight < 0) {
                hasRelative2D_Height = 0;
                logger.warning("Value for relative2DHeight is smaller than 0. Setting it to 0.");
            } else if (relative2DHeight > 1) {
                hasRelative2D_Height = 1;
                logger.warning("Value for relative2DHeight is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DWidth() {
        return hasRelative2D_Width;
    }

    public void setRelative2DWidth(double relative2DWidth) {

        if (relative2DWidth >= 0 && relative2DWidth <= 1) {
            hasRelative2D_Width = relative2DWidth;
        } else {
            if (relative2DWidth < 0) {
                hasRelative2D_Width = 0;
                logger.warning("Value for relative2DWidth is smaller than 0. Setting it to 0.");
            } else if (relative2DWidth > 1) {
                hasRelative2D_Width = 1;
                logger.warning("Value for relative2DWidth is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DPosX() {
        return hasRelative2D_PosX;
    }

    public void setRelative2DPosX(double relative2DPosX) {
        if (relative2DPosX >= 0 && relative2DPosX <= 1) {
            hasRelative2D_PosX = relative2DPosX;
        } else {
            if (relative2DPosX < 0) {
                hasRelative2D_PosX = 0;
                logger.warning("Value for relative2DPosX is smaller than 0. Setting it to 0.");
            } else if (relative2DPosX > 1) {
                hasRelative2D_PosX = 1;
                logger.warning("Value for relative2DPosX is larger than 1. Setting it to 1.");
            }
        }
    }

    public double getRelative2DPosY() {
        return hasRelative2D_PosY;
    }

    public void setRelative2DPosY(double relative2DPosY) {
        if (relative2DPosY >= 0 && relative2DPosY <= 1) {
            hasRelative2D_PosY = relative2DPosY;
        } else {
            if (relative2DPosY < 0) {
                hasRelative2D_PosY = 0;
                logger.warning("Value for relative2DPosY is smaller than 0. Setting it to 0.");
            } else if (relative2DPosY > 1) {
                hasRelative2D_PosY = 1;
                logger.warning("Value for relative2DPosY is larger than 1. Setting it to 1.");
            }
        }
    }

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "State";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new State();
    }

    public State() {
        implCapsule = new ImplementsFunctionalityCapsule<IState>(this);
    }

    /**
     * @param behavior
     * @param labelForID
     * @param guardBehavior
     * @param functionSpecification
     * @param incomingTransition
     * @param outgoingTransition
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public State(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                 IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                 String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, comment, additionalLabel, additionalAttribute);
        implCapsule = new ImplementsFunctionalityCapsule<IState>(this);
        setGuardBehavior(guardBehavior);
        setFunctionSpecification(functionSpecification);
        setIncomingTransitions(incomingTransition);
        setOutgoingTransitions(outgoingTransition);
        generateAction();
    }

    public State(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null);
        implCapsule = new ImplementsFunctionalityCapsule<IState>(this);
        setGuardBehavior(null);
        setFunctionSpecification(null);
        setIncomingTransitions(null);

        setOutgoingTransitions(null);
        generateAction();
    }


    public void addIncomingTransition(ITransition transition) {
        if (transition == null) {
            return;
        }
        if (incomingTransitions.tryAdd(transition.getModelComponentID(), transition)) {
            transition.setTargetState(this);
            publishElementAdded(transition);
            transition.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasIncomingTransition, transition.getUriModelComponentID()));
        }
    }

    /**
     * Method that returns the incoming transition attribute of the instance
     *
     * @return The incoming transition attribute of the instance
     */
    public Map<String, ITransition> getIncomingTransitions() {
        return new HashMap<String, ITransition>(incomingTransitions);
    }


    public void addOutgoingTransition(ITransition transition) {
        if (transition == null) {
            return;
        }
        if (outgoingTransitions.tryAdd(transition.getModelComponentID(), transition)) {
            transition.setSourceState(this);
            publishElementAdded(transition);
            transition.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasOutgoingTransition, transition.getUriModelComponentID()));
        }
    }

    public void setOutgoingTransitions(Set<ITransition> transitions, int removeCascadeDepth) {
        for (ITransition transition : getOutgoingTransitions().values()) {
            removeOutgoingTransition(transition.getModelComponentID());
        }
        if (transitions == null) return;
        for (ITransition transition : transitions) {
            addOutgoingTransition(transition);
        }
    }

    public void setOutgoingTransitions(Set<ITransition> transitions) {
        for (ITransition transition : getOutgoingTransitions().values()) {
            removeOutgoingTransition(transition.getModelComponentID());
        }
        if (transitions == null) return;
        for (ITransition transition : transitions) {
            addOutgoingTransition(transition);
        }
    }

    public void setIncomingTransitions(Set<ITransition> transitions, int removeCascadeDepth) {
        for (ITransition transition : getIncomingTransitions().values()) {
            removeIncomingTransition(transition.getModelComponentID());
        }
        if (transitions == null) return;
        for (ITransition transition : transitions) {
            addIncomingTransition(transition);
        }
    }

    public void setIncomingTransitions(Set<ITransition> transitions) {
        for (ITransition transition : getIncomingTransitions().values()) {
            removeIncomingTransition(transition.getModelComponentID());
        }
        if (transitions == null) return;
        for (ITransition transition : transitions) {
            addIncomingTransition(transition);
        }
    }

    public void removeOutgoingTransition(String modelCompID, int removeCascadeDepth) {
        if (modelCompID == null) return;
        ITransition transition = outgoingTransitions.get(modelCompID);
        if (transition != null) {
            outgoingTransitions.remove(modelCompID);
            transition.unregister(this);
            transition.setSourceState(null);
            action.updateRemoved(transition, this);
            removeTriple(new IncompleteTriple(OWLTags.stdHasOutgoingTransition, transition.getUriModelComponentID()));
        }
    }

    public void removeOutgoingTransition(String modelCompID) {
        if (modelCompID == null) return;
        ITransition transition = outgoingTransitions.get(modelCompID);
        if (transition != null) {
            outgoingTransitions.remove(modelCompID);
            transition.unregister(this);
            transition.setSourceState(null);
            action.updateRemoved(transition, this);
            removeTriple(new IncompleteTriple(OWLTags.stdHasOutgoingTransition, transition.getUriModelComponentID()));
        }
    }

    public void removeIncomingTransition(String modelCompID, int removeCascadeDepth) {
        if (modelCompID == null) return;
        ITransition transition = incomingTransitions.get(modelCompID);
        if (transition != null) {
            incomingTransitions.remove(modelCompID);
            transition.unregister(this);
            transition.setTargetState(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasIncomingTransition, transition.getUriModelComponentID()));
        }
    }

    public void removeIncomingTransition(String modelCompID) {
        if (modelCompID == null) return;
        ITransition transition = incomingTransitions.get(modelCompID);
        if (transition != null) {
            incomingTransitions.remove(modelCompID);
            transition.unregister(this);
            transition.setTargetState(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasIncomingTransition, transition.getUriModelComponentID()));
        }
    }

    /**
     * Method that returns the outgoing transition attribute of the instance
     *
     * @return The outgoing transition attribute of the instance
     */
    public Map<String, ITransition> getOutgoingTransitions() {
        return new HashMap<String, ITransition>(outgoingTransitions);
    }

    public void setFunctionSpecification(IFunctionSpecification funSpec, int removeCascadeDepth) {
        IFunctionSpecification oldSpec = functionSpecification;
        // Might set it to null
        functionSpecification = funSpec;

        if (oldSpec != null) {
            if (oldSpec.equals(funSpec)) return;
            oldSpec.unregister(this);
            removeTriple(new IncompleteTriple(OWLTags.stdHasFunctionSpecification, oldSpec.getUriModelComponentID()));
        }

        if (funSpec != null) {
            publishElementAdded(funSpec);
            funSpec.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasFunctionSpecification, funSpec.getUriModelComponentID()));
        }
    }

    public void setFunctionSpecification(IFunctionSpecification funSpec) {
        IFunctionSpecification oldSpec = functionSpecification;
        // Might set it to null
        functionSpecification = funSpec;

        if (oldSpec != null) {
            if (oldSpec.equals(funSpec)) return;
            oldSpec.unregister(this);
            removeTriple(new IncompleteTriple(OWLTags.stdHasFunctionSpecification, oldSpec.getUriModelComponentID()));
        }

        if (funSpec != null) {
            publishElementAdded(funSpec);
            funSpec.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasFunctionSpecification, funSpec.getUriModelComponentID()));
        }
    }


    public IFunctionSpecification getFunctionSpecification() {
        return functionSpecification;
    }


    public void setGuardBehavior(IGuardBehavior guardBehav, int removeCascadeDepth) {
        IGuardBehavior oldBehavior = guardBehavior;
        // Might set it to null
        guardBehavior = guardBehav;

        if (oldBehavior != null) {
            if (oldBehavior.equals(guardBehav)) return;
            oldBehavior.unregister(this, removeCascadeDepth);
            oldBehavior.removeGuardedState(getModelComponentID());
            removeTriple(new IncompleteTriple(OWLTags.stdGuardedBy, oldBehavior.getUriModelComponentID()));
        }

        if (guardBehav != null) {
            publishElementAdded(guardBehav);
            guardBehav.register(this);
            guardBehav.addGuardedState(this);
            addTriple(new IncompleteTriple(OWLTags.stdGuardedBy, guardBehav.getUriModelComponentID()));
        }
    }

    public void setGuardBehavior(IGuardBehavior guardBehav) {
        IGuardBehavior oldBehavior = guardBehavior;
        // Might set it to null
        guardBehavior = guardBehav;

        if (oldBehavior != null) {
            if (oldBehavior.equals(guardBehav)) return;
            oldBehavior.unregister(this, 0);
            oldBehavior.removeGuardedState(getModelComponentID());
            removeTriple(new IncompleteTriple(OWLTags.stdGuardedBy, oldBehavior.getUriModelComponentID()));
        }

        if (guardBehav != null) {
            publishElementAdded(guardBehav);
            guardBehav.register(this);
            guardBehav.addGuardedState(this);
            addTriple(new IncompleteTriple(OWLTags.stdGuardedBy, guardBehav.getUriModelComponentID()));
        }
    }


    public IGuardBehavior getGuardBehavior() {
        return guardBehavior;
    }

    //TODO: neu implementieren ?
    protected void generateAction(IAction newGeneratedAction) {
        IAction oldAction = action;
        // Might set it to null
        String label = (getModelComponentLabelsAsStrings().size() > 0) ? getModelComponentLabelsAsStrings().get(0) : getModelComponentID();
        this.action = (newGeneratedAction == null) ? new Action(this, "ActionFor" + label) : newGeneratedAction;

        if (oldAction != null) {
            if (oldAction.equals(action)) return;
            oldAction.unregister(this);
            oldAction.removeFromEverything();
            removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldAction.getUriModelComponentID()));
        }

        if (!(action == null)) {
            publishElementAdded(action);
            action.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, action.getUriModelComponentID()));
        }
    }

    protected void generateAction() {
        IAction oldAction = action;
        // Might set it to null
        String label = (getModelComponentLabelsAsStrings().size() > 0) ? getModelComponentLabelsAsStrings().get(0) : getModelComponentID();
        this.action = new Action(this, "ActionFor" + label);

        if (oldAction != null) {
            if (oldAction.equals(action)) return;
            oldAction.unregister(this);
            oldAction.removeFromEverything();
            removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldAction.getUriModelComponentID()));
        }

        if (!(action == null)) {
            publishElementAdded(action);
            action.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, action.getUriModelComponentID()));
        }
    }


    public IAction getAction() {
        return action;
    }

    public boolean isStateType(IState.StateType stateType) {
        return stateTypes.contains(stateType);
    }

    public void setIsStateType(IState.StateType stateType) {
        switch (stateType) {
            case InitialStateOfBehavior:
                if (stateTypes.add(stateType))
                    addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfBehavior"));
                break;
            case InitialStateOfChoiceSegmentPath:
                if (stateTypes.add(stateType))
                    addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfChoiceSegmentPath"));
                break;
            case EndState:
                if (stateTypes.add(stateType)) {
                    addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "EndState"));
                    ISubjectBehavior behavior = getContainedBy();
                    if (behavior != null && (behavior instanceof ISubjectBaseBehavior baseBehav)) {
                        baseBehav.registerEndState(this);
                    }
                }
                break;
        }

    }

    public void removeStateType(IState.StateType stateType) {
        // If the type was removed successfully
        if (stateTypes.remove(stateType)) {
            switch (stateType) {
                case InitialStateOfBehavior:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfBehavior"));
                    ISubjectBehavior behav = getContainedBy();
                    if (behav != null) {
                        behav.setInitialState(null);
                    }
                    break;
                case InitialStateOfChoiceSegmentPath:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfChoiceSegmentPath"));
                    break;
                case EndState:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "EndState"));
                    ISubjectBehavior behavior = getContainedBy();
                    if (behavior != null && behavior instanceof ISubjectBaseBehavior baseBehav) {
                        baseBehav.unregisterEndState(getModelComponentID());
                    }
                    break;
            }

        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        //TODO: in alps.net.api sind folgende 3 Zeilen auskommentiert
        Locale customLocale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getInstance(customLocale);
        numberFormat.setGroupingUsed(false);
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element)) {
            return true;
        } else if (element != null) {
            if (element instanceof ITransition transition) {
                if (predicate.contains(OWLTags.hasIncomingTransition)) {
                    addIncomingTransition(transition);
                    return true;
                } else if (predicate.contains(OWLTags.hasOutgoingTransition)) {
                    addOutgoingTransition(transition);
                    return true;
                }

            } else if (predicate.contains(OWLTags.guardedBy) && element instanceof IGuardBehavior guard) {
                setGuardBehavior(guard);
                return true;
            } else if (predicate.contains(OWLTags.hasFunctionSpecification) && element instanceof IFunctionSpecification specification) {
                setFunctionSpecification(specification);
                return true;
            } else if (predicate.contains(OWLTags.belongsTo) && element instanceof IAction action) {
                generateAction(action);
                return true;
            } else if (predicate.contains(OWLTags.referencesMacroBehavior) && element instanceof IMacroBehavior behavior) {
                setReferencedMacroBehavior(behavior);
                return true;
            }

        } else if (predicate.contains(OWLTags.abstrHas2DPageRatio)) {
            try {
                set2DPageRatio(numberFormat.parse(objectContent).doubleValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_PosX)) {
            try {
                setRelative2DPosX(numberFormat.parse(objectContent).doubleValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_PosY)) {
            try {
                setRelative2DPosY(numberFormat.parse(objectContent).doubleValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_Height)) {
            try {
                setRelative2DHeight(numberFormat.parse(objectContent).doubleValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_Width)) {
            try {
                setRelative2DWidth(numberFormat.parse(objectContent).doubleValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if (predicate.contains(OWLTags.type)) {
            if (objectContent.toLowerCase().contains("endstate") && !(objectContent.toLowerCase().contains("sendstate"))) {
                setIsStateType(StateType.EndState);
                return true;
            } else if (objectContent.toLowerCase().contains("initialstateofbehavior")) {
                setIsStateType(StateType.InitialStateOfBehavior);
                return true;
            } else if (objectContent.toLowerCase().contains("initialstateofchoicesegmentpath")) {
                setIsStateType(StateType.InitialStateOfChoiceSegmentPath);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getAction() != null)
            baseElements.add(getAction());
        if (getGuardBehavior() != null && specification == ConnectedElementsSetSpecification.ALL)
            baseElements.add(getGuardBehavior());
        if (getFunctionSpecification() != null && (specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD))
            baseElements.add(getFunctionSpecification());
        if (specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD ||
                specification == ConnectedElementsSetSpecification.TO_REMOVE_DIRECTLY_ADJACENT || specification == ConnectedElementsSetSpecification.TO_REMOVE_ADJACENT_AND_MORE) {
            for (ITransition transition : getOutgoingTransitions().values())
                baseElements.add(transition);

            if (specification != ConnectedElementsSetSpecification.TO_REMOVE_DIRECTLY_ADJACENT)
                for (ITransition transition : getIncomingTransitions().values())
                    baseElements.add(transition);
        }
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller);
        if (update == null) return;
        if (update.equals(action)) {
            // TODO what to do here?
        } else if (update.equals(guardBehavior)) {
            setGuardBehavior(null, removeCascadeDepth);
        } else if (update.equals(functionSpecification)) {
            setFunctionSpecification(null, removeCascadeDepth);
        } else {
            for (ITransition trans : incomingTransitions.values()) {
                if (update.equals(trans)) {
                    removeIncomingTransition(trans.getModelComponentID(), removeCascadeDepth);
                    return;
                }
            }
            for (ITransition trans : outgoingTransitions.values()) {
                if (update.equals(trans)) {
                    removeOutgoingTransition(trans.getModelComponentID(), removeCascadeDepth);
                    return;
                }
            }
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller);
        if (update == null) return;
        if (update.equals(action)) {
            // TODO what to do here?
        } else if (update.equals(guardBehavior)) {
            setGuardBehavior(null, 0);
        } else if (update.equals(functionSpecification)) {
            setFunctionSpecification(null, 0);
        } else {
            for (ITransition trans : incomingTransitions.values()) {
                if (update.equals(trans)) {
                    removeIncomingTransition(trans.getModelComponentID(), 0);
                    return;
                }
            }
            for (ITransition trans : outgoingTransitions.values()) {
                if (update.equals(trans)) {
                    removeOutgoingTransition(trans.getModelComponentID(), 0);
                    return;
                }
            }
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (incomingTransitions.containsKey(oldID)) {
            ITransition element = incomingTransitions.get(oldID);
            incomingTransitions.remove(oldID);
            incomingTransitions.put(element.getModelComponentID(), element);
        }

        if (outgoingTransitions.containsKey(oldID)) {
            ITransition element = outgoingTransitions.get(oldID);
            outgoingTransitions.remove(oldID);
            outgoingTransitions.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }


    protected static final String STATE_REF_CLASS_NAME = "StateReference";

    /**
     * Sets a state that is referenced by this state.
     * According to the PASS standard, this functionality belongs to the "StateReference" class.
     * Here, this functionality is inside the state class and should be used if the current instance should be a StateReference
     *
     * @param state              The referenced state
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setReferencedState(IState state, int removeCascadeDepth) {
        if (!(state.getClass().equals(this.getClass()))) return;
        IState oldReference = referenceState;
        // Might set it to null
        this.referenceState = state;

        if (oldReference != null) {
            if (oldReference.equals(state)) return;
            oldReference.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldReference.getUriModelComponentID()));
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + STATE_REF_CLASS_NAME));
        }

        if (!(state == null)) {
            state.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, state.getUriModelComponentID()));
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + STATE_REF_CLASS_NAME));
        }
    }

    /**
     * Sets a state that is referenced by this state.
     * According to the PASS standard, this functionality belongs to the "StateReference" class.
     * Here, this functionality is inside the state class and should be used if the current instance should be a StateReference
     *
     * @param state The referenced state
     */
    public void setReferencedState(IState state) {
        if (!(state.getClass().equals(this.getClass()))) return;
        IState oldReference = referenceState;
        // Might set it to null
        this.referenceState = state;

        if (oldReference != null) {
            if (oldReference.equals(state)) return;
            oldReference.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldReference.getUriModelComponentID()));
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + STATE_REF_CLASS_NAME));
        }

        if (!(state == null)) {
            state.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdReferences, state.getUriModelComponentID()));
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + STATE_REF_CLASS_NAME));
        }
    }

    /**
     * Gets the state that is referenced by this state.
     * According to the PASS standard, this functionality belongs to the "StateReference" class.
     * Here, this functionality is inside the state class and should be used if the current instance should be a StateReference
     *
     * @return The referenced state
     */
    public IState getReferencedState() {
        return referenceState;
    }

    public boolean isReference() {
        return !(referenceState == null);
    }

    @Override
    public boolean register(IValueChangedObserver<IPASSProcessModelElement> observer) {
        boolean added = super.register(observer);
        // Special case: State is parsed, action knows about state, state does not know action -> action registeres at the state, state sets reference to action
        if (added && observer instanceof IAction action && getAction() == null) {
            generateAction(action);
        }
        return added;
    }

    @Override
    public boolean unregister(IValueChangedObserver<IPASSProcessModelElement> observer, int removeCascadeDepth) {
        boolean added = super.unregister(observer, removeCascadeDepth);
        // Special case: State is parsed, action knows about state, state does not know action -> action registeres at the state, state sets reference to action
        if (added && observer instanceof IAction action && getAction().equals(action)) {
            generateAction(null);
        }
        return added;
    }

    @Override
    public boolean unregister(IValueChangedObserver<IPASSProcessModelElement> observer) {
        boolean added = super.unregister(observer, 0);
        // Special case: State is parsed, action knows about state, state does not know action -> action registeres at the state, state sets reference to action
        if (added && observer instanceof IAction action && getAction().equals(action)) {
            generateAction(null);
        }
        return added;
    }

    public void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs) {
        implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
    }

    public void addImplementedInterfaceIDReference(String implementedInterfaceID) {
        implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
    }

    public void removeImplementedInterfacesIDReference(String implementedInterfaceID) {
        implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
    }

    public Set<String> getImplementedInterfacesIDReferences() {
        return implCapsule.getImplementedInterfacesIDReferences();
    }

    public void setImplementedInterfaces(Set<IState> implementedInterface, int removeCascadeDepth) {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
    }

    public void setImplementedInterfaces(Set<IState> implementedInterface) {
        implCapsule.setImplementedInterfaces(implementedInterface, 0);
    }

    public void addImplementedInterface(IState implementedInterface) {
        implCapsule.addImplementedInterface(implementedInterface);
    }

    public void removeImplementedInterfaces(String id, int removeCascadeDepth) {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
    }

    public void removeImplementedInterfaces(String id) {
        implCapsule.removeImplementedInterfaces(id, 0);
    }

    public Map<String, IState> getImplementedInterfaces() {
        return implCapsule.getImplementedInterfaces();
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
}


