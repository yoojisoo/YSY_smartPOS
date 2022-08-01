/**	create by clubbboy@naver.com
 * 	공통 service crud..
 *  - default async
 *  - param url , 서버 전송할 json data
 */
import axios from 'axios';

class CommonService {
	async fn_save(url, params) {
		await axios
			.post(url, params)
			.then(res => {
				return res.data;
			})
			.catch(error => {
				console.log('CommonService fn_save error', error);
				return { data: 'error' };
			});
	}

	async fn_saveMultipart(url, params) {
		await axios
			.post(url, params)
			.then(res => {})
			.catch(error => {
				console.log('CommonService fn_multipartSave error', error);
			});
	}

	async fn_modifyData(url, params) {
		await axios
			.post(url, params)
			.then(res => {
				return res;
			})
			.catch(error => {
				console.log('CommonService fn_ModifyData error', error);
			});
	}
	async fn_modifyMultipartData(url, params) {
		await axios
			.post(url, params)
			.then(res => {
				return res;
			})
			.catch(error => {
				console.log('CommonService fn_ModifyData error', error);
			});
	}

	async fn_deleteData(url, params) {
		await axios
			.post(url, params)
			.then(res => {
				return res;
			})
			.catch(error => {
				console.log('CommonService fn_deleteData error', error);
			});
	}

	async fn_getDataList(url, params) {
		console.log('url    ', url);
		console.log('params ', params);
		let result;
		await axios
			.get(url, params)
			.then(res => {
				console.log('CommonService success fn_getDataList', res);
				result = res;
				// return res;
			})
			.catch(error => {
				result = null;
				console.log('CommonService fn_getDataList error', error);
			});
		console.log('result', result);
		return result;
	}
	async fn_getData(url, params) {
		await axios
			.get(url, params)
			.then(res => {
				return res;
			})
			.catch(error => {
				console.log('CommonService fn_getData error', error);
			});
	}
}

export default new CommonService();
