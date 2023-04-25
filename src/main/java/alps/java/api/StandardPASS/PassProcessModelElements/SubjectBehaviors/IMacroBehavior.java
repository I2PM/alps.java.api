package alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IGenericReturnToOriginReference;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IStateReference;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;

import java.util.Map;

/**
 * Interface to the macro bahvior class
 */

public interface IMacroBehavior extends ISubjectBehavior {

    /**
     * Method that returns all state references held by the instance
     *
     * @return a list of state references
     */
    Map<String, IStateReference> getStateReferences();

    /**
     * Method that returns all return to origin references held by the instance
     *
     * @return a list of return to origin references
     */
    Map<String, IGenericReturnToOriginReference> getReturnReferences();

}