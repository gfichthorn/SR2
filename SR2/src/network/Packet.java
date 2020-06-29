package network;

import java.time.LocalTime;
import java.util.Vector;

/**
 * @author Greg
 *
 */
public class Packet {
	public String m_msg; //content of packet
	public Vector<Node> m_path; //path taken by packet
	public Node m_currentNode;
	public boolean m_requireResponse;

	/**
	 * packet with no preset message. Used by nodes.
	 * 
	 * @param path route to be taken by this packet
	 * @param requireResponse boolean value of whether this packet requires a response or not
	 */
	Packet(Vector<Node> path, boolean requireResponse) {
		m_msg = "a";
		m_path = path;
		m_currentNode = path.get(0);
		m_requireResponse = requireResponse;
	}

	/**
	 * packet with a preset message. Used by nodes.
	 * 
	 * @param path route to be taken by this packet
	 * @param msg message contained by this packet
	 * @param requireResponse boolean value of whether this packet requires a response or not
	 */
	Packet(Vector<Node> path, String msg, boolean requireResponse) {
		m_msg = msg;
		m_path = path;
		m_currentNode = path.get(0);
		m_requireResponse = requireResponse;
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
	 * reverses the path of this packet for the purpose of responding
	 * @return
	 */
	public Vector<Node> flipPath() {
		Vector<Node> newPath = new Vector<Node>();
		for(int i = getPath().size() - 1; i >= 0; i--) {
			newPath.add(getPath().get(i));
		}
		return newPath;
	}

	/**
	 * returns the current node this packet is at
	 * @return
	 */
	public Node getCurrentNode() {
		return m_currentNode;
	}

	/**
	 * sets m_currentNode to n. returns n
	 * 
	 * @param n node
	 * @return
	 */
	public Node setCurrentNode(Node n) {
		m_currentNode = n;
		return m_currentNode;
	}

	/**
	 * returns the next node in the path. If dropped, then returns current node
	 * 
	 * @return
	 */
	public Node getNextNode() {
		Node n;
		if(getPath().indexOf(getCurrentNode()) + 1 != getPath().size()) {
			n = getPath().get(getPath().indexOf(getCurrentNode()) + 1);
		} else {
			n = getCurrentNode();
		}
		return n;
	}

	/**
	 * returns the boolean value determining whether this packet is requesting a response upon delivery
	 * 
	 * @return
	 */
	public boolean getRequireResponse() {
		return m_requireResponse;
	}
	
	/**
	 * updates and returns new value for boolean value require response
	 * @param b new boolean value for require response
	 * @return 
	 */
	public boolean setRequireResponse(boolean b) {
		m_requireResponse = b;
		return m_requireResponse;
	}

	/**
	 * returns the path of the packet
	 * 
	 * @return 
	 */
	public String printPath() {
		String s = new String(LocalTime.now() + " |");
		for(int i = 0; i < m_path.size(); i++) {
			s = s + (" " + m_path.get(i).getUid() + " ");
		}
		return s;
	}

	/**
	 * does this when origin node sends this packet
	 */
	public void send() {
		setCurrentNode(getNextNode());
	}

	/**
	 * does this when intermediary nodes forwards this packet
	 */
	public void forward() {
		setCurrentNode(getNextNode());
	}

	/**
	 * does this when destination node receives this packet
	 */
	public void receive() {
		System.out.println(printPath() + " received!");
	}

	/**
	 * does this when destination node drops this packet
	 */
	public void drop() {
		System.out.println(printPath() + " dropped!");
	}

}
