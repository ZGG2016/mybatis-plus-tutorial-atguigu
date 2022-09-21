import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zgg.mybatisplus.mapper.TestMapper;

/**
 * 通过IOC容器测试
 */
public class MyBatisPlusIOCTest {

    @Test
    public void testMyBatis(){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        TestMapper mapper = context.getBean(TestMapper.class);
        mapper.getAllUser().forEach(System.out::println);
    }
}
