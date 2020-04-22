package VO;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Table.DataBase;
import Table.Order;
import Table.Table;

public class TableView extends JFrame{
	
	private JPanel allPanel;
	private JButton back;
	private JButton call;
	private int a = 0;
	
	private JLabel nameLabel;
	
	private ArrayList<JPanel> menuPanel = new ArrayList<JPanel>();
	private ArrayList<JLabel> menuName = new ArrayList<JLabel>();
	private ArrayList<JLabel> menuCount = new ArrayList<JLabel>();
	private ArrayList<JLabel> menuCost = new ArrayList<JLabel>();
	
	private TableView tv;
	private restartThread rt;
	//private Table table;
	private SelectView sv;
	private int index;
	private DataBase admin;
	
	
	public TableView(SelectView sv, int index) {
		try {
			tv = this;
			this.sv = sv;
			this.index = index;
			//this.table = sv.getDataBase().getTable(index);
			this.admin = sv.getDataBase();
			
			setTitle(admin.getTable(index).getTableNumber() + "번 테이블");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setResizable(false);
			
			
			allPanel = new JPanel();
			allPanel.setLayout(null);
			allPanel.setBackground(Color.white);
			allPanel.setBounds(0, 0, 400, 600);
			
			
			back = new JButton("타이틀로");
			back.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int result = JOptionPane.showConfirmDialog(null, "메인화면으로 이동할까요?", "이동", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							String answer = JOptionPane.showInputDialog("시스템 비밀번호 입력");
							System.out.println(sv.getDataBase().getPass());
							System.out.println(answer);
							if (answer.equals(sv.getDataBase().getPass())) {
								tv.dispose();
								sv.setVisible(true);
							}
							else {
								JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.", "오류", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "오류 발생", "오류", JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
			});
			back.setBounds(10, 10, 100, 30);
			back.setBackground(Color.white);
			back.setFont(new Font(getName(), 1, 16));
			
			
			call = new JButton("직원 호출");
			call.setBounds(270, 10, 110, 30);
			call.setBackground(Color.white);
			call.setFont(new Font(getName(), 1, 16));
			
			call.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int result = JOptionPane.showConfirmDialog(null, "종업원을 호출하시겠습니까?", "호출", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							admin.call(admin.getTable(index).getTableNumber());
							JOptionPane.showMessageDialog(null, "호출했습니다.", "호출", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e2) {
						System.out.println("종업원 호출 메소드 오류 발생");
					}
					
				}
			});
			
			nameLabel = new JLabel(admin.getTable(index).getCustomers() + " 고객님");
			nameLabel.setBounds(130, 10, 200, 30);
			nameLabel.setFont(new Font(getName(), 1, 20));
			
			allPanel.add(back);
			allPanel.add(call);
			allPanel.add(nameLabel);
			
			add(allPanel);
			setVisible(true);
			
			this.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowClosing(WindowEvent e) {
					try {
						int result = JOptionPane.showConfirmDialog(null, "메인화면으로 이동할까요?", "이동", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							String answer = JOptionPane.showInputDialog("시스템 비밀번호 입력");
							System.out.println(sv.getDataBase().getPass());
							System.out.println(answer);
							if (answer.equals(sv.getDataBase().getPass())) {
								sv.setVisible(true);
								tv.dispose();
								
							}
							else {
								JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.", "오류", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "오류 발생", "오류", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
			});

			rt = new restartThread(this);
			rt.start();
			
		} catch (Exception e) {
			System.out.println("오류");
		}
		
	}
	
	public void deleteMenuPanel() {
		
		admin.renewal();
		
		for (JPanel menu : menuPanel) {
			allPanel.remove(menu);
		}
		
		menuPanel.clear();
		menuName.clear();
		menuCount.clear();
		menuCost.clear();
		remove(allPanel);
	}
	
	public void showMenuPanel() {
		try {
			
			int menuY = 70;
			int i = 0;
			
			admin.renewal();
			
			nameLabel.setText(admin.getTable(index).getCustomers() + " 고객님");
			
			for (Order order : admin.getTable(index).getOrderList()) {
				menuPanel.add(new JPanel());
				menuName.add(new JLabel(order.getMenuName()));
				menuCount.add(new JLabel("주문 수량 : " + order.getOrderCount() + "개"));
				menuCost.add(new JLabel("총 가격 : " + order.getOrderTotal() + "원"));
				
				menuPanel.get(i).setLayout(null);
				menuPanel.get(i).setBounds(0, menuY, 400, 110);
				
				if (order.getMenuName().length() <= 3) {
					menuName.get(i).setBounds(160, 10, 80, 25);
				}
				else if (order.getMenuName().length() <= 5) {
					menuName.get(i).setBounds(135, 10, 130, 25);
				}
				else if (order.getMenuName().length() <= 8) {
					menuName.get(i).setBounds(100, 10, 200, 25);
				}
				else {
					menuName.get(i).setBounds(75, 10, 250, 25);
				}
				
				menuName.get(i).setFont(new Font(getName(), 1, 20));
				menuPanel.get(i).add(menuName.get(i));
				
				menuCount.get(i).setBounds(125, 45, 150, 25);
				menuCount.get(i).setFont(new Font(getName(), 1, 20));
				menuPanel.get(i).add(menuCount.get(i));
				
				menuCost.get(i).setBounds(125, 75, 150, 25);
				
				if (order.getOrderTotal() < 100000) {
					menuCost.get(i).setBounds(110, 75, 180, 25);
				}
				else if (order.getOrderTotal() < 1000000) {
					menuCost.get(i).setBounds(100, 75, 200, 25);
				}
				
				menuCost.get(i).setFont(new Font(getName(), 1, 20));
				menuPanel.get(i).add(menuCost.get(i));
				
				
				allPanel.add(menuPanel.get(i));
				
				menuY += 110;
				i++;
				
			}
			
			setBounds(getX(), getY(), 400, menuY + 70);
			add(allPanel);
			
		} catch (Exception e) {
			System.out.println("크리에이트 테이블 오류 발생");
		}
	}
	
	public void back(SelectView sv) {
		dispose();
		sv.setVisible(true);
	}
	
	public class restartThread extends Thread{
		private TableView tv;
		
		public restartThread(TableView tv) {
			this.tv = tv;
		}
		
		@Override
		public void run() {
			System.out.println("새로고침 쓰레드 작동\n");
			try {
				while (true) {
					
					if (admin.getTable(index).getCustomers() == null) {
						break;
					}
					
					tv.deleteMenuPanel();
					tv.showMenuPanel();
					tv.repaint();
					tv.revalidate();
					
					Thread.sleep(2000);
					System.out.println("새로고침 쓰레드 작동중\n");
				}
				JOptionPane.showMessageDialog(null, "결제되었습니다.\n메인화면으로 돌아갑니다.", "결제완료", JOptionPane.INFORMATION_MESSAGE);
				tv.back(sv);
			} catch (Exception e) {
				System.out.println("쓰레드 오류 발생");
			}
		}
		
		

	}
}
