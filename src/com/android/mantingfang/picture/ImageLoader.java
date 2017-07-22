package com.android.mantingfang.picture;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * 图片加载类
 * @author MrKID
 *
 */
public class ImageLoader {

	private static ImageLoader instance;
	/**
	 * String 图片路径
	 * 图片缓存的核心对象
	 */
	private LruCache<String, Bitmap> lruCache;

	/**
	 * 线程池
	 * 默认线程数
	 */
	private ExecutorService threadPool;
	private static final int DEFAULT_THREAD_COUNT = 1;

	/**
	 * 记录线程的调度方式
	 */
	private Type type = Type.LIFO;

	/**
	 * taskQueue 任务列表
	 * 任务队列
	 */
	private LinkedList<Runnable> taskQueue;
	/**
	 * 后台轮询线程
	 */
	private Thread poolThread;
	private Handler poolThreadHandler;
	
	/**
	 * UI线程中的Handler
	 */
	private Handler UIHandler;
	
	private Semaphore semaphorePoolThreadHandler = new Semaphore(0);
	private Semaphore semaphoreThreadPool;

	/**
	 * 线程的调度方式
	 * @author MrKID
	 *
	 */
	public enum Type {
		FIFO, LIFO
	}

	/**
	 * 构造方法
	 * @param threadCount	线程数
	 * @param type			线程调度方式
	 */
	private ImageLoader(int threadCount, Type type) {
		init(threadCount, type);
	}

	/**
	 * 初始化
	 * 
	 * @param threadCount
	 * @param type
	 */
	private void init(int threadCount, Type type) {
		// 后台轮询线程
		poolThread = new Thread() {
			@Override
			public void run() {
				Looper.prepare();

				poolThreadHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// 到线程池去取出一个任务执行
						threadPool.execute(getTask());
						try {
							semaphoreThreadPool.acquire();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				};
				//poolThreadHandler��ʼ����ϣ��ͷ��ź���
				semaphorePoolThreadHandler.release();
				Looper.loop();
			}
		};

		poolThread.start();

		// 获取我们应用的最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheMemory = maxMemory / 8;

		lruCache = new LruCache<String, Bitmap>(cacheMemory) {
			protected int sizeOf(String key, Bitmap value) {
				// 测量每个bitmap所占据的内存(大小)
				return value.getRowBytes() * value.getHeight();
			}
		};

		// 创建线程池
		threadPool = Executors.newFixedThreadPool(threadCount);
		taskQueue = new LinkedList<Runnable>();
		this.type = type;
		
		semaphoreThreadPool = new Semaphore(threadCount);
	}

	/**
	 * 从任务队列取出一个任务
	 * 
	 * @return
	 */
	private Runnable getTask() {

		if (type == Type.FIFO) {
			return taskQueue.removeFirst();
		} else if (type == Type.LIFO) {
			return taskQueue.removeLast();
		}

		return null;
	}

	/**
	 * 获取类的实例
	 * 两层判断 提高速率
	 * @return
	 */
	public static ImageLoader getInstance() {
		if (instance == null) {
			synchronized (ImageLoader.class) {
				if (instance == null) {
					instance = new ImageLoader(DEFAULT_THREAD_COUNT, Type.LIFO);
				}
			}
		}
		return instance;
	}
	
	public static ImageLoader getInstance(int threadCount, Type type) {
		if (instance == null) {
			synchronized (ImageLoader.class) {
				if (instance == null) {
					instance = new ImageLoader(threadCount, type);
				}
			}
		}
		return instance;
	}

	/**
	 * 根据path为ImageView设置图片
	 * 
	 * @param path	路径(加载图片)
	 * @param imageView
	 */
	@SuppressLint("HandlerLeak")
	public void loadImage(final String path, final ImageView imageView) {
		imageView.setTag(path);

		if (UIHandler == null) {
			UIHandler = new Handler() {
				public void handleMessage(Message msg) {
					// 获取得到的图片，为imageView回调设置图片
					ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
					Bitmap bm = holder.bitmap;
					ImageView imageView = holder.imageView;
					String path = holder.path;

					if (imageView.getTag().toString().equals(path)) {
						imageView.setImageBitmap(bm);
					}
				};
			};
		}
		
		//根据path在缓存中获取bitmap
		Bitmap bm = this.getBitmapFromLruCache(path);

		if (bm != null) {
			reFrashBitmap(path, imageView, bm);
		} else {
			addTasks(new Runnable() {

				@Override
				public void run() {
					// 加载图片
					// 图片的压缩
					// 1.获得图片需要显示的大小
					ImageSize imageSize = getImageViewSize(imageView);
					// 2。压缩图片
					Bitmap bm = decodeSampleBitmapFromPath(imageSize.width,
							imageSize.height, path);
					// 3.把图片加入到缓存
					addBitmapToLruCache(path, bm);
					
					reFrashBitmap(path, imageView, bm);
					
					semaphoreThreadPool.release();
				}
				
			});
		}
	}
	
	private void reFrashBitmap(final String path,
			final ImageView imageView, Bitmap bm) {
		Message message = Message.obtain();
		ImgBeanHolder holder = new ImgBeanHolder();
		holder.bitmap = bm;
		holder.path = path;
		holder.imageView = imageView;

		message.obj = holder;
		UIHandler.sendMessage(message);
	}
/**
 * 将图片加入LruCache(缓存)
 * @param path
 * @param bm
 */
	protected void addBitmapToLruCache(String path, Bitmap bm) {
		
		// 判断缓存中是否有了图片
		if(getBitmapFromLruCache(path) == null) {
			if(bm != null) {
				lruCache.put(path, bm);
			}
		}
	}

	/**
	 * 根据图片需要显示的宽和高对图片进行压缩
	 * 
	 * @param width
	 * @param height
	 * @param path
	 * @return
	 */
	protected Bitmap decodeSampleBitmapFromPath(int width, int height,
			String path) {
		// 获取图片的宽和高，并不把图片加载到内存中
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;	//不把图片加载到内存中
		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = caculateInSampleSize(options, width, height);
		
		//使用获取到的InSampleSize再次解析图片
		options.inJustDecodeBounds = false;	//把图片加载到内存中
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		
		//压缩后的图片
		return bitmap;
	}

	/**
	 * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
	 * 
	 * @param options	实际宽高
	 * @param reqwidth	需求宽度
	 * @param reqheight 需求高度
	 * @return
	 */
	private int caculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {

		int width = options.outWidth;
		int height = options.outHeight;

		int inSampleSize = 1;

		if (width > reqWidth || height > reqHeight) {
			// 四舍五入
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);
			
			// 取两者的大值 为了节省内存
			inSampleSize = Math.max(widthRadio, heightRadio);
		}
		return inSampleSize;
	}

	/**
	 * 根据ImageView获取适当的压缩的宽和高
	 * @param imageView	返回高度和宽度
	 * @return
	 */
	protected ImageSize getImageViewSize(ImageView imageView) {

		ImageSize imageSize = new ImageSize();

		DisplayMetrics metrics = imageView.getContext().getResources()
				.getDisplayMetrics();

		LayoutParams ip = imageView.getLayoutParams();

		int width = imageView.getWidth(); // 获取imageview的实际宽度
		if (width <= 0) {
			width = ip.width; // 获取imageview在layout中声明的宽度
		}

		if (width <= 0) {
			width = getImageFieldValue(imageView, "mMaxWidth");; // 检查最大值
		}

		if (width <= 0) {
			width = metrics.widthPixels;	//屏幕宽度
		}

		int height = imageView.getHeight(); // 实际高度
		if (height <= 0) {
			height = ip.height; // 声明高度
		}

		if (height <= 0) {
			height = getImageFieldValue(imageView, "mMaxHeight"); // ������ֵ
		}

		if (height <= 0) {
			height = metrics.heightPixels;
		}

		imageSize.height = height;
		imageSize.width = width;

		return imageSize;
	}
	/**
	 * 通过反射获取ImageView的某个属性值
	 * @param object
	 * @return
	 */
	private static int getImageFieldValue(Object object, String fieldName) {
		int value = 0;
		
		try {
			Field field = ImageView.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			int fieldValue = field.getInt(object);
			if(fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
				value = fieldValue;
			}
		}  catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}

	private synchronized void addTasks(Runnable runnable) {
		taskQueue.add(runnable);
		
		try {
			if(poolThreadHandler == null) {
				semaphorePoolThreadHandler.acquire();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		poolThreadHandler.sendEmptyMessage(0x110);
	}

	/**
	 * 根据path在缓存中获取bitmap
	 * 
	 * @param key
	 * @return
	 */
	private Bitmap getBitmapFromLruCache(String key) {
		return lruCache.get(key);
	}

	private class ImageSize {
		int width;
		int height;
	}

	private class ImgBeanHolder {
		Bitmap bitmap;
		ImageView imageView;
		String path;
	}
}
