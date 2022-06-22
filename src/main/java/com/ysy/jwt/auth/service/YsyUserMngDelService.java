package com.ysy.jwt.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysy.common.SysEnum;
import com.ysy.common.SysEnum.enumGrps;
import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.dto.ModUserDto;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@Service
public class YsyUserMngDelService {
	@Autowired
	private YsyUserMstRepository ysyUserRepository;

	/** grid에서 user 삭제 */
	public void delGridUserInfo(List<String> usernameList) {
		for(String username : usernameList) {
			ysyUserRepository.deleteById(username);
		}
	}
}
