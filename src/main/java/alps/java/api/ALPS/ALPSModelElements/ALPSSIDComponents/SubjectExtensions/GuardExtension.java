package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtensions;

import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents.SubjectExtension;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

public class GuardExtension extends SubjectExtension implements IGuardExtension {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "GuardExtension";

    /**
     * Constructor that creates a new empty instance of the communication act class
     *
     * @param layer
     */
    public GuardExtension(IModelLayer layer) {
        super(layer);
    }

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new GuardExtension();
    }

    protected GuardExtension() {
    }

    /**
     * Constructor that creates a new fully specified instance of the subject extension class
     *
     * @param layer
     * @param labelForID
     * @param extendedSubject
     * @param extensionBehavior
     * @param incomingMessageExchange
     * @param outgoingMessageExchange
     * @param maxSubjectInstanceRestriction
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public GuardExtension(IModelLayer layer, String labelForID, ISubject extendedSubject, Set<ISubjectBehavior> extensionBehavior,
                          Set<IMessageExchange> incomingMessageExchange, Set<IMessageExchange> outgoingMessageExchange,
                          int maxSubjectInstanceRestriction, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, extendedSubject, extensionBehavior, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction, comment, additionalLabel, additionalAttribute);
    }

}
