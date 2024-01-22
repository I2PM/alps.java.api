# alps.net.api
This Java library provides the functionality to create and modify in-memory PASS process models. These models might either be imported from an ontology (given in the owl/rdf format) or created from scratch.

The library currently supports the [Standard PASS](https://github.com/I2PM/Standard-PASS-Ontology) as well as the Abstract Layered PASS created by Matthes Elstermann. For more information have a look at the wiki pages or at the JavaDoc inside the classes.

//add where to get the library and which Frameworks it supports

# Supported Functionality
- Subject oriented in-memory modeling in C#
- Import models from OWL/RDF formatted files
  - Dynamic import supporting own OntologyClass definitions
  - Dynamic import supporting own C# class extensions
- Model modification by editing underlying triple store graph
- Export models to OWL/RDF formatted files
