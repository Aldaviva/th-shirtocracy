package org.techhouse.shirts.display.web.pages;

import java.net.URL;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.techhouse.shirts.data.entities.Design;

public class BallotPage extends BasePage {

	final IModel<List<Design>> designsModel = new LoadableDetachableModel<List<Design>>() {
		private static final long serialVersionUID = 1L;

		@Override
		protected List<Design> load() {
			return Design.findAllDesigns();
		}
	}; 
	
	public BallotPage() {
		super();
		
		add(new ListView<Design>("designListView", designsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Design> item) {
				item.setModel(new CompoundPropertyModel<Design>(item.getModelObject()));
				item.add(new Image("thumbnail").add(new AttributeModifier("src", new PropertyModel<URL>(item.getModel(), "thumbnail"))));
				item.add(new Label("name"));
				item.add(new Label("artist"));
				item.add(new Label("year"));
			}
		});
	}
	
}
