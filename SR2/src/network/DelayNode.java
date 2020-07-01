package network;

public class DelayNode extends Node {

	public DelayNode(int uid, double txr, double x, double y) {
		super(uid, txr, x, y);
		m_regularNode = false; // for purpose of looking at data afterwards
		this.setIntType(1);
		this.setType("Delay Node");
	}

	public void run() throws IllegalThreadStateException {
		long start = System.currentTimeMillis();
		long end = start + 5000;
		while(System.currentTimeMillis() < end) {
			if(getQueue().peek() != null) {
				try {
					Thread.sleep(100);
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
				Thread.sleep(5); //try again in 5ms so as to not overwork my computer
			} catch (InterruptedException e) {
				// caught!
			}

		}
	}

}

/**
 * runnable for Node. Loops endlessly. When NodeThreadQueue is not empty, performs action using thread pool.

public void run() throws IllegalThreadStateException {
	System.out.println(LocalTime.now() + " | starting thread on node " + getUid());
	while(true) {
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
			Thread.sleep(5); //try again in 5ms so as to not overwork my computer
		} catch (InterruptedException e) {
			// caught!
		}

	}
}*/