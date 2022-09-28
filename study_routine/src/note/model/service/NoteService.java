package note.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import note.model.dao.NoteDAO;
import note.vo.Note;

public class NoteService {

	private NoteDAO dao = new NoteDAO();

	/** 과목 전체 조회 service
	 * @return subjectList
	 * @throws Exception
	 */
	public List<Note> selectSubject() throws Exception {
		Connection conn = getConnection();
		
		List<Note> subjectList = dao.selectSubject(conn);
		
		close(conn);
		
		return subjectList;
	}

	/** 단원별 조회 service
	 * @param subjectNo
	 * @return chapterList
	 * @throws Exception
	 */
	public List<Note> selectChapter(int subjectNo) throws Exception {
		Connection conn = getConnection();
		
		List<Note> chapterList = dao.selectChapter(conn, subjectNo);
		
		close(conn);
		
		return chapterList;
	}

	/** 단원별 모든 필기 조회 service
	 * @param chapterId
	 * @return noteList
	 * @throws Exception
	 */
	public List<Note> selectAllNote(int chapterId) throws Exception {
		Connection conn = getConnection();
		
		List<Note> noteList = dao.selectAllNote(conn, chapterId);
		
		close(conn);
		
		return noteList;
	}

	/** 필기 검색 service
	 * @param condition
	 * @param query
	 * @return searchNote
	 * @throws Exception
	 */
	public List<Note> searchNote(int condition, String query) throws Exception {
		Connection conn = getConnection();
		
		List<Note> searchList = dao.searchNote(conn, condition, query);
		
		close(conn);
		
		return searchList;
	}

	/** 필기 삽입 service
	 * @param chapterId
	 * @param noteTitle
	 * @param noteContent
	 * @return result
	 * @throws Exception
	 */
	public int insertNote(int chapterId, String noteTitle, String noteContent) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.insertNote(conn, chapterId, noteTitle, noteContent);
		
		if(result>0) commit(conn);
		else		rollback(conn);
		
		close(conn);
		
		return result;
	}

}
