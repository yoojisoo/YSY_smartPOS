<!--
	*** props ***
	- info : {
		pageCnt 	 : int 	    > 페이지 수 ⭐필수
		totalVisible : int 	    > 표시될 버튼 수 				   (default 10개)
		useCircle 	 : boolean  > 버튼 모양 : true 원, false 사각형 (default true)
		updatePage	 : function > 페이지 버튼 눌렀을 때 실행될 함수
	}
-->
<template>
	<v-pagination
		v-if="info"
		v-model="page"
		:length="info.pageCnt"
		:total-visible="info.totalVisible ? info.totalVisible : 10"
		:circle="info.useCircle ? info.useCircle : true"
		@input="updatePage"
	></v-pagination>
</template>

<script>
export default {
	props: ['info'],
	data() {
		return {
			page: 1,
		};
	},
	methods: {
		updatePage(page) {
			this.info.updatePage(page);
		},
	},
};
</script>

<style></style>
