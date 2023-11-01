package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoints.ISimple2DVisualizationPathPoint;
import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.*;

/**
 * Class that represents a transition class
 */
public class Transition extends BehaviorDescribingComponent implements ITransition {
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
    private double has2DPageRatio=-1;
    private double hasRelative2D_BeginX=-1;
    private double hasRelative2D_BeginY=-1;
    private double hasRelative2D_EndX=-1;
    private double hasRelative2D_EndY=-1;
    private List<ISimple2DVisualizationPathPoint> pathPoints = new ArrayList<ISimple2DVisualizationPathPoint>();


    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new Transition();
    }

    public Transition() {
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
    }
//TODO: Out Parameter

    /**
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
                      List<IIncompleteTriple> additionalAttribute) {
        super(null, labelForID, comment, additionalLabel, additionalAttribute);
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
        ISubjectBehavior behavior = null;
        if (sourceState != null)
            behavior = sourceState.getContainedBy();
        if (behavior == null && targetState != null)
            behavior = targetState.getContainedBy();
        if (behavior != null)
            setContainedBy(behavior);
        setSourceState(sourceState);
        setTargetState(targetState);
        setTransitionCondition(transitionCondition);
        setTransitionType(transitionType);
    }

    public Transition(IState sourceState, IState targetState) {
        super(null);
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
        ISubjectBehavior behavior = null;
        if (sourceState != null)
            behavior = sourceState.getContainedBy();
        if (behavior == null && targetState != null)
            behavior = targetState.getContainedBy();
        if (behavior != null)
            setSourceState(sourceState);
        setTargetState(targetState);
        setTransitionCondition(null);
        setTransitionType(ITransition.TransitionType.Standard);
    }

    public Transition(ISubjectBehavior behavior, String labelForID, IState sourceState, IState targetState,
                      ITransitionCondition transitionCondition,
                      ITransition.TransitionType transitionType, String comment, String additionalLabel,
                      List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, comment, additionalLabel, additionalAttribute);
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
        setSourceState(sourceState);
        setTargetState(targetState);
        setTransitionCondition(transitionCondition);
        setTransitionType(transitionType);
    }

    public Transition(ISubjectBehavior behavior) {
        super(behavior);
        implCapsule = new ImplementsFunctionalityCapsule<ITransition>(this);
        setSourceState(null);
        setTargetState(null);
        setTransitionCondition(null);
        setTransitionType(TransitionType.Standard);
    }

    /**
     * Used to set the action that belongs to this transition.
     * Only called from inside the class, should not be visible to the user (the action is set/removed automatically when a source state is added/removed)
     */
    protected void setBelongsToAction(IAction action, int removeCascadeDepth) {
        IAction oldAction = belongsToAction;
        // Might set it to null
        this.belongsToAction = action;

        if (oldAction != null) {
            if (oldAction.equals(action)) return;
            oldAction.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldAction.getUriModelComponentID()));
        }

        if (!(action == null)) {
            publishElementAdded(action);
            action.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, action.getUriModelComponentID()));
        }
    }


    /**
     * Used to set the action that belongs to this transition.
     * Only called from inside the class, should not be visible to the user (the action is set/removed automatically when a source state is added/removed)
     */
    protected void setBelongsToAction(IAction action) {
        IAction oldAction = belongsToAction;
        // Might set it to null
        this.belongsToAction = action;

        if (oldAction != null) {
            if (oldAction.equals(action)) return;
            oldAction.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldAction.getUriModelComponentID()));
        }

        if (!(action == null)) {
            publishElementAdded(action);
            action.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, action.getUriModelComponentID()));
        }
    }


    public void setSourceState(IState sourceState, int removeCascadeDepth) {
        IState oldSourceState = this.sourceState;
        // Might set it to null
        this.sourceState = sourceState;

        if (oldSourceState != null) {
            if (oldSourceState.equals(sourceState)) return;
            oldSourceState.unregister(this, removeCascadeDepth);
            oldSourceState.removeOutgoingTransition(getModelComponentID());
            setBelongsToAction(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasSourceState, oldSourceState.getUriModelComponentID()));
        }
        if (!(sourceState == null)) {
            publishElementAdded(sourceState);
            sourceState.register(this);
            sourceState.addOutgoingTransition(this);
            setBelongsToAction(sourceState.getAction());
            addTriple(new IncompleteTriple(OWLTags.stdHasSourceState, sourceState.getUriModelComponentID()));
        }
    }

    public void setSourceState(IState sourceState) {
        IState oldSourceState = this.sourceState;
        // Might set it to null
        this.sourceState = sourceState;

        if (oldSourceState != null) {
            if (oldSourceState.equals(sourceState)) return;
            oldSourceState.unregister(this, 0);
            oldSourceState.removeOutgoingTransition(getModelComponentID());
            setBelongsToAction(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasSourceState, oldSourceState.getUriModelComponentID()));
        }
        if (!(sourceState == null)) {
            publishElementAdded(sourceState);
            sourceState.register(this);
            sourceState.addOutgoingTransition(this);
            setBelongsToAction(sourceState.getAction());
            addTriple(new IncompleteTriple(OWLTags.stdHasSourceState, sourceState.getUriModelComponentID()));
        }
    }


    public void setTargetState(IState targetState, int removeCascadeDepth) {
        IState oldState = this.targetState;
        // Might set it to null
        this.targetState = targetState;

        if (oldState != null) {
            if (oldState.equals(targetState)) return;
            oldState.unregister(this, removeCascadeDepth);
            oldState.removeIncomingTransition(getModelComponentID());
            removeTriple(new IncompleteTriple(OWLTags.stdHasTargetState, oldState.getUriModelComponentID()));
        }

        if (!(targetState == null)) {
            publishElementAdded(targetState);
            targetState.register(this);
            targetState.addIncomingTransition(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasTargetState, targetState.getUriModelComponentID()));
        }
    }

    public void setTargetState(IState targetState) {
        IState oldState = this.targetState;
        // Might set it to null
        this.targetState = targetState;

        if (oldState != null) {
            if (oldState.equals(targetState)) return;
            oldState.unregister(this, 0);
            oldState.removeIncomingTransition(getModelComponentID());
            removeTriple(new IncompleteTriple(OWLTags.stdHasTargetState, oldState.getUriModelComponentID()));
        }

        if (!(targetState == null)) {
            publishElementAdded(targetState);
            targetState.register(this);
            targetState.addIncomingTransition(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasTargetState, targetState.getUriModelComponentID()));
        }
    }

    public void setTransitionCondition(ITransitionCondition transitionCondition, int removeCascadeDepth) {
        ITransitionCondition oldCond = this.transitionCondition;
        // Might set it to null
        this.transitionCondition = transitionCondition;

        if (oldCond != null) {
            if (oldCond.equals(transitionCondition)) return;
            oldCond.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasTransitionCondition, oldCond.getUriModelComponentID()));
        }

        if (!(transitionCondition == null)) {
            publishElementAdded(transitionCondition);
            transitionCondition.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasTransitionCondition, transitionCondition.getUriModelComponentID()));
        }
    }

    public void setTransitionCondition(ITransitionCondition transitionCondition) {
        ITransitionCondition oldCond = this.transitionCondition;
        // Might set it to null
        this.transitionCondition = transitionCondition;

        if (oldCond != null) {
            if (oldCond.equals(transitionCondition)) return;
            oldCond.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasTransitionCondition, oldCond.getUriModelComponentID()));
        }

        if (!(transitionCondition == null)) {
            publishElementAdded(transitionCondition);
            transitionCondition.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasTransitionCondition, transitionCondition.getUriModelComponentID()));
        }
    }

    public IAction getBelongsToAction() {
        return belongsToAction;
    }


    public IState getSourceState() {
        return sourceState;
    }


    public IState getTargetState() {
        return targetState;
    }


    public ITransitionCondition getTransitionCondition() {
        return transitionCondition;
    }

    public void setTransitionType(ITransition.TransitionType type) {
        transitionType = type;
    }

    public ITransition.TransitionType getTransitionType() {
        return transitionType;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
            return true;
        else if (element != null) {
            if (predicate.contains(OWLTags.belongsTo) && element instanceof IAction action) {
                setBelongsToAction(action);
                return true;
            } else if (predicate.contains(OWLTags.hasTransitionCondition) && element instanceof ITransitionCondition condition) {
                setTransitionCondition(condition);
                return true;
            } else if (element instanceof IState state) {


                if (predicate.contains(OWLTags.hasSourceState)) {
                    setSourceState(state);
                    return true;
                } else if (predicate.contains(OWLTags.hasTargetState)) {
                    setTargetState(state);
                    return true;
                } else if (element instanceof ISimple2DVisualizationPathPoint point) {
                    //Console.WriteLine(this.getModelComponentID() + ": PathPoint:" + point.getModelComponentID());
                    if (this.pathPoints == null) this.pathPoints = new ArrayList<ISimple2DVisualizationPathPoint>();

                    this.pathPoints.add(point);
                }

            }

        } else {
            if (predicate.contains(OWLTags.type)) {
                if (objectContent.toLowerCase().contains(ITransition.TransitionType.Finalized.toString().toLowerCase())) {
                    setTransitionType(ITransition.TransitionType.Finalized);
                    setIsAbstract(true);
                    return true;
                } else if (objectContent.toLowerCase().contains(ITransition.TransitionType.Precedence.toString().toLowerCase())) {
                    setTransitionType(ITransition.TransitionType.Precedence);
                    setIsAbstract(true);
                    return true;
                } else if (objectContent.toLowerCase().contains(ITransition.TransitionType.Trigger.toString().toLowerCase())) {
                    setTransitionType(ITransition.TransitionType.Trigger);
                    setIsAbstract(true);
                    return true;
                } else if (objectContent.toLowerCase().contains(ITransition.TransitionType.Advice.toString().toLowerCase())) {
                    setTransitionType(ITransition.TransitionType.Advice);
                    setIsAbstract(true);
                    return true;
                } else if (objectContent.contains(ABSTRACT_NAME)) {
                    setIsAbstract(true);
                    return true;
                }
            } else if (predicate.contains(OWLTags.abstrHas2DPageRatio)) {
                set2DPageRatio(Double.parseDouble(objectContent));
                return true;
            } else if (predicate.contains(OWLTags.abstrHasRelative2D_BeginX)) {
                setRelative2DBeginX(Double.parseDouble(objectContent));
                return true;
            } else if (predicate.contains(OWLTags.abstrHasRelative2D_BeginY)) {
                setRelative2DBeginY(Double.parseDouble(objectContent));
                return true;
            } else if (predicate.contains(OWLTags.abstrHasRelative2D_EndY)) {
                setRelative2DEndY(Double.parseDouble(objectContent));
                return true;
            } else if (predicate.contains(OWLTags.abstrHasRelative2D_EndX)) {
                setRelative2DEndX(Double.parseDouble(objectContent));
                return true;
            }

        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getBelongsToAction() != null && specification == ConnectedElementsSetSpecification.ALL || specification == ConnectedElementsSetSpecification.TO_ADD)
            baseElements.add(getBelongsToAction());
        if (specification != ConnectedElementsSetSpecification.TO_ALWAYS_REMOVE) {
            if (getSourceState() != null)
                baseElements.add(getSourceState());
            if (getTargetState() != null)
                baseElements.add(getTargetState());
        }
        if (getTransitionCondition() != null)
            baseElements.add(getTransitionCondition());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IAction action && action.equals(getBelongsToAction()))
                setBelongsToAction(null, removeCascadeDepth);
            if (update instanceof IState state) {
                if (state.equals(getSourceState()))
                    setSourceState(null, removeCascadeDepth);
                if (state.equals(getTargetState()))
                    setTargetState(null, removeCascadeDepth);
            }
            if (update instanceof ITransitionCondition condition && condition.equals(getTransitionCondition()))
                setTransitionCondition(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IAction action && action.equals(getBelongsToAction()))
                setBelongsToAction(null, 0);
            if (update instanceof IState state) {
                if (state.equals(getSourceState()))
                    setSourceState(null, 0);
                if (state.equals(getTargetState()))
                    setTargetState(null, 0);
            }
            if (update instanceof ITransitionCondition condition && condition.equals(getTransitionCondition()))
                setTransitionCondition(null, 0);
        }
    }

    @Override
    public void updateAdded(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        if (update != null) {
            if (!(caller == null) && caller.equals(getSourceState())) {
                if (update instanceof IAction action) {
                    setBelongsToAction(action);
                }
            }
        }
    }


    public void setIsAbstract(boolean isAbstract) {
        this.isAbstractType = isAbstract;
        if (isAbstract) {
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        } else {
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
    }

    public boolean isAbstract() {
        return isAbstractType;
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

    public void setImplementedInterfaces(Set<ITransition> implementedInterface, int removeCascadeDepth) {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
    }

    public void setImplementedInterfaces(Set<ITransition> implementedInterface) {
        implCapsule.setImplementedInterfaces(implementedInterface, 0);
    }

    public void addImplementedInterface(ITransition implementedInterface) {
        implCapsule.addImplementedInterface(implementedInterface);
    }

    public void removeImplementedInterfaces(String id, int removeCascadeDepth) {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
    }

    public void removeImplementedInterfaces(String id) {
        implCapsule.removeImplementedInterfaces(id, 0);
    }

    public Map<String, ITransition> getImplementedInterfaces() {
        return implCapsule.getImplementedInterfaces();
    }

    public List<ISimple2DVisualizationPathPoint> getSimple2DPathPoints() {
        return this.pathPoints;
    }

    public void addSimple2DPathPoint(ISimple2DVisualizationPathPoint point) {
        this.pathPoints.add(point);
    }

    public double get2DPageRatio() {
        return has2DPageRatio;
    }

    public void set2DPageRatio(double has2DPageRatio) {
        if (has2DPageRatio >= 0) {
            this.has2DPageRatio = has2DPageRatio;
        } else {
            throw new IllegalArgumentException("has2DPageRatio" + "Value must be a positive double or 0.");
        }
    }

    public double getRelative2DBeginX() {
        return hasRelative2D_BeginX;
    }

    public void setRelative2DBeginX(double relative2DBeginX) {
        if (relative2DBeginX >= 0 && relative2DBeginX <= 1) {
            hasRelative2D_BeginX = relative2DBeginX;
        } else {
            throw new IllegalArgumentException("relative2DBeginX" + "Value must be between 0 and 1 (inclusive).");
        }
    }

    public double getRelative2DBeginY() {
        return hasRelative2D_BeginY;
    }

    public void setRelative2DBeginY(double relative2DBeginY) {
        if (relative2DBeginY >= 0 && relative2DBeginY <= 1) {
            hasRelative2D_BeginY = relative2DBeginY;
        } else {
            throw new IllegalArgumentException("relative2DBeginY" + "Value must be between 0 and 1 (inclusive).");
        }
    }

    public double getRelative2DEndX() {
        return hasRelative2D_EndX;
    }

    public void setRelative2DEndX(double relative2DEndX) {
        if (relative2DEndX >= 0 && relative2DEndX <= 1) {
            hasRelative2D_EndX = relative2DEndX;
        } else {
            throw new IllegalArgumentException("relative2DEndX" + "Value must be between 0 and 1 (inclusive).");
        }
    }

    public double getRelative2DEndY() {
        return hasRelative2D_EndY;
    }

    public void setRelative2DEndY(double relative2DEndY) {
        if (relative2DEndY >= 0 && relative2DEndY <= 1) {
            hasRelative2D_EndY = relative2DEndY;
        } else {
            throw new IllegalArgumentException("relative2DEndY" + "Value must be between 0 and 1 (inclusive).");
        }
    }
}
