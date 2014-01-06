package com.example.Other;

import com.example.webkit.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TabHost;

public class MorePopWindows extends PopupWindow {
	private TabHost moreTabHost;
	private Context context;
	private LayoutInflater moreInflater;
	private View moreTabView;

	/*
	 * �����ߺ������Ķ���
	 */
	public MorePopWindows(Context context, int width, int height) {
		super(context);
		this.context = context;
		moreInflater = LayoutInflater.from(context);

		initTab();

		//���ÿ��
		setWidth(width);
		setHeight(height);

		setOutsideTouchable(true);
		setFocusable(true);

		setContentView(moreTabView);
	}

	// ʵ������ǩ
	private void initTab() {
		moreTabView = moreInflater.inflate(R.layout.activity_tabs, null);
		// ��ȡtabhost
		moreTabHost = (TabHost) moreTabView.findViewById(android.R.id.tabhost);
		// ʹ��findViewById()����tabhostʱ�ڵ���addTabǰ�������
		moreTabHost.setup(); 

		moreTabHost.addTab(this.moreTabHost.newTabSpec("normal")
				.setIndicator("����").setContent(R.id.more_normal));
		this.moreTabHost.addTab(this.moreTabHost.newTabSpec("setttings")
				.setIndicator("����").setContent(R.id.more_setting));
		this.moreTabHost.addTab(this.moreTabHost.newTabSpec("tool")
				.setIndicator("����").setContent(R.id.more_tools));
		
		// ����Ĭ��ѡ�ֱ�ǩ
		this.moreTabHost.setCurrentTab(0); 
	}

	public View getView(int id) {
		return this.moreTabView.findViewById(id);
	}
}
