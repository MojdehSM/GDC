package JenaUtils;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;


public class Endpoint{
	private static final String prefix =
			"PREFIX geoloc: <http://localhost:9000/techweb/location/>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns/>"
			+ "PREFIX entity: <http://localhost:9000/techweb/entity#/>"
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#/>"
			+ "PREFIX city: <http://localhost:9000/techweb/city#/>"
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#/>"
			+ "PREFIX places: <http://localhost:9000/techweb#/>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#/>"
			;
	
	public void sparqlQuery(String query){
		
		
		
		 QueryExecution x = QueryExecutionFactory.create(prefix + query, SDBUtils.getModelSDB());
		 ResultSet results = x.execSelect();
		 ResultSetFormatter.out(System.out, results);
	}
	
}