import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zgg.mybatisplus.bean.User;
import org.zgg.mybatisplus.mapper.TestMapper;
import org.zgg.mybatisplus.mapper.UserMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Spring整合junit测试
 */

// 在Spring的环境中进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatisPlusJunitTest {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private UserMapper userMapper;

    // 测试spring整合mybatis
    @Test
    public void testMyBatisBySpring(){
        testMapper
                .getAllUser()
                .forEach(System.out::println);
    }

    // 测试加入 mybatis plus
    /*
       加入了MyBatis-Plus后，我们就可以使用MyBatis-Plus所提供的BaseMapper实现CRUD，
       并不需要编写映射文件以及SQL语句，
       但是若要自定义SQL语句，仍然可以编写映射文件而不造成任何影响
     */
    @Test
    public void testMyBatisPlus(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    // 测试BaseMapper的插入功能
    @Test
    public void testInsert(){
        User user = new User();
        user.setUid(7L);
        user.setName("张三");
        user.setAge(11);
        user.setEmail("zhangsan@qq.com");
        // INSERT INTO user ( uid, name, age, email ) VALUES ( ?, ?, ?, ? )
        // 7(Long), 张三(String), 11(Integer), zhangsan@qq.com(String)
        int i = userMapper.insert(user);
        System.out.println(i);
    }

    @Test
    public void testDelete(){
        // DELETE FROM user WHERE id=?
//        int i = userMapper.deleteById(7L);

//        User user = new User();
//        user.setUid(7L);
//        user.setName("张三");
//        user.setAge(11);
//        user.setEmail("zhangsan@qq.com");
//        // DELETE FROM user WHERE uid=?
//        int i = userMapper.deleteById(user);

        // DELETE FROM user WHERE name = ? AND age = ?
//        HashMap<String,Object> hm = new HashMap<>();
//        hm.put("name","张三");
//        hm.put("age",11);
//        int i = userMapper.deleteByMap(hm);

        // DELETE FROM user WHERE uid IN ( ? , ? )
        int i = userMapper.deleteBatchIds(Arrays.asList(6L, 7L));

        System.out.println(i);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setUid(6L);
        user.setName("李四");
        user.setAge(22);
        user.setEmail("lisi@qq.com");
        // UPDATE user SET name=?, age=?, email=? WHERE uid=?
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    @Test
    public void testSelect(){
        // SELECT uid,name,age,email FROM user WHERE uid IN ( ? , ? )
//        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L));

//        HashMap<String,Object> hm = new HashMap<>();
//        hm.put("name","Jone");
//        hm.put("age",18);
//        // SELECT uid,name,age,email FROM user WHERE name = ? AND age = ?
//        List<User> users = userMapper.selectByMap(hm);

        // SELECT uid,name,age,email FROM user
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 定义自定义方法
    @Test
    public void testCustomMethod(){
        User user = userMapper.getUserByName("Jack");
        System.out.println(user);
    }

    // 测试TableLogic注解
    @Test
    public void testTableLogic(){
        // UPDATE t_user SET is_deleted=1 WHERE uid=? AND is_deleted=0
//        int i = userMapper.deleteById(1571848848014368770L);
//        System.out.println(i);

        // SELECT uid,user_name AS name,age,email,is_deleted
        // FROM t_user WHERE uid=? AND is_deleted=0
        User user = userMapper.selectById(1571848848014368770L);
        System.out.println(user);
    }
}
