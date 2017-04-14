package com.weixin.core.bean;

public class NewsArticle {
	
	private String title;
    private String thumb_media_id;
    private String author;
    private String digest;
    private String show_cover_pic;
    private String content;
    private String content_source_url;
    private String url;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "NewsArticle [title=" + title + ", thumb_media_id="
				+ thumb_media_id + ", author=" + author + ", digest=" + digest
				+ ", show_cover_pic=" + show_cover_pic + ", content=" + content
				+ ", content_source_url=" + content_source_url + ", url=" + url
				+ "]";
	}
	
}
