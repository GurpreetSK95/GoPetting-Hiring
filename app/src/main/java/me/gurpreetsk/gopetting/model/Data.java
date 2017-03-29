package me.gurpreetsk.gopetting.model;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by gurpreet on 29/03/17.
 */

@SimpleSQLTable(table = "Data",
        provider = "DataProvider")
public class Data {

    @SimpleSQLColumn(value = "url", primary = true)
    private String url;
    @SimpleSQLColumn(value = "name")
    private String name;
    @SimpleSQLColumn(value = "startDate")
    private String startDate;
    @SimpleSQLColumn(value = "endDate")
    private String endDate;
    @SimpleSQLColumn(value = "iconUrl")
    private String iconUrl;
    @SimpleSQLColumn(value = "loginRequired")
    private String loginRequired;
    @SimpleSQLColumn(value = "objType")
    private String objType;


    public Data() {
    }

    public Data(String name, String startDate, String endDate, String url, String iconUrl, String loginRequired, String objType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.iconUrl = iconUrl;
        this.loginRequired = loginRequired;
        this.objType = objType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(String loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }
}
