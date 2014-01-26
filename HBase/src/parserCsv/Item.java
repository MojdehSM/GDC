package parserCsv;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import hbaseconnector.HbaseUtils;

public class Item {

	public static final String family = "populationFamily";
	public static final String table = "population";

	public String codeRegion;
	// public String nomRegion;
	public String codeDep;
	// public String codeArr;
	// public String codeCanton;
	public String codeCommune;
	public String nomCommune;
	// public String municipalePopulation;
	// public String otherPopulation;
	public String totalePopulation;

	@Override
	public String toString() {
		return codeRegion + " - " + codeDep + " - " + codeCommune + " - " + nomCommune + " - " + totalePopulation;
	}

	public void save() {
		String codeInsee = codeDep + codeCommune;
		HbaseUtils.putRaw(codeInsee, family, "codeRegion", codeRegion);
		HbaseUtils.putRaw(codeInsee, family, "codeDep", codeDep);
		HbaseUtils.putRaw(codeInsee, family, "codeCommune", codeCommune);
		HbaseUtils.putRaw(codeInsee, family, "nomCommune", nomCommune);
		HbaseUtils.putRaw(codeInsee, family, "totalePopulation", totalePopulation);
	}

	public static Item fromRaw(String raw) {
		Result res = HbaseUtils.getRaw(raw);
		Item item = new Item();
		item.codeRegion = Bytes.toString(res.getValue(family.getBytes(), "codeRegion".getBytes()));
		item.codeDep = Bytes.toString(res.getValue(family.getBytes(), "codeDep".getBytes()));
		item.codeCommune = Bytes.toString(res.getValue(family.getBytes(), "codeCommune".getBytes()));
		item.nomCommune = Bytes.toString(res.getValue(family.getBytes(), "nomCommune".getBytes()));
		item.totalePopulation = Bytes.toString(res.getValue(family.getBytes(), "totalePopulation".getBytes()));
		return item;
	}

}
