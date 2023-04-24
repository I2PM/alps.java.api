package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents an message exchange list
 */
public class MessageExchangeList extends InteractionDescribingComponent implements IMessageExchangeList {
    protected ICompatibilityDictionary<String, IMessageExchange> messageExchanges = new CompatibilityDictionary<String, IMessageExchange>();

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageExchangeList";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageExchangeList();
    }

    protected MessageExchangeList() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param messageExchanges
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public MessageExchangeList(IModelLayer layer, String labelForID, Set<IMessageExchange> messageExchanges, String comment, String additionalLabel,
                               List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setContainsMessageExchanges(messageExchanges);

    }

    public MessageExchangeList(IModelLayer layer) {
        super(layer, null, null, null, null);
        setContainsMessageExchanges(null);

    }


    public void addContainsMessageExchange(IMessageExchange messageExchange) {
        if (messageExchange == null) {
            return;
        }
        if (messageExchanges.tryAdd(messageExchange.getModelComponentID(), messageExchange)) {
            publishElementAdded(messageExchange);
            messageExchange.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, messageExchange.getUriModelComponentID()));
        }
    }


    public void setContainsMessageExchanges(Set<IMessageExchange> messageExchanges, int removeCascadeDepth) {
        for (IMessageExchange messageExchange : getMessageExchanges().values()) {
            removeMessageExchange(messageExchange.getModelComponentID(), removeCascadeDepth);
        }
        if (messageExchanges == null) return;
        for (IMessageExchange messageExchange : messageExchanges) {
            addContainsMessageExchange(messageExchange);
        }
    }

    public void setContainsMessageExchanges(Set<IMessageExchange> messageExchanges) {
        for (IMessageExchange messageExchange : getMessageExchanges().values()) {
            removeMessageExchange(messageExchange.getModelComponentID(), 0);
        }
        if (messageExchanges == null) return;
        for (IMessageExchange messageExchange : messageExchanges) {
            addContainsMessageExchange(messageExchange);
        }
    }

    public void removeMessageExchange(String id, int removeCascadeDepth) {
        if (id == null) return;
        IMessageExchange exchange = messageExchanges.get(id);
        if (exchange!=null) {
            messageExchanges.remove(id);
            exchange.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, exchange.getUriModelComponentID()));
        }
    }

    public void removeMessageExchange(String id) {
        if (id == null) return;
        IMessageExchange exchange = messageExchanges.get(id);
        if (exchange!=null) {
            messageExchanges.remove(id);
            exchange.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, exchange.getUriModelComponentID()));
        }
    }

    public Map<String, IMessageExchange> getMessageExchanges() {
        return new HashMap<String, IMessageExchange>(messageExchanges);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.ccontains) && element instanceof IMessageExchange exchange) {
                addContainsMessageExchange(exchange);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (messageExchanges.containsKey(oldID)) {
            IMessageExchange element = messageExchanges.get(oldID);
            messageExchanges.remove(oldID);
            messageExchanges.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IMessageExchange exchange : getMessageExchanges().values()) baseElements.add(exchange);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IMessageExchange exchange) {
                // Try to remove the incoming exchange
                removeMessageExchange(exchange.getModelComponentID(), removeCascadeDepth);
            }
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IMessageExchange exchange) {
                // Try to remove the incoming exchange
                removeMessageExchange(exchange.getModelComponentID(), 0);
            }
        }
    }
}