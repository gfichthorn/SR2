package network;

import java.util.concurrent.locks.Lock;
import java.time.*;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * @author Greg
 *
 */
public class Node extends Thread {
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
	public int m_responsesRequested; // number of packets sent requesting a response
	public int m_responsesReceived; // number of packets received requesting a response
	public double m_x; //x-coordinate
	public double m_y; //y-coordinate
	public Queue<MethodInfo> NodeThreadQueue = new LinkedList<MethodInfo>(); // queue of actions to be done by node
	public Vector<String> m_receivedMessages = new Vector<String>(); // list of received strings
	ExecutorService PacketThreadPool = Executors.newFixedThreadPool(5); // thread pool to work on NodeThreadQueue
	public Lock lock; // ensure that multiple threads from a pool dont enter critical section simultaneously
	public boolean m_regularNode; // for purposes of collecting data after a simulation ends

	/**
	 * runnable node with a unique identifier, transmission range, and positional x and y coordinates. Uses packets, contained within a network of nodes.
	 * @param uid unique identifier
	 * @param txr transmission range
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Node(int uid, double txr, double x, double y) {
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
		m_regularNode = true;
	}

	/**
	 * returns unique identifier of node
	 * @return m_uid
	 */
	public int getUid() {
		return m_uid;
	}

	/**
	 * returns transmission range
	 * @return m_txr
	 */
	public double getTxr() {
		return m_txr;
	}

	/**
	 * returns number of messages sent by this node
	 * @return m_sent
	 */
	public int getSent() {
		return m_sent;
	}

	/**
	 * returns incremented number of messages sent by this node
	 * @return m_sent
	 */
	public int incrementSent() {
		return m_sent++;
	}

	/**
	 * returns number of message forwarded by this node
	 * @return m_forwarded
	 */
	public int getForwarded() {
		return m_forwarded;
	}

	/**
	 * returns incremented number of messages forwarded by this node
	 * @return m_forwarded
	 */
	public int incrementForwarded() {
		return m_forwarded++;
	}

	/**
	 * returns number of packets received by this node
	 * @return m_received
	 */
	public int getReceived() {
		return m_received;
	}

	/**
	 * returns incremented number of messages received by this node
	 * @return m_received
	 */
	public int incrementReceived() {
		return m_received++;
	}

	/**
	 * returns number of packets received by this node
	 * @return m_dropped
	 */
	public int getDropped() {
		return m_dropped;
	}

	/**
	 * returns incremented number of messages received by this node
	 * @return m_dropped
	 */
	public int incrementDropped() {
		return m_dropped++;
	}

	/**
	 * returns number of bytes sent by this node
	 * @return m_bytesSent
	 */
	public int getBytesSent() {
		return m_bytesSent;
	}

	/**
	 * returns increased number of bytes sent by this node
	 * @param b number of bytes to increase total bytes sent
	 * @return m_bytesSent
	 */
	public int increaseBytesSent(byte[] b) {
		m_bytesSent += b.length;
		return m_bytesSent;
	}

	/**
	 * returns number of bytes forwarded by this node
	 * @return m_bytesForwarde
	 */
	public int getBytesForwarded() {
		return m_bytesForwarded;
	}

	/**
	 * returns increased number of bytes forwarded by this node
	 * @param b number of bytes to increase total bytes forwarded
	 * @return m_bytesForwarded
	 */
	public int increaseBytesForwarded(byte[] b) {
		m_bytesForwarded += b.length;
		return m_bytesForwarded;
	}

	/**
	 * returns number of bytes received by this node
	 * @return m_bytesReceived
	 */
	public int getBytesReceived() {
		return m_bytesReceived;
	}

	/**
	 * returns increased number of bytes received by this node
	 * @param b number of bytes to increase total bytes received
	 * @return m_bytesReceived
	 */
	public int increaseBytesReceived(byte[] b) {
		m_bytesReceived += b.length;
		return m_bytesReceived;
	}

	/**
	 * returns number of bytes dropped by this node
	 * @return m_bytesDropped
	 */
	public int getBytesDropped() {
		return m_bytesDropped;
	}

	/**
	 * returns increased number of bytes dropped by this node
	 * @param b number of bytes to increase total bytes dropped
	 * @return m_bytesDropped
	 */
	public int increaseBytesDropped(byte[] b) {
		m_bytesDropped += b.length;
		return m_bytesDropped;
	}

	/**
	 * returns x location
	 * 
	 * @return m_x
	 */
	public double getX() {
		return m_x;
	}

	/**
	 * sets and returns new x location
	 * 
	 * @param nx new x
	 * @return m_x
	 */
	public double setX(int nx) {
		m_x = nx;
		return m_x;
	}

	/**
	 * returns y location
	 * @return m_y
	 */
	public double getY() {
		return m_y;
	}

	/**
	 * sets and returns y location
	 * 
	 * @param ny new y
	 * @return m_y
	 */
	public double setY(int ny) {
		m_y = ny;
		return m_y;
	}

	/**
	 * returns the queue of actions to be taken by node
	 * @return NodeThreadQueue
	 */
	public Queue<MethodInfo> getQueue() {
		return NodeThreadQueue;
	}

	/**
	 * returns all of messages received by node
	 * @return m_receivedMessages
	 */
	public Vector<String> getReceivedMessages() {
		return m_receivedMessages;
	}

	/**
	 * adds s to received messages and returns all of messages received by node including s
	 * @param s string
	 * @return m_receivedMessages
	 */
	public Vector<String> addToReceivedMessages(String s) {
		getReceivedMessages().add(s);
		if(s == "responding!") {
			incrementResponsesReceived();
		}
		return m_receivedMessages;
	}

	/**
	 * returns number of packets sent requesting a response
	 * @return m_responsesRequested
	 */
	public int getResponsesRequested() {
		return m_responsesRequested;
	}

	/**
	 * increments and returns number of packets sent requesting a response
	 * @return m_responsesRequested
	 */
	public int incrementResponsesRequested() {
		return m_responsesRequested++;
	}

	/**
	 * returns number of packets received requesting a response
	 * @return m_responsesReceived
	 */
	public int getResponsesReceived() {
		return m_responsesReceived;
	}

	/**
	 * increments and returns number of packets received requesting a response
	 * @return m_responsesReceived
	 */
	public int incrementResponsesReceived() {
		return m_responsesReceived++;
	}

	/**
	 * all actions done by node when sending a packet
	 * @param p packet
	 */
	public void send(Packet p) {
		incrementSent();
		increaseBytesSent(p.getMsg().getBytes());
	}

	/**
	 * all actions done by node when forwarding a packet
	 * @param p packet
	 */
	public void forward(Packet p) {
		incrementForwarded();
		increaseBytesForwarded(p.getMsg().getBytes());
	}

	/**
	 * all actions done by node when receiving a packet
	 * @param p packet
	 */
	public void receive(Packet p) {
		incrementReceived();
		increaseBytesReceived(p.getMsg().getBytes());
		this.addToReceivedMessages(p.getMsg());
	}

	/**
	 * all actions done by node when dropping a packet
	 * @param p packet
	 */
	public void drop(Packet p) {
		incrementDropped();
		increaseBytesDropped(p.getMsg().getBytes());
	}
	
	public boolean getIsRegular() {
		return m_regularNode;
	}

	/**
	 * runnable for Node. Loops endlessly. When NodeThreadQueue is not empty, performs action using thread pool.
	 */
	public void run() throws IllegalThreadStateException {
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
	}

	/**
	 * determines and returns the distance between two nodes, n1 and n2.
	 * 
	 * @param n1 node 1
	 * @param n2 node 2
	 * @return distance between n1 and n2
	 */
	public double determineDistance(Node n1, Node n2) {
		double a = Math.abs(n1.getX() - n2.getX()); //a = x1-x2
		double b = Math.abs(n1.getY() - n2.getY()); //b = y1-y2
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)); //a^2 + b^2 = c^2 -> c = sqrt(a^2 + b^2);
	}

	/**
	 * returns boolean value if two nodes are within transmission range of each other
	 * 
	 * @param n other node
	 * @return boolean if two nodes are in transmission range of each other
	 */
	public boolean isInRange(Node n) {
		double a = Math.abs(getX() - n.getX()); //a = x1-x2
		double b = Math.abs(getY() - n.getY()); //b = y1-y2

		if(getTxr() < Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))) {
			return false;
		}
		return true;
	}

}
