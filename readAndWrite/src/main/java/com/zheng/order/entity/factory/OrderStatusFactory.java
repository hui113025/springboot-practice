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
//		resourceFiles.put("ar", "i18n/order-status_ar.properties");
//		resourceFiles.put("da", "i18n/order-status_da.properties");
//		resourceFiles.put("de", "i18n/order-status_de.properties");
//		resourceFiles.put("en", "i18n/order-status_en.properties");
//		resourceFiles.put("es_ES", "i18n/order-status_es_ES.properties");
//		resourceFiles.put("es_LA", "i18n/order-status_es_LA.properties");
//		resourceFiles.put("es", "i18n/order-status_es.properties");
//		resourceFiles.put("fi", "i18n/order-status_fi.properties");
//		resourceFiles.put("fr_CA", "i18n/order-status_fr_CA.properties");
//		resourceFiles.put("fr_FR", "i18n/order-status_fr_FR.properties");
//		resourceFiles.put("fr", "i18n/order-status_fr.properties");
//		resourceFiles.put("ja", "i18n/order-status_ja.properties");
//		resourceFiles.put("he", "i18n/order-status_he.properties");
//		resourceFiles.put("it", "i18n/order-status_it.properties");
//		resourceFiles.put("iw", "i18n/order-status_iw.properties");
//		resourceFiles.put("ko", "i18n/order-status_ko.properties");
//		resourceFiles.put("pt", "i18n/order-status_pt.properties");
//		resourceFiles.put("ru", "i18n/order-status_ru.properties");
//		resourceFiles.put("tr", "i18n/order-status_tr.properties");
//		resourceFiles.put("zf", "i18n/order-status_zf.properties");
//		resourceFiles.put("nl", "i18n/order-status_nl.properties");
//		resourceFiles.put("no", "i18n/order-status_no.properties");
//		resourceFiles.put("pt_BR", "i18n/order-status_pt_BR.properties");
//		resourceFiles.put("pt_PT", "i18n/order-status_pt_PT.properties");
//		resourceFiles.put("sv", "i18n/order-status_sv.properties");
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