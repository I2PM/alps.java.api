package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IStateReference;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.*;

/**
 * Class that represents a state
 */

public class State extends BehaviorDescribingComponent implements IStateReference
        {
protected final ICompatibilityDictionary<String, ITransition> incomingTransitions = new CompatibilityDictionary<String, ITransition>();
protected final ICompatibilityDictionary<String, ITransition> outgoingTransitions = new CompatibilityDictionary<String, ITransition>();
protected final IImplementsFunctionalityCapsule<IState> implCapsule;
protected final Set<IState.StateType> stateTypes = new HashSet<IState.StateType>();
protected IFunctionSpecification functionSpecification;
protected IGuardBehavior guardBehavior;
protected IAction action;
protected IState referenceState;

/**
 * Name of the class, needed for parsing
 */
            private final String className = "State";
@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new State();
        }

protected State() { implCapsule = new ImplementsFunctionalityCapsule<IState>(this); }

            /**
             *
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
            //TODO: KOnstuktor überladen
            public State(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                         IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                         String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
                super(behavior, labelForID, comment, additionalLabel, additionalAttribute);
        implCapsule = new ImplementsFunctionalityCapsule<IState>(this);
        setGuardBehavior(guardBehavior);
        setFunctionSpecification(functionSpecification);
        setIncomingTransitions(incomingTransition);
        setOutgoingTransitions(outgoingTransition);
        generateAction();
        }


public void addIncomingTransition(ITransition transition)
        {
        if (transition == null) { return; }
        if (incomingTransitions.tryAdd(transition.getModelComponentID(), transition))
        {
        transition.setTargetState(this);
        publishElementAdded(transition);
        transition.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdHasIncomingTransition, transition.getUriModelComponentID()));
        }
        }

            /**
             * Method that returns the incoming transition attribute of the instance
             * @return The incoming transition attribute of the instance
             */
            public Map<String, ITransition> getIncomingTransitions()
        {
        return new HashMap<String, ITransition>(incomingTransitions);
        }


public void addOutgoingTransition(ITransition transition)
        {
        if (transition == null) { return; }
        if (outgoingTransitions.tryAdd(transition.getModelComponentID(), transition))
        {
        transition.setSourceState(this);
        publishElementAdded(transition);
        transition.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdHasOutgoingTransition, transition.getUriModelComponentID()));
        }
        }

public void setOutgoingTransitions(Set<ITransition> transitions, int removeCascadeDepth)
        {
        for(ITransition transition: getOutgoingTransitions().values())
        {
        removeOutgoingTransition(transition.getModelComponentID());
        }
        if (transitions == null) return;
        for(ITransition transition: transitions)
        {
        addOutgoingTransition(transition);
        }
        }

            public void setOutgoingTransitions(Set<ITransition> transitions)
            {
                for(ITransition transition: getOutgoingTransitions().values())
                {
                    removeOutgoingTransition(transition.getModelComponentID());
                }
                if (transitions == null) return;
                for(ITransition transition: transitions)
                {
                    addOutgoingTransition(transition);
                }
            }

public void setIncomingTransitions(Set<ITransition> transitions, int removeCascadeDepth)
        {
        for(ITransition transition: getIncomingTransitions().values())
        {
        removeIncomingTransition(transition.getModelComponentID());
        }
        if (transitions == null) return;
        for(ITransition transition: transitions)
        {
        addIncomingTransition(transition);
        }
        }

            public void setIncomingTransitions(Set<ITransition> transitions)
            {
                for(ITransition transition: getIncomingTransitions().values())
                {
                    removeIncomingTransition(transition.getModelComponentID());
                }
                if (transitions == null) return;
                for(ITransition transition: transitions)
                {
                    addIncomingTransition(transition);
                }
            }


            public void removeOutgoingTransition(String modelCompID)
            {
                if (modelCompID == null) return;
                ITransition transition = outgoingTransitions.get(modelCompID);
                if(transition!=null){
                    outgoingTransitions.remove(modelCompID);
                    transition.unregister(this);
                    transition.setSourceState(null);
                    action.updateRemoved(transition, this);
                    removeTriple(new IncompleteTriple(OWLTags.stdHasOutgoingTransition, transition.getUriModelComponentID()));
                }
            }


    public void removeIncomingTransition(String modelCompID)
        {
        if (modelCompID == null) return;
        ITransition transition = incomingTransitions.get(modelCompID);
        if(transition!=null){
        incomingTransitions.remove(modelCompID);
        transition.unregister(this);
        transition.setTargetState(null);
        removeTriple(new IncompleteTriple(OWLTags.stdHasIncomingTransition, transition.getUriModelComponentID()));
        }
        }

            /**
             * Method that returns the outgoing transition attribute of the instance
             * @return The outgoing transition attribute of the instance
             */
            public Map<String, ITransition> getOutgoingTransitions()
        {
        return new HashMap<String, ITransition>(outgoingTransitions);
        }


public void setFunctionSpecification(IFunctionSpecification funSpec)
        {
        IFunctionSpecification oldSpec = functionSpecification;
        // Might set it to null
        functionSpecification = funSpec;

        if (oldSpec != null)
        {
        if (oldSpec.equals(funSpec)) return;
        oldSpec.unregister(this);
        removeTriple(new IncompleteTriple(OWLTags.stdHasFunctionSpecification, oldSpec.getUriModelComponentID()));
        }

        if (funSpec != null)
        {
        publishElementAdded(funSpec);
        funSpec.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdHasFunctionSpecification, funSpec.getUriModelComponentID()));
        }
        }


public IFunctionSpecification getFunctionSpecification()
        {
        return functionSpecification;
        }


public void setGuardBehavior(IGuardBehavior guardBehav, int removeCascadeDepth)
        {
        IGuardBehavior oldBehavior = guardBehavior;
        // Might set it to null
        guardBehavior = guardBehav;

        if (oldBehavior != null)
        {
        if (oldBehavior.equals(guardBehav)) return;
        oldBehavior.unregister(this, removeCascadeDepth);
        oldBehavior.removeGuardedState(getModelComponentID());
        removeTriple(new IncompleteTriple(OWLTags.stdGuardedBy, oldBehavior.getUriModelComponentID()));
        }

        if (guardBehav != null)
        {
        publishElementAdded(guardBehav);
        guardBehav.register(this);
        guardBehav.addGuardedState(this);
        addTriple(new IncompleteTriple(OWLTags.stdGuardedBy, guardBehav.getUriModelComponentID()));
        }
        }

            public void setGuardBehavior(IGuardBehavior guardBehav)
            {
                IGuardBehavior oldBehavior = guardBehavior;
                // Might set it to null
                guardBehavior = guardBehav;

                if (oldBehavior != null)
                {
                    if (oldBehavior.equals(guardBehav)) return;
                    oldBehavior.unregister(this, 0);
                    oldBehavior.removeGuardedState(getModelComponentID());
                    removeTriple(new IncompleteTriple(OWLTags.stdGuardedBy, oldBehavior.getUriModelComponentID()));
                }

                if (guardBehav != null)
                {
                    publishElementAdded(guardBehav);
                    guardBehav.register(this);
                    guardBehav.addGuardedState(this);
                    addTriple(new IncompleteTriple(OWLTags.stdGuardedBy, guardBehav.getUriModelComponentID()));
                }
            }


public IGuardBehavior getGuardBehavior()
        {
        return guardBehavior;
        }


protected void generateAction(IAction newGeneratedAction = null)
        {
        IAction oldAction = action;
        // Might set it to null
        String label = (getModelComponentLabelsAsStrings().Count > 0) ? getModelComponentLabelsAsStrings()[0] : getModelComponentID();
        this.action = (newGeneratedAction == null) ? new Action(this, "ActionFor" + label) : newGeneratedAction;

        if (oldAction != null)
        {
        if (oldAction.equals(action)) return;
        oldAction.unregister(this);
        oldAction.removeFromEverything();
        removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldAction.getUriModelComponentID()));
        }

        if (!(action == null))
        {
        publishElementAdded(action);
        action.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, action.getUriModelComponentID()));
        }
        }


public IAction getAction()
        {
        return action;
        }

public bool isStateType(StateType stateType)
        {
        return stateTypes.Contains(stateType);
        }

public virtual void setIsStateType(StateType stateType)
        {
        switch (stateType)
        {
        case StateType.InitialStateOfBehavior:
        if (stateTypes.Add(stateType))
        addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfBehavior"));
        break;
        case StateType.InitialStateOfChoiceSegmentPath:
        if (stateTypes.Add(stateType))
        addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfChoiceSegmentPath"));
        break;
        case StateType.EndState:
        if (stateTypes.Add(stateType))
        {
        addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "EndState"));

        if (getContainedBy(out ISubjectBehavior behavior) && (behavior is ISubjectBaseBehavior baseBehav))
        {
        baseBehav.registerEndState(this);
        }
        }
        break;
        }

        }

public virtual void removeStateType(StateType stateType)
        {
        // If the type was removed successfully
        if (stateTypes.Remove(stateType))
        {
        switch (stateType)
        {
        case StateType.InitialStateOfBehavior:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfBehavior"));
        if (getContainedBy(out ISubjectBehavior behav))
        {
        behav.setInitialState(null);
        }
        break;
        case StateType.InitialStateOfChoiceSegmentPath:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "InitialStateOfChoiceSegmentPath"));
        break;
        case StateType.EndState:
        removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "EndState"));
        if (getContainedBy(out ISubjectBehavior behavior) && behavior is ISubjectBaseBehavior baseBehav)
        {
        baseBehav.unregisterEndState(getModelComponentID());
        }
        break;
        }

        }
        }


protected override bool parseAttribute(string predicate, string objectContent, string lang, string dataType, IParseablePASSProcessModelElement element)
        {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
        return true;
        else if (element != null)
        {
        if (element is ITransition transition)
        {
        if (predicate.Contains(OWLTags.hasIncomingTransition))
        {
        addIncomingTransition(transition);
        return true;
        }
        else if (predicate.Contains(OWLTags.hasOutgoingTransition))
        {
        addOutgoingTransition(transition);
        return true;
        }

        }
        else if (predicate.Contains(OWLTags.guardedBy) && element is IGuardBehavior guard)
        {
        setGuardBehavior(guard);
        return true;
        }

        else if (predicate.Contains(OWLTags.hasFunctionSpecification) && element is IFunctionSpecification specification)
        {
        setFunctionSpecification(specification);
        return true;
        }

        else if (predicate.Contains(OWLTags.belongsTo) && element is IAction action)
        {
        generateAction(action);
        return true;
        }
        else if (predicate.Contains(OWLTags.type))
        {
        if (objectContent.ToLower().Contains("endstate"))
        {
        setIsStateType(StateType.EndState);
        return true;
        }
        else if (objectContent.ToLower().Contains("initialstateofbehavior"))
        {
        setIsStateType(StateType.InitialStateOfBehavior);
        return true;
        }
        else if (objectContent.ToLower().Contains("initialstateofchoicesegmentpath"))
        {
        setIsStateType(StateType.InitialStateOfChoiceSegmentPath);
        return true;
        }
        }
        }
        return base.parseAttribute(predicate, objectContent, lang, dataType, element);
        }


public override ISet<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification)
        {
        ISet<IPASSProcessModelElement> baseElements = base.getAllConnectedElements(specification);
        if (getAction() != null)
        baseElements.Add(getAction());
        if (getGuardBehavior() != null && specification == ConnectedElementsSetSpecification.ALL)
        baseElements.Add(getGuardBehavior());
        if (getFunctionSpecification() != null && (specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD))
        baseElements.Add(getFunctionSpecification());
        if (specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD ||
        specification == ConnectedElementsSetSpecification.TO_REMOVE_DIRECTLY_ADJACENT || specification == ConnectedElementsSetSpecification.TO_REMOVE_ADJACENT_AND_MORE)
        {
        foreach (ITransition transition in getOutgoingTransitions().Values)
        baseElements.Add(transition);

        if (specification != ConnectedElementsSetSpecification.TO_REMOVE_DIRECTLY_ADJACENT)
        foreach (ITransition transition in getIncomingTransitions().Values)
        baseElements.Add(transition);
        }
        return baseElements;
        }


public override void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth = 0)
        {
        base.updateRemoved(update, caller);
        if (update is null) return;
        if (update.Equals(action))
        {
        // TODO what to do here?
        }
        else if (update.Equals(guardBehavior))
        {
        setGuardBehavior(null, removeCascadeDepth);
        }
        else if (update.Equals(functionSpecification))
        {
        setFunctionSpecification(null, removeCascadeDepth);
        }
        else
        {
        foreach (ITransition trans in incomingTransitions.Values)
        {
        if (update.Equals(trans))
        {
        removeIncomingTransition(trans.getModelComponentID(), removeCascadeDepth);
        return;
        }
        }
        foreach (ITransition trans in outgoingTransitions.Values)
        {
        if (update.Equals(trans))
        {
        removeOutgoingTransition(trans.getModelComponentID(), removeCascadeDepth);
        return;
        }
        }
        }
        }

public override void notifyModelComponentIDChanged(string oldID, string newID)
        {
        if (incomingTransitions.ContainsKey(oldID))
        {
        ITransition element = incomingTransitions[oldID];
        incomingTransitions.Remove(oldID);
        incomingTransitions.Add(element.getModelComponentID(), element);
        }

        if (outgoingTransitions.ContainsKey(oldID))
        {
        ITransition element = outgoingTransitions[oldID];
        outgoingTransitions.Remove(oldID);
        outgoingTransitions.Add(element.getModelComponentID(), element);
        }
        base.notifyModelComponentIDChanged(oldID, newID);
        }



protected static readonly string STATE_REF_CLASS_NAME = "StateReference";

/// <summary>
/// Sets a state that is referenced by this state.
/// According to the PASS standard, this functionality belongs to the "StateReference" class.
/// Here, this functionality is inside the state class and should be used if the current instance should be a StateReference
/// </summary>
/// <param name="state">The referenced state</param>
/// <param name="removeCascadeDepth">Parses the depth of a cascading delete for elements that are connected to the currently deleted one</param>
public virtual void setReferencedState(IState state, int removeCascadeDepth = 0)
        {
        if (!(state.GetType().Equals(this.GetType()))) return;
        IState oldReference = referenceState;
        // Might set it to null
        this.referenceState = state;

        if (oldReference != null)
        {
        if (oldReference.Equals(state)) return;
        oldReference.unregister(this, removeCascadeDepth);
        removeTriple(new IncompleteTriple(OWLTags.stdReferences, oldReference.getUriModelComponentID()));
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + STATE_REF_CLASS_NAME));
        }

        if (!(state is null))
        {
        state.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdReferences, state.getUriModelComponentID()));
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + STATE_REF_CLASS_NAME));
        }
        }

/// <summary>
/// Gets the state that is referenced by this state.
/// According to the PASS standard, this functionality belongs to the "StateReference" class.
/// Here, this functionality is inside the state class and should be used if the current instance should be a StateReference
/// </summary>
/// <returns>The referenced state</returns>
public IState getReferencedState()
        {
        return referenceState;
        }

public bool isReference()
        {
        return !(referenceState is null);
        }

public override bool register(IValueChangedObserver<IPASSProcessModelElement> observer)
        {
        bool added = base.register(observer);
        // Special case: State is parsed, action knows about state, state does not know action -> action registeres at the state, state sets reference to action
        if (added && observer is IAction action && getAction() is null)
        {
        generateAction(action);
        }
        return added;
        }

public override bool unregister(IValueChangedObserver<IPASSProcessModelElement> observer, int removeCascadeDepth = 0)
        {
        bool added = base.unregister(observer, removeCascadeDepth);
        // Special case: State is parsed, action knows about state, state does not know action -> action registeres at the state, state sets reference to action
        if (added && observer is IAction action && getAction().Equals(action))
        {
        generateAction(null);
        }
        return added;
        }

public void setImplementedInterfacesIDReferences(ISet<string> implementedInterfacesIDs)
        {
        implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
        }

public void addImplementedInterfaceIDReference(string implementedInterfaceID)
        {
        implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
        }

public void removeImplementedInterfacesIDReference(string implementedInterfaceID)
        {
        implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
        }

public ISet<string> getImplementedInterfacesIDReferences()
        {
        return implCapsule.getImplementedInterfacesIDReferences();
        }

public void setImplementedInterfaces(ISet<IState> implementedInterface, int removeCascadeDepth = 0)
        {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
        }

public void addImplementedInterface(IState implementedInterface)
        {
        implCapsule.addImplementedInterface(implementedInterface);
        }

public void removeImplementedInterfaces(string id, int removeCascadeDepth = 0)
        {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
        }

public IDictionary<string, IState> getImplementedInterfaces()
        {
        return implCapsule.getImplementedInterfaces();
        }
        }


