package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions.IMessageExchangeCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a communication transition
 */
public class CommunicationTransition extends Transition implements ICommunicationTransition {
    /**
     * The name of the class
     */
    private final String className = "CommunicationTransition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new CommunicationTransition();
    }

    protected CommunicationTransition() {
    }

    public CommunicationTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                                   ITransition.TransitionType transitionType, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public CommunicationTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
    }

    public CommunicationTransition(ISubjectBehavior behavior, String label,
                                   IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                                   ITransition.TransitionType transitionType,
                                   String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, label, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public CommunicationTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
    }

    public IMessageExchangeCondition getTransitionCondition() {
        return (IMessageExchangeCondition) transitionCondition;
    }

    public void setTransitionCondition(ITransitionCondition condition, int removeCascadeDepth) {
        if (condition instanceof IMessageExchangeCondition messageCondition)
            super.setTransitionCondition(messageCondition);
    }

    public void setTransitionCondition(ITransitionCondition condition) {
        if (condition instanceof IMessageExchangeCondition messageCondition)
            super.setTransitionCondition(messageCondition);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.hasTransitionCondition) && element instanceof ITransitionCondition condition) {
                setTransitionCondition(condition);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

}