package stock.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import stock.StockRetrive;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StockRetrive.class)
public class MockStaticTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testplusplus() {

        PowerMockito.mockStatic(StockRetrive.class);
        when(StockRetrive.staticplus(1,1)).thenReturn("Test") ;

        //String staticresult1 = StockRetrive.staticplus(1,1);
        Assert.assertEquals("Test", StockRetrive.staticplus(1,1)) ;
        //System.out.println(staticresult1);

    }

}
