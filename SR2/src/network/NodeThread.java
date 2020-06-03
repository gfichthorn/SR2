package network;
import java.util.concurrent.*;

public class NodeThread extends Thread {

	public void run() {
		for(int i = 0; i < 500; i++) {
			System.out.println(i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				//caught!
			}
		}
		
	}
	
	public static void main(String[] args) {
		Thread t1 = new NodeThread();
		NodeThread t2 = new NodeThread();
		
		ExecutorService NodeThreadPool = Executors.newFixedThreadPool(1);
		
		NodeThreadPool.execute(t1);
		NodeThreadPool.execute(t2);
		System.out.print("Done!");
	}
	
	/*
	package network;
	import java.util.concurrent.*;

	public class NodeThread extends Thread {

		public void run(int n) {
			/**
			 * send
			 *
			if(n == 0) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					//caught!
				}
				/**
				 * forward (come in then go out)
				 *
			} else if (n == 1) {
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					// caught!
				}
				/**
				 * receive
				 *
			} else if (n == 2) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// caught!
				}
				/**
				 * drop
				 *
			} else if (n == 3) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// caught!
				}
			}
			
		}

	}
	*/

}
