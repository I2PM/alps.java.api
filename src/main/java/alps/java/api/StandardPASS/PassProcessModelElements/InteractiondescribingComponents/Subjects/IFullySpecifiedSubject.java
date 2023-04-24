package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinitions.ISubjectDataDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the FullySpecifiedSubject class
 */

public interface IFullySpecifiedSubject extends ISubject {
    /**
     * Sets a behavior as BaseBehavior for this subject.
     * If the behavior is not contained in the list of behaviors, it is also added to the list of normal behaviors.
     *
     * @param subjectBaseBehavior The new BaseBehavior
     * @param removeCascadeDepth  Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setBaseBehavior(ISubjectBehavior subjectBaseBehavior, int removeCascadeDepth);

    /**
     * Sets a behavior as BaseBehavior for this subject.
     * If the behavior is not contained in the list of behaviors, it is also added to the list of normal behaviors.
     *
     * @param subjectBaseBehavior The new BaseBehavior
     */
    void setBaseBehavior(ISubjectBehavior subjectBaseBehavior);

    /**
     * Returns the current BaseBehavior.
     *
     * @return the current BaseBehavior
     */
    ISubjectBehavior getSubjectBaseBehavior();

    /**
     * Adds a behavior to the current subject.
     *
     * @param behavior The behavior
     * @return a bool indicating whether the process of adding was a success
     */
    boolean addBehavior(ISubjectBehavior behavior);

    /**
     * Sets a set of behaviors as Behaviors for this subject, overwriting old behaviors.
     *
     * @param behaviors          The set of behaviors
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setBehaviors(Set<ISubjectBehavior> behaviors, int removeCascadeDepth);

    /**
     * Sets a set of behaviors as Behaviors for this subject, overwriting old behaviors.
     *
     * @param behaviors The set of behaviors
     */
    void setBehaviors(Set<ISubjectBehavior> behaviors);

    /**
     * Removes a behavior from the list of behaviors
     *
     * @param id                 the id of the behavior
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     * @return a bool indicating whether the process of removal was a success
     */
    boolean removeBehavior(String id, int removeCascadeDepth);

    /**
     * Removes a behavior from the list of behaviors
     *
     * @param id the id of the behavior
     * @return a bool indicating whether the process of removal was a success
     */
    boolean removeBehavior(String id);

    /**
     * Get all behaviors mapped with their ids
     *
     * @return A map of behaviors
     */
    Map<String, ISubjectBehavior> getBehaviors();

    /**
     * Sets the Data Definition for this subject
     *
     * @param subjectDataDefinition the Data Definition
     * @param removeCascadeDepth    Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setDataDefintion(ISubjectDataDefinition subjectDataDefinition, int removeCascadeDepth);

    /**
     * Sets the Data Definition for this subject
     *
     * @param subjectDataDefinition the Data Definition
     */
    void setDataDefintion(ISubjectDataDefinition subjectDataDefinition);

    /**
     * Returns the Data Definition for this subject
     *
     * @return the Data Definition
     */
    ISubjectDataDefinition getSubjectDataDefinition();

    /**
     * Adds an input pool constraint to the list of input pool constraints
     *
     * @param constraint
     * @return a bool indicating whether the process of adding was a success
     */
    boolean addInputPoolConstraint(IInputPoolConstraint constraint);

    /**
     * Overrides the set of input pool constraints
     *
     * @param constraints        the new constraints
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setInputPoolConstraints(Set<IInputPoolConstraint> constraints, int removeCascadeDepth);

    /**
     * Overrides the set of input pool constraints
     *
     * @param constraints the new constraints
     */
    void setInputPoolConstraints(Set<IInputPoolConstraint> constraints);

    /**
     * Removes a specified constraint
     *
     * @param id                 the id of the input pool constraint
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     * @return a bool indicating whether the process of removal was a success
     */
    boolean removeInputPoolConstraint(String id, int removeCascadeDepth);

    /**
     * Removes a specified constraint
     *
     * @param id the id of the input pool constraint
     * @return a bool indicating whether the process of removal was a success
     */
    boolean removeInputPoolConstraint(String id);

    /**
     * Returns the input pool constraints mapped with their ids
     *
     * @return A dictionary of input pool constraints
     */
    Map<String, IInputPoolConstraint> getInputPoolConstraints();

}

