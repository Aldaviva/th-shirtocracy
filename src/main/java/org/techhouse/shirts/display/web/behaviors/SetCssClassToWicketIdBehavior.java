package org.techhouse.shirts.display.web.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.parser.XmlTag;


public class SetCssClassToWicketIdBehavior extends AbstractBehavior{

	private static final long serialVersionUID = 1L;
	private static final String ATTRIBUTE = "class";
	private static final String SEPARATOR = " ";

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		if (tag.getType() != XmlTag.CLOSE) {
			String attribute = tag.getAttribute(ATTRIBUTE);
			if(attribute == null){
				attribute = "";
			} else {
				attribute += SEPARATOR;
			}
			attribute += component.getId();
			tag.getAttributes().put(ATTRIBUTE, attribute);
		}
	}

	
	

}
