package org.techhouse.shirts.display.web.components;

import java.net.URL;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.display.web.behaviors.InlineJavascript;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;

public class DetailsOverlay extends Panel {

	private static final long serialVersionUID = 1L;
	
	public DetailsOverlay(final String id, final IModel<Design> design) {
		super(id, new CompoundPropertyModel<Design>(design));
		setVisible(false);
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
		
		final WebMarkupContainer mask = new WebMarkupContainer("mask");
		/*mask.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				hide(target);
			}
		});*/
		add(mask);
		
		mask.add(new Label("name").add(new SetCssClassToWicketIdBehavior()));
		mask.add(new Label("artist").add(new SetCssClassToWicketIdBehavior()));
		mask.add(new Label("year").add(new SetCssClassToWicketIdBehavior()));
		
		final PropertyModel<URL> photographSrcModel = new PropertyModel<URL>(getDefaultModel(), "photograph");
		mask.add(new Image("photograph"){
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible() {
					return photographSrcModel.getObject() != null;
				}
			}
			.add(new AttributeModifier("src", photographSrcModel))
			.add(new SetCssClassToWicketIdBehavior()));
		
		mask.add(new Label("note"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return getDefaultModelObject() != null;
			}
		}.add(new SetCssClassToWicketIdBehavior()));
		
		mask.add(new AjaxLink<Void>("close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				hide(target);
			}
		}.add(new SetCssClassToWicketIdBehavior()));
		
//		mask.add(new InlineJavascript("$$('#"+mask.getMarkupId()+" .content')[0].on('click', function(event, target) { event.stop; alert('stopped event'); });"));
		mask.add(new InlineJavascript("(function(){ var detailsOverlay = $('"+getMarkupId()+"'); detailsOverlay.on('click', function(event, target) { detailsOverlay.hide(); console.log('hiding '+target.identify()); });})();", "hideMask"));
	}
	
	public void hide(AjaxRequestTarget target){
		setVisible(false);
		target.addComponent(DetailsOverlay.this);
	}

}
