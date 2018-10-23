package com;

public class Messages {

	boolean positiveAck;
	
	int maxID;
	int senderProcess;
	
	public int getMaxID() {
		return maxID;
	}

	public void setMaxID(int maxID) {
		this.maxID = maxID;
	}

	public int getSenderProcess() {
		return senderProcess;
	}

	public void setSenderProcess(int senderProcess) {
		this.senderProcess = senderProcess;
	}

	public boolean isPositiveAck() {
		return positiveAck;
	}

	public void setPositiveAck(boolean positiveAck) {
		this.positiveAck = positiveAck;
	}

	
}
