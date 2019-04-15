import java.util.ArrayList;
import java.util.List;

public class ComputerNode {

	private int id;
	private int timestamp;
	private List<ComputerNode> outNeighbors;

	public ComputerNode(int id, int timestamp) {
		this.id = id;
		this.timestamp = timestamp;
		outNeighbors = new ArrayList<ComputerNode>();
	}

	public int getID() {
		return this.id;
	}

	public int getTimestamp() {
		return this.timestamp;
	}

	public List<ComputerNode> getOutNeighbors(){
		return this.outNeighbors;
	}

	public void addOutNeighbor(ComputerNode outNeighbor) {
		outNeighbors.add(outNeighbor);
	}


}
