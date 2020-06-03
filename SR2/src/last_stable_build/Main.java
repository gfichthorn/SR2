/**
 * 
 */
package last_stable_build;

/**
 * @author Greg
 *
 */
public class Main {

	public static void main(String[] args) {
		//System.out.println("Hello World!");
		Network network = new Network();
		network.addNode(6, 0, 0); // 0th node
		network.addNode(6, 3, 4); // 1st node
		network.addNode(6, 4, 3); // 2nd node
		network.addNode(6, -3, -4); // 3rd node
		network.addNode(6, -4, -3); // 4th node
		network.addNode(6, 7, 7); // 5th node
		network.addNode(6, -7, -7); // 6th node
		network.addNode(6, 2, 2); // 7th node
		network.addNode(6, -2, -2); // 8th node
		network.addNode(6, 5, 0); // 9th node
		network.addNode(6, 0, 5); // 10th node
		network.addNode(6, -5, 0); // 11th node
		network.addNode(6, 0, -5); // 12th node
		network.addNode(6, -3, 4); // 13th node
		network.addNode(6, -4, 3); // 14th node
		network.addNode(6, 3, -4); // 15th node
		network.addNode(6, 4, -3); // 16th node
		network.addNode(6, -7, 7); // 17th node
		network.addNode(6, 7, -7); // 18th node
		network.addNode(6, -2, 2); // 19th node
		network.addNode(6, 2, -2); // 20th node
		
		Packet p1 = new Packet();
		network.sendPacket(network.getNodeList().get(3), network.getNodeList().get(18), p1);
		System.out.println(p1.getPath());
		
		Packet p2 = new Packet();
		network.sendPacket(network.getNodeList().get(6), network.getNodeList().get(17), p2);
		System.out.println(p2.getPath());
		
		byte b = 5;
		Packet p3 = new Packet(b, "Hello");
		network.sendPacket(network.getNodeList().get(18), network.getNodeList().get(10), p3);
		System.out.println(p3.getPath());
		network.printAll();
	}
}
