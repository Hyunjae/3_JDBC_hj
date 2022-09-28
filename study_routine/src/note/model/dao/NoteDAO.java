package note.model.dao;

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

import note.vo.Note;

public class NoteDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public NoteDAO() {
	
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("note-query.xml"));			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/** 과목 전체 조회 DAO
	 * @param conn
	 * @return subjectList
	 * @throws Exception
	 */
	public List<Note> selectSubject(Connection conn) throws Exception {
		List<Note> subjectList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectSubject");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Note subject = new Note();
				subject.setSubjectNo(rs.getInt("SUBJECT_NO"));
				subject.setSubjectName(rs.getString("SUBJECT_NM"));
			
				subjectList.add(subject);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return subjectList;
	}
 
	/** 단원 전체 조회 DAO
	 * @param conn
	 * @param subjectNo
	 * @return chapterList
	 * @throws Exception
	 */
	public List<Note> selectChapter(Connection conn, int subjectNo) throws Exception {
		List<Note> chapterList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectChapter");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, subjectNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Note chapter = new Note();
				
				chapter.setChapterId(rs.getInt("CH_ID"));
				chapter.setChapterNo(rs.getInt("CH_NO"));
				chapter.setChapterName(rs.getString("CH_NM"));
				
				chapterList.add(chapter);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return chapterList;
	}

	/** 단원별 모든 필기 조회 DAO
	 * @param conn
	 * @param chapterId
	 * @return noteList
	 * @throws Exception
	 */
	public List<Note> selectAllNote(Connection conn, int chapterId) throws Exception {
		List<Note> noteList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectAllNote");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chapterId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Note note = new Note();
				
				note.setNoteNo(rs.getInt("NOTE_NO"));
				note.setNoteTitle(rs.getString("NOTE_TITLE"));
				note.setNoteContent(rs.getString("NOTE_CONTENT"));
				
				noteList.add(note);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return noteList;
	}

	/** 필기 검색 DAO
	 * @param conn
	 * @param condition
	 * @param query
	 * @return searchList
	 * @throws Exception
	 */
	public List<Note> searchNote(Connection conn, int condition, String query) throws Exception {
		List<Note> searchList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchNote1") 
						+ prop.getProperty("searchNote2_" + condition)
						+ prop.getProperty("searchNote3");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, query);
			if(condition == 3) pstmt.setString(2, query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Note note = new Note();
				
				note.setSubjectNo(rs.getInt("SUBJECT_NO"));
				note.setSubjectName(rs.getString("SUBJECT_NM"));
				note.setChapterId(rs.getInt("CH_ID"));
				note.setChapterName(rs.getString("CH_NM"));
				note.setNoteTitle(rs.getString("NOTE_TITLE"));
				note.setNoteContent(rs.getString("NOTE_CONTENT"));
				
				searchList.add(note);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return searchList;
	}

	/** 필기 삽입 DAO
	 * @param conn
	 * @param chapterId
	 * @param noteTitle
	 * @param noteContent
	 * @return result
	 * @throws Exception
	 */
	public int insertNote(Connection conn, int chapterId,
			String noteTitle, String noteContent) throws Exception {
		
		int result=0;
		
		try {
			String sql = prop.getProperty("insertNote");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chapterId);
			pstmt.setString(2, noteTitle);
			pstmt.setString(3, noteContent);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
}
