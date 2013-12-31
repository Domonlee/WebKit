package com.example.webkit;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private WebView webView;
	private EditText webUrltText;
	private Button webUrlGotoBtn;
	
	//计时功能
	private static boolean isExit = false;
	private static Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);


		findViewById();
		new BtnClickedListener();
		// 将webview作为默认的网页显示
		webView.setWebViewClient(new MyWebViewClient());

	}
	
	private void findViewById() {
		
		webView = (WebView) findViewById(R.id.webview);
		webUrltText = (EditText) findViewById(R.id.web_Url_addr);
		webUrlGotoBtn = (Button) findViewById(R.id.GotoBtn);
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

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 若webview得到的地址和我的网站一样,返回false,同样的可以在当前的的webview打开其他的链接
			if (Uri.parse(url).getHost().equals("www.domon.cn")) {
				return false;
			}
			// 否则在新的默认的浏览器中打开网页
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
			return true;
		}
	}

	// // 捕获返回键,若不捕获返回,则默认的情况是退出当前Activity.
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
	// // 若按键为返回键,而且当前的webview可以返回,则返回上一次界面
	// webView.goBack();
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	/**
	 * @param 判断网页是否能返回
	 *            ,不能返回的话连续两次退出键 退出程序
	 */
	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			if (!isExit) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_LONG).show();
				handler.sendEmptyMessageDelayed(0, 2000);
			}
			else {
				finish();
				System.exit(0);
			}
		}
		super.onBackPressed();
	}

}
