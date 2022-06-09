<template>
	<v-card color="#6667AB" flat tile dark>
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
