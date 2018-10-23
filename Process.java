package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

public class Process extends Thread{
	final int UNKOWN_STATUS = 0;
	final int LEADER_STATUS = 1;
	int id, index, maxId, status, round, parentId, senderId;;
	HashMap<Integer, Integer> neighbours;
	HashMap<Integer, Integer> children;
	boolean terminateProcess, posAck;
	LinkedBlockingQueue<Messages>[] msgsQueue;
	ArrayList<Messages> msgFromNeigbours;
	
	public Process(int index, int id, HashMap<Integer, Integer> neighboursIndexAndID, LinkedBlockingQueue<Messages>[] msgQueue){
		this.index = index;
		this.id = id;
		this.neighbours = neighboursIndexAndID;
		this.maxId = id;
		this.status = UNKOWN_STATUS;
		this.round = 0;
		this.terminateProcess = false;
		this.posAck = false;
		msgsQueue = msgQueue;
	}
	
	@Override
	public void run() {
		while(!terminateProcess){
			
			if(round == 0){
				//send msg to neighbours
				for(int n: neighbours.keySet()){
					msgsQueue[n].add(generateMessage());								
				}
			}
			
			else{
				//read msgs from all neighbours
				msgFromNeigbours = new ArrayList<Messages>();
				while(msgFromNeigbours.size()<neighbours.size()){
						if(msgsQueue[index].poll() != null){
							msgFromNeigbours.add(msgsQueue[index].poll());
						}
				}
				
				//get maximum from incoming ids
				
				for(Messages m: msgFromNeigbours){
					if(m.getMaxID() > maxId){
						maxId = m.getMaxID();
						senderId = m.getSenderProcess();
						
						//if round is 1, each process has the id of its neighbours so 
						//select the parent
						if(round == 1){
							//max id sender will be parent
							parentId = senderId;
							posAck = true;
							Messages forParent = generateMessage();
							for(Entry<Integer, Integer> e: neighbours.entrySet()){
								if(e.getValue() == parentId){
									msgsQueue[e.getKey()].add(forParent);
									break;
								}
							}
							posAck = false;
						}
					}
					
					//if pos ack received means sender is child
					if(m.isPositiveAck()){
						for(Entry<Integer, Integer> e: neighbours.entrySet()){
							if(e.getValue() == m.senderProcess){
								children.put(e.getKey(), e.getValue());
							}
						}
					}
				}
				
				
			}
			
		}
		
	}

	private Messages generateMessage() {
		Messages message = new Messages();
		message.setSenderProcess(id);
		message.setMaxID(maxId);
		message.setPositiveAck(posAck);
		return message;
	}
}
