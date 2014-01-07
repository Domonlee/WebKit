package com.example.webkit;

import com.example.Other.MorePopWindows;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private WebView webView;
	private EditText webUrltText;
	private Button gotoButton;
	private ChromeClient webChromeClient;
	private WebSettings webSettings;

	// ��ҳ��ַ�Ͱ�������
	private LinearLayout webUrlLayout;
	private RelativeLayout btnsLayout;

	// ���ð���
	private Button preButton;
	private Button nextButton;
	private Button homeButton;
	private Button tabsButton;
	private Button moreButton;
	private Button moreBtn_normal_refreshButton;
	private Button moreBtn_normal_fullButton;
	private Button fullButton;
	private ProgressBar progressBar;

	// ��ȡ���ఴť��������
	private MorePopWindows morePopWindows;

	// ��ַ
	private String url = "";

	// ��ʱ����
	private static boolean isExit = false;
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	private MyWebViewClient myWebViewClient;
	// ���Ƽ���
	private GestureListener mGestureListener;
	private GestureDetector mGestureDetector;

	// ������
	private BtnClickedListener btnClickedListener;
	private WebUrlChangedListener webUrlChangedListener;
	private WebViewTouchListener webViewTouchListener;

	// �˵�
	private static int FIRST = Menu.FIRST;
	private static int SECOND = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = HomeActivity.this.getWindow();
		requestWindowFeature(window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_home);
		findViewById();

		// ��webview��ΪĬ�ϵ���ҳ��ʾ
		myWebViewClient = new MyWebViewClient();
		webView.setWebViewClient(myWebViewClient);

		// ���ؽ�����,�ٷ�֮���Զ�����
		webChromeClient = new ChromeClient();
		webView.setWebChromeClient(webChromeClient);

		// �������,������
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		// ���û���������
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);

		// Ϊ���밴�������¼�
		btnClickedListener = new BtnClickedListener();
		gotoButton.setOnClickListener(btnClickedListener);

		// ���webview�Ĵ���
		webViewTouchListener = new WebViewTouchListener();
		webView.setOnTouchListener(webViewTouchListener);

		// ����������GestureListener����
		mGestureListener = new GestureListener();
		mGestureDetector = new GestureDetector(this, mGestureListener);

		// Ϊ��ַ����Ӱ��¼�,����ַ�Ƿ�Ϸ�,�Լ��Զ���ȫ
		webUrlChangedListener = new WebUrlChangedListener();
		webUrltText.addTextChangedListener(webUrlChangedListener);

		preButton.setEnabled(false);
		nextButton.setEnabled(false);

		// Ϊ���ð�����Listener
		preButton.setOnClickListener(btnClickedListener);
		nextButton.setOnClickListener(btnClickedListener);
		homeButton.setOnClickListener(btnClickedListener);
		tabsButton.setOnClickListener(btnClickedListener);
		moreButton.setOnClickListener(btnClickedListener);

		fullButton.setOnClickListener(btnClickedListener);

		progressBar.setVisibility(View.GONE);

		this.morePopWindows = new MorePopWindows(this, this.getWindowManager()
				.getDefaultDisplay().getWidth() - 30, this.getWindowManager()
				.getDefaultDisplay().getHeight() / 3);

	}

	// ��ȡ�ؼ�
	private void findViewById() {
		webView = (WebView) findViewById(R.id.webview);
		webUrltText = (EditText) findViewById(R.id.web_Url_addr);
		gotoButton = (Button) findViewById(R.id.GotoBtn);
		webUrlLayout = (LinearLayout) findViewById(R.id.web_Url_Layout);
		btnsLayout = (RelativeLayout) findViewById(R.id.Btns_Layout);
		preButton = (Button) findViewById(R.id.Pre_Btn);
		nextButton = (Button) findViewById(R.id.Next_Btn);
		homeButton = (Button) findViewById(R.id.Home_Btn);
		tabsButton = (Button) findViewById(R.id.Tabs_Btn);
		moreButton = (Button) findViewById(R.id.More_Btn);
		fullButton = (Button) findViewById(R.id.Full_Btn);
		progressBar = (ProgressBar) findViewById(R.id.webProgressBar);
	}

	/*
	 * ��ť�¼�,�����ж������Ƿ�Ϸ�,���Ϸ�����
	 */
	private class BtnClickedListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// �õ�"ת��"����
			if (v.getId() == R.id.GotoBtn) {
				String url = webUrltText.getText().toString();
				// �ж��Ƿ�Ϸ�
				if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {
					webView.loadUrl(url);
				} else {
					// �����Ի�����ʾ�û�
					new AlertDialog.Builder(HomeActivity.this)
							.setTitle("����")
							.setMessage("������Ч����ַ!" + "\n" + "�뷵����������~")
							.setPositiveButton("����",
									new AlertDialog.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).create().show();
				}
			}
			// �õ����ذ���
			else if (v.getId() == R.id.Pre_Btn) {
				if (webView.canGoBack()) {
					webView.goBack();
				}
			} else if (v.getId() == R.id.Next_Btn) {
				if (webView.canGoForward()) {
					webView.goForward();
				}
			} else if (v.getId() == R.id.Home_Btn) {
				webView.loadUrl("http://www.baidu.com");
			} else if (v.getId() == R.id.Tabs_Btn) {
				Toast.makeText(getApplicationContext(), "���ڿ�����...",
						Toast.LENGTH_SHORT).show();
			} else if (v.getId() == R.id.More_Btn) {
				LayoutInflater toolsInflater = LayoutInflater
						.from(getApplicationContext());
				View toolsView = toolsInflater.inflate(R.layout.activity_tabs,
						null);
				morePopWindows.showAtLocation(toolsView, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0,
						moreButton.getHeight() + 20);
				moreBtn_normal_refreshButton = (Button) morePopWindows
						.getView(R.id.more_normal_refresh);
				moreBtn_normal_fullButton = (Button) morePopWindows
						.getView(R.id.more_normal_full);
				moreBtn_normal_refreshButton.setOnClickListener(this);
				moreBtn_normal_fullButton.setOnClickListener(this);
			} else if (v.getId() == R.id.more_normal_refresh) {
				if (!(url.equals("") && url.equals("http://"))) {
					webView.loadUrl(url);
				}
			} else if (v.getId() == R.id.more_normal_full) {
				webUrlLayout.setVisibility(View.GONE);
				btnsLayout.setVisibility(View.GONE);
				fullButton.setVisibility(View.VISIBLE);
			} else if (v.getId() == R.id.Full_Btn) {
				webUrlLayout.setVisibility(View.VISIBLE);
				btnsLayout.setVisibility(View.VISIBLE);
				fullButton.setVisibility(View.GONE);
			}

		}
	}

	/*
	 * TextWatcher ʵʱ���url�ĺϷ���
	 */
	private class WebUrlChangedListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			url = s.toString();
			if (!(url.startsWith("http://")) || (url.startsWith("https://"))) {
				url = "http://" + url;
			}
			if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {

				// changeStatueOfWebUrlGotoBtn(true);
			} else {
				// changeStatueOfWebUrlGotoBtn(false);
			}
		}
	}

	/*
	 * ͨ���Լ���webView����ʾ������ҳ,��Ҫoverride WebViewClient
	 */
	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		// ������ɺ����ص�ַ��
		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
			webUrltText.setText(url);
			// webUrlLayout.setVisibility(View.GONE);
			StatueOfPreAndNextBtns();
		}

	}

	/*
	 * ���Ƶ�ʵ�� �ϻ���������ʾ��ַ��,���»����ײ�,���ص�ַ��
	 */
	private class GestureListener implements OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (webView.getScrollY() == 0) {
				// ��������
				webUrlLayout.setVisibility(View.VISIBLE);
			}
			if (webView.getScrollY() > 0) {
				// �����ײ�
				webUrlLayout.setVisibility(View.GONE);
			}
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
	}

	/*
	 * �Զ���oTouchListener �����ƽ���GestureListener���
	 */

	private class WebViewTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v.getId() == R.id.webview) {
				return mGestureDetector.onTouchEvent(event);
			}
			return false;
		}
	}

	/*
	 * WebChromeClient�Զ���̳��� override onProgressChanged
	 */
	private class ChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if (newProgress == 100) {
				progressBar.setVisibility(view.GONE);
			} else {
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgress(newProgress);
			}
		}
	}

	/*
	 * �����Ƿ���Ե����ǰ���ť
	 */
	private void StatueOfPreAndNextBtns() {
		// �ɷ���,���ذ�ť�ɵ��,���򲻿���
		if (webView.canGoBack()) {
			preButton.setEnabled(true);
		} else {
			preButton.setEnabled(false);
		}
		// �ɽ���,���밴ť�ɵ��,���򲻿���
		if (webView.canGoForward()) {
			nextButton.setEnabled(true);
		} else {
			nextButton.setEnabled(false);
		}
	}

	/*
	 * �ж���ҳ�Ƿ��ܷ��� ,���ܷ��صĻ����������˳��� �˳�����
	 */
	@Override
	public void onBackPressed() {
		// �ж��Ƿ����
		if (webView.canGoBack()) {
			webView.goBack();
			// Ҳ���������и���������ť״̬
		} else {
			if (!isExit) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
						Toast.LENGTH_LONG).show();
				// 2s�ж�
				handler.sendEmptyMessageDelayed(0, 2000);
			} else {
				finish();
				System.exit(0);
			}
		}
	}

	// �˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, FIRST, 1, "����");
		menu.add(0, SECOND, 2, "�˳�");
		return true;
	}

	// �˵�ѡ��
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST: {
			new AlertDialog.Builder(HomeActivity.this)
					.setTitle("����")
					.setMessage(
							"Android���������" + "\n" + "������: ����" + "\n"
									+ "��Ŀ��ַhttps://github.com/Domonlee/WebKit"
									+ "\n" + "��ϵ��ʽ:viplizhao@gmail.com ")
					.setPositiveButton("����",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create().show();
			break;
		}
		case Menu.FIRST + 1: {
			new AlertDialog.Builder(HomeActivity.this)
					.setTitle("��ʾ")
					.setMessage("ȷ���˳���?")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									HomeActivity.this.finish();
								}
							})
					.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).create().show();
		}
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
