package org.techhouse.shirts.display.web.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.techhouse.shirts.data.entities.Design;

public class StatisticsPage extends BasePage {

	final IModel<List<Design>> designsModel = new LoadableDetachableModel<List<Design>>() {

		private static final long serialVersionUID = 1L;

		@Override
		protected List<Design> load() {
			return Design.findAllDesigns();
		}
	};

	public StatisticsPage() {
		super();

		add(new ListView<Design>("designList", designsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Design> designItem) {
				designItem.add(new Label("name", new PropertyModel<String>(designItem.getModelObject(), "name")));
				designItem.add(new Label("votes", new PropertyModel<Long>(designItem.getModelObject(), "countVotes()")));
			}
		});
	}

}
