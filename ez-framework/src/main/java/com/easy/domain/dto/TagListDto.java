package com.easy.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxx
 * @create 09--19--16:43
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    //标签名
    private String name;
    //备注
    private String remark;
}
