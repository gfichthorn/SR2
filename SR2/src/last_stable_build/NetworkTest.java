package last_stable_build;

import static org.junit.Assert.*;

import org.junit.Test;

public class NetworkTest {

	@Test
	public void testNetwork() {
		Network network = new Network();
		assertEquals(network.getNodeListSize(), 0);
		assertTrue(network.getNodeList().isEmpty());
	}

	@Test
	public void testAddNode1() {
		Network network = new Network();
		network.addNode(10, 0, 0);
		assertEquals(network.getNode(0), network.getNodeList().get(0));
		assertEquals(network.getNode(0).getDropped(), 0);
		assertEquals(network.getNode(0).getForwarded(), 0);
		assertEquals(network.getNode(0).getOn(), true);
		assertEquals(network.getNode(0).getReceived(), 0);
		assertEquals(network.getNode(0).getSent(), 0);
		assertTrue(network.getNode(0).getTxr() == 10.0);
		assertTrue(network.getNode(0).getX() == 0);
		assertTrue(network.getNode(0).getY() == 0);
	}
	
	@Test
	public void testAddNode2() {
		Network network = new Network();
		network.addNode(5.4, 5.5, 5.6);
		assertEquals(network.getNode(0), network.getNodeList().get(0));
		assertEquals(network.getNode(0).getDropped(), 0);
		assertEquals(network.getNode(0).getForwarded(), 0);
		assertEquals(network.getNode(0).getOn(), true);
		assertEquals(network.getNode(0).getReceived(), 0);
		assertEquals(network.getNode(0).getSent(), 0);
		assertTrue(network.getNode(0).getTxr() == 5.4);
		assertTrue(network.getNode(0).getX() == 5.5);
		assertTrue(network.getNode(0).getY() == 5.6);
	}
	
	@Test
	public void testNodeList1() {
		Network network = new Network();
		network.addNode(1, 1, 1);
		network.addNode(2, 2, 2);
		network.addNode(3, 3, 3);
		assertEquals(network.getNode(0), network.getNodeList().get(0));
		assertEquals(network.getNode(1), network.getNodeList().get(1));
		assertEquals(network.getNode(2), network.getNodeList().get(2));		
	}
	
	@Test
	public void testNegativeTxr() {
		Network network = new Network();
		network.addNode(-5, 1, 1);
		assertTrue(network.getNode(0).getTxr() == 5);
	}
	
	@Test
	public void testZeroTxr() {
		Network network = new Network();
		network.addNode(0, 1, 1);
		assertTrue(network.getNode(0).getTxr() == 1);
	}
	
	@Test
	public void testDetermineDistancePositive1() {
		Network network = new Network();
		network.addNode(1, 0, 0);
		network.addNode(1, 3, 4);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistancePositive2() {
		Network network = new Network();
		network.addNode(1, 3, 4);
		network.addNode(1, 0, 0);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistancePositive3() {
		Network network = new Network();
		network.addNode(1, 0, 0);
		network.addNode(1, 4, 3);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistancePositive4() {
		Network network = new Network();
		network.addNode(1, 4, 3);
		network.addNode(1, 0, 0);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}	

	
	@Test
	public void testDetermineDistanceNegative1() {
		Network network = new Network();
		network.addNode(1, 0, 0);
		network.addNode(1, -3, -4);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistanceNegative2() {
		Network network = new Network();
		network.addNode(1, -3, -4);
		network.addNode(1, 0, 0);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistanceNegative3() {
		Network network = new Network();
		network.addNode(1, 0, 0);
		network.addNode(1, -4, -3);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistanceNegative4() {
		Network network = new Network();
		network.addNode(1, -4, -3);
		network.addNode(1, 0, 0);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistanceAcrossOrigin1() {
		Network network = new Network();
		network.addNode(1, -2, -1);
		network.addNode(1, 2, 2);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 5);
	}
	
	@Test
	public void testDetermineDistanceAcrossOrigin2() {
		Network network = new Network();
		network.addNode(1, -10, -8);
		network.addNode(1, 10, 7);
		assertTrue(network.determineDistance(network.getNode(0), network.getNode(1)) == 25);
	}
	
	@Test
	public void testDetermineNextHop1() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 5, 5); //1st node
		network.addNode(10, 12, 12); //2nd node
		//System.out.println(network.determineNextHop(network.getNode(0), network.getNode(2)).getUid());
		assertEquals(network.determineNextHop(network.getNode(0), network.getNode(2)), network.getNode(1));
	}
	
	@Test
	public void testDetermineNextHop2() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		network.addNode(10, 5, 5); //2nd node
		network.addNode(10, 10, 10); //3rd node
		//System.out.println(network.determineNextHop(network.getNode(0), network.getNode(3)).getUid());
		assertEquals(network.determineNextHop(network.getNode(0), network.getNode(3)), network.getNode(2));
	}
	
	@Test
	public void testDetermineNextHop3() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		network.addNode(10, 6, 4); //2nd node
		network.addNode(10, 4, 6); //3rd node
		network.addNode(10, 10, 10); //4th node
		//System.out.println(network.determineNextHop(network.getNode(0), network.getNode(4)).getUid());
		assertEquals(network.determineNextHop(network.getNode(0), network.getNode(4)), network.getNode(2));
	}
	
	@Test
	public void testDetermineNextHopNoNextHop() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 10, 10); //1st node
		//System.out.println(network.determineNextHop(network.getNode(0), network.getNode(1)).getUid());
		assertEquals(network.determineNextHop(network.getNode(0), network.getNode(1)), network.getNode(0));
	}
	
	@Test
	public void testDetermineNextHopInRange() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 2, 2); //1st node
		network.addNode(10, 4, 4); //2nd node
		//System.out.println(network.determineNextHop(network.getNode(0), network.getNode(2)).getUid());
		assertEquals(network.determineNextHop(network.getNode(0), network.getNode(2)), network.getNode(2));
	}

	@Test
	public void testForwardPacketFail1() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 10, 10); //1st node
		Packet p = new Packet();
		assertFalse(network.forwardPacket(network.getNode(0), network.getNode(1), p));
		assertEquals(p.getPath(), "Path:  -> 0: dropped");
		assertTrue(p.getForwarded() == 0);
		assertTrue(network.getNode(0).getForwarded() == 0);
		assertTrue(network.getNode(0).getDropped() == 1);
		assertTrue(network.getNode(1).getReceived() == 0);
		assertTrue(p.getForwarded() == 0);
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testForwardPacketFail2() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		network.addNode(10, 11, 11); //2nd node
		Packet p = new Packet();
		assertFalse(network.forwardPacket(network.getNode(0), network.getNode(2), p));
		assertEquals(p.getPath(), "Path:  -> 0 -> 1: dropped");
		assertTrue(p.getForwarded() == 1);
		assertTrue(network.getNode(0).getForwarded() == 1);
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 0);
		assertTrue(network.getNode(1).getDropped() == 1);
		assertTrue(network.getNode(2).getReceived() == 0);
		assertTrue(p.getForwarded() == 1);
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testForwardPacketOneHop1() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		Packet p = new Packet();
		assertTrue(network.forwardPacket(network.getNode(0), network.getNode(1), p));
		assertEquals(p.getPath(), "Path:  -> 0 -> 1: received");
		assertTrue(p.getForwarded() == 1);
		assertTrue(network.getNode(0).getForwarded() == 1);
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(1).getReceived() == 1);
		assertTrue(p.getForwarded() == 1);
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testForwardPacketOneHop2() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		network.addNode(10, 2, 2); //2nd node
		Packet p = new Packet();
		assertTrue(network.forwardPacket(network.getNode(0), network.getNode(2), p));
		assertEquals(p.getPath(), "Path:  -> 0 -> 2: received");
		assertTrue(network.getNode(0).getForwarded() == 1);
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 0);
		assertTrue(network.getNode(2).getReceived() == 1);
		assertTrue(p.getForwarded() == 1);
		//System.out.println(p.getPath());
	}
	
	/* above this is done */
	
	@Test
	public void testForwardPacketTwoHop1() {
		Network network = new Network();
		network.addNode(6, 0, 0); //0th node
		network.addNode(6, 3, 4); //1st node. 5 dist from 0th node
		network.addNode(6, 7, 7); //2nd node. 5 dist from 1st node
		Packet p = new Packet();
		assertTrue(network.forwardPacket(network.getNode(0), network.getNode(2), p));
		assertEquals(p.getPath(), "Path:  -> 0 -> 1 -> 2: received");
		assertTrue(network.getNode(0).getForwarded() == 1);
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 1);
		assertTrue(network.getNode(1).getDropped() == 0);
		assertTrue(network.getNode(2).getReceived() == 1);
		assertTrue(p.getForwarded() == 2);
		//System.out.println(p.getForwarded());
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testForwardPacketTwoHop2() {
		Network network = new Network();
		network.addNode(6, 0, 0); //0th node
		network.addNode(6, -3, -4); //1st node. 5 dist from 0th node
		network.addNode(6, -7, -7); //2nd node. 5 dist from 1st node
		Packet p = new Packet();
		assertTrue(network.forwardPacket(network.getNode(0), network.getNode(2), p));
		assertEquals(p.getPath(), "Path:  -> 0 -> 1 -> 2: received");
		assertTrue(network.getNode(0).getForwarded() == 1);
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 1);
		assertTrue(network.getNode(1).getDropped() == 0);
		assertTrue(network.getNode(2).getReceived() == 1);
		assertTrue(p.getForwarded() == 2);
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testReceivePacket1() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		Packet p = new Packet();
		assertTrue(network.receivePacket(network.getNode(0), p) == "a");
		assertEquals(p.getMsg(), "a");
		assertTrue(p.getSize() == 1);
		assertEquals(p.getPath(), "Path:  -> 0: received");
		assertTrue(p.getForwarded() == 0);
		//System.out.println(network.receivePacket(network.getNode(0), p));
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testReceivePacket2() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		byte b = 2;
		Packet p = new Packet(b, "aa");
		assertTrue(network.receivePacket(network.getNode(0), p) == "aa");
		assertEquals(p.getMsg(), "aa");
		assertTrue(p.getSize() == 2);
		assertEquals(p.getPath(), "Path:  -> 0: received");
		assertTrue(p.getForwarded() == 0);
		//System.out.println(network.receivePacket(network.getNode(0), p));
		//System.out.println(p.getPath());
	}
	
	@Test
	public void testSendDummyPacketOneHop1() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		assertTrue(network.sendDummyPacket(network.getNode(0), network.getNode(1)));
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(0).getForwarded() == 0);
		assertTrue(network.getNode(0).getReceived() == 0);
		assertTrue(network.getNode(0).getSent() == 1);
		assertTrue(network.getNode(1).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 0);
		assertTrue(network.getNode(1).getReceived() == 1);
		assertTrue(network.getNode(1).getSent() == 0);
	}
	
	@Test
	public void testSendDummyPacketOneHop2() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 1, 1); //1st node
		network.addNode(10, 2, 2); //2nd node
		assertTrue(network.sendDummyPacket(network.getNode(0), network.getNode(2)));
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(0).getForwarded() == 0);
		assertTrue(network.getNode(0).getReceived() == 0);
		assertTrue(network.getNode(0).getSent() == 1);
		assertTrue(network.getNode(1).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 0);
		assertTrue(network.getNode(1).getReceived() == 0);
		assertTrue(network.getNode(1).getSent() == 0);
		assertTrue(network.getNode(2).getDropped() == 0);
		assertTrue(network.getNode(2).getForwarded() == 0);
		assertTrue(network.getNode(2).getReceived() == 1);
		assertTrue(network.getNode(2).getSent() == 0);
	}
	
	@Test
	public void testSendDummyPacketTwoHop1() {
		Network network = new Network();
		network.addNode(10, 0, 0); //0th node
		network.addNode(10, 5, 5); //1st node
		network.addNode(10, 10, 10); //2nd node
		assertTrue(network.sendDummyPacket(network.getNode(0), network.getNode(2)));
		assertTrue(network.getNode(0).getDropped() == 0);
		assertTrue(network.getNode(0).getForwarded() == 0);
		assertTrue(network.getNode(0).getReceived() == 0);
		assertTrue(network.getNode(0).getSent() == 1);
		assertTrue(network.getNode(1).getDropped() == 0);
		assertTrue(network.getNode(1).getForwarded() == 1);
		assertTrue(network.getNode(1).getReceived() == 0);
		assertTrue(network.getNode(1).getSent() == 0);
		assertTrue(network.getNode(2).getDropped() == 0);
		assertTrue(network.getNode(2).getForwarded() == 0);
		assertTrue(network.getNode(2).getReceived() == 1);
		assertTrue(network.getNode(2).getSent() == 0);
		
	}

	@Test
	public void testSendPacket() {
		
	}
	
}
