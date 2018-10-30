package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.BGMOperatorTypeEnum;
import com.imooc.mapper.BgmMapper;
import com.imooc.mapper.UsersReportMapperCustom;
import com.imooc.mapper.VideosMapper;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.Reports;
import com.imooc.service.VideoService;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.PagedResult;
import com.imooc.zookeeper.ZKCurator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author erpljq
 * @date 2018/10/9
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private UsersReportMapperCustom usersReportMapperCustom;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private ZKCurator zkCurator;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addBgm(Bgm bgm) {
        String bgmId = sid.nextShort();
        bgm.setId(bgmId);
        bgmMapper.insert(bgm);

        Map<String, String> map = new HashMap<>();
        map.put("operType", BGMOperatorTypeEnum.ADD.type);
        map.put("path", bgm.getPath());

        zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Example example = new Example(Bgm.class);
        List<Bgm> list = bgmMapper.selectByExample(example);
        PageInfo<Bgm> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pagedResult.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteBgm(String id) {
        Bgm bgm = bgmMapper.selectByPrimaryKey(id);
        bgmMapper.deleteByPrimaryKey(id);

        Map<String, String> map = new HashMap<>();
        map.put("operType", BGMOperatorTypeEnum.DELETE.type);
        map.put("path", bgm.getPath());

        zkCurator.sendBgmOperator(id, JsonUtils.objectToJson(map));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm selectOne(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();
        PageInfo<Reports> pageList = new PageInfo<>(reportsList);
        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(reportsList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    @Override
    public void updateVideoStatus(String videoId, Integer status) {
        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setStatus(status);
        videosMapper.updateByPrimaryKeySelective(videos);
    }
}
