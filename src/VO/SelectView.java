package VO;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MyClass.MyButton;
import Table.DataBase;
import Table.Table;
import VO.TableView.restartThread;

public class SelectView extends JFrame{
	
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();
	private ArrayList<JLabel> labelList = new ArrayList<JLabel>();
	private ArrayList<MyButton> buttonList = new ArrayList<MyButton>();
	private restartThread rt;
	private JPanel allPanel;
	private SelectView sv;
	private DataBase admin;
	
	public SelectView() {
		sv = this;
		admin = new DataBase();
		setTitle("번호 입력");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(230, 50, 899, 400);
		
		allPanel = new JPanel();
		allPanel.setLayout(new GridLayout(2, 3, 2, 2));
		allPanel.setBackground(Color.black);
		int i = 0;
		for (Table table : admin.getTableList()) {
			panelList.add(new JPanel());
			labelList.add(new JLabel("테이블 번호 : " + table.getTableNumber()));
			buttonList.add(new MyButton("상세보기", i));
			buttonList.get(i).setBackground(Color.white);
			buttonList.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MyButton btn = (MyButton) e.getSource();
						int index = btn.getIndex();
						int result = JOptionPane.showConfirmDialog(null, (index + 1) + "번 테이블로 이동할까요?", "이동", JOptionPane.YES_NO_OPTION);

						if (result == JOptionPane.YES_OPTION) {
							String answer = JOptionPane.showInputDialog("시스템 비밀번호 입력");
							if (answer.equals(getDataBase().getPass())) {
								if (admin.getTable(index).getCustomers() != null) {
									JOptionPane.showMessageDialog(null, "이동합니다", "이동", JOptionPane.INFORMATION_MESSAGE);
									TableView tv = new TableView(sv, index);
									sv.dispose();
								}
								else {
									JOptionPane.showMessageDialog(null, "이 테이블은 아무도 앉아있지 않습니다.", "금지", JOptionPane.ERROR_MESSAGE);
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.", "오류", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "이동이 취소되었습니다.", "이동", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					 catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, "오류 발생", "오류", JOptionPane.ERROR_MESSAGE);
					}
				}	
			});
			
			panelList.get(i).setLayout(null);
			panelList.get(i).setBounds(0, 0, 300, 200);
			labelList.get(i).setBounds(70, 40, 200, 25);
			buttonList.get(i).setBounds(75, 80, 150, 50);
			
			panelList.get(i).setBackground(new Color(250,250,250));
			labelList.get(i).setFont(new Font("", Font.PLAIN, 25));
			buttonList.get(i).setFont(new Font("", Font.PLAIN, 25));
			
			panelList.get(i).add(labelList.get(i));
			panelList.get(i).add(buttonList.get(i));
			allPanel.add(panelList.get(i));
			i++;
		}
		add(allPanel);
		
		
		setVisible(true);
		
	}
	
	public DataBase getDataBase(){
		admin.renewal();
		return admin;
	}
	
}
