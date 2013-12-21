package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dandelion.memberandroid.R;

public class NotificationListAdapter extends ArrayAdapter<String>{
	private Context context;
	public NotificationListAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.notification_item, null);
		return convertView;
	}
	


}
