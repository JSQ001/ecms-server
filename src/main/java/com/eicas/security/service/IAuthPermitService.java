package com.eicas.security.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.security.entity.AuthPermit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.security.pojo.param.PermitQueryParam;

import java.util.List;

/**
 * 权限信息服务类
 *
 * @author osnudt
 * @since 2022-04-23
 */
public interface IAuthPermitService extends IService<AuthPermit> {

    Page<AuthPermit> listPermits(PermitQueryParam param, Integer current, Integer size);

    Page<AuthPermit> listAll();

    List<AuthPermit> listPermitsByRequestPath(String requestUrl);
}
