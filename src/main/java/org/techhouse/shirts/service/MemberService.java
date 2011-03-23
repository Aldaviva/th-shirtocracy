package org.techhouse.shirts.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techhouse.shirts.data.entities.Member;
import org.techhouse.shirts.data.enums.Role;

@Service
@Transactional
public class MemberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);
	
	public Member createMember(String name){
		Member member = Member.findMember(name);
		if(member != null){
			throw new RuntimeException("Member '"+name+"' already exists.");
		}
		
		member = new Member();
		member.setName(name);
		member.setRole(Role.NORMAL);
		member.persist();
		
		LOGGER.info("Created new member {}", member.getName());
		
		return member;
	}
	
}
