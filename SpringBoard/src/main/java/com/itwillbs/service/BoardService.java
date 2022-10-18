package com.itwillbs.service;

import com.itwillbs.domain.BoardVO;

public interface BoardService {
	//글쓰기 
	public void boardWrite(BoardVO vo)throws Exception;
}
