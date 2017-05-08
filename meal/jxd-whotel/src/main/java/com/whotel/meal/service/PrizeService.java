package com.whotel.meal.service;

import com.whotel.card.dao.PrizeRecordDao;
import com.whotel.card.entity.PrizeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limingan on 2017/5/8.
 */
@Service
public class PrizeService {

    @Autowired
    PrizeRecordDao prizeRecordDao;

    public List<PrizeRecord> getByOpenId(String openId, String companyId) {
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", companyId);
        properties.put("openId", openId);
        return prizeRecordDao.findByProperties(properties);
    }

}
