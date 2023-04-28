package kr.bit.model;
//JDBC -> mybatis, JPA
import java.sql.*;
import java.util.ArrayList;


public class MemberDAO {
	private Connection connection; //db에서 가장 기본적으로 필요한 연결 객체이다.
	private PreparedStatement preparedstatement;
	private ResultSet resultset;
	
	//데이터베이스 연결객체 생성
	public void getConnect() {
		String URL = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&severTimeZone=UTC";
		String user ="root";
		String password="admin12345";
		//MySQL Driver Loading
		try {
			//동적로딩 : 실행시점에서 객체를 생성하는 방법),(드라이버를 메모리에 올리는 방법)
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(URL, user, password);//접속 시도하는 클래스, 연결정보를 넘겨준다.
		//DriverManager 관리하는 클래스
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int memberInsert(MemberVo vo) {
		String SQL="insert into member(id, pass, name, age, email, phone) values(?,?,?,?,?,?)";//?(파라메터) 1,2,3,4,5,6
		getConnect();
		//SQL문장을 전송하는 객체 생성
		int count = -1;
		try {
			preparedstatement=connection.prepareStatement(SQL);//미리 컴파일을 시킨다. db로 보낸다.(속도가 빠르기)
			preparedstatement.setString(1, vo.getId());
			preparedstatement.setString(2, vo.getPass());
			preparedstatement.setString(3, vo.getName());
			preparedstatement.setInt(4, vo.getAge());
			preparedstatement.setString(5, vo.getEmail());
			preparedstatement.setString(6, vo.getPhone());
			
			count=preparedstatement.executeUpdate();//전송(실행)
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return count;//1 or 0
	}
	//회원(VO)전체 리스트(ArrayList) 가져오기
	public ArrayList memberList() { 
		String SQL = "select * from member";
		getConnect();
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		try {
			preparedstatement = connection.prepareStatement(SQL);
			resultset =preparedstatement.executeQuery();
			System.out.println(resultset.toString()); // resultset -> 커서(결과의 집합을 갖고있다.)
			while(resultset.next()) {
				int num = resultset.getInt("num");
				String id = resultset.getString("id");
				String pass = resultset.getString("pass");
				String name = resultset.getString("name");
				int age = resultset.getInt("age");
				String email = resultset.getString("email");
				String phone = resultset.getString("phone");
				MemberVo vo = new MemberVo(num, id, pass, name, age, email, phone);
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return list;
		
	}
	
	public int memberDelete(int num) {
		String SQL ="delete from member where num=?";
		getConnect();
		int count = -1;
		try {
			preparedstatement=connection.prepareStatement(SQL);
			preparedstatement.setInt(1, num);
			count =preparedstatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return count;
	}
	
	public MemberVo memberContent(int num) {
		String SQL = "select * from member where num =?";
		getConnect();
		MemberVo vo = null;
		
		try {
			preparedstatement=connection.prepareStatement(SQL);
			preparedstatement.setInt(1, num);
			resultset=preparedstatement.executeQuery();
			if(resultset.next()) {
				//회원 한명의 정보를 가져와서-> 묶고(MemberVo)
				num = resultset.getInt("num");
				String id =  resultset.getString("id");
				String pass = resultset.getString("pass");
				String name = resultset.getString("name");
				int age = resultset.getInt("age");
				String email = resultset.getString("email");
				String phone = resultset.getString("phone");
				vo= new MemberVo(num, id, pass, name, age, email, phone);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return vo;
	}
	
	public int memberUpdate(MemberVo vo) {
		String SQL = "update member set age=?, email=?, phone=? where num=?";
		getConnect();
		int count =-1;
		try {
			preparedstatement=connection.prepareStatement(SQL);
			preparedstatement.setInt(1, vo.getAge());
			preparedstatement.setString(2, vo.getEmail());
			preparedstatement.setString(3, vo.getPhone());
			preparedstatement.setInt(4, vo.getNum());
			count =preparedstatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbClose();
		}
		return count;
		
	}
	
	//데이터베이스 연결 끊기
	public void dbClose() {
		try {
			if(resultset != null) resultset.close();
			if(preparedstatement != null)preparedstatement.close();
			if(connection != null)connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
		

}
