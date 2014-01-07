package com.example.Other;

import com.example.webkit.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TabHost;

public class MorePopWindows extends PopupWindow {
	private TabHost toolsTab;
	private Context context;
	private LayoutInflater toolsTabInflater;
	private View toolsTabView;

	/*
	 * 输入宽高和上下文对象
	 */
	public MorePopWindows(Context context, int width, int height) {
		super(context);
		this.context = context;
		this.toolsTabInflater = LayoutInflater.from(this.context);

		this.initTab();

		// 设置宽高
		setWidth(width);
		setHeight(height);

		setContentView(toolsTabView);
		setOutsideTouchable(true);
		setFocusable(true);

	}

	// 实例化标签
	private void initTab() {
		this.toolsTabView = this.toolsTabInflater.inflate(
				R.layout.activity_tabs, null);
		// 获取tabhost
		this.toolsTab = (TabHost) this.toolsTabView
				.findViewById(android.R.id.tabhost);
		// 使用findViewById()加载tabhost时在调用addTab前必须调用
		this.toolsTab.setup();

		this.toolsTab.addTab(this.toolsTab.newTabSpec("normal")
				.setIndicator("常用").setContent(R.id.more_normal));
		this.toolsTab.addTab(this.toolsTab.newTabSpec("setttings")
				.setIndicator("设置").setContent(R.id.more_setting));
		this.toolsTab.addTab(this.toolsTab.newTabSpec("tool")
				.setIndicator("工具").setContent(R.id.more_tools));

		// 设置默认选种标签
		this.toolsTab.setCurrentTab(0);
	}

	public View getView(int id) {
		return this.toolsTabView.findViewById(id);
	}
}
