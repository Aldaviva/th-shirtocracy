package org.techhouse.shirts.service;

import org.springframework.stereotype.Service;
import org.techhouse.shirts.data.entities.Design;

@Service
public class DesignService {

	public void save(Design design){
		design.persist();
	}
	
	public void delete(Design design){
		design.remove();
	}
	
}
