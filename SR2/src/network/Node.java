package network;

//import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
	public byte m_bytesSent; // number of bytes sent by this node
	public byte m_bytesForwarded; // number of bytes forwarded by this node
	public byte m_bytesReceived; // number of bytes received by this node
	public byte m_bytesDropped; // number of bytes dropped by this node
	public boolean m_on; //is the node on or off
	public double m_x; //x-coordinate
	public double m_y; //y-coordinate
	public Queue<MethodInfo> NodeThreadQueue = new LinkedList<MethodInfo>();
	public boolean m_isSending;
	public boolean m_isForwarding;
	public boolean m_isReceiving;
	public int m_actionCount;
	final Lock lock = new ReentrantLock();
	final Condition inUse = lock.newCondition();
	public Packet m_p;
	public Vector<String> m_receivedMessages = new Vector<String>();
	//final Lock lock = new ReentrantLock();
	//final Condition canAct  = lock.newCondition();
	//final Condition canReceive = lock.newCondition();


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
		m_on = true;
		m_x = x;
		m_y = y;

		m_isSending = false;
		m_isForwarding = false;
		m_isReceiving = false;

		//start();
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
	public byte getBytesSent() {
		return m_bytesSent;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes sent
	 * @return increased number of bytes sent by this node
	 */
	public byte increaseBytesSent(byte b) {
		m_bytesSent += b;
		return m_bytesSent;
	}

	/**
	 * 
	 * @return number of bytes forwarded by this node
	 */
	public byte getBytesForwarded() {
		return m_bytesForwarded;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes forwarded
	 * @return increased number of bytes forwarded by this node
	 */
	public byte increaseBytesForwarded(byte b) {
		m_bytesForwarded += b;
		return m_bytesForwarded;
	}

	/**
	 * 
	 * @return number of bytes received by this node
	 */
	public byte getBytesReceived() {
		return m_bytesReceived;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes received
	 * @return increased number of bytes received by this node
	 */
	public byte increaseBytesReceived(byte b) {
		m_bytesReceived += b;
		return m_bytesReceived;
	}

	/**
	 * 
	 * @return number of bytes dropped by this node
	 */
	public byte getBytesDropped() {
		return m_bytesDropped;
	}

	/**
	 * 
	 * @param b number of bytes to increase total bytes dropped
	 * @return increased number of bytes dropped by this node
	 */
	public byte increaseBytesDropped(byte b) {
		m_bytesDropped += b;
		return m_bytesDropped;
	}


	/**
	 * 
	 * @return is it on
	 */
	public boolean getOn() {
		return m_on;
	}

	/**
	 * 
	 * @param newVal new state for node
	 * @return new state
	 */
	public boolean setOn(boolean newVal) {
		m_on = newVal;
		return m_on;
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

	public boolean getIsSending() {
		return m_isSending;
	}

	public boolean setIsSending(boolean b) {
		m_isSending = b;
		return m_isSending;
	}

	public boolean getIsForwarding() {
		return m_isForwarding;
	}

	public boolean setIsForwarding(boolean b) {
		m_isForwarding = b;
		return m_isForwarding;
	}

	public boolean getIsReceiving() {
		return m_isReceiving;
	}

	public boolean setIsReceiving(boolean b) {
		m_isReceiving = b;
		return m_isReceiving;
	}

	public Packet getP() {
		return m_p;
	}

	public Packet setP(Packet p) {
		m_p = p;
		return m_p;
	}

	public int getActionCount() {
		return m_actionCount;
	}

	public int incrementActionCount() {
		return m_actionCount++;
	}

	public int decrementActionCount() {
		return m_actionCount--;
	}
	
	public Vector<String> getReceivedMessages() {
		return m_receivedMessages;
	}
	
	public Vector<String> addToReceivedMessages(String s) {
		getReceivedMessages().add(s);
		return m_receivedMessages;
	}

	public void threadSend(Node d, Packet p) {
		setIsSending(true);
		System.out.println("node " + getUid() +  " at time " + LocalTime.now() + " is sending.");
		/*try {
			//System.out.println("thread send 1");
			Thread.sleep(10);
			//System.out.println("thread send 2");
			//sendPacket(NodeList, d, p);
			//System.out.println("thread send 3");
		} catch (InterruptedException e) {
			System.out.println("caught");
		}*/

		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is sent.");
		setIsSending(false);
	}

	public void threadForward(Node d, Packet p) {
		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is forwarding.");
		try {
			Thread.sleep(75);
			//forwardPacket(NodeList, d, p);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is forwarded.");
	}

	public void threadReceive(Node d, Packet p) {
		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is receiving.");
		try {
			Thread.sleep(150);
			//receivePacket(d, p);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is received.");
	}

	public void threadDrop() {
		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is dropping.");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		System.out.println("Thread: " + getId() + " on node " + getUid() +  " at time " + LocalTime.now() + " is dropped.");
	}

	/*public void threadAction() {
		System.out.println("Thread action i: " + NodeThreadQueue.peek().getI() + " on node " + getUid());
		if(NodeThreadQueue.peek().getI() == 0) {
			System.out.println("thread action send on node" + getUid());
			threadSend(NodeThreadQueue.peek().getD(), NodeThreadQueue.peek().getP());
		} else if(NodeThreadQueue.peek().getI() == 1) {
			System.out.println("thread action forward on node" + getUid());
			threadForward(NodeThreadQueue.peek().getD(), NodeThreadQueue.peek().getP());
		} else if(NodeThreadQueue.peek().getI() == 2) {
			System.out.println("thread action recieve on node" + getUid());
			threadReceive(NodeThreadQueue.peek().getD(), NodeThreadQueue.peek().getP());
		} else if(NodeThreadQueue.peek().getI() == 3){
			threadDrop();
		}
		getQueue().remove();
		/*System.out.println(NodeThreadQueue.isEmpty() + " is it empty?1");
		NodeThreadQueue.remove();
		System.out.println(NodeThreadQueue.isEmpty() + " is it empty?2");
	}*/

	public void run () {

		System.out.println(LocalTime.now() + " | starting thread " + " on node " + getUid());
		//System.out.println("starting thread " + getId() + " on node " + getUid());
		while(true) {
			while(getActionCount() != 0) {
				System.out.println(LocalTime.now() + " | Node " + getUid() + " has action available");
				try {
					action(m_p);
				} catch (InterruptedException e) {
					//caught
				}

			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// caught!
			}

		}
		/*{
			while(!m_on) {
				try {
					this.wait(100);
				} catch (InterruptedException e) {
				}
			}
			while(m_on) {
				if(!NodeThreadQueue.isEmpty()) {
					ThreadAction();
				} else {
					m_on = false;
				}
			}

		}*/
	}

	public void action(Packet p) throws InterruptedException {
		//lock.lock();
		System.out.println(LocalTime.now() + " | Node " + getUid() + " attempting action!");
		try {
			while(getActionCount() == 0) {
				//inUse.await();
			}
		} finally {
			//p.signalCondition();
			System.out.println(LocalTime.now() + " | Node " + getUid() + " is performing action!");
			p.setB(false);
			threadAction();
			//lock.unlock();
		}
		decrementActionCount();
		System.out.println(LocalTime.now() + " | Node " + getUid() + " done with action!");
	}

	public void threadAction() {
		try {
			System.out.println(LocalTime.now() + " | Node " + getUid() + " attempting thread stuff!");
			Thread.sleep(100 * m_p.getSize());
			System.out.println(LocalTime.now() + " | Node " + getUid() + " done with thread stuff!");
		} catch (InterruptedException e) {
			// caught!
		}
	}

	public void signalCondition() {
		inUse.signal();
	}

	public void promptPacket(Packet p) throws InterruptedException {
		p.signalCondition();
	}

	public Packet getPacket(Packet p) {
		return p;
	}

}
