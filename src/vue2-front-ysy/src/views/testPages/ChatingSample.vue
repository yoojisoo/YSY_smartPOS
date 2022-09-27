<!-- create by clubbboy@naver.com
    websocket 을 이용한 쳇팅 client 
 -->
<template>
    <div>

        <input type="button" :value="btnTxt" @click="socketConnect" />   
        서버에서 전송된 데이터 : {{serverData}}     
         <div>
            <input type="button" value="respApi" @click="respApi" />  
            respApiData :  {{respApiData}}
         </div>
          <div>
            <input type="button" value="disconnect" @click="disconnect" />  
            respApiData :  {{respApiData}}
         </div>

    </div>
</template>

<script setup>
import {ref , onMounted , onUpdated } from "vue"
import {  CommonService } from '@/assets/import/index.js';

const btnTxt = ref("submit")

const address = "ws://localhost:8000/webSocketTest";
const logs = ref([])
const serverData = ref("")
const disabled = ref(false)
let socket = null;
let respApiData = ref("");


const btnClick = ()=>{
    console.log("btnClickbtnClickbtnClickbtnClickbtnClick, " , btnTxt);
    if(btnTxt.value === "submit"){
        btnTxt.value = "canel";
    }else{
        btnTxt.value = "submit";
    }
}

const  respApi = async ()=>{
    console.log("respApi serverData.value = ",serverData.value);
     const data = await CommonService.fn_getDataList('/ysy/v1/getTest1?msg=12345');
     console.log("data =@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " , data);
     respApiData.value = data.data
}

const disconnect = ()=>{
    console.log("socket ",socket);
    
    //  socket.send("12345");
    socket.close();
    socket = null;
}

const socketConnect = ()=>{
    if(socket != null) return;


    socket = new WebSocket(address);

    socket.onopen = () => {
        console.log("socket open####################");
        logs.value.push({ type: 'INFO', msg: 'CONNECTED' })
        disabled.value = false
    }
    socket.onerror = (error) => {
        console.log("socket error####################",error);
        logs.value.push({ type: 'ERROR', msg: 'ERROR:' })
    }
    socket.onmessage = ({ data }) => {
        serverData.value = data;
        console.log("socket msg recive####################",data);
        logs.value.push({ type: 'RECV', msg: 'RECV:' + data })
    }
    socket.onclose = (event) => {
        console.log("socket close####################",event);
        if (event.wasClean) {
            alert(`[close] 커넥션이 정상적으로 종료되었습니다(code=${event.code} reason=${event.reason})`);
        } else {
            // 예시: 프로세스가 죽거나 네트워크에 장애가 있는 경우
            // event.code가 1006이 됩니다.
            alert('[close] 커넥션이 죽었습니다.');
        }
        // logs.value.push({ type: 'ERROR', msg: 'Closed (Code: ' + msg.code + ', Message: ' + msg.reason + ')' })
    }
}


        

onMounted(() => {
    socketConnect();
 
  console.log("####################### on mounted");
})
onUpdated(()=>{
    console.log("onUpdated $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" , btnTxt.value);
})





// export default {
//     name: 'WebSocket',
//     data(){
//         return {

//             address: 'ws://localhost:8000',
//             logs: [],
//             disabled: true,
//             message: '',
//             selected: 'plain',
//             json: {
//                 key: 'value'
//             }
//         }
//     },
//     created(){
//         this.connect();
//     },
//     methods:{
//       connect(){
//         console.log("window.location.protocol = ",window.location.protocol);
//         this.socket = new WebSocket(this.address);
//         console.log("this.socket = ",this.socket);

//         this.socket.onopen = () => {
//             console.log("socket open####################");
//           this.logs.push({ type: 'INFO', msg: 'CONNECTED' })
//           this.disabled = false
//         }
//         this.socket.onerror = (error) => {
//             console.log("socket error####################",error);
//           this.logs.push({ type: 'ERROR', msg: 'ERROR:' })
//         }
//         this.socket.onmessage = ({ data }) => {
//             console.log("socket msg recive####################");
//           this.logs.push({ type: 'RECV', msg: 'RECV:' + data })
//         }
//         this.socket.onclose = (msg) => {
//             console.log("socket close####################",msg);
//           this.logs.push({ type: 'ERROR', msg: 'Closed (Code: ' + msg.code + ', Message: ' + msg.reason + ')' })
//         }
//       }  
//     },
// }
</script>

<style>

</style>