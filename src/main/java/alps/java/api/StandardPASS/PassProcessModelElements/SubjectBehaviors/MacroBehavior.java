package alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors;




import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IGenericReturnToOriginReference;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States.IStateReference;
import alps.java.api.StandardPASS.PassProcessModelElements.IBehaviorDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents an Macro behavior of a Subject
 * According to standard pass 1.1.0
 */
public class MacroBehavior extends SubjectBehavior implements IMacroBehavior
        {
/**
 * Name of the class, needed for parsing
 */
            private final String className = "MacroBehavior";

@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new MacroBehavior();
        }

protected MacroBehavior() { }
public MacroBehavior(IModelLayer layer, String labelForID, ISubject subject, Set<IBehaviorDescribingComponent> components,
                     Set<IStateReference> stateReferences, IState initialStateOfBehavior, int priorityNumber, String comment, String additionalLabel,
                     List<IIncompleteTriple> additionalAttribute){
        super(layer, labelForID, subject, components, initialStateOfBehavior, priorityNumber, comment, additionalLabel, additionalAttribute);
        if (stateReferences != null)
        for(IStateReference reference: stateReferences)
        addBehaviorDescribingComponent(reference);
        }

            public MacroBehavior(IModelLayer layer){
                super(layer);
            }
//TODO: neu implementieren OfType
public Map<String, IStateReference> getStateReferences()
        {
        Map<String, IStateReference> output = new HashMap<String, IStateReference>();
        for(Map.Entry<String, IStateReference> pair: getBehaviorDescribingComponents().OfType< Map.Entry<String, IStateReference>>());
        output.put(pair.getKey(), pair.getValue());
        return output;
        //return new Dictionary<string, IStateReference>(getBehaviorDescribingComponents().OfType<KeyValuePair<string, IStateReference>>());
        }

//TODO: Übersetzung für for each Schleife finden (OfType)
public Map<String, IGenericReturnToOriginReference> getReturnReferences()
        {
        Map<String, IGenericReturnToOriginReference> output = new HashMap<String, IGenericReturnToOriginReference>();
        for(Map.Entry<String, IGenericReturnToOriginReference> pair: getBehaviorDescribingComponents().OfType<KeyValuePair<string, IGenericReturnToOriginReference>>())
        output.put(pair.getKey(), pair.getValue());
        return output;
        //return new Dictionary<string, IGenericReturnToOriginReference>(getBehaviorDescribingComponents().OfType<KeyValuePair<string, IGenericReturnToOriginReference>>());
        }



        }

