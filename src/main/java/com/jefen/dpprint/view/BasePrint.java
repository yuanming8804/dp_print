package com.jefen.dpprint.view;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class BasePrint extends JFrame {
	public BasePrint() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		double width = Toolkit.getDefaultToolkit().getScreenSize().width; // �õ���ǰ��Ļ�ֱ��ʵĸ�
//		double height = Toolkit.getDefaultToolkit().getScreenSize().height;// �õ���ǰ��Ļ�ֱ��ʵĿ�
//		this.setSize((int) width, (int) height);// ���ô�С
		this.getContentPane().setBackground(Color.cyan);
		this.setSize(550, 400);
		this.setLocation(0, 0); // ���ô��������ʾ
//		this.setResizable(false);// ������󻯰�ť
	}
}
