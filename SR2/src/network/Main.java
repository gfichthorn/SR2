/**
 * 
 */
package network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Greg
 *
 */
public class Main {

	public static void main(String[] args) {
		//System.out.println("Hello World!");
		Network network = new Network();
		network.addNode(5, 0, 0); // 0th node
		network.addNode(5, 3, 4); // 1st node
		network.addNode(5, 4, 3); // 2nd node
		network.addNode(5, -3, -4); // 3rd node
		network.addNode(5, -4, -3); // 4th node
		network.addNode(5, 7, 7); // 5th node
		network.addNode(5, -7, -7); // 6th node
		network.addNode(5, 2, 2); // 7th node
		network.addNode(5, -2, -2); // 8th node
		network.addNode(5, 5, 0); // 9th node
		network.addNode(5, 0, 5); // 10th node
		network.addNode(5, -5, 0); // 11th node
		network.addNode(5, 0, -5); // 12th node
		network.addNode(5, -3, 4); // 13th node
		network.addNode(5, -4, 3); // 14th node
		network.addNode(5, 3, -4); // 15th node
		network.addNode(5, 4, -3); // 16th node
		network.addNode(5, -7, 7); // 17th node
		network.addNode(5, 7, -7); // 18th node
		network.addNode(5, -2, 2); // 19th node
		network.addNode(5, 2, -2); // 20th node

		//network.joinAllNodes();
		//System.out.println("0");
		//network.startAllThreads();
		
		//Packet p1 = new Packet();
		//System.out.println("1");
		//network.getNode(6).queueSend(network.getNodeList(), network.getNodeList().get(3), p1);
		//network.sendPacket(network.getNode(6), network.getNode(3), p1);
		byte b = 1;
		ExecutorService PacketThreadPool = Executors.newFixedThreadPool(5);
		Packet p1 = new Packet(network, network.getNode(6), network.getNode(8), b, "1");
		Packet p2 = new Packet(network, network.getNode(18), network.getNode(20), b, "2");
		Packet p3 = new Packet(network, network.getNode(5), network.getNode(7), b, "3");
		Packet p4 = new Packet(network, network.getNode(17), network.getNode(19), b, "4");
		Packet p5 = new Packet(network, network.getNode(10), network.getNode(12), b, "5");
		Packet p6 = new Packet(network, network.getNode(2), network.getNode(16), b, "6");
		Packet p7 = new Packet(network, network.getNode(4), network.getNode(14), b, "7");
		
		//network.sendPacket(network.getNode(6), network.getNode(5), p2);
		PacketThreadPool.execute(p1);
		PacketThreadPool.execute(p2);
		PacketThreadPool.execute(p3);
		PacketThreadPool.execute(p4);
		PacketThreadPool.execute(p5);
		PacketThreadPool.execute(p6);
		PacketThreadPool.execute(p7);
		
		//network.getNode(3).queueSend(network.getNodeList(), network.getNodeList().get(6), p2);
		
		//network.getNode(6).queueSend(network.getNodeList(), network.getNodeList().get(3), p1);
		//network.getNode(6).queueSend(network.getNodeList(), network.getNodeList().get(12), p1);
		//network.getNode(12).queueSend(network.getNodeList(), network.getNodeList().get(12), p1);
		/*//System.out.println(p1.getPath());
		//System.out.println("2");
		Packet p2 = new Packet();
		network.getNode(6).queueSend(network.getNodeList(), network.getNodeList().get(3), p2);
		//System.out.println(p2.getPath());
		//System.out.println("3");
		byte b = 5;
		Packet p3 = new Packet(b, "Hello");
		network.getNode(6).queueSend(network.getNodeList(), network.getNodeList().get(12), p3);
		//System.out.println("4");
		//System.out.println(p3.getPath());
		Packet p4 = new Packet();
		network.getNode(12).queueSend(network.getNodeList(), network.getNodeList().get(6), p4);
		//network.printAll();*/
	}
}
