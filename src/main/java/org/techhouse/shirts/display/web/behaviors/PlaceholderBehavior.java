package org.techhouse.shirts.display.web.behaviors;

import java.util.MissingResourceException;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaceholderBehavior extends AbstractBehavior {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaceholderBehavior.class);
	
	private static final long serialVersionUID = 1L;
	private static final String ATTRIBUTE = "placeholder";

	private String key;
	private String value;

	/**
	 * Use component's ID as key
	 */
	public PlaceholderBehavior() {
	}
	
	public PlaceholderBehavior(final String key) {
		this(false, key);
	}
	
	public PlaceholderBehavior(boolean isValue, String value) {
		if(isValue) {
			this.value = value;
		} else {
			this.key = value;
		}
	}

	@Override
	public void onComponentTag(final Component component, final ComponentTag tag) {
		if (isEnabled(component)) {
			if(value == null){
				if(key == null) {
					key = component.getId();
				}
				
				try {
					value = component.getString("placeholder."+key);
				} catch (final MissingResourceException e){
					value = "";
					LOGGER.warn("Missing resource (key = {})", key);
				}
			}
			
			tag.getAttributes().put(ATTRIBUTE, value);
		}
	}

}
