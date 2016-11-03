package com.styx.gta.homeui.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by amal.george on 25-10-2016.
 */
@IgnoreExtraProperties
public class ThermoStat {
    private String thermostatID;
    private String thermostatName;
    private String thermostatValue;

    public ThermoStat() {

    }

    public ThermoStat(String thermostatName, String thermostatValue) {
        this.thermostatName = thermostatName;
        this.thermostatValue = thermostatValue;
    }

    public void setThermostatID(String thermostatID) {
        this.thermostatID = thermostatID;
    }

    public void setThermostatName(String thermostatName) {
        this.thermostatName = thermostatName;
    }

    public String getThermostatValue() {
        return thermostatValue;
    }

    public String getThermostatID() {
        return thermostatID;
    }

    public String getThermostatName() {
        return thermostatName;
    }
}
