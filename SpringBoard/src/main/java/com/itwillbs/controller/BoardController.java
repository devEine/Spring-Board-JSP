package com.itwillbs.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.service.BoardService;


@Controller
@RequestMapping(value = "/board/*")
public class BoardController {
	
	/*
	 * // http://localhost:8088/controller/test(X) //
	 * http://localhost:8088/controller/board/test(X) // server의 module path: /로 설정
	 * -> http://localhost:8088/board/test(O)
	 * 
	 * @RequestMapping(value = "/test") public void test() { log.info("Test() 호출");
	 * }
	 */

	private static final Logger log 
						= LoggerFactory.getLogger(BoardController.class);
	
	//서비스객체 필요(의존적) : 게시판 글쓰기(POST) service 글쓰기 동작 불러올 시에 필요
	@Inject
	private BoardService service;
	
	
	//게시판 글쓰기(GET) : 사용자에게 글쓰기 페이지 출력
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public void registerGET() throws Exception{
		log.info("Controller: registerGET() 호출");
		log.info("/board/regist(GET)방식 호출 -> /board/regist.jsp 연결");
	}
	//게시판 글쓰기(GET)
	
	//게시판 글쓰기(POST) : 글쓰기 동작 수행 
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String registerPOST(BoardVO vo) throws Exception{
		log.info("Controller: registerPOST() 호출");
		
		//한글처리(필터)-> web.xml
		//전달된 정보 저장 
		log.info("글쓰기 정보: "+vo);
		//서비스 -> 글쓰기 동작 호출 : Controller-Service-DAO -> Controller와 DAO 사이의 결합도 낮춤
		service.boardWrite(vo);
		log.info("Controller: 글쓰기 동작 완료!");
		
		//페이지 이동(글 목록 리스트로)
		
		return "";
	}
	//게시판 글쓰기(POST) 
	
}
