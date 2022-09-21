import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zgg.mybatisplus.bean.User;
import org.zgg.mybatisplus.mapper.UserMapper;

import java.util.List;
import java.util.Map;

// 在Spring的环境中进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class WapperTest {
    @Autowired
    private UserMapper userMapper;

    // 组装查询条件
    @Test
    public void test01(){
        // 查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        /*
        SELECT uid,user_name AS name,age,email,is_deleted
        FROM t_user
        WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
         */
        queryWrapper.like("user_name","a")
                .between("age",20,30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // 组装排序条件
    @Test
    public void test02(){
        // 按年龄降序查询用户，如果年龄相同则按id升序排列
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        /*
           SELECT uid,user_name AS name,age,email,is_deleted
           FROM t_user
           WHERE is_deleted=0 ORDER BY age DESC,uid ASC
         */
        queryWrapper.orderByDesc("age").orderByAsc("uid");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    // 组装删除条件
    @Test
    public void test03(){
        // 删除email为空的用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        // UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        queryWrapper.isNull("email");
        int i = userMapper.delete(queryWrapper);
        System.out.println(i);
    }

    // QueryWrapper修改功能
    @Test
    public void test04() {
        // 将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        /*
            UPDATE t_user SET age=?, email=?
            WHERE is_deleted=0 AND (age >= ? AND user_name LIKE ? OR email IS NULL)
         */
        queryWrapper.ge("age",20)
                .like("user_name","a")
                .or()
                .isNull("email");
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        int i = userMapper.update(user, queryWrapper);
        System.out.println(i);
    }

    // 条件的优先级
    @Test
    public void test05() {
        // 将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        //lambda中的条件优先执行
        /*
        UPDATE t_user SET age=?, email=?
        WHERE is_deleted=0 AND (user_name LIKE ? AND (age >= ? OR email IS NULL))
         */
        queryWrapper.like("user_name","a")
                    .and(i->i.ge("age",20).or().isNull("email"));

        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");
        int i = userMapper.update(user, queryWrapper);
        System.out.println(i);
    }

    // 组装select子句
    @Test
    public void test06() {
        // 查询用户信息的username和age字段
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        // SELECT user_name,age FROM t_user WHERE is_deleted=0
        queryWrapper.select("user_name","age");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    // 实现子查询
    @Test
    public void test07() {
        // 查询id小于等于3的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        /*
        SELECT uid,user_name AS name,age,email,is_deleted
        FROM t_user
        WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid<=3))
         */
        queryWrapper.inSql("uid","select uid from t_user where uid<=3");
        // selectObjs的使用场景：只返回一列
        List<Object> objs = userMapper.selectObjs(queryWrapper);
        objs.forEach(System.out::println);
    }

    // 测试UpdateWrapper
    @Test
    public void test08() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        /*
           UPDATE t_user SET user_name=?, age=?,email=?
           WHERE is_deleted=0 AND (user_name LIKE ? AND (age >= ? OR email IS NULL))
         */
        updateWrapper.set("age", 18)
                    .set("email", "user@atguigu.com")
                    .like("user_name","a")
                    .and(i->i.ge("age",20).or().isNull("email"));
//        User user = new User();
//        user.setName("张三");
        // 这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
//        int i = userMapper.update(user, updateWrapper);
        int i = userMapper.update(null, updateWrapper);
        System.out.println(i);
    }

    // condition测试
    @Test
    public void test09() {
        // 使用带condition参数的重载方法构建查询条件
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        /*
        SELECT uid,user_name AS name,age,email,is_deleted
        FROM t_user
        WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
         */
        queryWrapper.like(StringUtils.isNotBlank(username), "user_name", username)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    // LambdaQueryWrapper测试
    @Test
    public void test10() {
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    // LambdaUpdateWrapper测试
    @Test
    public void test11() {
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName, "a")
                .and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail));
        updateWrapper.set(User::getName, "小黑").set(User::getEmail,"abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result："+result);
    }
}
