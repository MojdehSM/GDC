package main;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.XSD;


public class ModelTest {
        private OntModel model;
        private String namespace = "http://localhost:9000/techweb";
        private String ns_city = "http://localhost:9000/techweb#city";
        private String ns_entity = "http://localhost:9000/techweb#entity";
        private String ns_location = "http://localhost:9000/techweb#location";
        private String ns_airport = "http://localhost:9000/techweb#airport";
        private String ns_food = "http://localhost:9000/techweb#food";
        private String ns_lodging = "http://localhost:9000/techweb#lodging";
        private String ns_museum = "http://localhost:9000/techweb#museum";
        
        private OntClass city;
        private OntClass entity;
        private OntClass location;
        private OntClass airport;
        private OntClass food;
        private OntClass lodging;
        private OntClass museum;
        
        
        public ModelTest(){
                CreateOntologie();
        }
        
        /***************************************************************************************************************************
         * CREATION DU MODEL
         * *************************************************************************************************************************/
        public void CreateOntologie(){
                model = ModelFactory.createOntologyModel();
                model.createOntology (namespace);
                CreateOntClasses();
        }
        
        /******************************************************************************************************************************
         * CREATION DES CLASSES
         * ****************************************************************************************************************************/
        public void CreateOntClasses(){
                city = model.createClass (namespace + "#city");
                entity = model.createClass (namespace + "#entity");
                location = model.createClass (namespace + "#location");
                airport = model.createClass (namespace + "#airport");
                food = model.createClass (namespace + "#food");
                lodging = model.createClass (namespace + "#lodging");
                museum = model.createClass (namespace + "#museum");
                
                AddcityProperties();
                AddEntityProperty();
                AddLocationProperty();
                AddcityLocationProperty();
                AddEntityLocationProperty();
                AddSubClasses();
                toConsole();
        }
        
        /******************************************************************************************************************************
         * CREATION ET AJOUT DE PROPRIETEES
         *****************************************************************************************************************************/
        public OntProperty CreateProperty( OntClass classe, String namespace, String propertyName, String comment, String label, Resource resource) {
                OntProperty property = model.createOntProperty( namespace + propertyName);
                property.setDomain(classe);
                property.setRange(resource);
                property.addComment(comment, "fr");
                property.setLabel(label, "en");

                return property;
        }
        public ObjectProperty CreateObjectProperty(String propertyName, OntClass domaine, OntClass range, String comment, String label){
                ObjectProperty ObjProperty = model.createObjectProperty(namespace+propertyName);
                ObjProperty.setDomain(domaine);
                ObjProperty.setRange(range);
                ObjProperty.setComment(comment, "fr");
                ObjProperty.setLabel(label, "en");
                return ObjProperty;
        }
        public void AddcityProperties(){
                city.addProperty(CreateProperty(city, ns_city, "name", "Le nom de la city", "city name", XSD.xstring), ns_city);
                city.addProperty(CreateProperty(city, ns_city, "formatted_address", "l'address de la city", "city address", XSD.xstring), ns_city);
                city.addProperty(CreateProperty(city, ns_city, "location", "la localisation de la city ", "city localisation", location), ns_city);
        }
        
        public void AddEntityProperty(){
                
                entity.addProperty(CreateProperty(entity, ns_entity, "name", "le nom de l'entity", "Entity Name", XSD.xstring), ns_entity);
                entity.addProperty(CreateProperty(entity, ns_entity, "id", "l'identifiant de l'entity", "Entity Id", XSD.ID), ns_entity);
                entity.addProperty(CreateProperty(entity, ns_entity, "type", "le type de l'entity", "Entity Types", RDF.List), ns_entity);
                entity.addProperty(CreateProperty(entity, ns_entity, "address", "l'adresse de l'entité", "Entity Adress", XSD.xstring), ns_entity);
        }
        public void AddLocationProperty(){
                location.addProperty(CreateProperty(location, ns_location, "lat", "latitude de l'entity", "loc latitude", XSD.xdouble), ns_location);
                location.addProperty(CreateProperty(location, ns_location, "lng", "longitude de l'entity", "loc longitude", XSD.xdouble), ns_location);
        }
        
        public void AddcityLocationProperty(){
                CreateObjectProperty("aPourLocation", city, location,"Localisation d'une city","Localisation of a city");
        }
        public void AddEntityLocationProperty(){
                CreateObjectProperty("aPourLocation", entity, location,"Localisation d'une entité","Localisation of an entity");
        }
        
        public void AddSubClasses(){
                entity.addSubClass(airport);
                entity.addSubClass(food);
                entity.addSubClass(lodging);
                entity.addSubClass(museum);
        }
        /******************************************************************************************************************************
         * CREATION DES INSTANCES
         *****************************************************************************************************************************/
        /******************************************************************************************************************************
         * AFFICHAGE DE L'ONTOLOGIE
         *****************************************************************************************************************************/
        public void toConsole(){
                model.write(System.out,"N3");
        }
        
        }

