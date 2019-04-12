import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class CommunicationsMonitor {

	private HashMap<Integer, List<ComputerNode>> mapping = new HashMap<Integer, List<ComputerNode>>();
	
	public CommunicationsMonitor() {
			
	}
	
	public void addCommuncication(int c1, int c2, int timestamp) {
		ComputerNode c1Node = new ComputerNode(c1, timestamp);
		ComputerNode c2Node = new ComputerNode(c2, timestamp);
		c1Node.addOutNeighbor(c2Node);
		c2Node.addOutNeighbor(c1Node);
		mapping.get(c1).add(c1Node);
		mapping.get(c2).add(c2Node);
	}
	
	public void createGraph() {
		
	}
	
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
		List<ComputerNode> list = new ArrayList<ComputerNode>();
		
		return list;
	}
	
	public HashMap<Integer, List<ComputerNode>> getComputerMapping(){
		return this.mapping;
	}
	
	public List<ComputerNode> getComputerMapping(int c){
		return this.mapping.get(c);
	}
	
	public void PrintList() {
		System.out.println(mapping);
	}
}
