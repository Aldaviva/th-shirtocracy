package org.techhouse.shirts.display.web.pages;

import java.text.NumberFormat;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.query.QueryParam;
import org.techhouse.shirts.data.query.SortParam;
import org.techhouse.shirts.display.web.security.AuthenticatedWebPage;

public class StatisticsPage extends TemplatePage implements AuthenticatedWebPage {

	final IModel<List<Design>> designsModel = new LoadableDetachableModel<List<Design>>() {
		private static final long serialVersionUID = 1L;

		@Override
		protected List<Design> load() {
			return Design.findAllDesigns(new QueryParam().addSort(new SortParam("name", true)));
		}
	};

	final IModel<Integer> totalVotesModel = new LoadableDetachableModel<Integer>() {
		private static final long serialVersionUID = 1L;

		@Override
		protected Integer load() {
			return Design.countAllVotes();
		}
	};
	
	final IModel<Long> votesForDesignWithMostVotesModel = new LoadableDetachableModel<Long>() {
		private static final long serialVersionUID = 1L;

		@Override
		protected Long load() {
			return Design.getDesignWithMostVotes().getVoteCount();
		}
	};

	public StatisticsPage() {
		super();

		add(new ListView<Design>("votesTableRow", designsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Design> designItem) {
				designItem.add(new Label("name", new PropertyModel<String>(designItem.getModelObject(), "name")));
				final IModel<Long> votesModel = new PropertyModel<Long>(designItem.getModelObject(), "voteCount");
				designItem.add(new WebMarkupContainer("votes.bar").add(new VotesBarBehavior(votesModel)).setVisible(votesModel.getObject() > 0));
				designItem.add(new Label("votes.number", votesModel));
			}
		});

		add(new Label("total", totalVotesModel));
	}

	private final class VotesBarBehavior extends AttributeAppender {
		private static final String STYLE_ATTRIBUTE = "style";
		private static final long serialVersionUID = 1L;

		public VotesBarBehavior(final IModel<Long> votesModel) {

			super(STYLE_ATTRIBUTE, true, new AbstractReadOnlyModel<String>() {

				private static final long serialVersionUID = 1L;
				private static final String STYLE_KEY = "width: ";

				@Override
				public String getObject() {
					final long votesForThisDesign = votesModel.getObject();
					final int votesForAllDesigns = totalVotesModel.getObject();
					if (votesForAllDesigns > 0) {
						final long votesForDesignWithMostVotes = votesForDesignWithMostVotesModel.getObject();
						final double percentForThisDesign = ((double) votesForThisDesign) / votesForDesignWithMostVotes;
						final String styleValue = NumberFormat.getPercentInstance().format(percentForThisDesign);
						return STYLE_KEY + styleValue + ";";
					} else {
						return STYLE_KEY + "0%;";
					}
				}
			}, " ");
			
		}

	}
}
