//import com.ts.server.safe.base.domain.UniCheckItem;
//import com.ts.server.safe.base.domain.UniCheckTable;
//import com.ts.server.safe.base.domain.UniCheckType;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicInteger;

public class CheckTableSql {

//    private static AtomicInteger typeId = new AtomicInteger(0);
//    private static List<UniCheckType> types = new ArrayList<>();
//    private static AtomicInteger itemId = new AtomicInteger(0);
//    private static List<UniCheckItem> items = new ArrayList<>();
//    private static AtomicInteger tableId = new AtomicInteger(0);
//    private static List<UniCheckTable> tables = new ArrayList<>();
//
//    public static void main(String[] args)throws IOException {
//        InputStream stream = CheckTableSql.class.getResourceAsStream("111.xlsx");
//        Workbook workbook = WorkbookFactory.create(stream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> iterator = sheet.rowIterator();
//
//        int i = 0;
//        while (iterator.hasNext()){
//            Row row = iterator.next();
//            if(StringUtils.isNotBlank(getValue(row, 0))){
//                if(i++ > 0){
//                    UniCheckTable table = buildTable(row);
//                    addType(table.getTypeName());
//                    addItem(table.getTypeName(), table.getItemName());
//                    addTable(table);
//                }
//            }
//        }
//
//        outTableSql();
//    }
//
//    private static UniCheckTable buildTable(Row row){
//        UniCheckTable t = new UniCheckTable();
//
//        t.setTypeName(getValue(row, 0));
//        t.setItemName(getValue(row, 1));
//        t.setContent(getValue(row, 2));
//        t.setConDetail(getValue(row, 3));
//        t.setLawItem(getValue(row, 4));
//
//        return t;
//    }
//
//    private static String getValue(Row row, int col){
//        return row.getCell(col).getStringCellValue();
//    }
//
//    private static void addType(String name){
//        boolean has = types.stream().anyMatch(e -> StringUtils.equals(name, e.getName()));
//        if(has){
//           return ;
//        }
//
//        UniCheckType t = new UniCheckType();
//
//        t.setId(String.format("%03d", typeId.incrementAndGet()));
//        t.setName(name);
//        types.add(t);
//    }
//
//    private static Optional<UniCheckType> getType(String name){
//        return types.stream().filter(e -> StringUtils.equals(name, e.getName())).findFirst();
//    }
//
//    private static void addItem(String typeName, String name){
//        Optional<UniCheckType> typeOpt  = getType(typeName);
//        boolean has = items.stream().anyMatch(e -> StringUtils.equals(name, e.getName()));
//        if(has){
//            return ;
//        }
//
//        if(typeOpt.isEmpty()){
//            throw new RuntimeException("type not exist");
//        }
//
//        UniCheckItem t = new UniCheckItem();
//
//        t.setId(String.format("%03d", itemId.incrementAndGet()));
//        t.setTypeId(typeOpt.get().getId());
//        t.setName(name);
//
//        items.add(t);
//    }
//
//    private static Optional<UniCheckItem> getItem(String name){
//        return items.stream().filter(e -> StringUtils.equals(name, e.getName())).findFirst();
//    }
//
//    private static void addTable(UniCheckTable t){
//        Optional<UniCheckType> typeOpt  = getType(t.getTypeName());
//        Optional<UniCheckItem> itemOpt = getItem(t.getItemName());
//
//        if(typeOpt.isEmpty() || itemOpt.isEmpty()){
//            throw new RuntimeException("type or item not exist");
//        }
//
//        t.setId(String.format("%05d", tableId.incrementAndGet()));
//        t.setTypeId(typeOpt.get().getId());
//        t.setItemId(itemOpt.get().getId());
//
//        tables.add(t);
//    }
//
//    private static void outTypeSql(){
//        System.out.println("INSERT INTO b_check_type (id, name, create_time) VALUES ");
//        for (UniCheckType t : types){
//            String s = String.format("('%s','%s', now()),", t.getId(), t.getName());
//            System.out.println(s);
//        }
//    }
//
//    private static void outItemSql(){
//        System.out.println("INSERT INTO b_check_item (id, type_id, name, create_time) VALUES ");
//        for (UniCheckItem t : items){
//            String s = String.format("('%s','%s', '%s', now()),", t.getId(), t.getTypeId(), t.getName());
//            System.out.println(s);
//        }
//    }
//
//    private static void outTableSql(){
//        System.out.println("INSERT INTO b_check_table (id, type_id, type_name, item_id, item_name, content, con_detail, law_item, create_time) VALUES ");
//        for (UniCheckTable t : tables){
//            String s = String.format("('%s','%s', '%s', '%s', '%s', '%s', '%s', '%s', now()),",
//                    t.getId(), t.getTypeId(), t.getTypeName(), t.getItemId(), t.getItemName(),
//                    t.getContent(), t.getConDetail(), t.getLawItem());
//            System.out.println(s);
//        }
//    }
}
