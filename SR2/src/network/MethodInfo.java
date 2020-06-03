package network;

import java.util.Vector;

public class MethodInfo {
	public int m_i; //my int
	public Node m_n; //my node
	public Node m_d; // my destination
	public Packet m_p; // my packet
	//public Vector<Node> NodeList;
	
	public MethodInfo(int i, Node n, Node d, Packet p, Vector<Node> nodeList) {
		m_i = i;
		m_n = n;
		m_d = d;
		m_p = p;
		//NodeList = nodeList;
	}
	
	public int getI() {
		return m_i;
	}
	
	public Node getN() {
		return m_n;
	}
	
	public Node getD() {
		return m_d;
	}
	
	public Packet getP() {
		return m_p;
	}
	
	/*public Vector<Node> getNodeList() {
		return NodeList;
	}*/
}
