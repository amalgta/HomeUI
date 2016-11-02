package com.styx.gta.homeui.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class ThermoStat {
    private String thermostatID;
    private String thermostatName;
    private int thermostatValue;
    public ThermoStat(){

    }
    public ThermoStat(String thermostatName,int thermostatValue){
        this.thermostatName=thermostatName;
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
    public String thermostatName() {
        return thermostatName;
    }

    public void setThermostatValue(int thermostatValue) {
        this.thermostatValue = thermostatValue;
    }

}
