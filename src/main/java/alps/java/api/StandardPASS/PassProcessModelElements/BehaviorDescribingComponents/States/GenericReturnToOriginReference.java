package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents an GenericReturnToOriginReference
 */
public class GenericReturnToOriginReference extends State implements IGenericReturnToOriginReference
        {
/**
 * Name of the class, needed for parsing
 */
private final String className = "GenericReturnToOriginReference";
@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new GenericReturnToOriginReference();
        }

protected GenericReturnToOriginReference() { }
public GenericReturnToOriginReference(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                                      IFunctionSpecification functionSpecification,
                                      Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
    super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
         }

        }
