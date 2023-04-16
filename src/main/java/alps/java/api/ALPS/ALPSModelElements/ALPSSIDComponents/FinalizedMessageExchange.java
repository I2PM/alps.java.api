package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.MessageExchange;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * From abstract pass ont:
 * Exclusive Message Exchange is an abstract concept to be used on abstract layers
 * A finalized message exchange defines that a subject is not allowed to comunicate with the corresponding subject in any other way than this message exchange or similiar messages in the same model in any other way.
 * If an finalized message connection is used on a subject no other normal or abstract Message Exchange is allowed(while Communication Restrictions are not necessary).
 */
public class FinalizedMessageExchange extends MessageExchange implements IFinalizedMessageExchange {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "FinalizedMessageExchange";

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new FinalizedMessageExchange();
    }

    protected FinalizedMessageExchange() {
    }

    public FinalizedMessageExchange(IModelLayer layer, String label, IMessageSpecification messageSpecification, ISubject senderSubject,
                                    ISubject receiverSubject, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, label, messageSpecification, senderSubject, receiverSubject, comment, additionalLabel, additionalAttribute);
    }

    public FinalizedMessageExchange(IModelLayer layer) {
        super(layer, null, null, null, null, null, null, null);
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }
}
