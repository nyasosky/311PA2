import org.junit.jupiter.api.Test;

import java.util.List;

public class CommunicationsMonitorTest {
    @Test
    void test1() {
        CommunicationsMonitor test = new CommunicationsMonitor();
        test.addCommuncication(1, 3, 3);
        test.addCommuncication(1, 3, 2);
        test.addCommuncication(1, 3, 1);
        test.addCommuncication(1, 2, 1);
        test.addCommuncication(1, 4, 1);
        test.createGraph();
        List<ComputerNode> testList = test.queryInfection(4, 3, 1, 2);
        test.PrintPathList(testList);
        test.printComputerMapping();
    }
    
    @Test
    void test2() {
    	CommunicationsMonitor test = new CommunicationsMonitor();
    	test.addCommuncication(1, 2, 1);
    	test.addCommuncication(1, 4, 3);
    	test.addCommuncication(2, 3, 2);
    	test.addCommuncication(3, 4, 6);
    	test.createGraph();
    	List<ComputerNode> testList = test.queryInfection(2, 4, 1, 6);
    	test.PrintPathList(testList);
    	test.printComputerMapping();
    }
    
    @Test
    void test3() {
    	CommunicationsMonitor test = new CommunicationsMonitor();
    	test.addCommuncication(2, 1, 1);
    	test.createGraph();
    	List<ComputerNode> testList = test.queryInfection(2, 1, 1, 3);
    	test.PrintPathList(testList);
    	test.printComputerMapping();
    }
    
    @Test
    void test4() {
    	CommunicationsMonitor test = new CommunicationsMonitor();
    	test.addCommuncication(1, 2, 1);
    	test.addCommuncication(1, 3, 1);
    	test.addCommuncication(2, 3, 2);
    	test.addCommuncication(3, 4, 3);;
    	test.createGraph();
    	List<ComputerNode>testList = test.queryInfection(1, 3, 1, 9);
    	test.PrintPathList(testList);
    	test.printComputerMapping();
    	
    }
    
    @Test
    void noPossiblePath() {
    	CommunicationsMonitor test = new CommunicationsMonitor();
    	test.addCommuncication(1, 3, 1);
    	test.addCommuncication(1, 2, 2);
    	test.addCommuncication(2, 4, 3);
    	test.createGraph();
    	List<ComputerNode> testList = test.queryInfection(1, 4, 1, 2);
    	test.PrintPathList(testList);
    	test.printComputerMapping();
    }

}
