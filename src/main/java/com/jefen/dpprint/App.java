package com.jefen.dpprint;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jefen.dpprint.view.BasePrint;

public class App extends JFrame 
	implements ActionListener, /*CaretListener,*/ FocusListener, DocumentListener, KeyListener {
	JPanel panel1, panel2, panel3;
	JTextField skuInputText;
	JComboBox<Object> jianComboBox;
	String[] jianList = {"检01", "检02", "检03", "检04"};
	JButton button1, button2, button3, button4;
	String skuText = "不包含颜色尺码！";
	
	String driverName = "net.sourceforge.jtds.jdbc.Driver";
	String dbURL = "jdbc:jtds:sqlserver://tg.jefen.com:4321;DatabaseName=DpPrint";
	String userName = "sa";
	String userPwd = "NewSql2014-11-20";
	Connection dbConn;
	
	public App() {
		super("Jefen吊牌打印程序");
		skuInputText = new JTextField(skuText);
		jianComboBox = new JComboBox<Object>(jianList);
		button1 = new JButton("设置打印数量");
		button1.addActionListener(this);
		button2 = new JButton("查看打印数量");
		button3 = new JButton("创建修改模板");
		button4 = new JButton("模板文件维护");
//		button4.addActionListener(this);
//		panel1 = new JPanel();
//		panel1.add(skuInputText);
//		skuInputText.setBounds(0, 0, 200, 40);
		skuInputText.setForeground(Color.red);
		skuInputText.setFont(new Font("黑体", Font.BOLD, 30));
//		skuInputText.addCaretListener(this);
//		skuInputText.addFocusListener(this);
//		skuInputText.getDocument().addDocumentListener(this);
		skuInputText.addKeyListener(this);
//		panel1.setBounds(0, 0, 200, 40);
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 3));
		panel2.add(jianComboBox);
		panel2.add(button1);
		
		panel2.add(button2);
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 3));
		panel3.add(new JLabel(""));
		panel3.add(button3);
		panel3.add(button4);
		
		this.setLayout(new BorderLayout());
		this.add(skuInputText, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(panel3, BorderLayout.SOUTH);
		
		this.setBounds(200, 200, 400, 200);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			Class.forName(driverName);
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
		    System.out.println("连接数据库成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new App();
	}
	
	// 创建或修改模板窗体
	// 根据模板ID显示对应打印窗体
	private void setPrintCount(String styleId) {
		Map tempID = new HashMap();
		try {
			tempID = isExistStyleID(styleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!((Boolean) tempID.get("exist"))) {
			System.out.println("您输入的款号[" + styleId + "]不存在，请检查！");
		} else if (tempID.get("tempId") == null) {
			System.out.println("您输入的款号[" + styleId + "]未建立模板,请与信息部联系！");
		} else {
			String tempid = tempID.get("tempId").toString().substring(0, 3);
			if (tempid.equals("1.0"))
                print_1_0();
//			else if (tempid.equals("1.1"))
//                self.Print_1_1();
//			else if (tempid.equals("1.2"))
//                self.Print_1_2();
//            else if (tempid.equals("1.3"))
//                self.Print_1_3();
//            else if (tempid.equals("1.4"))
//                self.Print_1_4();
//            else if (tempid.equals("1.5"))
//                self.Print_1_5();
//            else if (tempid.equals("2.0"))
//                self.Print_2_0();
//            else if (tempid.equals("2.1"))
//                self.Print_2_1();
//            else if (tempid.equals("2.2"))
//                self.Print_2_2();
//            else if (tempid.equals("2.3"))
//                self.Print_2_3();
//            else if (tempid.equals("2.4"))
//                self.Print_2_4();
//            else if (tempid.equals("2.5"))
//                self.Print_2_5();
            else
            	System.out.println("您输入的款号[" + styleId + "]没有模板编号，请与信息部联系");
		}
	}
	
	// 无国标码[标准]
	private void print_1_0() {
		// TODO Auto-generated method stub
		BasePrint bpFrame = new BasePrint();
		bpFrame.setVisible(true);
		String jian = (String) jianComboBox.getSelectedItem();
		try {
			initStyleData(skuInputText.getText(), jian);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
	
	
	
	// 初始化款式数据
	private void initStyleData(String styleId, String jian) throws SQLException {
		// 获取颜色数据
		List<String> styleColorList = getStyleColor(styleId);
		for (String colorId : styleColorList) {
			getStyleSize(styleId, colorId);
		}
	}

	// 判断款号是否存在，如果存在于模板表则返回模板ID
	private Map isExistStyleID(String styleId) throws SQLException {
		Map re = new HashMap();
		PreparedStatement pStmt;
		// 先读取模板表
		String tempStr = "Select TempID From Template Where StyleID = ?";
		pStmt = dbConn.prepareStatement(tempStr);
		pStmt.setString(1, styleId);
		ResultSet rs = pStmt.executeQuery();
		if (rs.next()) {
//			System.out.println(rs.getString("TempID"));
			re.put("exist", true);
			re.put("tempId", rs.getObject(1));
			re.put("sizeId", "0M");
//				System.out.print(rs.getString("StyleID") + ",");
//				System.out.println(rs.getString("sizeid"));
			return re;
		}
		
		// 读取F22库
		tempStr = "Select StyleID, sizeid From jf.F22E.Dbo.j_Style A Left Join jf.F22E.Dbo.j_sizegrpsubs B "
                + "On A.sizegrpid = B.sizegrpid Where Styleid = ?";
		pStmt = dbConn.prepareStatement(tempStr);
		pStmt.setString(1, styleId);
		rs = pStmt.executeQuery();
		if (rs.next()) {
			re.put("exist", true);
			re.put("tempId", null);
			re.put("sizeId", rs.getObject(1));
		} else {
			re.put("exist", false);
			re.put("tempId", null);
		}
		return re;
	}
	
	// 返回款式颜色
	public List<String> getStyleColor(String styleId) throws SQLException {
		List<String> re = new ArrayList<String>();
		PreparedStatement pStmt;
		String sql = "SELECT ColorID FROM Template Where StyleID=? Group By ColorID Order By ColorID";
		pStmt = dbConn.prepareStatement(sql);
		pStmt.setString(1, styleId);
		ResultSet rs = pStmt.executeQuery();
		while (rs.next()) {
			re.add(rs.getString("ColorID"));
		}
		return re;
	}
	
	// 根据款式颜色返回尺码信息
	public void getStyleSize(String styleId, String colorId) throws SQLException {
		PreparedStatement pStmt;
		String sql = "Select a.*, IsNull(D.SL,0)SL, D.IP "
				+ "From Template as a "
				+ "INNER JOIN Size as b On a.SizeName = b.S_Name " 
				+ "Left Join SizeSub as C on b.S_ID = c.S_ID AND a.Size = c.Size_Name " 
				+ "Left Join DpPrint D On A.ClothingID = D.ClothingID AND D.IP = "
				+ "(Select client_net_address IP from sys.dm_exec_connections Where session_id = @@SPID)" 
				+ "Where a.StyleID = ? AND a.ColorID = ? Order By c.S_Order";
		pStmt = dbConn.prepareStatement(sql);
		pStmt.setString(1, styleId);
		pStmt.setString(2, colorId);
		ResultSet rs = pStmt.executeQuery();
		int rowCount = 1;
		while (rs.next()) {
			rs.getString(rowCount);
			rowCount++; 
		}
//		// 取到字段
//		Fields = [Field[0] for Field in self.cur.description]
//		
//		// 处理数据
//		RowList = []
//		for row in self.cur: # 此方法可以不用使用 self.cur.fetchall() 读取数据
//		    TempDict = {}
//		    for index,Field in enumerate(Fields):
//		        if Field in ("Brand","P_Name","DJ","LeiBie","Other1","Other2","Other3","Other4","Other5","Safe1","Safe2"):
//		            Val = zh_de(row[index])
//		        else:
//		            Val = row[index]
//		        TempDict[Field.__str__()] = Val
//		    RowList.append(TempDict)
//		
//		return RowList;
	}

//	public void caretUpdate(CaretEvent e) {
//		if (e.getSource() == skuInputText) {
//			Runnable clear = new Runnable() {
//				public void run() {
//					skuInputText.setText(null);
////					skuInputText.setText(skuInputText.getText().toUpperCase());
//				}
//			};
//			SwingUtilities.invokeLater(clear);
//		}
//	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getSource());
	}

	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(e.getSource());
	}

	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
//		skuInputText.setText(null);
//		skuInputText.setText(skuInputText.getText().toUpperCase());
		Runnable clear = new Runnable() {
			public void run() {
//				skuInputText.setText(null);
				skuInputText.setText(skuInputText.getText().toUpperCase());
			}
		};
		SwingUtilities.invokeLater(clear);
	}

	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button4)
			skuInputText.setText(null);
		if (e.getSource() == button1) {
			setPrintCount(skuInputText.getText());
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
//		skuInputText.setText(skuInputText.getText().toUpperCase());
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		int t = e.getKeyCode();
//		skuInputText.setText(skuInputText.getText().toUpperCase());
		if (skuInputText != null && skuInputText.getText().equals(skuText))
			skuInputText.setText(null);
	}

	public void keyReleased(KeyEvent e) {
		if (skuInputText == null || skuInputText.getText().equals(""))
			skuInputText.setText(skuText);
		skuInputText.setText(skuInputText.getText().toUpperCase());
	}
}
