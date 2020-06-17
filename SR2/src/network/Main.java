package network;

/**
 * @author Greg
 *
 */
public class Main {

	public static void main(String[] args) {
		Network network = new Network();
		network.test(); //this is the preset of nodes I've been using for the past few weeks
		/*Network n = new Network();
		n.setupTestNodes();
		Intruder i = new Intruder(n.getNodeListSize(), 10, 10, 10);
		n.addNode(i);
		Node node;
		node = i;
		SelfishNode s = new SelfishNode(5,5,5,5);
		s.start();*/
	}
}
