package com.rachein.mmzf2.core.service.impl;

import com.rachein.mmzf2.entity.DB.Company;
import com.rachein.mmzf2.core.mapper.CompanyMapper;
import com.rachein.mmzf2.core.service.ICompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单位注册 服务实现类
 * </p>
 *
 * @author 吴远健
 * @since 2022-11-26
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

}
