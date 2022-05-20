<template>
    <v-app>
        <v-navigation-drawer
            v-model="drawer"
            app
        >
        </v-navigation-drawer>
    </v-app>
</template>

<script>
export default {
    name: 'left_navi',
     data: () => ({
      drawer: null,
    }),
}
</script>

<style>

</style>