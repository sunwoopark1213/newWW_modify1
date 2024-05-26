package com.example.newww;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CalendarAdapter extends BaseAdapter {

    private Context mContext;
    private int[] weatherCodes;

    public CalendarAdapter(Context context, int[] weatherCodes) {
        this.mContext = context;
        this.weatherCodes = weatherCodes;
    }

    @Override
    public int getCount() {
        return weatherCodes.length;
    }

    @Override
    public Object getItem(int position) {
        return weatherCodes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        int weatherCode = weatherCodes[position];
        switch (weatherCode) {
            case 0: // Assuming 0 represents sunny
                imageView.setImageResource(R.drawable.sunny);
                break;
            case 1: // Assuming 1 represents cloudy
                imageView.setImageResource(R.drawable.cloudy);
                break;
            case 2: // Assuming 2 represents rainy
                imageView.setImageResource(R.drawable.rainy);
                break;
            case 3: // Assuming 3 represents snowy
                imageView.setImageResource(R.drawable.snowy);
                break;
            default:
                imageView.setImageResource(R.drawable.ic_launcher_background);
                break;
        }
        return imageView;
    }
}
