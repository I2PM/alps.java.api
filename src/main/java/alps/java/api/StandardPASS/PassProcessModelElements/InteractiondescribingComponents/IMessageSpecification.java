package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IPayloadDescription;
import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;

/**
 * Interface for the MessageSpecification class
 */

public interface IMessageSpecification extends IInteractionDescribingComponent
        {
            /**
             * Sets the payload description for the message specification, which describes the payload of the message
             * @param payloadDescription the payload description
             * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
             */
        void setContainedPayloadDescription(IPayloadDescription payloadDescription, int removeCascadeDepth);

            /**
             * Sets the payload description for the message specification, which describes the payload of the message
             * @param payloadDescription the payload description
             */
            void setContainedPayloadDescription(IPayloadDescription payloadDescription);

            /**
             * Returns the payload description for the message specification, which describes the payload of the message
             * @return the payload description
             */
        IPayloadDescription getContainedPayloadDescription();
        }
