/**	create by clubbboy@naver.com
 * 	공통 service crud..
 *  - default async
 *  - param url , 서버 전송할 json data
 */
import axios from 'axios';
// import fileDownload from "js-file-download";

class CommonService {
	
	
	
	
	/** 조회 공통 */
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
	
	async fn_save(url, params) {
		let result ;
		await axios
			.post(url, params)
			.then(res => {
				result = res;
				// return res.data;
			})
			.catch(error => {
				result = null;
				console.log('CommonService fn_save error', error);
				return { data: 'error' };
			});
			console.log("commonService save result ",result);
			return result;
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

	/** 파일 다운로드시 사용되는 함수
	 * 필수 파라메터.
	 * params {
	 * 		boardId : 1,
	 * 		fileId : 2,
	 * 		fileName : "파일명.jpg"
	 * }
	 */
	async fn_downloadFile(url , params){
		console.log("fn_downloadFile params " , params);
		let paramStr= "";
		let keys = Object.keys(params);
		for (let index = 0; index < keys.length; index++)
		{
			paramStr+= keys[index]+"="+params[keys[index]];
			if(keys.length-1 != index) paramStr+= "&";
		}
		await axios.get(url + "?" +paramStr, {responseType: "blob"})
		           .then(response=>{
						console.log("fn filedown res " , response);
						var fileURL = window.URL.createObjectURL(new Blob([response.data])); 
						var fileLink = document.createElement('a'); 
						fileLink.href = fileURL; 
						fileLink.setAttribute('download', params.fileName); 
						document.body.appendChild(fileLink); 
						fileLink.click(); 
				   })
				   .catch(error=>{
						console.log("File Download Error = ",error);
						alert("File Download Error \n 관리자에게 문의! ");
				   });
	}

	/**
	 * @param {*} url 
	 * @param {*} params  {
	 * 		boardId : 1
	 * }
	 * 아직 안댐 왜 안될까??? 배열로 받을때는 어케 받아야하지??
	 */
	async fn_downloadFiles(url , params){
		console.log("fn_downloadFiles params " , params);
		let paramStr= "";
		let keys = Object.keys(params);
		for (let index = 0; index < keys.length; index++)
		{
			paramStr+= keys[index]+"="+params[keys[index]];
			if(keys.length-1 != index) paramStr+= "&";
		}
		await axios.get(url + "?" +paramStr, {responseType: "blob"})
		           .then(response=>{
						console.log("fn filedown res " , response);

						let array = response.data;
						for (let i = 0; i < array.length; i++) {
							const file = array[i];
							var fileURL = window.URL.createObjectURL(new Blob([file])); 
							var fileLink = document.createElement('a'); 
							fileLink.href = fileURL; 
							fileLink.setAttribute('download', "downloadFile"+i); 
							document.body.appendChild(fileLink); 
							fileLink.click(); 
						}
						
				   })
				   .catch(error=>{
						console.log("File Download Error = ",error);
						alert("File Download Error \n 관리자에게 문의! ");
				   });
	}
	
}

export default new CommonService();
