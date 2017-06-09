package com.whotel.thirdparty.jxd.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whotel.meal.entity.*;
import com.whotel.thirdparty.jxd.mode.CyReservationResult;
import com.whotel.thirdparty.jxd.mode.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whotel.common.enums.Gender;
import com.whotel.common.enums.TradeType;
import com.whotel.common.http.HttpHelper.Response;
import com.whotel.common.util.DateUtil;
import com.whotel.common.util.Dom4jHelper;
import com.whotel.common.util.MD5Util;
import com.whotel.ext.json.JSONConvertFactory;
import com.whotel.ext.json.JSONDataUtil;
import com.whotel.hotel.entity.Hotel;
import com.whotel.meal.enums.MealOrderStatus;
import com.whotel.thirdparty.jxd.ApiException;

/**
 * 将接口返回的值转换成公共的VO对象
 *
 * @author 冯勇
 */
public class ApiXmlVoParser {
    private static final Logger log = Logger.getLogger(ApiXmlVoParser.class);

    /**
     * 解析xml为Map
     *
     * @param xml
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> parseXml2Map(String xml, String node, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        Map<String, String> map = dom4jHelper.getAllElements(node);
        return map;
    }

    /**
     * 解析xml为对象
     *
     * @param xml
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static MemberVO parseMemberVO(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        Map<String, String> map = parseXml2Map(xml, "Row", charset);

        MemberVO memberVO = new MemberVO();
        memberVO.setProfileId(map.get("ProfileId"));
        memberVO.setGuestType(map.get("GuestType"));
        memberVO.setIsMember(map.get("IsMember"));
        memberVO.setVipType(map.get("VIPType"));
        memberVO.setBlackList(map.get("BlackList"));
        memberVO.setMbrCardType(map.get("MbrCardType"));
        memberVO.setMbrCardTypeName(map.get("MbrCardTypeName"));
        memberVO.setMbrCardNo(map.get("MbrCardNo"));
        memberVO.setRfCardNo(map.get("RFCardNo"));
        memberVO.setCreditLevel(map.get("CreditLevel"));
        memberVO.setMbrExpired(map.get("MbrExpired"));
        memberVO.setGuestCName(map.get("GuestCName"));
        memberVO.setGuestEName(map.get("GuestEName"));
        memberVO.setCttNo(map.get("CttNo"));
        memberVO.setContactor(map.get("Contactor"));
        memberVO.setRateCode(map.get("RateCode"));
        memberVO.setHalfTimeBegin(map.get("HalfTimeBegin"));
        memberVO.setDayRentBegin(map.get("DayRentBegin"));
        memberVO.setSalutation(map.get("Salutation"));
        memberVO.setCertificateType(map.get("CertificateType"));
        memberVO.setCertificateNo(map.get("CertificateNo"));
        String gender = map.get("Gender");
        if (StringUtils.equals(gender, "男")) {
            memberVO.setGender(Gender.MALE);
        } else {
            memberVO.setGender(Gender.FEMALE);
        }
        memberVO.setBirthday(map.get("Birthday"));
        memberVO.setNationality(map.get("Nationality"));
        memberVO.setVisaExpired(map.get("VisaExpired"));
        memberVO.setAddress(map.get("Address"));
        memberVO.setZipCode(map.get("ZipCode"));
        memberVO.setTel(map.get("Tel"));
        memberVO.setOfficeTel(map.get("OfficeTel"));
        memberVO.setMobile(map.get("Mobile"));
        memberVO.setFax(map.get("Fax"));
        memberVO.setEmail(map.get("Email"));
        try {
            memberVO.setBalance(Float.valueOf(map.get("Balance")));
        } catch (Exception e) {
            memberVO.setBalance(0f);
        }
        try {
            memberVO.setDebtAmount(Float.valueOf(map.get("DebtAmount")));
        } catch (Exception e) {
            memberVO.setDebtAmount(0f);
        }

        try {
            memberVO.setValidScore(Float.valueOf(map.get("ValidScore")));
        } catch (Exception e) {
            memberVO.setValidScore(0f);
        }
        memberVO.setCreateDate(DateUtil.parseDatetime(map.get("CreateDate")));
        memberVO.setRemark(map.get("Remark"));

        try {
            memberVO.setTicketqty(Integer.valueOf(map.get("ticketqty")));
        } catch (Exception e) {
            memberVO.setTicketqty(0);
        }

        try {
            memberVO.setSubCardCount(Integer.valueOf(map.get("SubCardCount")));
        } catch (Exception e) {
            memberVO.setSubCardCount(0);
        }

        try {
            memberVO.setTicketamt(Float.valueOf(map.get("ticketamt")));
        } catch (Exception e) {
            memberVO.setTicketamt(0f);
        }

        try {
            memberVO.setTotalUsedBalance(Float.valueOf(map.get("TotalUsedBalance")));
        } catch (Exception e) {
            memberVO.setTotalUsedBalance(0f);
        }

        try {
            memberVO.setBaseAmtBalance(Float.valueOf(map.get("BaseAmtBalance")));
        } catch (Exception e) {
            memberVO.setBaseAmtBalance(0f);
        }
        try {
            memberVO.setIncamount(Float.valueOf(map.get("incamount")));
        } catch (Exception e) {
            memberVO.setIncamount(0f);
        }
        try {
            memberVO.setIncamt(Float.valueOf(map.get("incamt")));
        } catch (Exception e) {
            memberVO.setIncamt(0f);
        }
        try {
            memberVO.setChargeamt(Float.valueOf(map.get("chargeamt")));
        } catch (Exception e) {
            memberVO.setChargeamt(0f);
        }
        try {
            memberVO.setUsedScore(Float.valueOf(map.get("UsedScore")));
        } catch (Exception e) {
            memberVO.setUsedScore(0f);
        }
        try {
            memberVO.setDeductamt(Float.valueOf(map.get("deductamt")));
        } catch (Exception e) {
            memberVO.setDeductamt(0f);
        }
        try {
            memberVO.setScoreToAmount(Float.valueOf(map.get("ScoreToAmount")));
        } catch (Exception e) {
            memberVO.setScoreToAmount(0f);
        }
        try {
            memberVO.setScoretoamountratio(Float.valueOf(map.get("scoretoamountratio")));
        } catch (Exception e) {
            memberVO.setScoretoamountratio(0f);
        }
        memberVO.setFax(map.get("SaleName"));
        memberVO.setSaleName(map.get("SaleName"));
        memberVO.setEmail(map.get("SaleMobile"));
        memberVO.setIntroducer(map.get("Introducer"));
        memberVO.setIntroducerName(map.get("IntroducerName"));
        if (memberVO.getBalance() != null && memberVO.getIncamount() != null) {
            memberVO.setBalance(memberVO.getBalance() - memberVO.getIncamount());
        }
        return memberVO;
    }

    /**
     * 解析xml为对象
     *
     * @param xml
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static ReservationResult parseReservationResult(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        Map<String, String> map = parseXml2Map(xml, "ReservationResult", charset);

        ReservationResult result = new ReservationResult();
        result.setHotelCode(map.get("ResortId"));
        result.setConfirmationID(map.get("confirmationID"));
        result.setOrderNo(map.get("OrderNo"));
        result.setErrorMsg(map.get("ErrorMsg"));
        return result;
    }

    /**
     * 解析xml为对象
     *
     * @param xml
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static ReservationResult parseReservationResult(String xml, String charset, String node) throws DocumentException, UnsupportedEncodingException {
        Map<String, String> map = parseXml2Map(xml, node, charset);

        ReservationResult result = new ReservationResult();
        result.setHotelCode(map.get("HotelCode"));
        result.setConfirmationID(map.get("confirmationID"));
        result.setOrderNo(map.get("ResortRegID"));
        result.setErrorMsg(map.get("ErrorMsg"));
        return result;
    }

    /**
     * 解析为XML对象》》会员卡升级
     *
     * @param xml
     * @param charset
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static MbrCardUpgradeResult parseMbrCardUpgradeResult(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        Map<String, String> map = parseXml2Map(xml, "Row", charset);
        MbrCardUpgradeResult result = new MbrCardUpgradeResult();
        result.setRtnCode(map.get("rtncode"));
        result.setRtnInfo(map.get("rtninfo"));
        return result;
    }

    public static HotelOrderDetailVO parseHotelOrderDetailVO(String xml, String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        HotelOrderDetailVO hotelOrderDetailVO = null;
        Map<String, String> orderMap = dom4jHelper.getAllElements("OrderDetail");
        if (orderMap != null) {
            hotelOrderDetailVO = new HotelOrderDetailVO();
            hotelOrderDetailVO.setDetailId(orderMap.get("DetailId"));
            hotelOrderDetailVO.setOrderNo(orderMap.get("OrderNo"));
            hotelOrderDetailVO.setResortId(orderMap.get("ResortId"));
            hotelOrderDetailVO.setItemCode(orderMap.get("ItemCode"));
            hotelOrderDetailVO.setItemName(orderMap.get("ItemName"));
            hotelOrderDetailVO.setSalesPromotionId(orderMap.get("SalesPromotionId"));
            hotelOrderDetailVO.setSalesPromotionName(orderMap.get("SalesPromotionName"));
            hotelOrderDetailVO.setArriveDate(orderMap.get("ArriveDate"));
            hotelOrderDetailVO.setLeaveDate(orderMap.get("LeaveDate"));
            hotelOrderDetailVO.setItemQty(orderMap.get("ItemQty"));
            hotelOrderDetailVO.setTotalAmount(orderMap.get("TotalAmount"));
            hotelOrderDetailVO.setGuestName(orderMap.get("GuestName"));
            hotelOrderDetailVO.setRoomSpecial(orderMap.get("RoomSpecial"));
            hotelOrderDetailVO.setArriveTime(orderMap.get("ArriveTime"));
            hotelOrderDetailVO.setGuestRemark(orderMap.get("GuestRemark"));
            hotelOrderDetailVO.setAuthStatus(orderMap.get("AuthStatus"));
            hotelOrderDetailVO.setLockId(orderMap.get("LockId"));
            hotelOrderDetailVO.setStatus(orderMap.get("Status"));
        }
        return hotelOrderDetailVO;
    }

    /**
     * 将xml内容解析为订单列表
     *
     * @param xml
     * @return
     * @throws UnsupportedEncodingException
     */
    public static List<HotelOrderVO> parseHotelOrderVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("orderMain");
        List<HotelOrderVO> hotelOrderVOs = parseHotelOrderVOs(list);
        return hotelOrderVOs;
    }

    private static List<HotelOrderVO> parseHotelOrderVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<HotelOrderVO> orders = new ArrayList<HotelOrderVO>();
            for (Map<String, String> map : list) {
                HotelOrderVO vo = new HotelOrderVO();
                vo.setOrderNo(map.get("OrderNo"));
                vo.setMbrCardNo(map.get("MbrCardNo"));
                vo.setGuestType(map.get("GuestType"));
                vo.setSource(map.get("Source"));
                vo.setPayMethodType(map.get("PayMethodType"));
                vo.setPaidRefId(map.get("PaidRefId"));
                vo.setConfirmTypeName(map.get("ConfirmTypeName"));
                vo.setTotalAmount(Float.valueOf((map.get("TotalAmount"))));
                vo.setPaidAmount(Float.valueOf(map.get("PaidAmount")));
                vo.setCreateDate(map.get("CreateDate"));
                vo.setContactName(map.get("ContactName"));
                vo.setContactMobile(map.get("ContactMobile"));
                vo.setContactTel(map.get("ContactTel"));
                vo.setContactEmail(map.get("ContactEmail"));
                vo.setHotelCheckStatus(map.get("HotelCheckStatus"));
                vo.setHotelRevertMsg(map.get("HotelRevertMsg"));
                vo.setHotelRevertName(map.get("HotelRevertName"));
                vo.setHotelRevertDate(map.get("HotelRevertDate"));
                vo.setProfileId(map.get("ProfileId"));
                vo.setResortId(map.get("ResortId"));
                vo.setOrderType(map.get("OrderType"));
                vo.setConfirmationID(map.get("ConfirmationID"));
                orders.add(vo);
            }
            return orders;
        }
        return null;
    }

    public static List<OccupancyManVO> parseOccupancyManVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<OccupancyManVO> occupancyMans = parseOccupancyManVOs(list);
        return occupancyMans;
    }

    private static List<OccupancyManVO> parseOccupancyManVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<OccupancyManVO> occupancyMans = new ArrayList<OccupancyManVO>();
            for (Map<String, String> map : list) {
                OccupancyManVO vo = new OccupancyManVO();
                vo.setRegId(map.get("RegId"));//登记号
                vo.setRoomNo(map.get("RoomNo"));//房号
                vo.setLockNo(map.get("LockNo"));//锁号
                vo.setGuestCname(map.get("GuestCname"));//客人姓名
                vo.setArrDate(map.get("ArrDate"));//入住时间
                vo.setDepDate(map.get("DepDate"));//离店时间
                vo.setIdNo(map.get("idNo"));//身份证号码
                vo.setMobile(map.get("mobile"));
                occupancyMans.add(vo);
            }
            return occupancyMans;
        }
        return null;
    }

    public static List<MbrCardUpgradeVO> parseMbrCardUpgradeVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<MbrCardUpgradeVO> mvos = parseMbrCardUpgradeVOs(list);
        return mvos;
    }

    private static List<MbrCardUpgradeVO> parseMbrCardUpgradeVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<MbrCardUpgradeVO> mvos = new ArrayList<MbrCardUpgradeVO>();
            for (Map<String, String> map : list) {
                MbrCardUpgradeVO vo = new MbrCardUpgradeVO();
                vo.setMbrCardTypeCode(map.get("mbrcardtypecode"));
                vo.setMbrCardTypeCname(map.get("mbrcardtypecname"));
                vo.setEllAmount(Float.valueOf(map.get("sellamount")));
                mvos.add(vo);
            }
            return mvos;
        }
        return null;
    }

    public static List<PointRecordVO> parsePointRecordVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<PointRecordVO> rvos = parsePointRecordVOs(list);
        return rvos;
    }

    private static List<PointRecordVO> parsePointRecordVOs(List<Map<String, String>> list) {
        if (list != null) {

            List<PointRecordVO> rvos = new ArrayList<PointRecordVO>();
            for (Map<String, String> map : list) {
                PointRecordVO vo = new PointRecordVO();
                vo.setAmount(Float.valueOf(map.get("Score")));
                vo.setProfileId(map.get("ProfileId"));
                vo.setRemark(map.get("Remark"));
                vo.setOutlet(map.get("Outlet"));
                vo.setCreateDate(DateUtil.parseDatetime(map.get("CreateDate")));
                vo.setTradeType(TradeType.CHARGE);
                vo.setImgUrl("");
                rvos.add(vo);
            }
            return rvos;
        }
        return null;
    }

    public static List<PointRecordVO> parsePointRecordExchangesVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<PointRecordVO> rvos = parsePointRecordExchangesVOs(list);
        return rvos;
    }

    private static List<PointRecordVO> parsePointRecordExchangesVOs(List<Map<String, String>> list) {
        if (list != null) {

            List<PointRecordVO> rvos = new ArrayList<PointRecordVO>();
            for (Map<String, String> map : list) {
                PointRecordVO vo = new PointRecordVO();
                vo.setProfileId(map.get("ProfileId"));
                vo.setAmount(Float.valueOf(map.get("Score")));
                vo.setRemark(map.get("ItemCName"));
                vo.setCreateDate(DateUtil.parseDatetime(map.get("TransDate")));
                vo.setStatus(map.get("Status"));
                vo.setTradeType(TradeType.DEDUCT);
                if (map.get("ImageUrl") != null && map.get("ImageId") != null) {
                    vo.setImgUrl(map.get("ImageUrl").replace("{0}", map.get("ImageId")));
                }
                rvos.add(vo);
            }
            return rvos;
        }
        return null;
    }

    /**
     * 检查返回内容是否为是包含业务逻辑出错的信息，如果属于通用出错返回信息，则包装成Exception抛出
     *
     * @param returnXml
     * @throws ApiException, Exception
     */
    public static void checkReturnContent(Response res) throws ApiException, Exception {
        if (res.statusCode() == 200) { // 成功的状态码说明接口调用获得正常返回
            String html = res.html();

            if (log.isDebugEnabled()) {
                log.debug(res.charset() + ", result: \n" + html);
            }

            if (StringUtils.contains(html, "<ErrorMessage>")) {
                log.error(html);
                throw new ApiException(StringUtils.substringBetween(html, "<Message>", "</Message>"));
            }
        } else { // 出错的状态码
            throw new ApiException("API invoke invalid status: " + res.statusCode());
        }
    }

    public static List<RoomTypeVO> parseRoomTypeVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<RoomTypeVO> rtvos = parseRoomTypeVOs(list);
        return rtvos;
    }

    private static List<RoomTypeVO> parseRoomTypeVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<RoomTypeVO> rtvos = new ArrayList<RoomTypeVO>();
            for (Map<String, String> map : list) {
                RoomTypeVO vo = new RoomTypeVO();
                vo.setCname(map.get("CName"));
                vo.setEname(map.get("EName"));
                vo.setAlias(map.get("Alias"));
                vo.setCode(map.get("Code"));
                vo.setRateCode(map.get("RateCode"));
                vo.setHotelCode(map.get("HotelCode"));
                rtvos.add(vo);
            }
            return rtvos;
        }
        return null;
    }

    /**
     * 返回通用信息
     *
     * @param xml
     * @param charset
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static GeneralMsg parseGeneralMsg(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        Map<String, String> map = null;
        GeneralMsg generalMsg = new GeneralMsg();
        if (StringUtils.contains(xml, "<ErrorMessage>")) {
            log.error(xml);
            map = parseXml2Map(xml, "ErrorMessage", charset);
            generalMsg.setIsSuccess(false);
        } else if (StringUtils.contains(xml, "<ReturnMessage>")) {
            map = parseXml2Map(xml, "ReturnMessage", charset);
            generalMsg.setIsSuccess(true);
        } else {
            log.error(xml);
        }

        if (map != null) {
            generalMsg.setMessageNo(map.get("MessageNo"));
            generalMsg.setMessage(map.get("Message"));
            generalMsg.setOpType(map.get("OpType"));
        }
        return generalMsg;
    }

    public static List<MbrCardVO> parseMbrCardVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));

        log.info("xml charset:" + charset);
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<MbrCardVO> mcVOs = parseMbrCardVOs(list);
        return mcVOs;
    }

    private static List<MbrCardVO> parseMbrCardVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<MbrCardVO> mcVOs = new ArrayList<MbrCardVO>();
            for (Map<String, String> map : list) {
                MbrCardVO vo = new MbrCardVO();
                vo.setProfileId(map.get("ProfileId"));
                vo.setMbrCardType(map.get("MbrCardType"));
                vo.setMbrCardTypeName(map.get("MbrCardTypeName"));
                vo.setMbrCardNo(map.get("MbrCardNo"));
                vo.setRFCardNo(map.get("RFCardNo"));
                vo.setMbrExpired(map.get("MbrExpired"));
                vo.setOnLinePay(map.get("OnLinePay"));
                try {
                    vo.setBalance(Float.valueOf(map.get("Balance")));
                } catch (Exception e) {
                    vo.setBalance(0f);
                }
                try {
                    vo.setBaseamtbalance(Float.valueOf(map.get("baseamtbalance")));
                } catch (Exception e) {
                    vo.setBaseamtbalance(0f);
                }
                try {
                    vo.setIncamount(Float.valueOf(map.get("incamount")));
                } catch (Exception e) {
                    vo.setIncamount(0f);
                }
                try {
                    vo.setIncamt(Float.valueOf(map.get("incamt")));
                } catch (Exception e) {
                    vo.setIncamt(0f);
                }
                try {
                    vo.setDeductamt(Float.valueOf(map.get("deductamt")));
                } catch (Exception e) {
                    vo.setDeductamt(0f);
                }
                try {
                    vo.setChargeamt(Float.valueOf(map.get("chargeamt")));
                } catch (Exception e) {
                    vo.setChargeamt(0f);
                }

                try {
                    vo.setIspertain(Integer.valueOf(map.get("ispertain")));//0为主卡
                } catch (Exception e) {
                    vo.setIspertain(1);
                }

                vo.setMbrGuId(map.get("MbrGuId"));

                try {
                    vo.setGenGuIdDate(DateUtil.parseDate(map.get("GenGuIdDate"), DateUtil.DATETIME_PATTERN));
                } catch (Exception e) {
                    vo.setGenGuIdDate(DateUtil.getCurrDate());
                }
                mcVOs.add(vo);
            }
            return mcVOs;
        }
        return null;
    }

    public static List<MbrCardTypeVO> parseMbrCardTypeVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));

        log.info("xml charset:" + charset);
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<MbrCardTypeVO> mtVOs = parseMbrCardTypeVOs(list);
        return mtVOs;
    }

    private static List<MbrCardTypeVO> parseMbrCardTypeVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<MbrCardTypeVO> mtVOs = new ArrayList<MbrCardTypeVO>();
            for (Map<String, String> map : list) {
                MbrCardTypeVO vo = new MbrCardTypeVO();
                vo.setCode(map.get("Code"));
                vo.setName(map.get("Name"));
                try {
                    vo.setChargeAmt(Float.valueOf(map.get("ChargeAmt")));
                } catch (Exception e) {
                    vo.setChargeAmt(0f);
                }
                try {
                    vo.setSalePrice(Float.valueOf(map.get("SalePrice")));
                } catch (Exception e) {
                    vo.setSalePrice(0f);
                }
                vo.setRemark(map.get("Remark"));//***********************
                mtVOs.add(vo);
            }
            return mtVOs;
        }
        return null;
    }

    public static List<MemberTypeVO> parseMemberTypeVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));

        log.info("xml charset:" + charset);
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<MemberTypeVO> mtVOs = parseMemberTypeVOs(list);
        return mtVOs;
    }

    private static List<MemberTypeVO> parseMemberTypeVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<MemberTypeVO> mtVOs = new ArrayList<MemberTypeVO>();
            for (Map<String, String> map : list) {
                MemberTypeVO vo = new MemberTypeVO();
                vo.setCode(map.get("Code"));
                vo.setName(map.get("CName"));
                vo.setRemark(map.get("remark"));//***********************
                mtVOs.add(vo);
            }
            return mtVOs;
        }
        return null;
    }

    public static List<MemberTradeVO> parseMemberTradeVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<MemberTradeVO> mtVOs = parseMemberTradeVOs(list);
        return mtVOs;
    }

    private static List<MemberTradeVO> parseMemberTradeVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<MemberTradeVO> mtVOs = new ArrayList<MemberTradeVO>();
            for (Map<String, String> map : list) {
                MemberTradeVO vo = new MemberTradeVO();

                vo.setProfileId(map.get("ProfileId"));
                vo.setCreateDate(DateUtil.parseDatetime(map.get("CreateDate")));
                vo.setOutlet(map.get("Outlet"));
                try {
                    vo.setAmount(Float.valueOf(map.get("Amount")));
                } catch (Exception ex) {
                    vo.setAmount(0f);
                }
                vo.setRemark(map.get("Remark"));
                mtVOs.add(vo);
            }
            return mtVOs;
        }
        return null;
    }

    /**
     * 解析酒店数据
     *
     * @param xml
     * @param charset
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static List<HotelVO> parseHotelVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseHotelVOs(list);
    }

    private static List<HotelVO> parseHotelVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<HotelVO> hotelVOs = new ArrayList<HotelVO>();
            for (Map<String, String> map : list) {
                HotelVO hotelVO = new HotelVO();

                hotelVO.setHotelCode(map.get("HotelCode"));
                hotelVO.setcName(map.get("CName"));
                hotelVO.setAddress(map.get("Address1"));
                hotelVO.setTel(map.get("Tel"));
                hotelVO.setEmail(map.get("Email"));
                hotelVO.setArea(map.get("Area"));
                hotelVO.setProvince(map.get("Province"));
                hotelVO.setFeature(map.get("Feature"));
                hotelVO.setZipCode(map.get("ZipCode"));
                hotelVO.setCertificate(map.get("Certificate"));
                hotelVO.setPriceDesc(map.get("PriceDesc"));
                hotelVO.setRooms(map.get("Rooms"));
                hotelVO.setAnnouncement(map.get("Announcement"));
                hotelVO.setFacility(map.get("Facility"));
                hotelVO.setAvailbleService(map.get("AvailbleService"));
                hotelVOs.add(hotelVO);
            }
            return hotelVOs;
        }
        return null;
    }

    public static List<RoomInfoVO> parseRoomInfoVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseRoomInfoVOs(list);
    }

    private static List<RoomInfoVO> parseRoomInfoVOs(
            List<Map<String, String>> list) {
        if (list != null) {
            List<RoomInfoVO> roomInfos = new ArrayList<RoomInfoVO>();

            for (Map<String, String> map : list) {
                RoomInfoVO roomInfo = new RoomInfoVO();
                roomInfo.setOrderItemCode(map.get("OrderItemCode"));
                roomInfo.setOrderItemCName(map.get("OrderItemCName"));
                roomInfo.setcDescribe(map.get("CDescribe"));
                roomInfo.setImageid2(map.get("ImageId2"));
                roomInfo.setPriceName(map.get("PriceName"));
                roomInfo.setAveprice(map.get("Aveprice"));
                roomInfo.setBasePrice(map.get("BasePrice"));
                roomInfo.setAvebreakfast(map.get("Avebreakfast"));
                roomInfo.setPaymethod(map.get("Paymethod"));
                roomInfo.setMode(map.get("Mode"));
                roomInfo.setRatecode(map.get("Ratecode"));
                roomInfo.setPricesystemid(map.get("Pricesystemid"));
                roomInfo.setSalepromotionid(map.get("SalePromotionId"));
                roomInfo.setHotelImageLoadUrl(map.get("HotelImageLoadUrl"));
                roomInfo.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
                roomInfo.setImageNameList(map.get("ImageNameList"));
                roomInfo.setServiceList(map.get("ServiceList"));
                roomInfo.setLargessReturnType(map.get("LargessReturnType"));
                roomInfo.setLargessReturnValue(map.get("LargessReturnValue"));
                if (map.get("CanBooking") != null && map.get("CanBooking").equals("0")) {//1为可预订，0为不可预订
                    roomInfo.setCanBooking(false);
                } else {
                    roomInfo.setCanBooking(true);
                }
                roomInfo.setNoBookingReason(map.get("NoBookingReason"));
                try {
                    roomInfo.setMaxReturnMoneyPay(Float.valueOf(map.get("MaxReturnMoneyPay")));
                } catch (Exception ex) {
                    roomInfo.setMaxReturnMoneyPay(0f);
                }
                try {
                    roomInfo.setMaxScorePay(Float.valueOf(map.get("MaxScorePay")));
                } catch (Exception ex) {
                    roomInfo.setMaxScorePay(0f);
                }
                try {
                    roomInfo.setMaxCouponCountPay(Float.valueOf(map.get("MaxCouponCountPay")));
                } catch (Exception ex) {
                    roomInfo.setMaxCouponCountPay(0f);
                }
                roomInfos.add(roomInfo);
            }
            return roomInfos;
        }
        return null;
    }

    public static List<HotelBranchVO> parseHotelBranchVOs(String xml,
                                                          String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseHotelBranchVOs(list);
    }

    private static List<HotelBranchVO> parseHotelBranchVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<HotelBranchVO> branchs = new ArrayList<HotelBranchVO>();

            for (Map<String, String> map : list) {
                HotelBranchVO branch = new HotelBranchVO();

                branch.setCode(map.get("Code"));
                branch.setCname(map.get("CName"));
                branch.setAlias(map.get("Alias"));
                branch.setDetailCName(map.get("DetailCName"));
                branch.setEname(map.get("EName"));
                branch.setCity(map.get("City"));
                branch.setAddress(map.get("Address"));
                branch.setTel(map.get("Tel"));
                branch.setFeature(map.get("Feature"));
                branch.setPhotoID(map.get("PhotoID"));
                try {
                    branch.setMinPrice(Float.valueOf(map.get("MinPrice")));
                } catch (Exception e) {
                    branch.setMinPrice(0f);
                }
                branch.setHotelImageLoadUrl(map.get("HotelImageLoadUrl"));

                branchs.add(branch);
            }
            return branchs;
        }
        return null;
    }

    public static List<HotelServiceVO> parseHotelServiceVOs(String xml,
                                                            String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseHotelServiceVOs(list);
    }

    private static List<HotelServiceVO> parseHotelServiceVOs(
            List<Map<String, String>> list) {
        if (list != null) {
            List<HotelServiceVO> services = new ArrayList<HotelServiceVO>();

            for (Map<String, String> map : list) {
                HotelServiceVO service = new HotelServiceVO();

                service.setId(map.get("Id"));
                service.setTypeCode(map.get("TypeCode"));
                service.setServicesId(map.get("ServicesId"));
                service.setCname(map.get("CName"));
                service.setEname(map.get("EName"));
                service.setCdesc(map.get("CDesc"));
                service.setEdesc(map.get("EDesc"));
                service.setPictureId(map.get("PictureId"));
                service.setPictureUrl(map.get("PictureUrl"));
                service.setUnit(map.get("Unit"));

                try {
                    service.setIncludedQty(Integer.valueOf(map.get("IncludedQty")));
                } catch (Exception e) {
                    service.setIncludedQty(0);
                }

                try {
                    service.setIncludedPrice(Float.valueOf(map.get("IncludedPrice")));
                } catch (Exception e) {
                    service.setIncludedPrice(0f);
                }

                try {
                    service.setAvailableQty(Integer.valueOf(map.get("AvailableQty")));
                } catch (Exception e) {
                    service.setAvailableQty(0);
                }
                try {
                    service.setAvailablePrice(Float.valueOf(map.get("AvailablePrice")));
                } catch (Exception e) {
                    service.setAvailablePrice(0f);
                }
                services.add(service);
            }
            return services;
        }
        return null;
    }

    public static List<RoomPriceVO> parseRoomPriceVOs(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseRoomPriceVOs(list);
    }

    private static List<RoomPriceVO> parseRoomPriceVOs(
            List<Map<String, String>> list) {
        if (list != null) {
            List<RoomPriceVO> roomPriceVOs = new ArrayList<RoomPriceVO>();

            for (Map<String, String> map : list) {
                RoomPriceVO roomPrice = new RoomPriceVO();
                roomPrice.setPriceDate(map.get("PriceDate"));
                roomPrice.setPriceName(map.get("PriceName"));
                roomPrice.setOrderItemCode(map.get("orderItemCode"));
                roomPrice.setItemCname(map.get("ItemCname"));
                roomPrice.setPrice(map.get("Price"));
                roomPrice.setBreakfast(map.get("breakfast"));
                roomPrice.setServicerate(map.get("servicerate"));
                roomPrice.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
                roomPrice.setCancelNoticeCdesc(map.get("CancelNoticeCdesc"));
                roomPrice.setWeeknum(map.get("weeknum"));
                try {
                    roomPrice.setMaxReturnMoneyPay(Float.valueOf(map.get("MaxReturnMoneyPay")));
                } catch (Exception ex) {
                    roomPrice.setMaxReturnMoneyPay(0f);
                }
                try {
                    roomPrice.setMaxScorePay(Float.valueOf(map.get("MaxScorePay")));
                } catch (Exception ex) {
                    roomPrice.setMaxScorePay(0f);
                }
                try {
                    roomPrice.setMaxCouponCountPay(Float.valueOf(map.get("MaxCouponCountPay")));
                } catch (Exception ex) {
                    roomPrice.setMaxCouponCountPay(0f);
                }
                roomPriceVOs.add(roomPrice);
            }
            return roomPriceVOs;
        }
        return null;
    }

    public static String[] parseSendCouponRs(String xml,
                                             String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null && list.size() > 0) {
            Map<String, String> map = list.get(0);
            String rsCode = map.get("rtncode");
            String rsInfo = map.get("rtninfo");
            return new String[]{rsCode, rsInfo};
        }
        return null;
    }

    public static String[] parseUseCouponRs(String xml,
                                            String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null && list.size() > 0) {
            Map<String, String> map = list.get(0);
            String rsCode = map.get("rtncode");
            String rsInfo = map.get("rtninfo");
            return new String[]{rsCode, rsInfo};
        }
        return null;
    }


    public static List<MemberCouponVO> parseMemberCouponVOs(String xml,
                                                            String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseMemberCouponVOs(list);
    }

    private static List<MemberCouponVO> parseMemberCouponVOs(
            List<Map<String, String>> list) {
        if (list != null) {
            List<MemberCouponVO> memberCouponVOs = new ArrayList<MemberCouponVO>();

            for (Map<String, String> map : list) {
                MemberCouponVO memberCoupon = new MemberCouponVO();
                memberCoupon.setProfileid(map.get("profileid"));
                memberCoupon.setCode(map.get("Code"));
                memberCoupon.setTicketType(map.get("TicketType"));
                memberCoupon.setTicketTypeCname(map.get("TicketTypeCname"));
                memberCoupon.setMobile(map.get("Mobile"));
                memberCoupon.setUseflag(map.get("useflag"));
                memberCoupon.setUsedate(map.get("usedate"));
                memberCoupon.setUsebsnsdate(map.get("usebsnsdate"));
                memberCoupon.setUsedor(map.get("usedor"));
                memberCoupon.setUseresortid(map.get("useresortid"));
                memberCoupon.setSendflag(map.get("Sendflag"));
                memberCoupon.setSenddate(map.get("Senddate"));
                memberCoupon.setSendor(map.get("sendor"));
                try {
                    memberCoupon.setAmount(Float.valueOf(map.get("amount")));
                } catch (Exception e) {
                    memberCoupon.setAmount(0f);
                }
                memberCoupon.setLimitdate(DateUtil.parseDatetime(map.get("limitdate")));
                memberCoupon.setPolicy(map.get("Policy"));
                memberCoupon.setRefid(map.get("refid"));
                memberCoupon.setResortid(map.get("resortid"));
                memberCoupon.setRemark(map.get("remark"));
                memberCoupon.setModifieddate(DateUtil.parseDatetime(map.get("modifieddate")));
                memberCoupon.setModificator(map.get("modificator"));
                memberCoupon.setCreatedate(DateUtil.parseDatetime(map.get("createdate")));
                memberCoupon.setCreator(map.get("creator"));
                memberCoupon.setSeqid(map.get("seqid"));
                try {
                    memberCoupon.setChargeamtmodel(Integer.valueOf(map.get("chargeamtmodel")));
                } catch (Exception e) {
                }
                try {
                    memberCoupon.setChargeamt(Float.valueOf(map.get("Chargeamt")));
                } catch (Exception e) {
                    memberCoupon.setChargeamt(0f);
                }
                memberCoupon.setItemcode(map.get("itemcode"));
                memberCoupon.setNotice(map.get("Notice"));
                memberCouponVOs.add(memberCoupon);
            }
            return memberCouponVOs;
        }
        return null;
    }

    public static List<CategoryCodeVO> parseCategoryCodeVOs(String xml,
                                                            String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseCategoryCodeVOs(list);
    }

    private static List<CategoryCodeVO> parseCategoryCodeVOs(
            List<Map<String, String>> list) {
        if (list != null) {
            List<CategoryCodeVO> categoryCodeVOs = new ArrayList<CategoryCodeVO>();

            for (Map<String, String> map : list) {
                CategoryCodeVO categoryCode = new CategoryCodeVO();
                categoryCode.setCname(map.get("CName"));
                categoryCode.setCode(map.get("Code"));
                categoryCode.setExtraValue(map.get("remark"));
                categoryCodeVOs.add(categoryCode);
            }
            return categoryCodeVOs;
        }
        return null;
    }

    public static List<ExchangeGiftVO> parseExchangeGiftVOs(String xml,
                                                            String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseExchangeGiftVOs(list);
    }

    private static List<ExchangeGiftVO> parseExchangeGiftVOs(
            List<Map<String, String>> list) {
        if (list != null) {
            List<ExchangeGiftVO> giftVOs = new ArrayList<ExchangeGiftVO>();

            for (Map<String, String> map : list) {
                ExchangeGiftVO giftVO = new ExchangeGiftVO();
                giftVO.setItemId(map.get("ItemId"));
                giftVO.setItemCode(map.get("ItemCode"));
                giftVO.setItemCName(map.get("ItemCName"));
                giftVO.setItemEName(map.get("ItemEName"));
                try {
                    giftVO.setScore(Float.valueOf(map.get("Score")));
                } catch (Exception e) {
                    giftVO.setScore(0f);
                }
                giftVO.setPictureID(map.get("PictureID"));
                giftVO.setPictureUrl(map.get("PictureUrl"));
                try {
                    giftVO.setQuantity(Integer.valueOf(map.get("Quantity")));
                } catch (Exception e) {
                    giftVO.setQuantity(0);
                }
                giftVO.setOutletCode(map.get("OutletCode"));
                giftVO.setIsGoods(map.get("IsGoods"));
                giftVO.setIsApply(map.get("IsApply"));
                giftVO.setRemark(map.get("Remark"));

                giftVO.setBeginDate(DateUtil.parseDatetime(map.get("BeginDate")));
                giftVO.setEndDate(DateUtil.parseDatetime(map.get("EndDate")));
                giftVO.setGetMode(map.get("GetMode"));
                giftVOs.add(giftVO);
            }
            return giftVOs;
        }
        return null;
    }

    public static ExchangeGiftVO parseExchangeGiftVO(String xml, String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<ExchangeGiftVO> exchangeGiftVOs = parseExchangeGiftVOs(list);

        if (exchangeGiftVOs != null && exchangeGiftVOs.size() > 0) {
            return exchangeGiftVOs.get(0);
        }
        return null;
    }

    public static List<ChargeMoneyVO> parseChargeMoneyVO(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        List<ChargeMoneyVO> chargeMoneys = new ArrayList<>();
        if (list != null) {
            for (Map<String, String> map : list) {
                ChargeMoneyVO chargeMoneyVO = new ChargeMoneyVO();
                chargeMoneyVO.setsAmount(map.get("SAmount"));
                chargeMoneyVO.setDescript(map.get("Descript"));
                chargeMoneys.add(chargeMoneyVO);
            }
        }
        return chargeMoneys;
    }

    public static List<HotelCityVO> parseHotelCityVOs(String xml,
                                                      String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null) {
            List<HotelCityVO> hotelCitys = new ArrayList<HotelCityVO>();

            for (Map<String, String> map : list) {
                HotelCityVO hotelCityVO = new HotelCityVO();
                hotelCityVO.setCity(map.get("City"));
                hotelCitys.add(hotelCityVO);
            }
            return hotelCitys;
        }
        return null;
    }

    public static List<InterfaceListVO> parseInterfaceListVOs(String xml,
                                                              String charset) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null) {
            List<InterfaceListVO> vos = new ArrayList<InterfaceListVO>();

            for (Map<String, String> map : list) {
                InterfaceListVO vo = new InterfaceListVO();
                vo.setInterfaceName(map.get("InterfaceName"));
                vo.setInterfaceUrl(map.get("InterfaceUrl"));
                vo.setMobileAuditUrl(map.get("MobileAuditUrl"));
                try {
                    vo.setUseMemberInterface(Integer.valueOf(map.get("UseMemberInterface")));
                } catch (Exception e) {
//					vo.setUseMemberInterface(0);
                }
                vos.add(vo);
            }
            return vos;
        }
        return null;
    }

    public static List<FollowPolicyVO> parseFollowPolicyVOs(String xml,
                                                            String charset) throws UnsupportedEncodingException, DocumentException {
        System.out.println("parseFollowPolicyVOs-->xml==" + xml);
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null) {
            List<FollowPolicyVO> vos = new ArrayList<FollowPolicyVO>();

            for (Map<String, String> map : list) {
                FollowPolicyVO vo = new FollowPolicyVO();
                vo.setPolicyRule(map.get("PolicyRule"));
                vo.setPolicyRuleName(map.get("PolicyRuleName"));
                try {
                    vo.setReturnAmt(Float.valueOf(map.get("ReturnAmt")));
                } catch (Exception e) {
                    vo.setReturnAmt(0f);
                }
                vo.setRemark(map.get("Remark"));
                vo.setTicketTypeCode(map.get("TicketTypeCode"));
                try {
                    vo.setTicketCount(Integer.parseInt(map.get("TicketCount")));
                } catch (Exception e) {
                    vo.setTicketCount(1);
                }
                vo.setName(map.get("Name"));
                vos.add(vo);
            }
            return vos;
        }
        return null;
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static List<TicketCityVO> parseTicketCitysVOs(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null) {
            List<TicketCityVO> ticketCitys = new ArrayList<TicketCityVO>();

            for (Map<String, String> map : list) {
                TicketCityVO ticketCityVO = new TicketCityVO();
                ticketCityVO.setCity(map.get("City"));
                ticketCitys.add(ticketCityVO);
            }
            return ticketCitys;
        }
        return null;
    }

    public static List<TicketBranchVO> parseTicketBranchVOs(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketBranchVOs(list);
    }

    private static List<TicketBranchVO> parseTicketBranchVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketBranchVO> branchs = new ArrayList<TicketBranchVO>();

            for (Map<String, String> map : list) {
                TicketBranchVO branch = new TicketBranchVO();

                branch.setCode(map.get("Code"));
                branch.setCname(map.get("CName"));
                branch.setAlias(map.get("Alias"));
                branch.setDetailCName(map.get("DetailCName"));
                branch.setEname(map.get("EName"));
                branch.setCity(map.get("City"));
                branch.setAddress(map.get("Address"));
                branch.setTel(map.get("Tel"));
                branch.setFeature(map.get("Feature"));
                branch.setPhotoID(map.get("PhotoID"));
                try {
                    branch.setMinPrice(Float.valueOf(map.get("MinPrice")));
                } catch (Exception e) {
                    branch.setMinPrice(0f);
                }
                branch.setHotelImageLoadUrl(map.get("HotelImageLoadUrl"));

                branchs.add(branch);
            }
            return branchs;
        }
        return null;
    }

    public static List<TicketInfoVO> parseTicketInfoVOs(String xml, String charset)
            throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketInfoVOs(list);
    }

    private static List<TicketInfoVO> parseTicketInfoVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketInfoVO> roomInfos = new ArrayList<TicketInfoVO>();

            for (Map<String, String> map : list) {
                TicketInfoVO roomInfo = new TicketInfoVO();
                roomInfo.setOrderItemCode(map.get("OrderItemCode"));
                roomInfo.setOrderItemCName(map.get("OrderItemCName"));
                roomInfo.setcDescribe(map.get("CDescribe"));
                roomInfo.setImageid2(map.get("ImageId2"));
                roomInfo.setPriceName(map.get("PriceName"));
                roomInfo.setAveprice(map.get("Aveprice"));
                roomInfo.setAvebreakfast(map.get("Avebreakfast"));
                roomInfo.setPaymethod(map.get("Paymethod"));
                roomInfo.setMode(map.get("Mode"));
                roomInfo.setRatecode(map.get("Ratecode"));
                roomInfo.setPricesystemid(map.get("Pricesystemid"));
                roomInfo.setSalepromotionid(map.get("SalePromotionId"));
                roomInfo.setHotelImageLoadUrl(map.get("HotelImageLoadUrl"));
                roomInfo.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
                roomInfo.setImageNameList(map.get("ImageNameList"));
                roomInfo.setLargessReturnType(map.get("LargessReturnType"));
                roomInfo.setLargessReturnValue(map.get("LargessReturnValue"));
                if (map.get("CanBooking") != null && map.get("CanBooking").equals("0")) {//1为可预订，0为不可预订
                    roomInfo.setCanBooking(false);
                } else {
                    roomInfo.setCanBooking(true);
                }
                roomInfo.setNoBookingReason(map.get("NoBookingReason"));

                if (map.get("IsWqCombine") != null && map.get("IsWqCombine").equals("0")) {
                    roomInfo.setIsWqCombine(false);
                } else {
                    roomInfo.setIsWqCombine(true);
                }
                try {
                    roomInfo.setMaxReturnMoneyPay(Float.valueOf(map.get("MaxReturnMoneyPay")));
                } catch (Exception ex) {
                    roomInfo.setMaxReturnMoneyPay(0f);
                }
                try {
                    roomInfo.setMaxScorePay(Float.valueOf(map.get("MaxScorePay")));
                } catch (Exception ex) {
                    roomInfo.setMaxScorePay(0f);
                }
                try {
                    roomInfo.setMaxCouponCountPay(Float.valueOf(map.get("MaxCouponCountPay")));
                } catch (Exception ex) {
                    roomInfo.setMaxCouponCountPay(0f);
                }
                roomInfo.setAvaQty(map.get("AvaQty"));
                roomInfo.setIsScenic(map.get("IsScenic"));
                roomInfos.add(roomInfo);
            }
            return roomInfos;
        }
        return null;
    }

    public static List<TicketServiceVO> parseTicketServiceVOs(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketServiceVOs(list);
    }

    private static List<TicketServiceVO> parseTicketServiceVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketServiceVO> services = new ArrayList<TicketServiceVO>();

            for (Map<String, String> map : list) {
                TicketServiceVO service = new TicketServiceVO();

                service.setId(map.get("Id"));
                service.setTypeCode(map.get("TypeCode"));
                service.setServicesId(map.get("ServicesId"));
                service.setCname(map.get("CName"));
                service.setEname(map.get("EName"));
                service.setCdesc(map.get("CDesc"));
                service.setEdesc(map.get("EDesc"));
                service.setPictureId(map.get("PictureId"));
                service.setPictureUrl(map.get("PictureUrl"));
                service.setUnit(map.get("Unit"));

                try {
                    service.setIncludedQty(Integer.valueOf(map.get("IncludedQty")));
                } catch (Exception e) {
                    service.setIncludedQty(0);
                }

                try {
                    service.setIncludedPrice(Float.valueOf(map.get("IncludedPrice")));
                } catch (Exception e) {
                    service.setIncludedPrice(0f);
                }

                try {
                    service.setAvailableQty(Integer.valueOf(map.get("AvailableQty")));
                } catch (Exception e) {
                    service.setAvailableQty(0);
                }
                try {
                    service.setAvailablePrice(Float.valueOf(map.get("AvailablePrice")));
                } catch (Exception e) {
                    service.setAvailablePrice(0f);
                }
                services.add(service);
            }
            return services;
        }
        return null;
    }

    public static List<TicketPriceVO> parseTicketPriceVOs(String xml, String charset)
            throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketPriceVOs(list);
    }

    private static List<TicketPriceVO> parseTicketPriceVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketPriceVO> roomPriceVOs = new ArrayList<TicketPriceVO>();

            for (Map<String, String> map : list) {
                TicketPriceVO ticketPrice = new TicketPriceVO();
                ticketPrice.setPriceDate(map.get("PriceDate"));
                ticketPrice.setPriceName(map.get("PriceName"));
                ticketPrice.setOrderItemCode(map.get("orderItemCode"));
                ticketPrice.setItemCname(map.get("ItemCname"));
                ticketPrice.setPrice(map.get("Price"));
                ticketPrice.setBreakfast(map.get("breakfast"));
                ticketPrice.setServicerate(map.get("servicerate"));
                ticketPrice.setBookingNoticeCdesc(map.get("BookingNoticeCdesc"));
                ticketPrice.setCancelNoticeCdesc(map.get("CancelNoticeCdesc"));
                ticketPrice.setWeeknum(map.get("weeknum"));
                try {
                    ticketPrice.setMaxReturnMoneyPay(Float.valueOf(map.get("MaxReturnMoneyPay")));
                } catch (Exception ex) {
                    ticketPrice.setMaxReturnMoneyPay(0f);
                }
                try {
                    ticketPrice.setMaxScorePay(Float.valueOf(map.get("MaxScorePay")));
                } catch (Exception ex) {
                    ticketPrice.setMaxScorePay(0f);
                }
                try {
                    ticketPrice.setMaxCouponCountPay(Float.valueOf(map.get("MaxCouponCountPay")));
                } catch (Exception ex) {
                    ticketPrice.setMaxCouponCountPay(0f);
                }
                /**********套餐**************/
                ticketPrice.setRoomCategory(map.get("RoomCategory"));//分类，客房或门票
                try {
                    ticketPrice.setPricePercent(Float.valueOf(map.get("PricePercent")));//比例
                } catch (Exception ex) {
                    ticketPrice.setPricePercent(0f);
                }
                try {
                    ticketPrice.setCount(Integer.valueOf(map.get("Count")));//数量
                } catch (Exception ex) {
                    ticketPrice.setCount(0);
                }
                /**********套餐**************/


                roomPriceVOs.add(ticketPrice);
            }
            return roomPriceVOs;
        }
        return null;
    }

    public static List<TicketOrderVO> parseTicketOrderVOs(String xml, String charset)
            throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("orderMain");
        List<TicketOrderVO> ticketOrderVOs = parseTicketOrderVOs(list);
        return ticketOrderVOs;
    }

    private static List<TicketOrderVO> parseTicketOrderVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketOrderVO> orders = new ArrayList<TicketOrderVO>();
            for (Map<String, String> map : list) {
                TicketOrderVO vo = new TicketOrderVO();
                vo.setOrderNo(map.get("OrderNo"));
                vo.setMbrCardNo(map.get("MbrCardNo"));
                vo.setGuestType(map.get("GuestType"));
                vo.setSource(map.get("Source"));
                vo.setPayMethodType(map.get("PayMethodType"));
                vo.setPaidRefId(map.get("PaidRefId"));
                vo.setConfirmTypeName(map.get("ConfirmTypeName"));
                vo.setTotalAmount(Float.valueOf((map.get("TotalAmount"))));
                vo.setPaidAmount(Float.valueOf(map.get("PaidAmount")));
                vo.setCreateDate(map.get("CreateDate"));
                vo.setContactName(map.get("ContactName"));
                vo.setContactMobile(map.get("ContactMobile"));
                vo.setContactTel(map.get("ContactTel"));
                vo.setContactEmail(map.get("ContactEmail"));
                vo.setHotelCheckStatus(map.get("HotelCheckStatus"));
                vo.setHotelRevertMsg(map.get("HotelRevertMsg"));
                vo.setHotelRevertName(map.get("HotelRevertName"));
                vo.setHotelRevertDate(map.get("HotelRevertDate"));
                vo.setProfileId(map.get("ProfileId"));
                vo.setResortId(map.get("ResortId"));
                vo.setOrderType(map.get("OrderType"));
                vo.setConfirmationID(map.get("ConfirmationID"));
                orders.add(vo);
            }
            return orders;
        }
        return null;
    }

    public static List<TicketPriceDateVO> parseTicketPriceDateVOs(String xml, String charset)
            throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketPriceDateVOs(list);
    }

    private static List<TicketPriceDateVO> parseTicketPriceDateVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketPriceDateVO> ticketPriceDateVOs = new ArrayList<TicketPriceDateVO>();
            for (Map<String, String> map : list) {
                TicketPriceDateVO ticketPrice = new TicketPriceDateVO();
                if (map.get("pricedate") != null) {
                    ticketPrice.setPriceDate(map.get("pricedate").split(" ")[0]);
                }
                try {
                    ticketPrice.setPrice(Float.valueOf(map.get("price")));
                } catch (Exception e) {
                    ticketPrice.setPrice(0f);
                }
                ticketPriceDateVOs.add(ticketPrice);
            }
            return ticketPriceDateVOs;
        }
        return null;
    }

    public static List<TicketAccessoryVO> parseTicketAccessoryVOs(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketAccessoryVOs(list);
    }

    private static List<TicketAccessoryVO> parseTicketAccessoryVOs(List<Map<String, String>> list) {
        if (list != null) {
            List<TicketAccessoryVO> ticketAccessoryVOs = new ArrayList<TicketAccessoryVO>();

            for (Map<String, String> map : list) {
                TicketAccessoryVO accessoryVo = new TicketAccessoryVO();
                accessoryVo.setOrderItemCode(map.get("OrderItemCode"));//在下一步预订时需要传递回来
                accessoryVo.setOrderItemCName(map.get("OrderItemCName"));//用于预订时显示
                accessoryVo.setServiceType(map.get("ServiceType"));//产品类型
                try {
                    accessoryVo.setPrice(Float.valueOf(map.get("Price")));//单价
                } catch (Exception e) {
                    accessoryVo.setPrice(0f);
                }
                accessoryVo.setRatecode(map.get("Ratecode"));//价格体系代码
                accessoryVo.setPricesystemid(map.get("Pricesystemid"));//明细价格id
                accessoryVo.setSalePromotionId(map.get("SalePromotionId"));//优惠活动id
                try {
                    accessoryVo.setQty(Integer.valueOf(map.get("Qty")));//可预订数
                } catch (Exception e) {
                    accessoryVo.setPrice(0f);
                }
                ticketAccessoryVOs.add(accessoryVo);
            }
            return ticketAccessoryVOs;
        }
        return null;
    }

    public static List<TicketOrderVO> parseTicketOrderVO(String xml, String charset) throws Exception {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseTicketOrderVO(list);
    }

    private static List<TicketOrderVO> parseTicketOrderVO(List<Map<String, String>> list) {
        List<TicketOrderVO> vos = new ArrayList<>();
        if (list != null) {
            for (Map<String, String> map : list) {
                TicketOrderVO vo = new TicketOrderVO();
                vo.setOrderNo(map.get("SerialId"));
                try {
                    vo.setCheckedcount(Integer.valueOf(map.get("checkedcount")));
                } catch (Exception e) {
                    vo.setCheckedcount(0);
                }
                vo.setVlink(map.get("v_link"));
                vos.add(vo);
            }
        }
        return vos;
    }

    public static PWBTicketResult parsePWBTicketResult(String xml, String charset) throws Exception {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("PWBResponse");
        List<Map<String, String>> sublist = dom4jHelper.getListElements("subOrder");
        List<Map<String, String>> orderlist = dom4jHelper.getListElements("order");
        return parsePWBTicketResult(list, sublist, orderlist);
    }

    private static PWBTicketResult parsePWBTicketResult(List<Map<String, String>> list, List<Map<String, String>> sublist, List<Map<String, String>> orderlist) {
        if (list != null && list.size() > 0) {
            PWBTicketResult vo = new PWBTicketResult();
            Map<String, String> map = list.get(0);
            vo.setTransactionName(map.get("transactionName"));
            vo.setCode(map.get("code"));
            vo.setDescription(map.get("description"));
            vo.setImg(map.get("img"));
            if (sublist != null && sublist.size() > 0) {
                try {
                    map = sublist.get(0);
                    vo.setAlreadyCheckNum(Integer.valueOf(map.get("alreadyCheckNum")));
                } catch (Exception e) {
                    vo.setAlreadyCheckNum(-1);
                }
            }
            if (orderlist != null && orderlist.size() > 0) {
                map = orderlist.get(0);
                vo.setAssistCheckNo(map.get("assistCheckNo"));
            }
            return vo;
        }
        return null;
    }

    //////////////////////////////////////////////////////餐饮/////////////////////////////////////////////////////////////////////
    public static List<Dishes> parseDishesList(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseDishesList(list);
    }

    private static List<Dishes> parseDishesList(List<Map<String, String>> list) {
        if (list != null) {
            List<Dishes> dishesList = new ArrayList<Dishes>();

            for (Map<String, String> map : list) {
                Dishes dishes = new Dishes();
                dishes.setDishNo(map.get("DishNo"));
                dishes.setDishName(map.get("DishName"));
                dishes.setUnit(map.get("Unit"));
                try {
                    dishes.setPrice(Float.valueOf(map.get("Price")));
                } catch (Exception e) {
                    dishes.setPrice(0f);
                }
                dishes.setRemark(map.get("Remark"));
                dishes.setDishClass(map.get("DishClass"));
                dishes.setDishType(map.get("DishType"));
                dishes.setRestaurantId(map.get("RefeNo"));
                dishes.setDishno1(map.get("dishno1"));
                dishes.setShuffleNo(map.get("shuffleno"));
                dishesList.add(dishes);
            }
            return dishesList;
        }
        return null;
    }

    public static List<Shuffle> parseShuffleList(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseShuffleList(list);
    }

    private static List<Shuffle> parseShuffleList(List<Map<String, String>> list) {
        if (list != null) {
            List<Shuffle> shuffleList = new ArrayList<Shuffle>();

            for (Map<String, String> map : list) {
                Shuffle shuffle = new Shuffle();
                shuffle.setShuffleNo(map.get("ShuffleNo"));
                shuffle.setShuffleName(map.get("ShuffleName"));
                shuffle.setRemark(map.get("Remark"));
                shuffle.setStartTime(map.get("StartTime"));
                shuffle.setEndTime(map.get("EndTime"));
                shuffleList.add(shuffle);
            }
            return shuffleList;
        }
        return null;
    }

    public static List<DishesCategory> parseDishesCategoryList(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseDishesCategoryList(list);
    }

    private static List<DishesCategory> parseDishesCategoryList(List<Map<String, String>> list) {
        if (list != null) {
            List<DishesCategory> dishesCategoryList = new ArrayList<DishesCategory>();
            for (Map<String, String> map : list) {
                DishesCategory dishesCategory = new DishesCategory();
                dishesCategory.setDishNo(map.get("DishNo"));
                dishesCategory.setDishName(map.get("DishName"));
                dishesCategory.setRemark(map.get("Remark"));
                dishesCategoryList.add(dishesCategory);
            }
            return dishesCategoryList;
        }
        return null;
    }

    public static List<MealTab> parseMealTabList(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseMealTabList(list);
    }

    private static List<MealTab> parseMealTabList(List<Map<String, String>> list) {
        if (list != null) {
            List<MealTab> mealTabList = new ArrayList<MealTab>();

            for (Map<String, String> map : list) {
                MealTab mealTab = new MealTab();
                mealTab.setTabNo(map.get("TabNo"));
                mealTab.setRestaurantId(map.get("RefeNo"));
                mealTab.setTabName(map.get("TabName"));
                try {
                    mealTab.setStatus(Integer.valueOf(map.get("Status")));
                } catch (Exception e) {
                    mealTab.setStatus(0);
                }

                try {
                    mealTab.setSeats(Integer.valueOf(map.get("Seats")));
                } catch (Exception e) {
                    mealTab.setSeats(0);
                }
                mealTab.setRemark(map.get("Remark"));
                mealTabList.add(mealTab);
            }
            return mealTabList;
        }
        return null;
    }

    public static List<Restaurant> parseRestaurantList(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseRestaurantList(list);
    }

    private static List<Restaurant> parseRestaurantList(List<Map<String, String>> list) {
        if (list != null) {
            List<Restaurant> restaurantList = new ArrayList<Restaurant>();

            for (Map<String, String> map : list) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRefeNo(map.get("RefeNo"));
                restaurant.setName(map.get("RefeName"));
                restaurant.setTel(map.get("Tel"));
                restaurant.setRemark(map.get("Remark"));
                restaurantList.add(restaurant);
            }
            return restaurantList;
        }
        return null;
    }

    public static List<MealBranch> parseMealBranchList(String xml, String charset)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseMealBranchList(list);
    }

    private static List<MealBranch> parseMealBranchList(List<Map<String, String>> list) {
        if (list != null) {
            List<MealBranch> mealBranchList = new ArrayList<MealBranch>();

            for (Map<String, String> map : list) {
                MealBranch mealBranch = new MealBranch();
                mealBranch.setHotelCode(map.get("HotelCode"));


                mealBranchList.add(mealBranch);
            }
            return mealBranchList;
        }
        return null;
    }

    /**
     * 将xml内容解析为订单列表
     *
     * @param xml
     * @return
     * @throws UnsupportedEncodingException
     */
    public static List<MealOrder> parseMealOrder(String xml, String charset) throws DocumentException, UnsupportedEncodingException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);

        List<Map<String, String>> list = dom4jHelper.getListElements("Row");//Row
        List<MealOrder> mealOrderList = parseMealOrder(list);
        return mealOrderList;
    }

    private static List<MealOrder> parseMealOrder(List<Map<String, String>> list) {
        if (list != null) {
            List<MealOrder> orders = new ArrayList<MealOrder>();
            for (Map<String, String> map : list) {
                MealOrder vo = new MealOrder();
                vo.setConfirmationID(map.get("confirmationID"));
                if (map.get("Status").equals("未到")) {//未到
                    vo.setStatus(MealOrderStatus.NOARRIVE);
                } else if (map.get("Status").equals("已到")) {//已到
                    vo.setStatus(MealOrderStatus.ARRIVE);
                } else if (map.get("Status").equals("取消")) {//取消
                    vo.setStatus(MealOrderStatus.CANCELED);
                } else if (map.get("Status").equals("到期未到")) {//到期未到
                    vo.setStatus(MealOrderStatus.EXPIRED);
                }
                vo.setRemark(map.get("Remark"));
                vo.setCreateDate(DateUtil.parseDate(map.get("dt_Orderdate")));
                orders.add(vo);
            }
            return orders;
        }
        return null;
    }

    public static List<DishesAction> parseDishesActionList(String xml, String charset, Hotel hotel)
            throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseDishesActionList(list, hotel);
    }

    private static List<DishesAction> parseDishesActionList(List<Map<String, String>> list, Hotel hotel) {
        if (!CollectionUtils.isEmpty(list)) {
            List<DishesAction> dishesActionList = Lists.newArrayList();
            for (Map<String, String> map : list) {
                DishesAction dishesAction = new DishesAction();
                dishesAction.setCompanyId(hotel.getCompanyId());
                dishesAction.setHotelCode(hotel.getCode());
                dishesAction.setDishNo(map.get("Dishno"));
                dishesAction.setName(map.get("ActionName"));
                dishesAction.setActionNo(map.get("ActionNo"));
                dishesAction.setAddPrice(Float.valueOf(map.get("Addprice")));
                dishesActionList.add(dishesAction);
            }
            return dishesActionList;
        }
        return null;
    }

    public static List<Dishes> parseDishesSuiteList(String xml, String charset, Restaurant restaurant) throws UnsupportedEncodingException, DocumentException {
        InputStream ins = new ByteArrayInputStream(xml.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        return parseDishesSuiteList(list, restaurant);
    }

    private static List<Dishes> parseDishesSuiteList(List<Map<String, String>> list, Restaurant restaurant) {
        if (!CollectionUtils.isEmpty(list)) {
            String companyId = restaurant.getCompanyId();
            String hotelCode = restaurant.getHotelCode();
            String restaurantId = restaurant.getId();
            Map<String, Dishes> dishesMap = Maps.newHashMap();
            Map<String, Map<Integer, List<SuiteItem>>> itemMap = Maps.newHashMap();

            for (Map<String, String> map : list) {
                String dishNo = map.get("SuiteNo");
                String dishName = map.get("SuiteName");
                String unit = map.get("SuiteUnit");
                Float price = Float.valueOf(map.get("SuitePrice"));

                Dishes dishes = dishesMap.get(dishNo);
                Map<Integer, List<SuiteItem>> suiteItemMap = itemMap.get(dishNo);

                if (null == dishes) {
                    dishes = new Dishes();
                    dishes.setDishNo(dishNo);
                    dishes.setDishName(dishName);
                    dishes.setUnit(unit);
                    dishes.setCompanyId(companyId);
                    dishes.setHotelCode(hotelCode);
                    dishes.setRestaurantId(restaurantId);
                    dishes.setIsSuite(1);
                    dishes.setPrice(price);
                    suiteItemMap = Maps.newHashMap();
                }

                Integer grade = Integer.valueOf(map.get("Grade"));
                List<SuiteItem> suiteItems = suiteItemMap.get(grade);
                if (CollectionUtils.isEmpty(suiteItems)) {
                    suiteItems = Lists.newArrayList();
                }

                SuiteItem item = new SuiteItem();
                item.setSuiteNo(dishNo);
                item.setSuiteName(dishName);
                item.setSuiteUnit(unit);
                item.setGrade(grade);
                item.setIsAuto(Integer.valueOf(map.get("IsAuto")));
                item.setDishNo(map.get("DishNo"));
                item.setDishName(map.get("DishName"));
                item.setQuan(Float.valueOf(map.get("Quan")));
                item.setUnit(map.get("Unit"));
                item.setPrice(Float.valueOf(map.get("Price")));
                item.setAmount(Float.valueOf(map.get("Amount")));
                item.setRemark(map.get("Remark"));

                suiteItems.add(item);
                suiteItemMap.put(grade, suiteItems);
                itemMap.put(dishNo, suiteItemMap);
                dishesMap.put(dishNo, dishes);
            }
            List<Dishes> dishesList = Lists.newArrayList();
            for (Dishes dishes : dishesMap.values()) {
                String dishNo = dishes.getDishNo();

                List<List<SuiteItem>> lists = Lists.newArrayList();
                Map<Integer, List<SuiteItem>> suiteItemMap = itemMap.get(dishNo);
                for (List<SuiteItem> items : suiteItemMap.values()) {
                    lists.add(items);
                }
                JSONDataUtil jacksonConverter = JSONConvertFactory.getJacksonConverter();
                String json = jacksonConverter.jsonfromObject(lists);
                dishes.setSuiteData(json);
                dishesList.add(dishes);
            }
            return dishesList;
        }
        return null;
    }

    public static List<WaiterVO> parseWaiterList(String html, String charset) throws Exception {
        InputStream ins = new ByteArrayInputStream(html.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null && !list.isEmpty()) {
            List<WaiterVO> mealTabList = new ArrayList<WaiterVO>();
            String pwd = null;
            for (Map<String, String> map : list) {
                WaiterVO vo = new WaiterVO();
                pwd = map.get("Pwd");
                if (StringUtils.isNotBlank(pwd)) {
                    vo.setPwd(MD5Util.MD5(pwd));
                }
                vo.setUserName(map.get("UserName"));
                vo.setUserNo(map.get("UserNo"));
                vo.setStatus(map.get("Status"));
                vo.setiCCard(map.get("ICCard"));
                mealTabList.add(vo);
            }
            return mealTabList;
        }
        return null;
    }

    public static CyReservationResult parseOrder(String html, String charset) throws Exception {
        InputStream ins = new ByteArrayInputStream(html.getBytes(charset));
        Dom4jHelper dom4jHelper = null;
        try {
            dom4jHelper = new Dom4jHelper(ins, charset);
        } catch (DocumentException e) {
            throw new ApiException(html);
        }

        try {
            CyReservationResult result = new CyReservationResult();
            Map<String, String> map = dom4jHelper.getAllElements("Row");
            if (!CollectionUtils.isEmpty(map)) {
                result.setBillno(map.get("Billno"));
                result.setConfirmationID(map.get("confirmationID"));
                result.setHotelCode(map.get("HotelCode"));
                result.setErrorMsg(map.get("HotelCode"));
            }
            return result;
        } catch (Exception e) {
            String message = dom4jHelper.getElementValue("Message");
            throw new ApiException(message);
        }
    }

    public static List<DishesUnit> parseDishesUnit(String html, String charset) throws Exception {
        InputStream ins = new ByteArrayInputStream(html.getBytes(charset));
        Dom4jHelper dom4jHelper = new Dom4jHelper(ins, charset);
        List<Map<String, String>> list = dom4jHelper.getListElements("Row");
        if (list != null && !list.isEmpty()) {
            List<DishesUnit> mealTabList = Lists.newArrayList();

            int size = list.size();
            for (int i = 0; i < size; i++) {
                Map<String, String> map = list.get(i);
                DishesUnit unit = new DishesUnit();
                unit.setId(i + 1);
                unit.setPrice(Float.valueOf(map.get("Price")));
                unit.setUnit(map.get("Unit"));
                mealTabList.add(unit);
            }
            return mealTabList;
        }
        return null;
    }
}
