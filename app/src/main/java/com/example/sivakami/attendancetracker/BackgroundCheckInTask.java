package com.example.sivakami.attendancetracker;

import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SIVAKAMI on 26/0/17.
 */

class BackgroundCheckInTask extends AsyncTask<String,Void,Void>
{
    @Override
    protected Void doInBackground(String[] params) {

        String serverURL="http://192.168.0.103/abc/ispresentandcheckin.php";
        String empid=params[0];

        String time=params[1];
        try
        {
            URL url=new URL(serverURL);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String dataToServer = URLEncoder.encode("empid","UTF-8")+"="+URLEncoder.encode(empid,"UTF-8")+"&"+
                    URLEncoder.encode("intime","UTF-8")+"="+URLEncoder.encode(time,"UTF-8");
            bufferedWriter.write(dataToServer);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        return null;
    }


}
