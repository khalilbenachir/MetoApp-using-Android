package com.example.meteoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeteoListModel extends ArrayAdapter<MeteoItem> {

    private List<MeteoItem> listItems;
    private int resource;
    public static Map<String,Integer> images=new HashMap<>();


    static {
        images.put("Clear",R.drawable.sun);
        images.put("Clouds",R.drawable.sun);
        images.put("Rain",R.drawable.sun);
    }

    public MeteoListModel(@NonNull Context context,int resource,List<MeteoItem> data){
        super(context,resource,data);
        Log.i("Size",String.valueOf(data.size()));
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        Log.i("MyLog","............."+position);
        View listItem=convertView;
        if(listItem==null)
            listItem= LayoutInflater.from(getContext()).
                    inflate(resource,parent,false);
        ImageView imageView=listItem.findViewById(R.id.imageView);
        TextView textViewMax=listItem.findViewById(R.id.textViewMax2);
        TextView textViewMin=listItem.findViewById(R.id.textViewMin2);
        TextView textViewPression=listItem.findViewById(R.id.textViewPression2);
        TextView textViewHumidity=listItem.findViewById(R.id.textViewHumidity2);


        String key=listItems.get(position).image;

        if(key!=null)imageView.setImageResource(images.get(key));

        textViewMax.setText(String.valueOf(listItems.get(position).tempMax)+" ' *C");
        textViewMin.setText(String.valueOf(listItems.get(position).tempMin)+" ' *C");
        textViewPression.setText(String.valueOf(listItems.get(position).pression)+" ' hPa");
        textViewHumidity.setText(String.valueOf(listItems.get(position).humidite)+" ' %");





        return listItem;
    }
}
