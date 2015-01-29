package com.dianping.rotate.admin.dto;

/**
 * Created by yangjie on 1/27/15.
 */
public class TeamTerritoryDTO {
    private Integer teamId;
    private String teamName;
    private Integer territoryId;
    private String territoryName;
    private String territoryChiefName;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(Integer territoryId) {
        this.territoryId = territoryId;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public String getTerritoryChiefName() {
        return territoryChiefName;
    }

    public void setTerritoryChiefName(String territoryChiefName) {
        this.territoryChiefName = territoryChiefName;
    }
}
