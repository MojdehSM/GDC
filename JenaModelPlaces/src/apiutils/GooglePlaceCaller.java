package apiutils;

import googleplaces.City;
import googleplaces.SearchResult;
import JenaUtils.ModelFactoryPlaces;

import com.google.gson.GsonBuilder;
import com.hp.hpl.jena.ontology.OntClass;

public class GooglePlaceCaller {

	static final long towait = 2000;
	int radius = 5000;
	String serverurl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?sensor=false&location=";
	String key = "&key=AIzaSyD9_YnuHc2OkfKWfdVKMiMT2eDKqYdaNRQ";
	String type = "";

	String searchurl = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=";
	String nextpage = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?sensor=false&pagetoken=";

	public GooglePlaceCaller() {
	}

	public GooglePlaceCaller(int radius) {
		this.radius = radius;
	}

	private String getRadius() {
		return "&radius=" + radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * 
	 * @param cityName
	 * @return
	 */
	public City locationFromcityName(String cityName) {
		String uri = searchurl + cityName;

		// result contiens le contenu JSON
		String result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
		SearchResult r = new GsonBuilder().create().fromJson(result,
				SearchResult.class);
		City city = City.From(r);
		return city;
	}


	/**
	 * 
	 * @param cityName
	 * @return
	 */
	public City villeEntitiesFromWeb(String cityName) {
		City ret = locationFromcityName(cityName);

		//
		// if the result is not null
		//
		if (ret != null) {
			String uri = serverurl + ret.getLocationSearch() + type
					+ getRadius() + key;
			String result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
			 ret.Append(new GsonBuilder().create().fromJson(result, City.class));

			
			while (ret.getNext_page_token() != null) {
				uri = nextpage + ret.getNext_page_token() + key;

				try {
					System.out.println("Wait ...");
					Thread.sleep(towait);
				} catch (Exception e) {
					e.printStackTrace();
				}
				result = ApiCaller.cUrl(ApiCaller.getUrlFromString(uri));
				ret.Append(new GsonBuilder().create().fromJson(result,
						City.class));
			}
			
			
			return ret;
		} else {
			try {
				throw new Exception("Many or Not exist city");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	/****************************************************************************************************
	 * RECHERCHE PAR TYPE
	 ****************************************************************************************************/
	public City villeEntitiesFromWebByTypes(String cityName, String type) {
		OntClass OC = ModelFactoryPlaces.getMPlaces().getClassByString(type);
		String result = OC.toString();
		return byType(cityName, type);
	}

	private City byType(String cityName, String typeName) {
		type = "&types=" + typeName;
		return villeEntitiesFromWeb(cityName);
	}

}
