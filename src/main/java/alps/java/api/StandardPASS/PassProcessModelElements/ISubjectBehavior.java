package alps.java.api.StandardPASS.PassProcessModelElements;

import alps.java.api.ALPS.ALPSModelElements.*;
import alps.java.api.StandardPASS.*;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.util.*;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the Subject behavior class
 */
public interface ISubjectBehavior extends IPASSProcessModelElement, IContainableElement<IModelLayer>,
        IImplementingElementT<ISubjectBehavior>, IExtendingElement<ISubjectBehavior>, IPrioritizableElement {

// ######################## BehaviorDescribingComponents methods ########################

    /**
     * Adds an {@link IBehaviorDescribingComponent} to the current Subject Behavior.
     *
     * @param component the component that is being added
     * @return
     */
    boolean addBehaviorDescribingComponent(IBehaviorDescribingComponent component);

    /**
     * Sets all BehaviorDescribingComponents contained by the behavior.
     * Overwrites all components contained before.
     *
     * @param components         The new components that will be set
     * @param removeCascadeDepth
     */
    void setBehaviorDescribingComponents(Set<IBehaviorDescribingComponent> components, int removeCascadeDepth);

    /**
     * Sets all BehaviorDescribingComponents contained by the behavior.
     * Overwrites all components contained before.
     *
     * @param components The new components that will be set
     */
    void setBehaviorDescribingComponents(Set<IBehaviorDescribingComponent> components);

    /**
     * Removes a BehaviorDescribingComponent from the SubjectBehavior
     *
     * @param id                 the modelComponentID of the component that should be removed
     * @param removeCascadeDepth
     * @return
     */
    boolean removeBehaviorDescribingComponent(String id, int removeCascadeDepth);

    /**
     * Removes a BehaviorDescribingComponent from the SubjectBehavior
     *
     * @param id the modelComponentID of the component that should be removed
     * @return
     */
    boolean removeBehaviorDescribingComponent(String id);

    /**
     * Method that returns the behavior description component attribute of the instance
     *
     * @return The behavior description component attribute of the instance
     */
    Map<String, IBehaviorDescribingComponent> getBehaviorDescribingComponents();


    // ######################## Other getter and setter methods ########################


    /**
     * Method that sets the initial state of behaviors attribute of the instance
     *
     * @param initialStateOfBehavior
     * @param removeCascadeDepth
     */
    void setInitialState(IState initialStateOfBehavior, int removeCascadeDepth);

    /**
     * Method that sets the initial state of behaviors attribute of the instance
     *
     * @param initialStateOfBehavior
     */
    void setInitialState(IState initialStateOfBehavior);

    /**
     * Method that returns the initial state of behaviors attribute of the instance
     *
     * @return The initial state of behaviors attribute of the instance
     */
    IState getInitialStateOfBehavior();

    /**
     * @param subj
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setSubject(ISubject subj, int removeCascadeDepth);

    /**
     * @param subj
     */
    void setSubject(ISubject subj);

    /**
     * Method that gives access to the Subject the current behavior is connected with
     *
     * @return The subject this behavior is connected with
     */
    ISubject getSubject();
}