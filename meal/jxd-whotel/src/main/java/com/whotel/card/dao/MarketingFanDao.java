package com.whotel.card.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.whotel.card.entity.MarketingFan;
import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.dao.mongo.MongoFactoryBean;

@Repository
public class MarketingFanDao extends MongoDao<MarketingFan, String> {
	
	@Autowired
	private MongoFactoryBean mongoFactory;
	
	public void increaseFanNums(String openId,  int number) {
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<MarketingFan> updateOperations = ds
				.createUpdateOperations(MarketingFan.class);
		updateOperations.inc("fanNums", number);
		
		Query<MarketingFan> query = createQuery();
		query.field("openId").equal(openId);
		ds.update(query, updateOperations);
	}
	
	public void increaseMemberFanNums(String openId,  int number) {
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<MarketingFan> updateOperations = ds
				.createUpdateOperations(MarketingFan.class);
		updateOperations.inc("memberFanNums", number);
		
		Query<MarketingFan> query = createQuery();
		query.field("openId").equal(openId);
		ds.update(query, updateOperations);
	}
	
	public void decreaseFanNums(String openId, int number) {
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<MarketingFan> updateOperations = ds
				.createUpdateOperations(MarketingFan.class);
		updateOperations.inc("fanNums", -number);
		
		Query<MarketingFan> query = createQuery();
		query.field("openId").equal(openId);
		ds.update(query, updateOperations);
	}
}
