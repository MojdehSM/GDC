package main;

import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class ModelD2rq {
	static Model model = null;
	public static final String NL = System.getProperty("line.separator");

	static String GetEntete() {
		return " PREFIX db: <http://localhost/db#>" + NL
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + NL
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + NL
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + NL
				+ "PREFIX ville: <http://localhost/ville#>" + NL
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ NL + "PREFIX vocab: <http://localhost/vocab#>"+ NL;

	}

	public static Model get() {
		if (model == null) {
			model = new ModelD2RQ("ont9.ttl");
			// model = FileManager.get().loadModel("ont2.ttl");
			//model.write(System.out, "RDF/XML-ABBREV");
			
		}
		return model;
	}

}
