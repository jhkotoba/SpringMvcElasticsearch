package com.study.elasticsearch.sample1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.study.elasticsearch.sample1.model.Sample1BulkModel;

@Service
public class Sample1Service {
	
	private static final Logger logger = LoggerFactory.getLogger(Sample1Service.class);	
	
	/* 엘라스틱서치 서버정보 */
	private int PORT = 9200;
	private String HOST_NAME = "127.0.0.1";
	private String SCHEME = "http";
	
	/* 인덱스 */
	private String INDEX = "index001";
	
	/* bulk */
	private int TERM = 100;
	
	/**
	 * 샘플1 인덱스 생성 API
	 * @return
	 * @throws Exception
	 */
	public String sample1_indexCreate() throws Exception {
		
		logger.info("## sample1_indexCreate START ##");
		
		
		RestHighLevelClient client = null;
		
		try {
			
			//인덱스 생성
			CreateIndexRequest request = new CreateIndexRequest(this.INDEX);
			
			//인덱스 설정
			request.settings(Settings.builder() 
			    .put("index.number_of_shards", 3)
			    .put("index.number_of_replicas", 1)
			);
			
			request.setTimeout(TimeValue.timeValueMinutes(30));
			request.setMasterTimeout(TimeValue.timeValueMinutes(30));			
			request.waitForActiveShards(ActiveShardCount.DEFAULT); 
			
			//인덱스 맵핑
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject();
			{
			    builder.startObject("properties");
			    {
			        builder.startObject("content");
			        {
			            builder.field("type", "text");
			        }
			        builder.endObject();
			    }
			    builder.endObject();
			}
			builder.endObject();
			
			//빌드
			request.mapping(builder);
			
			//클라이언트 생성
			client = this.connectRestHighLevelClient();
			
			//API 전송
			CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
			logger.info("isShardsAcknowledged :: {}", createIndexResponse.isShardsAcknowledged());
			
			if(client != null) {
				client.close();
			}
			
			//결과 반환
			if(createIndexResponse.isShardsAcknowledged()) {
				String result =  "{\r\n"
				+ "  \"acknowledged\" : "+ createIndexResponse.isAcknowledged() + ",\r\n"
				+ "  \"shards_acknowledged\" : " + createIndexResponse.isShardsAcknowledged() + ",\r\n"
				+ "  \"index\" : \"" + createIndexResponse.index() + "\"\r\n"
				+ "}";
				logger.info("result :: {}", result);
				return result;
			}else {
				logger.info("result :: {}", createIndexResponse.toString());
				return createIndexResponse.toString();
			}
			
		}catch(Exception e) {
			throw e;
		}finally {
			try {
				if(client != null) {
					client.close();
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	/**
	 * 샘플1 인덱스 삭제 API
	 * @return
	 * @throws Exception
	 */
	public String sample1_indexDelete() throws Exception {
		logger.info("## sample1_indexDelete START ##");
		
		RestHighLevelClient client = null;
		
		try {
			DeleteIndexRequest request = new DeleteIndexRequest(this.INDEX);
			
			//삭제대기승인시간
			request.timeout(TimeValue.timeValueMinutes(2));
			//마스터노드 연결시간
			request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
			
			//클라이언트 생성
			client = this.connectRestHighLevelClient();
			
			//API 전송
			AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
			
			//결과 반환
			if(deleteIndexResponse.isAcknowledged()) {
				String result =  "{\"isAcknowledged\" : "+ deleteIndexResponse.isAcknowledged() + "}";
				logger.info("result :: {}", result);
				return result;
			}else {
				logger.info("result :: {}", deleteIndexResponse.toString());
				return deleteIndexResponse.toString();
			}			
		}catch (Exception e) {
			throw e;
		}finally {
			try {
				if(client != null) {
					client.close();
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	/**
	 * 샘플1 데이터 주입 (bulk)
	 * @return
	 * @throws Exception
	 */
	public Sample1BulkModel sample1_dataBulk(MultipartFile bulk) throws Exception {
		
		InputStream io = null;
		InputStreamReader re = null;
		BufferedReader br = null;
		
		Sample1BulkModel result = null;
		
		try {			
			String fileName = bulk.getOriginalFilename();						
			String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());		
			
			logger.info("fileName :: {}", fileName);
			logger.info("extension :: {}", extension);			
			
			if("CSV".equals(extension.toUpperCase())) {		
				io = bulk.getInputStream();
								
				re = new InputStreamReader(io);
				br = new BufferedReader(re);
				
				result = this.sample1_dataBulk_bulk(br);
			}else {
				throw new Exception("FILE EXTENSION NOT CSV");
			}
		}catch (Exception e) {
			throw e;
		}finally {
			try {
                if (io != null) {
                	io.close();
                }
                if(re != null) {
                	re.close();
                }
                if(br != null) {
                	br.close();
                }
            } catch (Exception e) {
            	throw e;
            }
		}
		return result;
	}
	
	/**
	 * 샘플1 데이터 주입 (bulk API)
	 * @return
	 * @throws Exception
	 */
	private Sample1BulkModel sample1_dataBulk_bulk(BufferedReader br) throws Exception {
		
		Sample1BulkModel result = new Sample1BulkModel(true);		
		String line = null;
		int count = 1;
		
		//bulk api 요청
		BulkRequest request = null;
		while(true) {
			line = br.readLine();
			logger.info("COUNT::{}  DATA:: {}", count, line);
			try {
				//데이터 끝
				if(line == null) {
					//남은 bulk 송신
					result.appendData(this.bulkApiSend(request));
					logger.info("## END BREAK ##");
					break;
				//빈값 데이터
				}else if(ObjectUtils.isEmpty(line)) {
					logger.info("## LINE EMPTY ##");
					result.failCountPlus();
					result.getFailList().add("ROW:[" + count + "][EMPTY DATA]");
				//bulk 시작
				}else {
					//bulk를 TERM 단위로 API 호출
					if(count % this.TERM == 0) {
						if(request == null) {
							request = new BulkRequest();
						}
						request.add(this.setIndexRequest(line));
						//bulk 송신
						result.appendData(this.bulkApiSend(request));
						request = null;
					}else {
						if(request == null) {
							request = new BulkRequest();
						}
						request.add(this.setIndexRequest(line));
					}
				}
			}catch (Exception e) {
				//EXCEPTION 데이터 세팅
				logger.error("EXCEPTION:: {}", line);
				result.failCountPlus();
				result.getFailList().add("ROW:[" + count + "]DATA:[" + line + "]EXCEPTION:["+ e.getMessage()+"]");
			}
			count++;
		}
		logger.info("RESULT :: {}", result.toString());
		return result;
	}
	
	/**
	 * IndexRequest 생성
	 * @param line
	 * @return
	 */
	private IndexRequest setIndexRequest(String line) {
		String[] word = line.split(",");
		return new IndexRequest(this.INDEX).id(word[0]).source(XContentType.JSON, "content", word[1]);
	}
	
	/**
	 * 엘라스틱서치 bulk api 메소드
	 * @return
	 * @throws Exception 
	 */
	private Sample1BulkModel bulkApiSend(BulkRequest request) throws Exception{		
		
		logger.info("## BULK API START ##");
		
		Sample1BulkModel result = new Sample1BulkModel();
		
		Iterator<BulkItemResponse> ite = null;
		BulkItemResponse item = null;
		
		//bulk 수행 timeout
		request.timeout(TimeValue.timeValueMinutes(2));
		//정책 새로고침
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		
		//클라이언트 생성
		RestHighLevelClient client = null;
		
		//bulk 전송부분
		try {
			client = this.connectRestHighLevelClient();
			BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
			
			//응답-실패
			if(bulkResponse.hasFailures()) {
				logger.error("BULK API FAIL::{}", bulkResponse.buildFailureMessage());	
				ite = bulkResponse.iterator();
				
				while(ite.hasNext()) {
					item = ite.next();
					result.getFailList().add("ID:" + item.getId() + " MESSAGE:" + item.getFailureMessage());
					result.failCountPlus();
					logger.debug("ID::{} MESSAGE::{}", item.getId(),item.getFailureMessage());
				}
			//응답 성공
			}else {
				logger.info("BULK API OK::{}", bulkResponse.status());				
				ite = bulkResponse.iterator();
				while(ite.hasNext()) {
					item = ite.next();
					result.successCountPlus();
				}
			}
		}catch (Exception e) {
			result.getFailList().add("ERROR EXCEPTION::["+e.getMessage()+"]");
			throw e;
		}finally {
			//close
			try {
				if(client != null) {
					client.close();
				}
			} catch (Exception e) {
				result.getFailList().add("ERROR EXCEPTION::["+e.getMessage()+"]");
				throw e;
			}
		}
		logger.info("bulkApiSend result ::{}", result.toString());
		
		return result;
	}
	
	//client 생성
	private RestHighLevelClient connectRestHighLevelClient() {
		return new RestHighLevelClient(
			RestClient.builder(
				new HttpHost(HOST_NAME, PORT, SCHEME)
			)
		);
	}
}
