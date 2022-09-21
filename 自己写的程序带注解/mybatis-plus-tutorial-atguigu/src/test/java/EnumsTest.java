import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zgg.mybatisplus.bean.Product;
import org.zgg.mybatisplus.bean.User;
import org.zgg.mybatisplus.enums.SexEnum;
import org.zgg.mybatisplus.mapper.ProductMapper;
import org.zgg.mybatisplus.mapper.UserMapper;

import java.util.List;

// 在Spring的环境中进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class EnumsTest {

    @Autowired
    private UserMapper userMapper;

    // 测试通用枚举
    @Test
    public void testEnums(){
        User user = new User();
        user.setName("Enum");
        user.setAge(20);
        // 设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        user.setSex(SexEnum.MALE);
        userMapper.insert(user);
    }
}
