package network;

import java.util.concurrent.ThreadLocalRandom;

public class ForgetfulNode extends Node {
	int m_dropChance;

	public ForgetfulNode(int uid, double txr, double x, double y) {
		super(uid, txr, x, y);
		m_dropChance = 50; // TODO needs accurate drop chance
		m_regularNode = false; // for purpose of looking at data afterwards
		this.setIntType(4);
		this.setType("Forgetful Node");
	}
	
	public void run() throws IllegalThreadStateException {
		long start = System.currentTimeMillis();
		long end = start + 5000;
		while(System.currentTimeMillis() < end) {
			if(getQueue().peek() != null) {
				try {
					lock.lock();
				} catch (Exception e) {
					//caught!!
				}
				MethodInfo m = getQueue().peek();
				try {
					getQueue().poll(); //the locks should keep multiple threads from the thread pool from beginning the same method
				} finally {
					try {
						lock.unlock();
					} catch (Exception e) {
						//caught!!
					}
				}
				int drop = ThreadLocalRandom.current().nextInt(0, 100);
				if(m_dropChance > drop) {
					PacketThreadPool.execute(m);
				}
				
			}

			try {
				Thread.sleep(5); //try again in 5ms so as to not overwork my computer
			} catch (InterruptedException e) {
				// caught!
			}

		}
	}

}
