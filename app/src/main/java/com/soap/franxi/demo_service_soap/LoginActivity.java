package com.soap.franxi.demo_service_soap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.soap.franxi.IO.DownloadImagesTask;
import com.soap.franxi.demo_service_soap.Configuration.Configuration;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Admin on 17/12/2016.
 */

public class LoginActivity extends Activity{

    ImageView imgLogo;
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    Animation animation;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        animation = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.zoom);

        addControls();
        btnLogin.setVisibility(View.INVISIBLE);
        edtPassword.setVisibility(View.INVISIBLE);
        edtUsername.setVisibility(View.INVISIBLE);
        imgLogo.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation animation2 = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.fadein);
                edtUsername.startAnimation(animation2);
                edtPassword.startAnimation(animation2);
                btnLogin.startAnimation(animation2);
                btnLogin.setVisibility(View.VISIBLE);
                edtPassword.setVisibility(View.VISIBLE);
                edtUsername.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        addEvents();
    }

    private void addControls() {
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang xử lý, vui lòng chờ!");
        progressDialog.setCanceledOnTouchOutside(false);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manager_Login();
            }
        });
    }

    private void Manager_Login() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        LoginTask task = new LoginTask();
        task.execute(username,password);
    }
    class LoginTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            try
            {
                boolean check=false;

                String user = params[0].toString();
                String pass = params[1].toString();
                SoapObject request = new SoapObject(Configuration.NAME_SPACE,Configuration.METHOD_LOGIN);
                request.addProperty(Configuration.PARAM_Username,user);
                request.addProperty(Configuration.PARAM_Password,pass);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE = new HttpTransportSE(Configuration.SERVER_URL);
                httpTransportSE.call(Configuration.SOAP_ACTION_LOGIN,envelope);

                SoapPrimitive data = (SoapPrimitive) envelope.getResponse();

                check=Boolean.parseBoolean(data.toString());

                return check;

            }catch (Exception ex)
            {
                Log.e("Loi",ex.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(result) {
                Intent i = new Intent(LoginActivity.this,ListManager_Activity.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
            progressDialog.hide();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            edtPassword.setText("");
            progressDialog.show();
        }
    }
}
