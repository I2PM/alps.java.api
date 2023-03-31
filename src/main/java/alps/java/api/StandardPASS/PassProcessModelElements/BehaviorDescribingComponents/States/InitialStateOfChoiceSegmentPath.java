package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;

/**
 * Class that represents InitialStateOfChoiceSegmentPath
 */
public class InitialStateOfChoiceSegmentPath extends State implements IInitialStateOfChoiceSegmentPath
        {
protected IChoiceSegmentPath choiceSegmentPath;
/**
 * Name of the class, needed for parsing
 */
private final String className = "InitialStateOfChoiceSegmentPath";
@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new InitialStateOfChoiceSegmentPath();
        }

protected InitialStateOfChoiceSegmentPath() { }
public InitialStateOfChoiceSegmentPath(ISubjectBehavior behavior, string labelForID = null, IGuardBehavior guardBehavior = null,
                                       IFunctionSpecification functionSpecification = null,
                                       ISet<ITransition> incomingTransition = null, ISet<ITransition> outgoingTransition = null,
                                       IChoiceSegmentPath choiceSegmentPath = null, string comment = null, string additionalLabel = null, IList<IIncompleteTriple> additionalAttribute = null)
        : base(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute)
        {
        setBelongsToChoiceSegmentPath(choiceSegmentPath);
        }

public void setBelongsToChoiceSegmentPath(IChoiceSegmentPath choiceSegmentPath)
        {
        IChoiceSegmentPath oldPath = this.choiceSegmentPath;
        // Might set it to null
        this.choiceSegmentPath = choiceSegmentPath;

        if (oldPath != null)
        {
        if (oldPath.Equals(choiceSegmentPath)) return;
        oldPath.unregister(this);
        removeTriple(new IncompleteTriple(OWLTags.stdBelongsTo, oldPath.getModelComponentID()));
        }

        if (!(choiceSegmentPath is null))
        {
        publishElementAdded(choiceSegmentPath);
        choiceSegmentPath.register(this);
        addTriple(new IncompleteTriple(OWLTags.stdBelongsTo, choiceSegmentPath.getModelComponentID()));
        }
        }


public IChoiceSegmentPath getChoiceSegmentPath()
        {
        return choiceSegmentPath;
        }


protected override bool parseAttribute(string predicate, string objectContent, string lang, string dataType, IParseablePASSProcessModelElement element)
        {
        if (element != null)
        {
        if (predicate.Contains(OWLTags.belongsTo) && element is IChoiceSegmentPath path)
        {
        setBelongsToChoiceSegmentPath(path);
        return true;
        }
        }
        return base.parseAttribute(predicate, objectContent, lang, dataType, element);
        }


public override ISet<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification)
        {
        ISet<IPASSProcessModelElement> baseElements = base.getAllConnectedElements(specification);
        if (getChoiceSegmentPath() != null)
        baseElements.Add(getChoiceSegmentPath());
        return baseElements;
        }

        }
