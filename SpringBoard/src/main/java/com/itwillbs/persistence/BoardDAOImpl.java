package com.itwillbs.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.BoardVO;

//@Repository : DAO의 어노테이션 <-> Service: @Service
@Repository
public class BoardDAOImpl implements BoardDAO{
	
	

	private static final Logger log 
					= LoggerFactory.getLogger(BoardDAOImpl.class);
	
	//SqlSession객체 주입
	@Inject
	private SqlSession sqlSession;
	
	//mapper주소 상수로 고정
	private static final String NAMESPACE = "com.itwillbs.mapper.BoardMapper";
	
	//글쓰기
	@Override
	public void insertBoard(BoardVO vo) {
		log.info("DAO: insertBoard(vo) 호출");
		//log.info("vo: "+vo); //DAO에 데이터 넘어왔는지 확인
		
		//SQL쿼리 실행 객체 - SqlSession객체: DB연결, MyBatis설정, Mapper설정, 자원해제 를 수행함 
		//글쓰기 - insert 쿼리문 실행 
		//쿼리문 - boardMapper.xml파일에서 불러옴
		//불러오는 법 : (상수 NAMESPACE(mapper주소)+".쿼리문명",Controller로부터 전달받은 값->mapper로 넘겨줄 값);
		int result = sqlSession.insert(NAMESPACE+".create",vo);
		
		if(result>0) { //작성된 글이 0개 초과여야 글쓰기 동작 완료라고 log에 표시 
		log.info("DAO: 글쓰기 동작 완료 !");
		}
	}
	
	//글 전체 목록 조회
	@Override
	public List<BoardVO> listAll() throws Exception {
		log.info("DAO: listAll()호출");

		// DB모든정보 가져오기(SQL/mapper 호출)
		List<BoardVO> boardList = sqlSession.selectList(NAMESPACE + ".listAll");

		// log.info("boardList: "+boardList);
		log.info("boardList: " + boardList.size());

		return boardList;
	}

	// 글 1개정보(내용) 조회
	@Override
	public BoardVO getBoard(Integer bno) throws Exception {
		log.info("DAO: getBoard(int)호출");
		BoardVO vo = sqlSession.selectOne(NAMESPACE+".read",bno);
		
		return vo;
		//return sqlSession.selectOne(NAMESPACE+".read",bno); -> 이것도 가능 
		
	}
	
	//글 조회수 1증가 
	@Override
	public void updateReadCount(Integer bno) throws Exception {
		log.info("DAO: updateReadCount() 호출");
		//SQL - mapper 쿼리구문 호출 
		sqlSession.update(NAMESPACE+".updateReadCnt",bno);
		
	}
	
	//글 수정 
	@Override
	public Integer updateBoard(BoardVO vo) throws Exception {
		log.info(" updateBoard(BoardVO vo) 호출 ");
		
		int cnt = sqlSession.update(NAMESPACE + ".updateBoard",vo);		
		
		return cnt;
	}
	
	
}
