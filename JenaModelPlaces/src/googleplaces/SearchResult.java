package googleplaces;

import java.util.ArrayList;
import java.util.List;

import utils.DumpString;


public class SearchResult {
	String status;
	List<CityInfoResult> results;

	public List<CityInfoResult> getResults() {
		return results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isOk() {
		return (status.equals("OK"));
	}

	public class CityInfoResult {
		Geometry geometry;

		List<Address_component> address_components = new ArrayList<Address_component>();

		String formatted_address;

		
		@Override
		public String toString() {
			return DumpString.dumpString(this);
		}
		
		public Geometry getGeometry() {
			return geometry;
		}

		public void setGeometry(Geometry geometry) {
			this.geometry = geometry;
		}

		public String getFormatted_address() {
			return formatted_address;
		}

		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}

		public void setAddress_components(
				List<Address_component> address_components) {
			this.address_components = address_components;
		}

		public List<Address_component> getAddress_components() {
			return address_components;
		}

	}

	/**
	 * Address Component
	 * 
	 * @author Mojdeh
	 * 
	 */
	public class Address_component {

		private String long_name;
		private String short_name;
		
		public String getLong_name() {
			return long_name;
		}

		public void setLong_name(String long_name) {
			this.long_name = long_name;
		}

		public String getShort_name() {
			return short_name;
		}

		public void setShort_name(String short_name) {
			this.short_name = short_name;
		}

				
		
		@Override
		public String toString() {
			return DumpString.dumpString(this);
		}

	}
	
	
	@Override
	public String toString() {
		return DumpString.dumpString(this);
	}


}
