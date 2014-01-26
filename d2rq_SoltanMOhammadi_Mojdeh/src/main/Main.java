package main;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;

public class Main {

	public static void main(String[] args) {

		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

		FileManager.get().readModel(m, "ont9.ttl");

		m.write(System.out, "N3");


		Iterator<OntClass> cl = m.listClasses();

		System.out.println(m.size());
		
		Iterator<OntProperty> p = m.listOntProperties();
		
		/*while (cl.hasNext()) {
			OntClass c = cl.next();

			System.err.println(c.getLocalName());
			Iterator<Statement> ps = c.listProperties();
			while (ps.hasNext()) {
				Statement p = ps.next();
				System.out.println(p);
			}
		}//*/
		
		while (p.hasNext()){
			OntProperty s = p.next();
			System.out.println(s);
		}

	}

	void test() {

		// ModelTest x = new ModelTest();

		// x.toConsole();

		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

		FileManager.get().readModel(m, "ont8.ttl");

		m.write(System.out, "RDF/XML-ABBREV");

		if (true) {
			return;
		}

		String sparql1 = ModelD2rq.GetEntete()
				+ ModelD2rq.NL
				+ "SELECT DISTINCT ?villename ?regionname WHERE{"
				+ "?regobj vocab:region_region ?idreg ."
				+ " ?comobject vocab:cog_r_codeReg ?codereg . FILTER (?idreg = ?codereg)."
				+ "?comobject vocab:cog_r_ncc ?villename ."
				+ "?regobj vocab:region_ncc ?regionname  " + "} " + "LIMIT 100";

		String sparql2 = ModelD2rq.GetEntete() + ModelD2rq.NL
				+ "SELECT  DISTINCT ?villename WHERE { "
				+ "?comobject vocab:cog_r_codeInsee ?inseecom ."
				+ "?comobject vocab:cog_r_codeReg \"91\" ."
				+ "?comobject vocab:cog_r_ncc ?villename ." + "} "
				+ "LIMIT 100";

		String sparql3 = ModelD2rq.GetEntete()
				+ ModelD2rq.NL
				+ "SELECT DISTINCT ?depname (count(distinct ?comobj) as ?numbercom) WHERE{ "
				+ "?depobj vocab:departement_departement ?iddep."
				+ "?depobj vocab:departement_ncc ?depname."
				+ "?comobj vocab:cog_r_codeDep ?iddep" + "} "
				+ "GROUP BY ?depname";

		String sparql4 = ModelD2rq.GetEntete() + ModelD2rq.NL
				+ "SELECT DISTINCT ?regionname ?villename WHERE{ "
				+ "?regionobj vocab:region_cheflieu ?chefreg ."
				+ "?regionobj vocab:region_ncc ?regionname ."
				+ "?comobj vocab:cog_r_codeInsee ?chefreg ."
				+ "?comobj vocab:cog_r_ncc ?villename" + "} ";

		String sparql5 = ModelD2rq.GetEntete()
				+ ModelD2rq.NL
				+ "SELECT DISTINCT ?localiteename ?DepName ?RegName WHERE{ "
				+ "?localiteeibj vocab:localite_codeinsee ?localiteecode ."
				+ "?impotobj  vocab:impot_CodeInsee ?localiteecode ."
				+ "?arrondissement  vocab:arrondissement_municipal_codeInsee ?localiteecode . "
				+ "?arrondissement  vocab:arrondissement_municipal_codeCommune ?communecodeinsee ."
				+ "?communeobj  vocab:cog_r_codeInsee ?communecodeinsee ."
				+ "?communeobj  vocab:cog_r_codeReg ?codeReg ."
				+ "?communeobj  vocab:cog_r_codeDep ?codeDep ."
				+ "?regionobj vocab:region_region ?codeReg ."
				+ "?depobj vocab:departement_departement ?codeDep ."
				+ "?arrondissement vocab:arrondissement_municipal_narm ?localiteename ."
				+ "?depobj vocab:departement_ncc ?DepName ."
				+ "?regionobj vocab:region_ncc ?RegName ." + "} " + "LIMIT 100";

		/*
		 * sparql6:Nombre d'arrondissement par commune
		 */
		String sparql6 = ModelD2rq.GetEntete()
				+ ModelD2rq.NL
				+ "SELECT DISTINCT ?comName (count(distinct ?arrondissement) as ?numberArr) WHERE{ "
				+ "?arrondissement  vocab:arrondissement_municipal_codeCommune ?communecodeinsee ."
				+ "?communeobj  vocab:cog_r_codeInsee ?communecodeinsee ."
				+ "?communeobj vocab:cog_r_ncc ?comName ." + "} "
				+ "GROUP BY ?comName" + " LIMIT 100";

		Query query = QueryFactory.create(sparql6);
		query.serialize(System.out);
		QueryExecution qrs = QueryExecutionFactory.create(query, m);

		try {
			ResultSet rs = qrs.execSelect();
			ResultSetFormatter.out(System.out, rs, query);
			System.out.println(rs.getRowNumber());
		} catch (Exception e) {
			// TODO: handle exception
		}
		m.close();
	}
}
