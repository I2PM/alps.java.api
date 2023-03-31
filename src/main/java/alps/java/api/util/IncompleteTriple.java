package alps.java.api.util;

import alps.java.api.parsing.*;
import org.apache.jena.ext.xerces.util.URI;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.impl.StatementImpl;

public class IncompleteTriple implements IIncompleteTriple {
    private String predicateContent;
    private IStringWithExtra extraString;

    public enum LiteralType {
        DATATYPE,
        LANGUAGE
    }

    public IncompleteTriple(String predicate, String objectContent) {
        predicateContent = predicate;
        this.extraString = new StringWithoutExtra(objectContent);
    }
    public IncompleteTriple(Statement realTriple, String baseUriToReplace) {
        String predicate = baseUriToReplace == null ? realTriple.getPredicate().getURI() : StaticFunctions.replaceBaseUriWithGeneric(realTriple.getPredicate().getURI(), baseUriToReplace);
        predicateContent = predicate;
        if (realTriple.getObject().isLiteral()) {
            Literal literal = realTriple.getObject().asLiteral();
            String language = literal.getLanguage();
            if (language != null && !language.equals("")) {
                extraString = new LanguageSpecificString(literal.getString(), language);
            } else if (literal.getDatatype() != null && !literal.getDatatype().getURI().equals("")) {
                extraString = new DataTypeString(literal.getString(), literal.getDatatype().getURI());
            } else {
                String content = baseUriToReplace == null ? realTriple.getObject().toString() : StaticFunctions.replaceBaseUriWithGeneric(realTriple.getObject().toString(), baseUriToReplace);
                extraString = new StringWithoutExtra(content);
            }
        } else {
            String content = baseUriToReplace == null ? realTriple.getObject().toString() : StaticFunctions.replaceBaseUriWithGeneric(realTriple.getObject().toString(), baseUriToReplace);
            extraString = new StringWithoutExtra(content);
        }
    }

    public IncompleteTriple(Statement realTriple) {
        predicateContent = realTriple.getPredicate().getURI();
        if (realTriple.getObject().isLiteral()) {
            Literal literal = realTriple.getObject().asLiteral();
            String language = literal.getLanguage();
            if (language != null && !language.equals("")) {
                extraString = new LanguageSpecificString(literal.getString(), language);
            } else if (literal.getDatatype() != null && !literal.getDatatype().getURI().equals("")) {
                extraString = new DataTypeString(literal.getString(), literal.getDatatype().getURI());
            } else {
                extraString = new StringWithoutExtra(realTriple.getObject().toString());
            }
        } else {
            extraString = new StringWithoutExtra(realTriple.getObject().toString());
        }
    }
    public IncompleteTriple(String predicate, String objectContent, LiteralType literalType, String objectAddition) {
        this.predicateContent = predicate;
        if (literalType == LiteralType.DATATYPE) {
            this.extraString = new DataTypeString(objectContent, objectAddition);
        } else if (literalType == LiteralType.LANGUAGE) {
            this.extraString = new LanguageSpecificString(objectContent, objectAddition);
        }
    }
    public IncompleteTriple(String predicate, IStringWithExtra objectWithExtra) {
        predicateContent = predicate;
        this.extraString = objectWithExtra;
    }

    public Triple getRealTriple(IPASSGraph graph, RDFNode subjectNode) {
        Resource predicateNode;
        try {
            predicateNode = graph.createUriNode(predicateContent);
        } catch (Exception e) {
            try {
                predicateNode = graph.createUriNode(new URI(predicateContent));
            } catch (URI.MalformedURIException ex) {
                throw new RuntimeException(ex);
            }
        }
        RDFNode objectNode = extraString.getNodeFromString(graph);

        return new StatementImpl(subjectNode, (Property) predicateNode, objectNode);
    }

    public String getPredicate() {
        return predicateContent;
    }

    public String getObject() {
        return extraString.getContent();
    }

    @Override
    public boolean equals(Object otherObject) {
        int matches = 0;
        if (otherObject instanceof IncompleteTriple) {
            IncompleteTriple triple = (IncompleteTriple) otherObject;
            if ((triple.getPredicate() != null && getPredicate() != null && triple.getPredicate().equals(getPredicate())) || (triple.getPredicate() == null && getPredicate() == null))
                matches++;
            if ((triple.getObjectWithExtra() != null && getObjectWithExtra() != null && triple.getObjectWithExtra().equals(getObjectWithExtra())) || (triple.getObjectWithExtra() == null && getObjectWithExtra() == null))
                matches++;
        }
        return matches == 2;
    }

    public IStringWithExtra getObjectWithExtra() {
        return extraString.clone();
    }
    @Override
    public int hashCode() {
        String baseString = "";
        if (getPredicate() != null)
            baseString += getPredicate();
        if (getObjectWithExtra() != null)
            baseString += getObjectWithExtra();
        return baseString.hashCode();
    }

}


