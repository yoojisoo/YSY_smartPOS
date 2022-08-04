<template>
        <v-text-field :label="label"  :prepend-icon="icon" v-model="data"></v-text-field>
</template>

<script>
/***
 * props -> fieldInfo
 *  - label :String
 *  - icon  :String   ex)mdi-mailbox-up
 *  - data  :String
 */
export default {
    props: ['fieldInfo'],
    data() {
        return {
          
        }
    },
    methods: {
    },
    mounted() {
        console.log("this.fieldInfo ------->");
        if(this.fieldInfo.label == undefined || this.fieldInfo.label.trim() === ""){
            this.fieldInfo.label = "우편번호";//default
        }
        if(this.fieldInfo.icon == undefined || this.fieldInfo.icon.trim() === ""){
            this.fieldInfo.icon = "mdi-mailbox-up";//default
        }
        if(this.fieldInfo.data == undefined || this.fieldInfo.data.trim() === ""){
            this.fieldInfo.data = "";//default
        }
        console.log(this.fieldInfo);
    }
}
</script>

<style>

</style>