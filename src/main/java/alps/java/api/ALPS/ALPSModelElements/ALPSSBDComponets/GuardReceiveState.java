package alps.java.api.ALPS.ALPSModelElements.ALPSSBDComponets;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IReceiveFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.ReceiveState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.IReceiveTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

public class GuardReceiveState extends ReceiveState implements IGuardReceiveState {
    /**
     * Name of the class, needed for parsing
     */
    private final String CLASS_NAME = "GuardReceiveState";

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
        return new GuardReceiveState();
    }

    protected GuardReceiveState() {
    }

    /**
     * Constructor that creates a new fully specified instance of the guard receive state class
     *
     * @param behavior
     * @param labelForId            a string describing this element which is used to generate the unique model component id
     * @param guardBehavior
     * @param functionSpecification
     * @param incomingTransition
     * @param outgoingTransition
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    //TODO: Konstruktor überladen
    public GuardReceiveState(ISubjectBehavior behavior, String labelForId, IGuardBehavior guardBehavior,
                             IReceiveFunction functionSpecification,
                             Set<ITransition> incomingTransition, Set<IReceiveTransition> outgoingTransition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForId, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
    }

}