package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a user cancel transition
 */
public class UserCancelTransition extends Transition implements IUserCancelTransition {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "UserCancelTransition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new UserCancelTransition();
    }

    protected UserCancelTransition() {
    }

    public UserCancelTransition(IState sourceState, IState targetState, String labelForID, ITransitionCondition transitionCondition,
                                ITransition.TransitionType transitionType, String comment, String additionalLabel,
                                List<IIncompleteTriple> additionalAttribute) {
        super(sourceState, targetState, labelForID, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public UserCancelTransition(IState sourceState, IState targetState) {
        super(sourceState, targetState, null, null, TransitionType.Standard, null, null, null);
    }

    public UserCancelTransition(ISubjectBehavior behavior, String labelForID,
                                IState sourceState, IState targetState, ITransitionCondition transitionCondition,
                                ITransition.TransitionType transitionType,
                                String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);
    }

    public UserCancelTransition(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, TransitionType.Standard, null, null, null);
    }
}
