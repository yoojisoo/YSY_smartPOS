<!-- 
      create by clubbboy@naver.com
      lib : https://happy-coding-clans.github.io/vue-easytable/#/en/doc/table/virtual-scroll


 -->
<template>
<v-app>
    <!-- 
        style="width:calc(100vw - 10px);"
        style="width:80%"
        max-height="calc(100vh - 350px)"
        :max-height="300"
        :border-around="true"
        :scroll-width="1200"



        :sort-option="sortOption"
        :columnHiddenOption="colHiddenOption"
        style="border-radius:10px;width:100%"
        :style="{'word-break':wordBreak}"
        :border-around="true"
        :border-x="true"
        :border-y="true"
        
     -->
    <v-btn class="button-demo" @click="show">show loding</v-btn>
    
    <ve-table  :columns="headers" :table-data="dataList" 
        :max-height="300"
        :virtual-scroll-option="virtualScrollOption"
        :fixed-header="true"
        row-key-field-name="col1"
        
    />
</v-app>
    
</template>

<script>
// import { veLoading } from "vue-easytable";
 export default {
    props: [ "dataList","headers","colHiddenOption"],
    components:{
        
    },
    data() {
      return {
        virtualScrollOption: {
                    enable: true,
                },
        // wordBreak: "normal",
        // wordBreak: "keep-all",
        wordBreak: "break-all",
        // wordBreak: "break-word",
          loadingInstance: null,
        // columns: [
        //   { field: "col1", key:"a", title: "Col1", align: "center" },
        //   { field: "col2", key:"b", title: "Col2", align: "left" },
        //   { field: "col3", key:"c", title: "Col3", align: "right" },
        //   { field: "col4", key:"d", title: "Col4" },
        //   { field: "col5", key:"e", title: "Col5" },
        //   { field: "col6", key:"f", title: "Col6" },
        // ],
      
        
        sortOption: {
          // sort always
          sortAlways: true,
          multipleSort: true,
          sortChange: (params) => {
            console.log("sortChange::", params);
            this.sortChange(params);
          },
        },
        
      };
    },
    methods:{
      show() {
        this.loadingInstance.show();

        setTimeout(() => {
          this.loadingInstance.close();
        }, 2000);
      },

      sortChange(params) {
        console.log("params.col1 = ");
        console.log(params);
        // this.dataList.sort((a,b)=>{
        //   if (a.col1 > b.col1) {
        //     return 1;
        //   }
        //   else if (a.col1 < b.col1) {
        //     return -1;
        //   }
        //   else return 0;
        // });
        // this.dataList.forEach((x,idx) => {
        //   console.log(idx + " = " + x.col1);
        // });
        // this.dataList.sort((a, b) => {
        //    console.log(a);
        //   if (params.col1) {
        //     if (params.col1 === "asc") {
        //       return a.col1 - b.col1;
        //     } else if (params.col1 === "desc") {
        //       return b.col1 - a.col1;
        //     } else {
        //       return 0;
        //     }
        //   } 
        // });
      },
     
    },
    mounted() {
        // this.loadingInstance = this.$veLoading({
        //     fullscreen: true,
        //     name: "bounce",
        //     lock: true,
        // });
    },
    destroyed() {
        this.loadingInstance.destroy();
    },
};
</script>

<style>

</style>