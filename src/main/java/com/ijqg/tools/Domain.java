package com.ijqg.tools;

public class Domain {
	private String daoPackageName;
	private String servicePackageName;
	private String domainPackageName;
	private String domainName;
	private String domainKey;
	private boolean hasKey;
	
	public String getDaoPackageName() {
		return daoPackageName;
	}
	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}
	public String getServicePackageName() {
		return servicePackageName;
	}
	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}
	
	public String getDomainPackageName() {
		return domainPackageName;
	}
	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainKey() {
		return domainKey;
	}
	public void setDomainKey(String domainKey) {
		this.domainKey = domainKey;
	}
	public boolean isHasKey() {
		return hasKey;
	}
	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}
	
	
}
