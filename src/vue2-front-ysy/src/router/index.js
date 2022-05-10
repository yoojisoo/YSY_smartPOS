import Vue from "vue";
import VueRouter from "vue-router";
import v_menus from "../assets/common/vMenus.js";
import v_pages from "../assets/common/vPages.js";

Vue.use(VueRouter);

const routes = v_menus.concat(v_pages);

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
