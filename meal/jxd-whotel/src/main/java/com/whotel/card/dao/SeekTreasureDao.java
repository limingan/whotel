package com.whotel.card.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.whotel.card.entity.SeekTreasure;
import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.dao.mongo.MongoFactoryBean;

@Repository
public class SeekTreasureDao extends MongoDao<SeekTreasure, String> {
	
	@Autowired
	private MongoFactoryBean mongoFactory;
	
	public void updateParticipationCounts(String seekTreasureId){
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<SeekTreasure> updateOperations = ds.createUpdateOperations(SeekTreasure.class);
		updateOperations.inc("participationCounts");
		Query<SeekTreasure> query = createQuery();
		query.field("id").equal(seekTreasureId);
		ds.update(query, updateOperations);
	}
}
