package alps.java.LibraryExample;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents.DataObjectDefinitions.ISubjectDataDefinition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IInputPoolConstraint;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subjects.FullySpecifiedSubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.ISubjectBaseBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * This class extends the FullySpecifiedSubject with new functionality.
 * It should be parsed instead of the standard FullySpecifiedSubject implementation in the library
 */
public class AdditionalFunctionalityFullySpecifiedSubject extends FullySpecifiedSubject implements IAdditionalFunctionalityElement {
    private String additionalFunctionality;

    /**
     * Protected Constructor for getParsedInstance(), so no parameters will be set here or in base implementation
     */
    protected AdditionalFunctionalityFullySpecifiedSubject() {
    }

    /**
     * All parameters are nullable or define a default value, the base constructor is used
     *
     * @param additionalFunctionality
     */
    public AdditionalFunctionalityFullySpecifiedSubject(IModelLayer layer, String additionalFunctionality, String labelForID, Set<IMessageExchange> incomingMessageExchange,
                                                        ISubjectBaseBehavior subjectBaseBehavior, Set<ISubjectBehavior> subjectBehaviors,
                                                        Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction, ISubjectDataDefinition subjectDataDefinition,
                                                        Set<IInputPoolConstraint> inputPoolConstraints, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, incomingMessageExchange, subjectBaseBehavior, subjectBehaviors, outgoingMessageExchange, maxSubjectInstanceRestriction,
                subjectDataDefinition, inputPoolConstraints, comment, additionalLabel, additionalAttribute);
        setAdditionalFunctionality(additionalFunctionality);
    }

    public AdditionalFunctionalityFullySpecifiedSubject(IModelLayer layer) {
        super(layer, null, null, null, null, null, 1,
                null, null, null, null, null);
        setAdditionalFunctionality(null);
    }

    /**
     * Needed to provide fresh instances to the factory.
     * It is called every time an instance for a
     *
     * @return
     */
    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        // Uses the protected constructor, this way we can differ between instances that were parsed (and should not contain any pre-set values)
        // And instances that are created while modeling, and should possibly contain pre-set values.
        return new AdditionalFunctionalityFullySpecifiedSubject();
    }

    public String getAdditionalFunctionality() {
        return additionalFunctionality;
    }

    public void setAdditionalFunctionality(String additionalFunctionality) {
        this.additionalFunctionality = additionalFunctionality;
    }
}
