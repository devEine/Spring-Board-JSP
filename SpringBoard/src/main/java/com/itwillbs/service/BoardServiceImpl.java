package com.itwillbs.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{
	

	private static final Logger log 
					= LoggerFactory.getLogger(BoardServiceImpl.class);
	
	//BoardDAO 객체 주입(DI) -> boardWrite메서드가 BoardDAO의 insertBoard(vo)글쓰기 메서드 불러야할 때 필요 
	@Autowired
	private BoardDAO dao;
	
	//글 쓰기
	@Override
	public void boardWrite(BoardVO vo) throws Exception {
		log.info("Service: boardWrite(vo) 호출");
		//Service에서 할 일 : DAO - insertBoard(vo) 메서드 호출 
		dao.insertBoard(vo);
		
		log.info("Service: boardWrite(vo)메서드 동작 완료");
	}
	
	//게시판 글 전체 목록
	@Override
	public List<BoardVO> getBoardListAll() throws Exception {
		log.info("Service: getBoardListAll()호출");
		
		log.info("Service: DAO - listAll()호출");
		//게시판 글 전체 목록을 가지고 오니까 List로, 데이터 타입은 게시판의 정보들의 타입인 BoardVO
		List<BoardVO> boardList = dao.listAll();//DAO의 listAll()메서드 호출
		
		return boardList;
	}
	
	//글 내용 조회 메서드 
	@Override
	public BoardVO getBoard(Integer bno) throws Exception {
		log.info("Service: getBoard() 호출");
		
		BoardVO vo = dao.getBoard(bno);
		
		return vo;
		//return dao.getBoard(bno); -> 가능
	}
	
	// 글 조회수 1증가 메서드
	@Override
	public void updateReadCount(Integer bno) throws Exception {
		log.info("Service: updateReadCount() 호출");

		// DAO -updateReadCount(bno) 호출
		dao.updateReadCount(bno);

	}

	// 글 수정 메서드
	@Override
	public Integer updateBoard(BoardVO vo) throws Exception {
		log.info("Service: updateBoard(BoardVO vo)호출");

		int cnt = dao.updateBoard(vo);

		return cnt;
	}


}
