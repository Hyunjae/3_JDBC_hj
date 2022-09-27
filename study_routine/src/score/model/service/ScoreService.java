package score.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import score.model.dao.ScoreDAO;
import score.vo.Score;

public class ScoreService {

	private ScoreDAO dao = new ScoreDAO();

	/** 성적입력 service
	 * @param score
	 * @return score
	 * @throws Exception
	 */
	public int updateScore(Score score) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateScore(conn, score);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 성적조회 service
	 * @param studentNo
	 * @return allScore
	 * @throws Exception
	 */
	public List<Score> selectScore(int studentNo) throws Exception {
		
		Connection conn = getConnection();
		
		List<Score> allScore = dao.selectScore(conn, studentNo);
		
		close(conn);
		
		return allScore;
	}
	
	
	
}
