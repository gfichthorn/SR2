package last_stable_build;

public class Packet {
	public byte m_size; //size of packet in bytes
	public String m_msg; //content of packet
	public String m_path; //path taken by packet
	public int m_forwarded; //number of nodes packet has touched
	
	
	/**
	 * dummy packet
	 * 
	 * @param size size of the packet in bytes
	 * @param msg content of the packet
	 * @param m_path is path taken so far. In uid
	 */
	Packet() {
		m_size = 1;
		m_msg = "a";
		m_path = "Path: ";
		m_forwarded = 0;
	}
	
	/**
	 * 
	 * @param size size of the packet in bytes
	 * @param msg content of the packet
	 * @param m_path is path taken so far. In uid
	 */
	Packet(byte size, String msg) {
		m_size = size;
		m_msg = msg;
		m_path = "Path: ";
		m_forwarded = 0;
	}
	
	/**
	 * 
	 * @return size of packet
	 */
	public byte getSize() {
		return m_size;
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
	public String getPath() {
		return m_path;
	}
	
	/**
	 * 
	 * @param newVal new step taken by packet
	 * @return updated path taken by packet
	 */
	public String addPath(String newVal) {
		m_path += newVal;
		return m_path;
	}
	
	/**
	 * returns number of times a packet has been forwarded
	 * @return
	 */
	public int getForwarded() {
		return m_forwarded;
	}
	
	/**
	 * increments and returns the number of times a packet has been forwarded
	 * @return
	 */
	public int incrementForwarded() {
		return m_forwarded++;
	}
}
