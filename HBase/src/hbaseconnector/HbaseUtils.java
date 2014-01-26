package hbaseconnector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

public class HbaseUtils {

	static Configuration hc = HBaseConfiguration.create();
	public static HTable htable;

	public static void setHtable(String htable) {
		try {
			HbaseUtils.htable = new HTable(hc, htable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create table
	 * 
	 * @param name
	 * @param family
	 */
	public static void hbaseconfig(String name, String... family) {
		List<String> ls = new ArrayList<String>();
		for (String string : family) {
			ls.add(string);
		}
		if (htable == null) {
			createtableIfNotExist(name, ls);
		}
	}

	/**
	 * Create table
	 * 
	 * @param name
	 * @param family
	 */
	public static void createtableIfNotExist(String name, List<String> family) {
		HTableDescriptor ht = null;

		try {
			HBaseAdmin hba = new HBaseAdmin(hc);

			for (HTableDescriptor exist : hba.listTables()) {
				if (exist.getNameAsString().equals(name)) {
					ht = exist;
					for (String fam : family) {
						ht.addFamily(new HColumnDescriptor(fam));
					}
					setHtable(name);
				}
			}

			if (ht == null) {
				ht = new HTableDescriptor(TableName.valueOf(name));
				for (String fam : family) {
					ht.addFamily(new HColumnDescriptor(fam));
				}
				hba.createTable(ht);
				setHtable(name);
			}

		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserer une ligne
	 * 
	 * @param table
	 * @param raw
	 * @param family
	 * @param qualifier
	 * @param value
	 */
	public static void putRaw(String raw, String family, String qualifier, String value) {
		Put put1 = new Put(new String(raw).getBytes());
		put1.add(new String(family).getBytes(), new String(qualifier).getBytes(), new String(value).getBytes());

		try {
			htable.put(put1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Result getRaw(String raw) {

		Get get = new Get(raw.getBytes());
		try {
			return htable.get(get);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
