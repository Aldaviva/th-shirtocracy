package org.techhouse.shirts.display.web.behaviors;

import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class HeadJavascriptReference extends AbstractBehavior {

	private static final long serialVersionUID = 1L;
	private final String filename;

	public HeadJavascriptReference(final String filename) {
		this.filename = filename;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(filename);
	}
}
