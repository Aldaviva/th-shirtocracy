package org.techhouse.shirts.display.web.behaviors;

import org.apache.wicket.behavior.SimpleAttributeModifier;


public class PlaceholderBehavior extends SimpleAttributeModifier {

	private static final long serialVersionUID = 1L;
	private static final String ATTRIBUTE = "placeholder";

	public PlaceholderBehavior(CharSequence value) {
		super(ATTRIBUTE, value);
	}

}
