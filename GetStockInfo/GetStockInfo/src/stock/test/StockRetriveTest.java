package stock.test;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stock.StockItem;
import stock.StockUtility;

public class StockRetriveTest extends TestCase {

    protected double fValue1;
    protected double fValue2;

    @Before
    public void setUp() {
        fValue1= 2.0;
        fValue2= 3.0;
        System.out.println("setUp");


    }

    @Test
    public void testCase() {
        System.out.println("stock.StockRetrive testCase "+ this.countTestCases());
        //Result result = JUnitCore.runClasses(stock.StockRetrive.class);
    }

    @Test
    public void testStock() throws Exception {
        //System.out.println("stock.StockRetrive testStock "+ this.countTestCases());
        //Result result = JUnitCore.runClasses(stock.StockRetrive.class);

        String str= "Junit is working fine";
        //check for equality
        assertEquals("Junit is working fine", str);

        StockItem itemforinfo = StockUtility.retrieveStock("3103");
        assertEquals("ユニチカ(株)",itemforinfo.name);

        System.out.println("Junit is working fine");

    }

    @Before
    @After

    public void tearDown() {

    }

}
