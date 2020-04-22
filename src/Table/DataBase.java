package Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {

	private Connection conn = null;
	private String url="jdbc:mysql://117.17.113.248:3306/restaurant";
	private String uid="guest";
	private String upass="123456";
	private Statement st;
	private ArrayList<Table> tableList = new ArrayList<Table>();
	private String masterPass;
	
	public DataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("mysql : ����̺� �����");
			
			conn = DriverManager.getConnection(url, uid, upass);
			System.out.println("mysql : ����̺꿡 DB ������");
			
			String sql1 = "SELECT * FROM mytable";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			
			while (rs.next()) {
				int tableNumber = rs.getInt("tableNumber");
				String customers = rs.getString("customers");
				int costTotal = rs.getInt("costTotal");
				tableList.add(new Table(tableNumber, customers, costTotal));
			}
			System.out.println("mysql : ���̺� ���� �Ϸ�");
			
			
			
			PreparedStatement pstmt = null;
			
			for (Table table : tableList) {
				String sql2 = "select * from tableorder where tableNumber=" + table.getTableNumber();
				rs = st.executeQuery(sql2);
				ArrayList<Order> orderList = new ArrayList<Order>();
				int total = 0;
				while (rs.next()) {
					total += rs.getInt("orderTotal");
					orderList.add(new Order(rs.getInt("orderNumber"), rs.getInt("tableNumber"), rs.getString("orderName"), rs.getInt("orderCost"), rs.getInt("orderCount"), rs.getInt("orderTotal")));
				}
				
				table.setCostTotal(total);
				table.setOrderList(orderList);
				
			}
			System.out.println("mysql : ���̺� ��������Ʈ ���� �Ϸ�");
			
			
			
			String sql3 = "SELECT * FROM masterid WHERE userName = '������'";
			st = conn.createStatement();
			ResultSet rs3 = st.executeQuery(sql3);
			
			while (rs3.next()) {
				masterPass = rs3.getString("pass");
			}
			System.out.println("mysql : ������ �н����� �ҷ����� �Ϸ�");
			
			
			st.close();
			rs.close();
			
			
			
			
		} catch (Exception e) {
			System.out.println("mysql : �ҷ����� ���� �߻�");
		}
	}

	public void renewal() {
		try {
			
			System.out.println("mysql : ���̺� ���� ����");
			
			tableList.clear();
			
			String sql1 = "SELECT * FROM mytable";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			
			System.out.println("mysql : ���̺� ����Ʈ ���� ����");
			
			while (rs.next()) {
				int tableNumber = rs.getInt("tableNumber");
				String customers = rs.getString("customers");
				int costTotal = rs.getInt("costTotal");
				tableList.add(new Table(tableNumber, customers, costTotal));
			}
			
			System.out.println("mysql : ���̺� ����Ʈ ���� �Ϸ�");
			System.out.println("mysql : ���̺� ��������Ʈ ���� ����");
			
			PreparedStatement pstmt = null;
			
			for (Table table : tableList) {
				String sql2 = "select * from tableorder where tableNumber=" + table.getTableNumber();
				rs = st.executeQuery(sql2);
				ArrayList<Order> orderList = new ArrayList<Order>();
				int total = 0;
				while (rs.next()) {
					total += rs.getInt("orderTotal");
					orderList.add(new Order(rs.getInt("orderNumber"), rs.getInt("tableNumber"), rs.getString("orderName"), rs.getInt("orderCost"), rs.getInt("orderCount"), rs.getInt("orderTotal")));
				}
				
				
				table.setCostTotal(total);
				table.setOrderList(orderList);
			}
			System.out.println("mysql : ���̺� ��������Ʈ ���� �Ϸ�");
			
			
			String sql3 = "SELECT * FROM masterid WHERE userName = '������'";
			st = conn.createStatement();
			ResultSet rs3 = st.executeQuery(sql3);
			
			while (rs3.next()) {
				masterPass = rs3.getString("pass");
			}
			System.out.println("mysql : ������ �н����� ���� �Ϸ�");
			
			
			st.close();
			rs.close();
			System.out.println("mysql : ���̺� ���� �Ϸ�");
		} catch (Exception e) {
			System.out.println("mysql : ���̺� ���� ���� �߻�");
		}
		
	}
	
	
	public ArrayList<Table> getTableList() {
		renewal();
		return tableList;
	}
	
	public Table getTable(int index) {
		renewal();
		return tableList.get(index);
	}
	
	public String getPass() {
		return masterPass;
	}
	
	public void call(int tableNumber) {
		try {
			System.out.println("mysql : ������ ȣ�� ����");
			
			String sql1 = "UPDATE mytable SET ownerCall = 1 WHERE tableNumber = " + tableNumber;
			st = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
			
			System.out.println("mysql : ������ ȣ�� ����");
		} catch (Exception e) {
			System.out.println("mysql : ������ ȣ�� �����߻�");
		}
	}
}
