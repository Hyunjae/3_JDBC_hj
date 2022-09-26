package routine.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import routine.model.vo.Routine;

public class RoutineDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	
	public RoutineDAO() {
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("routine-query.xml"));			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	/** 루틴 리스트 조회 DAO
	 * @param conn
	 * @return routineList
	 * @throws Exception
	 */
	public List<Routine> selectAllRt(Connection conn, int studentNo) throws Exception{
		
		List<Routine> routineList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAllRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	
				Routine rt = new Routine();
				rt.setRoutineName(rs.getString("RT_NAME"));
				rt.setRoutineNo(rs.getInt("RT_NO"));
				
				routineList.add(rt);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return routineList;
	}


	public int recordRt(Connection conn, int routineNo, String string, String recordDate) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("recordRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, routineNo);
			pstmt.setString(2, string);
			pstmt.setString(3, recordDate);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 루틴 추가 DAO
	 * @param conn
	 * @param studentNo
	 * @return result
	 * @throws Exception
	 */
	public int updateRt(Connection conn, String routineName, int studentNo) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, routineName);
			pstmt.setInt(2, studentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	public List<Map<String, String>> monthlyRt(Connection conn, int month, int studentNo)
	throws Exception{
		
		List<Map<String, String>> monthlyRt = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAllRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentNo);
			pstmt.setInt(2, month);
			
			rs = pstmt.executeQuery();
			
			Map<String, String> map = new LinkedHashMap<>();
			while(rs.next()) {
				map.put("rtNo", rs.getInt("RT_NO")+"");  // 문자열로 만들기 위해 ""붙임
				map.put("rtName", rs.getString("RT_NAME"));
				
				for(int i=1; i<=31; i++) {
					String col = i < 10 ? "0"+i : ""+i;
					map.put(col, rs.getString(col));
				}
				monthlyRt.add(map);
			}
		} catch(Exception e) {
			close(rs);
			close(pstmt);
		}
		return monthlyRt;
	}

	
	
}
