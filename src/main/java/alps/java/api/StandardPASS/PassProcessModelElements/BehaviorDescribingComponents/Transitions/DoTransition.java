package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IDoState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;

/**
 * Class that represents a DoTransition
 */
public class DoTransition extends Transition implements IDoTransition {
    protected int priorityNumber = 0;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DoTransition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DoTransition();
    }

    protected DoTransition() {
    }

    public DoTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                        ITransition.TransitionType transitionType, int priorityNumber, String comment, String additionalLabel,
                        List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setPriorityNumber(priorityNumber);
    }

    public DoTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
        setPriorityNumber(0);
    }

    public DoTransition(ISubjectBehavior behavior, String label,
                        IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                        ITransition.TransitionType transitionType,
                        int priorityNumber, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, label, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
        setPriorityNumber(priorityNumber);
    }

    public DoTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
        setPriorityNumber(0);
    }

    public void setSourceState(IState state, int removeCascadeDepth) {
        if (state instanceof IDoState) {
            super.setSourceState(state);
        }
    }

    public void setSourceState(IState state) {
        if (state instanceof IDoState) {
            super.setSourceState(state);
        }
    }

    public void setPriorityNumber(int positiveInteger) {
        if (positiveInteger == this.priorityNumber) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, Integer.toString(this.priorityNumber), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
        priorityNumber = (positiveInteger > 0) ? positiveInteger : 1;
        addTriple(new IncompleteTriple(OWLTags.stdHasPriorityNumber, Integer.toString(priorityNumber), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypePositiveInteger));
    }


    public int getPriorityNumber() {
        return priorityNumber;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasPriorityNumber)) {
            String prio = objectContent;
            setPriorityNumber(Integer.parseInt(prio));
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

}
