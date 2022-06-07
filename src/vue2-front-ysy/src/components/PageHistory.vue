<template>
	<v-card color="#385F73" dark>
		<v-card-subtitle>
			나중에 페이지 히스토리 연결할곳 !!! 현재 페이지 : {{ this.pageNameKo }}
		</v-card-subtitle>
	</v-card>
</template>

<script>
export default {
	props: ['pageNameKo'],
};
</script>

<style></style>
