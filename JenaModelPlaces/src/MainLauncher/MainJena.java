package MainLauncher;

import googleplaces.City;
import JenaUtils.ModelFactoryPlaces;
import JenaUtils.SDBUtils;
import apiutils.GooglePlaceCaller;



public class MainJena {
	public static void main(String[] args) {
		
		//GetFromWeb("Lyon");
		//inter.setSameAs(null);
		
		
		
		CreateJenaModel();
		//Endpoint ep = new Endpoint();

		//CreateJenaModel();
		//ModelFactoryPlaces MPF = ModelFactoryPlaces.getMPlaces();
		//MPF.exec();
	}
	
	
	public static void CreateJenaModel() {
		//SDBUtils.createSDBModel();
		//SDBUtils.emptySDBModel();
		ModelFactoryPlaces model = ModelFactoryPlaces.getMPlaces();
		model.toConsole();
	}

	public static City GetFromWeb(String city) {
		GooglePlaceCaller x = new GooglePlaceCaller(10000);
		City result =  x.villeEntitiesFromWeb(city);
		
		System.out.println(result);
		result.toIndividual();
		return result;
	}
	
	public static void GetFromWebByType(String city, String type) {
		GooglePlaceCaller x = new GooglePlaceCaller(10000);
		City r =  x.villeEntitiesFromWebByTypes(city, type);
/*		r.toIndividual();
		for(Entity ent : r.getResults()){
			System.out.println(ent.getName());
		}
*///		ModelFactoryPlaces.getMPlaces().toConsole();
	}
	
	
	}
