package com.whotel.card.entity;

import java.util.List;

import com.whotel.common.util.QiniuUtils;

public class GameLevel {
	
	private Integer seqNumber;//序号
	
	private String[] images;//图片

	public Integer getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(Integer seqNumber) {
		this.seqNumber = seqNumber;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
	
	public String[] getImagesUrls() {
		if(images != null) {
			String[] attachs = new String[images.length];
			for(int i=0; i<images.length; i++) {
				attachs[i] = QiniuUtils.getResUrl(images[i]);
			}
			return attachs;
		}
		return null;
	}
	
}
