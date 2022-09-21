import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zgg.mybatisx.bean.User;
import org.zgg.mybatisx.mapper.UserMapper;

// 在Spring的环境中进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class TestMybatisx {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        User user = new User();
        user.setId(7L);
        user.setName("张三");
        user.setAge(11);
        user.setEmail("zhangsan@qq.com");
        int i = userMapper.insertSelective(user);
        System.out.println(i);
    }

}
