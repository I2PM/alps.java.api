package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints;

import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

/**
 * Interface to the sender type constraint class
 */
public interface ISenderTypeConstraint extends IInputPoolConstraint {
    /**
     * Method that sets the subject attribute of the instance
     *
     * @param subject            the subject
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReferencesSubject(ISubject subject, int removeCascadeDepth);

    /**
     * Method that sets the subject attribute of the instance
     *
     * @param subject the subject
     */
    void setReferencesSubject(ISubject subject);

    /**
     * Method that returns the subject attribute of the instance
     *
     * @return The subject attribute of the instance
     */
    ISubject getReferenceSubject();
}