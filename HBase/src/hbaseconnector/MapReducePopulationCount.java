package hbaseconnector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;

import parserCsv.Item;

public class MapReducePopulationCount {

	public enum KindSearch {
		TOTALE, REGION, DEP, UNKNOWN
	}

	static KindSearch kind;
	static Map<String, Integer> results = new HashMap<>();

	static class Mapper1 extends TableMapper<ImmutableBytesWritable, IntWritable> {

		@Override
		public void map(ImmutableBytesWritable row, Result values, Context context) throws IOException {

			String code = "Totale";
			String pop = Bytes.toString(values.getValue(Item.family.getBytes(), "totalePopulation".getBytes()));

			switch (kind) {
			case REGION:
				code = Bytes.toString(values.getValue(Item.family.getBytes(), "codeRegion".getBytes()));
				break;

			case DEP:
				code = Bytes.toString(values.getValue(Item.family.getBytes(), "codeDep".getBytes()));
				break;

			case TOTALE:
				break;

			case UNKNOWN:
				break;

			default:
				break;
			}

			int population = 0;
			try {
				population = Integer.parseInt(pop);
			} catch (NumberFormatException e) {

			}

			ImmutableBytesWritable userKey = new ImmutableBytesWritable(code.getBytes());
			try {
				context.write(userKey, new IntWritable(population));
			} catch (InterruptedException e) {
				throw new IOException(e);
			}
		}
	}

	public static class Reducer1 extends TableReducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> {

		public void reduce(ImmutableBytesWritable key, Iterable<IntWritable> values, Context context) throws IOException,
				InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			//System.err.println(Bytes.toString(key.get()) + " -> " + sum);
			results.put(Bytes.toString(key.get()), sum);
		}
	}

	public static void config(KindSearch kin) {
		kind = kin;
		results.clear();
	}

	public static Map<String, Integer> populationCounter() {
		Configuration conf = HbaseUtils.hc;
		Job job = null;
		try {
			job = new Job(conf, "PopulationCounter");
		} catch (IOException e) {
			e.printStackTrace();
			return results;
		}
		job.setJarByClass(MapReducePopulationCount.class);
		Scan scan = new Scan();

		switch (kind) {
		case REGION:
			scan.addColumn(Item.family.getBytes(), "codeRegion".getBytes());
			break;

		case DEP:
			scan.addColumn(Item.family.getBytes(), "codeDep".getBytes());
			break;

		case TOTALE:
			break;

		case UNKNOWN:
			System.err.println(" UNKNON PLEAS CALL    config first");
			return results;

		default:
			break;
		}

		scan.addColumn(Item.family.getBytes(), "totalePopulation".getBytes());

		try {
			TableMapReduceUtil.initTableMapperJob(Item.table, scan, Mapper1.class, ImmutableBytesWritable.class, IntWritable.class, job);
			TableMapReduceUtil.initTableReducerJob(Item.table, Reducer1.class, job);
			job.waitForCompletion(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return results;
	}
}
