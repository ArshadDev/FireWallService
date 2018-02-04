package com.luxoft.assignment.fireWall.service;

import java.util.Set;

/**
 * @author Arshad
 */
public interface IPAddressAuthenticationService {

	boolean isValidIPAddress(String ipAddress);

	boolean isIPAddressBlackListed(String ipAddress);

	void addIPToBlackList(String ipAddress);

	void removeIPFromBlackList(String ipAddress);

	Set<String> getAllBlackListedIPs();
}
