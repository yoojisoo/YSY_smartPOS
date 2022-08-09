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

	/** form 생성 - 파일 저장 및 수정을 위한 form 생성
	 * @param {*} jsonObj  -> 기타 정보 
	 * @param {*} files  -> upload files
	 */
	createFormData( obj , files ){

		let formData = new FormData();
		// form data 생성

		let jsonObj = this.jsonRecursion(obj);
		

		let keys = Object.keys(jsonObj);

		//obj -> form 
		keys.forEach(key=>{
			// console.log("key =" , key);
			let data = jsonObj[key] == undefined || jsonObj[key] == null || jsonObj[key] == "null" || jsonObj[key] == ""? "" :  jsonObj[key];

			if(Array.isArray(data)){
				let idx = 0;
				data.forEach(x => {
					// formData.append(key, new Blob ([JSON.stringify(x)]) , {type:"application/json"});
					// formData.append(key+""+, x);
					const keys2 = Object.keys(x);
					keys2.forEach(key2=>{
						// ******************************************* 중요 포인트 ********************************************
						// ******************************************* 3시간 해맷다 ********************************************
						// DTO 매핑시 DTO안에 List를 formData로 넣을때는 해당 방법으로 해야 Spring에서 type이 맞음.
						formData.append(key +"["+idx+"]."+key2, x[key2]); 
					});
					idx++;
				});
			}else{
				if(data != "") formData.append(key, jsonObj[key]);
			}
		})
		// file -> form
		if(files !== undefined){
			files.forEach(x => {
				formData.append('files', x);
			});
		}
		for (var pair of formData.entries()) {
			console.log(pair[0] ," = ");
			console.log(pair[1]);
		  }

		return formData;
	}

	jsonRecursion(obj , orgKey , result , addType){
		
		

		if(result == undefined || result == null || result == "") result = {};



		if(typeof obj  != "object")
		{
			if(orgKey != undefined || orgKey != null || orgKey != ""){
				result[orgKey] = obj;
				return result;
			}
			else return result;
		}
		// else if(typeof obj  == "object" && Array.isArray(obj))
		// {
		// 	if(orgKey == undefined || orgKey == null || orgKey == "") result = [];
		// 	else                                                      result[orgKey] = [];

		// 	obj.forEach(x=>{
		// 		jsonRecursion(x , orgKey , result , "array");
		// 	});
		// }
		else{
			const keys = Object.keys(obj);

			keys.forEach(key=>{
				let data = obj[key] == undefined || obj[key] == null || obj[key] == "null" || obj[key] == ""? "" :  obj[key];
			
				if(Array.isArray(data))
				{
					result[key] = [];
					data.forEach(x=>{
						const pushData = this.jsonRecursion(x , key , null );
						result[key].push(pushData);
					});
				}
				else if(typeof data  == "object")
				{
					const addData = this.jsonRecursion(data , key , null );
					result[key] = addData;
				}
				else{
					if(data != "") result[key] = data;
				}
			});
			return result;
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
