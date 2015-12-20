package pl.pawc.Remote;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ClientReceivingTest extends TestCase{
    public ClientReceivingTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(ClientReceivingTest.class);
    }

    public void testClientReceiving(){
        assertTrue(true);
    }
}
