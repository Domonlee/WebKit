package com.example.webkit;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private WebView webView;
	private EditText webUrltText;
	private Button webUrlGotoBtn;
	
	private LinearLayout webUrlLayout;

	// 网址
	private String url = "";

	// 计时功能
	private static boolean isExit = false;
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = HomeActivity.this.getWindow();
		requestWindowFeature(window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_home);

		webView = (WebView) findViewById(R.id.webview);
		webUrltText = (EditText) findViewById(R.id.web_Url_addr);
		webUrlGotoBtn = (Button) findViewById(R.id.GotoBtn);
		
		webUrlLayout = (LinearLayout)findViewById(R.id.web_Url_Layout);
		
		// 将webview作为默认的网页显示
		webView.setWebViewClient(new MyWebViewClient());

		webUrlGotoBtn.setOnClickListener(new BtnClickedListener());
		
	}

	private class BtnClickedListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// 得到"转入"按键
			if (v.getId() == R.id.GotoBtn) {
				String url = webUrltText.getText().toString();
				// 判断是否合法
				if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {
					webView.loadUrl(url);
				}
			} else {
				new AlertDialog.Builder(getApplicationContext()).setTitle("警告")
						.setMessage("不是有效的网址").create().show();
			}
		}
	}

	/**
	 * TextWatcher
	 * 
	 * @author Domon 实时检测url的合法性
	 * 
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
			Log.d("Domon", "OnchangeText:" + url);

			if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {
				// changeStatueOfWebUrlGotoBtn(true);
			} else {
				// changeStatueOfWebUrlGotoBtn(false);
			}
		}

	}

	/**
	 * 通过自己的webView来显示所有网页,需要override WebViewClient
	 * 
	 * @author Domon
	 * 
	 */
	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		//加载完成后隐藏地址栏
		@Override
		public void onPageFinished(WebView view, String url) {
			
			super.onPageFinished(view, url);
			webUrlLayout.setVisibility(View.GONE);
		}



	}

	//菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	/*
	 *  判断网页是否能返回
	 *            ,不能返回的话连续两次退出键 退出程序
	 */
	@Override
	public void onBackPressed() {
		// 判断是否可退
		if (webView.canGoBack()) {
			webView.goBack();
			// 也可以在其中更改其他按钮状态
		} else {
			if (!isExit) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_LONG).show();
				// 2s判定
				handler.sendEmptyMessageDelayed(0, 2000);
			} else {
				finish();
				System.exit(0);
			}
		}
	}
	
	/*
	 * 手势的实现  上滑到顶部显示地址栏,向下滑到底部,隐藏地址栏
	 */
	
	
}