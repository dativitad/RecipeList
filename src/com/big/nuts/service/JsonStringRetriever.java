package com.big.nuts.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class JsonStringRetriever {
	
	private	String stringJSON = null;

	public String getJSONStrFromUrl(String url) {
		try {
			stringJSON = IOUtils.toString(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringJSON;
	}
	
}
