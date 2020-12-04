<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elasticsearch Example - 인덱스 생성</title>
<script type="text/javascript" src="/resources/static/js/jquery-3.5.1.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	//allClear
	$("#allClear").on("click", e => {
		$("#sampleIndex1_create_result").val("");
	});
	
	//## 인덱스 추가 ##
	
	//sampleIndex1 put api
	$("#sampleIndex1_putBtn").on("click", e => {
		$.ajax({
			url: "/sample1_indexCreate",
			type: "GET",
			dataType: "json",
		    success : function(data) {
		    	console.log("CREATE_RESULT::", data);
		    	$("#sampleIndex1_create_result").text("")
		    	$("#sampleIndex1_create_result").text(JSON.stringify(data));
		    }
		});	
	});	
	
	//sampleIndex1 clear
	$("#sampleIndex1_putClearBtn").on("click", e => $("#sampleIndex1_create_result").val(""));
	
	
	//## 인덱스 삭제 ##
	
	//sampleIndex1 delete api
	$("#sampleIndex1_deleteBtn").on("click", e => {
		$.ajax({
			url: "/sample1_indexDelete",
			type: "GET",
			dataType: "json",
		    success : function(data) {
		    	console.log("DELETE_RESULT::", data);
		    	$("#sampleIndex1_delete_result").text("")
		    	$("#sampleIndex1_delete_result").text(JSON.stringify(data));
		    	
		    }
		});	
	});	
	
	//sampleIndex1 clear
	$("#sampleIndex1_deleteClearBtn").on("click", e => $("#sampleIndex1_delete_result").val(""));
	
	
	//## 자동조회 ##
	
	//샘플데이터 다운로드
	$("#sampleDataDown").on("click", e => {
		window.location.href = "/resources/static/csv/sample1.csv";
	});
	
	//데이터 업로드, bulk
	$("#bulkBtn").on("click", function(){
		var form = $("#upload")[0];
		var formData = new FormData(form);
		
		$.ajax({
			url: "/sample1_dataBulk",
	        processData: false,
	        contentType: false,
	        data: formData,
	        type: "POST",
	        dataType: "json",
	        success: function(data){
	        	console.log("BULK_RESULT::", data);
		    	$("#sampleIndex1_bulk_result").text("")
		    	$("#sampleIndex1_bulk_result").text(JSON.stringify(data));
	        }
		});
	});
});
</script>
</head>
<body>
	<a href="/">뒤로가기</a>
	<a href="/sample1/config">환경설정</a>
	<div>
		<ul>
			<li>Sample Example1 <button id="allClear">all clear</button>
				<ul>
					<!-- 인덱스 생성 -->
					<li>
						<span>sample1 index create</span>
						<button id="sampleIndex1_putBtn">index put api</button>
						<button id="sampleIndex1_putClearBtn">clear</button>
					</li>
					<div style=" margin-top: 5px;">
						<textarea id="sampleIndex1_create" style="width: 45%; height: 260px;" readonly="readonly"></textarea>
						<textarea id="sampleIndex1_create_result" style="width: 45%; height: 260px;" readonly="readonly"></textarea>
					</div><br/>
					
					<!-- 인덱스 삭제 -->
					<li>
						<span>sample1 index delete</span>
						<button id="sampleIndex1_deleteBtn">index delete api</button>
						<button id="sampleIndex1_deleteClearBtn">clear</button>
					</li>
					<div style=" margin-top: 5px;">
						<textarea id="sampleIndex1_delete" style="width: 45%; height: 150px;" readonly="readonly"></textarea>
						<textarea id="sampleIndex1_delete_result" style="width: 45%; height: 150px;" readonly="readonly"></textarea>
					</div><br/>
					
					<!-- 데이터 등록 bulk -->
					<li>
						<span>sample1 data bulk</span>						
						<form  id="upload" method="post" enctype="multipart/form-data" onsubmit="return false">
							<input type="file" name="bulk">
							<button id="bulkBtn">index bulk api</button>
							<button id="sampleDataDown">sample data download</button>							
						</form>
					</li>
					<div>
						<textarea id="sampleIndex1_bulk_result" style="width: 90.7%; height: 200px;" readonly="readonly"></textarea>
					</div><br/>
					<!-- 자동 데이터 조회 -->
					<li>
						<span>sample1 auto search</span>						
						<input type="text">
						<div style="border: 1px solid black; margin: 3px 0px 0px 156px; width: 350px;"></div>
					</li><br/>
				</ul>
			</li>
		</ul>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
	let sampleIndex1 = "";
	sampleIndex1 += 'PUT index001                                                         \n';
	sampleIndex1 += '{                                                                    \n';
	sampleIndex1 += '    "settings":{                                                     \n';
	sampleIndex1 += '        "number_of_shards": 5,                                       \n';
	sampleIndex1 += '        "number_of_replicas": 1,                                     \n';
	sampleIndex1 += '        "analysis":{                                                 \n';
	sampleIndex1 += '            "tokenizer":{                                            \n';
	sampleIndex1 += '                "korean_nori_tokenizer":{                            \n';
	sampleIndex1 += '                    "type":"nori_tokenizer",                         \n';
	sampleIndex1 += '                    "decompound_mode":"mixed"                        \n';
	sampleIndex1 += '                }                                                    \n';
	sampleIndex1 += '            },                                                       \n';
	sampleIndex1 += '            "analyzer":{                                             \n';
	sampleIndex1 += '                "nori_analyzer":{                                    \n';
	sampleIndex1 += '                    "type":"custom",                                 \n';
	sampleIndex1 += '                    "tokenizer":"korean_nori_tokenizer",             \n';
	sampleIndex1 += '                    "filter":[                                       \n';
	sampleIndex1 += '                        "nori_posfilter",                            \n';
	sampleIndex1 += '                        "nori_readingform"                           \n';
	sampleIndex1 += '                    ]                                                \n';
	sampleIndex1 += '                }                                                    \n';
	sampleIndex1 += '            },                                                       \n';
	sampleIndex1 += '            "filter":{                                               \n';
	sampleIndex1 += '                "nori_posfilter":{                                   \n';
	sampleIndex1 += '                    "type":"nori_part_of_speech",                    \n';
	sampleIndex1 += '                    "stoptags":[                                     \n';
	sampleIndex1 += '                        "E","IC","J","MAG","MM","NA","NR","SC",      \n';
	sampleIndex1 += '                        "SE","SF","SH","SL","SN","SP","SSC","SSO",   \n';
	sampleIndex1 += '                        "SY","UNA","UNKNOWN","VA","VCN","VCP","VSV", \n';
	sampleIndex1 += '                        "VV","VX","XPN","XR","XSA","XSN","XSV"       \n';
	sampleIndex1 += '                    ]                                                \n';
	sampleIndex1 += '                }                                                    \n';
	sampleIndex1 += '            }                                                        \n';
	sampleIndex1 += '        }                                                            \n';
	sampleIndex1 += '    },                                                               \n';
	sampleIndex1 += '    "mappings": {                                                    \n';
	sampleIndex1 += '      "properties": {                                                \n';
	sampleIndex1 += '        "content": {                                                 \n';
	sampleIndex1 += '          "type": "text",                                            \n';
	sampleIndex1 += '          "analyzer": "nori_analyzer",                               \n';
	sampleIndex1 += '          "search_analyzer": "nori_analyzer"                         \n';
	sampleIndex1 += '        }                                                            \n';
	sampleIndex1 += '      }                                                              \n';
	sampleIndex1 += '    }                                                                \n';
	sampleIndex1 += '}                                                                    \n';
	$("#sampleIndex1_create").val(sampleIndex1);
	
	sampleIndex1 = "DELETE index001";
	$("#sampleIndex1_delete").val(sampleIndex1);
});
</script>
</html>