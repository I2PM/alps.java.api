package alps.net.api.api.util;

import org.apache.jena.rdf.model.Literal;
import alps.net.api.parsing;

/// <summary>
/// A class to represent a string along with a specified language identifier
/// This class is useful to represent an object (in a triple store context),
/// where the literal node might contain an additional language information.
/// The language is stored as extra.
/// If no extra is given, a default language identifier is used (such as en).
/// </summary>
public class LanguageSpecificString extends StringWithExtra {
    protected String standardLang = "en";

/// <summary>
/// Takes a label as string to create a new language specified string.
/// The label might be passed as label@lang (i.e. "someSubj@en") to be parsed correctly.
/// The label must not contain an @. If not, the standard language is safed for the label.
/// </summary>
/// <param name="content">the label</param>
    public LanguageSpecificString(String content) {
        super(content);
        }

/// <summary>
/// Creates a new label, specifying the label and the language in different strings.
/// If label and lang are currently together in one string, separated via @ (i.e. parsed from an owl file),
/// use the other constructor instead.
/// </summary>
/// <param name="content">the label</param>
/// <param name="lang">the language</param>
    public LanguageSpecificString(String content, String lang){
        super(content, lang);
        }

    @Override
    public StringWithExtra clone() {
        return new LanguageSpecificString(getContent(), getExtra());
    }

    @Override
    public Literal getNodeFromString(IPASSGraph graph) {
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
    public String ToString()
        {
        if (!getExtra().equals(""))
        return getContent() + "@" + getExtra();
        else return getContent();
        }
}

