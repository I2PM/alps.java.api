package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;


import alps.java.api.StandardPASS.IAbstractElement;
import alps.java.api.StandardPASS.IExtendingElement;
import alps.java.api.StandardPASS.IImplementingElementT;
import alps.java.api.StandardPASS.PassProcessModelElements.IInteractionDescribingComponent;

import java.util.Map;
import java.util.Set;

/**
 * Interface to the subject class
 */
public interface ISubject extends IInteractionDescribingComponent, IImplementingElementT<ISubject>, IExtendingElement<ISubject>, IAbstractElement {

    /**
     * Adds a MessageExchange to the list of incoming message exchanges
     *
     * @param exchange The new incoming exchange
     */
    public void addIncomingMessageExchange(IMessageExchange exchange);

    /**
     * Adds a MessageExchange to the list of outgoing message exchanges
     *
     * @param exchange The new outgoing exchange
     */
    public void addOutgoingMessageExchange(IMessageExchange exchange);

    /**
     * Provides all incoming MessageExchanges mapped with their model component ids
     *
     * @return A map of incoming message exchanges
     */
    public Map<String, IMessageExchange> getIncomingMessageExchanges();

    /**
     * Provides all outgoing MessageExchanges mapped with their model component ids
     *
     * @return A dictionary of outgoing message exchanges
     */
    public Map<String, IMessageExchange> getOutgoingMessageExchanges();

    /**
     * Overrides the set of incoming exchanges
     *
     * @param exchanges
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setIncomingMessageExchanges(Set<IMessageExchange> exchanges, int removeCascadeDepth);

    /**
     * Overrides the set of incoming exchanges
     *
     * @param exchanges
     */
    public void setIncomingMessageExchanges(Set<IMessageExchange> exchanges);

    /**
     * Overrides the set of outgoing exchanges
     *
     * @param exchanges          the set of new outgoing exchange
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void setOutgoingMessageExchanges(Set<IMessageExchange> exchanges, int removeCascadeDepth);

    /**
     * Overrides the set of outgoing exchanges
     *
     * @param exchanges the set of new outgoing exchange
     */
    public void setOutgoingMessageExchanges(Set<IMessageExchange> exchanges);

    /**
     * Removes an incoming exchange
     *
     * @param id                 the id of the exchange
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeIncomingMessageExchange(String id, int removeCascadeDepth);

    /**
     * Removes an incoming exchange
     *
     * @param id the id of the exchange
     */
    public void removeIncomingMessageExchange(String id);

    /**
     * Removes an outgoing exchange
     *
     * @param id                 the id of the exchange
     * @param removeCascadeDepth Parses the depth of a cascading delete for elements that are connected to the currently deleted one
     */
    public void removeOutgoingMessageExchange(String id, int removeCascadeDepth);

    /**
     * Removes an outgoing exchange
     *
     * @param id the id of the exchange
     */
    public void removeOutgoingMessageExchange(String id);

    /**
     * Getter for the instance restriction, defines how often the subject might be instanciated
     *
     * @return the instance restriction
     */
    int getInstanceRestriction();

    /**
     * Setter for the instance restriction, defines how often the subject might be instanciated
     *
     * @param restriction the instance restriction
     */
    void setInstanceRestriction(int restriction);

    /**
     * Represents roles that can be assigned to a subject
     */
    public enum Role {
        StartSubject
    }

    /**
     * Assigns a role to the current subject
     *
     * @param role the role that will be assigned
     */
    void assignRole(Role role);

    /**
     * Checks whether the specified role was assigned to this subject
     *
     * @param role the role that is checked
     * @return true if the subject is assigned to the role, false if not
     */
    boolean isRole(Role role);

    /**
     * Unassignes the specified role from the subject
     *
     * @param role >the role to be removed
     */
    void removeRole(Role role);
}

