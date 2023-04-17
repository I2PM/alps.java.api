package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;

/**
 * Class that represents a transition condition
 */
public class TransitionCondition extends BehaviorDescribingComponent implements ITransitionCondition
        {
protected String toolSpecificDefinition = "";
/**
 * Name of the class, needed for parsing
 */
            private final String className = "TransitionCondition";

@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new TransitionCondition();
        }

protected TransitionCondition() { }

            //TODO: out-Parameter
            public TransitionCondition(ITransition transition){
        super(null, null, null, null, null);
        if (transition != null)
        {
        if (transition.getContainedBy(out ISubjectBehavior behavior))
        setContainedBy(behavior);
        transition.setTransitionCondition(this);
        }
        setToolSpecificDefinition(null);
        }
                public TransitionCondition(ITransition transition, String label, String toolSpecificDefintion, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
                        super(null, label, comment, additionalLabel, additionalAttribute);
                        if (transition != null)
                        {
                                if (transition.getContainedBy(out ISubjectBehavior behavior))
                                        setContainedBy(behavior);
                                transition.setTransitionCondition(this);
                        }
                        setToolSpecificDefinition(toolSpecificDefintion);
                }

//TODO: ? ist nicht dasselbe
public void setToolSpecificDefinition(String toolSpecificDefinition)
        {
        if (toolSpecificDefinition != null && toolSpecificDefinition.equals(this.toolSpecificDefinition)) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasToolSpecificDefinition, this.toolSpecificDefinition, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        this.toolSpecificDefinition = (toolSpecificDefinition == null || toolSpecificDefinition.equals("")) ? null : toolSpecificDefinition;
        if (toolSpecificDefinition != null)
        {
        addTriple(new IncompleteTriple(OWLTags.stdHasToolSpecificDefinition, toolSpecificDefinition, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        }
        }


public String getToolSpecificDefinition()
        {
        return toolSpecificDefinition;
        }
@Override
protected  boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {
        if (predicate.contains(OWLTags.hasToolSpecificDefinition))
        {
        setToolSpecificDefinition(objectContent);
        return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
        }

        }