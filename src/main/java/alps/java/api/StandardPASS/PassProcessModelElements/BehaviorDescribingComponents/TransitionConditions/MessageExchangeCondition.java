package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionCondition;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents an message exchange conditon
 */
public class MessageExchangeCondition extends TransitionCondition implements IMessageExchangeCondition {
    protected IMessageExchange messageExchange;

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageExchangeCondition";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageExchangeCondition();
    }

    protected MessageExchangeCondition() {
    }

    public MessageExchangeCondition(ITransition transition, String labelForID, String toolSpecificDefinition, IMessageExchange messageExchange,
                                    String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(transition, labelForID, comment, toolSpecificDefinition, additionalLabel, additionalAttribute);
        setRequiresPerformedMessageExchange(messageExchange);
    }

    public MessageExchangeCondition(ITransition transition) {
        super(transition, null, null, null, null, null);
        setRequiresPerformedMessageExchange(null);
    }

    public void setRequiresPerformedMessageExchange(IMessageExchange messageExchange, int removeCascadeDepth) {
        IMessageExchange oldExchange = this.messageExchange;
        // Might set it to null
        this.messageExchange = messageExchange;

        if (oldExchange != null) {
            if (oldExchange.equals(messageExchange)) return;
            oldExchange.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresPerformedMessageExchange, oldExchange.getUriModelComponentID()));
        }

        if (!(messageExchange == null)) {
            publishElementAdded(messageExchange);
            messageExchange.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresPerformedMessageExchange, messageExchange.getUriModelComponentID()));
                /*if (messageExchange.getContainedBy(out IModelLayer layer)
                    setContainedBy(layer);*/
        }
    }

    public void setRequiresPerformedMessageExchange(IMessageExchange messageExchange) {
        IMessageExchange oldExchange = this.messageExchange;
        // Might set it to null
        this.messageExchange = messageExchange;

        if (oldExchange != null) {
            if (oldExchange.equals(messageExchange)) return;
            oldExchange.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdRequiresPerformedMessageExchange, oldExchange.getUriModelComponentID()));
        }

        if (!(messageExchange == null)) {
            publishElementAdded(messageExchange);
            messageExchange.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdRequiresPerformedMessageExchange, messageExchange.getUriModelComponentID()));
                /*if (messageExchange.getContainedBy(out IModelLayer layer)
                    setContainedBy(layer);*/
        }
    }

    public IMessageExchange getRequiresPerformedMessageExchange() {
        return messageExchange;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.requiresPerformedMessageExchange) && element instanceof IMessageExchange exchange) {
                setRequiresPerformedMessageExchange(exchange);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getRequiresPerformedMessageExchange() != null)
            baseElements.add(getRequiresPerformedMessageExchange());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IMessageExchange exchange && exchange.equals(getRequiresPerformedMessageExchange()))
                setRequiresPerformedMessageExchange(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IMessageExchange exchange && exchange.equals(getRequiresPerformedMessageExchange()))
                setRequiresPerformedMessageExchange(null, 0);
        }
    }
}
