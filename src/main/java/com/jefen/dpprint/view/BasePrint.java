package com.jefen.dpprint.view;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class BasePrint extends JFrame {
	public BasePrint() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		double width = Toolkit.getDefaultToolkit().getScreenSize().width; // 得到当前屏幕分辨率的高
//		double height = Toolkit.getDefaultToolkit().getScreenSize().height;// 得到当前屏幕分辨率的宽
//		this.setSize((int) width, (int) height);// 设置大小
		this.getContentPane().setBackground(Color.cyan);
		this.setSize(550, 400);
		this.setLocation(0, 0); // 设置窗体居中显示
//		this.setResizable(false);// 禁用最大化按钮
	}
}
