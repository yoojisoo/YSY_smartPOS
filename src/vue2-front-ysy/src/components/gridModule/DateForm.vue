<!--  날짜 포맷 변환 -->
<template>
  <span v-if="dateVal">
    {{ chgDate }}
  </span>
</template>

<script>
  export default {
    props: ["dateVal" , "gubun"],
    computed:{
      chgDate(){
        let userDate =  this.dateVal.getFullYear() 
                      + this.chgGubun
                      + ((this.dateVal.getMonth() + 1) > 9 ? this.dateVal.getMonth() + 1 : "0" + (this.dateVal.getMonth() + 1))
                      + this.chgGubun
                      + ((this.dateVal.getDate() + 1)  > 9 ? this.dateVal.getDate()  + 1 : "0" + (this.dateVal.getDate()  + 1));
         return userDate;
      },
      chgGubun(){
        if(this.gubun == undefined ||this.gubun == undefined ||this.gubun == "") return "-";
        else return this.gubun;
      }
    }
  }

</script>

<style lang="scss" scoped>

</style>