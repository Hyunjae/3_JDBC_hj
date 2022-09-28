---------< 메인화면 >----------

-- STUDENT 테이블 생성
CREATE TABLE STUDENT (
	STUDENT_NO NUMBER PRIMARY KEY,
	STUDENT_ID VARCHAR2(30) NOT NULL,
	STUDENT_PW VARCHAR2(30) NOT NULL,
	STUDENT_NM VARCHAR2(30) NOT NULL,
	STUDENT_GENDER CHAR(1) CHECK(STUDENT_GENDER IN ('M','F')),
	STUDENT_PHONE VARCHAR2(20) NOT NULL,
	ENROLL_DATE DATE DEFAULT SYSDATE,
	SECESSION_FL CHAR(1) DEFAULT 'N' CHECK(SECESSION_FL IN ('Y','N'))
);

COMMENT ON COLUMN STUDENT.STUDENT_NO IS '회원번호';
COMMENT ON COLUMN STUDENT.STUDENT_ID IS '회원 아이디';
COMMENT ON COLUMN STUDENT.STUDENT_PW IS '회원 비밀번호';
COMMENT ON COLUMN STUDENT.STUDENT_NM IS '회원 이름';
COMMENT ON COLUMN STUDENT.STUDENT_GENDER IS '회원 성별';
COMMENT ON COLUMN STUDENT.STUDENT_PHONE IS '회원 전화번호';
COMMENT ON COLUMN STUDENT.ENROLL_DATE IS '회원 가입일';
COMMENT ON COLUMN STUDENT.SECESSION_FL IS '탈퇴여부(Y/N)';

-- SEQUENCE 생성
CREATE SEQUENCE SEQ_NO_STD NOCACHE;

-- STUDENT 샘플 데이터 삽입
INSERT INTO STUDENT VALUES(SEQ_NO_STD.NEXTVAL, 'std01', 'pass01', '김길동', 'M', '010-1234-1234', DEFAULT, DEFAULT);
INSERT INTO STUDENT VALUES(SEQ_NO_STD.NEXTVAL, 'std02', 'pass02', '이지인', 'F', '010-1357-7073', DEFAULT, DEFAULT);
INSERT INTO STUDENT VALUES(SEQ_NO_STD.NEXTVAL, 'std03', 'pass03', '박주은', 'F', '010-3490-4558', DEFAULT, DEFAULT);
INSERT INTO STUDENT VALUES(SEQ_NO_STD.NEXTVAL, 'std04', 'pass04', '서민준', 'M', '010-2050-4620', DEFAULT, DEFAULT);

COMMIT;

-- 로그인
SELECT STUDENT_NO, STUDENT_ID, STUDENT_NM, STUDENT_GENDER, STUDENT_PHONE,
	TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일" HH24:MI:SS') ENROLL_DATE
FROM STUDENT
WHERE STUDENT_ID = 'std01'
AND STUDENT_PW = 'pass01'
AND SECESSION_FL = 'N';

SELECT * FROM STUDENT;
-- 회원가입
INSERT INTO STUDENT 
VALUES(SEQ_NO_STD.NEXTVAL, 'std05', 'pass05', '진미정', 'F', '010-1111-7777', DEFAULT, DEFAULT);

-- 아이디 중복 검사
SELECT COUNT(*)
FROM STUDENT
WHERE STUDENT_ID = 'std01'
AND SECESSION_FL = 'N';

-- 아이디 찾기
SELECT STUDENT_ID
FROM STUDENT
WHERE STUDENT_NM = '김길동'
AND STUDENT_PHONE = '010-1234-1234';


---------<공부 루틴 만들기>-------------

-- 루틴 명단 생성
CREATE TABLE ROUTINE(
	RT_NO NUMBER PRIMARY KEY,
	RT_NAME VARCHAR2(50) NOT NULL,
	STUDENT_NO NUMBER REFERENCES STUDENT(STUDENT_NO) ON DELETE CASCADE
);

COMMENT ON COLUMN ROUTINE.RT_NO IS '루틴 번호';
COMMENT ON COLUMN ROUTINE.RT_NAME IS '루틴 이름';
COMMENT ON COLUMN ROUTINE.STUDENT_NO IS '회원 번호';

SELECT * FROM ROUTINE;

CREATE SEQUENCE SEQ_NO_RT NOCACHE;

INSERT INTO ROUTINE VALUES(SEQ_NO_RT.NEXTVAL, '복습하기', 1);
INSERT INTO ROUTINE VALUES(SEQ_NO_RT.NEXTVAL, '운동하기', 1);
INSERT INTO ROUTINE VALUES(SEQ_NO_RT.NEXTVAL, '6시 기상', 1);
INSERT INTO ROUTINE VALUES(SEQ_NO_RT.NEXTVAL, '아침 먹기', 2);
INSERT INTO ROUTINE VALUES(SEQ_NO_RT.NEXTVAL, '복습하기', 2);

SELECT RT_NO, RT_NAME, STUDENT_NO, STUDENT_NM
FROM ROUTINE
LEFT JOIN STUDENT USING(STUDENT_NO);

COMMIT;


--------<매일 기록 체크>-------------
-- 체크리스트 생성

--DROP TABLE CHECK_RT;

CREATE TABLE CHECK_RT(
	CHECK_NO NUMBER PRIMARY KEY,
	RT_NO NUMBER REFERENCES ROUTINE(RT_NO) ON DELETE CASCADE,
	CHECK_RT VARCHAR2(10) NOT NULL,
	CHECK_DATE DATE DEFAULT SYSDATE
);

COMMENT ON COLUMN CHECK_RT.CHECK_NO IS '체크 번호';
COMMENT ON COLUMN CHECK_RT.RT_NO IS '루틴 번호';
COMMENT ON COLUMN CHECK_RT.CHECK_RT IS '루틴 체크';
COMMENT ON COLUMN CHECK_RT.CHECK_DATE IS '기록 날짜';

CREATE SEQUENCE SEQ_NO_CHECK NOCACHE;

INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 1, 'O', '2022-09-01');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 2, 'O', '2022-09-01');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 3, 'O', '2022-09-01');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 4, 'O', '2022-09-01');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 5, 'O', '2022-09-01');

INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 1, 'O', '2022-09-02');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 2, 'O', '2022-09-02');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 3, 'O', '2022-09-02');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 4, 'O', '2022-09-02');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 5, 'O', '2022-09-02');

INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 1, 'O', '2022-09-03');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 2, 'O', '2022-09-03');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 3, 'O', '2022-09-03');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 4, 'O', '2022-09-03');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 5, 'O', '2022-09-03');

INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 1, 'O', '2022-09-04');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 2, 'X', '2022-09-04');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 3, 'O', '2022-09-04');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 4, 'O', '2022-09-04');
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 5, 'X', '2022-09-04');

INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 1, 'O', TO_DATE('22/9/5'));
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 2, 'O', TO_DATE('22/'||'9/5'));
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 3, 'X', TO_DATE('22/'||'9/5'));
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 4, 'O', TO_DATE('22/'||'9/5'));
INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, 5, 'O', TO_DATE('22/'||'9/5'));


SELECT * FROM CHECK_RT
JOIN ROUTINE USING(RT_NO)
WHERE STUDENT_NO =1;

COMMIT;


--SELECT TO_DATE('22/7/21') FROM DUAL;


-- 나의 루틴 전체 조회
SELECT RT_NAME, RT_NO
FROM ROUTINE
WHERE STUDENT_NO = 1
AND SECESSION_FL = 'N';

-- 루틴 기록
--INSERT INTO CHECK_RT VALUES(SEQ_NO_CHECK.NEXTVAL, ?, ?, ?);

SELECT * FROM STUDENT;	
SELECT * FROM ROUTINE;
-- 루틴 추가
INSERT INTO ROUTINE VALUES(SEQ_NO_RT.NEXTVAL, '운동하기', 3);


SELECT RT_NAME, CHECK_RT, CHECK_DATE
FROM CHECK_RT
JOIN ROUTINE USING(RT_NO)
WHERE STUDENT_NO = 1
GROUP BY CHECK_DATE;

SELECT (SELECT RT_NAME FROM ROUTINE WHERE STUDENT_NO = 1)
FROM CHECK_RT C;

-- 회원 정보 수정
UPDATE STUDENT
SET STUDENT_NM = '김지민',
STUDENT_PHONE = '010-2050-7831'
WHERE STUDENT_NO = 6;


-- 비밀번호 변경
UPDATE STUDENT
SET STUDENT_PW = 'pass1234'
WHERE STUDENT_NO = 1
AND STUDENT_PW = 'pass01';


-- 한달 루틴 보기
SELECT * 
FROM (SELECT RT_NO, RT_NAME, CHECK_RT, TO_CHAR(CHECK_DATE, 'DD')  CHECK_DATE
    FROM CHECK_RT
    JOIN ROUTINE USING(RT_NO)
    WHERE STUDENT_NO = 4
    AND EXTRACT(MONTH FROM CHECK_DATE) = 9
    AND SECESSION_FL = 'N')
PIVOT( MIN(CHECK_RT) 
FOR CHECK_DATE
IN ('01', '02', '03', '04', '05',
   '06', '07', '08', '09' ,'10',
   '11', '12', '13', '14' ,'15',
   '16', '17', '18', '19' ,'20',
   '21', '22', '23', '24' ,'25',
   '26', '27', '28', '29' ,'30', '31'
));
-- List< Map<String, String> >

--           컬럼명,  값
--           "rtNo" ,  "1"
--         "reName" , "복습하기"
--            "01" , "O"
--            "02" , "X"

--           "rtNo" ,  "2"
--         "reName" , "운동하기"
--           "01" , "O"
--            "02" , "X"

--           "rtNo" ,  "3"
--         "reName" , "6시 기상"
--           "01" , "O"
--            "02" , "X"

/*
 * while(rs.next){
 *    Map<String,String> map = new LinkedHashMap<>();
 *  map.put("rtNo" , rs.getInt("RT_NO") + "" );
 *  map.put("rtName" , rs.getString("RT_NAME") );
 * 
 *  for(int i=1 ; i<=31 ; i++){
 *       String col = i < 10 ? "0" + i : "" + i;      
 *        map.put(col , rs.getString(col) );
 * 
 *  }
 *
 *  list.add(map);
 * } 
 * 
 * */

SELECT * FROM CHECK_RT
JOIN ROUTINE USING(RT_NO)
WHERE STUDENT_NO = 4;

SELECT * FROM ROUTINE;

SELECT TO_CHAR(CHECK_DATE, 'YYYY-MM-DD')
FROM CHECK_RT
JOIN ROUTINE USING(RT_NO)
WHERE STUDENT_NO = 1
AND EXTRACT(MONTH FROM CHECK_DATE) = 9   
GROUP BY TO_CHAR(CHECK_DATE, 'YYYY-MM-DD')
ORDER BY 1;
--> List<String> 날짜 저장

SELECT RT_NAME, CHECK_RT
FROM CHECK_RT
JOIN ROUTINE USING(RT_NO)
WHERE STUDENT_NO = 4
--AND CHECK_DATE = '2022-09-05' -- 위 SELECT 결과 1행씩 대입
ORDER BY RT_NO;

-- List< List <  VO(rtName, checkRt)  >  >


SELECT * FROM ROUTINE;

UPDATE ROUTINE SET RT_NAME = '아침먹기'
WHERE RT_NO = 4;

SELECT * FROM STUDENT;

--회원 탈퇴
UPDATE STUDENT SET SECESSION_FL='N'
WHERE STUDENT_NO = 6;

-- 성적 테이블 만들기

CREATE TABLE SCORE (
	TEST_NO NUMBER PRIMARY KEY, 
	TEST_DATE DATE DEFAULT SYSDATE,
	TEST_SCORE NUMBER NOT NULL,
	STUDENT_NO NUMBER REFERENCES STUDENT(STUDENT_NO) ON DELETE CASCADE
)

SELECT * FROM SCORE;

COMMENT ON COLUMN SCORE.TEST_NO IS '시험번호';
COMMENT ON COLUMN SCORE.TEST_DATE IS '시험일';
COMMENT ON COLUMN SCORE.TEST_SCORE IS '시험성적';
COMMENT ON COLUMN SCORE.STUDENT_NO IS '회원번호';

CREATE SEQUENCE SEQ_NO_TEST NOCACHE;

INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '2022-09-01', 75, 1);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '2022-09-01', 80, 2);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '2022-09-01', 100, 3);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-02', 80, 4);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-08', 75, 1);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-15', 80, 1);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-07', 88, 2);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-13', 77, 2);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-13', 95, 3);
INSERT INTO SCORE VALUES(SEQ_NO_TEST.NEXTVAL, '22-09-18', 89, 3);

COMMIT;

SELECT TO_CHAR(TEST_DATE, 'MM/DD (DY)') TEST_DATE, TEST_SCORE
FROM SCORE
WHERE STUDENT_NO = 1;

SELECT * FROM STUDENT;
SELECT * FROM ROUTINE;
SELECT * FROM CHECK_RT;

ALTER TABLE ROUTINE ADD(SECESSION_FL CHAR(1) DEFAULT 'N' CHECK(SECESSION_FL IN ('Y','N')));

UPDATE ROUTINE SET SECESSION_FL = 'Y'
WHERE RT_NO = 7;

UPDATE ROUTINE SET RT_NAME = '운동하기'
WHERE RT_NO = 9;

SELECT * FROM STUDENT;

SELECT *
FROM CHECK_RT
WHERE CHECK_DATE = '2022-09-09';

UPDATE CHECK_RT
SET CHECK_RT = 'O'
WHERE CHECK_DATE = TO_DATE('22/'||'9/9')
AND RT_NO = 6;
ROLLBACK;

UPDATE CHECK_RT SET CHECK_RT = 'O'
WHERE RT_NO = 6
AND CHECK_DATE = '2022-09-01';


DELETE FROM ROUTINE WHERE RT_NO = 15;
COMMIT;



----------------배운 내용 정리하기----------------------
-- 과목 테이블
CREATE TABLE SUBJECT(
	SUBJECT_NO NUMBER PRIMARY KEY,
	SUBJECT_NM VARCHAR2(30) NOT NULL
)

SELECT * FROM SUBJECT;

COMMENT ON COLUMN SUBJECT.SUBJECT_NO IS '과목번호';
COMMENT ON COLUMN SUBJECT.SUBJECT_NM IS '과목명';

CREATE SEQUENCE SEQ_NO_SUB NOCACHE;

INSERT INTO SUBJECT VALUES(SEQ_NO_SUB.NEXTVAL, 'Java');
INSERT INTO SUBJECT VALUES(SEQ_NO_SUB.NEXTVAL, 'SQL');

COMMIT;


-- 단원 테이블
CREATE TABLE CHAPTER(
	CH_ID NUMBER PRIMARY KEY,
	SUBJECT_NO NUMBER NOT NULL,
	CH_NO NUMBER NOT NULL,
	CH_NM VARCHAR2(100) NOT NULL
)

SELECT * FROM CHAPTER;

COMMENT ON COLUMN CHAPTER.CH_ID IS '챕터 식별번호';
COMMENT ON COLUMN CHAPTER.SUBJECT_NO IS '과목번호';
COMMENT ON COLUMN CHAPTER.CH_NO IS '챕터번호(단원)';
COMMENT ON COLUMN CHAPTER.CH_NM IS '챕터 이름';

CREATE SEQUENCE SEQ_NO_CH NOCACHE;

INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 1, '변수');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 2, '연산자');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 3, '제어문');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 4, '배열');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 5, 'OOP');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 6, '객체배열');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 7, '상속');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 8, '다형성');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 9, '예외처리');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 10, '컬렉션');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 11, 'IO');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 1, 12, '네트워크');

INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 1, 'DML(SELECT)');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 2, '함수(FUNCTION)');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 3, 'GROUP BY & HAVING');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 4, 'JOIN');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 5, 'DML(INSERT, UPDATE, DELETE)');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 6, '서브쿼리(SUBQUERY)');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 7, 'DDL(CREATE, ALTER, DROP)');
INSERT INTO CHAPTER VALUES(SEQ_NO_CH.NEXTVAL, 2, 8, 'ORACLE OBJECT');



COMMIT;

--용어&정의

CREATE TABLE NOTE(
	NOTE_NO NUMBER PRIMARY KEY,
	CH_ID NUMBER NOT NULL,
	NOTE_TITLE VARCHAR2(500) NOT NULL,
	NOTE_CONTENT VARCHAR2(4000) NOT NULL
)



COMMENT ON COLUMN NOTE.NOTE_NO IS '노트번호';
COMMENT ON COLUMN NOTE.CH_ID IS '챕터번호(단원)';
COMMENT ON COLUMN NOTE.NOTE_TITLE IS '노트제목';
COMMENT ON COLUMN NOTE.NOTE_CONTENT IS '노트내용';

CREATE SEQUENCE SEQ_NO_NOTE NOCACHE;

INSERT INTO NOTE VALUES(SEQ_NO_NOTE.NEXTVAL, 1, '변수', '메모리(RAM)에 값을 기록하는 공간(박스)', default);
INSERT INTO NOTE VALUES(SEQ_NO_NOTE.NEXTVAL, 1, '값(Data)', '정략적으로 특정 가능한 단위(문자, 숫자 등 키보드로 입력가능한 모두)');

UPDATE NOTE SET NOTE_CONTENT = '한 조직에 필요한 정보를 여러 응용 시스템에서 공용할 수 있도록 논리적으로 연관된 데이터를 모으고, 중복되는 데이터를 최소화하여 구조적으로 통합/저장해놓은 것'
WHERE NOTE_NO = 4;

SELECT * FROM SUBJECT;
SELECT * FROM CHAPTER;
SELECT * FROM NOTE;

SELECT * FROM CHAPTER
WHERE SUBJECT_NO = 1;

SELECT * FROM NOTE
WHERE CH_ID = 1;

ALTER TABLE NOTE ADD(SECESSION_FL CHAR(1) DEFAULT 'N' CHECK(SECESSION_FL IN ('Y','N')));
ALTER TABLE NOTE RENAME COLUMN SECESSION_FL TO DELETE_FL;

SELECT SUBJECT_NO, SUBJECT_NM, CH_ID, CH_NM, NOTE_TITLE, NOTE_CONTENT
FROM NOTE
JOIN CHAPTER USING(CH_ID)
JOIN SUBJECT USING(SUBJECT_NO)
WHERE DELETE_FL = 'N'
--AND NOTE_TITLE LIKE '%' || '변수' || '%'
AND (NOTE_TITLE LIKE '%' || '메모리' || '%'
		OR NOTE_CONTENT LIKE '%' || '메모리' || '%')
ORDER BY SUBJECT_NO, CH_ID;


UPDATE NOTE SET CH_ID = 13
WHERE NOTE_NO = 4;

COMMIT;