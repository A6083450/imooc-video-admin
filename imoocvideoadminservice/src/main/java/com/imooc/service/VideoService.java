package com.imooc.service;

import com.imooc.pojo.Bgm;
import com.imooc.utils.PagedResult;

/**
 * @author erpljq
 * @date 2018/10/9
 */
public interface VideoService {

    public void addBgm(Bgm bgm);

    public PagedResult queryBgmList(Integer page, Integer pageSize);

    public void deleteBgm(String id);

    public Bgm selectOne(String bgmId);

    PagedResult queryReportList(Integer page, Integer pageSize);

    void updateVideoStatus(String videoId, Integer status);
}
