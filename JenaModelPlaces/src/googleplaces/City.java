package googleplaces;

import googleplaces.SearchResult.Address_component;
import googleplaces.SearchResult.CityInfoResult;

import interconnexion.DbPediaConnexion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.DumpString;

import JenaUtils.ModelFactoryPlaces;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class City {
	private String next_page_token;
	private List<Entity> results = new ArrayList<Entity>();

	private String status;
	private String Identifier;
	private String locationSearch = "";

	private String shortName = "";
	private String address = "";
	private Location location;

	public String getLocationSearch() {
		return locationSearch;
	}

	public void setLocationSearch(String locationSearch) {
		this.locationSearch = locationSearch;
	}

	public String getAddress() {
		return address;
	}

	public String getLatitude() {
		return location.getLat().toString();
	}

	public String getLogitude() {
		return location.getLng().toString();
	}

	public String getShortName() {
		return shortName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}

	public String getIdentifier() {
		return Identifier;
	}

	public String getNext_page_token() {
		return this.next_page_token;
	}

	public void setNext_page_token(String next_page_token) {
		this.next_page_token = next_page_token;
	}

	public List<Entity> getResults() {
		return this.results;
	}

	public void setResults(List<Entity> results) {
		this.results = results;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void Append(City other) {
		for (Entity ent : other.getResults()) {
			results.add(ent);
		}
		next_page_token = other.getNext_page_token();
	}

	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}

	/**
	 * Convert To Jena
	 */
	public void toIndividual() {

		if (Identifier.equals("")) {
			System.err.println(" No Ville Selected");
			return;
		}

		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();

		Individual cityI = model.getCity().createIndividual(
				model.getNs_city() + Identifier);

		Iterator<Statement> stmt = model.getCity().listProperties();

		while (stmt.hasNext()) {
			Statement s = stmt.next();

			if (s.getPredicate().getLocalName().equals("name")) {
				if (address != null)
					cityI.addProperty(s.getPredicate(), getShortName());
			} else if (s.getPredicate().getLocalName()
					.equals("formatted_addres")) {
				cityI.addProperty(s.getPredicate(), getAddress());
			} else if (s.getPredicate().getLocalName().equals("location")) {
				cityI.addProperty(s.getPredicate(), location.toIndividualCity(Identifier));
			}
		}

		//
		// Create Entity of ville
		//
		for (Entity e : results) {
			e.toIndividual(cityI);
		}
		
		
		//
		// Interconnexion
		//
		Resource dbpediaResource = DbPediaConnexion.getSameAsFromDpPedia(this);
		if(dbpediaResource != null){
			cityI.setSameAs(dbpediaResource);
		}
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Create City From Search Result
	 * 
	 * @param cityInfo
	 * @return
	 */
	public static City From(SearchResult cityInfo) {
		City city = new City();

		//
		// If Request is Valide and there is a result
		//
		if (cityInfo.isOk() && cityInfo.getResults().size() > 0) {
			CityInfoResult infos = cityInfo.getResults().get(0);

			//
			// Set Identifier
			//
			String identifier = "";
			for (Address_component add : infos.getAddress_components()) {
				identifier += add.getLong_name() + "$";
			}
			identifier = identifier.substring(0, identifier.length() - 1);
			city.setIdentifier(identifier);

			//
			// set City Info Properties
			//
			if (infos.getAddress_components().size() > 0) {
				city.setShortName(infos.getAddress_components().get(0)
						.getLong_name());
			}

			//
			// Set Location
			//
			if (infos.getGeometry() != null) {
				if (infos.getGeometry().getLocation() != null) {
					city.setLocation(infos.getGeometry().getLocation());
				}
			}
			city.setAddress(infos.getFormatted_address());

			//
			// set Location Search
			//
			city.setLocationSearch(city.getLatitude() + ","
					+ city.getLogitude());
		}
		return city;
	}
}
