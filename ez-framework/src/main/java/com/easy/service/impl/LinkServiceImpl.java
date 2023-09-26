package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.constants.SystemConstants;
import com.easy.domain.ResponseResult;
import com.easy.domain.entity.Link;
import com.easy.domain.vo.LinkVo;
import com.easy.domain.vo.PageVo;
import com.easy.mapper.LinkMapper;
import com.easy.service.LinkService;
import com.easy.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author wxx
 * @since 2023-08-01 11:15:21
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    //分页查询友链列表
    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(link.getName()),Link::getName,link.getName());
        lambdaQueryWrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus,link.getStatus());

        Page<Link> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,lambdaQueryWrapper);

        List<Link> links = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setRows(links);
        pageVo.setTotal(page.getTotal());

        return pageVo;
    }
}
