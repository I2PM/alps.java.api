package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a transition class
 */
public class Transition extends BehaviorDescribingComponent implements ITransition
        {
protected IAction belongsToAction;
protected IState sourceState;
protected IState targetState;
protected ITransitionCondition transitionCondition;
private ITransition.TransitionType transitionType;
protected final IImplementsFunctionalityCapsule<ITransition> implCapsule;
protected boolean isAbstractType = false;

private final String ABSTRACT_NAME = "AbstractPASSTransition";

/**
 * Name of the class, needed for parsing
 */
            private final String className = "Transition";

@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new Transition();
        }

protected Transition() { implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this); }
//TODO: Out Parameter
            /**
             *
             * @param sourceState
             * @param targetState
             * @param labelForID
             * @param transitionCondition
             * @param transitionType
             * @param comment
             * @param additionalLabel
             * @param additionalAttribute
             */
            public Transition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                              ITransition.TransitionType transitionType, String comment, String additionalLabel,
                              List<IIncompleteTriple> additionalAttribute){
        super(null, labelForID, comment, additionalLabel, additionalAttribute);
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
        ISubjectBehavior behavior = null;
        if (sourceState != null)
        sourceState.getContainedBy(out behavior);
        if (behavior == null && targetState != null)
        targetState.getContainedBy(out behavior);
        if (behavior != null)
        setContainedBy(behavior);
        setSourceState(sourceState);
        setTargetState(targetState);
        setTransitionCondition(transitionCondition);
        setTransitionType(transitionType);
        }

            public Transition(IState sourceState, IState targetState){
                super(null);
                implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
                ISubjectBehavior behavior = null;
                setSourceState(sourceState);
                setTargetState(targetState);
                setTransitionCondition(null);
                setTransitionType(ITransition.TransitionType.Standard);
            }

public Transition(ISubjectBehavior behavior, string labelForID = null, IState sourceState = null, IState targetState = null,
        ITransitionCondition transitionCondition = null,
        ITransition.TransitionType transitionType = ITransition.TransitionType.Standard, string comment = null, string additionalLabel = null,
        IList<IIncompleteTriple> additionalAttribute = null)
        : base(behavior, labelForID, comment, additionalLabel, additionalAttribute)
        {
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
        setSourceState(sourceState);
        setTargetState(targetState);
        setTransitionCondition(transitionCondition);
        setTransitionType(transitionType);
        }


/// <summary>
/// Used to set the action that belongs to this transition.
/// Only called from inside the class, should not be visible to the user (the action is set/removed automatically when a source state is added/removed)
/// </summary>
protected void setBelongsToAction(IAction action, int removeCascadeDepth = 0)
        {
        IAction oldAction = belongsToAction;
        // Might set it to null
        this.belongsToAction = action;

        if (oldAction != null)
        {
        if (oldAction.Equals(action)) return;
        oldAction.unregister(this, removeCascadeDepth);
        removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldAction.getUriModelComponentID()));
        }

        if (!(action is null))
        {
        publishElementAdded(action);
        action.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, action.getUriModelComponentID()));
        }
        }


public virtual void setSourceState(IState sourceState, int removeCascadeDepth = 0)
        {
        IState oldSourceState = this.sourceState;
        // Might set it to null
        this.sourceState = sourceState;

        if (oldSourceState != null)
        {
        if (oldSourceState.Equals(sourceState)) return;
        oldSourceState.unregister(this, removeCascadeDepth);
        oldSourceState.removeOutgoingTransition(getModelComponentID());
        setBelongsToAction(null);
        removeTriple(new IncompleteTriple(OWLTags.stdHasSourceState, oldSourceState.getUriModelComponentID()));
        }
        if (!(sourceState is null))
        {
        publishElementAdded(sourceState);
        sourceState.register(this);
        sourceState.addOutgoingTransition(this);
        setBelongsToAction(sourceState.getAction());
        addTriple(new IncompleteTriple(OWLTags.stdHasSourceState, sourceState.getUriModelComponentID()));
        }
        }


public virtual void setTargetState(IState targetState, int removeCascadeDepth = 0)
        {
        IState oldState = this.targetState;
        // Might set it to null
        this.targetState = targetState;

        if (oldState != null)
        {
        if (oldState.Equals(targetState)) return;
        oldState.unregister(this, removeCascadeDepth);
        oldState.removeIncomingTransition(getModelComponentID());
        removeTriple(new IncompleteTriple(OWLTags.stdHasTargetState, oldState.getUriModelComponentID()));
        }

        if (!(targetState is null))
        {
        publishElementAdded(targetState);
        targetState.register(this);
        targetState.addIncomingTransition(this);
        addTriple(new IncompleteTriple(OWLTags.stdHasTargetState, targetState.getUriModelComponentID()));
        }
        }


public virtual void setTransitionCondition(ITransitionCondition transitionCondition, int removeCascadeDepth = 0)
        {
        ITransitionCondition oldCond = this.transitionCondition;
        // Might set it to null
        this.transitionCondition = transitionCondition;

        if (oldCond != null)
        {
        if (oldCond.Equals(transitionCondition)) return;
        oldCond.unregister(this, removeCascadeDepth);
        removeTriple(new IncompleteTriple(OWLTags.stdHasTransitionCondition, oldCond.getUriModelComponentID()));
        }

        if (!(transitionCondition is null))
        {
        publishElementAdded(transitionCondition);
        transitionCondition.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdHasTransitionCondition, transitionCondition.getUriModelComponentID()));
        }
        }


public IAction getBelongsToAction()
        {
        return belongsToAction;
        }


public IState getSourceState()
        {
        return sourceState;
        }


public IState getTargetState()
        {
        return targetState;
        }


public ITransitionCondition getTransitionCondition()
        {
        return transitionCondition;
        }

public void setTransitionType(ITransition.TransitionType type)
        {
        transitionType = type;
        }

public ITransition.TransitionType getTransitionType()
        {
        return transitionType;
        }

protected override bool parseAttribute(string predicate, string objectContent, string lang, string dataType, IParseablePASSProcessModelElement element)
        {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
        return true;
        else if (element != null)
        {
        if (predicate.Contains(OWLTags.belongsTo) && element is IAction action)
        {
        setBelongsToAction(action);
        return true;
        }

        else if (predicate.Contains(OWLTags.hasTransitionCondition) && element is ITransitionCondition condition)
        {
        setTransitionCondition(condition);
        return true;
        }

        else if (element is IState state)
        {


        if (predicate.Contains(OWLTags.hasSourceState))
        {
        setSourceState(state);
        return true;
        }
        else if (predicate.Contains(OWLTags.hasTargetState))
        {
        setTargetState(state);
        return true;
        }

        }

        }
        else
        {
        if (predicate.Contains(OWLTags.type))
        {
        if (objectContent.Contains(ABSTRACT_NAME))
        {
        setIsAbstract(true);
        return true;
        }
        }
        }
        return base.parseAttribute(predicate, objectContent, lang, dataType, element);
        }


public override ISet<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification)
        {
        ISet<IPASSProcessModelElement> baseElements = base.getAllConnectedElements(specification);
        if (getBelongsToAction() != null && specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD)
        baseElements.Add(getBelongsToAction());
        if (specification != ConnectedElementsSetSpecification.TO_ALWAYS_REMOVE)
        {
        if (getSourceState() != null)
        baseElements.Add(getSourceState());
        if (getTargetState() != null)
        baseElements.Add(getTargetState());
        }
        if (getTransitionCondition() != null)
        baseElements.Add(getTransitionCondition());
        return baseElements;
        }

public override void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth = 0)
        {
        base.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null)
        {
        if (update is IAction action && action.Equals(getBelongsToAction()))
        setBelongsToAction(null, removeCascadeDepth);
        if (update is IState state)
        {
        if (state.Equals(getSourceState()))
        setSourceState(null, removeCascadeDepth);
        if (state.Equals(getTargetState()))
        setTargetState(null, removeCascadeDepth);
        }
        if (update is ITransitionCondition condition && condition.Equals(getTransitionCondition()))
        setTransitionCondition(null, removeCascadeDepth);
        }
        }

public override void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller)
        {
        if (update != null)
        {
        if (!(caller is null) && caller.Equals(getSourceState()))
        {
        if (update is IAction action)
        {
        setBelongsToAction(action);
        }
        }
        }
        }


public void setIsAbstract(bool isAbstract)
        {
        this.isAbstractType = isAbstract;
        if (isAbstract)
        {
        addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
        else
        {
        removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
        }

public bool isAbstract()
        {
        return isAbstractType;
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

public void setImplementedInterfaces(ISet<ITransition> implementedInterface, int removeCascadeDepth = 0)
        {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
        }

public void addImplementedInterface(ITransition implementedInterface)
        {
        implCapsule.addImplementedInterface(implementedInterface);
        }

public void removeImplementedInterfaces(string id, int removeCascadeDepth = 0)
        {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
        }

public IDictionary<string, ITransition> getImplementedInterfaces()
        {
        return implCapsule.getImplementedInterfaces();
        }

        }
