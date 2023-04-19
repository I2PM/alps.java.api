package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionConditions;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.TransitionCondition;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a sending failed condition
 */
public class SendingFailedCondition extends TransitionCondition implements ISendingFailedCondition
        {
/**
 * Name of the class, needed for parsing
 */
            private final String className = "SendingFailedCondition";
@Override
public String getClassName()
        {
        return className;
        }
        @Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new SendingFailedCondition();
        }

protected SendingFailedCondition() { }
public SendingFailedCondition(ITransition transition, String labelForID, String toolSpecificDefintion ,
                              String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
        super(transition, labelForID,  toolSpecificDefintion, comment, additionalLabel, additionalAttribute); }


        }