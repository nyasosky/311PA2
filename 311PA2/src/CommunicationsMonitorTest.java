import org.junit.jupiter.api.Test;

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
        System.out.println(test.queryInfection(4, 2, 1, 1).toString());
        test.printComputerMapping();
    }

}
