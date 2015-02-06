package com.dianping.rotate.admin.dto;

import com.dianping.rotate.territory.dto.TerritoryDto;
import lombok.Data;

/**
 * Created by yangjie on 2/6/15.
 */
@Data
public class TerritoryExtendDTO extends TerritoryDto {
    private Integer territoryChiefId;
    private String territoryChiefName;
    private Integer notOnlineMutGroupCountLimit;
    private Integer notOnlineSingleGroupCountLimit;
}