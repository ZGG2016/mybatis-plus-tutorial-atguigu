import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zgg.mybatisplus.bean.Product;
import org.zgg.mybatisplus.bean.User;
import org.zgg.mybatisplus.mapper.ProductMapper;
import org.zgg.mybatisplus.mapper.UserMapper;
import org.zgg.mybatisplus.service.UserService;

import java.util.List;

// 在Spring的环境中进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 指定Spring的配置文件
@ContextConfiguration("classpath:applicationContext.xml")
public class PluginsTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    // 测试分页插件
    @Test
    public void testPage(){
        //设置分页参数
        Page<User> page = new Page<>(2, 3);
        /*
            SELECT uid,user_name AS name,age,email,is_deleted
            FROM t_user
            WHERE is_deleted=0 LIMIT ?,?
         */
        userMapper.selectPage(page, null);
        //获取分页数据
        List<User> records = page.getRecords();
        records.forEach(System.out::println);

        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());
    }

    // 测试自定义分页
    @Test
    public void testPageCustom(){
        //设置分页参数
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPage(page, null);
        //获取分页数据
        List<User> records = page.getRecords();
        records.forEach(System.out::println);

        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());
    }

    // 测试使用乐观锁前
    @Test
    public void testLock(){
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李：" + p1.getPrice());

        Product p2 = productMapper.selectById(1L);
        System.out.println("小王：" + p2.getPrice());

        p1.setPrice(p1.getPrice()+50);
        int i1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + i1);

        p2.setPrice(p2.getPrice()-30);
        int i2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + i2);

        Product p3 = productMapper.selectById(1L);
        System.out.println("老板：" + p3.getPrice()); // 70
    }

    // 测试使用乐观锁后
    @Test
    public void testLock2(){
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李：" + p1.getPrice());

        Product p2 = productMapper.selectById(1L);
        System.out.println("小王：" + p2.getPrice());

        p1.setPrice(p1.getPrice()+50);
        int i1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + i1);

        p2.setPrice(p2.getPrice()-30);
        int i2 = productMapper.updateById(p2);
        if (i2==0){
            //失败重试，重新获取version并更新
            p2 = productMapper.selectById(1L);
            p2.setPrice(p2.getPrice() - 30);
            i2 = productMapper.updateById(p2);
        }
        System.out.println("小王修改结果：" + i2);

        Product p3 = productMapper.selectById(1L);
        System.out.println("老板：" + p3.getPrice()); // 120
    }
}
