package network;

import java.time.LocalTime;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Packet extends Thread{
	public byte m_size; //size of packet in bytes
	public String m_msg; //content of packet
	public Vector<Node> m_path; //path taken by packet
	public int m_forwarded; //number of nodes packet has touched
	public boolean m_b;
	final Lock lock = new ReentrantLock();
	final Condition inUse = lock.newCondition();

	/**
	 * dummy packet
	 * 
	 * @param size size of the packet in bytes
	 * @param msg content of the packet
	 * @param m_path is path taken so far. In uid
	 */
	Packet(Network network, Node n, Node d) {
		m_size = 1;
		m_msg = "a";
		m_forwarded = 0;
		m_path = network.calculatePath(n, d);
		m_b = true;
		/*for(int i = 0; i < m_path.size(); i++) {
			System.out.println(i + " " + m_path.get(i).getUid());
		}*/
	}

	/**
	 * 
	 * @param size size of the packet in bytes
	 * @param msg content of the packet
	 * @param m_path is path taken so far. In uid
	 */
	Packet(Network network, Node n, Node d, byte size, String msg) {
		m_size = size;
		m_msg = msg;
		m_forwarded = 0;
		m_path = network.calculatePath(n, d);
		m_b = true;
	}

	/**
	 * 
	 * @return size of packet
	 */
	public byte getSize() {
		return m_size;
	}

	/**
	 * 
	 * @return content of packet
	 */
	public String getMsg() {
		return m_msg;
	}

	/**
	 * 
	 * @return path taken so far by packet
	 */
	public Vector<Node> getPath() {
		return m_path;
	}

	/**
	 * 
	 * @param newVal new step taken by packet
	 * @return updated path taken by packet
	 */
	public Node addPath(Node newVal) {
		m_path.add(newVal);
		return newVal;
	}

	/**
	 * returns number of times a packet has been forwarded
	 * @return
	 */
	public int getForwarded() {
		return m_forwarded;
	}

	/**
	 * increments and returns the number of times a packet has been forwarded
	 * @return
	 */
	public int incrementForwarded() {
		return m_forwarded++;
	}

	public boolean getB() {
		return m_b;
	}

	public boolean setB(boolean b) {
		m_b = b;
		return m_b;
	}

	public void threadAction() {
		try {
			System.out.println(LocalTime.now() + " | Thread action from packet " + getMsg() + "!");
			Thread.sleep(100 * getSize());
		} catch (InterruptedException e) {
			// caught!
		}
	}

	public void signalCondition() {
		inUse.signal();
	}

	public void promptNode(Node n) throws InterruptedException {
		System.out.println(LocalTime.now() + " | Packet " + getMsg() + " is prompting node " + n.getUid());
		while(n.getActionCount() != 0) {

		}
		//n.signalCondition();
		n.incrementActionCount();
		n.setP(this);
		System.out.println(LocalTime.now() + " | Packet " + getMsg() + " has prompted node " + n.getUid());
	}

	public void action() throws InterruptedException {
		//lock.lock();
		for(int i = 0; i < m_path.size(); i++) {
			//m_path.get(i).signalCondition();
			try {
				System.out.println(LocalTime.now() + " | Packet " + getMsg() + " waiting for action");
				promptNode(m_path.get(i));
				while(m_b) {

					Thread.sleep(100);


					//inUse.await();
				}
			} finally {
				setB(true);
				System.out.println(LocalTime.now() + " | Packet " + getMsg() + " acting");
				threadAction();
				//lock.unlock();
			}

		}
		setB(false);
	}

	public void run() {
		System.out.println(LocalTime.now() + " | Packet \" + getMsg() + \" started");
		while(getB()) {
			try {
				System.out.println(LocalTime.now() + " | Packet " + getMsg() + " preparing for action");
				action();
			} catch (InterruptedException e) {
				// caught!
			}
		}
		String s = new String("");
		for(int i = 0; i < m_path.size(); i++) {
			s = s + (" " + m_path.get(i).getUid() + " ");
		}
		
		System.out.println(LocalTime.now() + " | Packet " + getMsg() + " finished. Path taken: " + s);
	}

}
