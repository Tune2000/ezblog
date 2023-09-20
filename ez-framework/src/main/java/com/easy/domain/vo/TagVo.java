package com.easy.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxx
 * @create 09--20--23:03
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;
    //标签名
    private String name;
    //备注
    private String remark;
}
