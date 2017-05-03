package com.whotel.meal.service;

import com.whotel.card.dao.GuestDao;
import com.whotel.card.entity.Guest;
import com.whotel.common.dao.mongo.Order;
import com.whotel.common.enums.TradeStatus;
import com.whotel.hotel.enums.HotelOrderStatus;
import com.whotel.meal.entity.MealOrder;
import org.apache.log4j.Logger;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limingan on 2017/5/3.
 */
@Service
public class GuestService {

    private static final Logger logger = Logger.getLogger(GuestService.class);

    @Autowired
    GuestDao guestDao;

    public Guest getById(String id) {
        return guestDao.get(id);
    }

    public void save(Guest guest) {
        guestDao.save(guest);
    }

    public List<Guest> getByOpenId(String openId, String companyId) {
        Query<Guest> query = guestDao.createQuery();
        query.field("openId").equal(openId);
        query.field("companyId").equal(companyId);
        query.order(guestDao.getOrderString(Order.desc("isDefault")));
        return query.asList();
    }

    public void delete(String id) {
        guestDao.delete(id);
    }

    public void setDefault(String companyId, String openId, String guestId) {
        List<Guest> list = this.getByOpenId(openId, companyId);
        for (Guest guest : list) {
            String id = guest.getId();
            Integer isDefault = guest.getIsDefault();
            if (id.equals(guestId) && (null == isDefault || isDefault.equals(0))) {
                guest.setIsDefault(1);
                guestDao.save(guest);
            } else if (!id.equals(guestId) && null != isDefault && isDefault.equals(1)) {
                guest.setIsDefault(0);
                guestDao.save(guest);
            }
        }
    }
}
