package com.itwillbs.domain;

import com.google.protobuf.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data: lombok 라이브러리를 사용해서 
//VO 객체 안에 set/get 메서드를 자동구현하고, toString자동생성(오버라이딩) -> 클래스명 + F4로 생성확인 가능 

//@Setter
//@Getter
//@ToString
@Data
public class BoardVO {
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private int viewcnt; //형변환 없이 카운팅만 하는 변수는 int로 표현
	private Timestamp regdate;
}
