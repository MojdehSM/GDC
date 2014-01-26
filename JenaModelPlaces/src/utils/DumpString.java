package utils;

import java.lang.reflect.Field;

public class DumpString {

	public static String dumpString(Object c) {

		String result = "";

		for (Field f : c.getClass().getDeclaredFields()) {
			boolean acc = false;
			if (!f.isAccessible()) {
				f.setAccessible(true);
				acc = true;
			}

			try {
				Object o = f.get(c);

				if (o == null) {
					result += f.getName() + " => null\n";
				} else {
					result += f.getName() + " => " + o.toString() + "\n";
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			if (acc) {
				f.setAccessible(false);
			}

		}

		return result;
	}
}
