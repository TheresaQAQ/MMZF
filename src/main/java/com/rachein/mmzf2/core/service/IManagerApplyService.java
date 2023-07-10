package com.rachein.mmzf2.core.service;

import com.rachein.mmzf2.entity.DB.ManagerApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rachein.mmzf2.entity.RO.ManagerApplyRo;
import com.rachein.mmzf2.entity.RO.ManagerPaddingRo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴远健
 * @since 2023-02-05
 */
public interface IManagerApplyService extends IService<ManagerApply> {

    void add(ManagerApplyRo ro);

    void padding(ManagerPaddingRo ro);
}
