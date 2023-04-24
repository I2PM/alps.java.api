package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.InputPoolConstraints;

import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;

/**
 * Interface to the message type constraint class
 */
public interface IMessageTypeConstraint extends IInputPoolConstraint {
    /**
     * Sets the referenced message specification
     *
     * @param messageSpecification the new message specification
     * @param removeCascadeDepth   Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setReferencedMessageSpecification(IMessageSpecification messageSpecification, int removeCascadeDepth);

    /**
     * Sets the referenced message specification
     *
     * @param messageSpecification the new message specification
     */
    void setReferencedMessageSpecification(IMessageSpecification messageSpecification);

    /**
     * Gets the referenced message specification
     *
     * @return the message specification
     */
    IMessageSpecification getReferencedMessageSpecification();

}
