import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a computer that can communicate with other computers and also be infected
 * 
 * @author Ryan Connolly Nathan Yasosky
 *
 */

public class ComputerNode {

	/**
	 * The integer id that is associated with a computer
	 */
	private int id;
	
	/**
	 * The time that at which this computer communicated with another computer
	 */
	private int timestamp;
	
	/**
	 * A list of outward neighbors of the computer
	 */
	private List<ComputerNode> outNeighbors;
	
	/**
	 * The color of the computer that is used when searching
	 */
	private int color;
	
	/**
	 * The predecessor computer of this computer
	 */
	private ComputerNode pred;
	
	/**
	 * A constructor for the ComputerNode object
	 * @param id the integer value associated with the computer
	 * @param timestamp the time when the computer commucicated with another computer
	 */
	public ComputerNode(int id, int timestamp) {
		this.id = id;
		this.timestamp = timestamp;
		outNeighbors = new ArrayList<ComputerNode>();
	}

	/**
	 * Method for getting the id
	 * @return the id of the computer
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Method for getting the timestamp
	 * @return the timestamp of the computer
	 */
	public int getTimestamp() {
		return this.timestamp;
	}
	
	/**
	 * Method for getting the list of out neighbors
	 * @return the out neighbors of the computer
	 */
	public List<ComputerNode> getOutNeighbors(){
		return this.outNeighbors;
	}
	
	/**
	 * Method for adding an out neighbor to the list of out neighbors
	 * @param outNeighbor neighbor that will be added to the list
	 */
	public void addOutNeighbor(ComputerNode outNeighbor) {
		outNeighbors.add(outNeighbor);
	}
	
	/**
	 * Method for getting the color of the computer
	 * @return the color value of the computer
	 */
	public int getColor() {
		return this.color;
	}
	
	/**
	 * Method for setting the color of a computer
	 * @param color the color value to be set
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * Method for getting the predecessor computer of the current computer
	 * @return The predecessor computer
	 */
	public ComputerNode getPred() {
		return this.pred;
	}
	
	/**
	 * Method for setting the pointer to the predecessor computer
	 * @param pred the predecessor computer
	 */
	public void setPred(ComputerNode pred) {
		this.pred = pred;
	}
}
