package org.techhouse.shirts.data.entities;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime deadline;

	public static Configuration getConfiguration() {
		List<Configuration> entries = findConfigurationEntries(0, 1);
		if (entries.isEmpty()) {
			return new Configuration();
		} else {
			return entries.get(0);
		}
	}
}
