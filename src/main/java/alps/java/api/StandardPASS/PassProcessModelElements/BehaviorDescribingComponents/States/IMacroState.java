package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the macro state class
 * <p>
 * From PASS Doc: A state that references a macro behavior that is executed upon entering this state. Only after executing the macro behavior this state is finished also
 */

public interface IMacroState extends IState {
    /**
     * Sets the macro behavior that is referenced by the macro state
     *
     * @param macroBehavior      the macro behavior
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReferencedMacroBehavior(IMacroBehavior macroBehavior, int removeCascadeDepth);

    /**
     * Sets the macro behavior that is referenced by the macro state
     *
     * @param macroBehavior the macro behavior
     */
    void setReferencedMacroBehavior(IMacroBehavior macroBehavior);

    /**
     * Gets the macro behavior that is referenced by the macro state
     *
     * @return the macro behavior
     */
    IMacroBehavior getReferencedMacroBehavior();



}