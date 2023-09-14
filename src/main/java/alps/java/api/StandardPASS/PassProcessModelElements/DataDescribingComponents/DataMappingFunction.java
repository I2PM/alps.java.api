package alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponents;


import alps.java.api.StandardPASS.PassProcessModelElements.DataDescribingComponent;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.text.ParseException;
import java.util.List;

/**
 * Class that represents a data mapping function
 */
public class DataMappingFunction extends DataDescribingComponent implements IDataMappingFunction {
    protected String dataMappingString;
    protected String feelExpression;
    protected String toolSpecificDefinition;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "DataMappingFunction";


    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new DataMappingFunction();
    }

    protected DataMappingFunction() {
    }

    public DataMappingFunction(IPASSProcessModel model, String labelForID, String dataMappingString, String feelExpression,
                               String toolSpecificDefinition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(model, labelForID, comment, additionalLabel, additionalAttribute);
        setDataMappingString(dataMappingString);
        setFeelExpressionAsDataMapping(feelExpression);
        setToolSpecificDefinition(toolSpecificDefinition);
    }

    public DataMappingFunction(IPASSProcessModel model) {
        super(model, null, null, null, null);
        setDataMappingString(null);
        setFeelExpressionAsDataMapping(null);
        setToolSpecificDefinition(null);
    }

    public void setDataMappingString(String dataMappingString) {
        if (dataMappingString != null && dataMappingString.equals(this.dataMappingString)) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasDataMappingString, this.dataMappingString, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        this.dataMappingString = (dataMappingString == null || dataMappingString.equals("")) ? null : dataMappingString;
        if (dataMappingString != null)
            addTriple(new IncompleteTriple(OWLTags.stdHasDataMappingString, dataMappingString, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));

    }


    public void setFeelExpressionAsDataMapping(String feelExpression) {
        if (feelExpression != null && feelExpression.equals(this.feelExpression)) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasFeelExpressionAsDataMapping, this.feelExpression, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        this.feelExpression = (feelExpression == null || feelExpression.equals("")) ? null : feelExpression;
        if (feelExpression != null) {
            addTriple(new IncompleteTriple(OWLTags.stdHasFeelExpressionAsDataMapping, feelExpression, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
            setToolSpecificDefinition(null);
        }
    }


    public void setToolSpecificDefinition(String toolSpecificDefinition) {
        if (toolSpecificDefinition != null && toolSpecificDefinition.equals(this.toolSpecificDefinition)) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasToolSpecificDefinition, this.toolSpecificDefinition, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
        this.toolSpecificDefinition = (toolSpecificDefinition == null || toolSpecificDefinition.equals("")) ? null : toolSpecificDefinition;
        if (toolSpecificDefinition != null) {
            addTriple(new IncompleteTriple(OWLTags.stdHasToolSpecificDefinition, toolSpecificDefinition, IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeString));
            setFeelExpressionAsDataMapping(null);
        }
    }


    public String getDataMappingString() {
        return dataMappingString;
    }


    public String getFeelExpressionAsDataMapping() {
        return feelExpression;
    }


    public String getToolSpecificDefinition() {
        return toolSpecificDefinition;
    }

    public boolean hasToolSpecificDefinition() {
        return !(feelExpression == null);
    }

    public boolean hasFeelExpression() {
        return !(feelExpression == null);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) throws ParseException {
        if (predicate.contains(OWLTags.hasDataMappingString)) {
            setDataMappingString(objectContent);
            return true;
        } else if (predicate.contains(OWLTags.hasFeelExpressionAsDataMapping)) {
            setFeelExpressionAsDataMapping(objectContent);
            return true;
        } else if (predicate.contains(OWLTags.hasToolSpecificDefinition)) {
            setToolSpecificDefinition(objectContent);
            return true;
        } else return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


}


