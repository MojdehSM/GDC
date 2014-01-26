package JenaUtils;

import googleplaces.GooglePlaceType;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import utils.OntClassType;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;

public class ModelFactoryPlaces {
	private OntModel model;
	private InfModel inf;
	private String namespace = "http://localhost:9000/techweb#";
	private String ns_city = "http://localhost:9000/techweb/city#";
	private String ns_entity = "http://localhost:9000/techweb/entity#";
	private String ns_location = "http://localhost:9000/techweb/location#";

	private OntClass city;
	private OntClass entity;
	private OntClass location;

	private OntClass transport;
	private OntClass food;
	private OntClass lodging;
	private OntClass health;
	private OntClass money;
	private OntClass religion;
	private OntClass loisirs;
	private OntClass study;

	static ModelFactoryPlaces singleton = null;

	private ModelFactoryPlaces() {
		CreateIfNotExistOntologie();
	}

	public OntModel getModel() {
		return model;
	}

	public InfModel getInfModel() {
		return inf;
	}

	public static ModelFactoryPlaces getMPlaces() {
		if (singleton == null) {
			singleton = new ModelFactoryPlaces();
		}
		return singleton;

	}

	/**
	 * Creation of Ontologie if does not exist
	 */
	public void CreateIfNotExistOntologie() {

		model = SDBUtils.getModelSDB();
		Iterator<OntClass> cl = model.listClasses();

		/*
		 * Si La base est deja creer
		 */
		if (cl.hasNext()) {
			System.out.println("Getting existing ");
			do {
				OntClass c = cl.next();

				OntClassType type = OntClassType.valueOf(c.getLocalName());
				switch (type) {
				case city:
					city = c;
					break;
				case transport:
					transport = c;
					break;
				case entity:
					entity = c;
					break;
				case food:
					food = c;
					break;
				case location:
					location = c;
					break;
				case lodging:
					location = c;
					break;
				case loisirs:
					loisirs = c;
					break;
				case health:
					health = c;
					break;
				case money:
					money = c;
					break;
				case religion:
					religion = c;
					break;
				case study:
					study = c;
					break;
				default:
					break;
				}

				System.err.println(c.getLocalName());
				Iterator<OntProperty> pso = c.listDeclaredProperties();
				while (pso.hasNext()) {
					OntProperty p = pso.next();
					System.out.println(p.getLocalName());
				}

			} while (cl.hasNext());
		}

		/* sinon on la cree */
		else {
			System.out.println("Creating Ont Class ");
			CreateOntClasses();
		}
		// infGeneration();
	}

	/**
	 * Create Ont Classes of Places Model
	 */
	public void CreateOntClasses() {

		model.setNsPrefix("places", namespace);
		model.setNsPrefix("geoloc", ns_location);
		model.setNsPrefix("city", ns_city);
		model.setNsPrefix("entity", ns_entity);

		city = model.createClass(namespace + "city");

		entity = model.createClass(namespace + "entity");
		location = model.createClass(namespace + "location");

		transport = model.createClass(namespace + "transport");
		food = model.createClass(namespace + "food");
		lodging = model.createClass(namespace + "lodging");
		loisirs = model.createClass(namespace + "loisirs");
		religion = model.createClass(namespace + "religion");
		health = model.createClass(namespace + "health");
		money = model.createClass(namespace + "money");
		study = model.createClass(namespace + "study");

		city = model.createClass(namespace + "city");

		entity = model.createClass(namespace + "entity");
		location = model.createClass(namespace + "location");

		// model.add(location,OWL.equivalentClass,"http://dbpedia.org/ontology/Place");

		transport = model.createClass(namespace + "transport");
		food = model.createClass(namespace + "food");
		lodging = model.createClass(namespace + "lodging");

		loisirs = model.createClass(namespace + "loisirs");
		// model.add(loisirs,OWL.equivalentClass,"http://dbpedia.org/ontology/loisirs");
		religion = model.createClass(namespace + "religion");
		// model.add(religion,OWL.equivalentClass,"http://dbpedia.org/ontology/religion");
		health = model.createClass(namespace + "health");
		// model.add(health,OWL.equivalentClass,"http://dbpedia.org/ontology/health");
		money = model.createClass(namespace + "money");
		// model.add(money,OWL.equivalentClass,"http://dbpedia.org/ontology/money");
		study = model.createClass(namespace + "study");
		// model.add(study,OWL.equivalentClass,"http://dbpedia.org/ontology/study");

		AddCityProperties();
		AddEntityProperty();
		AddLocationProperty();
		AddcityLocationProperty();
		AddEntityLocationProperty();
		AddSubClasses();

		// infGeneration();
	}

	public OntProperty CreateProperty(OntClass classe, String namespace,
			String propertyName, String comment, String label, Resource resource) {
		OntProperty property = model
				.createOntProperty(namespace + propertyName);
		property.setDomain(classe);
		property.setRange(resource);
		property.addComment(comment, "fr");
		property.setLabel(label, "en");

		return property;
	}

	public ObjectProperty CreateObjectProperty(String propertyName,
			OntClass domaine, OntClass range, String comment, String label) {
		ObjectProperty ObjProperty = model.createObjectProperty(namespace
				+ propertyName);
		ObjProperty.setDomain(domaine);
		ObjProperty.setRange(range);
		ObjProperty.setComment(comment, "fr");
		ObjProperty.setLabel(label, "en");
		return ObjProperty;
	}

	void AddCityProperties() {
		city.addProperty(
				CreateProperty(city, ns_city, "name", "Le nom de la city",
						"city name", XSD.xstring), ns_city);
		city.addProperty(
				CreateProperty(city, ns_city, "formatted_address",
						"l'address de la city", "city address", XSD.xstring),
				ns_city);
		city.addProperty(
				CreateProperty(city, ns_city, "location",
						"la localisation de la city ", "city localisation",
						location), ns_city);

	}

	void AddEntityProperty() {

		entity.addProperty(
				CreateProperty(entity, ns_entity, "name", "le nom de l'entity",
						"Entity Name", XSD.xstring), ns_entity);
		entity.addProperty(
				CreateProperty(entity, ns_entity, "id",
						"l'identifiant de l'entity", "Entity Id", XSD.ID),
				ns_entity);
		entity.addProperty(
				CreateProperty(entity, ns_entity, "city",
						"la city de l'entity", "Entity city", city), ns_entity);
		entity.addProperty(
				CreateProperty(entity, ns_entity, "address",
						"l'adresse de l'entité", "Entity Adress", XSD.xstring),
				ns_entity);
	}

	void AddLocationProperty() {
		location.addProperty(
				CreateProperty(location, ns_location, "lat",
						"latitude de l'entity", "loc latitude", XSD.xdouble),
				ns_location);
		location.addProperty(
				CreateProperty(location, ns_location, "lng",
						"longitude de l'entity", "loc longitude", XSD.xdouble),
				ns_location);
	}

	void AddcityLocationProperty() {
		CreateObjectProperty("aPourLocation", city, location,
				"Localisation d'une city", "Localisation of a city");
	}

	void AddEntityLocationProperty() {
		CreateObjectProperty("aPourLocation", entity, location,
				"Localisation d'une entité", "Localisation of an entity");
	}

	void AddSubClasses() {

		entity.addSubClass(transport);
		entity.addSubClass(food);
		entity.addSubClass(lodging);
		entity.addSubClass(loisirs);
		entity.addSubClass(religion);
		entity.addSubClass(health);
		entity.addSubClass(money);
		entity.addSubClass(study);
	}

	/******************************************************************************************************************************
	 * AFFICHAGE DE L'ONTOLOGIE
	 *****************************************************************************************************************************/
	public void toConsole() {
		try {
			model.write(new OutputStreamWriter(System.out,"UTF8"), "RDF/XML-ABBREV");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void toConsoleInf() {
		getInfModel().write(System.out, "RDF/XML-ABBREV");
	}

	/**
	 * Get Airport OntClass
	 * 
	 * @return @{OntClass}
	 */
	public OntClass getTransport() {
		return transport;
	}

	/**
	 * Get City OntClass
	 * 
	 * @return @{OntClass}
	 */
	public OntClass getCity() {
		return city;
	}

	/**
	 * ....
	 * 
	 * @return
	 */
	public OntClass getFood() {
		return food;
	}

	public OntClass getEntity() {
		return entity;
	}

	public OntClass getLocation() {
		return location;
	}

	public OntClass getLodging() {
		return lodging;
	}

	public OntClass getLoisirs() {
		return loisirs;
	}

	public OntClass getHealth() {
		return health;
	}

	public OntClass getMoney() {
		return money;
	}

	public OntClass getReligion() {
		return religion;
	}

	/**
	 * Get Ont CLASS FROM GOOGLE PLACE TYPE
	 * 
	 * @param googleplacetype
	 * @return
	 */
	public OntClass getClassByString(String googleplacetype) {

		OntClass ret = null;
		try {
			GooglePlaceType name = GooglePlaceType.valueOf(googleplacetype);
			switch (name) {
			case city:
				ret = city;
				break;

			case church:
			case mosque:
			case hindu_temple:
				ret = religion;
				break;

			case amusement_park:
			case aquarium:
			case art_gallery:
			case bowling_alley:
			case florist:
			case gym:
			case zoo:
			case stadium:
			case museum:
			case movie_theater:
				ret = loisirs;
				break;

			case university:
			case school:
				ret = study;
				break;

			case food:
			case bar:
			case restaurant:
			case cafe:
				ret = food;
				break;

			case location:
				ret = location;
				break;

			case travel_agency:
			case lodging:
				ret = lodging;
				break;

			case accounting:
			case bank:
			case bicycle_store:
			case book_store:
			case beauty_salon:
			case casino:
			case department_store:
			case store:
			case clothing_store:
				ret = money;
				break;

			case doctor:
			case health:
			case hospital:
			case dentist:
			case pharmacy:
			case veterinary_care:
				ret = health;
				break;

			case train_station:
			case bus_station:
			case parking:
			case subway_station:
			case airport:
			case taxi_stand:
				ret = transport;
				break;

			case entity:
				break;

			default:
				ret = null;
			}

		} catch (Exception e) {
			System.out.println(" ONT CLASS Dont EXIST : " + googleplacetype);
		}

		return ret;

	}

	/**
	 * Get City NameSpace
	 * 
	 * @return
	 */
	public String getNs_city() {
		return ns_city;
	}

	/**
	 * Get Entity NameSpace
	 * 
	 * @return
	 */
	public String getNs_entity() {
		return ns_entity;
	}

	/**
	 * Get Location NameSpace
	 * 
	 * @return
	 */
	public String getNs_location() {
		return ns_location;
	}

	public String getNamespace() {
		return namespace;
	}

	// private void infGeneration(){
	// inf = ModelFactory.createRDFSModel(model);
	// inf.add(city,OWL.equivalentClass,"http://dbpedia.org/ontology/City");
	// }
	//
	// public void exec(){
	// String q ="PREFIX p: <http://localhost:9000/techweb/city#>"
	// +"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
	// +"PREFIX dbp:<http://dbpedia.org/ontology/>"
	// + " select * where { ?o  ?s \"http://dbpedia.org/ontology/City\"}";
	//
	// Query q1 = QueryFactory.create(q);
	// QueryExecution qexec = QueryExecutionFactory.create(q1, getInfModel());
	//
	// try {
	//
	// ResultSet rs = qexec.execSelect() ;
	// ResultSetFormatter.out(System.out, rs, q1);
	// }
	// finally
	// {
	// qexec.close() ;
	// }
	//
	// }

}
