package com.whotel.common.dto;

public class UploadFileState {

	private long readedBytes = 0L;/* 已经上传的位数 */
	private long totalBytes = 0L;/* 文件所占位数 */
	private int currentItem = 0;
	private int rate = 0; /* 上传百分比 */

	public long getReadedBytes() {
		return readedBytes;
	}

	public void setReadedBytes(long readedBytes) {
		this.readedBytes = readedBytes;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public int getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(int currentItem) {
		this.currentItem = currentItem;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
}
