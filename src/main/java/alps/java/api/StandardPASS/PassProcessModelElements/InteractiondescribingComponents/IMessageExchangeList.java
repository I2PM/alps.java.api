package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;
import alps.java.api.util.IHasSimple2DVisualizationLine;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the message exchange list class
 */
public interface IMessageExchangeList extends IInteractionDescribingComponent, IHasSimple2DVisualizationLine {
    /**
     * Adds a message exchange to the exchange list
     *
     * @param messageExchange the new message exchange
     */
    void addContainsMessageExchange(IMessageExchange messageExchange);

    /**
     * Overrides the set of contained message exchanges
     *
     * @param messageExchanges   a set of new message exchanges
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    void setContainsMessageExchanges(Set<IMessageExchange> messageExchanges, int removeCascadeDepth);

    /**
     * Overrides the set of contained message exchanges
     *
     * @param messageExchanges a set of new message exchanges
     */
    void setContainsMessageExchanges(Set<IMessageExchange> messageExchanges);

    /**
     * Returns all message exchanges contained by the list, mapped with their ids
     *
     * @return A map containing all message exchanges
     */
    Map<String, IMessageExchange> getMessageExchanges();

    /**
     * Rmoves a message exchange from the list
     *
     * @param id                 the id of the message exchange
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeMessageExchange(String id, int removeCascadeDepth);

    /**
     * Rmoves a message exchange from the list
     *
     * @param id the id of the message exchange
     */
    public void removeMessageExchange(String id);

}