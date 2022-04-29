import { createRouter, createWebHistory } from 'vue-router'
import v_menus from '@/assets/vMenus.js'
import v_pages from '@/assets/vPages.js'

const routes = v_menus.concat(v_pages)

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router