package org.techhouse.shirts.display.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.IBehavior;

public abstract class SavablePage extends BasePage {

	private final AjaxFallbackLink<Void> saveLink;

	public SavablePage() {
		super();
		
		add(saveLink = new AjaxFallbackLink<Void>("save"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
			}
		});
	}
	
	protected void addSaveBehavior(IBehavior behavior){
		saveLink.add(behavior);
	}

}