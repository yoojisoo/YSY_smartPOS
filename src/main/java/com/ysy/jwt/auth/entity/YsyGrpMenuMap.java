package com.ysy.jwt.auth.entity;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;

public class YsyGrpMenuMap {

	
	
	@OneToMany
	@JoinColumns({@JoinColumn(name = "BIZ_CD" , referencedColumnName = ""),
		          @JoinColumn(name = "GRP_ID" , referencedColumnName = "")})
	private YsyGrpMst ysyGrpMst ;
	
	
}
