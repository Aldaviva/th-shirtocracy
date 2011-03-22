package org.techhouse.shirts.display.web.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.query.SortParam;
import org.techhouse.shirts.display.web.security.AdminPage;

public class DesignListPage extends BasePage implements AdminPage {

	private final IModel<List<Design>> designsModel;

	public DesignListPage() {
		super();

		designsModel = new LoadableDetachableModel<List<Design>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Design> load() {
				return Design.findAllDesigns(new SortParam("name", true));
			}
		};

		add(new ListView<Design>("designs", designsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Design> item) {
				final Link<Design> link = new Link<Design>("link", item.getModel()){

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new DesignEditPage(getModelObject()));
					}
				};
				link.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(link);
			}
		});
	}
}
