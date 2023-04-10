package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.IPayloadDescription;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that contains a certain message specification
 */
public class MessageSpecification extends InteractionDescribingComponent implements IMessageSpecification {
    protected IPayloadDescription payloadDescription;
    protected String message;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageSpecification";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageSpecification();
    }

    protected MessageSpecification() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param payloadDescription
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public MessageSpecification(IModelLayer layer, String labelForID, IPayloadDescription payloadDescription,
                                String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setContainedPayloadDescription(payloadDescription);
    }

    public MessageSpecification(IModelLayer layer) {
        super(layer, null, null, null, null);
        setContainedPayloadDescription(null);
    }

    public void setContainedPayloadDescription(IPayloadDescription payloadDescription, int removeCascadeDepth) {
        IPayloadDescription oldDescription = this.payloadDescription;
        // Might set it to null
        this.payloadDescription = payloadDescription;

        if (oldDescription != null) {
            if (oldDescription.equals(payloadDescription)) return;
            oldDescription.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContainsPayloadDescription, oldDescription.getUriModelComponentID()));
        }

        if (!(payloadDescription == null)) {
            publishElementAdded(payloadDescription);
            payloadDescription.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContainsPayloadDescription, payloadDescription.getUriModelComponentID()));
        }
    }

    public void setContainedPayloadDescription(IPayloadDescription payloadDescription) {
        IPayloadDescription oldDescription = this.payloadDescription;
        // Might set it to null
        this.payloadDescription = payloadDescription;

        if (oldDescription != null) {
            if (oldDescription.equals(payloadDescription)) return;
            oldDescription.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContainsPayloadDescription, oldDescription.getUriModelComponentID()));
        }

        if (!(payloadDescription == null)) {
            publishElementAdded(payloadDescription);
            payloadDescription.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContainsPayloadDescription, payloadDescription.getUriModelComponentID()));
        }
    }

    public IPayloadDescription getContainedPayloadDescription() {
        return payloadDescription;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.containsPayloadDescription) && element instanceof IPayloadDescription description) {
                setContainedPayloadDescription(description);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getContainedPayloadDescription() != null) baseElements.add(getContainedPayloadDescription());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IPayloadDescription payload && payload.equals(getContainedPayloadDescription())) {
                // Try to remove the incoming exchange
                setContainedPayloadDescription(null, removeCascadeDepth);
            }
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IPayloadDescription payload && payload.equals(getContainedPayloadDescription())) {
                // Try to remove the incoming exchange
                setContainedPayloadDescription(null, 0);
            }
        }
    }
}