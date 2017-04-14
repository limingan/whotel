package com.whotel.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class NameUtil {

	private static final Logger logger = Logger.getLogger(NameUtil.class);
	
	public static String[] convertPinyinName(String chineseName) {
		String pinyinName = PinyinHelper.convertToPinyinString(chineseName, " ", PinyinFormat.WITHOUT_TONE);
		String[] names = new String[2];
		if(pinyinName != null) {
			String[] pns = pinyinName.split(" ");
			int len = pns.length;
			if(len == 1) {
				names[0] = toAlephUpper(pns[0]);
		    	names[1] = toAlephUpper(pns[0]);
			} else if(len == 2) {
		    	names[0] = toAlephUpper(pns[0]);
		    	names[1] = toAlephUpper(pns[1]);
			} else if(len == 3) {
				names[0] = toAlephUpper(pns[0]);
		    	names[1] = toAlephUpper(pns[1]) + " " + toAlephUpper(pns[2]);
			} else if(len == 4) {
				names[0] = toAlephUpper(pns[0]) + " " + toAlephUpper(pns[1]);
		    	names[1] = toAlephUpper(pns[2]) + " " + toAlephUpper(pns[3]);
			} else if(len > 0) {
				names[0] = toAlephUpper(pns[0]);
		    	names[1] = "";
		    	for(int i=1; i<len; i++) {
		    		names[1] += toAlephUpper(pns[i]) + " ";
		    	}  
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug(chineseName+" convert to " + pinyinName);
		}
		return names;
	}
	
	private static String toAlephUpper(String str) {
		if(StringUtils.isNotBlank(str)) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return "";
	}
}
