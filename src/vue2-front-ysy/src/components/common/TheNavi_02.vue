
<template>
  <v-card
    height="700"
    width="300"
  >
    <v-navigation-drawer
      height="700"
      width="300"
      class="deep-purple accent-4"
      dark
      permanent
    >

      <v-list>
      <v-list-group
        v-for="item in items"
        :key="item.title"
        v-model="item.active"
        :prepend-icon="item.action"
        no-action
      >
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
        </template>

        <v-list-item
          v-for="child in item.items"
          :key="child.title"
        >
          <v-list-item-content>
            <v-list-item-title v-text="child.title"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
    </v-list>

      <!-- <v-list>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list> -->

      <template v-slot:append>
        <div class="pa-2">
          <v-btn block>
            Logout
          </v-btn>
        </div>
      </template>
    </v-navigation-drawer>

    <v-btn @click="testAxios">axios test</v-btn>

  </v-card>

</template>

<script>
  export default {
    // created() { 
    //   console.log("Child created") 
    // },
    data: () => ({
        // items: [
        //   { title: 'Dashboard', icon: 'mdi-view-dashboard' },
        //   { title: 'Account', icon: 'mdi-account-box' },
        //   { title: 'Admin', icon: 'mdi-gavel' },
        // ],

        items: [
        {
          action: 'mdi-ticket',
          items: [{ title: 'List Item' }],
          title: 'Attractions',
        },
        {
          action: 'mdi-silverware-fork-knife',
          active: true,
          items: [
            { title: 'Breakfast & brunch' },
            { title: 'New American' },
            { title: 'Sushi' },
          ],
          title: 'Dining',
        },
        {
          action: 'mdi-school',
          items: [{ title: 'List Item' }],
          title: 'Education',
        },
        {
          action: 'mdi-human-male-female-child',
          items: [{ title: 'List Item' }],
          title: 'Family',
        },
        {
          action: 'mdi-bottle-tonic-plus',
          items: [{ title: 'List Item' }],
          title: 'Health',
        },
        {
          action: 'mdi-briefcase',
          items: [{ title: 'List Item' }],
          title: 'Office',
        },
        {
          action: 'mdi-tag',
          items: [{ title: 'List Item' }],
          title: 'Promotions',
        },
      ],

        postValue: "con axios test"
    }),
    methods: {
      testAxios: function() {
        this.$axios.get('/testCtrl/getAxios')
            .then(res => {
              console.log("vue: getAxios = " + res.data)
            });

        this.$axios.post('/testCtrl/postAxios', this.postValue)
            .then(res => {
              console.log("vue:postAxios = " + res.data)
            });
      }
    }
  }
</script>