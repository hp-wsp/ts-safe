import com.ts.server.safe.base.domain.UniSupervise;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class SuperviseSql {

    public static void main(String[] args){
        SuperviseSql sql = new SuperviseSql();
    }

    public SuperviseSql(){
        Stack<UniSupervise> stack1 = new Stack<>();
        Stack<UniSupervise> stack2 = new Stack<>();
        List<UniSupervise> list = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        try(InputStream stream = SuperviseSql.class.getResourceAsStream("aa.csv")){
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            AtomicInteger count3 = new AtomicInteger(0);
            bufferedReader.lines().forEach(e -> {
                int index = count.incrementAndGet() -1;
                if(index > 0){
                    String[] array = StringUtils.splitByWholeSeparatorPreserveAllTokens(e, ",");
                    String id = String.format("%05d", index);
                    if(StringUtils.isNotBlank(array[0])){
                        UniSupervise t = build(id, array[0], "root", 1);
                        stack1.push(t);
                    }
                    if(StringUtils.isNotBlank(array[1])){
                        count3.set(0);
                        UniSupervise p = stack1.peek();
                        UniSupervise t = build(id, array[1], p.getId(), 2);
                        stack2.push(t);
                    }
                    if(StringUtils.isNotBlank(array[2])){
                        UniSupervise pp = stack1.peek();
                        UniSupervise p = stack2.peek();
                        UniSupervise t = build(id, array[2], p.getId(), 3);
                        t.setNum(pp.getNum()  + p.getNum() + String.format("%02d", count3.incrementAndGet()));
                        list.add(t);
                    }
                }
            });

            System.out.println("INSERT INTO b_supervise (id, num, name, parent_id, level, create_time) VALUES ");
            stack1.forEach(e -> System.out.println(buildSql(e)));
            stack2.forEach(e -> System.out.println(buildSql(e)));
            list.forEach(e -> System.out.println(buildSql(e)));

        }catch (IOException ex){

        }

    }

    private UniSupervise build(String id, String name, String parentId, int level){
        UniSupervise t = new UniSupervise();

        t.setId(id);
        t.setNum(num(name, level));
        t.setName(StringUtils.trim(name));
        t.setParentId(parentId);
        t.setLevel(level);

        return t;
    }

    private String num(String name, int level){
        if(level == 1){
            return StringUtils.left(name, 1);
        }
        if(level == 2){
            return StringUtils.left(name, 2);
        }
        return "";
    }

    private String buildSql(UniSupervise t){
        return String.format("('%s', '%s', '%s', '%s', %d, now()),", t.getId(), t.getNum(), t.getName(), t.getParentId(), t.getLevel());
    }
}
