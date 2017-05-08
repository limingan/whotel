package com.whotel.meal.service;

import com.google.common.collect.Lists;
import com.whotel.card.dao.PrizeDao;
import com.whotel.card.dao.PrizeRecordDao;
import com.whotel.card.entity.Prize;
import com.whotel.card.entity.PrizeRecord;
import com.whotel.card.enums.PrizeType;
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
    @Autowired
    PrizeDao prizeDao;

    public List<PrizeRecord> getByOpenId(String openId, String companyId) {
        Map<String, Serializable> properties = new HashMap<>();
        properties.put("companyId", companyId);
        properties.put("openId", openId);
        List<PrizeRecord> list = prizeRecordDao.findByProperties(properties);

        List<PrizeRecord> result = Lists.newArrayList();
        for(PrizeRecord prizeRecord : list){
            prizeRecord.setPrizeValue(1F);
            Prize prize = prizeDao.get(prizeRecord.getPrizeId());
//            if(null != prize && !PrizeType.GOODS.equals(prize.getType())){
                result.add(prizeRecord);
//            }
        }
        return result;
    }

}
