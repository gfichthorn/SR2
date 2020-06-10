package network;

//import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.time.*;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.*;

public class Node extends Thread{
	public int m_uid; //unique identifier
	public double m_txr; //transmission range
	public int m_sent; //number of packets sent by this node
	public int m_forwarded; //number of packets received then forwarded by this node
	public int m_received; //number of packets received with this node as the destination
	public int m_dropped; // number of packet dropped by this node
	public int m_bytesSent; // number of bytes sent by this node
	public int m_bytesForwarded; // number of bytes forwarded by this node
	public int m_bytesReceived; // number of bytes received by this node
	public int m_bytesDropped; // number of bytes dropped by this node
	public int m_responsesRequested;
	public int m_responsesReceived;
	public double m_x; //x-coordinate
	public double m_y; //y-coordinate
	public Queue<MethodInfo> NodeThreadQueue = new LinkedList<MethodInfo>();
	public Vector<String> m_receivedMessages = new Vector<String>();
	ExecutorService PacketThreadPool = Executors.newFixedThreadPool(5);
	public Lock lock;


	/**
	 * simple node with a unique identifier and transmission range
	 * 
	 * @param uid unique identifier
	 * @param txr transmission range
	 * @param m_sent number of messages sent by this node
	 * @param m_forwarded number of messages received then forwarded by this node
	 * @param m_received number of messages received with this node as the destination
	 * @param m_dropped number of messages dropped by this node
	 * @param m_on is the node on or off
	 */
	public Node(int uid, double txr, double x, double y)
	{
		m_uid = uid;
		m_txr = Math.abs(txr); // Transmission range must be positive
		if (m_txr == 0) {
			m_txr = 1;
		}
		m_sent = 0;
		m_forwarded = 0;
		m_received = 0;
		m_dropped = 0;
		m_bytesSent = 0;
		m_bytesForwarded = 0;
		m_bytesReceived = 0;
		m_bytesDropped = 0;
		m_x = x;
		m_y = y;
		m_responsesRequested = 0;
		m_responsesReceived = 0;
	}

	/**
	 * 
	 * @return unique identifier of node
	 */
	public int getUid() {
		return m_uid;
	}

	/**
	 * 
	 * @return transmission range
	 */
	public double getTxr() {
		return m_txr;
	}

	/**
	 * 
	 * @return number of messages sent by this node
	 */
	public int getSent() {
		return m_sent;
	}

	/**
	 * 
	 * @return incremented number of messages sent by this node
	 */
	public int incrementSent() {
		return m_sent++;
	}

	/**
	 * 
	 * @return number of message forwarded by this node
	 */
	public int getForwarded() {
		return m_forwarded;
	}

	/**
	 * 
	 * @return incremented number of messages forwarded by this node
	 */
	public int incrementForwarded() {
		return m_forwarded++;
	}

	/**
	 * 
	 * @return number of packets received by this node
	 */
	public int getReceived() {
		return m_received;
	}

	/**
	 * 
	 * @return incremented number of messages received by this node
	 */
	public int incrementReceived() {
		return m_received++;
	}

	/**
	 * 
	 * @return number of packets received by this node
	 */
	public int getDropped() {
		return m_dropped;
	}

	/**
	 * 
	 * @return incremented number of messages received by this node
	 */
	public int incrementDropped() {
		return m_dropped++;
	}

	/**
	 * 
	 * @return number of bytes sent by this node
	 */
	public int getBytesSent() {
		return m_bytesSent;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes sent
	 * @return increased number of bytes sent by this node
	 */
	public int increaseBytesSent(byte[] b) {
		m_bytesSent += b.length;
		return m_bytesSent;
	}

	/**
	 * 
	 * @return number of bytes forwarded by this node
	 */
	public int getBytesForwarded() {
		return m_bytesForwarded;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes forwarded
	 * @return increased number of bytes forwarded by this node
	 */
	public int increaseBytesForwarded(byte[] b) {
		m_bytesForwarded += b.length;
		return m_bytesForwarded;
	}

	/**
	 * 
	 * @return number of bytes received by this node
	 */
	public int getBytesReceived() {
		return m_bytesReceived;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes received
	 * @return increased number of bytes received by this node
	 */
	public int increaseBytesReceived(byte[] b) {
		m_bytesReceived += b.length;
		return m_bytesReceived;
	}

	/**
	 * 
	 * @return number of bytes dropped by this node
	 */
	public int getBytesDropped() {
		return m_bytesDropped;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes dropped
	 * @return increased number of bytes dropped by this node
	 */
	public int increaseBytesDropped(byte[] b) {
		m_bytesDropped += b.length;
		return m_bytesDropped;
	}

	/**
	 * prints the contents of a packet
	 * 
	 * @param p: packet
	 * @return
	 */
	public String printPacket(Packet p) {
		return p.getMsg();
	}

	/**
	 * returns x location
	 * 
	 * @return
	 */
	public double getX() {
		return m_x;
	}

	/**
	 * sets and returns new x location
	 * 
	 * @param nx: new x
	 * @return
	 */
	public double setX(int nx) {
		m_x = nx;
		return m_x;
	}

	/**
	 * returns y location
	 * @return
	 */
	public double getY() {
		return m_y;
	}

	/**
	 * sets and returns y location
	 * 
	 * @param ny
	 * @return
	 */
	public double setY(int ny) {
		m_y = ny;
		return m_y;
	}

	public Queue<MethodInfo> getQueue() {
		return NodeThreadQueue;
	}

	public Vector<String> getReceivedMessages() {
		return m_receivedMessages;
	}

	public Vector<String> addToReceivedMessages(String s) {
		getReceivedMessages().add(s);
		if(s == "responding!") {
			incrementResponsesReceived();
		}
		return m_receivedMessages;
	}

	public int getResponsesRequested() {
		return m_responsesRequested;
	}

	public int incrementResponsesRequested() {
		return m_responsesRequested++;
	}

	public int getResponsesReceived() {
		return m_responsesReceived;
	}

	public int incrementResponsesReceived() {
		return m_responsesReceived++;
	}

	public void send(Packet p) {
		incrementSent();
		increaseBytesSent(p.getMsg().getBytes());
	}

	public void forward(Packet p) {
		incrementForwarded();
		increaseBytesForwarded(p.getMsg().getBytes());
	}

	public void receive(Packet p) {
		incrementReceived();
		increaseBytesReceived(p.getMsg().getBytes());
		this.addToReceivedMessages(p.getMsg());
	}

	public void drop(Packet p) {
		incrementDropped();
		increaseBytesDropped(p.getMsg().getBytes());
	}

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
				Thread.sleep(5); //try again in 50ms so as to not overwork my computer
			} catch (InterruptedException e) {
				// caught!
			}

		}
	}

	/**
	 * determines the distance between two nodes, n1 and n2.
	 * 
	 * @param n1 node 1
	 * @param n2 node 2
	 * @return
	 */
	public double determineDistance(Node n1, Node n2) {
		double a = Math.abs(n1.getX() - n2.getX()); //a = x1-x2
		double b = Math.abs(n1.getY() - n2.getY()); //b = y1-y2
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)); //a^2 + b^2 = c^2 -> c = sqrt(a^2 + b^2);
	}

	public boolean isInRange(Node n) {
		double a = Math.abs(getX() - n.getX()); //a = x1-x2
		double b = Math.abs(getY() - n.getY()); //b = y1-y2

		if(getTxr() < Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))) {
			return false;
		}
		return true;
	}

}
