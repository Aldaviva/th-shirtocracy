package org.techhouse.shirts.display.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VoteButton extends AjaxFallbackLink<Void> {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(VoteButton.class);
	private static final String SELECTED_CSS_CLASS = "selected";
	
	private final IModel<Boolean> isSelectedModel;

	public VoteButton(String id, IModel<Boolean> isSelectedModel) {
		super(id);
		this.isSelectedModel = isSelectedModel;
		
		add(new AttributeAppender("class", true, new VoteButtonClassModel(), " "));
		setOutputMarkupId(true);
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		isSelectedModel.setObject(!isSelectedModel.getObject());
		if(target != null){
			target.addComponent(this);
		}
	}
	
	private final class VoteButtonClassModel extends AbstractReadOnlyModel<String> {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getObject() {
			if(isSelectedModel.getObject() == null){
				LOGGER.warn("isSelectedModel returned a null object, should be TRUE or FALSE");
				return "ERROR";
			}
			if(Boolean.TRUE.equals(isSelectedModel.getObject())){
				return SELECTED_CSS_CLASS;
			} else {
				return "";
			}
		}
	}
	
}
