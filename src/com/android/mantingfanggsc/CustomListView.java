package com.android.mantingfanggsc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;



/**
 * ListView����ˢ�ºͼ��ظ���<p> 
 * 
 */
public class CustomListView extends ListView implements OnScrollListener {

	/**  ��ʾ��ʽ������ģ��   */
	private final static String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm";
	
	/**  ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı���   */
	private final static int RATIO = 3;
	
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	
	/**  ������   */
	private final static int ENDINT_LOADING = 1;
	/**  �ֶ����ˢ��   */
	private final static int ENDINT_MANUAL_LOAD_DONE = 2;
	/**  �Զ����ˢ��   */
	private final static int ENDINT_AUTO_LOAD_DONE = 3;
	
	/**    0:RELEASE_TO_REFRESH;
	 * <p> 1:PULL_To_REFRESH;
	 * <p> 2:REFRESHING;
	 * <p> 3:DONE;
	 * <p> 4:LOADING */
	private int mHeadState;
	/**    0:���/�ȴ�ˢ�� ;
	 * <p> 1:������  */
	private int mEndState;
	
	// ================================= ��������Flag ================================
	
	/**  ���Լ��ظ��ࣿ   */
	private boolean mCanLoadMore = false;
	/**  ��������ˢ�£�   */
	private boolean mCanRefresh = false;
	/**  �����Զ����ظ����𣿣�ע�⣬���ж��Ƿ��м��ظ��࣬���û�У����flagҲû�����壩   */
	private boolean mIsAutoLoadMore = true;
	/** ����ˢ�º��Ƿ���ʾ��һ��Item    */
	private boolean mIsMoveToFirstItemAfterRefresh = false;
	
	public boolean isCanLoadMore() {
		return mCanLoadMore;
	}
	
	public void setCanLoadMore(boolean pCanLoadMore) {
		mCanLoadMore = pCanLoadMore;
		if(mCanLoadMore && getFooterViewsCount() == 0){
			addFooterView();
		}
	}
	
	public boolean isCanRefresh() {
		return mCanRefresh;
	}
	
	public void setCanRefresh(boolean pCanRefresh) {
		mCanRefresh = pCanRefresh;
	}
	
	public boolean isAutoLoadMore() {
		return mIsAutoLoadMore;
	}

	public void setAutoLoadMore(boolean pIsAutoLoadMore) {
		mIsAutoLoadMore = pIsAutoLoadMore;
	}
		
	public boolean isMoveToFirstItemAfterRefresh() {
		return mIsMoveToFirstItemAfterRefresh;
	}

	public void setMoveToFirstItemAfterRefresh(
			boolean pIsMoveToFirstItemAfterRefresh) {
		mIsMoveToFirstItemAfterRefresh = pIsMoveToFirstItemAfterRefresh;
	}
	
	// ============================================================================

	private LayoutInflater mInflater;

	private LinearLayout mHeadView;
	private TextView mTipsTextView;
	private TextView mLastUpdatedTextView;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	
	private View mEndRootView;
	private ProgressBar mEndLoadProgressBar;
	private TextView mEndLoadTipsTextView;

	/**  headView����   */
	private RotateAnimation mArrowAnim;
	/**  headView��ת����   */
	private RotateAnimation mArrowReverseAnim;
 
	/** ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��    */
	private boolean mIsRecored;

	private int mHeadViewWidth;
	private int mHeadViewHeight;

	private int mStartY;
	private boolean mIsBack;
	
	private int mFirstItemIndex;
	private int mLastItemIndex;
	private int mCount;
	private boolean mEnoughCount;//�㹻����������Ļ�� 
	
	private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;

	public CustomListView(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		init(pContext);
	}

	public CustomListView(Context pContext) {
		super(pContext);
		init(pContext);
	}

	public CustomListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
		super(pContext, pAttrs, pDefStyle);
		init(pContext);
	}

	/**
	 * ��ʼ������
	 * @param pContext 
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void init(Context pContext) {
		setCacheColorHint(pContext.getResources().getColor(R.color.transparent));
		mInflater = LayoutInflater.from(pContext);

		addHeadView();
		
		setOnScrollListener(this);

		initPullImageAnimation(0);
	}

	/**
	 * �������ˢ�µ�HeadView 
	 *
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addHeadView() {
		mHeadView = (LinearLayout) mInflater.inflate(R.layout.head, null);

		mArrowImageView = (ImageView) mHeadView
				.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(70);
		mArrowImageView.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadView
				.findViewById(R.id.head_progressBar);
		mTipsTextView = (TextView) mHeadView.findViewById(
				R.id.head_tipsTextView);
		mLastUpdatedTextView = (TextView) mHeadView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(mHeadView);
		mHeadViewHeight = mHeadView.getMeasuredHeight();
		mHeadViewWidth = mHeadView.getMeasuredWidth();
		
		mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
		mHeadView.invalidate();

		Log.v("size", "width:" + mHeadViewWidth + " height:"
                + mHeadViewHeight);

		addHeaderView(mHeadView, null, false);
		
		mHeadState = DONE;
	}
	
	/**
	 * ��Ӽ��ظ���FootView
	 *
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addFooterView() {
		mEndRootView = mInflater.inflate(R.layout.list_footer_more, null);
		mEndRootView.setVisibility(View.VISIBLE);
		mEndLoadProgressBar = (ProgressBar) mEndRootView
				.findViewById(R.id.pull_to_refresh_progress);
		mEndLoadTipsTextView = (TextView) mEndRootView.findViewById(R.id.load_more);
		mEndRootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mCanLoadMore){
					if(mCanRefresh){
						// ����������ˢ��ʱ�����FootViewû�����ڼ��أ�����HeadViewû������ˢ�£��ſ��Ե�����ظ��ࡣ
						if(mEndState != ENDINT_LOADING && mHeadState != REFRESHING){
							mEndState = ENDINT_LOADING;
							onLoadMore();
						}
					}else if(mEndState != ENDINT_LOADING){
						// ����������ˢ��ʱ��FootView�����ڼ���ʱ���ſ��Ե�����ظ��ࡣ
						mEndState = ENDINT_LOADING;
						onLoadMore();
					}
				}
			}
		});
		
		addFooterView(mEndRootView);
		
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
	}

	/**
	 * ʵ��������ˢ�µļ�ͷ�Ķ���Ч�� 
	 * @param pAnimDuration ��������ʱ��
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void initPullImageAnimation(final int pAnimDuration) {
		
		int _Duration;
		
		if(pAnimDuration > 0){
			_Duration = pAnimDuration;
		}else{
			_Duration = 250;
		}
//		Interpolator _Interpolator;
//		switch (pAnimType) {
//		case 0:
//			_Interpolator = new AccelerateDecelerateInterpolator();
//			break;
//		case 1:
//			_Interpolator = new AccelerateInterpolator();
//			break;
//		case 2:
//			_Interpolator = new AnticipateInterpolator();
//			break;
//		case 3:
//			_Interpolator = new AnticipateOvershootInterpolator();
//			break;
//		case 4:
//			_Interpolator = new BounceInterpolator();
//			break;
//		case 5:
//			_Interpolator = new CycleInterpolator(1f);
//			break;
//		case 6:
//			_Interpolator = new DecelerateInterpolator();
//			break;
//		case 7:
//			_Interpolator = new OvershootInterpolator();
//			break;
//		default:
//			_Interpolator = new LinearInterpolator();
//			break;
//		}
		
		Interpolator _Interpolator = new LinearInterpolator();
		
		mArrowAnim = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowAnim.setInterpolator(_Interpolator);
		mArrowAnim.setDuration(_Duration);
		mArrowAnim.setFillAfter(true);

		mArrowReverseAnim = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowReverseAnim.setInterpolator(_Interpolator);
		mArrowReverseAnim.setDuration(_Duration);
		mArrowReverseAnim.setFillAfter(true);
	}

	/**
	 * ����HeadView���(ע�⣺�˷�����������LinearLayout��������Լ�������֤��)
	 * @param pChild 
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 *Ϊ���жϻ�����ListView�ײ�û
	 */
	@Override
	public void onScroll(AbsListView pView, int pFirstVisibleItem,
			int pVisibleItemCount, int pTotalItemCount) {
		mFirstItemIndex = pFirstVisibleItem;
		mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
		mCount = pTotalItemCount - 2;
		if (pTotalItemCount > pVisibleItemCount ) {
			mEnoughCount = true;
//			endingView.setVisibility(View.VISIBLE);
		} else {
			mEnoughCount = false;
		}
	}

	/**
	 *
	 */
	@Override
	public void onScrollStateChanged(AbsListView pView, int pScrollState) {
		if(mCanLoadMore){// ���ڼ��ظ��๦��
			if (mLastItemIndex ==  mCount && pScrollState == SCROLL_STATE_IDLE) {
				//SCROLL_STATE_IDLE=0������ֹͣ
				if (mEndState != ENDINT_LOADING) {
					if(mIsAutoLoadMore){// �Զ����ظ��࣬������FootView��ʾ ����    �ࡱ
						if(mCanRefresh){
							// ��������ˢ�²���HeadViewû������ˢ��ʱ��FootView�����Զ����ظ��ࡣ
							if(mHeadState != REFRESHING){
								// FootView��ʾ : ��    ��  ---> ������...
								mEndState = ENDINT_LOADING;
								onLoadMore();
								changeEndViewByState();
							}
						}else{// û������ˢ�£�����ֱ�ӽ��м��ظ��ࡣ
							// FootView��ʾ : ��    ��  ---> ������...
							mEndState = ENDINT_LOADING;
							onLoadMore();
							changeEndViewByState();
						}
					}else{// �����Զ����ظ��࣬������FootView��ʾ ��������ء�
						// FootView��ʾ : �������  ---> ������...
						mEndState = ENDINT_MANUAL_LOAD_DONE;
						changeEndViewByState();
					}
				}
			}
		}else if(mEndRootView != null && mEndRootView.getVisibility() == VISIBLE){
			// ͻȻ�رռ��ظ��๦��֮������Ҫ�Ƴ�FootView��
		//	System.out.println("this.removeFooterView(endRootView);...");
			mEndRootView.setVisibility(View.GONE);
			this.removeFooterView(mEndRootView);
		}
	}

	/**
	 * �ı���ظ���״̬
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void  changeEndViewByState() {
		if (mCanLoadMore) {
			//������ظ���
			switch (mEndState) {
			case ENDINT_LOADING://ˢ����
				
				// ������...
				if(mEndLoadTipsTextView.getText().equals(
						R.string.p2refresh_doing_end_refresh)){
					break;
				}
				mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_MANUAL_LOAD_DONE:// �ֶ�ˢ�����
				
				// �������
				mEndLoadTipsTextView.setText(R.string.p2refresh_end_click_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			case ENDINT_AUTO_LOAD_DONE:// �Զ�ˢ�����
				
				// ��    ��
				mEndLoadTipsTextView.setText(R.string.p2refresh_end_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			default:
				// ԭ���Ĵ�����Ϊ�ˣ� ������item�ĸ߶�С��ListView����ĸ߶�ʱ��
				// Ҫ���ص�FootView������Լ�ȥԭ���ߵĴ���ο���
				
//				if (enoughCount) {					
//					endRootView.setVisibility(View.VISIBLE);
//				} else {
//					endRootView.setVisibility(View.GONE);
//				}
				break;
			}
		}
	}
	
	/**
	 *
	 */
	public boolean onTouchEvent(MotionEvent event) {
		
		if (mCanRefresh) {
			if(mCanLoadMore && mEndState == ENDINT_LOADING){
				// ������ڼ��ظ��๦�ܣ����ҵ�ǰ���ڼ��ظ��࣬Ĭ�ϲ���������ˢ�£����������Ϻ����ʹ�á�
				return super.onTouchEvent(event);
			}
			
			switch (event.getAction()) {
			
			case MotionEvent.ACTION_DOWN:
				if (mFirstItemIndex == 0 && !mIsRecored) {
					mIsRecored = true;
					mStartY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:

				if (mHeadState != REFRESHING && mHeadState != LOADING) {
					if (mHeadState == DONE) {
						
					}
					if (mHeadState == PULL_TO_REFRESH) {
						mHeadState = DONE;
						changeHeaderViewByState();
					}
					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadState = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				mIsRecored = false;
				mIsBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!mIsRecored && mFirstItemIndex == 0) {
					mIsRecored = true;
					mStartY = tempY;
				}

				if (mHeadState != REFRESHING && mIsRecored && mHeadState != LOADING) {

					// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head��
					// ����������б�����Ļ�Ļ����������Ƶ�ʱ���б��ͬʱ���й���
					// ��������ȥˢ����
					if (mHeadState == RELEASE_TO_REFRESH) {

						setSelection(0);

						// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
						if (((tempY - mStartY) / RATIO < mHeadViewHeight)
								&& (tempY - mStartY) > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
						// һ�����Ƶ�����
						else if (tempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeaderViewByState();
						}
						// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
					}
					// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
					if (mHeadState == PULL_TO_REFRESH) {

						setSelection(0);

						// ���������Խ���RELEASE_TO_REFRESH��״̬
						if ((tempY - mStartY) / RATIO >= mHeadViewHeight) {
							mHeadState = RELEASE_TO_REFRESH;
							mIsBack = true;
							changeHeaderViewByState();
						} else if (tempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeaderViewByState();
						}
					}

					if (mHeadState == DONE) {
						if (tempY - mStartY > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (mHeadState == PULL_TO_REFRESH) {
						mHeadView.setPadding(0, -1 * mHeadViewHeight
								+ (tempY - mStartY) / RATIO, 0, 0);

					}

					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadView.setPadding(0, (tempY - mStartY) / RATIO
								- mHeadViewHeight, 0, 0);
					}
				}
				break;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * ��HeadView״̬�ı�ʱ�򣬵��ø÷������Ը��½���
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void changeHeaderViewByState() {
		switch (mHeadState) {
		case RELEASE_TO_REFRESH:
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mArrowAnim);
			// �ɿ�ˢ��
			mTipsTextView.setText(R.string.p2refresh_release_refresh);

			break;
		case PULL_TO_REFRESH:
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.VISIBLE);
			// ����RELEASE_To_REFRESH״̬ת������
			if (mIsBack) {
				mIsBack = false;
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mArrowReverseAnim);
				// ����ˢ��
				mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			} else {
				// ����ˢ��
				mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			}
			break;

		case REFRESHING:
			mHeadView.setPadding(0, 0, 0, 0);
			
			// �����Ľ��飺 ʵ���������setPadding�����ö��������档��û���ԣ������Ҽ�������ʵ�е���Ҳ��Scroller����ʵ�����Ч����
			// ��ûʱ���о��ˣ���������չ�������������С���������~ ����Ľ��˼ǵ÷�����������~
			// �������䣺 xxzhaofeng5412@gmail.com
			
			mProgressBar.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			// ����ˢ��...
			mTipsTextView.setText(R.string.p2refresh_doing_head_refresh);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		case DONE:
			mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
			
			// �˴����ԸĽ���ͬ��������
			
			mProgressBar.setVisibility(View.GONE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setImageResource(R.drawable.arrowla);
			// ����ˢ��
			mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		}
	}

	/**
	 * ����ˢ�¼����ӿ�
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}
	
	/**
	 * ���ظ�������ӿ�
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
	}
	
	public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
		if(pRefreshListener != null){
			mRefreshListener = pRefreshListener;
			mCanRefresh = true;
		}
	}

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if(pLoadMoreListener != null){
			mLoadMoreListener = pLoadMoreListener;
			mCanLoadMore = true;
			if(mCanLoadMore && getFooterViewsCount() == 0){
				addFooterView();
			}
		}
	}
	
	/**
	 * ��������ˢ��
	 *
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void onRefresh() {
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh();
		}
	}
	
	/**
	 * ����ˢ�����
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onRefreshComplete() {
		// ����ˢ�º��Ƿ���ʾ��һ��Item 
		if(mIsMoveToFirstItemAfterRefresh)setSelection(0);
		
		mHeadState = DONE;
		// �������: Time
		mLastUpdatedTextView.setText(
				getResources().getString(R.string.p2refresh_refresh_lasttime) + 
				new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		changeHeaderViewByState();
	}

	/**
	 * ���ڼ��ظ��࣬FootView��ʾ �� ������...
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void onLoadMore() {
		if (mLoadMoreListener != null) {
			// ������...
			mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
			mEndLoadTipsTextView.setVisibility(View.VISIBLE);
			mEndLoadProgressBar.setVisibility(View.VISIBLE);
			
			mLoadMoreListener.onLoadMore();
		}
	}

	/**
	 * ���ظ������ 
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onLoadMoreComplete() {
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
		changeEndViewByState();
	}
	
	/**
	 * ��Ҫ����һ��ˢ��ʱ������
	 * @param adapter
	 * 
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void setAdapter(BaseAdapter adapter) {
		// �������: Time
		mLastUpdatedTextView.setText(getResources().getString(R.string.p2refresh_refresh_lasttime) + 
				new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		super.setAdapter(adapter);
	}
	
	/*public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}*/

}
