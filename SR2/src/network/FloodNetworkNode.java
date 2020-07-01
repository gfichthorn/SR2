package network;

import java.time.LocalTime;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class FloodNetworkNode extends Node {
	Vector<Vector<Node>> m_broadcast;
	
	public FloodNetworkNode(int uid, double txr, double x, double y, Vector<Vector<Node>> broadcast) {
		super(uid, txr, x, y);
		m_regularNode = false; // for purpose of looking at data afterwards
		m_broadcast = broadcast;
		this.setIntType(3);
		this.setType("Flood Broadcast Node");
	}
	
	public void run() throws IllegalThreadStateException {
		long start = System.currentTimeMillis();
		long end = start + 5000;
		while(System.currentTimeMillis() < end) {
			int n = ThreadLocalRandom.current().nextInt(0, 20);
			String[] msgs = {"hello", "!@#$", "greetings!", "a", "Good morning!"}; // TODO get all messages that I want
			int msg = ThreadLocalRandom.current().nextInt(0, 5);
			Packet p = new Packet(m_broadcast.get(n), msgs[msg], false);
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