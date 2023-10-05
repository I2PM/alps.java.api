package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

/**
 * Class that represents an InputPoolConstraint
 */
public class InputPoolConstraint extends InteractionDescribingComponent implements IInputPoolConstraint {
    protected IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy;
    protected int limit = 0;
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "InputPoolConstraint";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new InputPoolConstraint();
    }

    public InputPoolConstraint() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param inputPoolConstraintHandlingStrategy
     * @param limit
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public InputPoolConstraint(IModelLayer layer, String labelForID, IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy,
                               int limit, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setInputPoolConstraintHandlingStrategy(inputPoolConstraintHandlingStrategy);
        setLimit(limit);
    }

    /**
     * @param layer
     */
    public InputPoolConstraint(IModelLayer layer) {
        super(layer, null, null, null, null);
        setInputPoolConstraintHandlingStrategy(null);
        setLimit(0);
    }

    public void setInputPoolConstraintHandlingStrategy(IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy, int removeCascadeDepth) {
        IInputPoolConstraintHandlingStrategy oldStrat = this.inputPoolConstraintHandlingStrategy;
        // Might set it to null
        this.inputPoolConstraintHandlingStrategy = inputPoolConstraintHandlingStrategy;

        if (oldStrat != null) {
            if (oldStrat.equals(inputPoolConstraintHandlingStrategy)) return;
            oldStrat.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdHasHandlingStrategy, oldStrat.getUriModelComponentID()));
        }

        if (!(inputPoolConstraintHandlingStrategy == null)) {
            publishElementAdded(inputPoolConstraintHandlingStrategy);
            inputPoolConstraintHandlingStrategy.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasHandlingStrategy, inputPoolConstraintHandlingStrategy.getUriModelComponentID()));
        }
    }

    public void setInputPoolConstraintHandlingStrategy(IInputPoolConstraintHandlingStrategy inputPoolConstraintHandlingStrategy) {
        IInputPoolConstraintHandlingStrategy oldStrat = this.inputPoolConstraintHandlingStrategy;
        // Might set it to null
        this.inputPoolConstraintHandlingStrategy = inputPoolConstraintHandlingStrategy;

        if (oldStrat != null) {
            if (oldStrat.equals(inputPoolConstraintHandlingStrategy)) return;
            oldStrat.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdHasHandlingStrategy, oldStrat.getUriModelComponentID()));
        }

        if (!(inputPoolConstraintHandlingStrategy == null)) {
            publishElementAdded(inputPoolConstraintHandlingStrategy);
            inputPoolConstraintHandlingStrategy.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasHandlingStrategy, inputPoolConstraintHandlingStrategy.getUriModelComponentID()));
        }
    }

    public void setLimit(int nonNegativInteger) {
        if (nonNegativInteger == getLimit()) return;
        removeTriple(new IncompleteTriple(OWLTags.stdHasLimit, Integer.toString(this.limit), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeNonNegativeInt));
        limit = (nonNegativInteger >= 0) ? nonNegativInteger : 0;
        addTriple(new IncompleteTriple(OWLTags.stdHasLimit, Integer.toString(this.limit), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeNonNegativeInt));
    }


    public IInputPoolConstraintHandlingStrategy getInputPoolConstraintHandlingStrategy() {
        return inputPoolConstraintHandlingStrategy;
    }


    public int getLimit() {
        return limit;
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (predicate.contains(OWLTags.hasLimit)) {
            String limit = objectContent;
            setLimit(Integer.parseInt(limit));
            return true;
        } else if (element != null) {
            if (predicate.contains(OWLTags.hasHandlingStrategy) && element instanceof IInputPoolConstraintHandlingStrategy strategy) {
                setInputPoolConstraintHandlingStrategy(strategy);
                return true;
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (getInputPoolConstraintHandlingStrategy() != null)
            baseElements.add(getInputPoolConstraintHandlingStrategy());
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IInputPoolConstraintHandlingStrategy strategy && strategy.equals(getInputPoolConstraintHandlingStrategy()))
                setInputPoolConstraintHandlingStrategy(null, removeCascadeDepth);
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IInputPoolConstraintHandlingStrategy strategy && strategy.equals(getInputPoolConstraintHandlingStrategy()))
                setInputPoolConstraintHandlingStrategy(null, 0);
        }
    }

}