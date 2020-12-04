package com.study.elasticsearch.sample1.model;

import java.util.ArrayList;
import java.util.List;

public class Sample1BulkModel {
	
	private int successCount = 0;

	private int failCount = 0;
	
	private List<String> failList = new ArrayList<String>();
	
	public Sample1BulkModel() {}
	
	public Sample1BulkModel(Boolean isInit) {
		if(isInit) {
			this.successCount = 0;
			this.failCount = 0;
			this.failList = new ArrayList<String>();
		}
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
	public void successCountPlus() {
		this.successCount++;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	
	public void failCountPlus() {
		this.failCount++;
	}

	public List<String> getFailList() {
		return failList;
	}

	public void setFailList(List<String> failList) {
		this.failList = failList;
	}
	
	public void appendData(Sample1BulkModel model) {
		this.failCount += model.failCount;
		this.successCount += model.successCount;
		this.failList.addAll(model.getFailList());
	}
	
	@Override
	public String toString() {
		return "EsBulkModel [successCount=" + successCount + ", failCount=" + failCount + "]";
	}
}
