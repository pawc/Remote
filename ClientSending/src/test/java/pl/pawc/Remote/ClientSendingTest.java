package pl.pawc.Remote;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ClientSendingTest extends TestCase{
    public ClientSendingTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(ClientSendingTest.class);
    }

    public void testClientSending(){
        assertTrue(true);
    }
}
