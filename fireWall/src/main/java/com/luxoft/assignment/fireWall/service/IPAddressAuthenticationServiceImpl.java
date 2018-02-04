package com.luxoft.assignment.fireWall.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arshad
 */
public class IPAddressAuthenticationServiceImpl implements IPAddressAuthenticationService {
	
	private static IPAddressAuthenticationService ipAddressAuthenticationService;
	private Set<String> blackListedIPAddresses = new HashSet<>();

	private IPAddressAuthenticationServiceImpl(){
		loadBlockedIPAddressesFromFile("BlackListedIPAddresses.txt");
		createShutdownHook();
	}

	/**
	 * This factory method can be used to get the instance of this service
	 * @return
	 */
	public static IPAddressAuthenticationService getInstance() {
		if (ipAddressAuthenticationService == null) {
			ipAddressAuthenticationService = new IPAddressAuthenticationServiceImpl();
		}
		return ipAddressAuthenticationService;
	}
	
	
	/**
	 * This method can be used to validate the given IP Address
	 */
	@Override
	public boolean isValidIPAddress(String ipAddress) {
		Pattern ipPattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
		Matcher ipMatcher = ipPattern.matcher(ipAddress);
		return ipMatcher.find();
	}

	/**
	 * This method can be used to check if given IP address is black listed or not
	 */
	@Override
	public boolean isIPAddressBlackListed(String ipAddress) {
		return blackListedIPAddresses.contains(ipAddress);
	}

	/**
	 * This method can be used to add given IP Address to black listed IP addresses
	 */
	@Override
	public void addIPToBlackList(String ipAddress) {
		if (null != ipAddress && !blackListedIPAddresses.contains(ipAddress) && isValidIPAddress(ipAddress)) {
			blackListedIPAddresses.add(ipAddress);
		}
	}

	/**
	 * This method can be used to remove given IP Address from black Listed IP addresses
	 */
	@Override
	public void removeIPFromBlackList(String ipAddress) {
		if (null != ipAddress && blackListedIPAddresses.contains(ipAddress)) {
			blackListedIPAddresses.remove(ipAddress);
		}
	}

	/**
	 * This method can be used to get All the black listed IP Address
	 * It will return a read only Set of IP Address
	 */
	@Override
	public Set<String> getAllBlackListedIPs() {
		return Collections.unmodifiableSet(blackListedIPAddresses);
	}
	
	/**
	 * This method will add Shutdown Hook to write back the updated list of Black Listed IP Addresses to File
	 */
	private void createShutdownHook() {
		Runtime r = Runtime.getRuntime();

		r.addShutdownHook(new Thread() {
			@Override
			public void run() {
				writeBlockedIPAddressesToFile("BlackListedIPAddresses.txt");
			}
		});
	}

	/**
	 * This method is used to load Blocked IP address data from File
	 */
	private void loadBlockedIPAddressesFromFile(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();

			while (line != null) {
				String[] ipAddresses = line.split(",");
				if (null != ipAddresses && ipAddresses.length > 0) {
					Collections.addAll(blackListedIPAddresses, ipAddresses);
				}
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will write the updated list of blocked IP addresses to File
	 */
	private void writeBlockedIPAddressesToFile(String fileName){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			String ipAddressData = Arrays.toString(blackListedIPAddresses.toArray(new String[0]));
			if (null != ipAddressData) {
				ipAddressData = ipAddressData.replaceAll("\\[", "");
				ipAddressData = ipAddressData.replaceAll("\\]", "");
				bw.write(ipAddressData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
