import com.ts.server.safe.base.domain.UniRiskChemical;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Stack;

public class UniRiskChemicalSql {

//    public static void main(String[] args)throws IOException {
//        InputStream stream = UniRiskChemicalSql.class.getResourceAsStream("whp.xlsx");
//        Workbook workbook = WorkbookFactory.create(stream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> iterator = sheet.rowIterator();
//        Stack<UniRiskChemical> all = new Stack<>();
//
//        int i = 0;
//        for(;iterator.hasNext();){
//            Row row = iterator.next();
//            if(i++ > 0){
//                all.push(build(i, all, row));
//            }
//        }
//
//        printSql(all);
//    }

    private static UniRiskChemical build(int index, Stack<UniRiskChemical> all, Row row){
        UniRiskChemical t = new UniRiskChemical();

        String name = getCellValue(row.getCell(1));
        String alis = getCellValue(row.getCell(2));
        String cas = getCellValue(row.getCell(3));
        String remark = getCellValue(row.getCell(4));
        if(StringUtils.isBlank(cas)){
            UniRiskChemical o = all.peek();
            cas = o.getCas();
        }
        if(StringUtils.isBlank(name)){
            UniRiskChemical o = all.peek();
            name = o.getName();
            alis = o.getAlias();
        }

        t.setId(String.format("%05d", index - 1));
        t.setName(name);
        t.setAlias(alis);
        t.setCas(StringUtils.remove(cas, "；"));
        t.setRemark(remark);

        return t;
    }

    private static String getCellValue(Cell cell){
        String v;
        if(cell.getCellType() == CellType.NUMERIC){
            v = String.valueOf(cell.getNumericCellValue());
        }else{
            v = cell.getStringCellValue();
        }
        if(v == null){
            return "";
        }
        return StringUtils.replace(StringUtils.trim(v), "'", "''");
    }

    private static void printSql(Stack<UniRiskChemical> all){
        System.out.println("INSERT INTO b_risk_chemistry (id, name, alias, cas, remark, create_time) VALUES ");
        for (UniRiskChemical t : all){
            String s = String.format("('%s','%s', '%s', '%s', '%s', now()),",
                    t.getId(), t.getName(), t.getAlias(), t.getCas(), t.getRemark());
            System.out.println(s);
        }
    }
}
