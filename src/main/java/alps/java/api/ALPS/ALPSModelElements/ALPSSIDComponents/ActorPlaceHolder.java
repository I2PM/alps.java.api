package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponent;
import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * From abstract pass ont:
 * An empty model component that may be the (empty) origin or target of a message exchange can later (in implementing layers) be substituted for any other subject
 * Idea if the place is supposed to be left empty
 */
public class ActorPlaceHolder extends ALPSSIDComponent implements IActorPlaceHolder {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ActorPlaceHolder";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ActorPlaceHolder();
    }

    protected ActorPlaceHolder() {
    }

    //TODO: Konstruktor überladen
    public ActorPlaceHolder(IModelLayer layer, String labelForID, String comment, String additionalLabel,
                            List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
    }
        public ActorPlaceHolder(IModelLayer layer) {
                super(layer, null, null, null, null);
        }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

}