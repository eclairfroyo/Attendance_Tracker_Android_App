package com.example.sivakami.attendancetracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by SIVAKAMI on 26/0/17.
 */

public class BackgroundRegTask extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context ctx;

    BackgroundRegTask(Context ctx)
    {
       this.ctx=ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("information");
    }

    @Override
    protected String doInBackground(String[] params) {
        String serverURL="http://192.168.0.103/abc/validation.php";
        String empid=params[0];
        String  mobno=params[1];
        try
        {
            URL url=new URL(serverURL);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String dataToServer = URLEncoder.encode("empid","UTF-8")+"="+URLEncoder.encode(empid,"UTF-8")+"&"+
                                  URLEncoder.encode("mobno","UTF-8")+"="+URLEncoder.encode(mobno,"UTF-8");
            bufferedWriter.write(dataToServer);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            InputStream is=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String result="";
            String httpResult="";
            while ((httpResult=bufferedReader.readLine())!=null)
            {
                result=result+httpResult;
            }
            bufferedReader.close();
            is.close();
            httpURLConnection.disconnect();
            return result;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return "Try again";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "Try again";
        }

    }

    @Override
    protected void onPostExecute(String result)

    {
         if(result!=null) {
             if (result.toLowerCase().contains("welcome")) {
                 Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(ctx, BridgetoService.class);
                 ctx.startActivity(intent);
                 ((Activity) ctx).finish();

             }
             else
             {
                 Toast.makeText(ctx,"Connection failed. Try again !", Toast.LENGTH_SHORT).show();
             }
         }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();


            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
            };

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);
                }
            });

            handler.postDelayed(runnable, 2000);

        }

    }
}
