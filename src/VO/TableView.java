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
			
			setTitle(admin.getTable(index).getTableNumber() + "�� ���̺�");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setResizable(false);
			
			
			allPanel = new JPanel();
			allPanel.setLayout(null);
			allPanel.setBackground(Color.white);
			allPanel.setBounds(0, 0, 400, 600);
			
			
			back = new JButton("Ÿ��Ʋ��");
			back.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int result = JOptionPane.showConfirmDialog(null, "����ȭ������ �̵��ұ��?", "�̵�", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							String answer = JOptionPane.showInputDialog("�ý��� ��й�ȣ �Է�");
							System.out.println(sv.getDataBase().getPass());
							System.out.println(answer);
							if (answer.equals(sv.getDataBase().getPass())) {
								tv.dispose();
								sv.setVisible(true);
							}
							else {
								JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "���� �߻�", "����", JOptionPane.ERROR_MESSAGE);
					}
					
					
				}
			});
			back.setBounds(10, 10, 100, 30);
			back.setBackground(Color.white);
			back.setFont(new Font(getName(), 1, 16));
			
			
			call = new JButton("���� ȣ��");
			call.setBounds(270, 10, 110, 30);
			call.setBackground(Color.white);
			call.setFont(new Font(getName(), 1, 16));
			
			call.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int result = JOptionPane.showConfirmDialog(null, "�������� ȣ���Ͻðڽ��ϱ�?", "ȣ��", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							admin.call(admin.getTable(index).getTableNumber());
							JOptionPane.showMessageDialog(null, "ȣ���߽��ϴ�.", "ȣ��", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e2) {
						System.out.println("������ ȣ�� �޼ҵ� ���� �߻�");
					}
					
				}
			});
			
			nameLabel = new JLabel(admin.getTable(index).getCustomers() + " ����");
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
						int result = JOptionPane.showConfirmDialog(null, "����ȭ������ �̵��ұ��?", "�̵�", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							String answer = JOptionPane.showInputDialog("�ý��� ��й�ȣ �Է�");
							System.out.println(sv.getDataBase().getPass());
							System.out.println(answer);
							if (answer.equals(sv.getDataBase().getPass())) {
								sv.setVisible(true);
								tv.dispose();
								
							}
							else {
								JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "���� �߻�", "����", JOptionPane.ERROR_MESSAGE);
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
			System.out.println("����");
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
			
			nameLabel.setText(admin.getTable(index).getCustomers() + " ����");
			
			for (Order order : admin.getTable(index).getOrderList()) {
				menuPanel.add(new JPanel());
				menuName.add(new JLabel(order.getMenuName()));
				menuCount.add(new JLabel("�ֹ� ���� : " + order.getOrderCount() + "��"));
				menuCost.add(new JLabel("�� ���� : " + order.getOrderTotal() + "��"));
				
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
			System.out.println("ũ������Ʈ ���̺� ���� �߻�");
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
			System.out.println("���ΰ�ħ ������ �۵�\n");
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
					System.out.println("���ΰ�ħ ������ �۵���\n");
				}
				JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.\n����ȭ������ ���ư��ϴ�.", "�����Ϸ�", JOptionPane.INFORMATION_MESSAGE);
				tv.back(sv);
			} catch (Exception e) {
				System.out.println("������ ���� �߻�");
			}
		}
		
		

	}
}
