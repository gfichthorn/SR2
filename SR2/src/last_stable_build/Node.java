package last_stable_build;

public class Node {
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
	
}
