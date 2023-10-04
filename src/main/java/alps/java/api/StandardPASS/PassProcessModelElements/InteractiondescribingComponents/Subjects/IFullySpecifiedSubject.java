package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinitions.ISubjectDataDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubjectExecutionMapping;

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

    /**
     * Returns an object containing a subject execution mapping
     *
     * @return
     */
    ISubjectExecutionMapping getSubjectExecutionMapping();

    /**
     * Returns an object containing a subject execution mapping
     *
     * @param subjectExecutionMapping a reference to the accoring execution mapping object
     */
    void setSubjectExecutionMapping(ISubjectExecutionMapping subjectExecutionMapping);


    /**
     * For simple simulation the costs that one subject has per hour of execution
     */
    double getSisiExecutionCostPerHour();

    /**
     * For simple simulation the costs that one subject has per hour of execution
     */
    void setSisiExecutionCostPerHour(double sisiExecutionCostPerHour);

    /**
     * Define what type of Subject this is. Standard; Production Subject, Storage Subject
     */
    SimpleSimVSMSubjectTypes getSisiVSMSubjectType();

    /**
     * Define what type of Subject this is. Standard; Production Subject, Storage Subject
     */
    void setSisiVSMSubjectType(SimpleSimVSMSubjectTypes sisiVSMSubjectType);

    double getSisiVSMInventory();

    void setSisiVSMInventory(double sisiVSMInventor);

    /**
     * Enter a number that represents the amout of inventory in that facility.
     * Mind the unit that you have chosen to consider in your VSM analisys and keep
     * it konstant over all Storage Subjects
     */
    double getSisiVSMProcessQuantity();

    /**
     * Enter a number that represents the amout of inventory in that facility.
     * Mind the unit that you have chosen to consider in your VSM analisys and keep
     * it konstant over all Storage Subjects
     */
    void setSisiVSMProcessQuantity(double sisiVSMProcessQuantity);

    double getSisiVSMQualityRate();

    void setSisiVSMQualityRate(double sisiVSMQualityRate);

    double getSisiVSMAvailability();

    void setSisiVSMAvailability(double sisiVSMAvailability);


    /**
     * Message types for Value Stream Mapping Analysis
     * Values should be:  Product Subject, Storage Subject, or Standard
     */
    enum SimpleSimVSMSubjectTypes {
        Standard,
        ProductionSubject,
        StorageSubject
    }
}

