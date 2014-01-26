package googleplaces;

import java.util.Iterator;

import utils.DumpString;

import JenaUtils.ModelFactoryPlaces;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Statement;

public class Location {
	private Number lat;
	private Number lng;

	public Number getLat() {
		return this.lat;
	}

	public void setLat(Number lat) {
		this.lat = lat;
	}

	public Number getLng() {
		return this.lng;
	}

	public void setLng(Number lng) {
		this.lng = lng;
	}

	public Individual toIndividual(String id) {

		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();

		Individual locationI = model.getEntity().createIndividual(
				model.getNs_entity() + id);

		Iterator<Statement> stmt = model.getEntity().listProperties();

		while (stmt.hasNext()) {
			Statement s = stmt.next();

			if (s.getPredicate().getLocalName().equals("lat")) {
				locationI.addProperty(s.getPredicate(), lat.toString());
			} else if (s.getPredicate().getLocalName().equals("lng")) {
				locationI.addProperty(s.getPredicate(), lng.toString());
			}
		}

		return locationI;

	}

	public Individual toIndividualCity(String id) {

		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();

		Individual locationI = model.getEntity().createIndividual(
				model.getNs_city()+ id);

		Iterator<Statement> stmt = model.getEntity().listProperties();

		while (stmt.hasNext()) {
			Statement s = stmt.next();

			if (s.getPredicate().getLocalName().equals("lat")) {
				locationI.addProperty(s.getPredicate(), lat.toString());
			} else if (s.getPredicate().getLocalName().equals("lng")) {
				locationI.addProperty(s.getPredicate(), lng.toString());
			}
		}

		return locationI;

	}
	
	
	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}

}
