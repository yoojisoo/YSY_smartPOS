<template>
	<!-- <v-btn outlined :height="parsed" @click="event"> -->
	<v-btn outlined :height="btnHeight">
		{{ btnText }}
	</v-btn>
</template>

<script>
export default {
	props: ['btnText', 'btnHeight'],
	//props: {
	//    btnText: String,
	//    btnHeight: Number,
	//    click: Function
	//},
};
</script>
