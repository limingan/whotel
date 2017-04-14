package com.whotel.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 冯勇
 * 
 */
public class Dom4jHelper {
	private static Logger logger = LoggerFactory.getLogger(Dom4jHelper.class);
	/** XML文档 */
	private Document document = null;

	public Dom4jHelper() {
	}

	public Dom4jHelper(String xmlPath) throws DocumentException {
		SAXReader reader = new SAXReader();
		this.document = reader.read(xmlPath);
	}

	public Dom4jHelper(String xmlPath, String encoding) throws DocumentException {
		SAXReader reader = new SAXReader();
		reader.setEncoding(encoding);
		this.document = reader.read(xmlPath);
	}

	public Dom4jHelper(InputStream in) throws DocumentException {
		SAXReader reader = new SAXReader();
		this.document = reader.read(in);
	}

	public Dom4jHelper(InputStream in, String encoding) throws DocumentException {
		SAXReader reader = new SAXReader();
		reader.setEncoding(encoding);
		this.document = reader.read(in);
	}

	/**
	 * 创建文档
	 * 
	 * @param rootName 根节点名称
	 */
	public Element createXML(String rootName) {
		return createXML(rootName, null);
	}

	public Element createXML(String rootName, String xmlEncoding) {
		try {
			document = DocumentHelper.createDocument();
			if (xmlEncoding != null) {
				document.setXMLEncoding(xmlEncoding);
			}
			logger.info("createXML() successful...");
			Element root = document.addElement(rootName);
			return root;
		} catch (Exception e) {
			logger.error("createXML() Exception", e);
			return null;
		}
	}

	/**
	 * 添加根节点的child
	 * 
	 * @param nodeName 节点名
	 * @param nodeValue 节点值
	 */
	public Element addNodeFromRoot(String nodeName, String nodeValue) {
		Element root = this.document.getRootElement();
		Element child = root.addElement(nodeName);
		child.addText(nodeValue);
		return child;
	}

	/**
	 * 获得XML表示的字符串
	 */
	public String getXML() {
		return document.asXML();
	}

	/**
	 * 获得某个节点的值
	 * 
	 * @param nodeName 节点名称
	 */
	public String getElementValue(String nodeName) {
		Node node = document.selectSingleNode("//" + nodeName);
		if (node == null)
			return null;
		return node.getText();
	}

	/**
	 * 获得某个节点的子节点的值
	 * 
	 * @param nodeName
	 * @param childNodeName
	 * @return
	 */
	public String getElementValue(String nodeName, String childNodeName) {
		Node node = this.document.selectSingleNode("//" + nodeName + "/" + childNodeName);
		if (node == null)
			return null;
		return node.getText();
	}

	/**
	 * 获得某个节点的所有子节点键值对
	 * 
	 * @param nodeName
	 * @param childNodeName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getAllElements(String nodeName) {
		Iterator<Element> it = null;
		if (nodeName == null) {
			it = this.document.getRootElement().elementIterator();
		} else {
			Node n = this.document.selectSingleNode("//" + nodeName);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				it = e.elementIterator();
			}
		}
		if (it != null) {
			Map<String, String> entrys = new HashMap<String, String>();
			while (it.hasNext()) {
				Element e = it.next();
				if (e.getName() != null && e.getText() != null) {
					entrys.put(e.getName(), e.getText());
				}
			}
			return entrys;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getNodeElements(Node node) {
		Iterator<Element> it = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) node;
			it = e.elementIterator();
		}
		if (it != null) {
			Map<String, String> entrys = new HashMap<String, String>();
			while (it.hasNext()) {
				Element e = it.next();
				if (e.getName() != null && e.getText() != null) {
					entrys.put(e.getName(), e.getText());
				}
			}
			return entrys;
		}
		return null;
	}

	/**
	 * 解析xml中列表部分
	 * 
	 * @param nodeName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<Map<String, String>> getListElements(String nodeName) {
		Iterator<Element> it = null;
		List<Node> nodes = this.document.selectNodes("//" + nodeName);
		if (nodes != null) {
			List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
			for (Node node : nodes) {
				Map<String, String> map = getNodeElements(node);
				retVal.add(map);
			}
			return retVal;
		}
		return null;
	}

	public Map<String, String> getAllElements() {
		return getAllElements(null);
	}

	/**
	 * 设置一个节点的text
	 * 
	 * @param nodeName 节点名
	 * @param nodeValue 节点值
	 */
	public void setElementValue(String nodeName, String nodeValue) {
		Node node = this.document.selectSingleNode("//" + nodeName);
		node.setText(nodeValue);
	}

	/**
	 * 设置一个节点值
	 * 
	 * @param nodeName 父节点名
	 * @param childNodeName 节点名
	 * @param nodeValue 节点值
	 */
	public void setElementValue(String nodeName, String childNodeName, String nodeValue) {
		Node node = this.document.selectSingleNode("//" + nodeName + "/" + childNodeName);
		node.setText(nodeValue);
	}

}
