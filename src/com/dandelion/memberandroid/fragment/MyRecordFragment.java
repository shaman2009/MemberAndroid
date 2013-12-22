package com.dandelion.memberandroid.fragment;

import java.util.UUID;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dandelion.memberandroid.R;
import com.dandelion.memberandroid.constant.QiNiuConstant;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.qiniu.auth.JSONObjectRet;
import com.qiniu.io.IO;
import com.qiniu.io.PutExtra;
import com.squareup.picasso.Picasso;

public class MyRecordFragment extends Fragment {

	private Button btnUpload;
	private TextView hint;
	private ImageView image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_merchant_my_record, container, false);
	}

	@Override
	public void onStart() {
		initWidget();
		btnUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, QiNiuConstant.PICK_PICTURE_RESUMABLE);
				return;
			}
		});
		super.onStart();
	}

	public void initWidget() {
		btnUpload = (Button) getActivity().findViewById(R.id.record_pic_upload);
		hint = (TextView) getActivity().findViewById(
				R.id.record_pic_upload_status);
		image = (ImageView) getActivity().findViewById(
				R.id.imageView_record_merchant_avator);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != SlidingActivity.RESULT_OK) return;

		if (requestCode == QiNiuConstant.PICK_PICTURE_RESUMABLE) {
			doUpload(data.getData());
			return;
		}		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	boolean uploading = false;
	/**
	 * 普通上傳文件
	 * 
	 * @param uri
	 */
	private void doUpload(Uri uri) {
		if (uploading) {
			hint.setText("上傳中，請稍後");
			return;
		}
		uploading = true;
		final String key = UUID.randomUUID().toString();
		// 上传文件名
		PutExtra extra = new PutExtra();
		extra.checkCrc = PutExtra.AUTO_CRC32;
		extra.params.put("x:arg", "value");
		hint.setText("上傳中");
		IO.putFile(getActivity(), QiNiuConstant.UP_TOKEN, key, uri, extra, new JSONObjectRet() {
			@Override
			public void onSuccess(JSONObject resp) {
				uploading = false;
				String hash;
				String value;
				try {
					hash = resp.getString("hash");
					value = resp.getString("x:arg");
				} catch (Exception ex) {
					hint.setText(ex.getMessage());
					return;
				}
				String redirect = "http://" + QiNiuConstant.DOWNLOAD_DOMAIN + "/" + key;
				hint.setText("上傳成功！ " + hash);
				Log.d("QINIU_UPLOAD", "redirect : " + redirect);
				downloadViaPicasso(getActivity(), redirect);
			}

			@Override
			public void onFailure(Exception ex) {
				uploading = false;
				hint.setText("錯誤: " + ex.getMessage());
			}
		});
	}

	public void downloadViaPicasso(Context context, String path) {
		Picasso.with(context).load(path).into(image);
	}
}
