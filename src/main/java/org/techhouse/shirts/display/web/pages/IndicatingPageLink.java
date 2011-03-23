package org.techhouse.shirts.display.web.pages;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;


public class IndicatingPageLink extends Link<Void> {

	private static final long serialVersionUID = 1L;

	private static final String HTML_CLASS_ATTRIBUTE = "class";
	private static final String ACTIVE_CSS_CLASS = "active";
	
	private Class<? extends Page> pageClass;
	private PageParameters parameters;
	private Page page;
	
	public IndicatingPageLink(String id, Class<? extends Page> pageClass) {
		super(id);
		this.pageClass = pageClass;
	}
	
	public IndicatingPageLink(String id, Class<? extends Page> pageClass, PageParameters parameters) {
		super(id);
		this.pageClass = pageClass;
		this.parameters = parameters;
	}

	public IndicatingPageLink(String id, Page page) {
		super(id);
		this.page = page;
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		String classAttribute = tag.getAttribute(HTML_CLASS_ATTRIBUTE);

		final Page currentPage = getPage();
		final boolean areOnCurrentPage;
		if(pageClass != null){
			areOnCurrentPage = (currentPage.getPageClass().equals(pageClass));
		} else if(page != null) {
			areOnCurrentPage = (currentPage.getPageClass().equals(page.getPageClass()));
		} else {
			areOnCurrentPage = false;
		}
		
		if(areOnCurrentPage){
			if(classAttribute == null){
				classAttribute = ACTIVE_CSS_CLASS;
			} else {
				classAttribute += " " + ACTIVE_CSS_CLASS;
			}
			tag.put(HTML_CLASS_ATTRIBUTE, classAttribute);
		}
	}

	@Override
	public final void onClick() {
		if(parameters != null && pageClass != null) {
			setResponsePage(pageClass, parameters);
		} else if(pageClass != null) {
			setResponsePage(pageClass);
		} else if(page != null){
			setResponsePage(page);
		} else {
			throw new WicketRuntimeException("IndicatingPageLink has a null pageClass and page");
		}
	}

}
