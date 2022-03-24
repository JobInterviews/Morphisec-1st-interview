package com.morphisec.interview;


import java.util.Vector;

public class ProcessItems {
	
	public static void main(String[] args) throws Exception {

		Vector<Item> sharedQueue = new Vector<Item>();
		int size = 20;
		Thread prodThread = new Thread(new Producer(sharedQueue, size),
				"Producer");
		Thread consThread = new Thread(new Consumer(sharedQueue), "Consumer");		
		prodThread.start();
		consThread.start();
	}
}
