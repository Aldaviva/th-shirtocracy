package org.techhouse.shirts.display.web.components;

import java.net.URL;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
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
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;

public class DetailsOverlay extends Panel {

	private static final long serialVersionUID = 1L;
	
	public DetailsOverlay(final String id, final IModel<Design> design) {
		super(id, new CompoundPropertyModel<Design>(design));
		setVisible(false);
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
		
		final WebMarkupContainer mask = new WebMarkupContainer("mask");
		mask.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				hide(target);
			}
		});
		add(mask);
		
		mask.add(new Label("name").add(new SetCssClassToWicketIdBehavior()));
		mask.add(new Label("artist").add(new SetCssClassToWicketIdBehavior()));
		mask.add(new Label("year").add(new SetCssClassToWicketIdBehavior()));
		
		mask.add(new Image("photograph").add(new AttributeModifier("src", new PropertyModel<URL>(getDefaultModel(), "photograph"))).add(new SetCssClassToWicketIdBehavior()));
		
		mask.add(new Label("notes", "NOTES").add(new SetCssClassToWicketIdBehavior()));
		
		mask.add(new AjaxLink<Void>("close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				hide(target);
			}
		}.add(new SetCssClassToWicketIdBehavior()));
	}
	
	public void hide(AjaxRequestTarget target){
		setVisible(false);
		target.addComponent(DetailsOverlay.this);
	}

}
