package com.itwillbs.controller;


import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String registerPOST(BoardVO vo,RedirectAttributes rttr/*Model model*/) throws Exception{
		log.info("Controller: registerPOST() 호출");
		
		//한글처리(필터)-> web.xml
		//전달된 정보 저장 
		log.info("글쓰기 정보: "+vo);
		//서비스 -> 글쓰기 동작 호출 : Controller-Service-DAO -> Controller와 DAO 사이의 결합도 낮춤
		service.boardWrite(vo);
		log.info("Controller: 글쓰기 동작 완료!");
		
		//연결 view페이지(listAll.jsp로 정보 보내기)
		//1번 방법) model.addAttribute("msg","OK");
		//=> 주소줄에 데이터 노출됨, 새로고침 시에도 데이터 남아있음 
		
		//3번 방법) RedirectAttributes 객체사용 -> redirect페이지에만 사용가능 
		//=> 주소줄에 데이터 노출되지 않음, 새로고침 시에 데이터 사라짐
		rttr.addFlashAttribute(" msg","OK");
		
		//페이지 이동(글 목록 리스트로)-> 화면,페이지 주소 모두 변경(redirect)
		
		//return "/board/success";
		//2번 방법)
		//return "redirect:/board/listAll?msg=OK"; listAll.jsp 뷰페이지 이동 시 주소줄에 데이터 담아 이동하는 방식
		return "redirect:/board/listAll";
	}
	//게시판 글쓰기(POST)
	
	//게시판 리스트 (GET) : 게시판 리스트 출력(조회)
	//http://localhost:8088/board/listAll
	@RequestMapping(value = "/listAll",method = RequestMethod.GET)
	public void listALLGET(@ModelAttribute("msg") String msg,Model model,HttpSession session) throws Exception{
		log.info("Controller: listALLGET()호출");
		//1)글쓰기 -> 리스트, 2)리스트 호출만 
		log.info("msg: "+msg);
		//연결된 view페이지로 정보 전달 
		model.addAttribute("msg",msg);
		//서비스 - getBoardListAll 글 전체 목록 가져오는 메서드 호출
		List<BoardVO> boardList = service.getBoardListAll();
		
		model.addAttribute("boardList",boardList);
		
		//세션객체 - isUpdate 정보전달 : 리스트에서 글내용보기로 넘어갈때 회원 정보 넘기기 위해 (조회수 1증가 제어)
		session.setAttribute("isUpdate", false);
		
		log.info("Controller: /board/listAll -> /board/listAll.jsp 호출");
	}
	//게시판 리스트 (GET) : 게시판 리스트 출력(조회)
	
	//(제목 클릭 시)글 내용 보기 (GET)
	@RequestMapping(value = "/read",method = RequestMethod.GET)
	public void readGET(@RequestParam("bno") int bno,Model model,HttpSession session/* @ModelAttribute("bno")*/) throws Exception{
//------------------------------------------------------------------------------------------------		
		//@RequestParam : 
		//@ModelAttribute
//------------------------------------------------------------------------------------------------		
		log.info("Controller: readGET() 호출");
		//전달정보(bno)
		log.info("bno: "+bno);
		//본인이 쓴 글은 본인 클릭 시 , 조회수 올라가지 않게 제어 -> 회원(본인)정보가 필요 
		//boolean isUpdate = false;
		log.info("Controller- isUpdate: "+session.getAttribute("isUpdate"));
		
		//받아오는 session값을 변수에 담아서 사용 
		boolean isUpdate = (boolean)session.getAttribute("isUpdate");
		//if(isUpdate == false)
		if(!isUpdate) {// !: 부정의 의미 -> false면 참(실행됨), true면 거짓(실행안됨) 
			
			//서비스 -updateReadCount(bno)조회수 1증가 메서드 호출 
			service.updateReadCount(bno);
			log.info("조회수 1증가!!");
			session.setAttribute("isUpdate", true);
			
		}
		
		
		//서비스 -getBoard(int)글 내용 조회 메서드 호출 
		BoardVO vo = service.getBoard(bno);
		log.info("Controller: vo :"+vo);
		model.addAttribute("vo", vo);

		log.info("Controller: /board/read -> /board/read.jsp 호출");
	}
	
	//(제목 클릭 시)글 내용 보기 (GET)
	
	//글 수정하기 -기존 정보 조회 출력 + 수정할 정보 입력받는 페이지(GET)
	@RequestMapping(value = "/modify",method = RequestMethod.GET)
	public void modifyGET(@RequestParam("bno")int bno,Model model) throws Exception{
		//전달정보 저장(bno)
		log.info("Controller- bno: "+bno);
		//서비스 -게시판 글 정보를 가져오는 메서드 
		service.getBoard(bno);
		//연결된 뷰 정보 전달
		 model.addAttribute("vo",service.getBoard(bno));
		//뷰페이지 이동 (출력) /board/modify
		 
	}
	//글 수정하기 -기존 정보 조회 출력 + 수정할 정보 입력받는 페이지(GET)
	
	
	//글 수정하기 -수정동작수행(POST)
	@RequestMapping(value = "/modify",method = RequestMethod.POST)
	public String modifyPOST(BoardVO vo) throws Exception{
		log.info("Controller: modifyPOST() 호출");
		//한글처리 -> 필터로 완료 
		//전달정보 저장(수정할 정보) VO
		log.info("수정할 정보: "+vo);
		//서비스 -글 수정 메서드 
		int cnt = service.updateBoard(vo);
		//수정성공시 /ListAll 페이지 이동 
		if(cnt==1) {
			return "redirect:/board/listAll";
		}else {
			//예외처리 
			//new NullPointerException();
			return "/board/modify"+vo.getBno();
		}
	}
	//글 수정하기 -수정동작수행(POST)
}
