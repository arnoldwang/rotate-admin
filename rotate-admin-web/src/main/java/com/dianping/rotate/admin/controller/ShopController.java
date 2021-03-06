package com.dianping.rotate.admin.controller;

import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.combiz.service.CityService;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.core.api.service.RotateGroupUserService;
import com.dianping.rotate.shop.api.ApolloShopService;
import com.dianping.rotate.territory.api.TerritoryService;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dp.arts.client.SearchService;
import com.dp.arts.client.request.KeywordQuery;
import com.dp.arts.client.request.Request;
import com.dp.arts.client.request.TermQuery;
import com.dp.arts.client.response.Record;
import com.dp.arts.client.response.Response;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 3/5/15.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    @Qualifier("rotateSearchService")
    private SearchService searchService;

    @Autowired
    private TerritoryService territoryService;

    @Autowired
    private UserService userService;


    @Autowired
    CityService cityService;

    @Autowired
    ApolloShopService apolloShopService;

    @Autowired
    RotateGroupUserService rotateGroupUserService;

    private static final String[] fields = {
        "shopname",  "shopid", "bizrotateids", "bigcustomerbiz", "territoryids", "cityid", "bizusers"
    };

    private static String buildBizField(String bizId, String fieldId) {
        return bizId + ":" + fieldId;
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Object search(
            @RequestParam(value = "bizId") String bizId,
            @RequestParam(value = "pageIndex") Integer pageIndex,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "salesId", required = false) String salesId,
            @RequestParam(value = "shopId", required = false) String shopId,
            @RequestParam(value = "shopName", required = false) String shopName,
            @RequestParam(value = "rotateId", required = false) String rotateId
    ) {
        Request request = initRequest();

        request.setLimit((pageIndex - 1) * pageSize, pageIndex * pageSize);

        if (StringUtils.isNotEmpty(salesId)) {
            request.addQuery(new TermQuery("bizusers", buildBizField(bizId, salesId)));
        }

        if (StringUtils.isNotEmpty(shopId)) {
            request.addQuery(new TermQuery("shopid", shopId));
        }

        if (StringUtils.isNotEmpty(shopName)) {
            request.addQuery(new KeywordQuery("searchkeyword", shopName));
        }

        if (StringUtils.isNotEmpty(rotateId)) {
            request.addQuery(new TermQuery("bizrotateids", buildBizField(bizId, rotateId)));
        }

        Response response = searchService.search(request);

        return buildSearchResults(response, bizId);
    }


    @RequestMapping(value = "/setVIP", method = RequestMethod.POST)
    @ResponseBody
    public boolean markBigCustomer(@RequestBody Map o) {
        return setBigCustomer(o, 1);
    }

    @RequestMapping(value = "/cancelVIP", method = RequestMethod.POST)
    @ResponseBody
    public boolean unmarkBigCustomer(@RequestBody Map o) {
        return setBigCustomer(o, 0);
    }

    private boolean setBigCustomer(Map o, int type) {
        try {
            Integer bizId = (Integer) o.get("bizId");
            List<Map> items = (List<Map>) o.get("selectedItems");

            return apolloShopService.updateApolloShopTypeByRotateGroupID(Lists.transform(items, new Function<Map, Integer>() {
                @Override
                public Integer apply(Map input) {
                    return (Integer) input.get("rotateId");
                }
            }), bizId, type);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }


    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public boolean release(@RequestBody Map o) {
        Integer loginId = LoginUtils.getUserLoginId();
        List<Map> items;

        try {
            items = (List<Map>) o.get("selectedItems");
        } catch (Exception e) {
            throw new ApplicationException("请传入selectedItems");
        }

        com.dianping.rotate.smt.dto.Response<Boolean> ret;
        try {
            ret = rotateGroupUserService.batchReleaseToPublic(Lists.transform(items, new Function<Map, Integer>() {
                @Override
                public Integer apply(Map input) {
                    return (Integer) input.get("rotateId");
                }
            }), loginId);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }


        if (ret.isSuccess()) {
            return ret.getObj();
        } else {
            throw new ApplicationException(ret.getComment());
        }
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public boolean transfer(@RequestBody Map o) {
        Integer loginId = LoginUtils.getUserLoginId();
        Integer salesId;
        List<Map> items;
        try {
             salesId = (Integer) o.get("salesId");
             items = (List<Map>) o.get("selectedItems");
        } catch (Exception e) {
            throw new ApplicationException("请传入selectedItems和salesId");
        }

        com.dianping.rotate.smt.dto.Response<Boolean> ret;

        try {
            ret = rotateGroupUserService.batchTransfer(Lists.transform(items, new Function<Map, Integer>() {
                @Override
                public Integer apply(Map input) {
                    return (Integer) input.get("rotateId");
                }
            }), salesId, loginId);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        if (ret.isSuccess()) {
            return ret.getObj();
        } else {
            throw new ApplicationException(ret.getComment());
        }
    }


    private Object buildSearchResults(Response response, final String bizId) {
        if (!response.getStatus().equals(Response.OK)) {
            throw new ApplicationException(response.getErrorMessage());
        }

        final List<Record> records = response.getRecordList();

        Map<String, Object> ret = Maps.newHashMap();

        ret.put("total", response.getTotalHits());
        ret.put("items", Lists.transform(records, new Function<Record, Object>() {
            @Override
            public Object apply(Record record) {
                return new SearchRecordWrapper(record, bizId);
            }
        }));

        return ret;
    }

    private Request initRequest() {
        Request shopSearchRequest = new Request(Request.Platform.MAPI, "RotateCore");

        for(String field : fields) {
            shopSearchRequest.addOutputField(field); //需要输出的字段
        }

        return shopSearchRequest;
    }


    private class SearchRecordWrapper {
        private final Record record;
        private final String bizId;

        public SearchRecordWrapper(Record record, String bizId) {
            this.record = record;
            this.bizId = bizId;
        }

        public String getShopName() {
            return record.get("shopname");
        }

        public Integer getShopId() {
            return Integer.valueOf(record.get("shopid"));
        }

        public Integer getRotateId() {
            return Integer.valueOf(getValueFromRecordField(record.get("bizrotateids"), bizId));
        }

        public Boolean isBigCustomer() {
            String sBigCustomers = record.get("bigcustomerbiz");
            if (StringUtils.isEmpty(sBigCustomers)) {
                return false;
            }

            String[] bigCustomers = sBigCustomers.split("\\s");
            return Arrays.asList(bigCustomers).contains(bizId);
        }

        public String getTerritoryName() {
            Integer bizId = Integer.valueOf(this.bizId);
            String sTerritoryIds = record.get("territoryids");
            if (StringUtils.isEmpty(sTerritoryIds)) {
                return null;
            }

            String[] territoryIds = sTerritoryIds.split("\\s");

            List<TerritoryDto> territoryDtos = territoryService.batchQuery(Lists.transform(Arrays.asList(territoryIds), new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return Integer.valueOf(input);
                }
            }));

            for (TerritoryDto territoryDto: territoryDtos) {
                if (territoryDto.getBizId().equals(bizId)) {
                    return territoryDto.getTerritoryName();
                }
            }

            return null;
        }

        public String getSalesName() {
            Integer salesId = Integer.valueOf(getValueFromRecordField(record.get("bizusers"), bizId));
            if (salesId.equals(0)) {
                return null;
            }

            UserDto user = userService.queryUserByLoginID(salesId);

            if (user != null) {
                return user.getRealName();
            }

            return null;
        }

        public Integer getCityId() {
            return Integer.valueOf(record.get("cityid"));
        }

        private String getValueFromRecordField(String sFields, String bizId) {
            if (sFields == null) {
                return null;
            }

            String[] fields = sFields.split("\\s");
            for (String field: fields) {
                String[] pair = field.split(":");
                // 保险起见，加一个判断
                if (pair.length == 2) {
                    if (pair[0].equals(bizId)) {
                        return pair[1];
                    }
                }
            }
            return null;
        }
    }
}
