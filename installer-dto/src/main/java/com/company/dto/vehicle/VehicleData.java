package com.company.dto.vehicle;

import javax.persistence.Transient;

import com.company.api.fw.DictCodes;
import com.company.api.fw.TransformField;

/**
 * 
 * @author kouwl
 *
 */
public class VehicleData {

    //value
    @TransformField(groupCode = DictCodes.VEHICLE)
    private String value;
    
    @Transient
    private String valueDisp;

    //text
    private Integer text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getText() {
        return text;
    }

    public void setText(Integer text) {
        this.text = text;
    }

    public String getValueDisp() {
        return valueDisp;
    }

    public void setValueDisp(String valueDisp) {
        this.valueDisp = valueDisp;
    }

}