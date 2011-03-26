package org.techhouse.shirts.display.web.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;

public class PlaceholderBehavior extends AbstractBehavior {

	private static final long serialVersionUID = 1L;
	private static final String ATTRIBUTE = "placeholder";

	private final String key;

	/**
	 * Use component's ID as key
	 */
	public PlaceholderBehavior() {
		key = null;
	}
	
	public PlaceholderBehavior(String key) {
		this.key = key;
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		if (isEnabled(component)) {
			String key = this.key;
			if(key == null) {
				key = component.getId();
			}
			tag.getAttributes().put(ATTRIBUTE, component.getString("placeholder."+key));
		}
	}

}
