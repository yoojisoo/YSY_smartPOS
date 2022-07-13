'use strict';

const YsyUtil = {
	methods: {
		isNull: str => {
			if (str === undefined || str == null) {
				return true;
			} else {
				return false;
			}
		},
		isNullAndEmpty: str => {
			if (str === undefined || str == null || str.trim() === '') {
				return true;
			} else {
				return false;
			}
		},
	},
};

export default YsyUtil;
