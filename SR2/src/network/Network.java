/**
 * 
 */
package network;
import java.time.LocalTime;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @author Greg
 *
 */
public class Network {
	public int NodeListSize;
	public Vector<Node> NodeList;

	public Network() {
		NodeList = new Vector<Node>();
		NodeListSize = 0;
		setupNodes();
	}

	public int getNodeListSize() {
		return NodeListSize;
	}

	/**
	 * creates new node and adds to vector of all nodes. Updates count of all nodes, NodeListSize.
	 * 
	 * @param txr transmission range
	 * @param x x coordinates
	 * @param y y coordinates
	 * @return New Node
	 */
	public Node addNode(double txr, double x, double y) {
		Node n = new Node(NodeListSize++, txr, x, y);
		NodeList.add(n);
		n.start();
		return n;
	}

	/**
	 * 
	 * @param index index of node you want
	 * @return node at index
	 */
	public Node getNode(int index) {
		return NodeList.get(index);
	}

	/**
	 * 
	 * @return NodeList
	 */
	public Vector<Node> getNodeList() {
		return NodeList;
	}

	/**
	 * 
	 * @return overall number of packets sent by all nodes in NodeList
	 */
	public int getOverallSent() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getSent();
		}
		return c;
	}

	/**
	 * 
	 * @return overall number of packets forwarded by all nodes in NodeList
	 */
	public int getOverallForwarded() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getForwarded();
		}
		return c;
	}

	/**
	 * 
	 * @return overall number of packets received by all nodes in NodeList
	 */
	public int getOverallReceived() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getReceived();
		}
		return c;
	}

	/**
	 * 
	 * @return overall number of packets dropped by all nodes in node list
	 */
	public int getOverallDropped() {
		int c = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			c += NodeList.get(i).getDropped();
		}
		return c;
	}

	/**
	 * 
	 * @return overall number of bytes sent by all nodes in node list
	 */
	public int getOverallBytesSent() {
		int j = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			j += NodeList.get(i).getBytesSent();
		}
		return j;
	}

	/**
	 * 
	 * @return overall number of bytes Forwarded by all nodes in node list
	 */
	public int getOverallBytesForwarded() {
		int j = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			j += NodeList.get(i).getBytesForwarded();
		}
		return j;
	}

	/**
	 * 
	 * @return overall number of bytes Received by all nodes in node list
	 */
	public int getOverallBytesReceived() {
		int j = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			j += NodeList.get(i).getBytesReceived();
		}
		return j;
	}

	/**
	 * 
	 * @return overall number of bytes Dropped by all nodes in node list
	 */
	public int getOverallBytesDropped() {
		int j = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			j += NodeList.get(i).getBytesDropped();
		}
		return j;
	}

	public int getOverallPacketsRequestingResponse() {
		int j = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			j += NodeList.get(i).getResponsesRequested();
		}
		return j;
	}

	public int getOverallPacketsReceivingResponse() {
		int j = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			j += NodeList.get(i).getResponsesReceived();
		}
		return j;
	}

	/**
	 * prints out all 
	 */
	public void printAll() {
		System.out.println("-----------------------------------------");
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
		System.out.println("-----------------------------------------");
	}

	/**
	 * determines the best next hop for the packet
	 * 
	 * @param n packet is here
	 * @param d packet wants to be here
	 * @return node within transmission range with least distance to d
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
	 * @return
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

	public void joinAllNodes() {
		System.out.println(LocalTime.now() + " joining all nodes");
		for(int i = 0; i < NodeList.size(); i++) {
			try {
				System.out.println(LocalTime.now() + " joining node " + i);
				NodeList.get(i).join(100);
				System.out.println(LocalTime.now() + " node " + i + " joined");
			} catch (InterruptedException e) {
				// caught!
			}
		}
	}

	public void setupNodes() {
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

	public void send(int n, int d, String msg, boolean r) {
		Packet p1 = new Packet(calculatePath(getNode(n), getNode(d)), msg, r);
		MethodInfo m1 = new MethodInfo(0, getNode(n), p1);
		getNode(n).getQueue().add(m1);
	}

	public void test() {
		String[] msg = {"hello", "!@#$", "greetings!", "a", "Good morning!"};
		int n, d, m, r;
		long end = System.currentTimeMillis() + 1500;
		while(System.currentTimeMillis() < end) {
			n = ThreadLocalRandom.current().nextInt(0, 22);
			d = ThreadLocalRandom.current().nextInt(0, 22);
			m = ThreadLocalRandom.current().nextInt(0, 5);
			r = ThreadLocalRandom.current().nextInt(0, 2);
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
}
