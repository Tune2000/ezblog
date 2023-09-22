package com.easy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.domain.ResponseResult;
import com.easy.domain.dto.TagListDto;
import com.easy.domain.entity.LoginUser;
import com.easy.domain.entity.Tag;
import com.easy.domain.vo.PageVo;
import com.easy.domain.vo.TagListVo;
import com.easy.domain.vo.TagVo;
import com.easy.mapper.TagMapper;
import com.easy.service.TagService;
import com.easy.utils.BeanCopyUtils;
import com.easy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author wxx
 * @since 2023-09-05 10:28:11
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Override
    //查询标签
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        // Page<Tag> tagList = page(page, queryWrapper);
        page(page,queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    //新增标签
    public ResponseResult addTag(TagListDto tagListDto) {
        //新增标签没有更新人，更新时间，仅有创建人，创建时间
        //新增数据转换为Tag数据
        Tag tag = new Tag();
        //获得[创建人，创建时间]
        LoginUser loginUser = SecurityUtils.getLoginUser();
        tag.setCreateBy(loginUser.getUser().getId());

        //创建，更新时间
        try {
            //创建SimpleDateFormat对象，指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前日期格式
            Date now = new Date();
            //将当前日期转化为指定日期格式
            String strNow = sdf.format(now);
            //将字符串转换成Date类型
            Date date = sdf.parse(strNow);
            tag.setCreateTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //标签，描述修改
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        //添加到数据库
        tagMapper.insert(tag);
        return ResponseResult.okResult();
    }

    @Override
    //删除标签
    public ResponseResult deleteTag(Long id) {
        //通过数据id查找数据
        Tag tag = tagMapper.selectById(id);
        //把值传入数据库中进行更新
        if(tag != null) {
            int flag = 1;
            tagMapper.myTagUpdateById(id);
        }
        return ResponseResult.okResult();
    }

    @Override
    //根据标签id获取标签数据
    public ResponseResult getLableById(Long id) {
        Tag tag = tagMapper.selectById(id);
        //封装成vo响应给前端
        TagVo tagVoData = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVoData);
    }

    @Override
    //修改标签
    public ResponseResult updateById(TagVo tagVo) {
        //修改数据转换为Tag类型
        Tag tag = new Tag();
        //获得[创建人，创建时间]
        LoginUser loginUser = SecurityUtils.getLoginUser();
        tag.setCreateBy(loginUser.getUser().getId());

        //创建，更新时间
        try {
            //创建SimpleDateFormat对象，指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前日期格式
            Date now = new Date();
            //将当前日期转化为指定日期格式
            String strNow = sdf.format(now);
            //将字符串转换成Date类型
            Date date = sdf.parse(strNow);
            //时间修改
            tag.setUpdateTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //标签，描述修改
        tag.setId(tagVo.getId());
        tag.setName(tagVo.getName());
        tag.setRemark(tagVo.getRemark());

        //更新到数据库
        tagMapper.updateById(tag);

        return ResponseResult.okResult();
    }

    @Override
    //获取所有标签
    public List<TagListVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagListVo> tagVos = BeanCopyUtils.copyBeanList(list, TagListVo.class);
        return tagVos;
    }


}
