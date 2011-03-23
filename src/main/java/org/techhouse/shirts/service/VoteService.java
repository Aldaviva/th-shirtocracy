package org.techhouse.shirts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.service.ServiceException.DeadlinePassedException;

@Service
@Transactional
public class VoteService {

	@Autowired
	private DeadlineService deadlineService;
	
	public Member submitBallot(Member member) throws DeadlinePassedException {
		if(!deadlineService.hasDeadlinePassed()){
			member = member.merge();
		} else {
			throw new ServiceException.DeadlinePassedException();
		}
		return member;
		
	}
	
}
