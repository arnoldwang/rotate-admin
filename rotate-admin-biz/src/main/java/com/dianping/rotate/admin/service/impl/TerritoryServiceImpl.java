package com.dianping.rotate.admin.service.impl;

import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.rotate.admin.dto.TerritoryExtendDTO;
import com.dianping.rotate.admin.service.TerritoryService;
import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import com.dianping.rotate.territory.api.TerritoryChiefService;
import com.dianping.rotate.territory.api.TerritoryRotateConfigService;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryRotateConfigDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2/6/15.
 */
@Service
public class TerritoryServiceImpl implements TerritoryService {
    @Autowired
    private TerritoryServiceAgent territoryServiceAgent;


    @Autowired
    private TerritoryChiefService rotateTerritoryChiefService;


    @Autowired
    private UserService userService;

    @Autowired
    private TerritoryRotateConfigService territoryRotateConfigService;

    @Override
    public List<TerritoryExtendDTO> queryAllLeafTerritoriesWithExtend(Integer id) {
        List<TerritoryDto> ts = territoryServiceAgent.queryAllLeafTerritories(id);

        if (CollectionUtils.isEmpty(ts)) {
            return Lists.newArrayList();
        }

        Collections.sort(ts, new Comparator<TerritoryDto>() {
            @Override
            public int compare(TerritoryDto o1, TerritoryDto o2) {
                return o1.getTerritoryName().compareTo(o2.getTerritoryName());
            }
        });

        List<Integer> tids = Lists.transform(ts, new Function<TerritoryDto, Integer>() {
            @Override
            public Integer apply(TerritoryDto input) {
                return input.getId();
            }
        });


        final Map<Integer, Integer> territoryIdToChiefIdMap = rotateTerritoryChiefService.getTerritoryToChiefMap(tids);

        final Map<Integer, TerritoryRotateConfigDto> terrtoryIdToConfigMap = territoryRotateConfigService.getTerritoryRotateConfigMap(tids);


        final Map<Integer, UserDto> userIdToUser = Maps.newHashMap();
        for (UserDto user: userService.queryUserByLoginIDs(Lists.newArrayList(territoryIdToChiefIdMap.values()))) {
            userIdToUser.put(user.getLoginId(), user);
        }

        for (UserDto user: userService.queryUserByLoginIDs(Lists.transform(ts, new Function<TerritoryDto, Integer>() {
            @Override
            public Integer apply(TerritoryDto input) {
                return input.getUpdateBy();
            }
        }))) {
            userIdToUser.put(user.getLoginId(), user);
        }

        return Lists.transform(ts, new Function<TerritoryDto, TerritoryExtendDTO>() {
            @Override
            public TerritoryExtendDTO apply(TerritoryDto input) {
                TerritoryExtendDTO output = new TerritoryExtendDTO();
                output.setId(input.getId());
                output.setBizId(input.getBizId());
                output.setTerritoryName(input.getTerritoryName());
                UserDto updateBy = userIdToUser.get(input.getUpdateBy());
                if (updateBy != null) {
                    output.setUpdateByName(updateBy.getRealName());
                }
                output.setUpdateTime(input.getUpdateTime());

                Integer chiefId = territoryIdToChiefIdMap.get(input.getId());
                if (chiefId != null) {
                    UserDto chief = userIdToUser.get(chiefId);
                    if (chief != null) {
                        output.setTerritoryChiefName(chief.getRealName());
                        output.setTerritoryChiefDepartmentName(chief.getDepartmentName());
                    }
                }

                TerritoryRotateConfigDto config = terrtoryIdToConfigMap.get(input.getId());

                if (config != null) {
                    output.setNotOnlineMutGroupCountLimit(config.getNotOnlineMutGroupCountLimit());
                    output.setNotOnlineSingleGroupCountLimit(config.getNotOnlineSingleGroupCountLimit());
                }

                return output;
            }
        });

    }

}
