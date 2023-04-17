package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IState;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.State;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;
import alps.java.api.util.StaticFunctions;
import org.apache.jena.rdf.model.Statement;

import java.util.List;
import java.util.Map;

/**
 * Class that represents a StateReference
 * Because a StateReference acts the same as the class of the state it references,
 * it s not possible to use the StateReference as standalone C#-class.<br></br>
 * Example: SendTransition from a StateReference would not work, because the Transition needs a SendState as Origin.
 * One solution would be creating a new class for each possible State, implementing the IStateReference interface end extending the state -> many new classes.<br></br>
 * The current approach is to move the functionality into the State class. Every state the extends the standard State class can reference other states,
 * to use the functionality the state must be casted to IStateReference.
 * This class is only for parsing reasons (loads references and converts them to states) and should not be used to model!
 */
public class ParsedStateReferenceStub extends State implements IStateReference {
//protected IState referenceState;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "StateReference";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ParsedStateReferenceStub();
    }

    public ParsedStateReferenceStub() {
    }


    public void setReferencesState(IState state, int removeCascadeDepth) {
        return;
    }

    public void setReferencesState(IState state) {
        return;
    }


    public IState getReferencesState() {
        return null;
    }

    //TODO: out-Parameter
    public IState transformToState(Map<String, IParseablePASSProcessModelElement> allElements) {
        List<IIncompleteTriple> allTriples = getIncompleteTriples();
        for (Statement t : getTriples())
            allTriples.add(new IncompleteTriple(t));

        for (IIncompleteTriple t : allTriples) {
            if (t.getPredicate().toString().contains(OWLTags.references)) {
                String objID = StaticFunctions.removeBaseUri(t.getObject().toString(), null);
                if (allElements.getOrDefault(objID, out IParseablePASSProcessModelElement element)) {
                    IState state = (IState) element.getParsedInstance();

                    if (state instanceof IParseablePASSProcessModelElement parseable)
                        parseable.addTriples(allTriples);
                    state.setModelComponentID(getModelComponentID());
                    if (state instanceof IStateReference reference)
                        reference.setReferencedState((IState) element);
                    return state;
                }
            }
        }
        return null;
    }
}
