package org.techhouse.shirts.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techhouse.shirts.data.entities.Member;

@Service
@Transactional
public class VoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);
	
	public Member submitBallot(Member member) {
		//TODO check to make sure the deadline has not passed
		member = member.merge();
		LOGGER.info("{} is now voting for {}.", member.getName(), StringUtils.join(member.getDesigns(), ", "));
		return member;
	}
	
}
