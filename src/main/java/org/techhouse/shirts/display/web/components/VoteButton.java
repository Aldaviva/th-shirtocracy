package org.techhouse.shirts.display.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.techhouse.shirts.data.entities.Design;


public class VoteButton extends AjaxFallbackButton {

	private static final long serialVersionUID = 1L;
	private static final String SELECTED_CSS_CLASS = "selected";
	
//	private final IModel<Design> designModel;
	private final IModel<Boolean> isSelectedModel;
//	private final IConverter converter = new VoteButtonConverter();

	public VoteButton(String id, IModel<Design> designModel, IModel<Boolean> isSelectedModel) {
		super(id, null);
//		this.designModel = designModel;
		this.isSelectedModel = isSelectedModel;
		
		add(new AttributeAppender("class", true, new VoteButtonClassModel(), " "));
		setDefaultFormProcessing(false);
		setOutputMarkupId(true);
	}

	/*@Override
	protected boolean supportsPersistence() {
		return true;
	}*/

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if(target != null){
			target.addComponent(this);
		}
	}

	/*@Override
	public IConverter getConverter(Class<?> type) {
		if(Design.class.equals(type)){
			return converter;
		} else {
			return super.getConverter(type);
		}
	}*/
	
	private final class VoteButtonClassModel extends AbstractReadOnlyModel<String> {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getObject() {
			if(isSelectedModel.getObject()){
				return SELECTED_CSS_CLASS;
			} else {
				return "";
			}
		}
	}

	/*private final class VoteButtonConverter implements IConverter {

		private static final long serialVersionUID = 1L;

		@Override
		public Object convertToObject(String value, Locale locale) {
			if(isSelectedModel.getObject()){
				return VoteButton.this.designModel.getObject();
			} else {
				return null;
			}
		}

		@Override
		public String convertToString(Object value, Locale locale) {
			return null;
		}
		
	}*/
	
}
