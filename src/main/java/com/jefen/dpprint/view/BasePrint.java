package com.jefen.dpprint.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class BasePrint extends JFrame {
	JButton saveCountBtn;
	JPanel panel1, panel2;

	public BasePrint(Map<Object, Object> dpData) {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// double width = Toolkit.getDefaultToolkit().getScreenSize().width;
		// double height = Toolkit.getDefaultToolkit().getScreenSize().height;
		// this.setSize((int) width, (int) height);
		Container con = this.getContentPane();
		// con.setBackground(Color.cyan);
		con.setLayout(new BorderLayout());
		saveCountBtn = new JButton("保存打印数量");
		panel2 = new JPanel();
		panel2.setBackground(new Color(46, 139, 87));
		panel2.setLayout(null);
		panel1 = new PicPanel(dpData);
		panel2.add(panel1);
		con.add(saveCountBtn, BorderLayout.NORTH);
		con.add(new JScrollPane(panel2), BorderLayout.CENTER);
		this.setSize(550, 400);
		this.setLocation(0, 0);
		// this.setResizable(false);
	}

}

class PicPanel extends JPanel {
	final static int WIDTH = 190;
	final static int HEIGHT = 430;
	final static int OVAL_RADIUS = 9;
	JTextField countTextField;
	Map<Object, Object> dpData;
	JLabel colorAndSizeLabel;
	JLabel standardLabel, productNameLabel, typeLabel, brandLabel, levelLabel, productPlaceLabel, jianLabel,
			skillTypeLabel, priceLabel, skuLabel;

	public PicPanel(Map<Object, Object> dpData) {
		this.dpData = dpData;
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBounds(0, 0, WIDTH, HEIGHT);
		// 数量
		countTextField = new JTextField(5);
		countTextField.setText("0");
		countTextField.setBounds(6, 20, 40, 18);
		this.add(countTextField);
		// 颜色、尺码
		colorAndSizeLabel = new JLabel("1P:0S");
		colorAndSizeLabel.setForeground(new Color(46, 139, 87));
		colorAndSizeLabel.setBounds(7, 8, 33, 10);
		this.add(colorAndSizeLabel);
		// 字体
		Font labelFont = new Font("宋体", Font.PLAIN, 12);
		int lineHeight = 23, initY = 98;
		// 标准
		standardLabel = new JLabel("标准：");
		standardLabel.setBounds(10, initY, 40, 40);
		standardLabel.setFont(labelFont);
		this.add(standardLabel);
		// 品名
		productNameLabel = new JLabel("品名：");
		productNameLabel.setBounds(10, initY + lineHeight, 40, 40);
		productNameLabel.setFont(labelFont);
		this.add(productNameLabel);
		// 号型
		typeLabel = new JLabel("号型：");
		typeLabel.setBounds(10, initY + lineHeight * 2, 40, 40);
		typeLabel.setFont(labelFont);
		this.add(typeLabel);
		// 商标
		brandLabel = new JLabel("商标：");
		brandLabel.setBounds(10, initY + lineHeight * 3, 40, 40);
		brandLabel.setFont(labelFont);
		this.add(brandLabel);
		// 等级
		levelLabel = new JLabel("等级：");
		levelLabel.setBounds(10, initY + lineHeight * 4, 40, 40);
		levelLabel.setFont(labelFont);
		this.add(levelLabel);
		// 产地
		productPlaceLabel = new JLabel("产地：");
		productPlaceLabel.setBounds(10, initY + lineHeight * 5, 40, 40);
		productPlaceLabel.setFont(labelFont);
		this.add(productPlaceLabel);
		// 检验
		jianLabel = new JLabel("检验：");
		jianLabel.setBounds(10, initY + lineHeight * 6, 40, 40);
		jianLabel.setFont(labelFont);
		this.add(jianLabel);
		// 安全技术类别
		skillTypeLabel = new JLabel("安全技术类别：");
		skillTypeLabel.setBounds(10, initY + lineHeight * 7, 40, 40);
		skillTypeLabel.setFont(labelFont);
		this.add(skillTypeLabel);
		// 价格
		priceLabel = new JLabel("价格：");
		priceLabel.setBounds(10, initY + lineHeight * 8, 40, 40);
		priceLabel.setFont(labelFont);
		this.add(priceLabel);
		// 款号
		skuLabel = new JLabel("款号：");
		skuLabel.setBounds(10, initY + lineHeight * 9, 40, 40);
		skuLabel.setFont(labelFont);
		this.add(skuLabel);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		// 消除线条锯齿
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 圆
		g2d.setColor(new Color(51, 130, 8));
		int diameter = OVAL_RADIUS * 2;
		g2d.fillOval(WIDTH / 2 - OVAL_RADIUS, 10, diameter, diameter);

		for (Map.Entry entry : dpData.entrySet()) {
			entry.getKey();
			entry.getValue();
		}
		// 虚线
		Stroke st = g2d.getStroke();
		Stroke bs;
		bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 16, 4 }, 0);
		g2d.setStroke(bs);
		g2d.drawLine(0, 360, WIDTH, 360);
		g2d.setStroke(st);

		//
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode("J313DS4203041P0S", // (String)
																		// dpData.get("ClothingID"),
					BarcodeFormat.CODE_128, 150, 20, hints);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage bufImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		g2d.drawImage(bufImage, null, 0, 365);

		// g.drawString("测试", 0, 80);

	}
}