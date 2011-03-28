package org.techhouse.shirts.display.web.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.util.string.JavascriptUtils;

public class InlineJavascript extends AbstractBehavior {

	private static final long serialVersionUID = 1L;
	
	private final CharSequence scriptContent;
	private final String uniqueId;
	
	public InlineJavascript(CharSequence scriptContent) {
		this.scriptContent = scriptContent;
		this.uniqueId = null;
	}

	public InlineJavascript(CharSequence scriptContent, final String uniqueId) {
		this.scriptContent = scriptContent;
		this.uniqueId = uniqueId;
	}

	@Override
	public void onRendered(Component component) {
		JavascriptUtils.writeJavascript(component.getResponse(), scriptContent, uniqueId);
	}

}
