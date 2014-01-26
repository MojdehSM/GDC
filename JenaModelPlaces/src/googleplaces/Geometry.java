package googleplaces;

import utils.DumpString;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Resource;


public class Geometry {
   	private Location location;
   	
 	public Location getLocation(){
		return this.location;
	}
	public void setLocation(Location location){
		this.location = location;
	}
 	
	
	public Individual toIndividual(String id){
		if(location != null){
			return location.toIndividual(id);
		} else {
			return null;
		}
	}
	
	public static Geometry fromIndivdual(Resource ressource) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}
}
