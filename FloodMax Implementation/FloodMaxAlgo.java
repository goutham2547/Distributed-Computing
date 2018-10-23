package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class FloodMaxAlgo{
	
	public void run(){
		
	}
	
	public static void main(String[] args) {
		int n = 0;
		HashMap<Integer, Integer> neighboursIndexAndID;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("enter no. of processes:");
			n = Integer.parseInt(br.readLine());
			
			Process processes[] = new Process[n];
			int processId[] = new int[n];
			int links[][] = new int[n][n];
			LinkedBlockingQueue<Messages> msgQueue[] = new LinkedBlockingQueue[n];
			
			for(int i=0; i<n; i++){
				System.out.println("Enter id of process "+(i+1));
				processId[i] = br.read();
				for(int j=0; j<n; j++){
					System.out.println("Enter connectivity link between process "+(i+1)+" and "+(j+1));
					links[i][j] = Integer.parseInt(br.readLine());
					
				}
			}
			
			//finding neighbours of each process and creating process objects
			for(int i=0; i<n; i++){
				neighboursIndexAndID = new HashMap<Integer, Integer>();
				for(int j=0; j<n; j++){
					if(links[i][j] == 1){
						neighboursIndexAndID.put(j, processId[j]);
					}
				}
				processes[i] = new Process(i, processId[i], neighboursIndexAndID, msgQueue);
			}
			
			//starting all processes (threads)
			for(Process p: processes){
				p.start();
			}
			
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
