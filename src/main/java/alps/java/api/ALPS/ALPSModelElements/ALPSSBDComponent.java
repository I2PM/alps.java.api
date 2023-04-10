package alps.java.api.ALPS.ALPSModelElements;

import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;

import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;

/**
 * Class that represents an ALPS SBD component
 */
public class ALPSSBDComponent extends BehaviorDescribingComponent implements IALPSSBDComponent {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ALPSSBDComponent";

    @Override
    public String getClassName() {
        return className;
    }


    public ALPSSBDComponent(ISubjectBehavior subjectBehavior, String labelForID, String comment, String additionalLabel,
                            List<IIncompleteTriple> additionalAttribute) {
        super(subjectBehavior, labelForID, comment, additionalLabel, additionalAttribute);
    }

    public ALPSSBDComponent(ISubjectBehavior subjectBehavior) {
        super(subjectBehavior);
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ALPSSBDComponent();
    }

    protected ALPSSBDComponent() {
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }
}