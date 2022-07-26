<template>
	<v-pagination
		v-if="paginationInfo"
		v-model="page"
		:length="paginationInfo.cardCntInPage"
		:total-visible="paginationInfo.totalVisible ? paginationInfo.totalVisible : 10"
		:circle="paginationInfo.useCircle ? paginationInfo.useCircle : true"
		@input="updatePage"
	></v-pagination>
</template>

<script>
export default {
	props: ['paginationInfo'],
	data() {
		return {
			page: 1,
		};
	},
	methods: {
		updatePage(page) {
			this.paginationInfo.updatePage(page);
		},
	},
};
</script>

<style></style>
