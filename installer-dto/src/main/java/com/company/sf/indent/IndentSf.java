package com.company.sf.indent;

import java.util.Date;
import java.util.List;

import com.company.po.indent.Indent;

public class IndentSf extends Indent {

    private static final long serialVersionUID = 1L;

    private Date comitDateBegin;

    private Date comitDateEnd;

    private Date gotDateBegin;

    private Date gotDateEnd;
    
    private String isAPI;
    
    private String searchName1;
    
    private String searchAddr1;
    
    private String indentType;
    
    private String progress;
    
    private String managerName1;
    
    private String workerName1;
    
    private List<String> executeStatusList;
    
    public Date getComitDateBegin() {
        return comitDateBegin;
    }

    public void setComitDateBegin(Date comitDateBegin) {
        this.comitDateBegin = comitDateBegin;
    }

    public Date getComitDateEnd() {
        return comitDateEnd;
    }

    public void setComitDateEnd(Date comitDateEnd) {
        this.comitDateEnd = comitDateEnd;
    }

    public Date getGotDateBegin() {
        return gotDateBegin;
    }

    public void setGotDateBegin(Date gotDateBegin) {
        this.gotDateBegin = gotDateBegin;
    }

    public Date getGotDateEnd() {
        return gotDateEnd;
    }

    public void setGotDateEnd(Date gotDateEnd) {
        this.gotDateEnd = gotDateEnd;
    }

    public String getIsAPI() {
        return isAPI;
    }

    public void setIsAPI(String isAPI) {
        this.isAPI = isAPI;
    }

    public String getSearchName1() {
        return searchName1;
    }

    public void setSearchName1(String searchName1) {
        this.searchName1 = searchName1;
    }

    public String getSearchAddr1() {
        return searchAddr1;
    }

    public void setSearchAddr1(String searchAddr1) {
        this.searchAddr1 = searchAddr1;
    }

    public String getIndentType() {
        return indentType;
    }

    public void setIndentType(String indentType) {
        this.indentType = indentType;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getManagerName1() {
        return managerName1;
    }

    public void setManagerName1(String managerName1) {
        this.managerName1 = managerName1;
    }

    public String getWorkerName1() {
        return workerName1;
    }

    public void setWorkerName1(String workerName1) {
        this.workerName1 = workerName1;
    }

    public List<String> getExecuteStatusList() {
        return executeStatusList;
    }

    public void setExecuteStatusList(List<String> executeStatusList) {
        this.executeStatusList = executeStatusList;
    }

}
