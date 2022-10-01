package com.example.sivakami.attendancetracker;

import android.app.Application;

/**
 * Created by SIVAKAMI on 26/0/17.
 */

public class GlobalClass extends Application
{
    private  String globalEmpId;
    private Boolean isCheckedIn;
    private String checkInTime;

    public String getGlobalEmpId()
    {
        return globalEmpId;
    }
    public Boolean getIsCheckedIn()
    {
        return isCheckedIn;
    }
    public String getCheckInTime(){ return checkInTime;}
    public void setGlobalEmpId(String e)
    {
        globalEmpId=e;
    }
    public void setIsCheckedIn(Boolean state)
    {
        isCheckedIn=state;
    }
    public void setCheckInTime(String time){checkInTime=time;}

}
