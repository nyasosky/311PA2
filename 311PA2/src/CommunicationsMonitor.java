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

    public void addCommunication(int c1, int c2, int timestamp) {
        if (!graphMade) {
            List<Integer> tripleToAdd = new ArrayList<Integer>();
            tripleToAdd.add(c1);
            tripleToAdd.add(c2);
            tripleToAdd.add(timestamp);
            tripleList.add(tripleToAdd);
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

    public HashMap<Integer, List<ComputerNode>> getComputerMapping() {
        if (graphMade) {
            return mapping;
        } else {
            return null;
        }
	}
	
	public List<ComputerNode> getComputerMapping(int c){
        if (graphMade) {
            return mapping.get(c);
        } else {
            return null;
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

    public ComputerNode DFS(ComputerNode n, int c2, int time) {
        n.setColor(0);
        n.setPred(null);
        initDFS(n);
        return DFSVisit(n, c2, time);
    }

    public ComputerNode DFSVisit(ComputerNode n, int c2, int time) {
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
}
