//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.junit.platform.commons.util.StringUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Iterator;

public class CheckTableSql {

//    public static void main(String[] args)throws IOException {
//        InputStream stream = CheckTableSql.class.getResourceAsStream("111.xlsx");
//        Workbook workbook = WorkbookFactory.create(stream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> iterator = sheet.rowIterator();
//        System.out.println("INSERT INTO b_check_table (id, check_type, check_item, content, con_detail, law_item, update_time, create_time) VALUES ");
//        int i = 0;
//        while (iterator.hasNext()){
//            Row row = iterator.next();
//            if(StringUtils.isNotBlank(getValue(row, 0))){
//                if(i++ > 0){
//                    String s = String.format("('%s','%s', '%s', '%s', '%s', '%s', now(), now()),",
//                            String.format("%03d",i-1), getValue(row, 0), getValue(row, 1), getValue(row, 2),
//                            getValue(row, 3), getValue(row, 4));
//                    System.out.println(s);
//                }
//            }
//        }
//    }
//
//    private static String getValue(Row row, int col){
//        return row.getCell(col).getStringCellValue();
//    }

}
