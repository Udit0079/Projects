package com.cbs.web.utils;

import java.util.ResourceBundle;

/**
 * @author pnayak
 * 
 */
public class LocalizationUtil {

	public static final ResourceBundle resources;

	static {
		resources = ResourceBundle.getBundle("resources.ApplicationMessages");
	}

	/**
	 * returns localized text for given key.
	 * 
	 * @param key
	 * @return
	 */
	public static String getLocalizedText(String key) {
		String label = key;
		try {
			label = resources.getString(key);
		} catch (Exception e) {
			return label;
		}
		return label;
	}
}
