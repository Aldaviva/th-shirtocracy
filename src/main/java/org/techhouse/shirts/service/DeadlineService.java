package org.techhouse.shirts.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.techhouse.shirts.data.entities.Configuration;

@Service
public class DeadlineService {

	public boolean hasDeadlinePassed() {
		if(isDeadlineSet()){
			return getDeadline().isBeforeNow();
		} else {
			return false;
		}
	}

	public DateTime getDeadline() {
		return Configuration.get().getDeadline();
	}
	
	public boolean isDeadlineSet(){
		return getDeadline() != null;
	}

}
