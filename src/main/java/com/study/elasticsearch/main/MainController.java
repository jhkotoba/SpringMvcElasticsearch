package com.study.elasticsearch.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	//메인 페이지
	@GetMapping("/")
	public String main() {return "/elasticsearch/page";}
	
	//참고사이트 페이지
	@GetMapping("/referenceSite")
	public String referenceSite() {return "/elasticsearch/referenceSite";}
}
