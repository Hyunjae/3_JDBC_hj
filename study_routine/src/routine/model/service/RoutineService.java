package routine.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import routine.model.dao.RoutineDAO;
import routine.model.vo.Routine;

public class RoutineService {

	private RoutineDAO dao = new RoutineDAO();

	/** 1. 루틴 리스트 조회 service
	 * @return routineList
	 * @throws Exception
	 */
	public List<Routine> selectAllRt(int studentNo) throws Exception {
		
		Connection conn = getConnection();
		
		List<Routine> routineList = dao.selectAllRt(conn, studentNo);
		
		close(conn);
		
		return routineList;
	}

	/** 하루 기록 입력 service
	 * @param routineList
	 * @param oxList
	 * @throws Exception
	 */
	public int recordRt(List<Routine> routineList, List<String> oxList, 
			String recordDate) throws Exception {
		
		Connection conn = getConnection();
		
		int result = 0;
		for(int i=0; i<routineList.size() ; i++) {
			result += dao.recordRt(conn, routineList.get(i).getRoutineNo(), 
					oxList.get(i), recordDate);
		}
		
		if(result == routineList.size()) commit(conn);
		else							 rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 루틴 기록 수정 service
	 * @param routineList
	 * @param oxList
	 * @param recordDate
	 * @return result
	 * @throws Exception
	 */
	public int updateRtRecord(List<Routine> routineList, List<String> oxList, String recordDate)
	throws Exception{
		
		Connection conn = getConnection();
		
		int result = 0;
		for(int i=0; i<routineList.size() ; i++) {
			result += dao.updateRtRecord(conn, routineList.get(i).getRoutineNo(),
										oxList.get(i), recordDate);
		}
		
		if(result == routineList.size()) commit(conn);
		else							 rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 루틴 추가 service
	 * @param studentNo
	 * @return result
	 * @throws Exception
	 */
	public int insertRt(String routineName, int studentNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.insertRt(conn, routineName, studentNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}
	

	/** 루틴 수정 service
	 * @param rtNo
	 * @param studentNo
	 * @return result
	 * @throws Exception
	 */
	public int updateRt(int rtNo, String rtName) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateRt(conn, rtNo, rtName);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 루틴 삭제 service
	 * @param rtNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteRt(int rtNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.deleteRt(conn, rtNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	/** 한 달 기록 조회 service
	 * @param month
	 * @param studentNo
	 * @return monthlyRt
	 * @throws Exception
	 */
	public List<Map<String, String>> monthlyRt(int month, int studentNo)
	throws Exception{
		
		Connection conn = getConnection();
		
		List<Map<String, String>> monthlyRt = dao.monthlyRt(conn, month, studentNo);
		
		close(conn);
		
		return monthlyRt;
	}

	


}
