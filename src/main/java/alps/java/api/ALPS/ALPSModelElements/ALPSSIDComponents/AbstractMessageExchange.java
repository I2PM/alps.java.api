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
 * An abstract message exchange is defined on an abstract layer.
 * It defines a possible message exchange between two subjects. (a recommendation for a message)
 * On an implementing layer though it is allowed to unite the two subjects and thus ignore this communication. (e.g.because in the real process a human being is doing both tasks unitedly
 * In contrast a standard MessageExchange would defined that two subjects (abstract or not) are not to be united.
 */
public class AbstractMessageExchange extends MessageExchange implements IAbstractMessageExchange {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "AbstractMessageExchange";

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new AbstractMessageExchange();
    }

    public AbstractMessageExchange() {
    }

    /**
     * Constructor that creates a fully specified empty instance of the abstract message exchange class
     *
     * @param layer
     * @param labelForId
     * @param messageSpecification the type of message
     * @param senderSubject
     * @param receiverSubject
     * @param comment              the comment
     * @param additionalLabel
     * @param additionalAttribute  list of additional attributes
     */
    //TODO: Konstruktor überladen
    public AbstractMessageExchange(IModelLayer layer, String labelForId, IMessageSpecification messageSpecification, ISubject senderSubject,
                                   ISubject receiverSubject, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForId, messageSpecification, senderSubject, receiverSubject, comment, additionalLabel, additionalAttribute);
    }
    public AbstractMessageExchange(IModelLayer layer) {
        super(layer, null, null, null, null, null, null, null);
    }
    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }
}