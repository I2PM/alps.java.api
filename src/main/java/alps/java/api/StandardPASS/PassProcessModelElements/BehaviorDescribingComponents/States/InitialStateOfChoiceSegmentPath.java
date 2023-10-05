package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;


import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents InitialStateOfChoiceSegmentPath
 */
public class InitialStateOfChoiceSegmentPath extends State implements IInitialStateOfChoiceSegmentPath {
    protected IChoiceSegmentPath choiceSegmentPath;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "InitialStateOfChoiceSegmentPath";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new InitialStateOfChoiceSegmentPath();
    }

    public InitialStateOfChoiceSegmentPath() {
    }

    public InitialStateOfChoiceSegmentPath(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                                           IFunctionSpecification functionSpecification,
                                           Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition,
                                           IChoiceSegmentPath choiceSegmentPath, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
        setBelongsToChoiceSegmentPath(choiceSegmentPath);
    }

    public InitialStateOfChoiceSegmentPath(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);
        setBelongsToChoiceSegmentPath(null);
    }

    public void setBelongsToChoiceSegmentPath(IChoiceSegmentPath choiceSegmentPath) {
        IChoiceSegmentPath oldPath = this.choiceSegmentPath;
        // Might set it to null
        this.choiceSegmentPath = choiceSegmentPath;

        if (oldPath != null) {
            if (oldPath.equals(choiceSegmentPath)) return;
            oldPath.unregister(this);
            removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldPath.getModelComponentID()));
        }

        if (!(choiceSegmentPath == null)) {
            publishElementAdded(choiceSegmentPath);
            choiceSegmentPath.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, choiceSegmentPath.getModelComponentID()));
        }
    }


    public IChoiceSegmentPath getChoiceSegmentPath() {
        return choiceSegmentPath;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (element != null) {
            if (predicate.contains(OWLTags.belongsTo) && element instanceof IChoiceSegmentPath path) {
                setBelongsToChoiceSegmentPath(path);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getChoiceSegmentPath() != null)
            baseElements.add(getChoiceSegmentPath());
        return baseElements;
    }

}
