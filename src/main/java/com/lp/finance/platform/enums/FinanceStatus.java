package com.lp.finance.platform.enums;

public enum FinanceStatus {

	Init(1),
	//预理财中，commit之后变成3
	PreFinancing(2),
	Financing(3),
	Exiting(4),
	Exited(5),
	Invalid(6);
	
	private int status;

	private FinanceStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
}
