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

public class MacroExtension extends SubjectExtension implements IMacroExtension {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MacroExtension";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MacroExtension();
    }

    protected MacroExtension() {
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
    public MacroExtension(IModelLayer layer, String labelForID, ISubject extendedSubject, Set<ISubjectBehavior> extensionBehavior,
                          Set<IMessageExchange> incomingMessageExchange, Set<IMessageExchange> outgoingMessageExchange,
                          int maxSubjectInstanceRestriction, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, extendedSubject, extensionBehavior, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction, comment, additionalLabel, additionalAttribute);
    }
        public MacroExtension(IModelLayer layer) {
                super(layer, null, null, null, null, null, 1, null, null, null);
        }
}