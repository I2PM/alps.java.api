package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints;

import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;

/**
 * Interface to the MessageSenderTypeConstraint Class
 */
public interface IMessageSenderTypeConstraint extends IInputPoolConstraint {
    /**
     * Sets the referenced Message specification
     *
     * @param messageSpecification the referenced Message specification
     * @param removeCascadeDepth   Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReferencedMessageSpecification(IMessageSpecification messageSpecification, int removeCascadeDepth);

    /**
     * Sets the referenced Message specification
     *
     * @param messageSpecification the referenced Message specification
     */
    void setReferencedMessageSpecification(IMessageSpecification messageSpecification);

    /**
     * Gets the referenced Message specification
     *
     * @return the referenced Message specification
     */
    IMessageSpecification getReferencedMessageSpecification();

    /**
     * Sets the referenced subject
     *
     * @param subject            the referenced subject
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReferencedSubject(ISubject subject, int removeCascadeDepth);

    /**
     * Sets the referenced subject
     *
     * @param subject the referenced subject
     */
    void setReferencedSubject(ISubject subject);

    /**
     * Gets the referenced Message subject
     *
     * @return the referenced Message subject
     */
    ISubject getReferencedSubject();

}
