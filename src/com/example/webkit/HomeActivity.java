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
	
	//��ʱ����
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
		// ��webview��ΪĬ�ϵ���ҳ��ʾ
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
			// �õ�"ת��"����
			if (v.getId() == R.id.GotoBtn) {
				String url = webUrltText.getText().toString();
				// �ж��Ƿ�Ϸ�
				if (URLUtil.isNetworkUrl(url) && URLUtil.isValidUrl(url)) {
					webView.loadUrl(url);
				}
			} else {
				new AlertDialog.Builder(getApplicationContext()).setTitle("����")
						.setMessage("������Ч����ַ").create().show();
			}
		}
	}

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// ��webview�õ��ĵ�ַ���ҵ���վһ��,����false,ͬ���Ŀ����ڵ�ǰ�ĵ�webview������������
			if (Uri.parse(url).getHost().equals("www.domon.cn")) {
				return false;
			}
			// �������µ�Ĭ�ϵ�������д���ҳ
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
			return true;
		}
	}

	// // ���񷵻ؼ�,�������񷵻�,��Ĭ�ϵ�������˳���ǰActivity.
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
	// // ������Ϊ���ؼ�,���ҵ�ǰ��webview���Է���,�򷵻���һ�ν���
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
	 * @param �ж���ҳ�Ƿ��ܷ���
	 *            ,���ܷ��صĻ����������˳��� �˳�����
	 */
	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			if (!isExit) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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
