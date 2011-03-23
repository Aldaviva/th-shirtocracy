package org.techhouse.shirts.display.web.converters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class URLConverter implements IConverter {

	private static final long serialVersionUID = 1L;
	
	public URL convertToObject(String value, Locale locale) {
		System.out.println("Converting to URL using my function.");
		if(value == null || value == ""){
			return null;
		}
		try {
			return new URL(value);
		} catch (MalformedURLException e) {
			throw new ConversionException("'" + value + "' is not a valid URL.");
		}
	}

	public String convertToString(Object value, Locale locale) {
		return value != null ? value.toString() : null;
	}
}