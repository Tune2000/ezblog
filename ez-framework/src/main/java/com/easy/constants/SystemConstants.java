package com.easy.constants;

/**
 * 在代码中不允许直接使用字面值。都需要定义成常量来使用。
 */
public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 文章列表当前查询页数
     */
    public static final int ARTICLE_STATUS_CURRENT = 1;

    /**
     * 文章列表每页显示的数据条数
     */
    public static final int ARTICLE_STATUS_SIZE = 10;

    /**
     *  文章分类是正常状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链状态为审核通过
     */
    public static final Object LINK_STATUS_NORMAL = 0;
    /**
     * 评论区的某条评论是根评论
     */
    public static final String COMMENT_ROOT = "-1";
    /**
     *  评论类型：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     *  评论类型：友链评论
     */
    public static final String LINK_COMMENT = "1";

    public static final String MENU = "C";
    public static final String BUTTON = "F";
    /** 正常状态 */
    public static final String NORMAL = "0";
    /**
     *  后台用户类型
     */
    public static final String ADMAIN = "1";
}