import java.util.List;

public class ComputerNode {
	
	private int id;
	private int timestamp;
	private List<ComputerNode> outNeighbors;
	
	public int getID() {
		return this.id;
	}
	
	public int getTimestamp() {
		return this.timestamp;
	}
	
	public List<ComputerNode> getOutNeighbors(){
		return this.outNeighbors;
	}
	
	

}
