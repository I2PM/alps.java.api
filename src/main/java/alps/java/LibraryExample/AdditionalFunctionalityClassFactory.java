package alps.java.LibraryExample;

import alps.java.api.parsing.BasicPASSProcessModelElementFactory;
import alps.java.api.parsing.IParseablePASSProcessModelElement;

import java.util.Map;

/**
 * This factory is passed to the library parser.
 * It is consulted and has to decide for one class when the parser finds multiple available parsing options.
 */
public class AdditionalFunctionalityClassFactory extends BasicPASSProcessModelElementFactory {
    @Override
    protected Map.Entry<IParseablePASSProcessModelElement, String> decideForElement
            (Map<IParseablePASSProcessModelElement, String> possibleElements)
    {
        // Always chose IAdditionalFunctionalityElements over standard classes,
        // if no IAdditionalFunctionalityElement is available let the standard implementation decide
        for(Map.Entry<IParseablePASSProcessModelElement, String> pair: possibleElements.entrySet())
        {
            if (pair.getKey() instanceof IAdditionalFunctionalityElement) return pair;
        }
        return super.decideForElement(possibleElements);
    }
}
