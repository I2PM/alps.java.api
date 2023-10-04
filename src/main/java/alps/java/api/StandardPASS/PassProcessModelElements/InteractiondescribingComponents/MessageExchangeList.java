package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.ALPS.ALPSModelElements.Simple2DVisualizationPoints.ISimple2DVisualizationPathPoint;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.CompatibilityDictionary;
import alps.java.api.util.ICompatibilityDictionary;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Class that represents an message exchange list
 */
public class MessageExchangeList extends InteractionDescribingComponent implements IMessageExchangeList {
    protected ICompatibilityDictionary<String, IMessageExchange> messageExchanges = new CompatibilityDictionary<String, IMessageExchange>();

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "MessageExchangeList";
    private double has2DPageRatio;
    private double hasRelative2D_BeginX;
    private double hasRelative2D_BeginY;
    private double hasRelative2D_EndX;
    private double hasRelative2D_EndY;
    private static final Logger Log = Logger.getLogger(MessageExchangeList.class.getName());
    private List<ISimple2DVisualizationPathPoint> pathPoints = new ArrayList<ISimple2DVisualizationPathPoint>();


    public double get2DPageRatio() {
        return has2DPageRatio;
    }

    public void set2DPageRatio(double has2DPageRatio) {
        if (has2DPageRatio > 0) {
            this.has2DPageRatio = has2DPageRatio;
        }
        if (has2DPageRatio == 0) {
            this.has2DPageRatio = 1;
            Log.warning("found 2D page ratio of 0. This is impossible. changed it to 1");
        } else {
            this.has2DPageRatio = Math.abs(has2DPageRatio);
            Log.warning("found negative 2d page ratio. Changed it to positive value");
        }
    }

    public double getRelative2DBeginX() {
        return hasRelative2D_BeginX;
    }

    public void setRelative2DBeginX(double relative2DBeginX) {
        if (relative2DBeginX >= 0 && relative2DBeginX <= 1) {
            hasRelative2D_BeginX = relative2DBeginX;
        } else {
            if (relative2DBeginX < 0) {
                hasRelative2D_BeginX = 0;
                Log.warning("Value for relative2DBeginX is smaller than 0. Setting it to 0.");
            } else if (relative2DBeginX > 1) {
                hasRelative2D_BeginX = 1;
                Log.warning("Value for relative2DBeginX is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DBeginY() {
        return hasRelative2D_BeginY;
    }

    public void setRelative2DBeginY(double relative2DBeginY) {
        if (relative2DBeginY >= 0 && relative2DBeginY <= 1) {
            hasRelative2D_BeginY = relative2DBeginY;
        } else {
            if (relative2DBeginY < 0) {
                hasRelative2D_BeginY = 0;
                Log.warning("Value for relative2DBeginY is smaller than 0. Setting it to 0.");
            } else if (relative2DBeginY > 1) {
                hasRelative2D_BeginY = 1;
                Log.warning("Value for relative2DBeginY is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DEndX() {
        return hasRelative2D_EndX;
    }

    public void setRelative2DEndX(double relative2DEndX) {
        if (relative2DEndX >= 0 && relative2DEndX <= 1) {
            hasRelative2D_EndX = relative2DEndX;
        } else {
            if (relative2DEndX < 0) {
                hasRelative2D_EndX = 0;
                Log.warning("Value for relative2DEndX is smaller than 0. Setting it to 0.");
            } else if (relative2DEndX > 1) {
                hasRelative2D_EndX = 1;
                Log.warning("Value for relative2DEndX is larger than 1. Setting it to 1.");
            }
        }

    }

    public double getRelative2DEndY() {
        return hasRelative2D_EndY;
    }

    public void setRelative2DEndY(double relative2DEndY) {
        if (relative2DEndY >= 0 && relative2DEndY <= 1) {
            hasRelative2D_EndY = relative2DEndY;
        } else {
            if (relative2DEndY < 0) {
                hasRelative2D_EndY = 0;
                Log.warning("Value for relative2DEndY is smaller than 0. Setting it to 0.");
            } else if (relative2DEndY > 1) {
                hasRelative2D_EndY = 1;
                Log.warning("Value for relative2DEndY is larger than 1. Setting it to 1.");
            }
        }
    }

    public List<ISimple2DVisualizationPathPoint> getSimple2DPathPoints() {
        return this.pathPoints;
    }

    public void addSimple2DPathPoint(ISimple2DVisualizationPathPoint point) {
        this.pathPoints.add(point);
    }


    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new MessageExchangeList();
    }

    protected MessageExchangeList() {
    }

    /**
     * @param layer
     * @param labelForID
     * @param messageExchanges
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public MessageExchangeList(IModelLayer layer, String labelForID, Set<IMessageExchange> messageExchanges, String comment, String additionalLabel,
                               List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);
        setContainsMessageExchanges(messageExchanges);

    }

    public MessageExchangeList(IModelLayer layer) {
        super(layer, null, null, null, null);
        setContainsMessageExchanges(null);

    }


    public void addContainsMessageExchange(IMessageExchange messageExchange) {
        if (messageExchange == null) {
            return;
        }
        if (messageExchanges.tryAdd(messageExchange.getModelComponentID(), messageExchange)) {
            publishElementAdded(messageExchange);
            messageExchange.register(this);
            addTriple(new IncompleteTriple(OWLTags.stdContains, messageExchange.getUriModelComponentID()));
        }
    }


    public void setContainsMessageExchanges(Set<IMessageExchange> messageExchanges, int removeCascadeDepth) {
        for (IMessageExchange messageExchange : getMessageExchanges().values()) {
            removeMessageExchange(messageExchange.getModelComponentID(), removeCascadeDepth);
        }
        if (messageExchanges == null) return;
        for (IMessageExchange messageExchange : messageExchanges) {
            addContainsMessageExchange(messageExchange);
        }
    }

    public void setContainsMessageExchanges(Set<IMessageExchange> messageExchanges) {
        for (IMessageExchange messageExchange : getMessageExchanges().values()) {
            removeMessageExchange(messageExchange.getModelComponentID(), 0);
        }
        if (messageExchanges == null) return;
        for (IMessageExchange messageExchange : messageExchanges) {
            addContainsMessageExchange(messageExchange);
        }
    }

    public void removeMessageExchange(String id, int removeCascadeDepth) {
        if (id == null) return;
        IMessageExchange exchange = messageExchanges.get(id);
        if (exchange != null) {
            messageExchanges.remove(id);
            exchange.unregister(this, removeCascadeDepth);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, exchange.getUriModelComponentID()));
        }
    }

    public void removeMessageExchange(String id) {
        if (id == null) return;
        IMessageExchange exchange = messageExchanges.get(id);
        if (exchange != null) {
            messageExchanges.remove(id);
            exchange.unregister(this, 0);
            removeTriple(new IncompleteTriple(OWLTags.stdContains, exchange.getUriModelComponentID()));
        }
    }

    public Map<String, IMessageExchange> getMessageExchanges() {
        return new HashMap<String, IMessageExchange>(messageExchanges);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        Locale customLocale = new Locale("en", "US");
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(customLocale);
        customSymbols.setDecimalSeparator('.');
        DecimalFormat customFormatter = new DecimalFormat("#.##########", customSymbols);
        if (element != null) {
            if (predicate.contains(OWLTags.ccontains) && element instanceof IMessageExchange exchange) {
                addContainsMessageExchange(exchange);
                return true;
            } else if (element instanceof ISimple2DVisualizationPathPoint point) {
                if (this.pathPoints == null) this.pathPoints = new ArrayList<ISimple2DVisualizationPathPoint>();

                this.pathPoints.add(point);
            }
        } else if (predicate.contains(OWLTags.abstrHas2DPageRatio)) {
            try {
                double ratio = customFormatter.parse(objectContent).doubleValue();
                set2DPageRatio(ratio);
                return true;
            } catch (ParseException e) {
            }
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_BeginX)) {
            try {
                double value = customFormatter.parse(objectContent).doubleValue();
                setRelative2DBeginX(value);
                return true;
            } catch (ParseException e) {
            }
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_BeginY)) {
            try {
                double value = customFormatter.parse(objectContent).doubleValue();
                setRelative2DBeginY(value);
                return true;
            } catch (ParseException e) {
            }
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_EndY)) {
            try {
                double value = customFormatter.parse(objectContent).doubleValue();
                setRelative2DEndY(value);
                return true;
            } catch (ParseException e) {
            }
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_EndX)) {
            try {
                double value = customFormatter.parse(objectContent).doubleValue();
                setRelative2DEndX(value);
                return true;
            } catch (ParseException e) {
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (messageExchanges.containsKey(oldID)) {
            IMessageExchange element = messageExchanges.get(oldID);
            messageExchanges.remove(oldID);
            messageExchanges.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IMessageExchange exchange : getMessageExchanges().values()) baseElements.add(exchange);
        return baseElements;
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IMessageExchange exchange) {
                // Try to remove the incoming exchange
                removeMessageExchange(exchange.getModelComponentID(), removeCascadeDepth);
            }
        }
    }

    @Override
    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IMessageExchange exchange) {
                // Try to remove the incoming exchange
                removeMessageExchange(exchange.getModelComponentID(), 0);
            }
        }
    }
}