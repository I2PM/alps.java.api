package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IPayloadDescription;
import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;
import alps.java.api.util.ISiSiTimeDistribution;

/**
 * Interface for the MessageSpecification class
 */

public interface IMessageSpecification extends IInteractionDescribingComponent {
    /**
     * Sets the payload description for the message specification, which describes the payload of the message
     *
     * @param payloadDescription the payload description
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setContainedPayloadDescription(IPayloadDescription payloadDescription, int removeCascadeDepth);

    /**
     * Sets the payload description for the message specification, which describes the payload of the message
     *
     * @param payloadDescription the payload description
     */
    void setContainedPayloadDescription(IPayloadDescription payloadDescription);

    /**
     * Returns the payload description for the message specification, which describes the payload of the message
     *
     * @return the payload description
     */
    IPayloadDescription getContainedPayloadDescription();

    /**
     * For simple simulation of processes: The (expected) transmission time of this kind of message. Necessary only for simulation purposes
     */
    ISiSiTimeDistribution getSimpleSimTransmissionTime();

    /**
     * For simple simulation of processes: The (expected) transmission time of this kind of message. Necessary only for simulation purposes
     */
    void setSimpleSimTransmissionTime(ISiSiTimeDistribution sisitime);

    /**
     * for values streamm analysisefine what type of Messag this is. Standard;Conveyance Time (internal);Conveyance Time (external);Information Flow (internal);Information Flow (external);
     */
    SimpleSimVSMMessageTypes getSimpleSimVSMMessageType();

    /**
     * for values streamm analysisefine what type of Messag this is. Standard;Conveyance Time (internal);Conveyance Time (external);Information Flow (internal);Information Flow (external);
     */
    void setSimpleSimVSMMessageType(SimpleSimVSMMessageTypes simplesim);
}

/**
 * Message types for Value Stream Mapping Analysis
 * Values should be: Standard;Conveyance Time (internal);Conveyance Time (external);Information Flow (internal);Information Flow (external);
 */
enum SimpleSimVSMMessageTypes {
    Standard,
    ConveyanceTimeInternal,
    ConveyanceTimeExternal,
    InformationFlowInternal,
    InformationFlowExternal
}
