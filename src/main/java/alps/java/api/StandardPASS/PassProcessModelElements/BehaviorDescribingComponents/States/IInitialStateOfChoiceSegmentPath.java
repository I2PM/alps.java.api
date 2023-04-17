package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;

/**
 * Interface to the InitialStateOfChoiceSegmentPath class
 */
public interface IInitialStateOfChoiceSegmentPath extends IState {
    /**
     * Sets the choice segment path that contains this state as initial state
     *
     * @param choiceSegmentPath the choice segment path
     */
    void setBelongsToChoiceSegmentPath(IChoiceSegmentPath choiceSegmentPath);

    /**
     * Gets the choice segment path that contains this state as initial state
     *
     * @return the choice segment path
     */
    IChoiceSegmentPath getChoiceSegmentPath();
}