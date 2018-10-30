package com.imooc.mapper;

import com.imooc.pojo.UsersReport;
import com.imooc.pojo.vo.Reports;
import com.imooc.utils.MyMapper;

import java.util.List;

public interface UsersReportMapperCustom extends MyMapper<UsersReport> {

    List<Reports> selectAllVideoReport();
}