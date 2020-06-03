/**
 * 
 */
package last_stable_build;
import java.util.Vector;

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
	 * sends a packet with byte=1, msg="a" to destination
	 * 
	 * @param d destination node
	 * @return
	 */
	public boolean sendDummyPacket(Node n, Node d) {
		Packet p = new Packet();
		p.addPath(String.valueOf(n.getUid()));
		Node nextHop = determineNextHop(n, d);
		if(nextHop == n) { //if next hop does not change, fails.
			n.incrementDropped();
			n.increaseBytesDropped(p.getSize());
			return false;
		} else if (nextHop == d) { //if next hop last hop, target receive
			n.incrementSent();
			n.increaseBytesSent(p.getSize());
			receivePacket(d, p);
			return true;
		} else { //else send
			n.incrementSent();
			n.increaseBytesSent(p.getSize());
			return forwardPacket(nextHop, d, p);
		}
	}

	/**
	 * creates and sends a new packet to a specified destination
	 * 
	 * @param d destination node
	 * @param size size of packet to be sent
	 * @param msg content of packet to be sent
	 * @return
	 */
	public boolean sendPacket(Node n, Node d, Packet p) {
		p.addPath(String.valueOf(n.getUid()));
		Node nextHop = determineNextHop(n, d);
		if(nextHop == n) { //if next hop does not change, fails.
			n.incrementDropped();
			n.increaseBytesDropped(p.getSize());
			return false;
		} else if (nextHop == d) { //if next hop last hop, target receive
			n.incrementSent();
			n.increaseBytesSent(p.getSize());
			receivePacket(d, p);
			return true;
		} else { //else forward
			n.incrementSent();
			n.increaseBytesSent(p.getSize());
			return forwardPacket(nextHop, d, p);
		}
	}

	/**
	 * forwards the packet to the next hop
	 * 
	 * @param n current node
	 * @param d destination node
	 * @param p packet
	 * @return
	 */
	public boolean forwardPacket(Node n, Node d, Packet p) {	
		p.addPath(String.valueOf(" -> " + n.getUid()));
		Node nextHop = determineNextHop(n,d); //get next hop
		if(nextHop == n) { //if next hop does not change, fails.
			n.incrementDropped();
			n.increaseBytesDropped(p.getSize());
			p.addPath(": dropped");
			return false;
		} else if (nextHop == d) { //if next hop last hop, target receive
			n.incrementForwarded();
			n.increaseBytesForwarded(p.getSize());
			receivePacket(d, p);
			p.incrementForwarded();
			return true;
		} else { //else forward
			n.incrementForwarded();
			n.increaseBytesForwarded(p.getSize());
			p.incrementForwarded();
			return forwardPacket(nextHop, d, p);
		}
	}

	/**
	 * node receives a packet, increments packetsReceived, returns packet
	 * 
	 * @param n node receiving packet
	 * @param p packet being received
	 * @return packet
	 */
	public String receivePacket(Node n, Packet p) {
		n.incrementReceived();
		n.increaseBytesReceived(p.getSize());
		p.addPath(" -> " + n.getUid() + ": received");
		return n.printPacket(p);
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
		
		for(int i = 0; i < NodeListSize; i++) {
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
	public byte getOverallBytesSent() {
		byte b = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			b += NodeList.get(i).getBytesSent();
		}
		return b;
	}
	
	/**
	 * 
	 * @return overall number of bytes Forwarded by all nodes in node list
	 */
	public byte getOverallBytesForwarded() {
		byte b = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			b += NodeList.get(i).getBytesForwarded();
		}
		return b;
	}
	
	/**
	 * 
	 * @return overall number of bytes Received by all nodes in node list
	 */
	public byte getOverallBytesReceived() {
		byte b = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			b += NodeList.get(i).getBytesReceived();
		}
		return b;
	}
	
	/**
	 * 
	 * @return overall number of bytes Dropped by all nodes in node list
	 */
	public byte getOverallBytesDropped() {
		byte b = 0;
		for(int i = 0; i < NodeList.size(); i++) {
			b += NodeList.get(i).getBytesDropped();
		}
		return b;
	}
	
	/**
	 * prints out all 
	 */
	public void printAll() {
		System.out.println("Number of packets sent by all nodes: " + getOverallSent());
		System.out.println("Number of bytes sent by all nodes: " + getOverallBytesSent());
		System.out.println("Number of packets Forwarded by all nodes: " + getOverallForwarded());
		System.out.println("Number of bytes Forwarded by all nodes: " + getOverallBytesForwarded());
		System.out.println("Number of packets Received by all nodes: " + getOverallReceived());
		System.out.println("Number of bytes Received by all nodes: " + getOverallBytesReceived());
		System.out.println("Number of packets Dropped by all nodes: " + getOverallDropped());
		System.out.println("Number of bytes Dropped by all nodes: " + getOverallBytesDropped());
	}
	
}
