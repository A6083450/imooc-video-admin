package com.imooc.imoocvideoadmin;

import com.imooc.mapper.BgmMapper;
import com.imooc.pojo.Bgm;
import com.imooc.zookeeper.ZKCurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author erpljq
 * @date 2018/10/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BgmMapperTest {

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private ZKCurator zkCurator;

    @Test
    public void selectAll(){
        List<Bgm> bgms = bgmMapper.selectAll();
        System.out.println(bgms.size());
    }

    @Test
    public void zkTest(){
        System.out.println(zkCurator);
    }
}