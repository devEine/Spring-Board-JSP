package com.itwillbs.service;

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
	
	@Override
	public void boardWrite(BoardVO vo) throws Exception {
		log.info("Service: boardWrite(vo) 호출");
		//Service에서 할 일 : DAO - insertBoard(vo) 메서드 호출 
		dao.insertBoard(vo);
		
		log.info("Service: boardWrite(vo)메서드 동작 완료");
	}
}
