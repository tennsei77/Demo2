package stock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StockUtility {

    static public StockItem retrieveStock(String code) throws Exception {
        StockItem stockItem = null;
        String strURL = "https://stocks.finance.yahoo.co.jp/stocks/detail/?code=" + code;
        URL urlobj = new URL(strURL);
        HttpsURLConnection http = (HttpsURLConnection) urlobj.openConnection();
        try(InputStream is = http.getInputStream()){
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

            String htmlText = new String(res, "utf-8");
            Document doc = Jsoup.parse(new ByteArrayInputStream(res), "utf-8", strURL);

            Element elemStockName = doc.selectFirst("#stockinf th.symbol h1");
            Element elemStockPrice =  doc.selectFirst("#stockinf td.stoksPrice:not(.realTimChange)") ;
            Element elemStoksVolume =  doc.selectFirst("div.marB6.chartFinance").select("div.lineFi").get(4).selectFirst("strong") ;

            if(elemStockName!=null && elemStockPrice!=null && elemStoksVolume!=null) {

                stockItem = new StockItem();

                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

                String NowdateTime= ft.format(dNow);
                String strStockName = elemStockName.html();
                String strStockPrice = elemStockPrice.html();
                String strStoksVolume = elemStoksVolume.html();

                stockItem.tradeTime=NowdateTime;
                stockItem.code=code;
                stockItem.name=strStockName;
                stockItem.price=Integer.parseInt(strStockPrice.replaceAll(",",""));
                stockItem.volume=Long.parseLong(strStoksVolume.replaceAll(",",""));

            }
        }
        return stockItem;
    }

    public static void main(String[] args) throws Exception {
        String testStockinfo = "3103";
        StockItem a = retrieveStock(testStockinfo);

        System.out.println(a.code);
        System.out.println(a.name);
        System.out.println(a.price);
        System.out.println(a.tradeTime);
        System.out.println(a.volume);

    }
}
