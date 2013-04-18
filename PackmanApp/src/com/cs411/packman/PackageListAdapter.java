package com.cs411.packman;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.packman.R;

class PackageListAdapter extends BaseAdapter {

    Context context;
    JSONArray data;
    private static LayoutInflater inflater = null;

    public PackageListAdapter(Context context, JSONArray data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
			return data.getJSONObject(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.simplerow, null);
        TextView text = (TextView) vi.findViewById(R.id.title);
        TextView description = (TextView) vi.findViewById(R.id.description);
        ImageView icon = (ImageView) vi.findViewById(R.id.icon);
        try {
        	JSONObject obj = (JSONObject) getItem(position);
			text.setText(obj.get("pkgid") + "");
			description.setText(obj.get("desc") + "");
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("UPS", R.drawable.icon_ups);
			map.put("FedEx", R.drawable.icon_fedex);
			map.put("USPS", R.drawable.icon_usps);
			
			icon.setBackgroundResource(map.get(obj.get("carrier")));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return vi;
    }
}