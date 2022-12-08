<template>
	<v-container class="main_layout_container">
		<v-row class="main_layout_row">
			<v-col class="main_layout_col" cols="12" md="10" lg="10" xl="10">
				<v-row justify="center" no-gutters style="height: inherit">
					<v-col cols="12">
						<v-breadcrumbs :items="breadCrumbsInfo">
							<template v-slot:divider>
								<v-icon>mdi-chevron-right</v-icon>
							</template>
						</v-breadcrumbs>
					</v-col>
					<v-col cols="12">
						<v-tabs centered>
							<v-tab>전체</v-tab>
							<v-tab>판매중</v-tab>
							<v-tab>품절</v-tab>
							<v-tab>숨김</v-tab>
						</v-tabs>
					</v-col>
					<v-col cols="12">
						<ysyGrid :gridInfo="prodInfo" />
					</v-col>
				</v-row>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import ysyGrid from '@/components/FGrid.vue';

export default {
	components: {
		ysyGrid,
	},
	data() {
		return {
			prodInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'no', width: '15%', key: true },
					{ text: '상품명', value: 'prodName', width: '40%' },
					{ text: '가격', value: 'price', width: '30%' },
					{ text: '카테고리', value: 'category', width: '15%', sortable: false },
				],
				dateGubun: '/',
				gridNm: '상품관리',
				path: '/prodMng',
				isCheckBox: true,
				isSingleSelect: false,
				gridDense: false,
				useBtn: true,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
			},
		};
	},
};
</script>

<style>
</style>