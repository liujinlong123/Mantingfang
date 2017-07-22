package com.android.mantingfang.picture;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Picture extends Activity {

	private TextView btnFinish;
	
	private GridView gridView;
	private ImageAdapter imgAdapter;
	private List<String> imgs;

	private RelativeLayout bottomLy;

	private TextView dirName, dirCount;

	private File currentDir;
	private FilenameFilter filter;
	private int maxCount = 0;

	private List<FolderBean> folderBeans = new ArrayList<FolderBean>();

	private ProgressDialog progressDialog;

	private static final int DATA_LOADED = 0X110;

	private ImageDirPopWindow popupWindow;
	
	/**
	 * 选中图片的路径集合
	 */
	private ArrayList<String> setPath;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case DATA_LOADED:
				// 加载完成
				progressDialog.dismiss();

				// 绑定数据到view中去
				data2View();

				initPopupWindow();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_activity);

		initView();
		initDatas();
		initEvent();
	}

	protected void initPopupWindow() {
		popupWindow = new ImageDirPopWindow(Picture.this, folderBeans);

		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {

				lightOn();
			}
		});

		popupWindow
				.setOnDirSelectedListener(new ImageDirPopWindow.OnDirSelectedListener() {

					@Override
					public void onSelected(FolderBean folderBean) {

						currentDir = new File(folderBean.getDir());

						imgs = Arrays.asList(currentDir.list(filter));
						imgAdapter = new ImageAdapter(Picture.this, imgs,
								currentDir.getAbsolutePath());
						
						gridView.setAdapter(imgAdapter);
						
						dirCount.setText(imgs.size() + "");
						dirName.setText(folderBean.getName());
						
						popupWindow.dismiss();
					}
				});

	}

	protected void data2View() {

		if (currentDir == null) {
			Toast.makeText(this, "为扫描到任何图片", Toast.LENGTH_LONG).show();
			return;
		}
		// currentDir.list()所有图片的集合
		// 将数组转换为list

		imgs = Arrays.asList(currentDir.list(filter));

		if (imgs == null) {
			System.out.println("imgs = null !");
		}

		imgAdapter = new ImageAdapter(Picture.this, imgs,
				currentDir.getAbsolutePath());
		setPath = getIntent().getStringArrayListExtra("photos");
		
		gridView.setAdapter(imgAdapter);
		if (setPath != null && setPath.size() != 0) {
			imgAdapter.setSelectedImg(setPath);
		}

		dirCount.setText(maxCount + "");
		dirName.setText(currentDir.getName());
	}

	/**
	 * 利用ContentProvider扫描手机中的所有图片
	 */
	private void initDatas() {

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
			return;
		}
		//进度对话框
		progressDialog = ProgressDialog.show(this, null, "正在加载...");

		filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
						|| filename.endsWith(".png")) {
					return true;

				}
				return false;
			}
		};
		
		/**
		 * 扫描手机图片线程
		 */
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//指向手机中的所有存储图片
				Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

				ContentResolver contentResolver = Picture.this
						.getContentResolver();
				Cursor cursor = contentResolver.query(uri, null,
						MediaStore.Images.Media.MIME_TYPE + "= ? or "
								+ MediaStore.Images.Media.MIME_TYPE + " = ?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				// 存储父路径 防止重复扫描
				Set<String> dirPaths = new HashSet<String>();

				while (cursor.moveToNext()) {
					// 当前图片路径
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					
					File parentFile = new File(path).getParentFile();
					if (parentFile == null) {
						continue;
					}

					String dirPath = parentFile.getAbsolutePath();

					FolderBean folderBean = null;

					if (dirPaths.contains(dirPath)) {
						continue;
					} else {
						dirPaths.add(dirPath);
						folderBean = new FolderBean();
						folderBean.setDir(dirPath);
						folderBean.setFirstImgPath(path);

					}

					if (parentFile.list() == null) {
						continue;
					}

					// 父路径中文件的数量----图片数量
					int picSize = parentFile.list(filter).length;

					folderBean.setCount(picSize);
					folderBeans.add(folderBean);

					// 判断最大数量
					if (picSize > maxCount) {
						maxCount = picSize;
						currentDir = parentFile;
					}
				}

				cursor.close();

				// 扫描完成，释放临时变量的内存 (自动)
				// 通知handler 扫描图片完成
				handler.sendEmptyMessage(DATA_LOADED);

			}
		}.start();
	}

	/**
	 * ��Ӽ�����
	 */
	private void initEvent() {
		bottomLy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				popupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
				popupWindow.showAsDropDown(bottomLy, 0, 0);

				lightOff();
			}
		});
	}

	/**
	 * ��������������ı�͸����
	 */
	protected void lightOn() {

		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.alpha = 1.0f;
		this.getWindow().setAttributes(lp);
	}

	/**
	 * ��������䰵
	 */
	protected void lightOff() {

		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.alpha = 0.3f;
		this.getWindow().setAttributes(lp);

	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		btnFinish = (TextView)findViewById(R.id.finish_choose);
		gridView = (GridView) findViewById(R.id.id_gridView);
		bottomLy = (RelativeLayout) findViewById(R.id.id_button_ly);
		dirName = (TextView) findViewById(R.id.id_dir_name);
		dirCount = (TextView) findViewById(R.id.id_dir_count);
		
		btnFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				setPath = new ArrayList<>(imgAdapter.getSelectedImg());
				bundle.putStringArrayList("listPhoto", setPath);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
