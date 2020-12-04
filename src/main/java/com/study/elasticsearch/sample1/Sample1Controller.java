package com.study.elasticsearch.sample1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Sample1Controller {
	
	@Autowired
	private Sample1Service elasticsearchSample1Service;	
		
	//환경설정 페이지
	@GetMapping("/sample1/config")
	public String config() {return "/elasticsearch/sample1/config";}	
	
	//샘플1 페이지
	@GetMapping("/sample1")
	public String sample1() {return "/elasticsearch/sample1/sample1";}
	
	//샘플1 인덱스 생성
	@ResponseBody
	@GetMapping("/sample1_indexCreate")
	public String sample1_indexCreate() {
		try {
			return elasticsearchSample1Service.sample1_indexCreate();
		} catch (Exception e) {
			return "{\"result\":\"error\", \"message\":  \""+ e.getMessage()+"\"}";
		}
	}
	
	//샘플1 인덱스 삭제
	@ResponseBody
	@GetMapping("/sample1_indexDelete")
	public String sample1_indexDelete() {
		try {
			return elasticsearchSample1Service.sample1_indexDelete();
		} catch (Exception e) {
			return "{\"result\":\"error\", \"message\":  \""+ e.getMessage()+"\"}";
		}
	}
	
	//샘플1 데이터 주입 (bulk)
	@ResponseBody
	@PostMapping("/sample1_dataBulk")
	public Map<String, Object> sample1_dataBulk(MultipartFile bulk){		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("result", "success");
			result.put("data", elasticsearchSample1Service.sample1_dataBulk(bulk));
		} catch (Exception e) {
			result.put("result", "error");
			result.put("message", e.getMessage());
		}
		return result;
	}
}
