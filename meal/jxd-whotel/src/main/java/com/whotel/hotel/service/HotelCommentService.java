package com.whotel.hotel.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.BeanUtil;
import com.whotel.hotel.dao.CommentConfigDao;
import com.whotel.hotel.dao.HotelCommentDao;
import com.whotel.hotel.dao.ThumbUpRecordDao;
import com.whotel.hotel.entity.CommentConfig;
import com.whotel.hotel.entity.HotelComment;
import com.whotel.hotel.entity.ThumbUpRecord;

@Service
public class HotelCommentService {
	
	@Autowired
	private HotelCommentDao hotelCommentDao;
	
	@Autowired
	private ThumbUpRecordDao thumbUpRecordDao;
	
	@Autowired
	private CommentConfigDao commentConfigDao;
	
	public CommentConfig getCommentConfig(String companyId){
		return commentConfigDao.getByProperty("companyId", companyId);
	}
	
	public void saveCommentConfig(CommentConfig commentConfig){
		commentConfigDao.save(commentConfig);
	}
	
	public void findHotelComments(Page<HotelComment> page) {
		hotelCommentDao.find(page);
	}
	
	/**
	 * 查询酒店下的点评
	 */
	public List<HotelComment> listHotelComment(String companyId,String hotelCode){
		Query<HotelComment> query = hotelCommentDao.createQuery();
		query.field("companyId").equal(companyId);
		if(StringUtils.isNotBlank(hotelCode)){
			query.field("hotelCode").equal(hotelCode);
		}
		query.field("checkStatus").equal(true);//审核通过的
		query.order("-createTime");
		return hotelCommentDao.findByQuery(query).asList();
	}
	
	/**
	 * 查看评论详情
	 */
	public HotelComment getCommentDetails(String id){
		if(StringUtils.isBlank(id)) {
			return null;
		}
		return hotelCommentDao.get(id);
	}
	
	/**
	 * 点评
	 */
	public void saveHotelComment(HotelComment hotelComment,String replyId){
		if(hotelComment != null){
			if(StringUtils.isNotBlank(replyId)){//回复
				HotelComment updateComment = getCommentDetails(replyId);
				List<HotelComment> comments = updateComment.getComments();
				if(comments == null){
					comments = new ArrayList<>();
				}
				hotelComment.setCreateTime(new Date());
				comments.add(hotelComment);
				updateComment.setComments(comments);
				hotelCommentDao.save(updateComment);
			}else if(StringUtils.isNotBlank(hotelComment.getId())) {//审核，修改点评
				HotelComment updateComment = getCommentDetails(hotelComment.getId());
				BeanUtil.copyProperties(updateComment, hotelComment, true);
				hotelCommentDao.save(updateComment);
			}else{
				hotelComment.setCreateTime(new Date());
				hotelCommentDao.save(hotelComment);
			}
		}
	}
	
	public void updateThumbUpCount(HotelComment hotelComment){
		hotelCommentDao.save(hotelComment);
	}

	public void deleteHotelCommentById(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				hotelCommentDao.delete(id);
			}
		}
	}
	
	public void saveThumbUpRecordDao(ThumbUpRecord thumbUpRecord){
		thumbUpRecord.setCreateTime(new Date());
		thumbUpRecordDao.save(thumbUpRecord);
	}
	
	public Map<String,ThumbUpRecord> findThumbUpRecordMap(String openId){
		Map<String,ThumbUpRecord> map = new HashMap<String, ThumbUpRecord>();
		List<ThumbUpRecord> list = thumbUpRecordDao.findByProperty("openId", openId);
		for (ThumbUpRecord record : list) {
			map.put(record.getCommentId(), record);
		}
		return map;
	}
	
	public HotelComment getHotelCommentsByOrderSn(String orderSn){
		return hotelCommentDao.getByProperty("orderSn", orderSn);
	}

}
