package main;

import hbaseconnector.HbaseUtils;
import hbaseconnector.MapReducePopulationCount;
import hbaseconnector.MapReducePopulationCount.KindSearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import parserCsv.CsvParser;
import parserCsv.Item;

public class Main {
	public static void main(String args[]) throws IOException {
		HbaseUtils.hbaseconfig(Item.table, Item.family);

		// parseTest();
		// mapReduceTest();
		getRawTest();
	}

	public static void getRawTest() {
		System.out.println(Item.fromRaw("34172"));
	}

	public static void mapReduceTest() {
		MapReducePopulationCount.config(KindSearch.REGION);
		Map<String, Integer> res = MapReducePopulationCount.populationCounter();

		for (Entry<String, Integer> pair : res.entrySet()) {
			System.out.println(pair.getKey() + " -> " + pair.getValue());
		}

		MapReducePopulationCount.config(KindSearch.TOTALE);
		res = MapReducePopulationCount.populationCounter();

		for (Entry<String, Integer> pair : res.entrySet()) {
			System.out.println(pair.getKey() + " -> " + pair.getValue());
		}

		MapReducePopulationCount.config(KindSearch.DEP);
		res = MapReducePopulationCount.populationCounter();

		for (Entry<String, Integer> pair : res.entrySet()) {
			System.out.println(pair.getKey() + " -> " + pair.getValue());
		}

	}

	public static void parseTest() {
		CsvParser pars = new CsvParser("ensemble1.csv", ":");
		List<Item> lst = pars.parse();

		for (Item item : lst) {
			System.out.println(item);
			item.save();
		}

	}
}
