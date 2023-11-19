package alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents;


import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.FunctionalityCapsules.ExtendsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.IExtendsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.IImplementsFunctionalityCapsule;
import alps.java.api.FunctionalityCapsules.ImplementsFunctionalityCapsule;
import alps.java.api.StandardPASS.IPASSProcessModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.IPASSProcessModel;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractionDescribingComponent;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents a subject
 */
public class Subject extends InteractionDescribingComponent implements ISubject {

// Needs StartSubject as type


    protected int instanceRestriction;
    protected final ICompatibilityDictionary<String, IMessageExchange> incomingExchange = new CompatibilityDictionary<String, IMessageExchange>();
    protected final ICompatibilityDictionary<String, IMessageExchange> outgoingExchange = new CompatibilityDictionary<String, IMessageExchange>();
    protected final IImplementsFunctionalityCapsule<ISubject> implCapsule;
    protected final IExtendsFunctionalityCapsule<ISubject> extendsCapsule;
    protected final List<ISubject.Role> roles = new ArrayList<Role>();
    protected boolean isAbstractType = false;

    private final String ABSTRACT_NAME = "AbstractSubject";

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "Subject";

    private double has2DPageRatio = -1;
    private double hasRelative2D_Height = -1;
    private double hasRelative2D_Width = -1;
    private double hasRelative2D_PosX = -1;
    private double hasRelative2D_PosY = -1;

    public double get2DPageRatio() {
        return has2DPageRatio;
    }

    public void set2DPageRatio(double has2DPageRatio) {
        if (has2DPageRatio >= 0) {
            this.has2DPageRatio = has2DPageRatio;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double getRelative2DHeight() {
        return hasRelative2D_Height;
    }

    public void setRelative2DHeight(double relative2DHeight) {
        if (relative2DHeight >= 0 && relative2DHeight <= 1) {
            hasRelative2D_Height = relative2DHeight;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double getRelative2DWidth() {
        return hasRelative2D_Width;
    }

    public void setRelative2DWidth(double relative2DWidth) {
        if (relative2DWidth >= 0 && relative2DWidth <= 1) {
            hasRelative2D_Width = relative2DWidth;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double getRelative2DPosX() {
        return hasRelative2D_PosX;
    }

    public void setRelative2DPosX(double relative2DPosX) {
        if (relative2DPosX >= 0 && relative2DPosX <= 1) {
            hasRelative2D_PosX = relative2DPosX;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public double getRelative2DPosY() {
        return hasRelative2D_PosY;
    }

    public void setRelative2DPosY(double relative2DPosY) {
        if (relative2DPosY >= 0 && relative2DPosY <= 1) {
            hasRelative2D_PosY = relative2DPosY;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new Subject();
    }

    public Subject() {
        implCapsule = new ImplementsFunctionalityCapsule<ISubject>(this);
        extendsCapsule = new ExtendsFunctionalityCapsule<ISubject>(this);
    }

    /**
     * @param layer
     * @param labelForID
     * @param incomingMessageExchange
     * @param outgoingMessageExchange
     * @param maxSubjectInstanceRestriction
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public Subject(IModelLayer layer, String labelForID, Set<IMessageExchange> incomingMessageExchange,
                   Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction, String comment, String additionalLabel,
                   List<IIncompleteTriple> additionalAttribute) {
        super(layer, labelForID, comment, additionalLabel, additionalAttribute);

        extendsCapsule = new ExtendsFunctionalityCapsule<ISubject>(this);
        implCapsule = new ImplementsFunctionalityCapsule<ISubject>(this);
        setIncomingMessageExchanges(incomingMessageExchange);
        setInstanceRestriction(maxSubjectInstanceRestriction);
        setOutgoingMessageExchanges(outgoingMessageExchange);
    }

    public Subject(IModelLayer layer) {
        super(layer, null, null, null, null);

        extendsCapsule = new ExtendsFunctionalityCapsule<ISubject>(this);
        implCapsule = new ImplementsFunctionalityCapsule<ISubject>(this);
        setIncomingMessageExchanges(null);
        setInstanceRestriction(1);
        setOutgoingMessageExchanges(null);
    }

    public int getInstanceRestriction() {
        return instanceRestriction;
    }

    public void setInstanceRestriction(int restriction) {
        if (restriction == this.instanceRestriction) return;
        removeTriple(new IncompleteTriple(OWLTags.stdhasInstanceRestriction, Integer.toString(instanceRestriction), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeNonNegativeInt));
        this.instanceRestriction = (restriction >= 0) ? restriction : 0;
        //TODO: hier tritt jetzt Fehlermeldung
        addTriple(new IncompleteTriple(OWLTags.stdhasInstanceRestriction, Integer.toString(instanceRestriction), IncompleteTriple.LiteralType.DATATYPE, OWLTags.xsdDataTypeNonNegativeInt));
    }


    public void addIncomingMessageExchange(IMessageExchange exchange) {
        if (exchange == null) {
            return;
        }
        if (incomingExchange.tryAdd(exchange.getModelComponentID(), exchange)) {
            publishElementAdded(exchange);
            exchange.register(this);
            exchange.setReceiver(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasIncomingMessageExchange, exchange.getUriModelComponentID()));
        }
    }

    public void addOutgoingMessageExchange(IMessageExchange exchange) {
        if (exchange == null) {
            return;
        }
        if (outgoingExchange.tryAdd(exchange.getModelComponentID(), exchange)) {
            publishElementAdded(exchange);
            exchange.register(this);
            exchange.setSender(this);
            addTriple(new IncompleteTriple(OWLTags.stdHasOutgoingMessageExchange, exchange.getUriModelComponentID()));
        }
    }

    public Map<String, IMessageExchange> getIncomingMessageExchanges() {
        return new HashMap<String, IMessageExchange>(incomingExchange);
    }

    public Map<String, IMessageExchange> getOutgoingMessageExchanges() {
        return new HashMap<String, IMessageExchange>(outgoingExchange);
    }


    public void setIncomingMessageExchanges(Set<IMessageExchange> exchanges, int removeCascadeDepth) {
        for (IMessageExchange exchange : getIncomingMessageExchanges().values()) {
            removeIncomingMessageExchange(exchange.getModelComponentID(), removeCascadeDepth);
        }
        if (exchanges == null) return;
        for (IMessageExchange exchange : exchanges) {
            addIncomingMessageExchange(exchange);
        }
    }

    public void setIncomingMessageExchanges(Set<IMessageExchange> exchanges) {
        for (IMessageExchange exchange : getIncomingMessageExchanges().values()) {
            removeIncomingMessageExchange(exchange.getModelComponentID(), 0);
        }
        if (exchanges == null) return;
        for (IMessageExchange exchange : exchanges) {
            addIncomingMessageExchange(exchange);
        }
    }

    public void setOutgoingMessageExchanges(Set<IMessageExchange> exchanges, int removeCascadeDepth) {
        for (IMessageExchange exchange : getOutgoingMessageExchanges().values()) {
            removeOutgoingMessageExchange(exchange.getModelComponentID(), removeCascadeDepth);
        }
        if (exchanges == null) return;
        for (IMessageExchange exchange : exchanges) {
            addOutgoingMessageExchange(exchange);
        }
    }

    public void setOutgoingMessageExchanges(Set<IMessageExchange> exchanges) {
        for (IMessageExchange exchange : getOutgoingMessageExchanges().values()) {
            removeOutgoingMessageExchange(exchange.getModelComponentID(), 0);
        }
        if (exchanges == null) return;
        for (IMessageExchange exchange : exchanges) {
            addOutgoingMessageExchange(exchange);
        }
    }

    public void removeIncomingMessageExchange(String id, int removeCascadeDepth) {
        if (id == null) return;
        IMessageExchange exchange = incomingExchange.get(id);
        if (exchange != null) {
            incomingExchange.remove(id);
            exchange.unregister(this, removeCascadeDepth);
            exchange.setReceiver(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasIncomingMessageExchange, exchange.getUriModelComponentID()));
        }
    }

    public void removeIncomingMessageExchange(String id) {
        if (id == null) return;
        IMessageExchange exchange = incomingExchange.get(id);
        if (exchange != null) {
            incomingExchange.remove(id);
            exchange.unregister(this, 0);
            exchange.setReceiver(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasIncomingMessageExchange, exchange.getUriModelComponentID()));
        }
    }

    public void removeOutgoingMessageExchange(String id, int removeCascadeDepth) {
        if (id == null) return;
        IMessageExchange exchange = outgoingExchange.get(id);
        if (exchange != null) {
            outgoingExchange.remove(id);
            exchange.unregister(this, removeCascadeDepth);
            exchange.setSender(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasOutgoingMessageExchange, exchange.getUriModelComponentID()));
        }
    }

    public void removeOutgoingMessageExchange(String id) {
        if (id == null) return;
        IMessageExchange exchange = outgoingExchange.get(id);
        if (exchange != null) {
            outgoingExchange.remove(id);
            exchange.unregister(this, 0);
            exchange.setSender(null);
            removeTriple(new IncompleteTriple(OWLTags.stdHasOutgoingMessageExchange, exchange.getUriModelComponentID()));
        }
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        if (implCapsule != null && implCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
            return true;
        else if (extendsCapsule != null && extendsCapsule.parseAttribute(predicate, objectContent, lang, dataType, element))
            return true;
        else if (predicate.contains(OWLTags.hasInstanceRestriction)) {
            setInstanceRestriction(Integer.parseInt(objectContent));
            return true;
        } else if (predicate.contains(OWLTags.type) && objectContent.contains("StartSubject")) {
            assignRole(ISubject.Role.StartSubject);
            return true;
        } else if (element != null) {
            if (element instanceof IMessageExchange messageExchange) {
                if (predicate.contains(OWLTags.hasIncomingMessageExchange)) {
                    addIncomingMessageExchange(messageExchange);
                    return true;
                } else if (predicate.contains(OWLTags.hasOutgoingMessageExchange)) {
                    addOutgoingMessageExchange(messageExchange);
                    return true;
                }
            }
        } else if (predicate.contains(OWLTags.abstrHas2DPageRatio)) {
            try {
                double parsedValue = parseDoubleWithLocale(objectContent, customLocale);

                set2DPageRatio(parsedValue);
            } catch (ParseException e) {
                System.out.println("Fehler beim Parsen: " + e.getMessage());
            }

            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_PosX)) {
            try {
                double parsedValue = parseDoubleWithLocale(objectContent, customLocale);

                setRelative2DPosX(parsedValue);
            } catch (ParseException e) {
                System.out.println("Fehler beim Parsen: " + e.getMessage());
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_PosY)) {
            try {
                double parsedValue = parseDoubleWithLocale(objectContent, customLocale);

                setRelative2DPosY(parsedValue);
            } catch (ParseException e) {
                System.out.println("Fehler beim Parsen: " + e.getMessage());
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_Height)) {
            try {
                double parsedValue = parseDoubleWithLocale(objectContent, customLocale);

                setRelative2DHeight(parsedValue);
            } catch (ParseException e) {
                System.out.println("Fehler beim Parsen: " + e.getMessage());
            }
            return true;
        } else if (predicate.contains(OWLTags.abstrHasRelative2D_Width)) {
            try {
                double parsedValue = parseDoubleWithLocale(objectContent, customLocale);

                setRelative2DWidth(parsedValue);
            } catch (ParseException e) {
                System.out.println("Fehler beim Parsen: " + e.getMessage());
            }
            return true;
        } else {
            if (predicate.contains(OWLTags.type)) {
                if (objectContent.contains(ABSTRACT_NAME)) {
                    setIsAbstract(true);
                    return true;
                }
            }
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }


    public static double parseDoubleWithLocale(String value, Locale locale) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        return numberFormat.parse(value).doubleValue();
    }

    public void assignRole(ISubject.Role role) {
        if (!roles.contains(role)) {
            roles.add(role);
            if (role == ISubject.Role.StartSubject) {
                addTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.stdStartSubject));
                IModelLayer layer = getContainedBy();
                if (layer != null) {
                    IPASSProcessModel model = layer.getContainedBy();
                    if (model != null)
                        model.addStartSubject(this);
                }
            }
        }
    }

    public boolean isRole(ISubject.Role role) {
        return roles.contains(role);
    }

    //TODO: out-Parameter
    public void removeRole(ISubject.Role role) {
        if (roles.contains(role)) {
            roles.remove(role);
            if (role == ISubject.Role.StartSubject) {
                removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.stdStartSubject));
                IModelLayer layer = getContainedBy();
                if (layer != null) {
                    IPASSProcessModel model = layer.getContainedBy();
                    if (model != null)
                        model.removeStartSubject(getModelComponentID());
                }
            }
        }
    }

    @Override
    public void notifyModelComponentIDChanged(String oldID, String newID) {
        if (incomingExchange.containsKey(oldID)) {
            IMessageExchange element = incomingExchange.get(oldID);
            incomingExchange.remove(oldID);
            incomingExchange.put(element.getModelComponentID(), element);
        }
        if (outgoingExchange.containsKey(oldID)) {
            IMessageExchange element = outgoingExchange.get(oldID);
            outgoingExchange.remove(oldID);
            outgoingExchange.put(element.getModelComponentID(), element);
        }
        super.notifyModelComponentIDChanged(oldID, newID);
    }

    @Override
    public Set<IPASSProcessModelElement> getAllConnectedElements(ConnectedElementsSetSpecification specification) {
        Set<IPASSProcessModelElement> baseElements = super.getAllConnectedElements(specification);
        for (IMessageExchange exchange : getIncomingMessageExchanges().values()) baseElements.add(exchange);
        for (IMessageExchange exchange : getOutgoingMessageExchanges().values()) baseElements.add(exchange);
        return baseElements;
    }

    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller, int removeCascadeDepth) {
        super.updateRemoved(update, caller, removeCascadeDepth);
        if (update != null) {
            if (update instanceof IMessageExchange exchange) {
                // Try to remove the incoming exchange
                removeIncomingMessageExchange(exchange.getModelComponentID(), removeCascadeDepth);
                // Try to remove the outgoing exchange
                removeOutgoingMessageExchange(exchange.getModelComponentID(), removeCascadeDepth);
            }
        }
    }

    public void updateRemoved(IPASSProcessModelElement update, IPASSProcessModelElement caller) {
        super.updateRemoved(update, caller, 0);
        if (update != null) {
            if (update instanceof IMessageExchange exchange) {
                // Try to remove the incoming exchange
                removeIncomingMessageExchange(exchange.getModelComponentID(), 0);
                // Try to remove the outgoing exchange
                removeOutgoingMessageExchange(exchange.getModelComponentID(), 0);
            }
        }
    }

    public void setIsAbstract(boolean isAbstract) {
        this.isAbstractType = isAbstract;
        if (isAbstract) {
            addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        } else {
            removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + ABSTRACT_NAME));
        }
    }

    public boolean isAbstract() {
        return isAbstractType;
    }

    public void setImplementedInterfacesIDReferences(Set<String> implementedInterfacesIDs) {
        implCapsule.setImplementedInterfacesIDReferences(implementedInterfacesIDs);
    }

    public void addImplementedInterfaceIDReference(String implementedInterfaceID) {
        implCapsule.addImplementedInterfaceIDReference(implementedInterfaceID);
    }

    public void removeImplementedInterfacesIDReference(String implementedInterfaceID) {
        implCapsule.removeImplementedInterfacesIDReference(implementedInterfaceID);
    }

    public Set<String> getImplementedInterfacesIDReferences() {
        return implCapsule.getImplementedInterfacesIDReferences();
    }

    public void setImplementedInterfaces(Set<ISubject> implementedInterface, int removeCascadeDepth) {
        implCapsule.setImplementedInterfaces(implementedInterface, removeCascadeDepth);
    }

    public void setImplementedInterfaces(Set<ISubject> implementedInterface) {
        implCapsule.setImplementedInterfaces(implementedInterface, 0);
    }

    public void addImplementedInterface(ISubject implementedInterface) {
        implCapsule.addImplementedInterface(implementedInterface);
    }

    public void removeImplementedInterfaces(String id, int removeCascadeDepth) {
        implCapsule.removeImplementedInterfaces(id, removeCascadeDepth);
    }

    public void removeImplementedInterfaces(String id) {
        implCapsule.removeImplementedInterfaces(id, 0);
    }

    public Map<String, ISubject> getImplementedInterfaces() {
        return implCapsule.getImplementedInterfaces();
    }

    public void setExtendedElement(ISubject element) {
        extendsCapsule.setExtendedElement(element);
    }

    public void setExtendedElementID(String elementID) {
        extendsCapsule.setExtendedElementID(elementID);
    }

    public ISubject getExtendedElement() {
        return extendsCapsule.getExtendedElement();
    }

    public String getExtendedElementID() {
        return extendsCapsule.getExtendedElementID();
    }

    public boolean isExtension() {
        return extendsCapsule.isExtension();
    }
}
