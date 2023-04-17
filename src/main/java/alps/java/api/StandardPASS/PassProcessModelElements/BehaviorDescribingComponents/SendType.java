package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents a send type
 */
public class SendType extends BehaviorDescribingComponent implements ISendType
        {
/**
 * Name of the class, needed for parsing
 */
            private final String className = "SendType";
@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new SendType();
        }

protected SendType() { }

            /**
             *
             * @param behavior
             */
            //TODO: Konstruktor überladen
            public SendType(ISubjectBehavior behavior){
        super(behavior, null, null,null, null);}

            public SendType(ISubjectBehavior behavior, String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
                super(behavior, labelForID, comment, additionalLabel, additionalAttribute);}

        }