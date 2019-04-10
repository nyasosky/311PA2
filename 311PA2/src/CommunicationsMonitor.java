import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class CommunicationsMonitor {

	private HashMap<Integer, List<ComputerNode>> mapping;
	private List<ComputerNode> nodes;
	
	public CommunicationsMonitor() {
			
	}
	
	public void addCommuncication(int c1, int c2, int timestamp) {
		
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
}
