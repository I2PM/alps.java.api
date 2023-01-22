package alps.java.api.util;
import alps.java.api.parsing.PASSGraph;

public class StaticFunctions {
    public static final char BASE_URI_SEPARATOR = '#';

    /**
     *Replaces a base uri contained in a string with a generic placeholder.
     * Example: http://www.imi.kit.edu/exampleBaseURI#MyModel1 will return baseuri:MyModel1.
     * The baseuri name mapping is defined in the triple store graph.
     * This way, the base uri can be changed without changing all triples.
     * @param input The string that MAY contain a base uri
     * @return the string without specific base uri
     */

    public static String replaceBaseUriWithGeneric(String input, String baseuri) {
        String output = input;

        // If a base uri is contained, replace it with generic one
        if (output.contains(baseuri + BASE_URI_SEPARATOR)) {
            output = output.replace(baseuri + BASE_URI_SEPARATOR, PASSGraph.EXAMPLE_BASE_URI_PLACEHOLDER);
        }

        // If a base uri is contained, replace it with generic one
        if (output.equals(baseuri)) {
            output = baseuri;
        }
        return output;
    }

    /**
     * Replaces a base uri contained in a string with a generic placeholder.
     * Example: http://www.imi.kit.edu/exampleBaseURI#MyModel1 will return baseuri:MyModel1.
     * The baseuri name mapping is defined in the triple store graph.
     * This way, the base uri can be changed without changing all triples.
     * @param input
     * @param baseuri
     * @return
     */
    public static String removeBaseUri(String input, String baseuri) {
        String output = input;

        if (baseuri != null) {
            // If a base uri is contained, replace it with generic one
            if (output.contains(baseuri + BASE_URI_SEPARATOR)) {
                output = output.replace(baseuri + BASE_URI_SEPARATOR, "");
            }

            // If a base uri is contained, replace it with generic one
            if (output.contains(baseuri)) {
                output = output.replace(baseuri, "");
            }
        }

        // If a base uri is contained, replace it with generic one
        if (output.contains(PASSGraph.EXAMPLE_BASE_URI_PLACEHOLDER)) {
            output = output.replace(PASSGraph.EXAMPLE_BASE_URI_PLACEHOLDER, "");
        }

        return output;
    }

    public static String addGenericBaseURI(String input) {
        String output = input;

        if (!output.startsWith(PASSGraph.EXAMPLE_BASE_URI_PLACEHOLDER)) {
            output = PASSGraph.EXAMPLE_BASE_URI_PLACEHOLDER + output;
        }

        return output;
    }
}
