package com.whotel.common.dto;

/**
 * Image upload DTO
 * 
 */
@SuppressWarnings("serial")
public class UploadImgDto extends UploadFileDto {
	private String imgSizeRange; // 宽高范围(不超过：,80x70；等于：80x70,80x70; 大于：80x70,)
	private Integer limitWidth;
	private Integer limitHeight;
	private Boolean checkWidth;
	private Boolean checkHeight;
	
	/**
	 * 返回图像尺寸限制：
	 * 
	 * <pre>
	 * "~80x70" : 宽<80，高<70
	 * "50x30~80x70" : 50<宽<80，30<高<70
	 * "80x70" : 宽=80，高=70
	 * "80x70~" : 宽>80，高>70
	 * </pre>
	 * 
	 * @return
	 */
	public String getImgSizeRange() {
		return imgSizeRange;
	}

	/**
	 * 设定图像尺寸限制
	 * 
	 * <pre>
	 * "~80x70" : 宽<80，高<70
	 * "50x30~80x70" : 50<宽<80，30<高<70
	 * "80x70" : 宽=80，高=70
	 * "80x70~" : 宽>80，高>70
	 * </pre>
	 * 
	 * @param dimensionRange
	 */
	public void setImgSizeRange(String imgSizeRange) {
		this.imgSizeRange = imgSizeRange;
	}
	
	public Boolean getCheckWidth() {
		return checkWidth;
	}

	public void setCheckWidth(Boolean checkWidth) {
		this.checkWidth = checkWidth;
	}

	public Boolean getCheckHeight() {
		return checkHeight;
	}

	public void setCheckHeight(Boolean checkHeight) {
		this.checkHeight = checkHeight;
	}

	public Integer getLimitHeight() {
		return limitHeight;
	}

	public void setLimitHeight(Integer limitHeight) {
		this.limitHeight = limitHeight;
	}

	public Integer getLimitWidth() {
		return limitWidth;
	}

	public void setLimitWidth(Integer limitWidth) {
		this.limitWidth = limitWidth;
	}

}
