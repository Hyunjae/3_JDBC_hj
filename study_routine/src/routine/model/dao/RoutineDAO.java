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


	/** 루틴 기록 DAO
	 * @param conn
	 * @param routineNo
	 * @param string
	 * @param recordDate
	 * @return result
	 * @throws Exception
	 */
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


	/** 루틴 기록 수정 DAO
	 * @param conn
	 * @param string
	 * @param recordDate
	 * @return result
	 * @throws Exception
	 */
	public int updateRtRecord(Connection conn, int routineNo, String string, String recordDate) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("updateRtRecord");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, string);
			pstmt.setString(2, recordDate);
			pstmt.setInt(3, routineNo);
			
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
	public int insertRt(Connection conn, String routineName, int studentNo) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, routineName);
			pstmt.setInt(2, studentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** 루틴 수정 DAO
	 * @param conn
	 * @param rtNo
	 * @param studentNo
	 * @return result
	 * @throws Exception
	 */
	public int updateRt(Connection conn, int rtNo, String rtName) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rtName);
			pstmt.setInt(2, rtNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	

	/** 루틴 삭제 DAO
	 * @param conn
	 * @param rtNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteRt(Connection conn, int rtNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rtNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 한 달 기록 조회 DAO
	 * @param conn
	 * @param month
	 * @param studentNo
	 * @return monthlyRt
	 * @throws Exception
	 */
	public List<Map<String, String>> monthlyRt(Connection conn, int month, int studentNo)
	throws Exception{
		
		List<Map<String, String>> monthlyRt = new ArrayList<Map<String, String>>();
		
		try {
			String sql = prop.getProperty("monthlyRt");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentNo);
			pstmt.setInt(2, month);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new LinkedHashMap<String, String>();
  
				map.put("루틴이름", rs.getString("RT_NAME")); // 문자열로 만들기 위해 ""붙임
				
				for(int i=1; i<=31; i++) {
		               String col = i < 10 ? "0"+i : ""+i;
		               
		               String temp = "\'" + col + "\'";
		               
		               map.put(col, rs.getString(temp));
		            }
				monthlyRt.add(map);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return monthlyRt;
	}




	
	
}
