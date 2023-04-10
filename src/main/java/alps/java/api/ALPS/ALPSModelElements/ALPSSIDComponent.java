package alps.java.api.ALPS.ALPSModelElements;

import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

/**
 * Class that represents an ALPS SID component
 * From abstract pass ont:
 * An abstract SID Component is a model Component only used on model layers defined as abstract.
 * It is used to specify a possible process or process elements that later can be used to implement others.
 */
public class ALPSSIDComponent extends InteractionDescribingComponent implements IALPSSIDComponent {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ALPSSIDComponent";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ALPSSIDComponent();
    }

    protected ALPSSIDComponent() {
    }

    /**
     * Constructor that creates a new fully specified instance of the ALPSSIDComponent class
     *
     * @param layer
     * @param label
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public ALPSSIDComponent(IModelLayer layer, String label, String comment,
                            String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, label, comment, additionalLabel, additionalAttribute);

    }
    public ALPSSIDComponent(IModelLayer layer) {
        super(layer);

    }

}
