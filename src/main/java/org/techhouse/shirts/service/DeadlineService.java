package org.techhouse.shirts.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.techhouse.shirts.data.entities.Configuration;

@Service
public class DeadlineService {

	public boolean hasDeadlinePassed() {
		final DateTime deadline = getDeadline();
		return (!isDeadlineSet()) || (deadline.isBeforeNow());
	}

	public DateTime getDeadline() {
		return Configuration.get().getDeadline();
	}
	
	public boolean isDeadlineSet(){
		return getDeadline() != null;
	}

}
