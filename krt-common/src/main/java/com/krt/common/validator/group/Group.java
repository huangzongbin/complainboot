package com.krt.common.validator.group;

import javax.validation.GroupSequence;


/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@GroupSequence({InsertGroup.class, UpdateGroup.class})
public interface Group {

}
