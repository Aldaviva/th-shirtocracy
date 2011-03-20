package org.techhouse.shirts.display.web.pages;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketId;
import org.techhouse.shirts.display.web.components.VoteButton;
import org.techhouse.shirts.service.VoteService;
import org.techhouse.shirts.service.security.WicketSession;

public class BallotPage extends BasePage /* implements AuthenticatedWebPage */ {

	@SpringBean
	private VoteService voteService;
	
	private final Member member;
	
	private final HashMap<Design, Boolean> designToChoiceMap;
	
	private final IModel<List<Design>> designsModel;
	
	public BallotPage() {
		super();
		
		member = //WicketSession.get().getMember();
			Member.findMember("ben");
		
		designsModel = new LoadableDetachableModel<List<Design>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Design> load() {
				return Design.findAllDesigns();
			}
		};
		
		designToChoiceMap = setUpDesignToChoiceMap(member);
		
		final Form<Member> ballotForm = new Form<Member>("ballotForm", Model.of(member)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				Collection<Design> selectedDesigns = new ArrayList<Design>(designToChoiceMap.size());
				for (Entry<Design, Boolean> choice : designToChoiceMap.entrySet()) {
					if(choice.getValue()){
						selectedDesigns.add(choice.getKey());
					}
				}
				voteService.submitBallot(getModelObject(), selectedDesigns);
			}
			
		};
		add(ballotForm);
		
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
				
				item.add(new VoteButton("voteButton", item.getModel(), new SelectedDesignModel(item.getModel())).add(new SetCssClassToWicketId()));
			}
		});
	}
	
	private final class SelectedDesignModel extends Model<Boolean> {

		private static final long serialVersionUID = 1L;
		private IModel<Design> designModel;

		public SelectedDesignModel(IModel<Design> designModel) {
			this.designModel = designModel;
		}

		@Override
		public Boolean getObject() {
			return designToChoiceMap.get(designModel.getObject());
		}

		@Override
		public void setObject(Boolean value) {
			designToChoiceMap.put(designModel.getObject(), value);
		}
		
	}

	private HashMap<Design, Boolean> setUpDesignToChoiceMap(Member member) {
		HashMap<Design, Boolean> map = new HashMap<Design, Boolean>((int) Design.countDesigns());
		
		for (Design design : designsModel.getObject()){
			map.put(design, Boolean.FALSE);
		}
		
		member.getDesigns();
		for (Design chosenDesign : member.getDesigns()) {
			map.put(chosenDesign, Boolean.TRUE);
		}
		
		return map;
	}
	
}
