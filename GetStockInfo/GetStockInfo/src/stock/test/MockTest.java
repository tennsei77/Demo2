package stock.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import stock.StockRetrive;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockTest {

    @Mock
    StockRetrive sRetrive;


    @Test
    public void testPlus() throws Exception {


        //StockRetrive 是一个方法 但还没完成 想要实现输入2，输出3，继续用这个3做事情

//        when(sRetrive.plus(100)).thenReturn("200");
//        String stringresult = sRetrive.plus(100);
        when(sRetrive.nochange(anyInt())).thenReturn("Test") ;
        when(sRetrive.nochange(100)).thenReturn("200") ;
        //when(sRetrive.plus(50)).thenReturn("50") ;

        String stringresult1 = sRetrive.nochange(70);
        String stringresult2 = sRetrive.nochange(100);
        String stringresult3 = sRetrive.nochange(50);

        //输入22，指定plus方法返回“Hello world”

        Assert.assertEquals("Test", sRetrive.nochange(70)) ;    // ????
        System.out.println(stringresult1);
        Assert.assertEquals("200", sRetrive.nochange(100)) ;    // ????
        System.out.println(stringresult2);
        Assert.assertEquals("Test", sRetrive.nochange(50)) ;    // ????
        System.out.println(stringresult3);

        //verify(sRetrive, times(2)).plus(anyInt());
        //verify(sRetrive, times(1)).retrieveAndSaveValue();
    }

//    @Test
//    public void testPlusplus() throws Exception {
//
//        //测试静态方法
//        when(sRetrive.plusplus(1,1)).thenReturn("1+1=2") ;
//        String stringany = sRetrive.plusplus(1,1);
//        Assert.assertEquals("1+1=2", sRetrive.plusplus(1,1)) ;
//        System.out.println(stringany);
//
////        when(sRetrive.plusplus(1,1)).thenReturn("1+1=2") ;
////        String stringresultplus = sRetrive.plusplus(1,1);
////        Assert.assertEquals("1+1=2", sRetrive.plusplus(1,1)) ;
////        System.out.println(stringresultplus);
////
////        String stringany2 = sRetrive.plusplus(2,2);
////        Assert.assertEquals("everything", sRetrive.plusplus(2,2)) ;
////        System.out.println(stringany2);
//
//    }

    //@Test
//    public void verify_behaviour(){
//        //模拟创建一个List对象
//        List mock = mock(List.class);
//        //使用mock的对象
//        mock.add(1);
//        mock.clear();
//        //验证add(1)和clear()行为是否发生
//        verify(mock).add(1);
//        System.out.println("OK");
//        verify(mock).clear();
//
//    }


}
