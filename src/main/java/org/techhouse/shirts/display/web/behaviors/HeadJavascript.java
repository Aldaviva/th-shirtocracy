package org.techhouse.shirts.display.web.behaviors;

import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class HeadJavascript extends AbstractBehavior {

	private static final long serialVersionUID = 1L;
	
	private final CharSequence scriptContent;

	public HeadJavascript(CharSequence scriptContent) {
		this.scriptContent = scriptContent;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderJavascript(scriptContent, null); //TODO: null here may cause duplicates upon Ajax updates
	}

}
