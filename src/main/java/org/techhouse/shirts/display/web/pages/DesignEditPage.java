package org.techhouse.shirts.display.web.pages;

import java.net.URL;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.display.web.AuthenticatedWebPage;
import org.techhouse.shirts.display.web.WicketApplication;
import org.techhouse.shirts.display.web.behaviors.PlaceholderBehavior;
import org.techhouse.shirts.display.web.behaviors.SetCssClassToWicketIdBehavior;
import org.techhouse.shirts.service.DesignService;


public class DesignEditPage extends BasePage implements AuthenticatedWebPage {

	private Form<Design> form;
	private final boolean isNewDesign;
	
	@SpringBean
	private DesignService designService;
	
	public DesignEditPage(){
		isNewDesign = true;
		init(new Design());
	}
	
	public DesignEditPage(Design design){
		isNewDesign = false;
		init(design);
	}
	
	private void init(Design design){
		form = new Form<Design>("designEditForm",  new CompoundPropertyModel<Design>(design)){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				designService.save(getModelObject());
				setResponsePage(WicketApplication.get().getHomePage());
			}

			@Override
			protected void onError() {
				super.onError();
			}
		};
		
		form.add(
				new TextField<String>("name")
				.setRequired(true)
				.add(new PlaceholderBehavior("Design Name"))
				.add(new SetCssClassToWicketIdBehavior())
		);
		
		form.add(
				new TextField<URL>("thumbnail")
				.setRequired(true)
				.add(new PlaceholderBehavior("http://"))
				.add(new SetCssClassToWicketIdBehavior())
		);
		
		form.add(
				new TextField<String>("artist")
				.add(new PlaceholderBehavior("Josiah Carberry"))
				.add(new SetCssClassToWicketIdBehavior())
		);
		
		form.add(
				new TextField<Integer>("year")
				.add(new RangeValidator<Integer>(1985, 2021))
				.add(new PlaceholderBehavior("YYYY"))
				.add(new SetCssClassToWicketIdBehavior())
		);
		
		form.add(
				new TextField<URL>("photograph")
				.add(new PlaceholderBehavior("http://"))
				.add(new SetCssClassToWicketIdBehavior())
		);
		
		form.add(
				new Button("delete"){
					private static final long serialVersionUID = 1L;
		
					@Override
					protected String getOnClickScript() {
						return "return confirm('Are you sure you want to delete this Design?');";
					}
		
					@Override
					public void onSubmit() {
						designService.delete(form.getModelObject());
						setResponsePage(WicketApplication.get().getHomePage());
					}
				}
				.setDefaultFormProcessing(false)
				.setVisible(isNewDesign)
				.add(new SetCssClassToWicketIdBehavior())
		);
	}
	
}
