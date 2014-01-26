package interconnexion;

import googleplaces.City;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;

public class DbPediaConnexion {

	public static String dpPediaNamespace = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
			+ "\n"
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
			+ "\n"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "\n"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "\n"
			+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
			+ "\n"
			+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
			+ "\n"
			+ "PREFIX : <http://dbpedia.org/resource/>"
			+ "\n"
			+ "PREFIX dbpprop: <http://dbpedia.org/property/>"
			+ "\n"
			+ "PREFIX dbpedia: <http://dbpedia.org/>"
			+ "\n"
			+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"
			+ "\n"
			+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>";

	/**
	 * Execute Request
	 * 
	 * @param query
	 * @return
	 */
	static ResultSet executeQuerySparql(String query) {

		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://dbpedia.org/sparql", query);

		return qexec.execSelect();
	}

	/**
	 * Get Same As DbPedia
	 * 
	 * @param city
	 * @return
	 */
	public static Resource getSameAsFromDpPedia(City city) {
		ResultSet rest = executeQuerySparql(CreateQuerySparqlForDbpedia(city));
		try {
			while (rest.hasNext()) {
				QuerySolution sol = rest.next();
				System.err.println(sol.getResource("s"));
				return sol.getResource("s");
			}
		} catch (Exception e) {
			System.err.println("Impossible to find Resource for " + city + "!");
			return null;
		}
		return null;
	}

	/**
	 * Create Query From City
	 * 
	 * @param city
	 * @return
	 */
	static String CreateQuerySparqlForDbpedia(City city) {
		String q = " SELECT ?s WHERE {" + "?s dbpprop:name ?p ." + "\n"
				+ "FILTER regex(?p, \"^" + city.getShortName() + "$\", \"i\")."
				+ "\n" + "?s rdf:type dbpedia-owl:PopulatedPlace ." + "\n"
				+ "?s dbpprop:latitude ?lat ." + "\n"
				+ "?s dbpprop:longitude ?lon ." + "\n" + "FILTER(ABS(?lat - "
				+ city.getLatitude() + ") <= 0.5 && " + "\n" + "ABS(?lon - "
				+ city.getLogitude() + ") <= 0.5 )" + "\n" + "} limit 10";

		q = dpPediaNamespace + q;
		System.out.println(q);
		return q;
	}
}
