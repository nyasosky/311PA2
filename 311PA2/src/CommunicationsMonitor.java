import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class CommunicationsMonitor {

	private HashMap<Integer, List<ComputerNode>> mapping = new HashMap<Integer, List<ComputerNode>>();
	private List<ComputerNode> nodes = new ArrayList<ComputerNode>();
	
	public CommunicationsMonitor() {
			
	}
	
	public void addCommuncication(int c1, int c2, int timestamp) {
		List<ComputerNode> list = new ArrayList<ComputerNode>();
		list.add(new ComputerNode(c1, timestamp));
		list.add(new ComputerNode(c2, timestamp));
		nodes.add(new ComputerNode(c1, timestamp));
		nodes.add(new ComputerNode(c2, timestamp));
		mapping.put(timestamp, list);
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
		return this.nodes;
	}
	
	public void PrintList() {
		System.out.println(mapping);
	}
}
