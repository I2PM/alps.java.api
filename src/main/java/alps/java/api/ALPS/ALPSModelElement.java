package alps.java.api.ALPS;

import alps.java.api.StandardPASS.PASSProcessModelElement;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;

import java.text.ParseException;
import java.util.List;

/**
 * Class that represents an ALPS model element
 */
public class ALPSModelElement extends PASSProcessModelElement implements IALPSModelElement {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ALPSModelElement";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ALPSModelElement();
    }

    protected ALPSModelElement() {
    }

    /**
     * The default value of all Objects is null.
     * @param labelForID
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */

    public ALPSModelElement(String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(labelForID, comment, additionalLabel, additionalAttribute);
    }

    @Override
    protected String getExportTag() {
        return OWLTags.abstr;
    }
}
