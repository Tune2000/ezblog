package com.easy.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxx
 * @create 07--28--10:06
 * @description:
 * 直接查询会返回很多冗余数据，hotArticleVo只返回热门文章数据里面的id，title，viewCount数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
