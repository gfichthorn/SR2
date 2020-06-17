package network;

import java.time.LocalTime;

/**
 * @author Greg
 *
 */
public class MethodInfo extends Thread {
	public int m_i; //my integer representation of the method
	public Node m_n; //my node
	public Packet m_p; // my packet

	/**
	 * runnable containing the all of the information needed by a node to perform its next action
	 * 
	 * @param i integer representation of method
	 * @param n current node
	 * @param p packet
	 */
	public MethodInfo(int i, Node n, Packet p) {
		m_i = i;
		m_n = n;
		m_p = p;
	}

	/**
	 * returns integer representation of method
	 * @return i
	 */
	public int getI() {
		return m_i;
	}

	/**
	 * returns current node
	 * @return n
	 */
	public Node getN() {
		return m_n;
	}

	/**
	 * returns packet
	 * @return p
	 */
	public Packet getP() {
		return m_p;
	}

	/**
	 * runnable. Does action based on i.
	 */
	public void run() {
		if(getI() == 0) {
			send();
		} else if (getI() == 1) {
			forward();
		} else if (getI() == 2) {
			receive();
		} else if (getI() == 3) {
			threadDrop();
		}
	}

	/**
	 * everything the thread for MethodInfo class does when sent
	 * @return 0
	 */
	public int threadSend() {
		try {
			Thread.sleep(100); // TODO accurate sleep time for sending
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has sent a packet.");
		return 0;
	}

	/**
	 * prompts the actions of MethodInfo, Node, Packet when sent
	 * @return 0
	 */
	public int send() {
		Node nextHop = getP().getNextNode();
		if(nextHop == getN()) { //if next hop does not change, does not send.

		} else if (nextHop == getP().getPath().lastElement()) { //if next hop last hop, sned packet to destination
			threadSend(); //everything done in sending for this class
			getN().send(getP()); //everything done in sending for the node class
			getP().send(); //everything done in sending for the packet class
			nextHop.getQueue().add(new MethodInfo(2, nextHop, getP()));
			if(getP().getRequireResponse()) {
				getN().incrementResponsesRequested();
			}
		} else { //if next hop is neither this node nor the last node, then send to a middle node
			threadSend(); //everything done in sending for this class
			getN().send(getP()); //everything done in sending for the node class
			getP().send(); //everything done in sending for the packet class
			nextHop.getQueue().add(new MethodInfo(1, nextHop, getP()));
			if(getP().getRequireResponse()) {
				getN().incrementResponsesRequested();
			}
		}
		return 0;
	}

	/**
	 * everything the thread for MethodInfo class does when forwarded
	 * @return 1
	 */
	public int threadForward() {
		try {
			Thread.sleep(100); // TODO accurate sleep time for forwarding
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has forwarded a packet.");
		return 1;
	}

	/**
	 * Prompts the actions of MethodInfo, Node, Packet when forwarded
	 * 
	 * @return 1
	 */
	public int forward() {
		Node nextHop = getP().getNextNode(); //get next hop
		if(nextHop == getN() || !getN().isInRange(nextHop)) { //if next hop does not change, fails.
			threadDrop();
			getN().drop(getP());
			getP().drop();
			nextHop.getQueue().add(new MethodInfo(3, nextHop, getP()));
		} else if(nextHop == getP().getPath().lastElement()) { //if next hop last hop, target receive
			threadForward();
			getN().forward(getP());
			getP().forward();
			nextHop.getQueue().add(new MethodInfo(2, nextHop, getP()));
		} else { //else forward
			threadForward();
			getN().forward(getP());
			getP().forward();
			nextHop.getQueue().add(new MethodInfo(1, nextHop, getP()));
		}
		return 1;
	}

	/**
	 * everything the thread for MethodInfo class does when received
	 * @return 2
	 */
	public int threadReceive() {
		try {
			Thread.sleep(100); // TODO accurate sleep time for receiving
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has received a packet.");
		return 2;
	}

	/**
	 * Prompts the actions of MethodInfo, Node, Packet when received
	 * @return 2
	 */
	public int receive() {
		threadReceive();
		getN().receive(getP());
		getP().receive();
		if(getP().getRequireResponse()) {
			Packet p = new Packet(getP().flipPath(), "responding!", false); // TODO make responding message accurate
			MethodInfo m = new MethodInfo(0, getN(), p);
			getN().getQueue().add(m);
		}
		return 2;
	}

	/**
	 * everything the thread for MethodInfo class does when dropped
	 * @return 0
	 */
	public int threadDrop() {
		try {
			Thread.sleep(100); // TODO accurate sleep time for dropping
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has dropped a packet.");
		return 3;
	}

}
