package com.soap.franxi.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.soap.franxi.IO.DownloadImagesTask;
import com.soap.franxi.demo_service_soap.R;
import com.soap.franxi.model.Manager;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Admin on 27/12/2016.
 */

public class listManagerAdapter extends ArrayAdapter<Manager> {

    Activity context;
    int resource;
    List<Manager> objects;
    public listManagerAdapter(Activity context, int resource, List<Manager> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        View row = layoutInflater.inflate(this.resource,null);

        ImageView imgAvatar = (ImageView) row.findViewById(R.id.imgAvatar);
        TextView txtFullName = (TextView) row.findViewById(R.id.txtFullName);
        TextView txtID = (TextView) row.findViewById(R.id.txtID);
        Manager manager = this.objects.get(position);
        String imageUrl = "http://192.168.1.21/androidservice/Content/img/avatar/"+manager.getAvatar();
        DownloadImagesTask task = new DownloadImagesTask(imgAvatar);
        task.execute(imageUrl);
        final Integer manager_id = manager.getID();
        txtFullName.setText(manager.getFullName());

        txtFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(),manager_id.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        return row;
    }
}
