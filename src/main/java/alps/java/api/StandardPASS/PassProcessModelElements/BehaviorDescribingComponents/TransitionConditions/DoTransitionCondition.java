package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionCondition;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents an DoTransitionCondtition
 */
public class DoTransitionCondition extends TransitionCondition implements IDoTransitionCondition
        {
/**
 * Name of the class, needed for parsing
 */
            private final String className = "DoTransitionCondition";
@Override
public String getClassName()
        {
        return className;
        }
        @Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new DoTransitionCondition();
        }

protected DoTransitionCondition() { }
public DoTransitionCondition(ITransition transition, String labelForID, String toolSpecificDefintion,
                             String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
        super(transition, labelForID, toolSpecificDefintion,  comment, additionalLabel, additionalAttribute);
}
            public DoTransitionCondition(ITransition transition){
                super(transition, null, null,  null, null, null);
            }
        }
