package alps.java.api.util;

import alps.java.api.parsing.IPASSGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.RDFNode;

/**
 * A class to represent a string along with a specified language identifier
 * This class is useful to represent an object (in a triple store context),
 * where the literal node might contain an additional language information.
 * The language is stored as extra.
 * If no extra is given, a default language identifier is used (such as en).
 */
public class LanguageSpecificString extends StringWithExtra {
    protected String standardLang = "en";

    /**
     * Takes a label as string to create a new language specified string.
     * The label might be passed as label@lang (i.e. "someSubj@en") to be parsed correctly.
     * The label must not contain an @. If not, the standard language is saved for the label.
     *
     * @param content the label
     */
    public LanguageSpecificString(String content) {
        super(content);
        // No need for calls here, base calls our overwritten setContent
    }

    /**
     * Creates a new label, specifying the label and the language in different strings.
     * If label and lang are currently together in one string, separated via @ (i.e. parsed from an owl file),
     * use the other constructor instead.
     *
     * @param content the label
     * @param lang    the language
     */
    public LanguageSpecificString(String content, String lang) {
        super(content, lang);
        // No need for calls here, base calls our overwritten setContent
    }

    @Override
    public IStringWithExtra clone() {
        return new LanguageSpecificString(getContent(), getExtra());
    }

    @Override
    public Node getNodeFromString(IPASSGraph graph) {
        return graph.createLiteralNode(getContent(), getExtra());
    }

    @Override
    public void setContent(String content) {
        if (content == null) {
            this.content = "";
            return;
        }
        if (content.contains("@")) {
            this.content = content.split("@")[0];
            setExtra(content.split("@")[1]);
        } else {
            this.content = content;
            if (extra.equals("")) setExtra(standardLang);
        }
    }

    @Override
    public void setExtra(String lang) {
        if (lang == null) {
            this.extra = standardLang;
            return;
        }
        if (lang.contains("@")) {
            setContent(lang);
        } else {
            this.extra = lang;
        }
    }

    @Override
    public String toString() {
        if (!getExtra().equals(""))
            return getContent() + "@" + getExtra();
        else return getContent();
    }
}

