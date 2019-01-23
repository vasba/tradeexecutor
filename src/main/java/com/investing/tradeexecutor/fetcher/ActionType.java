package com.investing.tradeexecutor.fetcher;

public enum ActionType {

	BUY("buy"), 
	SELL("sell");
	
	private final String operation; 

	public String getOperation() {
		return operation;
	}

	private ActionType(String operation) {
		this.operation = operation;
	}
}
