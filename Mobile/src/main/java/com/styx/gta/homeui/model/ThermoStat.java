package com.styx.gta.homeui.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class ThermoStat {
    private String thermostatID;
    private int thermostatValue;
    public ThermoStat(){

    }
    public ThermoStat(String thermostatID,int thermostatValue){
        this.thermostatID=thermostatID;
        this.thermostatValue=thermostatValue;
    }
    public void setThermostatID(String thermostatID) {
        this.thermostatID = thermostatID;
    }

    public int getThermostatValue() {
        return thermostatValue;
    }

    public String getThermostatID() {
        return thermostatID;
    }

    public void setThermostatValue(int thermostatValue) {
        this.thermostatValue = thermostatValue;
    }
}
