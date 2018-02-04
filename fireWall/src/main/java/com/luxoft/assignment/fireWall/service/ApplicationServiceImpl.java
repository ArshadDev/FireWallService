package com.luxoft.assignment.fireWall.service;

import com.luxoft.assignment.fireWall.model.HTTPStatus;
import com.luxoft.assignment.fireWall.model.RequestModel;
import com.luxoft.assignment.fireWall.model.ResponseModel;

/**
 * @author Arshad
 */
public class ApplicationServiceImpl implements ApplicationService {

	private static ApplicationService applicationService;
	private IPAddressAuthenticationService ipAddressAuthenticationService;

	private ApplicationServiceImpl() {
		ipAddressAuthenticationService = IPAddressAuthenticationServiceImpl.getInstance();
	}
	
	/**
	 * This factory method can be used to get the instance of this service
	 * @return
	 */
	public static ApplicationService getInstance() {
		if (applicationService == null) {
			applicationService = new ApplicationServiceImpl();
		}
		return applicationService;
	}

	/**
	 * This is some application related dummy perform method
	 */
	@Override
	public ResponseModel perform(RequestModel request) {
		ResponseModel response = new ResponseModel();
		
		if (null != request) {
			if (ipAddressAuthenticationService.isIPAddressBlackListed(request.getIpAddress())) {
				response.setResponseData("Request from UnAuthorized User ["+ request.getIpAddress() +"]");
				response.setResponseCode(HTTPStatus.UNAUTHORIZED.getCode());
				response.setResponseInfo(HTTPStatus.UNAUTHORIZED.toString());
			} else {
				response.setResponseData("Request Served Successfully for ["+ request.getIpAddress() +"]");
				response.setResponseCode(HTTPStatus.OK.getCode());
				response.setResponseInfo(HTTPStatus.OK.toString());
			}
		} else {
			response.setResponseData("Bad Request !!");
			response.setResponseCode(HTTPStatus.BAD_REQUEST.getCode());
			response.setResponseInfo(HTTPStatus.BAD_REQUEST.toString());
		}
		return response;
	}
	
	
}
