package com.whotel.common.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * read xml info
 * @author fengyong
 *
 */
public class XmlReadUtil {
	
	private Document document;
	
	public XmlReadUtil(URL url) {
		document = loadXmlFile(url);
	}
	
	/**
	 * load xml file by filePath
	 * @param filePath
	 * @return
	 */
	private Document loadXmlFile(URL url) {
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(url);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * get nodes value by xpath
	 * @param xpath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getNodesValue(String xpath) {
		List<String> values = new ArrayList<String>();
		List<Element> nodes = document.selectNodes(xpath);
		if(nodes != null) {
			for(Iterator<Element> it = nodes.iterator(); it.hasNext();) {
				Element node = it.next();
				values.add(node.getTextTrim());
			}
		}
		return values;
	}
	
	/**
	 * get nodes attribute by xpath and attributeName
	 * @param xpath
	 * @param attributeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getNodesAttributeValue(String xpath, String... attributeName) {
		List<String> values = new ArrayList<String>();
		List<Element> nodes = document.selectNodes(xpath);
		if (nodes != null) {
			for(Iterator<Element> it = nodes.iterator(); it.hasNext();) {
				Element node = it.next();
				StringBuilder attributeValue = new StringBuilder();
				for(int i=0; i<attributeName.length; i++) {
					attributeValue.append(node.attributeValue(attributeName[i])).append(",");
				}
				values.add(attributeValue.deleteCharAt(attributeValue.length()-1).toString());
			}
		}
		return values;
	}
}
