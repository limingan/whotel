package com.whotel.card.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.whotel.card.entity.Prize;
import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.dao.mongo.MongoFactoryBean;

@Repository
public class PrizeDao extends MongoDao<Prize, String> {
	
	@Autowired
	private MongoFactoryBean mongoFactory;
	
	public void updatePrizeCount(String prizeId){
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<Prize> updateOperations = ds.createUpdateOperations(Prize.class);
		updateOperations.inc("count");
		Query<Prize> query = createQuery();
		query.field("id").equal(prizeId);
		ds.update(query, updateOperations);
	}
	
	public void updatePrizeNum(String prizeId){
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<Prize> updateOperations = ds.createUpdateOperations(Prize.class);
		updateOperations.dec("number");
		Query<Prize> query = createQuery();
		query.field("id").equal(prizeId);
		ds.update(query, updateOperations);
	}
}
