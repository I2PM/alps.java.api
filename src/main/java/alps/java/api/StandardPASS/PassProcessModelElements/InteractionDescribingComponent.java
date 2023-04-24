package alps.java.api.StandardPASS.PassProcessModelElements;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PASSProcessModelElement;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents an InteractionDescriptionComponten
 */
public class InteractionDescribingComponent extends PASSProcessModelElement implements IInteractionDescribingComponent {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "InteractionDescribingComponent";

    protected IModelLayer layer;

    @Override
    public String getClassName() {
        return className;
    }

    public void setContainedBy(IModelLayer layer) {
        if (layer == null || layer.equals(this.layer)) return;
        this.layer = layer;
        layer.addElement(this);
    }

    public IModelLayer getContainedBy() {
        return layer;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new InteractionDescribingComponent();
    }

    protected InteractionDescribingComponent() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public InteractionDescribingComponent(IModelLayer layer, String labelForID, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(labelForID, comment, additionalLabel, additionalAttribute);
        setContainedBy(layer);
    }

    public InteractionDescribingComponent(IModelLayer layer) {
        super(null, null, null, null);
        setContainedBy(layer);
    }

    @Override
    protected Map<String, IParseablePASSProcessModelElement> getDictionaryOfAllAvailableElements() {
        IModelLayer layer = getContainedBy();
        if (layer == null) return null;
        IPASSProcessModel model = layer.getContainedBy();
        if (model == null) return null;
        Map<String, IPASSProcessModelElement> allElements = model.getAllElements();
        Map<String, IParseablePASSProcessModelElement> allParseableElements = new HashMap<String, IParseablePASSProcessModelElement>();
        for (Map.Entry<String, IPASSProcessModelElement> pair : allElements.entrySet()) {
            IPASSProcessModelElement value = pair.getValue();
            if (value instanceof IParseablePASSProcessModelElement) {
                IParseablePASSProcessModelElement parseable = (IParseablePASSProcessModelElement) value;
                allParseableElements.put(pair.getKey(), parseable);
            }
        }
        return allParseableElements;
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        if (specification == ConnectedElementsSetSpecification.ALL) {
            IModelLayer layer = getContainedBy();
            if (layer != null) {
                baseElements.add(layer);
            }
        }

        return baseElements;
    }

    public void removeFromContainer() {
        layer.removeContainedElement(getModelComponentID());
        layer = null;
    }

}

