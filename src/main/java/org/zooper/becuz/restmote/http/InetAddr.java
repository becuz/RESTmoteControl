package org.zooper.becuz.restmote.http;

public class InetAddr {

	private String inetName;
	private String ip;
	
	public InetAddr(String inetName, String ip) {
		super();
		this.inetName = inetName;
		this.ip = ip;
	}
	public String getInetName() {
		return inetName;
	}
	public void setInetName(String inetName) {
		this.inetName = inetName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return getInetName() + " - " + getIp();
	}
	

}
