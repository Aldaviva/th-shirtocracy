package org.techhouse.shirts.display.web.converters;

import java.util.Date;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.joda.time.DateTime;

public class DateToDateTimeModel extends Model<Date> {
	private static final long serialVersionUID = 1L;
	
	private final IModel<DateTime> dateTimeModel;
	
	public DateToDateTimeModel(final IModel<DateTime> dateTimeModel) {
		this.dateTimeModel = dateTimeModel;
	}

	@Override
	public Date getObject() {
		DateTime dateTime = dateTimeModel.getObject();
		if(dateTime == null){
			return null;
		} else {
			return dateTime.toDate();
		}
	}

	@Override
	public void setObject(Date object) {
		dateTimeModel.setObject(new DateTime(object));
	}
}