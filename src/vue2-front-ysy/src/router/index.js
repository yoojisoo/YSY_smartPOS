import Vue from 'vue';
import VueRouter from 'vue-router';
import AuthRouter from './routes/AuthRouter';
import CommonRouter from './routes/CommonRouter';
import SignRouter from './routes/SignRouter';
import ErrorRouter from './routes/ErrorRouter';
import TestPageRouter from './routes/TestPageRouter'; // add by clubbboy@naver.com

Vue.use(VueRouter);


const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes: [...AuthRouter, ...CommonRouter, ...SignRouter, ...TestPageRouter
		   , ...ErrorRouter],
});

export default router;
