package com.whotel.card.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.whotel.card.entity.Activity;
import com.whotel.common.dao.mongo.MongoDao;
import com.whotel.common.dao.mongo.MongoFactoryBean;

@Repository
public class ActivityDao extends MongoDao<Activity, String> {
	
	@Autowired
	private MongoFactoryBean mongoFactory;
	
	public void updateParticipationCounts(String activityId){
		Datastore ds = mongoFactory.getMorphia().createDatastore(
				mongoFactory.getMongo(), mongoFactory.getDatabase());
		UpdateOperations<Activity> updateOperations = ds.createUpdateOperations(Activity.class);
		updateOperations.inc("participationCounts");
		Query<Activity> query = createQuery();
		query.field("id").equal(activityId);
		ds.update(query, updateOperations);
	}
}
