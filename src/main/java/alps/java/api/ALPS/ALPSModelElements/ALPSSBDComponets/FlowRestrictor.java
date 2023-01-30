package alps.java.api.ALPS.ALPSModelElements.ALPSSBDComponets;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

public class FlowRestrictor extends Transition implements IFlowRestrictor
        {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "FlowRestrictor";

@Override
public String getClassName()
        {
        return CLASS_NAME;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new FlowRestrictor();
        }

protected FlowRestrictor() {  }

            /**
             * The constructor for a FlowRestrictor that is created by passing the referenced states (source and target)
             * @param sourceState
             * @param targetState
             * @param labelForId a string describing this element which is used to generate the unique model component id
             * @param transitionCondition
             * @param transitionType
             * @param comment
             * @param additionalLabel
             * @param additionalAttribute
             */
            public FlowRestrictor(IState sourceState, IState targetState, String labelForId, ITransitionCondition transitionCondition,
                                  ITransition.TransitionType transitionType = ITransition.TransitionType.Standard, String comment, string additionalLabel,
                                  List<IIncompleteTriple> additionalAttribute){
        super(sourceState, targetState, labelForId, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);

        }

            /**
             * The constructor for a FlowRestrictor that is created by passing the parent behavior (source and target state are optional and can be specified later)
             * @param behavior The behavior on which the FlowRestrictor will be created
             * @param labelForId a string describing this element which is used to generate the unique model component id
             * @param sourceState
             * @param targetState
             * @param transitionCondition
             * @param transitionType
             * @param comment
             * @param additionalLabel
             * @param additionalAttribute
             */
            public FlowRestrictor(ISubjectBehavior behavior, String labelForId, IState sourceState, IState targetState,
                                  ITransitionCondition transitionCondition,
                                  ITransition.TransitionType transitionType = ITransition.TransitionType.Standard, String comment, String additionalLabel,
                                  List<IIncompleteTriple> additionalAttribute){
        super(behavior, labelForId, sourceState, targetState, transitionCondition, transitionType, comment, additionalLabel, additionalAttribute);

        }
        }