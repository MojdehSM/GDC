package parserCsv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CsvParser {

	String path;
	String deliminator = "\t";

	public CsvParser(String filename, String deliminator) {
		path = filename;
		this.deliminator = deliminator;
	}

	public List<Item> parse() {

		List<Item> lst = new LinkedList<>();
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(path));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] strs = sCurrentLine.split(deliminator);
				// for (String str : strs) {
				// System.err.print(str + " - ");
				// }
				// System.err.println();

				Item m = new Item();
				m.codeRegion = strs[0];
				// m.nomRegion = strs[1];
				m.codeDep = strs[2];
				// m.codeArr = strs[3];
				// m.codeCanton = strs[4];
				m.codeCommune = strs[5];
				m.nomCommune = strs[6];
				// m.municipalePopulation = strs[7];
				// m.otherPopulation = strs[8];
				m.totalePopulation = strs[9];
				lst.add(m);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return lst;

	}

}
