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
	 * 输入宽高和上下文对象
	 */
	public MorePopWindows(Context context, int width, int height) {
		super(context);
		this.context = context;
		moreInflater = LayoutInflater.from(context);

		initTab();

		//设置宽高
		setWidth(width);
		setHeight(height);

		setOutsideTouchable(true);
		setFocusable(true);

		setContentView(moreTabView);
	}

	// 实例化标签
	private void initTab() {
		moreTabView = moreInflater.inflate(R.layout.activity_tabs, null);
		// 获取tabhost
		moreTabHost = (TabHost) moreTabView.findViewById(android.R.id.tabhost);
		// 使用findViewById()加载tabhost时在调用addTab前必须调用
		moreTabHost.setup(); 

		moreTabHost.addTab(this.moreTabHost.newTabSpec("normal")
				.setIndicator("常用").setContent(R.id.more_normal));
		this.moreTabHost.addTab(this.moreTabHost.newTabSpec("setttings")
				.setIndicator("设置").setContent(R.id.more_setting));
		this.moreTabHost.addTab(this.moreTabHost.newTabSpec("tool")
				.setIndicator("工具").setContent(R.id.more_tools));
		
		// 设置默认选种标签
		this.moreTabHost.setCurrentTab(0); 
	}

	public View getView(int id) {
		return this.moreTabView.findViewById(id);
	}
}
