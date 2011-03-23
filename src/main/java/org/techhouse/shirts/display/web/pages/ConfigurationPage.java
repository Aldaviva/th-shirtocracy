package org.techhouse.shirts.display.web.pages;

import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateTime;
import org.techhouse.shirts.data.entities.Configuration;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;
import org.techhouse.shirts.display.web.converters.DateToDateTimeModel;
import org.techhouse.shirts.display.web.security.AdminPage;

public class ConfigurationPage extends BasePage implements AdminPage {

	private final IModel<Configuration> configurationModel;

	public ConfigurationPage() {
		configurationModel = new LoadableDetachableModel<Configuration>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected Configuration load() {
				return Configuration.get();
			}
		};

		final Form<Configuration> form = new Form<Configuration>("configurationForm", configurationModel) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				setModelObject(getModelObject().merge());
				info(getString("message.info.saveSuccess", Model.<String>of("Configuration")));
			}

		};
		add(form);

		final DateToDateTimeModel deadlineModel = new DateToDateTimeModel(new PropertyModel<DateTime>(form.getModel(), "deadline"));
		form.add(new DateTimeField("deadline", deadlineModel).add(new SetCssClassToWicketIdBehavior()));
	}
}
