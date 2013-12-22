package com.dandelion.memberandroid.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.model.NotificationMessagePO;
import com.squareup.picasso.Picasso;

public class NotificationListAdapter extends BaseAdapter{
	private Context context;
	private List<NotificationMessagePO> notificationData = new ArrayList<NotificationMessagePO>();
	
	
	public NotificationListAdapter(Context context) {
		this.context = context;
		//TODO 
		notificationData = fakeData();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if (convertView == null) {
	    	convertView = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
	      holder = new ViewHolder();
	      holder.image = (ImageView) convertView.findViewById(R.id.image_notification);
	      holder.text = (TextView) convertView.findViewById(R.id.text_notification);
	      convertView.setTag(holder);
	    } else {
	      holder = (ViewHolder) convertView.getTag();
	    }

	    // Get the image URL for the current position.
	    NotificationMessagePO notificationMessage = (NotificationMessagePO) getItem(position);
	    String url = notificationMessage.getAvatorUrl();
	    holder.text.setText(notificationMessage.getContext());
	    

	    // Trigger the download of the URL asynchronously into the image view.
	    Picasso.with(context)
	        .load(url)
	        .placeholder(R.drawable.placeholder)
	        .error(R.drawable.error)
	        .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
	        .centerInside()
	        .into(holder.image);
		return convertView;
	}

	@Override
	public int getCount() {
		return notificationData.size();
	}


	@Override
	public Object getItem(int position) {
		return notificationData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	


	
	
	
	public List<NotificationMessagePO> fakeData() {
		List<NotificationMessagePO> fakeNotificationData = new ArrayList<NotificationMessagePO>();
		NotificationMessagePO notificationMessagePO1 = new NotificationMessagePO();
		NotificationMessagePO notificationMessagePO2 = new NotificationMessagePO();
		notificationMessagePO1.setAvatorUrl("http://img.hb.aicdn.com/c46ff78ec3a4013cdba090c690407a2ac106aacf13209-4Zazxz_fw658");
		notificationMessagePO1.setContext("Shaman 想要加入成為你的會員");
		fakeNotificationData.add(notificationMessagePO1);
		notificationMessagePO2.setAvatorUrl("http://img.hb.aicdn.com/a284ebacc7f6fc0fd2a9573d2d22e30146b9195513db5-xeRWvB_fw658");
		notificationMessagePO2.setContext("Daniel 發佈了更新");
		fakeNotificationData.add(notificationMessagePO2);
		return fakeNotificationData;
	}
	
	static class ViewHolder {
		ImageView image;
		TextView text;
	}
}
