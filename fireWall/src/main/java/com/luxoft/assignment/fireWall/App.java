package com.luxoft.assignment.fireWall;

import com.luxoft.assignment.fireWall.model.RequestModel;
import com.luxoft.assignment.fireWall.model.ResponseModel;
import com.luxoft.assignment.fireWall.service.ApplicationService;
import com.luxoft.assignment.fireWall.service.ApplicationServiceImpl;
import com.luxoft.assignment.fireWall.service.IPAddressAuthenticationService;
import com.luxoft.assignment.fireWall.service.IPAddressAuthenticationServiceImpl;

/**
 * @author Arshad
 */
public class App {
	
	private IPAddressAuthenticationService ipAddressAuthenticationService;
	private ApplicationService applicationService;
	
	public App(){
		ipAddressAuthenticationService = IPAddressAuthenticationServiceImpl.getInstance();
		applicationService = ApplicationServiceImpl.getInstance();
	}
	
	public static void main(String[] args) {
		
		App application = new App();
		
		RequestModel blockedIPRequest = new RequestModel();
		blockedIPRequest.setIpAddress("10.23.45.12");
		application.performAction(blockedIPRequest);
		
		
		RequestModel validRequest = new RequestModel();
		validRequest.setIpAddress("10.23.45.112");
		application.performAction(validRequest);
		
		application.blockIPAddress("10.23.45.112");
		application.performAction(validRequest);
		
		application.unBlockIPAddress("10.23.45.12");
		application.performAction(blockedIPRequest);
		
		application.isIPAddressValid("10c.23.45.12");
		application.isIPBlackListed("10.23.45.112");
		
		application.displayAllBlockedIPAddresses();
	}
	
	public void performAction(RequestModel request){
		ResponseModel response = applicationService.perform(request);
		System.out.println("Response : " + response.getResponseData().toString());
		System.out.println("Response Info: " + response.getResponseInfo());
		System.out.println("Response Status Code : " + response.getResponseCode());
	}
	
	public void blockIPAddress(String ipAddress){
		System.out.println("Blocking IP Address : "+ ipAddress);
		ipAddressAuthenticationService.addIPToBlackList(ipAddress);
	}
	
	public void unBlockIPAddress(String ipAddress){
		System.out.println("Un-Blocking IP Address : "+ ipAddress);
		ipAddressAuthenticationService.removeIPFromBlackList(ipAddress);
	}
	
	public void displayAllBlockedIPAddresses(){
		System.out.println(ipAddressAuthenticationService.getAllBlackListedIPs());
	}
	
	public void isIPBlackListed(String ipAddress){
		if(ipAddressAuthenticationService.isIPAddressBlackListed(ipAddress)){
			System.out.println("IP Address : "+ ipAddress + " is black listed !");
		}else{
			System.out.println("IP Address : "+ ipAddress + " is not black listed !");
		}
	}
	
	public void isIPAddressValid(String ipAddress){
		if(ipAddressAuthenticationService.isValidIPAddress(ipAddress)){
			System.out.println("IP Address : "+ ipAddress + " is valid !");
		}else{
			System.out.println("IP Address : "+ ipAddress + " is not valid!");
		}
	}
}
