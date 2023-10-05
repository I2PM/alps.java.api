package alps.java.api.ALPS.ALPSModelElements.ALPSSBDComponets;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

public class StatePlaceholder extends State implements IStatePlaceholder {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "StatePlaceholder";

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
        return new StatePlaceholder();
    }

    public StatePlaceholder() {
    }

    /**
     * @param behavior
     * @param labelForId
     * @param guardBehavior
     * @param functionSpecification
     * @param incomingTransition
     * @param outgoingTransition
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public StatePlaceholder(ISubjectBehavior behavior, String labelForId, IGuardBehavior guardBehavior,
                            IFunctionSpecification functionSpecification, Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                            String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForId, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
    }

    /**
     * @param behavior
     */
    public StatePlaceholder(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);
    }


}