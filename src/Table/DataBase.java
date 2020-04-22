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
			System.out.println("mysql : 드라이브 적재됨");
			
			conn = DriverManager.getConnection(url, uid, upass);
			System.out.println("mysql : 드라이브에 DB 연동됨");
			
			String sql1 = "SELECT * FROM mytable";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			
			while (rs.next()) {
				int tableNumber = rs.getInt("tableNumber");
				String customers = rs.getString("customers");
				int costTotal = rs.getInt("costTotal");
				tableList.add(new Table(tableNumber, customers, costTotal));
			}
			System.out.println("mysql : 테이블 정리 완료");
			
			
			
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
			System.out.println("mysql : 테이블에 오더리스트 정리 완료");
			
			
			
			String sql3 = "SELECT * FROM masterid WHERE userName = '마스터'";
			st = conn.createStatement();
			ResultSet rs3 = st.executeQuery(sql3);
			
			while (rs3.next()) {
				masterPass = rs3.getString("pass");
			}
			System.out.println("mysql : 마스터 패스워드 불러오기 완료");
			
			
			st.close();
			rs.close();
			
			
			
			
		} catch (Exception e) {
			System.out.println("mysql : 불러오기 오류 발생");
		}
	}

	public void renewal() {
		try {
			
			System.out.println("mysql : 테이블 갱신 시작");
			
			tableList.clear();
			
			String sql1 = "SELECT * FROM mytable";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			
			System.out.println("mysql : 테이블 리스트 갱신 시작");
			
			while (rs.next()) {
				int tableNumber = rs.getInt("tableNumber");
				String customers = rs.getString("customers");
				int costTotal = rs.getInt("costTotal");
				tableList.add(new Table(tableNumber, customers, costTotal));
			}
			
			System.out.println("mysql : 테이블 리스트 갱신 완료");
			System.out.println("mysql : 테이블 오더리스트 갱신 시작");
			
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
			System.out.println("mysql : 테이블 오더리스트 갱신 완료");
			
			
			String sql3 = "SELECT * FROM masterid WHERE userName = '마스터'";
			st = conn.createStatement();
			ResultSet rs3 = st.executeQuery(sql3);
			
			while (rs3.next()) {
				masterPass = rs3.getString("pass");
			}
			System.out.println("mysql : 마스터 패스워드 갱신 완료");
			
			
			st.close();
			rs.close();
			System.out.println("mysql : 테이블 갱신 완료");
		} catch (Exception e) {
			System.out.println("mysql : 테이블 갱신 오류 발생");
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
			System.out.println("mysql : 종업원 호출 시작");
			
			String sql1 = "UPDATE mytable SET ownerCall = 1 WHERE tableNumber = " + tableNumber;
			st = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
			
			System.out.println("mysql : 종업원 호출 성공");
		} catch (Exception e) {
			System.out.println("mysql : 종업원 호출 오류발생");
		}
	}
}
