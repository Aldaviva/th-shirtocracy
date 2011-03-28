package org.techhouse.shirts.display.web.pages;

import java.util.Locale;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.DateTime;
import org.techhouse.shirts.data.entities.Configuration;
import org.techhouse.shirts.display.web.WicketApplication;
import org.techhouse.shirts.display.web.behaviors.PlaceholderBehavior;
import org.techhouse.shirts.display.web.security.AdminPage;

public class ConfigurationPage extends TemplatePage implements AdminPage {

	private final IModel<Configuration> configurationModel;

	public ConfigurationPage() {
		configurationModel = new LoadableDetachableModel<Configuration>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected Configuration load() {
				return Configuration.getConfiguration();
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

		/*final DateToDateTimeModel deadlineModel = new DateToDateTimeModel(new PropertyModel<DateTime>(form.getModel(), "deadline"));
		form.add(new DateTimeField("deadline", deadlineModel).add(new SetCssClassToWicketIdBehavior()));*/
		
		final String deadlinePattern = "MMM d, YYYY, h:mm a";
		form.add(new TextField<DateTime>("deadline", new PropertyModel<DateTime>(form.getModel(), "deadline")){

			private static final long serialVersionUID = 1L;

			@Override
			public IConverter getConverter(Class<?> type) {
				if(type.equals(DateTime.class)){
					return new IConverter() {
						private static final long serialVersionUID = 1L;

						@Override
						public String convertToString(Object value, Locale locale) {
							return ((DateTime) value).withZone(WicketApplication.get().getDefaultDateTimeZone()).toString(deadlinePattern);
						}
						
						@Override
						public Object convertToObject(String value, Locale locale) {
							if(value != ""){
								long millis;
								try {
									org.pojava.datetime.DateTime parse = org.pojava.datetime.DateTime.parse(value);
									millis = parse.toMillis();
								} catch (IllegalArgumentException e) {
									throw new ConversionException("'" + value + "' is not a valid date and time", e);
								}
								return new DateTime(millis).withZoneRetainFields(WicketApplication.get().getDefaultDateTimeZone());
							} else {
								return null;
							}
						}
					};
				}
				return super.getConverter(type);
			}
		}.add(new PlaceholderBehavior(true, deadlinePattern))
		);
	}
}
