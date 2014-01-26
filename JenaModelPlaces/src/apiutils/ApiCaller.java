package apiutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public abstract class ApiCaller {

	public static String cUrl(URL url) {
		
		System.out.println(url);
		
		InputStream is = null;
		try {
			is = url.openConnection().getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is,  "UTF8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String result = "";
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(result);
		return result;
	}

	public static URL getUrlFromString(String url) {

		URL url_object = null;
		try {

			url_object = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("ERREUR URL" + url);
		}
		return url_object;

	}

	public static String urlEncode(String str) {
		String res = "";
		try {
			res = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return res;

	}

}
