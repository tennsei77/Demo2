import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.URL;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;

public class StockRetrive {

    public static void retrieveAndSaveMaster() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // try using resouce
        try(Connection con = DriverManager.getConnection("jdbc:mysql://192.168.100.112:3306/test?characterEncoding=utf-8", "root", "password")) {
            String sqlInsert = "insert into FinanceTest.CodeMaster (StockCode,StockName) VALUES (?,?)";
            con.setAutoCommit(false);
            try(PreparedStatement ps = con.prepareStatement(sqlInsert)){
                for(int i=0;i<=9999;i++) {
                    try {
                        String code = String.format("%04d", i);
                        StockItem item = StockUtility.retrieveStock(code) ;
                        if (item != null) {
                            ps.setString(1, item.code);
                            ps.setString(2, item.name);
                            ps.executeUpdate();
                            //con.createStatement().execute("INSERT()") ;

                            System.out.println("Code="+item.code+" Name="+item.name); //打印结果
                            if(i%100==99) {
                                con.commit();
                            }
                        }
                    } catch(Exception ex) {

                    }

                }
                con.commit();
            }
        }
    }

    public static void retrieveAndSaveValue() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // try using resouce
        try (Connection con = DriverManager.getConnection("jdbc:mysql://192.168.100.112:3306/test?characterEncoding=utf-8", "root", "password")) {
            String sqlInsert = "insert into FinanceTest.FinanceData (DataGetTime,StockCode,Price,TradingVolume) VALUES (?,?,?,?)";
            String sql = "SELECT * FROM FinanceTest.CodeMaster;";
            con.setAutoCommit(false);

            // SELECT STOCKCODE FROM CODEMASTER
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                String searchCode = result.getString(1);
                try {
                    StockItem itemforinfo = StockUtility.retrieveStock(searchCode);

                    try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                        ps.setString(1, itemforinfo.tradeTime);
                        ps.setString(2, itemforinfo.code);
                        ps.setInt(3, itemforinfo.price);
                        ps.setLong(4, itemforinfo.volume);

                        ps.executeUpdate();
                        //con.createStatement().execute("INSERT()") ;

                        System.out.println("Code="+itemforinfo.code); //打印结果
                        con.commit();
                    }

                }catch(Exception ex) {
                    System.out.println("错误Code="+searchCode);
                    ex.printStackTrace();
                }

            };

        }
    }

    public static void main(String[] args) throws Exception {

        if(args.length==0) {
            retrieveAndSaveValue() ;
        } else if(args.length==1 && args[0].equals("code")) {
            retrieveAndSaveMaster();
        } else {
            for(String code: args) {
                StockItem itemforinfo = StockUtility.retrieveStock(code);
                System.out.println(itemforinfo.code);
                System.out.println(itemforinfo.name);
                System.out.println(itemforinfo.price);
                System.out.println(itemforinfo.tradeTime);
                System.out.println(itemforinfo.volume);

            }
        }



        //String strType = "" ;
        //if(args.length==0){
            //System.out.println("请输入：");
            // InputStream / Reader
            //  OutputStream / Writer
           // String  str = new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine() ;
            //System.in
            //Scanner A=new Scanner(System.in);
            //String str = A.next();
            //strType = str ;
            //System.out.println("您输入的数字是"+str);
       // } else {
           // System.out.println("Hello World");// 打印 Hello World
            //for(String s: args) {
            // System.out.print(s+" ") ;
                /*
                System.out.print(s) ;
                System.out.print(" ");
                */
            //String test;
            // }
         //con.createStatement().execute("DELETE FROM FinanceTest.CodeMaster;") ;
/*
        for(int i=0; i<outByteList.size(); ++i) {
            res[i] = outByteList.get(i) ;
        }
*/
//        System.out.println(htmlText);
/*
        //
        java.io.ByteArrayOutputStream ous = new java.io.ByteArrayOutputStream() ;
        byte[] buf = new byte[1024] ;
        int count = 0 ;
        while((count=is.read(buf))>0) {
            ous.write(buf,0, count);
        }
        System.out.println(new String(ous.toByteArray(),"utf-8"));
 */

//        Document doc = Jsoup.connect(strURL).get();
//                        con.rollback();

            //System.out.println("Code = " + elemStockCode.html());

            //Element elemStockName = doc.selectFirst("#stockinf th.symbol h1");
            //System.out.println("Name = " + elemStockName.html());

//        Element elemStockPrice =  doc.selectFirst("#stockinf td.stoksPrice:not(.realTimChange)") ;
//        System.out.println( "Price = " + elemStockPrice.html() ) ;

//        Element elemStoksVolume =  doc.selectFirst("div#detail").select("div.lineFi").get(4).selectFirst("strong") ;
//        System.out.println( "StoksVolume = " + elemStoksVolume.html() ) ;
        }
    }

