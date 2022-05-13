<template>
  <v-app>
    <main-header/>
    <h1>Favorite page</h1>
    <my-info/>
    <main-footer/>
  </v-app>
</template>

<script>
import MainHeader from '@/components/common/TheHeader.vue'
import MainFooter from '@/components/common/TheFooter.vue'
import MyInfo from '@/components/common/TheMyInfo.vue'

export default {
  components: {
    'main-header': MainHeader,
    'main-footer': MainFooter,
    'my-info': MyInfo,
  }
}
</script>