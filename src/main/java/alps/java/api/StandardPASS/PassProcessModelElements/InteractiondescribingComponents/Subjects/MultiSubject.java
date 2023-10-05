package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents a multi subject
 */
public class MultiSubject extends Subject implements IMultiSubject {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MultiSubject";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MultiSubject();
    }

    public MultiSubject() {
    }

    public MultiSubject(IModelLayer layer) {
        super(layer, null, null, null, -1, null, null, null);
        setInstanceRestriction(2);
    }

    public MultiSubject(IModelLayer layer, String labelForID, Set<IMessageExchange> incomingMessageExchange, Set<IMessageExchange> outgoingMessageExchange,
                        int maxSubjectInstanceRestriction, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, incomingMessageExchange, outgoingMessageExchange, -1, comment, additionalLabel, additionalAttribute);
        setInstanceRestriction(maxSubjectInstanceRestriction);
    }

    public void setInstanceRestriction(int instanceRestriction) {
        if (instanceRestriction >= 2) {
            super.setInstanceRestriction(instanceRestriction);
        } else {
            super.setInstanceRestriction(2);
        }
    }


}
