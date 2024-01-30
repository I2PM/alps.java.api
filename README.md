# alps.java.api
This Java library provides the functionality to create and modify in-memory PASS process models. These models might either be imported from an ontology (given in the owl/rdf format) or created from scratch.

The library currently supports the [Standard PASS](https://github.com/I2PM/Standard-PASS-Ontology) as well as the Abstract Layered PASS created by Matthes Elstermann. For more information have a look at the [wiki pages](https://github.com/sanjasm/alps.java.api/wiki), or at the [JavaDoc](https://docshoster.org/p/i2pm/alps.java.api/latest/introduction.html) hosted on dochoster or at the JavaDoc inside the classes (reason why the project mostly consists of html code).

# Supported Functionality
- Subject oriented in-memory modeling in Java
- Import models from OWL/RDF formatted files
  - Dynamic import supporting own OntologyClass definitions
  - Dynamic import supporting own Java class extensions
- Model modification by editing underlying triple store graph
- Export models to OWL/RDF formatted files
