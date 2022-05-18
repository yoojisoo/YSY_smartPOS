<template>
  <v-container>
      <userGrid/>
  </v-container>
</template>

<script>
import userGrid from '../../components/common/ysyGrid.vue'
export default {
    name: 'admin-view',
    created(){
        console.log(1234);
    },
    components: {
        userGrid
    }
}
</script>

<style>

</style>