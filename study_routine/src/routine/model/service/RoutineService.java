package routine.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import routine.model.dao.RoutineDAO;
import routine.model.vo.Routine;

public class RoutineService {

	private RoutineDAO rtDao = new RoutineDAO();

	public List<Routine> selectAllRt() throws Exception {
		
		Connection conn = getConnection();
		
		List<Routine> routineList = new ArrayList<>();
		
		routineList = rtDao.selectAllRt(conn);
		
		close(conn);
		
		return null;
	}


}
