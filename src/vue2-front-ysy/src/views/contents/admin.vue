<template>
    <v-app>
        <base2 />
    </v-app>
</template>

<script>
// import userGrid from '../../components/admin/ysyGrid.vue'
import base2 from '../../components/admin/adminLayoutBase.vue'
export default {
    name: 'admin-view',
    created(){
        console.log(1234);
    },
    components: {
        base2,
    }
}
</script>

<style>

</style>