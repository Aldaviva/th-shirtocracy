package org.techhouse.shirts.display.web.pages;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketId;
import org.techhouse.shirts.display.web.components.VoteButton;
import org.techhouse.shirts.service.security.WicketSession;

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
		
		
		
		final Form<Member> ballotForm = new Form<Member>("ballotForm", Model.of(WicketSession.get().getMember())) {

			private static final long serialVersionUID = 1L;
			
		};
		
		ballotForm.add(new ListView<Design>("designListView", designsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Design> item) {
				item.setModel(new CompoundPropertyModel<Design>(item.getModelObject()));
				
				item.add(new Image("thumbnail").add(new AttributeModifier("src", new PropertyModel<URL>(item.getModel(), "thumbnail"))));
				item.add(new Label("name").add(new SetCssClassToWicketId()));
				item.add(new Label("artistAndYear", new AbstractReadOnlyModel<String>() {

					private static final long serialVersionUID = 1L;
					private final Set<Object> nullInACollection = Collections.singleton(null);

					@Override
					public String getObject() {
						final List<Object> split = new ArrayList<Object>(2);
						split.add(item.getModelObject().getArtist());
						split.add(item.getModelObject().getYear());
						split.removeAll(nullInACollection);
						return StringUtils.join(split, ", ");
					}
				}).add(new SetCssClassToWicketId()));
				
				item.add(new VoteButton("voteButton", item.getModel()).add(new SetCssClassToWicketId()));
			}
		});
	}
	
}
