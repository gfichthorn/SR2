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
	public long m_duration;

	/**
	 * Network containing nodes and performing actions that requires all nodes
	 * @param duration length of experiment, ms
	 */
	public Network(long duration) {
		NodeList = new Vector<Node>();
		NodeListSize = 0;
		m_duration = duration;
		this.setupHealthyTestNodes();
		//this.setupDelayTestNodes(); // int is 1
		//this.setupMirrorTestNodes(); // int is 2
		//this.setupTargetNode(); // int is 3
		//this.setupBroadcastNode(); // int is 3
		//this.setupForgetfulNode(); // int is 4
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
		n.start();
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
			if(NodeList.get(i).getIsRegular()) {
				c += NodeList.get(i).getBytesSent();
			}
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
			if(NodeList.get(i).getIsRegular()) {
				c += NodeList.get(i).getBytesForwarded();
			}
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
			if(NodeList.get(i).getIsRegular()) {
				c += NodeList.get(i).getBytesReceived();
			}
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
			if(NodeList.get(i).getIsRegular()) {
				c += NodeList.get(i).getBytesDropped();
			}
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
			if(NodeList.get(i).getIsRegular()) {
				c += NodeList.get(i).getResponsesRequested();
			}
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
			if(NodeList.get(i).getIsRegular()) {
				c += NodeList.get(i).getResponsesReceived();
			}
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
		int bSent = getOverallBytesSent();
		System.out.println("Number of packets sent by benign nodes: " + getOverallSent());
		System.out.println("Number of bytes sent by benign nodes: " + bSent);
		int bForwarded = getOverallBytesForwarded();
		System.out.println("Number of packets forwarded by benign nodes: " + getOverallForwarded());
		System.out.println("Number of bytes forwarded by benign nodes: " + bForwarded);
		int bReceived = getOverallBytesReceived();
		System.out.println("Number of packets received by benign nodes: " + getOverallReceived());
		System.out.println("Number of bytes received by benign nodes: " + bReceived);
		int bDropped = getOverallBytesDropped();
		System.out.println("Number of packets dropped by benign nodes: " + getOverallDropped());
		System.out.println("Number of bytes dropped by benign nodes: " + bDropped);
		System.out.println("Number of packets requesting a response sent by benign nodes: " + getOverallPacketsRequestingResponse());
		System.out.println("Number of packets received by benign nodes in response to a request: " + getOverallPacketsReceivingResponse());
		double duration =  new Double(m_duration);
		System.out.println("Duration of experiment: " + duration + " ms");
		int bAll = bSent + bForwarded + bReceived + bDropped;
		double bDouble = new Double(bAll);
		System.out.println("Number of bytes managed by each node: " + bDouble);
		double bps = new Double(1000 * bDouble / duration);
		System.out.println("Average number of bytes managed by benign nodes per second: " + bps);
		int j = 0, k = 0, l = 0;;
		for(int i = 0; i < NodeListSize - 1; i++) {
			if(NodeList.get(i).getthreshold() == 0) {
				j++;
			} else {
				k++;
				if(NodeList.lastElement().getIntType() == NodeList.get(i).getthreshold()) {
					l++;
				}
			}
		}
		System.out.println("Number of nodes to guess there was no intruder: " + j);
		System.out.println("Number of nodes to guess there was an intruder: " + k);
		String s = "Type of intruder: ";
		Node last = getNodeList().lastElement();
		if(last.getIsRegular()) {
			s += "no intruder";
			System.out.println("Number of nodes to correctly guess the type of intruder: " + j);
		} else {
			s += getNodeList().lastElement().getType();
			System.out.println("Number of nodes to correctly guess the type of intruder: " + l);
		}
		System.out.println(s);
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
			//v.add(d);
		}
		return v;
	}

	/**
	 * sets up a set of test nodes
	 */
	public void setupHealthyTestNodes() {
		addNode(5, 3, 4); // 0th node
		addNode(5, 4, 3); // 1st node
		addNode(5, -3, -4); // 2nd node
		addNode(5, -4, -3); // 3rd node
		addNode(5, 7, 7); // 4th node
		addNode(5, -7, -7); // 5th node
		addNode(5, 2, 2); // 6th node
		addNode(5, -2, -2); // 7th node
		addNode(5, 5, 0); // 8th node
		addNode(5, 0, 5); // 9th node
		addNode(5, -5, 0); // 10th node
		addNode(5, 0, -5); // 11th node
		addNode(5, -3, 4); // 12th node
		addNode(5, -4, 3); // 13th node
		addNode(5, 3, -4); // 14th node
		addNode(5, 4, -3); // 15th node
		addNode(5, -7, 7); // 16th node
		addNode(5, 7, -7); // 17th node
		addNode(5, -2, 2); // 18th node
		addNode(5, 2, -2); //  19th node
		addNode(5, 0, 0); // 20th node
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
	public void testNetwork() {
		String[] msg = {"hello", "!@#$", "greetings!", "a", "Good morning!"}; // TODO get all messages that I want
		int n, d, m, r;
		long end = System.currentTimeMillis() + m_duration;
		while(System.currentTimeMillis() < end) {
			n = ThreadLocalRandom.current().nextInt(0, 21);
			d = ThreadLocalRandom.current().nextInt(0, 21);
			m = ThreadLocalRandom.current().nextInt(0, 5);
			r = ThreadLocalRandom.current().nextInt(0, 2); // TODO accurate chance of packet requesting a response = always
			while(n == d) {
				d = ThreadLocalRandom.current().nextInt(0, 21);
			}
			if(r == 0) {
				send(n, d, msg[m], false);
			} else {
				send(n, d, msg[m], true);
			}

			try {
				Thread.sleep(5); //TODO length in between sending messages
			} catch (InterruptedException e) {
				// caught!
				e.printStackTrace();
			}
		}
		printAll();
	}
	
	public void setupMirrorTestNodes() {
		addNode(5, 3, 4); // 0th node
		addNode(5, 4, 3); // 1st node
		addNode(5, -3, -4); // 2nd node
		addNode(5, -4, -3); // 3rd node
		addNode(5, 0, 0); // 4th node
		addNode(5, -7, -7); // 5th node
		addNode(5, 2, 2); // 6th node
		addNode(5, -2, -2); // 7th node
		addNode(5, 5, 0); // 8th node
		addNode(5, 0, 5); // 9th node
		addNode(5, -5, 0); // 10th node
		addNode(5, 0, -5); // 11th node
		addNode(5, -3, 4); // 12th node
		addNode(5, -4, 3); // 13th node
		addNode(5, 3, -4); // 14th node
		addNode(5, 4, -3); // 15th node
		addNode(5, -7, 7); // 16th node
		addNode(5, 7, -7); // 17th node
		addNode(5, -2, 2); // 18th node
		addNode(5, 2, -2); // 19th node
		MirrorNode mN = new MirrorNode(NodeListSize++, 5, 7, 7);
		this.addNode(mN); // 20th node
	}
	
	public void setupDelayTestNodes() {
		addNode(5, 3, 4); // 0th node
		addNode(5, 4, 3); // 1st node
		addNode(5, -3, -4); // 2nd node
		addNode(5, -4, -3); // 3rd node
		addNode(5, 0, 0); // 4th node
		addNode(5, -7, -7); // 5th node
		addNode(5, 2, 2); // 6th node
		addNode(5, -2, -2); // 7th node
		addNode(5, 5, 0); // 8th node
		addNode(5, 0, 5); // 9th node
		addNode(5, -5, 0); // 10th node
		addNode(5, 0, -5); // 11th node
		addNode(5, -3, 4); // 12th node
		addNode(5, -4, 3); // 13th node
		addNode(5, 3, -4); // 14th node
		addNode(5, 4, -3); // 15th node
		addNode(5, -7, 7); // 16th node
		addNode(5, 7, -7); // 17th node
		addNode(5, -2, 2); // 18th node
		addNode(5, 2, -2); //  19th node
		DelayNode dN = new DelayNode(NodeListSize++, 5, 7, 7);
		this.addNode(dN); // 20th node
	}
	
	public void setupTargetNode() {
		addNode(5, 3, 4); // 0th node
		addNode(5, 4, 3); // 1st node
		addNode(5, -3, -4); // 2nd node
		addNode(5, -4, -3); // 3rd node
		addNode(5, 0, 0); // 4th node
		addNode(5, -7, -7); // 5th node
		addNode(5, 2, 2); // 6th node
		addNode(5, -2, -2); // 7th node
		addNode(5, 5, 0); // 8th node
		addNode(5, 0, 5); // 9th node
		addNode(5, -5, 0); // 10th node
		addNode(5, 0, -5); // 11th node
		addNode(5, -3, 4); // 12th node
		addNode(5, -4, 3); // 13th node
		addNode(5, 3, -4); // 14th node
		addNode(5, 4, -3); // 15th node
		addNode(5, -7, 7); // 16th node
		addNode(5, 7, -7); // 17th node
		addNode(5, -2, 2); // 18th node
		addNode(5, 2, -2); //  19th node
		Node temp = new Node(NodeListSize++, 5, 7, 7);
		Vector<Node> target = this.calculatePath(temp, this.getNode(5));
		FloodTargetNode ftn = new FloodTargetNode(NodeListSize, 5, 7, 7, target);
		this.addNode(ftn); // 20th node
	}
	
	public void setupBroadcastNode() {
		addNode(5, 3, 4); // 0th node
		addNode(5, 4, 3); // 1st node
		addNode(5, -3, -4); // 2nd node
		addNode(5, -4, -3); // 3rd node
		addNode(5, 0, 0); // 4th node
		addNode(5, -7, -7); // 5th node
		addNode(5, 2, 2); // 6th node
		addNode(5, -2, -2); // 7th node
		addNode(5, 5, 0); // 8th node
		addNode(5, 0, 5); // 9th node
		addNode(5, -5, 0); // 10th node
		addNode(5, 0, -5); // 11th node
		addNode(5, -3, 4); // 12th node
		addNode(5, -4, 3); // 13th node
		addNode(5, 3, -4); // 14th node
		addNode(5, 4, -3); // 15th node
		addNode(5, -7, 7); // 16th node
		addNode(5, 7, -7); // 17th node
		addNode(5, -2, 2); // 18th node
		addNode(5, 2, -2); //  19th node
		Node temp = new Node(NodeListSize++, 5, 7, 7);
		Vector<Vector<Node>> broadcast = new Vector<Vector<Node>>();
		for(int i = 0; i < NodeListSize - 1; i++) {
			broadcast.add(this.calculatePath(temp, getNode(i)));
		}
		FloodNetworkNode fnn = new FloodNetworkNode(NodeListSize, 5, 7, 7, broadcast);
		this.addNode(fnn); // 20th node
	}
	
	public void setupForgetfulNode() {
		addNode(5, 3, 4); // 0th node
		addNode(5, 4, 3); // 1st node
		addNode(5, -3, -4); // 2nd node
		addNode(5, -4, -3); // 3rd node
		addNode(5, 0, 0); // 4th node
		addNode(5, -7, -7); // 5th node
		addNode(5, 2, 2); // 6th node
		addNode(5, -2, -2); // 7th node
		addNode(5, 5, 0); // 8th node
		addNode(5, 0, 5); // 9th node
		addNode(5, -5, 0); // 10th node
		addNode(5, 0, -5); // 11th node
		addNode(5, -3, 4); // 12th node
		addNode(5, -4, 3); // 13th node
		addNode(5, 3, -4); // 14th node
		addNode(5, 4, -3); // 15th node
		addNode(5, -7, 7); // 16th node
		addNode(5, 7, -7); // 17th node
		addNode(5, -2, 2); // 18th node
		addNode(5, 2, -2); //  19th node
		ForgetfulNode f = new ForgetfulNode(NodeListSize++, 5, 7, 7);
		this.addNode(f); // 20th node
	}
	
}
