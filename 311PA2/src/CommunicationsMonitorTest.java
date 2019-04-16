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
        List<ComputerNode> testList = test.queryInfection(4, 2, 1, 1);
        System.out.println(test.queryInfection(4, 2, 1, 1).toString());
        test.printComputerMapping();
    }

}
