import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Newpneumonia {
    public static void main(String[] args) throws Exception {
        String strCode = "" ;
        if(args.length==0){
            System.out.println("请输入：");
            // InputStream / Reader
            //  OutputStream / Writer
            String  str = new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine() ;
            //System.in
            //Scanner A=new Scanner(System.in);
            //String str = A.next();
            strCode = str ;
            System.out.println("您输入的数字是"+str);
        } else {
            System.out.println("Hello World");// 打印 Hello World
            for(String s: args) {
                System.out.println(s+" ") ;
            }
            strCode = args[0] ;
        }

        //      Document doc = Jsoup.parse(new ByteArrayInputStream(res), "utf-8", strURL) ;
        Document doc = Jsoup.connect("https://3g.dxy.cn/newh5/view/pneumonia").get();

        Element elemConfirmCount =  doc.selectFirst("script#getStatisticsService") ;
        //System.out.println( "确诊 = " + elemConfirmCount.html() ) ;

        String htmText = elemConfirmCount.html() ;
        int startPos = htmText.indexOf("=") ;
        int lastPos = htmText.indexOf("}catch") ;
        String jsonText = htmText.substring(startPos+1,lastPos).trim();

        ObjectMapper om = new ObjectMapper() ;
        JsonNode rootNode = om.readTree(jsonText) ;

        KoronaStatus statusOfChina = new KoronaStatus() ;
        KoronaStatus statusOfJs = new KoronaStatus() ;

        statusOfChina.confirmedCount = rootNode.get("confirmedCount").asInt();
        statusOfChina.suspectedCount = rootNode.get("suspectedCount").asInt();
        statusOfChina.curedCount = rootNode.get("curedCount").asInt();
        statusOfChina.deadCount = rootNode.get("deadCount").asInt();

        System.out.println("确诊 = " + statusOfChina.confirmedCount);
        System.out.println("疑似 = " + statusOfChina.suspectedCount);
        System.out.println("治愈 = " + statusOfChina.curedCount);
        System.out.println("死亡 = " + statusOfChina.deadCount);

        Element elemJiangsuCount =  doc.selectFirst("script#getAreaStat") ;

        String htmJiangsuText = elemJiangsuCount.html() ;
        int startJiangsuPos = htmJiangsuText.indexOf("=") ;
        int lastJiangsuPos = htmJiangsuText.indexOf("}catch") ;
        String jsonJiangsuText = htmJiangsuText.substring(startJiangsuPos+1,lastJiangsuPos).trim();
//    System.out.println( "江苏 = " + jsonJiangsuText ) ;

        JsonNode rootNodeAll = om.readTree(jsonJiangsuText) ;
        rootNodeAll.forEach(provNode->{
            if(provNode.get("provinceName").asText().equals("江苏省")) {

//                statusOfJs = new KoronaStatus() ;
                statusOfJs.confirmedCount= provNode.get("confirmedCount").asInt();
                statusOfJs.suspectedCount=provNode.get("suspectedCount").asInt();
                statusOfJs.curedCount=provNode.get("curedCount").asInt();
                statusOfJs.deadCount=provNode.get("deadCount").asInt();

                System.out.println("江苏省");
                System.out.println( "确诊 = " + statusOfJs.confirmedCount ) ;
                System.out.println( "疑似 = " + statusOfJs.suspectedCount ) ;
                System.out.println( "治愈 = " + statusOfJs.curedCount ) ;
                System.out.println( "死亡 = " + statusOfJs.deadCount ) ;

                provNode.get("cities").forEach(cityNode->{
                    if(cityNode.get("cityName").asText().equals("扬州")) {
                        System.out.println("扬州市");
                        System.out.println( "确诊 = " + cityNode.get("confirmedCount").asInt() ) ;
                        System.out.println( "疑似 = " + cityNode.get("suspectedCount").asInt() ) ;
                        System.out.println( "治愈 = " + cityNode.get("curedCount").asInt() ) ;
                        System.out.println( "死亡 = " + cityNode.get("deadCount").asInt() ) ;
                    }
                });
            }
        });

        /*
        String outputPath = "C:\\Users\\xu-ts\\Desktop\\java_excel_test\\";  // TODO
        String fileName = "test.xlsx";

        String excelPath = outputPath+fileName;
        File excelFile = new File(excelPath);

        try(XSSFWorkbook workbook = excelFile.isFile() ?  new XSSFWorkbook(excelFile) : new XSSFWorkbook()) {


            String sheetNameCountry = "全国肺炎疫情" ;
            XSSFSheet sheetCountry = workbook.getSheet(sheetNameCountry);
            if(sheetCountry==null) {
                sheetCountry = workbook.createSheet(sheetNameCountry);
            }
            int lastRowIndexCountry = sheetCountry.getLastRowNum() ;
            if(lastRowIndexCountry==0) {
                XSSFRow rowTitle = sheetCountry.createRow(1) ;
                rowTitle.createCell(1).setCellValue("AAAAAA"); ;
                rowTitle.createCell(2).setCellValue("AAAAAA"); ;
                rowTitle.createCell(3).setCellValue("AAAAAA"); ;
                rowTitle.createCell(4).setCellValue("AAAAAA"); ;
                lastRowIndexCountry++ ;
            }

            XSSFRow rowDataCountry = sheetCountry.createRow(lastRowIndexCountry+1) ;
            rowDataCountry.createCell(1).setCellValue(statusOfChina.confirmedCount); ;
            rowDataCountry.createCell(2).setCellValue(statusOfChina.suspectedCount); ;
            rowDataCountry.createCell(3).setCellValue(statusOfChina.curedCount); ;
            rowDataCountry.createCell(4).setCellValue(statusOfChina.deadCount); ;


            String sheetNameJianshu = "江苏省肺炎疫情" ;
            XSSFSheet sheetJianshu = workbook.getSheet(sheetNameJianshu);
            if(sheetJianshu==null) {
                sheetJianshu = workbook.createSheet(sheetNameJianshu);
            }

            if(excelFile.isFile()) {
                excelFile.delete() ;
            }
            try(OutputStream outs = new FileOutputStream(excelFile,false)) {
                workbook.write(outs);
            }

        }

         */

        String outputPath = "C:\\Users\\xu-ts\\Desktop\\java_excel_test\\";  // TODO
        String fileName = "test.xlsx";

        String excelPath = outputPath+fileName;

        XSSFWorkbook workbook1 = new XSSFWorkbook();
        XSSFSheet quanguoSheet = workbook1.createSheet();
        workbook1.setSheetName(0, "全国肺炎疫情");
        quanguoSheet = workbook1.getSheet("全国肺炎疫情");

        XSSFSheet jiangsuSheet = workbook1.createSheet();
        workbook1.setSheetName(1, "江苏省肺炎疫情");
        jiangsuSheet = workbook1.getSheet("江苏省肺炎疫情");


        //全国sheet数据
        XSSFRow row1 = quanguoSheet.createRow(1);
        XSSFCell cell1 = row1.createCell(1);
                 cell1.setCellValue("确诊:");

        XSSFCell cell2 = row1.createCell(2);
                 cell2.setCellValue(statusOfChina.confirmedCount);

        XSSFRow row2 = quanguoSheet.createRow(2);
        XSSFCell cell3 = row2.createCell(1);
        XSSFCell cell4 = row2.createCell(2);

        XSSFRow row3 = quanguoSheet.createRow(3);
        XSSFCell cell5 = row3.createCell(1);
        XSSFCell cell6 = row3.createCell(2);

        XSSFRow row4 = quanguoSheet.createRow(4);
        XSSFCell cell7 = row4.createCell(1);
        XSSFCell cell8 = row4.createCell(2);


        cell3.setCellValue("疑似:");
        cell4.setCellValue(statusOfChina.suspectedCount);

        cell5.setCellValue("治愈:");
        cell6.setCellValue(statusOfChina.curedCount);

        cell7.setCellValue("死亡:");
        cell8.setCellValue(statusOfChina.deadCount);


        //江苏sheet数据
        XSSFRow row21 = jiangsuSheet.createRow(1);
        XSSFCell cell21 = row21.createCell(1);
        XSSFCell cell22 = row21.createCell(2);


        XSSFRow row22 = jiangsuSheet.createRow(2);
        XSSFCell cell23 = row22.createCell(1);
        XSSFCell cell24 = row22.createCell(2);

        XSSFRow row23 = jiangsuSheet.createRow(3);
        XSSFCell cell25 = row23.createCell(1);
        XSSFCell cell26 = row23.createCell(2);

        XSSFRow row24 = jiangsuSheet.createRow(4);
        XSSFCell cell27 = row24.createCell(1);
        XSSFCell cell28 = row24.createCell(2);

        cell21.setCellValue("确诊:");
        cell22.setCellValue(statusOfJs.confirmedCount);

        cell23.setCellValue("疑似:");
        cell24.setCellValue(statusOfJs.suspectedCount);

        cell25.setCellValue("治愈:");
        cell26.setCellValue(statusOfJs.curedCount);

        cell27.setCellValue("死亡:");
        cell28.setCellValue(statusOfJs.deadCount);

        // 出力ファイルの設定
        FileOutputStream outExcelFile = null;
//        String outputPath = "C:\\Users\\xu-ts\\Desktop\\java_excel_test\\";
//        String fileName = "test.xlsx";

        try{
            // ファイルを出力
//            String excelPath = outputPath+fileName;
            File excel = new File(excelPath);

            if (excel.isFile() && excel.exists()) {
                System.out.println( "文件已存在") ;
                //开始解析
                XSSFWorkbook wb;
                //XSSFWorkbook workbook1 = new XSSFWorkbook();

                 wb = new XSSFWorkbook(excel);
                //开始解析
                XSSFSheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: "+firstRowIndex);
                System.out.println("lastRowIndex: "+lastRowIndex);


                XSSFRow nrow1 = sheet.createRow(lastRowIndex+2);
                    XSSFCell ncell1 = nrow1.createCell(1);
                    ncell1.setCellValue("确诊:");

                    XSSFCell ncell2 = nrow1.createCell(2);
                    ncell2.setCellValue(statusOfChina.confirmedCount);


                    try {// 変更するエクセルファイルを指定
                        excel.delete() ; //需要先删除文件
                        FileOutputStream out = new FileOutputStream(excelPath);
                        wb.write(out);
                }catch(Exception e){
                    e.printStackTrace();
                } finally{
                    //out.close();
                    wb.close();
                }

            }else {
                outExcelFile = new FileOutputStream(outputPath + fileName);
                workbook1.write(outExcelFile);
                System.out.println("Excel放在了"+outputPath);
            }

        }catch(Exception e){
            System.out.println(e.toString());
        }finally{
            try {
                outExcelFile.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }

    }
}

//String strURL = "https://stocks.finance.yahoo.co.jp/stocks/detail/?code="+strCode ;
//URL urlobj = new URL(strURL);
//HttpsURLConnection http = (HttpsURLConnection) urlobj.openConnection();
//InputStreamReader is = new InputStreamReader(http.getInputStream());
//InputStream is = http.getInputStream();

// 可変長リストへ読み込み
//int xVal ;
//ArrayList<Byte> outByteList = new ArrayList<Byte>() ;
//while( (xVal = is.read())!=-1) {
//    outByteList.add((byte) xVal) ;
//}

// 可変長リスト => 配列
//byte[] res = new byte[outByteList.size()] ;
/*
        for(int i=0; i<outByteList.size(); ++i) {
            res[i] = outByteList.get(i) ;
        }
*/
//int i = 0 ;
//for(Byte v : outByteList) {
//    res[i++] = v ;
//}
// byte[] => String
//String htmlText = new String(res,"utf-8") ;
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

//    System.out.println( "江苏 = " + elemJiangsuCount.html() ) ;
/*
        String htmJiangsuText = elemJiangsuCount.html() ;
        int startJiangsuPos = htmJiangsuText.indexOf("江苏省") ;
        //int lastJiangsuPos = htmJiangsuText.indexOf("\"cities\": [{") ;
        String jsonJiangsuText = htmJiangsuText.substring(startJiangsuPos,startJiangsuPos+100).trim();
        System.out.println( "江苏 = " + jsonJiangsuText ) ;
*/
