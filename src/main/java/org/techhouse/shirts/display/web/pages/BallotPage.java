package org.techhouse.shirts.display.web.pages;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
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
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.data.query.SortParam;
import org.techhouse.shirts.display.web.WicketApplication;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;
import org.techhouse.shirts.display.web.security.DeadlinePage;
import org.techhouse.shirts.display.web.security.WicketSession;
import org.techhouse.shirts.service.ServiceException.DeadlinePassedException;
import org.techhouse.shirts.service.VoteService;

public class BallotPage extends BasePage implements DeadlinePage {

	@SpringBean
	private VoteService voteService;
	
	private final IModel<Member> memberModel;
	
	private final IModel<List<Design>> designsModel;

	private final Form<Member> ballotForm;

	public BallotPage() {
		super();
		
		if(WicketApplication.get().isDevelopment()){
			memberModel = Model.of(Member.findMember("ben"));
		} else {
			memberModel = Model.of(WicketSession.get().getMember()); 
		}
			
		designsModel = new LoadableDetachableModel<List<Design>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Design> load() {
				return Design.findAllDesigns(new SortParam("year", false), new SortParam("name", true));
			}
		};
		
		ballotForm = new Form<Member>("ballotForm", memberModel) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				try {
					setModelObject(voteService.submitBallot(getModelObject()));
					info(getString("feedback.submitted.success"));
				} catch (DeadlinePassedException e) {
					error(getString("Exception."+e.getClass().getSimpleName()));
				}
			}
		};
		add(ballotForm);
		
		final CheckGroup<Design> checkGroup = new CheckGroup<Design>("checkGroup", new PropertyModel<List<Design>>(ballotForm.getModel(), "designs"));
		checkGroup.setRenderBodyOnly(false);
		ballotForm.add(checkGroup);
		
		checkGroup.add(new ListView<Design>("designListView", designsModel) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Design> item) {
				item.setModel(new CompoundPropertyModel<Design>(item.getModelObject()));
				
				item.add(new Check<Design>("voteCheck", new PropertyModel<Design>(item, "modelObject"), checkGroup)
						.add(new AttributeAppender("class", true, Model.of("voteButton"), " ")));
				
				item.add(new Image("thumbnail").add(new AttributeModifier("src", new PropertyModel<URL>(item.getModel(), "thumbnail"))));
				item.add(new Label("name").add(new SetCssClassToWicketIdBehavior()));
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
				}).add(new SetCssClassToWicketIdBehavior()));
			}
		});
	}
}
