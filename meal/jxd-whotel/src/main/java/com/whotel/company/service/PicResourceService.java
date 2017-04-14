package com.whotel.company.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.weixin.core.api.MediaUploador;
import com.weixin.core.api.NewsResourceApi;
import com.weixin.core.bean.MaterialItem;
import com.weixin.core.common.BaseToken;
import com.weixin.core.common.WeixinConstant;
import com.whotel.common.dao.mongo.Page;
import com.whotel.common.util.QiniuUtils;
import com.whotel.company.dao.PicResourceDao;
import com.whotel.company.entity.PicResource;
import com.whotel.company.entity.PublicNo;
import com.whotel.weixin.service.TokenService;

/**
 * 图片素材数据管理服务类
 * @author 冯勇
 *
 */
@Service
public class PicResourceService {
	
	private static final Logger logger = Logger.getLogger(PicResourceService.class);
	
	@Autowired
	private PicResourceDao picResourceDao;
	
	@Autowired
	private PublicNoService publicNoService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 保存图片
	 * @param picResource
	 */
	public void savePicResource(PicResource picResource) {
		if(picResource != null) {
			Date now = new Date();
			Date createTime = picResource.getCreateTime();
			if(createTime == null) {
				picResource.setCreateTime(new Date());
			}
			picResource.setName(String.valueOf(now.getTime()));
			picResourceDao.save(picResource);
		}
	}
	
	/**
	 * 保存图片素材并同步更新到微信公众平台
	 * @param picResource
	 */
	public void saveAndSynchronizePicResourceToMP(final PicResource picResource) {
		savePicResource(picResource);
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				synchronizePicResourceToMP(picResource);
			}
		});
	}
	
	/**
	 * 同步素材到微信公众后台
	 * @param picResource
	 * @return
	 */
	public String synchronizePicResourceToMP(PicResource picResource) {
		
		String mediaId = picResource.getMediaId();
		String companyId = picResource.getCompanyId();
		PublicNo publicNo = publicNoService.getPublicNoByCompanyId(companyId);
		if(publicNo == null) {
			logger.warn("未绑定公众号！");
			return null;
		}
			
		try {
			BaseToken accessToken = TokenService.getTokenService().getAccessToken(publicNo.getAppId(), publicNo.getAppSecret());
			if(StringUtils.isNotBlank(mediaId)) {
				NewsResourceApi.deleteMaterial(mediaId, accessToken);
			}
			String[] pic_media = MediaUploador.uploadPersistentWebPic(accessToken, WeixinConstant.MsgType.ImageMsg, picResource.getPicUrl());
			if(pic_media != null) {
				mediaId = pic_media[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		picResource.setMediaId(mediaId);
		savePicResource(picResource);
		return mediaId;
	}
	
	/**
	 * 分页查找图片
	 * @param page 分页工具类
	 */
	public void findPicResources(Page<PicResource> page) {
		picResourceDao.find(page);
	}
	
	public PicResource getPicResourceById(String id) {
		return picResourceDao.get(id);
	}
	
	public void deletePicResource(PicResource picResource) {
		picResourceDao.delete(picResource);
	}
	
	/**
	 * 批量删除栏图片
	 * @param ids
	 */
	public void deleteMorePicResource(String ids, String companyId) {
		if(StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				PicResource picResource = getPicResourceById(id);
				if(picResource != null) {
					picResource.setCompanyId(companyId);
					deletePicResource(picResource);
				}
			}
		}
	}
	
	public PicResource getPicResourceByMediaId(String mediaId) {
		return picResourceDao.getByProperty("mediaId", mediaId);
	}
	
	/**
	 * 将微信公众平台图片素材数据转换为第三方平台图片素材数据
	 * @param accessToken
	 * @param item
	 * @param companyId
	 * @return
	 */
	public PicResource transferToResource(BaseToken accessToken, MaterialItem item, String companyId) {
		
		String media_id = item.getMedia_id();
		
		PicResource picResource = getPicResourceByMediaId(media_id);
		if(picResource == null) {
			picResource = new PicResource();
			Date update_time = item.getUpdate_time();
			String itemName = item.getName();
			
			picResource.setCreateTime(update_time);
			picResource.setName(itemName);
			
			try {
				byte[] data = NewsResourceApi.getImageMaterial(media_id, accessToken);
				String key = QiniuUtils.upload(data);
				picResource.setPic(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			picResource.setCompanyId(companyId);
			savePicResource(picResource);
		}
		return picResource;
	}
}
