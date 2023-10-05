package alps.java.api.ALPS.ALPSModelElements;

import alps.java.api.ALPS.IALPSModelElement;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.ISubject;
import alps.java.api.StandardPASS.PassProcessModelElements.PASSProcessModel;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.util.IIncompleteTriple;

import java.util.List;
import java.util.Set;

public class LayeredPassProcessModel extends PASSProcessModel implements IALPSModelElement, ILayeredPassProcessModel {
    /**
     * Name of the class, needed for parsing
     */
    private final String className = "LayeredPASSProcessModel";

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new LayeredPassProcessModel();
    }

    public LayeredPassProcessModel() {
    }

    /**
     * Constructor that creates a new fully specified instance of the layered pass process modell class
     *
     * @param baseURI
     * @param labelForID
     * @param messageExchanges
     * @param relationsToModelComponent
     * @param startSubject
     * @param comment
     * @param additionalLabel
     * @param additionalAttribute
     */
    public LayeredPassProcessModel(String baseURI, String labelForID, Set<IMessageExchange> messageExchanges, Set<ISubject> relationsToModelComponent,
                                   Set<ISubject> startSubject, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(baseURI, labelForID, messageExchanges, relationsToModelComponent, startSubject, comment, additionalLabel, additionalAttribute);
    }


}
