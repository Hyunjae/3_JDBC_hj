package score.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import score.vo.Score;

public class ScoreDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public ScoreDAO() {
		
		try {
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("score-query.xml"));			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/** 성적입력 DAO
	 * @param conn
	 * @param score
	 * @return result
	 * @throws Exception
	 */
	public int updateScore(Connection conn, Score score) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateScore");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, score.getTestDate());
			pstmt.setInt(2, score.getTestScore());
			pstmt.setInt(3, score.getStudentNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 성적조회 DAO
	 * @param conn
	 * @param studentNo
	 * @return allScore
	 * @throws Exception
	 */
	public List<Score> selectScore(Connection conn, int studentNo) throws Exception {
		
		List<Score> allScore = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectScore");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, studentNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Score score = new Score();
				score.setTestDate(rs.getString("TEST_DATE"));
				score.setTestScore(rs.getInt("TEST_SCORE"));
				
				allScore.add(score);
			}
		} finally {
			close(pstmt);
		}
		return allScore;
	}
}
