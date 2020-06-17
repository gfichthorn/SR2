package network;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Greg
 *
 */
public class Network {
	public int NodeListSize;
	public Vector<Node> NodeList;

	/**
	 * Network containing nodes and performing actions that requires all nodes
	 */
	public Network() {
		NodeList = new Vector<Node>();
		NodeListSize = 0;
		setupTestNodes();
	}

	/**
	 * returns number of nodes in network
	 * @return NodeListSize
	 */
	public int getNodeListSize() {
		return NodeListSize;
	}

	/**
	 * creates new node and adds to vector of all nodes. Updates count of all nodes, NodeListSize.
	 * 
	 * @param txr transmission range
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return New Node
	 */
	public Node addNode(double txr, double x, double y) {
		Node n = new Node(NodeListSize++, txr, x, y);
		NodeList.add(n);
		n.start();
		return n;
	}
	
	/**
	 * adds an existing node to NodeList and returns it
	 * @param n new node
	 * @return n
	 */
	public Node addNode(Node n) {
		NodeList.add(n);
		return n;
	}

	/**
	 * returns node at index
	 * @param index index of node you want
	 * @return NodeList.get(index)
	 */
	public Node getNode(int index) {
		return NodeList.get(index);
	}

	/**
	 * returns the list of nodes
	 * @return NodeList
	 */
	public Vector<Node> getNodeList() {
		return NodeList;
	}

	/**
	 * returns overall number of packets sent by all nodes in NodeList
	 * @return c: counter of packets sent
	 */
	public int getOverallSent() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getSent();
		}
		return c;
	}

	/**
	 * returns overall number of packets forwarded by all nodes in NodeList
	 * @return c: counter of packets forwarded
	 */
	public int getOverallForwarded() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getForwarded();
		}
		return c;
	}

	/**
	 * returns overall number of packets received by all nodes in NodeList
	 * @return c: counter of packets received
	 */
	public int getOverallReceived() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getReceived();
		}
		return c;
	}

	/**
	 * returns overall number of packets dropped by all nodes in node list
	 * @return c: counter of packets dropped
	 */
	public int getOverallDropped() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getDropped();
		}
		return c;
	}

	/**
	 * returns overall number of bytes sent by all nodes in node list
	 * @return c: c: counter of bytes sent
	 */
	public int getOverallBytesSent() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getBytesSent();
		}
		return c;
	}

	/**
	 * returns overall number of bytes forwarded by all nodes in node list
	 * @return c: counter of bytes forwarded
	 */
	public int getOverallBytesForwarded() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getBytesForwarded();
		}
		return c;
	}

	/**
	 * returns overall number of bytes received by all nodes in node list
	 * @return c: counter of bytes received
	 */
	public int getOverallBytesReceived() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getBytesReceived();
		}
		return c;
	}

	/**
	 * returns overall number of bytes dropped by all nodes in node list
	 * @return c: counter of bytes dropped
	 */
	public int getOverallBytesDropped() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getBytesDropped();
		}
		return c;
	}

	/**
	 * returns overall number of packets sent requesting a response
	 * @return c: counter of packets sent requesting a response
	 */
	public int getOverallPacketsRequestingResponse() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getResponsesRequested();
		}
		return c;
	}

	/**
	 * returns overall number of packets received requesting a response
	 * @return c: counter of packets received requesting a response
	 */
	public int getOverallPacketsReceivingResponse() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getResponsesReceived();
		}
		return c;
	}

	/**
	 * prints out all statistics tracked by nodes: getOverallSent(), getOverallBytesSent(), getOverallForwarded(), 
	 * getOverallBytesForwarded(), getOverallReceived(), getOverallBytesReceived(), getOverallDropped(), 
	 * getOverallBytesDropped(), getOverallPacketsRequestingResponse(), getOverallPacketsReceivingResponse()
	 */
	public void printAll() {
		System.out.println("----------------------------------------------");
		System.out.println("Number of packets sent by all nodes: " + getOverallSent());
		System.out.println("Number of bytes sent by all nodes: " + getOverallBytesSent());
		System.out.println("Number of packets forwarded by all nodes: " + getOverallForwarded());
		System.out.println("Number of bytes forwarded by all nodes: " + getOverallBytesForwarded());
		System.out.println("Number of packets received by all nodes: " + getOverallReceived());
		System.out.println("Number of bytes received by all nodes: " + getOverallBytesReceived());
		System.out.println("Number of packets dropped by all nodes: " + getOverallDropped());
		System.out.println("Number of bytes dropped by all nodes: " + getOverallBytesDropped());
		System.out.println("Number of packets requesting a response sent by all nodes: " + getOverallPacketsRequestingResponse());
		System.out.println("Number of packets received by all nodes in response to a request: " + getOverallPacketsReceivingResponse());
		System.out.println("----------------------------------------------");
	}

	/**
	 * determines and returns the best candidate for next hop for packet
	 * 
	 * @param n packet is at this node
	 * @param d packet wants to be at this node
	 * @return node within transmission range, closest to d
	 */
	public Node determineNextHop(Node n, Node d) {
		double nd_dist, ni_dist, id_dist, min_id_dist;
		int min_id_dist_node_id = n.getUid();
		nd_dist = determineDistance(n, d); // determine distance of n to d
		min_id_dist = nd_dist;

		for(int i = 0; i < NodeList.size(); i++) {
			ni_dist = determineDistance(n, NodeList.get(i)); // distance of n to intermediary node i
			id_dist = determineDistance(NodeList.get(i), d); // distance of i to d

			if (ni_dist > n.getTxr()) { 
				//i is outside of n's transmission range
			} else if (ni_dist == nd_dist) {
				return d; // d node is within transmission range
			}
			else if (ni_dist > nd_dist) { 
				//i is further away from n than d is from n
			} else { // is eligible
				if (min_id_dist > id_dist) {// pick i closest to d
					min_id_dist = id_dist;
					min_id_dist_node_id = i;
				}
			}
		}
		return NodeList.get(min_id_dist_node_id);
	}

	/**
	 * determines the distance between two nodes, n1 and n2.
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
	 * node receives a packet, increments packetsReceived, returns packet
	 * 
	 * @param n node receiving packet
	 * @param p packet being received
	 * @return packet
	 */
	public boolean receivePacket(Node d, Packet p) {
		d.incrementReceived();
		d.increaseBytesReceived(p.getMsg().getBytes());
		return true;
	}

	/**
	 * calculates and returns a vector of nodes (the path) for the packet to go from node n to node d
	 * @param n starting node
	 * @param d destination node
	 * @return v: vector of nodes to traverse
	 */
	public Vector<Node> calculatePath(Node n, Node d) {
		Vector<Node> v = new Vector<Node>();
		Node i = n;
		v.add(n);
		while(this.determineNextHop(i, d) != i) {
			i = determineNextHop(i, d);
			v.add(i);
		}

		if(i != d) { // could not reach destination
			v.add(d);
		}
		return v;
	}

	/**
	 * sets up a set of test nodes
	 */
	public void setupTestNodes() {
		addNode(5, 0, 0); // 0th node
		addNode(5, 3, 4); // 1st node
		addNode(5, 4, 3); // 2nd node
		addNode(5, -3, -4); // 3rd node
		addNode(5, -4, -3); // 4th node
		addNode(5, 7, 7); // 5th node
		addNode(5, -7, -7); // 6th node
		addNode(5, 2, 2); // 7th node
		addNode(5, -2, -2); // 8th node
		addNode(5, 5, 0); // 9th node
		addNode(5, 0, 5); // 10th node
		addNode(5, -5, 0); // 11th node
		addNode(5, 0, -5); // 12th node
		addNode(5, -3, 4); // 13th node
		addNode(5, -4, 3); // 14th node
		addNode(5, 3, -4); // 15th node
		addNode(5, 4, -3); // 16th node
		addNode(5, -7, 7); // 17th node
		addNode(5, 7, -7); // 18th node
		addNode(5, -2, 2); // 19th node
		addNode(5, 2, -2); // 20th node
		addNode(5, 100, 100); // 21st node. Outside the range of every other node
	}

	/**
	 * send a message from node n to node d with message msg
	 * @param n starting node
	 * @param d destination node
	 * @param msg message to be sent
	 * @param r boolean is it requesting a response
	 */
	public void send(int n, int d, String msg, boolean r) {
		Packet p1 = new Packet(calculatePath(getNode(n), getNode(d)), msg, r);
		MethodInfo m1 = new MethodInfo(0, getNode(n), p1);
		getNode(n).getQueue().add(m1);
	}

	/**
	 * test simulation using the test set of nodes
	 */
	public void test() {
		String[] msg = {"hello", "!@#$", "greetings!", "a", "Good morning!"}; // TODO get all messages that I want
		int n, d, m, r;
		long end = System.currentTimeMillis() + 1500;
		while(System.currentTimeMillis() < end) {
			n = ThreadLocalRandom.current().nextInt(0, 22);
			d = ThreadLocalRandom.current().nextInt(0, 22);
			m = ThreadLocalRandom.current().nextInt(0, 5);
			r = ThreadLocalRandom.current().nextInt(0, 2); // TODO accurate chance of packet requesting a response
			while(n == d) {
				d = ThreadLocalRandom.current().nextInt(0, 21);
			}
			if(r == 0) {
				send(n, d, msg[m], false);
			} else {
				send(n, d, msg[m], true);
			}

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// caught!
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// caught!
			e.printStackTrace();
		}

		printAll();
		System.out.println("Node 0 received messages: " + getNode(0).getReceivedMessages());
	}
	
	// TODO baseline test for nodes? Number of nodes? Placement? Length of simulation? How to process/analyze the data?
	// TODO visual representation of data?
}
