package org.techhouse.shirts.display.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.entities.Member;


public class VoteButton extends AjaxFallbackButton {

	private static final long serialVersionUID = 1L;
	private final IModel<Design> designModel;

	public VoteButton(String id, IModel<Design> designModel) {
		super(id, null);
		this.designModel = designModel;
		add(new SimpleAttributeModifier("class", "voteButton"));
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		Member member = ((Form<Member>) form).getModelObject();
		if(target != null){
			
		}
	}

}
