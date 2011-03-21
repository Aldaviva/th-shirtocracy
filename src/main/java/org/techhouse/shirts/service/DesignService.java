package org.techhouse.shirts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.techhouse.shirts.data.entities.Design;

@Service
@Transactional
public class DesignService {

	public void save(Design design){
		design.merge();
	}
	
	public void delete(Design design){
		design.remove();
	}
	
}
