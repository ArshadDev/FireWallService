package com.luxoft.assignment.fireWall.service;

import com.luxoft.assignment.fireWall.model.RequestModel;
import com.luxoft.assignment.fireWall.model.ResponseModel;

/**
 * @author Arshad
 */
public interface ApplicationService {
	
	ResponseModel perform(RequestModel request);
}
