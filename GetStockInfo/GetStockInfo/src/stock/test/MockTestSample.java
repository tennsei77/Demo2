package stock.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class MockTestSample {
    @Test
    public void argumentMatcherTest(){
        List<String> list = mock(List.class);

        when(list.get(anyInt())).thenReturn("helloworld");

        String result = list.get(0)+list.get(1);

        verify(list,times(2)).get(anyInt());

        Assert.assertEquals("helloworld", result);
    }
}
