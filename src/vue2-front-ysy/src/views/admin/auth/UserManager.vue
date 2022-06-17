<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container fluid pa-0 ma-0 style="height: 100%">
				<v-row>
					<v-col cols="2">1234</v-col>
					<v-col cols="10">5678</v-col>
				</v-row>
			</v-container>
		</v-main>
		<!-- Main End -->

		<!-- Footer Start -->
		<v-footer class="ma-0 pa-0" fixed app>
			<mainFooter />
		</v-footer>
		<!-- Footer End -->
	</v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/footer/TheFooter.vue';
// import ysyGrid from '@/components/YsyGrid.vue';
import { mapGetters, mapActions } from 'vuex';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		// ysyGrid,
	},
	data() {
		return {
			pageName: 'User Manager',
		};
	},
};
</script>

<style>
@import '../../../assets/css/global_layout.css';
</style>
