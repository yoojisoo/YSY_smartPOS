'use strict';

class YsyUtil {
	constructor() {
		this.accessToken = 'authorization'; //server에서 받는 access token key name
		this.isDebugger = true;
	}
	isNull(str) {
		if (str === undefined || str == null) {
			return true;
		} else {
			return false;
		}
	}
	isNullAndEmpty(str) {
		if (str === undefined || str == null || str.trim() === '') {
			return true;
		} else {
			return false;
		}
	}

	/** access token 셋팅 */
	setAccessToken(axios, accessToken) {
		axios.defaults.headers.common['Authorization'] = accessToken;
	}

	removeAccessToken(axios) {
		axios.defaults.headers.common['Authorization'] = '';
	}

	log(str, obj) {
		if (this.isDebugger) {
			if (obj) {
				console.log(str, obj);
			} else {
				console.log(str);
			}
		}
	}
}
//const YsyUtil = {
//	accessToken: 'authorization',
//	methods: {
//		isNull: str => {
//			if (str === undefined || str == null) {
//				return true;
//			} else {
//				return false;
//			}
//		},
//		isNullAndEmpty: str => {
//			if (str === undefined || str == null || str.trim() === '') {
//				return true;
//			} else {
//				return false;
//			}
//		},

//		/** access token 셋팅 */
//		setAccessToken(accessToken, axios) {
//			axios.defaults.headers.common['Authorization'] = accessToken;
//		},
//	},
//};

export default new YsyUtil();
