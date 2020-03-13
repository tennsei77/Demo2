package stock;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.net.URL;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
import  java.util.*;
import java.text.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class StockInfo {
    public static void main(String[] args) throws Exception {
        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://192.168.100.112:3306/test?characterEncoding=utf-8", "root", "password");

        String sqlInsert = "insert into FinanceTest.FinanceData (DataGetTime,StockCode,Price,TradingVolume) VALUES (?,?,?,?)";
        con.setAutoCommit(false);
        con.createStatement().execute("DELETE FROM FinanceTest.FinanceData;") ;

        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

        try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

            for (int s = 1300; s <= 9999; s++) {

                try {
                    String strCode = String.format("%04d", s);
                    String strURL = "https://stocks.finance.yahoo.co.jp/stocks/detail/?code=" + strCode;
                    //String strURL = "https://stocks.finance.yahoo.co.jp/stocks/detail/?code="+args ;
                    URL urlobj = new URL(strURL);
                    HttpsURLConnection http = (HttpsURLConnection) urlobj.openConnection();
                    //InputStreamReader is = new InputStreamReader(http.getInputStream());
                    InputStream is = http.getInputStream();

                    // 可変長リストへ読み込み
                    int xVal;
                    ArrayList<Byte> outByteList = new ArrayList<Byte>();
                    while ((xVal = is.read()) != -1) {
                        outByteList.add((byte) xVal);
                    }

                    // 可変長リスト => 配列
                    byte[] res = new byte[outByteList.size()];
                    int i = 0;
                    for (Byte v : outByteList) {
                        res[i++] = v;
                    }
                    // byte[] => String
                    String htmlText = new String(res, "utf-8");
                    Document doc = Jsoup.parse(new ByteArrayInputStream(res), "utf-8", strURL);
                    Element elemStockCode = doc.selectFirst("div.forAddPortfolio dl.stocksInfo dt");

                    if (elemStockCode != null) {

                        Element elemStockName = doc.selectFirst("#stockinf th.symbol h1");
                        Element elemStockPrice =  doc.selectFirst("#stockinf td.stoksPrice:not(.realTimChange)") ;
                        //        System.out.println( "Price = " + elemStockPrice.html() ) ;
                        //Element elemStoksVolume =  doc.selectFirst("div#detail").select("div.lineFi").get(4).selectFirst("strong") ;

                        Element elemStoksVolume =  doc.selectFirst("div.marB6.chartFinance").select("div.lineFi").get(4).selectFirst("strong") ;
                        //        System.out.println( "StoksVolume = " + elemStoksVolume.html() ) ;

                        String strStockCode = elemStockCode.html();
                        String strStockPrice = elemStockPrice.html();
                        String strStoksVolume = elemStoksVolume.html();


                        //System.out.println("当前时间为: " + ft.format(dNow));

                        ps.setString(1, ft.format(dNow));
                        ps.setString(2, strStockCode);
                        ps.setInt(3,  Integer.parseInt(strStockPrice.replaceAll(",","")));
                        ps.setInt(4, Integer.parseInt(strStoksVolume.replaceAll(",","")));

                        ps.executeUpdate();

                        //con.createStatement().execute("INSERT()") ;

                        System.out.println("Code=" + strStockCode); //打印结果
                        if(s%100==99) {
                         con.commit();
                        }
                    }
                } catch (Exception ex) {
                }
            }
            con.commit();
        }







    }
}
