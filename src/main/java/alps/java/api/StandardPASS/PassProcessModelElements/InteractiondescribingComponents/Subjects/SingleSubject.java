package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a single subject
 */
public class SingleSubject extends Subject implements ISingleSubject {

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "SingleSubject";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new SingleSubject();
    }

    public SingleSubject() {
    }

    public SingleSubject(IModelLayer layer, String labelForID, Set<IMessageExchange> incomingMessageExchange, Set<IMessageExchange> outgoingMessageExchange,
                         String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, incomingMessageExchange,
                outgoingMessageExchange, 1, comment, additionalLabel, additionalAttribute);
    }

    public SingleSubject(IModelLayer layer) {
        super(layer, null, null,
                null, 1, null, null, null);
    }

    public void setInstanceRestriction(int restriction) {
        super.setInstanceRestriction(1);
    }


}
