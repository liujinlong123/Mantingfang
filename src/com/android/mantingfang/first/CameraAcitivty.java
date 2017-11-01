package com.android.mantingfang.first;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.android.mantingfanggsc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraAcitivty extends Activity {

	private ImageView imgBack;
	private ImageView imgReverse;
	private ImageView imgPai;
	private ImageView imgLight;
	private ImageView imgPics;

	public static final String TAG = "CameraSimple";
	private Camera mCamera;
	private CameraPreview mPreview;
	private FrameLayout mCameralayout;
	private int mCameraId = CameraInfo.CAMERA_FACING_BACK;

	//private static String path = Environment.getExternalStorageDirectory() + "/data/pang";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		if (!checkCameraHardware(this)) {
			Toast.makeText(CameraAcitivty.this, "相机不支持", Toast.LENGTH_SHORT).show();
		} else {
			openCamera();
			initViews();
			setCameraDisplayOrientation(this, mCameraId, mCamera);
		}
	}

	private void initViews() {
		imgBack = (ImageView) findViewById(R.id.camera_view_back);
		imgReverse = (ImageView) findViewById(R.id.camera_view_reverse);
		imgPai = (ImageView) findViewById(R.id.camera_view_pai);
		imgLight = (ImageView) findViewById(R.id.camera_view_light);
		imgPics = (ImageView) findViewById(R.id.camera_view_pics);

		imgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		imgReverse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 镜头反转
				switchCamera();
			}
		});

		imgPai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCamera.autoFocus(mAutoFocusCallback);
			}
		});

		imgLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		imgPics.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	// 判断相机是否支持
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	// 获取相机实例
	public Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(mCameraId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	// 开始预览相机
	@SuppressLint("ClickableViewAccessibility")
	public void openCamera() {
		if (mCamera == null) {
			mCamera = getCameraInstance();
			mPreview = new CameraPreview(CameraAcitivty.this, mCamera);
			mPreview.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					mCamera.autoFocus(null);
					return false;
				}
			});
			mCameralayout = (FrameLayout) findViewById(R.id.camera_view);
			mCameralayout.addView(mPreview);
			mCamera.startPreview();
		}
	}

	// 释放相机
	public void releaseCamera() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	// 拍照回调
	private PictureCallback mPictureCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(final byte[] data, Camera camera) {
			File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

			new DateFormat();
			final String picturePath = pictureDir + File.separator
					+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString() + ".jpg";
			final int cameraid = mCameraId;
			new Thread(new Runnable() {
				@Override
				public void run() {
					File file = new File(picturePath);
					try {
						// 获取当前旋转角度，并旋转图片
						Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
						if (cameraid == CameraInfo.CAMERA_FACING_BACK) {
							bitmap = rotateBitmapByDegree(bitmap, 90);
						} else {
							bitmap = rotateBitmapByDegree(bitmap, -90);
						}
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
						bos.flush();
						bos.close();
						bitmap.recycle();
						
						Intent intent = new Intent(CameraAcitivty.this, PoemPic.class);
						intent.putExtra("path", picturePath);
						startActivity(intent);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

			mCamera.startPreview();
		}
	};

	// 旋转图片
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

	// 设置相机横竖屏
	public void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
		CameraInfo info = new CameraInfo();
		Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;
		} else {
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	// 切换前置和后置摄像头
	public void switchCamera() {
		CameraInfo cameraInfo = new CameraInfo();
		Camera.getCameraInfo(mCameraId, cameraInfo);
		if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
			mCameraId = CameraInfo.CAMERA_FACING_FRONT;
		} else {
			mCameraId = CameraInfo.CAMERA_FACING_BACK;
		}
		mCameralayout.removeView(mPreview);
		releaseCamera();
		openCamera();
		setCameraDisplayOrientation(CameraAcitivty.this, mCameraId, mCamera);
	}

	// 聚焦回调
	private AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				mCamera.takePicture(null, null, mPictureCallback);
			}
		}
	};
}
