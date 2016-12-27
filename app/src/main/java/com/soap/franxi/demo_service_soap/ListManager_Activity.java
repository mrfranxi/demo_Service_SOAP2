package com.soap.franxi.demo_service_soap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.soap.franxi.Adapter.listManagerAdapter;
import com.soap.franxi.demo_service_soap.Configuration.Configuration;
import com.soap.franxi.model.Manager;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

import java.util.ArrayList;

/**
 * Created by Admin on 17/12/2016.
 */

public class ListManager_Activity extends Activity {
    ListView lvManager;
    ProgressDialog progressDialog;
    ArrayList<Manager>list_manager;
    ArrayAdapter<Manager>ad_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manager);
        addControls();
        addOnLoad();
        addEvents();
    }

    private void addOnLoad() {
        getListManager();
    }

    private void getListManager() {
        ListManagerTask task = new ListManagerTask();
        task.execute();
    }

    private void addEvents() {
    }

    private void chat() {
        String id="";
    }

    class ListManagerTask extends AsyncTask<Void,Void,ArrayList<Manager>>
    {
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ad_manager.clear();
            progressDialog.dismiss();
        }

        @Override
        protected void onPostExecute(ArrayList<Manager> managers) {
            super.onPostExecute(managers);
            ad_manager.clear();
            ad_manager.addAll(managers);
            progressDialog.dismiss();
        }

        @Override
        protected ArrayList<Manager> doInBackground(Void... params) {
            ArrayList<Manager>list=new ArrayList<>();

            try{
                SoapObject request = new SoapObject(Configuration.NAME_SPACE,Configuration.METHOD_LIST_MANAGER);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransportSE = new HttpTransportSE(Configuration.SERVER_URL);
                httpTransportSE.call(Configuration.SOAP_ACTION_LIST_MANAGER,envelope);

                SoapObject data = (SoapObject) envelope.getResponse();

                for(int i=0;i<data.getPropertyCount();i++)
                {
                    SoapObject soapObject = (SoapObject) data.getProperty(i);
                    Manager manager = new Manager();
                    if(soapObject.hasProperty("ID"))
                    {
                        manager.setID(Integer.parseInt(soapObject.getPropertyAsString("ID")));
                    }
                    if(soapObject.hasProperty("Avatar"))
                    {
                        manager.setAvatar(soapObject.getPropertyAsString("Avatar"));
                    }
                    if(soapObject.hasProperty("fullName"))
                    {
                        manager.setFullName(soapObject.getPropertyAsString("fullName"));
                    }
                    list.add(manager);
                }
            }catch (Exception ex)
            {
                Log.e("Lỗi", ex.toString());
            }
            return list;
        }
    }
    private void addControls() {
        lvManager = (ListView) findViewById(R.id.lvManager);
        progressDialog = new ProgressDialog(ListManager_Activity.this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang tải danh sách, xin vui lòng chờ...");
        progressDialog.setCanceledOnTouchOutside(false);
        list_manager=new ArrayList<>();
        ad_manager = new listManagerAdapter(
                ListManager_Activity.this,
                R.layout.listview_manager,
                list_manager);
        lvManager.setAdapter(ad_manager);
    }
}
