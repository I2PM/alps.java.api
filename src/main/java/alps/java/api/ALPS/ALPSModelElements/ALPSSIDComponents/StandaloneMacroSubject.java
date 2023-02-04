package alps.java.api.ALPS.ALPSModelElements.ALPSSIDComponents;

import alps.java.api.ALPS.ALPSModelElements.IModelLayer;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.IMessageExchange;
import alps.java.api.StandardPASS.PassProcessModelElements.InteractiondescribingComponents.Subject;
import alps.java.api.StandardPASS.PassProcessModelElements.SubjectBehaviors.IMacroBehavior;
import alps.java.api.parsing.IParseablePASSProcessModelElement;
import alps.java.api.src.OWLTags;
import alps.java.api.util.IIncompleteTriple;
import alps.java.api.util.IncompleteTriple;

import java.util.List;
import java.util.Set;

public class StandaloneMacroSubject extends Subject implements IStandaloneMacroSubject
        {
private IMacroBehavior macroBehavior;

/**
 * Name of the class, needed for parsing
 */
            private final String className = "StandaloneMacroSubject";
@Override
public String getClassName()
        {
        return className;
        }
@Override
public IParseablePASSProcessModelElement getParsedInstance()
        {
        return new StandaloneMacroSubject();
        }

protected StandaloneMacroSubject() { }

            /**
             *
             * @param layer
             * @param labelForID
             * @param incomingMessageExchange
             * @param macroBehavior
             * @param outgoingMessageExchange
             * @param maxSubjectInstanceRestriction
             * @param comment
             * @param additionalLabel
             * @param additionalAttribute
             */
            //TODO: Konstruktor überladen
            public StandaloneMacroSubject(IModelLayer layer, String labelForID, Set<IMessageExchange> incomingMessageExchange,
                                          IMacroBehavior macroBehavior, Set<IMessageExchange> outgoingMessageExchange, int maxSubjectInstanceRestriction,
                                          String comment, String additionalLabel, List<IIncompleteTriple> additionalAttribute){
        super(layer, labelForID, incomingMessageExchange, outgoingMessageExchange, maxSubjectInstanceRestriction,
        comment, additionalLabel, additionalAttribute);
        setBehavior(macroBehavior);
        }

public void setBehavior(IMacroBehavior behavior, int removeCascadeDepth)
        {
        IMacroBehavior oldDef = this.macroBehavior;
        // Might set it to null
        this.macroBehavior = behavior;

        if (oldDef != null)
        {
        if (oldDef.equals(behavior)) return;
        oldDef.unregister(this, removeCascadeDepth);
        behavior.setSubject(null);
        removeTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, oldDef.getUriModelComponentID()));
        }

        if (behavior == null) return;

        publishElementAdded(behavior);
        behavior.register(this);
        behavior.setSubject(this);
        addTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
        }
            public void setBehavior(IMacroBehavior behavior)
            {
                IMacroBehavior oldDef = this.macroBehavior;
                // Might set it to null
                this.macroBehavior = behavior;

                if (oldDef != null)
                {
                    if (oldDef.equals(behavior)) return;
                    oldDef.unregister(this, 0);
                    behavior.setSubject(null);
                    removeTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, oldDef.getUriModelComponentID()));
                }

                if (behavior == null) return;

                publishElementAdded(behavior);
                behavior.register(this);
                behavior.setSubject(this);
                addTriple(new IncompleteTriple(OWLTags.stdContainsBehavior, behavior.getUriModelComponentID()));
            }
@Override
protected boolean parseAttribute(String predicate, String objectContent, String lang, String dataType, IParseablePASSProcessModelElement element)
        {
        if (element instanceof IMacroBehavior subjectBehavior && predicate.contains(OWLTags.containsBehavior))
        {
        setBehavior(subjectBehavior);
        return true;
        }

        return super.parseAttribute(predicate, objectContent, lang, dataType, element);
        }


public IMacroBehavior getBehavior()
        {
        return macroBehavior;
        }
        }
