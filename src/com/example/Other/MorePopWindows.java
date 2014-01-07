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
	 * �����ߺ������Ķ���
	 */
	public MorePopWindows(Context context, int width, int height) {
		super(context);
		this.context = context;
		this.toolsTabInflater = LayoutInflater.from(this.context);

		this.initTab();

		// ���ÿ��
		setWidth(width);
		setHeight(height);

		setContentView(toolsTabView);
		setOutsideTouchable(true);
		setFocusable(true);

	}

	// ʵ������ǩ
	private void initTab() {
		this.toolsTabView = this.toolsTabInflater.inflate(
				R.layout.activity_tabs, null);
		// ��ȡtabhost
		this.toolsTab = (TabHost) this.toolsTabView
				.findViewById(android.R.id.tabhost);
		// ʹ��findViewById()����tabhostʱ�ڵ���addTabǰ�������
		this.toolsTab.setup();

		this.toolsTab.addTab(this.toolsTab.newTabSpec("normal")
				.setIndicator("����").setContent(R.id.more_normal));
		this.toolsTab.addTab(this.toolsTab.newTabSpec("setttings")
				.setIndicator("����").setContent(R.id.more_setting));
		this.toolsTab.addTab(this.toolsTab.newTabSpec("tool")
				.setIndicator("����").setContent(R.id.more_tools));

		// ����Ĭ��ѡ�ֱ�ǩ
		this.toolsTab.setCurrentTab(0);
	}

	public View getView(int id) {
		return this.toolsTabView.findViewById(id);
	}
}
