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
		toolsTabInflater = LayoutInflater.from(this.context);

		initTab();

		//设置宽高
		setWidth(width);
		setHeight(height);

		setOutsideTouchable(true);
		setFocusable(true);

		setContentView(toolsTabView);
	}

	// 实例化标签
	private void initTab() {
		toolsTabView = toolsTabInflater.inflate(R.layout.activity_tabs, null);
		// 获取tabhost
		toolsTab = (TabHost) toolsTabView.findViewById(android.R.id.tabhost);
		// 使用findViewById()加载tabhost时在调用addTab前必须调用
		toolsTab.setup(); 

		toolsTab.addTab(this.toolsTab.newTabSpec("normal")
				.setIndicator("常用").setContent(R.id.more_normal));
		toolsTab.addTab(this.toolsTab.newTabSpec("setttings")
				.setIndicator("设置").setContent(R.id.more_setting));
		toolsTab.addTab(this.toolsTab.newTabSpec("tool")
				.setIndicator("工具").setContent(R.id.more_tools));
		
		// 设置默认选种标签
		toolsTab.setCurrentTab(0); 
	}

	public View getView(int id) {
		return this.toolsTabView.findViewById(id);
	}
}
