package com.example.login;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomList extends BaseAdapter {

	private final Activity context;
	private ArrayList<Item> data;

	public CustomList(Activity context, ArrayList<Item> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return  position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.list_single, parent, false);

			// save components to ViewHolder
			holder = new ViewHolder();
			holder.id = (TextView) row.findViewById(R.id.itemID);
			holder.username = (TextView) row.findViewById(R.id.itemUsername);
			holder.password = (TextView) row.findViewById(R.id.itemPassword);			
			row.setTag(holder);
		} else {
			// get components from ViewHolder
			holder = (ViewHolder) row.getTag();
		}

		Item item = data.get(position);
		holder.id.setText("ID : " + item.getId());	
		holder.username.setText("username : " + item.getUsername());
		holder.password.setText("password : " + item.getPassword());		
		return row;
	}

	static class ViewHolder {
		TextView id;
		TextView username;
		TextView password;
	}


}
