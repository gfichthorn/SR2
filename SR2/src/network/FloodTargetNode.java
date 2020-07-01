package network;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class FloodTargetNode extends Node {
	Vector<Node> m_target;

	public FloodTargetNode(int uid, double txr, double x, double y, Vector<Node> target) {
		super(uid, txr, x, y);
		m_regularNode = false; // for purpose of looking at data afterwards
		m_target = target;
		this.setIntType(3);
		this.setType("Flood Target Node");
	}

	public void run() throws IllegalThreadStateException {
		long start = System.currentTimeMillis();
		long end = start + 5000;
		while(System.currentTimeMillis() < end) {
			//TODO make all of the threads in the thread pool do something!
			String[] msgs = {"hello", "!@#$", "greetings!", "a", "Good morning!"};
			int msg = ThreadLocalRandom.current().nextInt(0, 5);
			Packet p = new Packet(m_target, msgs[msg], false);
			MethodInfo mi = new MethodInfo(0, this, p);
			this.getQueue().add(mi);
			
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
				PacketThreadPool.execute(m);
			}

			try {
				Thread.sleep(1); //try again in 5ms so as to not overwork my computer
			} catch (InterruptedException e) {
				// caught!
			}

		}
	}

}
