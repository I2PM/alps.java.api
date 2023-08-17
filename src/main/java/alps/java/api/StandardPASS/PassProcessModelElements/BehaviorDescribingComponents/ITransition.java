package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.IAbstractElement;
import alps.java.api.StandardPASS.IImplementingElementT;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
import alps.java.api.util.IHasSimple2DVisualizationLine;

/**
 * Interface to the transition class
 */
public interface ITransition extends IBehaviorDescribingComponent, IImplementingElementT<ITransition>, IAbstractElement, IHasSimple2DVisualizationLine
        {
            /**
             * enum which describes all the possible states a transition can have
             */
            public enum TransitionType
{
    /**
     * Standart transition type (if no further specification is give, all transitions are standart)
     */
    Standard,
    /**
     * Finalized transition type
     */
    Finalized,
    /**
     * Precedence transition type
     */
    Precedence,
    /**
     * Trigger transition type
     */
    Trigger,
    /**
     * Advice transition type
     */
    Advice
}

            /**
             * Method that returns the action attribute of the instance
             * @return The action attribute of the instance
             */
    IAction getBelongsToAction();

            /**
             * Method that sets the source state (where the transition is coming from)
             * @param sourceState the source state
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
    void setSourceState(IState sourceState, int removeCascadeDepth );

            /**
             * Method that sets the source state (where the transition is coming from)
             * @param sourceState the source state
             */
            void setSourceState(IState sourceState);

            /**
             * Method that returns the source state (where the transition is coming from)
             * @return The source state attribute of the instance
             */
    IState getSourceState();

            /**
             * Method that sets the target state (where the transition is going)
             * @param targetState
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
    void setTargetState(IState targetState, int removeCascadeDepth);

            /**
             * Method that sets the target state (where the transition is going)
             * @param targetState
             */
            void setTargetState(IState targetState);

            /**
             * Method that returns the target state (where the transition is going)
             * @return The target state attribute of the instance
             */
    IState getTargetState();

            /**
             * Method that sets the transition condition attribute of the instance
             * @param transitionCondition the transition condition
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
    void setTransitionCondition(ITransitionCondition transitionCondition, int removeCascadeDepth);

            /**
             * Method that sets the transition condition attribute of the instance
             * @param transitionCondition the transition condition
             */
            void setTransitionCondition(ITransitionCondition transitionCondition);

            /**
             * Method that returns the transition condition attribute of the instance
             * @return The transition condition attribute of the instance
             */
    ITransitionCondition getTransitionCondition();

            /**
             * Sets a type for the current instance
             * @param type The type
             */
    void setTransitionType(TransitionType type);

            /**
             * Returns the current type of the transition
             * @return the current type
             */
    TransitionType getTransitionType();

}