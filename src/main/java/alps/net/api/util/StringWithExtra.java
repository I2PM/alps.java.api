package alps.net.api.util;
import alps.net.api.parsing;
import org.apache.jena.rdf.model.RDFNode;

public abstract class StringWithExtra implements IStringWithExtra {
    protected String content = "";
    protected String extra = "";

    public StringWithExtra(String input) {
        setContent(input);
    }

    public StringWithExtra(String content, String extra) {
        setContent(content);
        setExtra(extra);
    }

    public String getContent() {
        return content;
    }

    public String getExtra() {
        return extra;
    }

    public void setContent(String content) {
        if (content == null) {
            this.content = "";
            return;
        } else {
            this.content = content;
        }
    }

    public void setExtra(String extra) {
        if (extra == null) {
            this.extra = "";
            return;
        } else {
            this.extra = extra;
        }
    }

    public abstract RDFNode getNodeFromString(IPASSGraph graph);
    @Override
    public abstract IStringWithExtra clone();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IStringWithExtra extra) {
            if (getClass().equals(extra.getClass())) {
                int matches = 0;
                if ((extra.getContent() != null && getContent() != null && extra.getContent().equals(getContent())) || (extra.getContent() == null && getContent() == null)) {
                    matches++;
                }
                if ((extra.getExtra() != null && getExtra() != null && extra.getExtra().equals(getExtra())) || (extra.getExtra() == null && getExtra() == null)) {
                    matches++;
                }
                if (matches == 2) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return (getContent() + getExtra()).hashCode();
    }

    public abstract String ToString();
}
