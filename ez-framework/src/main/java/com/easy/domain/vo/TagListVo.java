package com.easy.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxx
 * @create 09--21--23:33
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListVo {
    private Long id;

    //标签名
    private String name;

}