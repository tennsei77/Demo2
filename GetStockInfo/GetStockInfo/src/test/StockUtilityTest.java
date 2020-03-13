package test;

import org.junit.Test;
import stock.StockItem;
import stock.StockUtility;

import static org.junit.Assert.assertEquals;

public class StockUtilityTest {

    @Test
    public void retrieveStock() throws Exception {
        StockItem itemforinfo = StockUtility.retrieveStock("3103");
        assertEquals("ユニチカ(株)",itemforinfo.name);
        System.out.println("Junit is working fine");
    }
}