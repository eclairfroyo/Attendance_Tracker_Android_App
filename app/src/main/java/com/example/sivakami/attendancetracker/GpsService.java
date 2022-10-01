package com.example.sivakami.attendancetracker;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SIVAKAMI on 26/0/17.
 */

public class GpsService extends Service
{
    private Double getlongitue;
    private Double getlatitude;
    private Double officeLongitude=12.934001;
    private Double officeLatitude=80.140516;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        class mythread extends Thread {

            public void run() {

                LocationManager mloc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // CALLED WHEN A NEW LOCATION IS FOUND BY THE LOCATION LISTENER.
                        getlongitue = location.getLongitude();
                        getlatitude = location.getLatitude();


                        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                        Boolean isEmployeeInOffice=globalVariable.getIsCheckedIn();
                        String empid=globalVariable.getGlobalEmpId();

                        // current date and time values.
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df;
                        df=new SimpleDateFormat("hh:mm:ss");
                        String time=df.format(c.getTime());

                        //NOTE: AN EMPLOYEE MAY LEAVE THE OFFICE IN A DAY MORE THAN ONCE FOR INSTANCES LIKE LUNCH.
                        // SO ATTENDANCE IS MARKED ONLY ON THE INSERTION OF 'FIRST CHECKIN' FOR A USER IN A DAY.

                        if(!isEmployeeInOffice)
                            if (getlatitude == officeLatitude && getlongitue == officeLongitude) {

                                //CASE1: EMPLOYEE WHO WASN'T IN OFFICE PREVIOUSLY HAS ENTERED OFFICE NOW.
                                // IF ATTENDANCE WASN'T MARKED INITIALLY FOR THAT PARTICULAR DAY IT
                                // HAS TO BE MARKED AND CHECK IN TIME HAS TO BE ENTERED.

                                globalVariable.setCheckInTime(time);
                                BackgroundCheckInTask backgroundCheckInTask=new BackgroundCheckInTask();
                                backgroundCheckInTask.execute(empid,time);

                            }
                        else
                        {
                            if(getlatitude!=officeLatitude&&getlongitue!=officeLongitude)
                            {
                                //CASE2:EMPLOYEE WHO WAS INITIALLY IN THE OFFICE HAS MOVED ELSEWHERE.
                                //IN THIS CASE CHECK OUT HAS TO BE INSERTED

                                String checkintime=globalVariable.getCheckInTime();
                                BackgroundCheckoutTask backgroundCheckoutTask=new BackgroundCheckoutTask();
                                backgroundCheckoutTask.execute(empid,checkintime,time);

                            }
                        }
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {

                    }

                    public void onProviderDisabled(String provider) {
                    }
                };



// Register the listener with the Location Manager to receive location updates

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mloc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener, Looper.getMainLooper());
            }


        }

        mythread mythreadobj=new mythread();
        mythreadobj.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
