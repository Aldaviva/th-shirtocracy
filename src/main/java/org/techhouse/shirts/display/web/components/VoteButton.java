package org.techhouse.shirts.display.web.components;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.display.web.behaviors.HeadJavascriptReference;
import org.techhouse.shirts.display.web.behaviors.InlineJavascript;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;

public class VoteButton extends Panel {

	private static final long serialVersionUID = 1L;
	private static final String CHECK_ID = "check";
	private static final String BUTTON_ID = "voteButton";
	
	private final Check<Design> check;
	private final WebMarkupContainer button;
	
	public VoteButton(String id, IModel<Design> model, CheckGroup<Design> checkGroup) {
		super(id, model);
		
		check = new Check<Design>(CHECK_ID, model, checkGroup);
		check.setOutputMarkupId(true);
		add(check);
		
		button = new WebMarkupContainer(BUTTON_ID);
		button.setOutputMarkupId(true);
		button.add(new SetCssClassToWicketIdBehavior());
		add(button);

		add(new HeadJavascriptReference("scripts/VoteButton.js"));
		add(new InlineJavascript("new VoteButton('"+check.getMarkupId()+"', '"+button.getMarkupId()+"');"));
	}
}