package org.techhouse.shirts.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techhouse.shirts.data.entities.Design;
import org.techhouse.shirts.data.entities.Member;

@Service
public class VoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);
	
	@Transactional
	public void submitBallot(Member member, Collection<Design> selectedDesigns) {
		member.getDesigns().clear();
		member.getDesigns().addAll(selectedDesigns);
		member.merge();
//		LOGGER.info("{} is now voting for {}.", member.getName(), member.getDesigns().toString());
	}
	
}
