<template>
  <!-- <header> -->
    <v-navigation-drawer
      v-model="drawer"
      app
    >
    </v-navigation-drawer>
  <!-- </header> -->
</template>

<script>
  export default {
    name: 'admin-navi',
    props: ["drawer"],
    data: () => ({
    }),
  }
</script>