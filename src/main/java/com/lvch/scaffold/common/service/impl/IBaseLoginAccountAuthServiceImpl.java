package com.lvch.scaffold.common.service.impl;

import com.lvch.scaffold.common.dao.BaseLoginAccountAuthDao;
import com.lvch.scaffold.common.service.IBaseLoginAccountAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chunhelv
 * @date 2023-10-13
 * @apiNote
 */
@Service
public class IBaseLoginAccountAuthServiceImpl implements IBaseLoginAccountAuthService {

    @Autowired
    private BaseLoginAccountAuthDao loginAccountAuthDao;

    @Override
    public void findByLoginId(String loginId) {
        loginAccountAuthDao.findCountByLoginId(loginId);
    }


}
