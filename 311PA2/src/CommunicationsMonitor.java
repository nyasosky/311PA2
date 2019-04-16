import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class CommunicationsMonitor {

    private HashMap<Integer, List<ComputerNode>> mapping;
    private List<List<Integer>> tripleList;
    private boolean graphMade;
	
	public CommunicationsMonitor() {
        mapping = new HashMap<Integer, List<ComputerNode>>();
        tripleList = new ArrayList<List<Integer>>();
        graphMade = false;
	}
	
	public void addCommuncication(int c1, int c2, int timestamp) {
        if (!graphMade) {
            List<Integer> tripleToAdd = new ArrayList<Integer>();
            tripleToAdd.add(c1);
            tripleToAdd.add(c2);
            tripleToAdd.add(timestamp);
            tripleList.add(tripleToAdd);
        } else {
            System.out.println("Sorry the graph has already been made. You can no longer add more computer communications to the list");
        }
	}
	
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
                    }
                }
                if (mapping.get(triple.get(1)) == null) {
                    c2List = new ArrayList<ComputerNode>();
                } else {
                    c2List = mapping.get(triple.get(1));
                    if (c2List.get(c2List.size() - 1).getTimestamp() == triple.get(2)) {
                        c2Node = c2List.get(c2List.size() - 1);
                        newC2Node = false;
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
	
	public List<ComputerNode> queryInfection(int c1, int c2, int x, int y){
        if (graphMade) {
            ArrayList<ComputerNode> list = new ArrayList<ComputerNode>();
            for (ComputerNode n : mapping.get(c1)) {
            	if (n.getTimestamp() >= x) {
            		DFS(n, c2, y, list);
            		break;
            	}
            }
            return list;
        } else {
            System.out.println("Graph has not been made yet");
            return new ArrayList<ComputerNode>();
        }
	}

    public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
        if (graphMade) {
            return mapping;
        } else {
            System.out.println("Graph has not been made yet");
            return new HashMap<Integer, List<ComputerNode>>();
        }
	}
	
	public List<ComputerNode> getComputerMapping(int c){
        if (graphMade) {
            if (mapping.get(c) != null)
                return mapping.get(c);
            return new ArrayList<ComputerNode>();
        } else {
            System.out.println("Graph has not been made yet");
            return new ArrayList<ComputerNode>();
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

    private List<List<Integer>> MergeSort(List<List<Integer>> list) {
        int n = list.size();
        if (n == 1)
            return list;
        List<List<Integer>> firstHalf = list.subList(0, n / 2);
        List<List<Integer>> secondHalf = list.subList(n / 2, n);
        return Merge(MergeSort(firstHalf), MergeSort(secondHalf));
    }

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
    
    public void DFS(ComputerNode n, int c2, int time, ArrayList<ComputerNode> list){
    	for (ComputerNode neighbor : n.getOutNeighbors()) {
    		neighbor.setColor(0);
    		neighbor.setPred(null);
    	}
    	for (ComputerNode node : mapping.get(n.getID())){
    		if (node.getColor() == 0) {
    			DFSVisit(node, c2, time, list);
    		}
    		
    	}
    }
    
    public void DFSVisit(ComputerNode n, int c2, int time, ArrayList<ComputerNode> list) {
    	n.setColor(1);
    	for (ComputerNode neighbor : n.getOutNeighbors()) {
    		if (neighbor.getID() == c2 && neighbor.getTimestamp() == time) {
    			list.add(neighbor);
    			while(neighbor.getPred() != null) {
    				list.add(neighbor.getPred());
    			}
    			Collections.reverse(list);
    		}
    		else if (neighbor.getColor() == 0) {
    			neighbor.setPred(n);
    			DFSVisit(neighbor, c2, time, list);
    		}
    	}
    	n.setColor(2);
    }
}
