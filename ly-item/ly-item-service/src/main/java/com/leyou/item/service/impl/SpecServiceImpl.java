package com.leyou.item.service.impl;

import com.leyou.item.common.enums.ExceptionEnum;
import com.leyou.item.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.ISpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecServiceImpl implements ISpecService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> querySpecGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> select = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(select)) {
            throw new LyException(ExceptionEnum.SPECGROUP_NOT_FOUND);
        }
        return select;
    }

    @Override
    public List<SpecParam> querySpecParamsByGroupId(Long gid, Long cid, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> select = specParamMapper.select(specParam);
        if (CollectionUtils.isEmpty(select)) {
            throw new LyException(ExceptionEnum.SPECGROUP_NOT_FOUND);
        }
        return select;
    }

    @Override
    @Transactional
    public void updateSpecGroup(SpecGroup specGroup) {
        int count = specGroupMapper.updateByPrimaryKey(specGroup);
        if (count == 0) {
            throw new LyException(ExceptionEnum.UPDATE_SPECGROUP_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void addSpecGroup(SpecGroup specGroup) {
        int count = specGroupMapper.insert(specGroup);
        if (count == 0) {
            throw new LyException(ExceptionEnum.UPDATE_SPECGROUP_SERVER_ERROR);
        }
    }

    @Override
    public void deleteSpecGroupsByGid(Long gid) {
        int count = specGroupMapper.deleteByPrimaryKey(gid);
        if (count == 0) {
            throw new LyException(ExceptionEnum.UPDATE_SPECGROUP_SERVER_ERROR);
        }
    }

    @Override
    public void addSpecParam(SpecParam specParam) {
        int count = specParamMapper.insert(specParam);
        if (count == 0) {
            throw new LyException(ExceptionEnum.UPDATE_SPECGROUP_SERVER_ERROR);
        }
    }

    @Override
    public void updateSpecParam(SpecParam specParam) {
        int count = specParamMapper.updateByPrimaryKey(specParam);
        if (count == 0) {
            throw new LyException(ExceptionEnum.UPDATE_SPECGROUP_SERVER_ERROR);
        }
    }

    @Override
    public void deleteSpecParamByPid(Long pid) {
        int count = specParamMapper.deleteByPrimaryKey(pid);
        if (count == 0) {
            throw new LyException(ExceptionEnum.UPDATE_SPECGROUP_SERVER_ERROR);
        }
    }
}
