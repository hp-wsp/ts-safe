import com.ts.server.safe.base.domain.UniIndCtg;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class SuperviseSql {

//    public static void main(String[] args){
//        SuperviseSql sql = new SuperviseSql();
//    }

    public SuperviseSql(){
        Stack<UniIndCtg> stack1 = new Stack<>();
        Stack<UniIndCtg> stack2 = new Stack<>();
        List<UniIndCtg> list = new ArrayList<>();
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
                        String v = StringUtils.trim(array[0]);
                        String num = num(v, 1);
                        String name = StringUtils.substring(v, 1);
                        UniIndCtg t = build(id, num, name, name, "root", 1);
                        stack1.push(t);
                    }
                    if(StringUtils.isNotBlank(array[1])){
                        count3.set(0);
                        UniIndCtg p = stack1.peek();
                        String v = StringUtils.trim(array[1]);
                        String num = num(v, 2);
                        String name = StringUtils.substring(v, 2);
                        UniIndCtg t = build(id, p.getNum() + num, name, p.getFullName() + "/" + name, p.getId(), 2);
                        stack2.push(t);
                    }
                    if(StringUtils.isNotBlank(array[2])){
                        UniIndCtg pp = stack1.peek();
                        UniIndCtg p = stack2.peek();
                        String name = StringUtils.trim(array[2]);
                        UniIndCtg t = build(id, "", name, p.getFullName() + "/" + name, p.getId(), 3);
                        t.setNum(p.getNum() + String.format("%02d", count3.incrementAndGet()));
                        list.add(t);
                    }
                }
            });

            System.out.println("INSERT INTO b_ind_ctg (id, num, name, full_name, parent_id, level, create_time) VALUES ");
            stack1.forEach(e -> System.out.println(buildSql(e)));
            stack2.forEach(e -> System.out.println(buildSql(e)));
            list.forEach(e -> System.out.println(buildSql(e)));

        }catch (IOException ex){

        }

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

    private UniIndCtg build(String id, String num, String name, String fullName, String parentId, int level){
        UniIndCtg t = new UniIndCtg();

        t.setId(id);
        t.setNum(num);
        t.setName(StringUtils.trim(name));
        t.setFullName(StringUtils.trim(fullName));
        t.setParentId(parentId);
        t.setLevel(level);

        return t;
    }

    private String buildSql(UniIndCtg t){
        return String.format("('%s', '%s', '%s', '%s', '%s', %d, now()),", t.getId(), t.getNum(), t.getName(), t.getFullName(), t.getParentId(), t.getLevel());
    }
}
