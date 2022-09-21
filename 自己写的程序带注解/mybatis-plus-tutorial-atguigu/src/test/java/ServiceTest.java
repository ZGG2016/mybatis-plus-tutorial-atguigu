import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zgg.mybatisplus.bean.User;
import org.zgg.mybatisplus.service.UserService;

import java.util.ArrayList;
import java.util.List;

// 在Spring的环境中进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class ServiceTest {

    @Autowired
    private UserService userService;

    // 测试IService的统计功能
    @Test
    public void testCount(){
        // SELECT COUNT( * ) FROM user
        long count = userService.count();
        System.out.println("总记录数：" + count);
    }

    // 测试IService的批量添加功能
    @Test
    public void testSaveBatch(){
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setName("abc"+i);
            user.setAge(20+i);
            list.add(user);
        }
        // INSERT INTO user ( uid, name, age ) VALUES ( ?, ?, ? )
        boolean b = userService.saveBatch(list);
        System.out.println(b);
    }
}
