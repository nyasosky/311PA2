import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class for building the data structure and querying infections throughout the computers
 * @author Ryan Connolly Nathan Yasosky
 *
 */
public class CommunicationsMonitor {

	/**
	 * HashMap used to hold the communications and create the graph structure
	 */
    private HashMap<Integer, List<ComputerNode>> mapping;

    /**
     * List of triples to make adding them into the hashmap easier
     */
    private List<List<Integer>> tripleList;

    /**
     * Boolean used to check if the graph has been made
     */
    private boolean graphMade;
	
    /**
     * Constructor for setting up a CommunicationsMonitor
     */
	public CommunicationsMonitor() {
        mapping = new HashMap<Integer, List<ComputerNode>>();
        tripleList = new ArrayList<List<Integer>>();
        graphMade = false;
	}

	/**
	 * Method for adding a communication between two computers at a certain time
	 * @param c1 The first computer
	 * @param c2 The second computer
	 * @param timestamp The time at which they communicated
	 */
    public void addCommunication(int c1, int c2, int timestamp) {
        if (!graphMade) {
            List<Integer> tripleToAdd = new ArrayList<Integer>();
            tripleToAdd.add(c1);
            tripleToAdd.add(c2);
            tripleToAdd.add(timestamp);
            tripleList.add(tripleToAdd);
        }
	}
	
    /**
     * Method for creating the graph structure that is used when querying infections
     */
	public void createGraph() {
        if (tripleList.size() > 0) {
            tripleList = MergeSort(tripleList);
            for (List<Integer> triple : tripleList) {
                List<ComputerNode> c1List, c2List;
                ComputerNode c2Node = new ComputerNode(triple.get(1), triple.get(2));
                ComputerNode c1Node = new ComputerNode(triple.get(0), triple.get(2));
                boolean newC1Node = true, newC2Node = true;
                if (mapping.get(triple.get(0)) == null) {
                    c1List = new ArrayList<ComputerNode>();
                } else {
                    c1List = mapping.get(triple.get(0));
                    if (c1List.get(c1List.size() - 1).getTimestamp() == triple.get(2)) {
                        c1Node = c1List.get(c1List.size() - 1);
                        newC1Node = false;
                    } else {
                        c1List.get(c1List.size() - 1).addOutNeighbor(c1Node);
                    }
                }
                if (mapping.get(triple.get(1)) == null) {
                    c2List = new ArrayList<ComputerNode>();
                } else {
                    c2List = mapping.get(triple.get(1));
                    if (c2List.get(c2List.size() - 1).getTimestamp() == triple.get(2)) {
                        c2Node = c2List.get(c2List.size() - 1);
                        newC2Node = false;
                    } else {
                        c2List.get(c2List.size() - 1).addOutNeighbor(c2Node);
                    }
                }
                c1Node.addOutNeighbor(c2Node);
                c2Node.addOutNeighbor(c1Node);
                if (newC1Node) {
                    c1List.add(c1Node);
                }
                if (newC2Node) {
                    c2List.add(c2Node);
                }
                mapping.put(triple.get(0), c1List);
                mapping.put(triple.get(1), c2List);
            }
        }
        graphMade = true;
	}
	
	/**
	 * Method for querying an infection from a single computer and checking to see if the computer c2 has been infected at time y
	 * @param c1 First infected computer
	 * @param c2 Second computer that is being checked to see if has been infected at time y
	 * @param x the time that c1 has been infected
	 * @param y the time that is being checked to see if c2 has been infected by
	 * @return The list of computers from the c1 to c2 that have been infected along the way
	 */
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
		ComputerNode endOfList = null;
        if (graphMade) {
            ArrayList<ComputerNode> list = new ArrayList<ComputerNode>();
            ComputerNode lastNode = null;
            if (mapping.get(c1) != null) {
                for (ComputerNode n : mapping.get(c1)) {
                    if (n.getTimestamp() >= x) {
                        lastNode = DFS(n, c2, y);
                        break;
                    }
                }
            }
            if (lastNode != null) {
                list.add(lastNode);
                while (lastNode.getPred() != null) {
                    lastNode = lastNode.getPred();
                    list.add(lastNode);
                }
                Collections.reverse(list);
                return list;
            } else
                return null;
        } else {
            return null;
        }
	}

	/**
	 * Method for getting themapping of the hashmap
	 * @return the hashmap structure mapping
	 */
    public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
        if (graphMade) {
            return mapping;
        } else {
            return null;
        }
	}
	
    /**
     * Method for getting the mapping from a certain computer
     * @param c the id of the computer
     * @return the mapping of the hashmap at that certain index
     */
	public List<ComputerNode> getComputerMapping(int c){
        if (graphMade) {
            return mapping.get(c);
        } else {
            return null;
        }
	}

    /**
     * MergeSort is used to sort the triples by nondecreasing timestamp
     * @param list the list of triples
     * @return the sorted list
     */
    private List<List<Integer>> MergeSort(List<List<Integer>> list) {
        int n = list.size();
        if (n == 1)
            return list;
        List<List<Integer>> firstHalf = list.subList(0, n / 2);
        List<List<Integer>> secondHalf = list.subList(n / 2, n);
        return Merge(MergeSort(firstHalf), MergeSort(secondHalf));
    }

    /**
     * Method for sorting two lists
     * @param A The first lsit to sort
     * @param B The second list to sort
     * @return The sorted list from A and B
     */
    private List<List<Integer>> Merge(List<List<Integer>> A, List<List<Integer>> B) {
        int p = A.size();
        int q = B.size();
        List<List<Integer>> C = new ArrayList<List<Integer>>();
        int i = 0;
        int j = 0;
        while (i < p && j < q) {
            if (A.get(i).get(2) == B.get(j).get(2)) {
                if (A.get(i).get(0) == B.get(i).get(0) && A.get(i).get(1) == B.get(i).get(1)) {
                    C.add(A.get(i));
                    i++;
                    j++;
                } else if (A.get(i).get(0) == B.get(i).get(1) && A.get(i).get(1) == B.get(i).get(0)) {
                    C.add(A.get(i));
                    i++;
                    j++;
                } else {
                    C.add(A.get(i));
                    i++;
                    C.add(B.get(j));
                    j++;
                }
            } else if (A.get(i).get(2) > B.get(j).get(2)) {
                C.add(B.get(j));
                j++;
            } else if (A.get(i).get(2) < B.get(j).get(2)) {
                C.add(A.get(i));
                i++;
            }
        }
        if (i >= p) {
            for (; j < q; j++) {
                C.add(B.get(j));
            }
        } else {
            for (; i < p; i++) {
                C.add(A.get(i));
            }
        }
        return C;
    }

    /**
     * DFS is used to search and traverse through the graph
     * @param n The node to start at
     * @param c2 The computer id that is trying to be found
     * @param time The time at which is being checked to see if c2 has been infected by
     * @return The ComputerNode communication of c2 that has been infected by time time
     */
    private ComputerNode DFS(ComputerNode n, int c2, int time) {
        n.setColor(0);
        n.setPred(null);
        initDFS(n);
        if (n.getID() == c2 && n.getTimestamp() <= time) {
        	return n;
        }
        return DFSVisit(n, c2, time);
    }

    /**
     * Used to check to see if the current node has neighbors that have the id of c2 and has been infected at time time
     * @param n current node
     * @param c2 The computer id that is trying to be found
     * @param time The time at which is being checked to see if c2 has been infected by
     * @return The ComputerNode communication of c2 that has been infected by time time
     */
    private ComputerNode DFSVisit(ComputerNode n, int c2, int time) {
    	n.setColor(1);
    	for (ComputerNode neighbor : n.getOutNeighbors()) {
            if (neighbor.getID() == c2 && neighbor.getTimestamp() <= time) {
                neighbor.setPred(n);
                return neighbor;
    		}
    		else if (neighbor.getColor() == 0) {
                neighbor.setPred(n);
                ComputerNode returnedNode = DFSVisit(neighbor, c2, time);
                if (returnedNode != null) {
                    return returnedNode;
                }
    		}
    	}
    	n.setColor(2);
        return null;
       }

    /**
     * Initializes all of the ComputerNodes color to be white
     * @param n The node to start at
     */
    private void initDFS(ComputerNode n) {
        for (ComputerNode neighbor : n.getOutNeighbors()) {
            if (neighbor.getColor() != 0) {
                neighbor.setColor(0);
                neighbor.setPred(null);
                if (neighbor.getOutNeighbors() != null) {
                    initDFS(neighbor);
                }
            }
        }
    }

    public void printComputerMapping() {
        int i = 0, j = 0;
        while (i < mapping.size()) {
            System.out.println("" + j + " :");
            if (mapping.get(j) != null) {
                for (ComputerNode c : mapping.get(j)) {
                    System.out.println("\tID : " + c.getID());
                    System.out.println("\tTimestamp : " + c.getTimestamp());
                    System.out.println("\tNeighbors : ");
                    for (ComputerNode d : c.getOutNeighbors()) {
                        System.out.println("\t\tID : " + d.getID());
                        System.out.println("\t\tTimestamp : " + d.getTimestamp() + "\n");
                    }
                }
                i++;
            } else {
                System.out.println("\tThis entry is empty");
            }
            j++;
        }
    }

    public void PrintPathList(List<ComputerNode> list) {
        if (list == null) {
            System.out.println("List is empty: No Possible Path\n");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print("<" + list.get(i).getID() + "," + list.get(i).getTimestamp() + ">" + " ");
        }
        System.out.println("\n");
    }
}
