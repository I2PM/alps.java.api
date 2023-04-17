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

    /**
     * Adds a StateReference to the set of contained StateReferences.
     *
     * @param stateReference the new state reference
     */
    void addStateReference(IStateReference stateReference);

    /**
     * Remove a StateReference from the list of current StateReferences
     *
     * @param stateRefID         the id of the reference
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void removeStateReference(String stateRefID, int removeCascadeDepth);

    /**
     * Remove a StateReference from the list of current StateReferences
     *
     * @param stateRefID the id of the reference
     */
    void removeStateReference(String stateRefID);

    /**
     * Overrides all current StateReferences
     *
     * @param references         the new references
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setStateReferences(Set<IStateReference> references, int removeCascadeDepth);

    /**
     * Overrides all current StateReferences
     *
     * @param references the new references
     */
    void setStateReferences(Set<IStateReference> references);

    /**
     * Return all the StateReferences
     *
     * @return all references
     */
    Map<String, IStateReference> getStateReferences();

}