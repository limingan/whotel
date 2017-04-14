package com.whotel.common.util;


import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportExcel {

	List<Object[]> columns = null;
	
	public List<?> dataSource = null;
	
	public void setDataSource(List<?> dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * 0:中文名称 1：对象的属性名称 2：列的宽
	 * @param columns
	 */
	public void setColumns(List<Object[]> columns){
		this.columns = columns;
	}
	
	public void export(File file){
		try {
			if(file==null){
				System.out.println("导出路径为空");
				return;
			}
            WritableWorkbook book = Workbook.createWorkbook(file);
            WritableSheet sheet = book.createSheet("第一页", 0);
            setTitle(sheet);//设置标题
            setContent(sheet);//设置内容
            book.write();
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		   
	}
	
	public void exportByIndex(File file){
		try {
			if(file==null){
				System.out.println("导出路径为空");
				return;
			}
            WritableWorkbook book = Workbook.createWorkbook(file);
            WritableSheet sheet = book.createSheet("第一页", 0);
            setTitle(sheet);//设置标题
            setContentByIndex(sheet);//设置内容
            book.write();
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		   
	}
	
	public <T> void export(File file,Map<String, List<T>> map){
		try {
			if(file==null){
				System.out.println("导出路径为空");
				return;
			}
            WritableWorkbook book = Workbook.createWorkbook(file);
            int i = 0;
            for (String name : map.keySet()) {
            	setDataSource(map.get(name));
            	WritableSheet sheet = book.createSheet(name, i);
            	setTitle(sheet);//设置标题
            	setContent(sheet);//设置内容
            	i++;
			}
            book.write();
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		   
	} 
	/**
	 * 设置内容
	 * @param sheet
	 */
	public void setContent(WritableSheet sheet){
        try { 
        	jxl.write.WritableCellFormat contentFormat = getStyle(false);
            
            for (int i = 0; i < dataSource.size(); i++) {
            	for (int j = 0; j < columns.size(); j++) {
            		Object object = dataSource.get(i);
            		String[] propertyName = columns.get(j)[1].toString().split("\\.");
            		Object obj = null;
            		for (int k = 0; k < propertyName.length; k++) {
//            			obj = PropertyUtils.getSimpleProperty(object, propertyName[k]);
            			propertyName[k] = "get"+propertyName[k].substring(0, 1).toUpperCase() + propertyName[k].substring(1);
            			if(object!=null){
            				Method method = object.getClass().getMethod(propertyName[k]);
            				method.setAccessible(true);
            				obj = method.invoke(object);
            				object = obj;
            			}
            		}
            		
            		
            		jxl.write.Number serialNumber = new jxl.write.Number(0,i+1,i+1,contentFormat);
        			sheet.addCell(serialNumber);
//            		
//            		if(obj instanceof String){
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,obj.toString(),contentFormat);
//            			sheet.addCell(label);
//            		}else if(obj instanceof Integer){
//            			 jxl.write.Number number = new jxl.write.Number(j+1,i+1,(Integer)obj,contentFormat);
//            			 sheet.addCell(number);
//            		}else if(obj instanceof Double){
//           			 	jxl.write.Number number = new jxl.write.Number(j+1,i+1,(Double)obj,contentFormat);
//           			 	sheet.addCell(number);
//            		}else if(obj instanceof Date){
//            			String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,date,contentFormat);
//    					sheet.addCell(label);
//            		}else if(obj instanceof Boolean){
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,Boolean.TRUE.equals(obj)?"是":"否",contentFormat);
//            			sheet.addCell(label);
//            		}else if(obj==null){
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,"",contentFormat);
//            			sheet.addCell(label);
//            		}else{
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,""+obj,contentFormat);
//            			sheet.addCell(label);
//            		}
            		createDataSource(sheet, contentFormat, obj, i+1, j+1);
    			}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setContentByIndex(WritableSheet sheet){
        try { 
        	jxl.write.WritableCellFormat contentFormat = getStyle(false);
            
            for (int i = 0; i < dataSource.size(); i++) {
            	for (int j = 0; j < columns.size(); j++) {
            		Object object = dataSource.get(i);
            		
            		String[] propertyName = columns.get(j)[1].toString().split("\\.");
            		int index = (int)columns.get(j)[3];
            		List<Object> objs = null;
            		Object obj = null;
            		for (int k = 0; k < propertyName.length; k++) {
            			propertyName[k] = "get"+propertyName[k].substring(0, 1).toUpperCase() + propertyName[k].substring(1);
            			if(object!=null){
            				Method method = object.getClass().getMethod(propertyName[k]);
            				method.setAccessible(true);
            				obj = method.invoke(object);
            				
        					if(obj instanceof java.util.List){
        						objs = (List<Object>)obj;
        						object = objs.get(j-index);
        					}else{
        						object = obj;
        					}
            			}
            		}
            		
            		jxl.write.Number serialNumber = new jxl.write.Number(0,i+1,i+1,contentFormat);
            		sheet.addCell(serialNumber);
            		createDataSource(sheet, contentFormat, obj, i+1, j+1);
//            		
//            		if(obj instanceof String){
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,obj.toString(),contentFormat);
//            			sheet.addCell(label);
//            		}else if(obj instanceof Integer){
//            			 jxl.write.Number number = new jxl.write.Number(j+1,i+1,(Integer)obj,contentFormat);
//            			 sheet.addCell(number);
//            		}else if(obj instanceof Double){
//           			 	jxl.write.Number number = new jxl.write.Number(j+1,i+1,(Double)obj,contentFormat);
//           			 	sheet.addCell(number);
//            		}else if(obj instanceof Date){
//            			String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,date,contentFormat);
//    					sheet.addCell(label);
//            		}else if(obj instanceof Boolean){
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,Boolean.TRUE.equals(obj)?"是":"否",contentFormat);
//            			sheet.addCell(label);
//            		}else if(obj==null){
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,"",contentFormat);
//            			sheet.addCell(label);
//            		}else{
//            			jxl.write.Label label = new jxl.write.Label(j+1,i+1,""+obj,contentFormat);
//            			sheet.addCell(label);
//            		}
    			}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置标题
	 * @param sheet
	 */
	public void setTitle(WritableSheet sheet){
		
        try {
        	jxl.write.WritableCellFormat titleFormat = getStyle(true);//获取样式
    		sheet.setColumnView(0,4);
            jxl.write.Label serialNum = new jxl.write.Label(0,0,"序号",titleFormat);
        	sheet.addCell(serialNum);
            for (int i = 0; i < columns.size(); i++) {
            	sheet.setColumnView(i+1,Integer.parseInt(columns.get(i)[2].toString()));
            	jxl.write.Label label = new jxl.write.Label(i+1,0,columns.get(i)[0].toString(),titleFormat);
            	sheet.addCell(label);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取样式
	 * @param isTitle
	 * @return
	 */
	public jxl.write.WritableCellFormat getStyle(boolean isTitle){
		int fontSize = 8;
		if(isTitle){
			fontSize = 14;
		}
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, fontSize,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		jxl.write.WritableCellFormat wcfFHead = new jxl.write.WritableCellFormat(
				titleFont);
  	    try {
    	    wcfFHead.setAlignment(Alignment.CENTRE);
			wcfFHead.setVerticalAlignment(VerticalAlignment.CENTRE);
			if(isTitle){
				wcfFHead.setBackground(Colour.GRAY_25);
			}
			wcfFHead.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wcfFHead;
	}
	
	/**
	 * 打印订单
	 */
	public void printTheOrder(File file,List<Object[]> items){
		try {
			if(file==null){
				System.out.println("导出路径为空");
				return;
			}
            WritableWorkbook book = Workbook.createWorkbook(file);
            WritableSheet sheet = book.createSheet("订单", 0);
            
            sheet.setColumnView(0,3);//第1列的宽(10宽==85像素)
            sheet.setColumnView(1,2);//第2列的宽(10宽==85像素)
            sheet.setColumnView(2,6);//第3列的宽(10宽==85像素)
            sheet.setColumnView(8,2);//第9列的宽(10宽==85像素)
            sheet.getSettings().setDefaultRowHeight(500);//行高
            sheet.getSettings().setShowGridLines(false);//无网格
            
            jxl.write.WritableCellFormat titleFormat = getOrderStyle("title");
            sheet.mergeCells(1,1,8,1);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
            jxl.write.Label labelTitle = new jxl.write.Label(1,1,columns.get(0)[0].toString(),titleFormat);//列，行
    		sheet.addCell(labelTitle);
    		
    		jxl.write.WritableCellFormat leftFormat = getOrderStyle("left");
    		jxl.write.WritableCellFormat rightFormat = getOrderStyle("right");
    		jxl.write.WritableCellFormat bottomFormat = getOrderStyle("bottom");
            jxl.write.WritableCellFormat contentFormat = getOrderStyle("content");
            
            int itemsStart = 0;
            for (int i = 0; i < columns.size(); i++) {
            	String column2 = columns.get(i)[1].toString();
            	if(StringUtils.equals("-2", column2)){
        			itemsStart = i+1;
        		}
            }
            
            for (int i = 2; i <= itemsStart; i++) {//int i = 2; i <= columns.size()-2; i++
            	String column1 = columns.get(i-1)[0].toString();
            	String column2 = columns.get(i-1)[1].toString();
            	
            	if(StringUtils.equals("-1", column2) || StringUtils.equals("-2", column2)){
            		sheet.mergeCells(2,i,7,i);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
            	}else{
            		sheet.mergeCells(2,i,4,i);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
            		sheet.mergeCells(5,i,7,i);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
                	jxl.write.Label labelRight = new jxl.write.Label(5,i,column2,contentFormat);//列，行
                	sheet.addCell(labelRight);
            	}
            	jxl.write.Label labelLeft = new jxl.write.Label(2,i,column1,contentFormat);//列，行
        		sheet.addCell(labelLeft);
        		
            	jxl.write.Label left = new jxl.write.Label(1,i,"",leftFormat);//列，行
            	sheet.addCell(left);
            	jxl.write.Label right = new jxl.write.Label(8,i,"",rightFormat);//列，行
            	sheet.addCell(right);
    		}
            
            setOrderItems(sheet,items,itemsStart+1);//设置订单详情
            
            int num = 0;
            int y_axis = itemsStart+dataSource.size()+2;//columns.size()+dataSource.size();
            int y_maxAxis = columns.size()+dataSource.size()+1;
            for (int i = y_axis; i <= y_maxAxis; i++) {//(int i = num; i <= num+1; i++)
            	int index = itemsStart+num;//i-2-dataSource.size();
            	num++;
            	String column1 = columns.get(index)[0].toString();
            	String column2 = columns.get(index)[1].toString();
            	
            	if(StringUtils.equals("-1", column2) || StringUtils.equals("-2", column2)){
            		sheet.mergeCells(2,i,7,i);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
            	}else{
            		sheet.mergeCells(2,i,4,i);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
            		sheet.mergeCells(5,i,7,i);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
                	jxl.write.Label labelRight = new jxl.write.Label(5,i,column2,contentFormat);//列，行
                	sheet.addCell(labelRight);
            	}
            	jxl.write.Label labelLeft = new jxl.write.Label(2,i,column1,contentFormat);//列，行
        		sheet.addCell(labelLeft);
            	if(i==y_maxAxis){//最后
	       		 	sheet.mergeCells(1,i+1,8,i+1);//x,y,m,n...表示将从第x+1列，y+1行到m+1列，n+1行合并 
	       		 	jxl.write.Label labelbottom = new jxl.write.Label(1,i+1,"",bottomFormat);//列，行
	       		 	sheet.addCell(labelbottom);
            	}
            	
            	jxl.write.Label left = new jxl.write.Label(1,i,"",leftFormat);//列，行
            	sheet.addCell(left);
            	jxl.write.Label right = new jxl.write.Label(8,i,"",rightFormat);//列，行
            	sheet.addCell(right);
			}
            
            
            book.write();
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * 设置订单详情
	 */
	public void setOrderItems(WritableSheet sheet,List<Object[]> items,int itemsStart){
		try {
			jxl.write.WritableCellFormat leftFormat = getOrderStyle("left");
    		jxl.write.WritableCellFormat rightFormat = getOrderStyle("right");
        	jxl.write.WritableCellFormat contentFormat = getOrderStyle("all");//获取样式
            jxl.write.Label serialNum = new jxl.write.Label(2,itemsStart,"序号",contentFormat);//列，行
        	sheet.addCell(serialNum);
            for (int i = 0; i < items.size(); i++) {
            	sheet.setColumnView(i+3,Integer.parseInt(items.get(i)[2].toString()));
            	jxl.write.Label label = new jxl.write.Label(i+3,itemsStart,items.get(i)[0].toString(),contentFormat);
            	sheet.addCell(label);
            	
            	jxl.write.Label left = new jxl.write.Label(1,itemsStart,"",leftFormat);//列，行
            	sheet.addCell(left);
            	jxl.write.Label right = new jxl.write.Label(8,itemsStart,"",rightFormat);//列，行
            	sheet.addCell(right);
    		}
            
            itemsStart = itemsStart + 1;
            for (int i = 0; i < dataSource.size(); i++) {
            	for (int j = 0; j < items.size(); j++) {
            		Object object = dataSource.get(i);
            		String[] propertyName = items.get(j)[1].toString().split("\\.");
            		Object obj = null;
            		for (int k = 0; k < propertyName.length; k++) {
//            			obj = PropertyUtils.getSimpleProperty(object, propertyName[k]);
            			if(!StringUtils.equals(propertyName[k], "-1")){
            				propertyName[k] = "get"+propertyName[k].substring(0, 1).toUpperCase() + propertyName[k].substring(1);
            				if(object!=null){
            					Method method = object.getClass().getMethod(propertyName[k]);
            					method.setAccessible(true);
            					obj = method.invoke(object);
            					object = obj;
            				}
            			}else{
            				obj = "无";
            			}
            		}
            		
            		jxl.write.Number serialNumber = new jxl.write.Number(2,i+itemsStart,i+1,contentFormat);
            		sheet.addCell(serialNumber);
            		createDataSource(sheet, contentFormat, obj, i+itemsStart, j+3);
            		
//            		if(obj instanceof String){
//            			jxl.write.Label label = new jxl.write.Label(j+3,i+itemsStart,obj.toString(),contentFormat);
//            			sheet.addCell(label);
//            		}else if(obj instanceof Integer){
//            			 jxl.write.Number number = new jxl.write.Number(j+3,i+itemsStart,(Integer)obj,contentFormat);
//            			 sheet.addCell(number);
//            		}else if(obj instanceof Double){
//           			 	jxl.write.Number number = new jxl.write.Number(j+3,i+itemsStart,(Double)obj,contentFormat);
//           			 	sheet.addCell(number);
//            		}else if(obj instanceof Date){
//            			String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);
//            			jxl.write.Label label = new jxl.write.Label(j+3,i+itemsStart,date,contentFormat);
//    					sheet.addCell(label);
//            		}else if(obj instanceof Boolean){
//            			jxl.write.Label label = new jxl.write.Label(j+3,i+itemsStart,Boolean.TRUE.equals(obj)?"是":"否",contentFormat);
//            			sheet.addCell(label);
//            		}else if(obj==null){
//            			jxl.write.Label label = new jxl.write.Label(j+3,i+itemsStart,"",contentFormat);
//            			sheet.addCell(label);
//            		}else{
//            			jxl.write.Label label = new jxl.write.Label(j+3,i+itemsStart,""+obj,contentFormat);
//            			sheet.addCell(label);
//            		}
    			}
            	jxl.write.Label left = new jxl.write.Label(1,i+itemsStart,"",leftFormat);//列，行
            	sheet.addCell(left);
            	jxl.write.Label right = new jxl.write.Label(8,i+itemsStart,"",rightFormat);//列，行
            	sheet.addCell(right);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取样式
	 * @param isTitle
	 * @return
	 */
	public jxl.write.WritableCellFormat getOrderStyle(String stype){
		int fontSize = 11;
		if(StringUtils.equals(stype, "title")){
			fontSize = 14;
		}
		WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), fontSize,WritableFont.NO_BOLD, false, 
				UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
		jxl.write.WritableCellFormat wcfFHead = new jxl.write.WritableCellFormat(titleFont);
  	    try {
  	    	wcfFHead.setWrap(true); 
  	    	if(StringUtils.equals(stype, "title")){//标题
  	    		wcfFHead.setAlignment(Alignment.CENTRE);
  	    		wcfFHead.setBorder(Border.LEFT, BorderLineStyle.THIN);
  	    		wcfFHead.setBorder(Border.RIGHT, BorderLineStyle.THIN);
  	    		wcfFHead.setBorder(Border.TOP, BorderLineStyle.THIN);
  	    	}else if(StringUtils.equals(stype, "left")){//左边
  	    		wcfFHead.setBorder(Border.LEFT, BorderLineStyle.THIN);
  	    	}else if(StringUtils.equals(stype, "right")){//右边
  	    		wcfFHead.setBorder(Border.RIGHT, BorderLineStyle.THIN);
  	    	}else if(StringUtils.equals(stype, "bottom")){//最后
  	    		wcfFHead.setBorder(Border.LEFT, BorderLineStyle.THIN);
  	    		wcfFHead.setBorder(Border.RIGHT, BorderLineStyle.THIN);
  	    		wcfFHead.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
  	    	}else if(StringUtils.equals(stype, "all")){
  	    		wcfFHead.setAlignment(Alignment.CENTRE);
  	    		wcfFHead.setBorder(Border.ALL, BorderLineStyle.THIN);
  	    	}
  	    	wcfFHead.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return wcfFHead;
	}
	
	/**
	 * dataSource
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public void createDataSource(WritableSheet sheet,WritableCellFormat contentFormat,Object obj,int i,int j) throws RowsExceededException, WriteException{
//		jxl.write.Number serialNumber = new jxl.write.Number(0,i,i,contentFormat);
//		sheet.addCell(serialNumber);
		
		if(obj instanceof String){
			jxl.write.Label label = new jxl.write.Label(j,i,obj.toString(),contentFormat);
			sheet.addCell(label);
		}else if(obj instanceof Integer){
			 jxl.write.Number number = new jxl.write.Number(j,i,(Integer)obj,contentFormat);
			 sheet.addCell(number);
		}else if(obj instanceof Double){
			 	jxl.write.Number number = new jxl.write.Number(j,i,(Double)obj,contentFormat);
			 	sheet.addCell(number);
		}else if(obj instanceof Date){
			String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);
			jxl.write.Label label = new jxl.write.Label(j,i,date,contentFormat);
			sheet.addCell(label);
		}else if(obj instanceof Boolean){
			jxl.write.Label label = new jxl.write.Label(j,i,Boolean.TRUE.equals(obj)?"是":"否",contentFormat);
			sheet.addCell(label);
		}else if(obj==null){
			jxl.write.Label label = new jxl.write.Label(j,i,"",contentFormat);
			sheet.addCell(label);
		}else{
			jxl.write.Label label = new jxl.write.Label(j,i,""+obj,contentFormat);
			sheet.addCell(label);
		}
	}
}
