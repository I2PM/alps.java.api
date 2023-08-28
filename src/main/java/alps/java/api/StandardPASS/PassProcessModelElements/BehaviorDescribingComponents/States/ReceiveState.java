package alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.States;


import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.FunctionSpecifications.IReceiveFunction;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.IFunctionSpecification;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.ITransition;
import alps.java.api.StandardPASS.PassProcessModelElements.BehaviorDescribingComponents.Transitions.IReceiveTransition;
import alps.java.api.StandardPASS.PassProcessModelElements.ISubjectBehavior;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IGuardBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class that represents a receive state
 */
public class ReceiveState extends StandardPASSState implements IReceiveState {

    /**
     * Name of the class, needed for parsing
     */
    private final String className = "ReceiveState";
    protected String exportTag = OWLTags.std;
    protected String exportClassname = className;

    private double _billedWaitingTime;
    private static final Logger logger = Logger.getLogger( "ReceiveState");
    public double getSisiBilledWaitingTime() { return this._billedWaitingTime; }
    public void   setSiSiBilledWaitingTime(double value)
    {
        if (value >= 0.0 && value <= 1.0)
        {
            _billedWaitingTime = value;
        }
        else
        {
            if (value < 0)
            {
                _billedWaitingTime = 0;
                logger.warning("Value for billedWaitingTime is smaller than 0. Setting it to 0.");
            }
            else if (value > 1)
            {
                _billedWaitingTime = 1;
                logger.warning("Value for billedWaitingTime is larger than 1. Setting it to 1.");
            }
        }
    }
    @Override
    public String getClassName() {
        return exportClassname;
    }

    @Override
    public IParseablePASSProcessModelElement getParsedInstance() {
        return new ReceiveState();
    }

    protected ReceiveState() {
    }

    @Override
    protected String getExportTag() {
        return exportTag;
    }

    public ReceiveState(ISubjectBehavior behavior, String labelForID, IGuardBehavior guardBehavior,
                        IReceiveFunction functionSpecification,
                        Set<ITransition> incomingTransition, Set<ITransition> outgoingTransition, String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute) {
        super(behavior, labelForID, guardBehavior, functionSpecification, incomingTransition, outgoingTransition, comment, additionalLabel, additionalAttribute);
    }

    public ReceiveState(ISubjectBehavior behavior) {
        super(behavior, null, null, null, null, null, null, null, null);
    }


    public IReceiveFunction getFunctionSpecification() {
        return (IReceiveFunction) super.getFunctionSpecification();
    }


    public void setFunctionSpecification(IFunctionSpecification specification, int removeCascadingDepth) {
        if (specification instanceof IReceiveFunction) {
            super.setFunctionSpecification(specification, removeCascadingDepth);
        } else {
            super.setFunctionSpecification(null);
        }
    }

    public void setFunctionSpecification(IFunctionSpecification specification) {
        if (specification instanceof IReceiveFunction) {
            super.setFunctionSpecification(specification, 0);
        } else {
            super.setFunctionSpecification(null);
        }
    }

    @Override
    public void addOutgoingTransition(ITransition transition) {
        if (transition instanceof IReceiveTransition)
            super.addOutgoingTransition(transition);
    }

    @Override
    protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element) {
        Locale customLocale = new Locale("en", "US");

        NumberFormat numberFormat = NumberFormat.getInstance(customLocale);
        numberFormat.setGroupingUsed(false);
        if (element != null) {
            if (predicate.contains(OWLTags.hasFunctionSpecification) && element instanceof IReceiveFunction receiveFunction) {
                setFunctionSpecification(receiveFunction);
                return true;
            }
        } else if (predicate.contains(OWLTags.type)) {
            if (objectContent.contains("AbstractReceiveState")) {
                setIsStateType(StateType.Abstract);
                return true;
            } else if (objectContent.contains("FinalizedReceiveState")) {
                setIsStateType(StateType.Finalized);
                return true;
            }
        }
        else if (predicate.contains(OWLTags.abstrHasSimpleSimBilledWaitingTime))
        {
            try {
                setSiSiBilledWaitingTime(numberFormat.parse(objectContent).doubleValue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
    }

    @Override
    public void setIsStateType(StateType stateType) {
        if (!stateTypes.contains(stateType)) {
            switch (stateType) {
                case Abstract:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    exportTag = OWLTags.abstr;
                    exportClassname = "Abstract" + className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    if (isStateType(StateType.Finalized))
                        removeStateType(StateType.Finalized);
                    break;
                case Finalized:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    exportTag = OWLTags.abstr;
                    exportClassname = "Finalized" + className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    if (isStateType(StateType.Abstract))
                        removeStateType(StateType.Abstract);
                    break;
                default:
                        super.setIsStateType(stateType);
                    break;
            }
        }

    }

    @Override
    public void removeStateType(StateType stateType) {
        if (stateTypes.contains(stateType)) {
            switch (stateType) {
                case Abstract:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "Abstract" + getExportTag() + getClassName()));
                    stateTypes.remove(stateType);
                    exportTag = OWLTags.std;
                    exportClassname = className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    break;
                case Finalized:
                    removeTriple(new IncompleteTriple(OWLTags.rdfType, OWLTags.std + "Finalized" + getExportTag() + getClassName()));
                    stateTypes.remove(stateType);
                    exportTag = OWLTags.std;
                    exportClassname = className;
                    addTriple(new IncompleteTriple(OWLTags.rdfType, getExportTag() + getClassName()));
                    break;
                default:
                    super.removeStateType(stateType);
                    break;
            }
        }
    }
    public void setEndState(boolean isEndState)
    {
        if (isEndState)
        {
            if (!this.isStateType(StateType.EndState))
            {
                this.setIsStateType(StateType.EndState);
            }
        }
        else
        {
            if (this.isStateType(StateType.EndState))
            {
                this.removeStateType(StateType.EndState);
            }
        }
    }

    public boolean isEndState()
    {
        return this.isStateType(StateType.EndState);
    }


}