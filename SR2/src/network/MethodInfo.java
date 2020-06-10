package network;

import java.time.LocalTime;

public class MethodInfo extends Thread{
	public int m_i; //my int representation of the method
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

	public int getI() {
		return m_i;
	}

	public Node getN() {
		return m_n;
	}

	public Packet getP() {
		return m_p;
	}

	public void run() {
		if(getI() == 0) {
			send();
		} else if (getI() == 1) {
			forward();
		} else if (getI() == 2) {
			receive();
		} else if (getI() == 3) {
			//drop
		}
	}

	public int threadSend() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has sent a packet.");
		return 0;
	}

	public void send() {
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
	}

	public int threadForward() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has forwarded a packet.");
		return 1;
	}

	/**
	 * forwards the packet to the next hop
	 * 
	 * @param n current node
	 * @param d destination node
	 * @param p packet
	 * @return
	 */
	public void forward() {
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
	}

	public int threadReceive() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has received a packet.");
		return 2;
	}

	public void receive() {
		threadReceive();
		getN().receive(getP());
		getP().receive();
		if(getP().getRequireResponse()) {
			Packet p = new Packet(getP().flipPath(), "responding!", false);
			MethodInfo m = new MethodInfo(0, getN(), p);
			getN().getQueue().add(m);
		}
	}

	public int threadDrop() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//caught!
		}
		System.out.println(LocalTime.now() + " | node " + getN().getUid() +  " at time " + LocalTime.now() + " has dropped a packet.");
		return 3;
	}

}
