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
		
		// ��webview��ΪĬ�ϵ���ҳ��ʾ
		webView.setWebViewClient(new MyWebViewClient());

		webUrlGotoBtn.setOnClickListener(new BtnClickedListener());
		
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

	/**
	 * TextWatcher
	 * 
	 * @author Domon ʵʱ���url�ĺϷ���
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
	 * ͨ���Լ���webView����ʾ������ҳ,��Ҫoverride WebViewClient
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
		
		//������ɺ����ص�ַ��
		@Override
		public void onPageFinished(WebView view, String url) {
			
			super.onPageFinished(view, url);
			webUrlLayout.setVisibility(View.GONE);
		}



	}

	//�˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	/*
	 *  �ж���ҳ�Ƿ��ܷ���
	 *            ,���ܷ��صĻ����������˳��� �˳�����
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
	
	/*
	 * ���Ƶ�ʵ��  �ϻ���������ʾ��ַ��,���»����ײ�,���ص�ַ��
	 */
	
	
}