package com.zheng.order.entity.factory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OrderStatusFactory {

	/* resource files user configure it through spring configuration file */
	private Map<String, String> resourceFiles = new HashMap<String, String>();

	/* a resource container including all standreport file configure, */
	private Map<String, Resource> resourceMap = new HashMap<String, Resource>();

	/**
	 * convert the file names init spring io resource and push into a map
	 * 
	 * @param resourceFiles
	 */
	public OrderStatusFactory(Map<String, String> resourceFiles) {
		Assert.notEmpty(resourceFiles, "resourceFiles should not be empty!");
	    this.resourceFiles = resourceFiles;
		for (String key : resourceFiles.keySet()) {
			Resource resource = new ClassPathResource(resourceFiles.get(key));
			resourceMap.put(key, resource);
		}
	}

	/**
	 * You could get the Resource via passing a hashmap key counterpart the
	 * configuration
	 * 
	 * @param key
	 * @return
	 */
	public Resource getResource(String key) {
		Assert.hasText(key, "key should have a valid string");
		Resource resource = resourceMap.get(key);
		return resource;
	}

	public String getOrderStatusI18n(String language, String key) throws IOException {
		Resource resource = getResource(language);
		if (null == resource) {
			resource = getResource("en");
		}
		Properties prop = new Properties();
		prop.load(resource.getInputStream());

		return prop.getProperty(key);
	}

	public Map<String, String> getResourceFiles() {
		return resourceFiles;
	}

	public void setResourceFiles(Map<String, String> resourceFiles) {
		this.resourceFiles = resourceFiles;
	}

	public Map<String, Resource> getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(Map<String, Resource> resourceMap) {
		this.resourceMap = resourceMap;
	}

}