var java = require("java");

// classpath
java.classpath.push('./neo4j-java-driver-1.4.4.jar');
java.classpath.push('../target/materialisation_neo4j-1.0-SNAPSHOT.jar');

var Test = java.newInstanceSync("esilv/quelhasu/materialisation_neo4j/Main");