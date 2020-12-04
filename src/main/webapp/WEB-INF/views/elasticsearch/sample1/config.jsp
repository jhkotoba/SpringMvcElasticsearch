<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elasticsearch Example - 환경설정</title>
</head>
<body>
	<a href="/sample1">뒤로가기</a>
	<div>
		<ul>
			<li>엘라스틱서치 환경설정
				<ul>
					<li>엘라스틱서치, 키바나 버전 7.8.0버전으로 테스트</li>
				</ul>
			</li>
		</ul>
		<ul>
			<li>엘라스틱 다운로드
				<ul>
					<li>https://www.elastic.co/kr/downloads/past-releases/elasticsearch-7-8-0</li>
				</ul>
			</li>
		</ul>
		<ul>
			<li>키바나 다운로드
				<ul>
					<li>https://www.elastic.co/kr/downloads/past-releases/kibana-7-8-0</li>
				</ul>
			</li>
		</ul>
		<ul>
			<li>엘라스틱 설치 및 실행
				<ul>
					<li>다운로드한 파일을 임의폴더에 압축해제</li>
					<li>elasticsearch-7.8.0\bin\elasticsearch.bat 실행</li>
				</ul>
			</li>
		</ul>
		<ul>
			<li>키바나 설치 및 실행
				<ul>
					<li>다운로드한 파일을 임의폴더에 압축해제</li>
					<li>kibana-7.8.0-windows-x86_64\bin\kibana.bat 실행</li>
					<li>http://localhost:5601/</li>
				</ul>
			</li>
		</ul>
		<ul>
			<li>nori 플러그인 설치
				<ul>
					<li>bin/elasticsearch-plugin install analysis-nori</li>
				</ul>
			</li>
		</ul>
		
		
		<ul>
			<li>서버환경
				<ul>
					<li>Spring Boot2 MVC</li>
					<li>elasticsearch-rest-high-level-client 7.8.0 사용</li>
				</ul>
			</li>
		</ul>
	</div>
</body>
</html>