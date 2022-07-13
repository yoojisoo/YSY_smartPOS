<!-- 22.06.29 yoojisoo -->
<template>
	<v-container fluid pa-0 ma-0 mt-3>
		<v-card class="mb-3 pa-0" outlined tile>
			<v-data-table
				v-model="selected"
				:headers="gridInfo.headers"
				:items="gridInfo.dataList"
				:page.sync="page"
				:items-per-page="rowCnt"
				:show-select="gridInfo.isCheckBox"
				:single-select="gridInfo.isSingleSelect"
				:hide-default-header="false"
				hide-default-footer
				:item-key="gridKey"
				@page-count="pageCount = $event"
				@click:row="rowClick"
				@dblclick:row="rowDbClick"
			>
				<template v-slot:top>
					<v-toolbar flat dense>
						<v-toolbar-title>{{ gridInfo.gridNm }}</v-toolbar-title>
						<v-divider class="mx-4" inset vertical />
						<v-spacer />

						<div v-if="gridInfo.useBtn">
							<v-btn v-if="!gridInfo.isDetail" plain :to="gridInfo.path"
								>전체보기</v-btn
							>
							<v-btn-toggle v-else group>
								<v-btn plain @click="editedItem(item, '추가')">
									<h4 style="color: black" slot="">추가</h4>
								</v-btn>
								<v-btn plain @click="deleteItem(item)">
									<h4 style="color: black">삭제</h4>
								</v-btn>
							</v-btn-toggle>
						</div>
					</v-toolbar>
				</template>

				<template v-if="gridInfo.isDetail" v-slot:item.actions="{ item }">
					<v-icon small class="mr-2" @click.stop="editedItem(item, '수정')">
						mdi-pencil
					</v-icon>
				</template>
			</v-data-table>
			<!--<template>
				<v-pagination v-model="page" :length="pageCount" :total-visible="pageTotCnt" circle />
			</template>-->
		</v-card>
	</v-container>
</template>

<script>
export default {
	props: ['gridInfo', 'cardHeight'],
	data: () => ({
		selected: [],
		page: 1, // 최초 나타나는 페이지
		pageCount: 0, // 데이터 겟수에 따라 변경됨 ->  @page-count="pageCount = $event"
	}),
	methods: {
		/** grid click event */
		rowClick(row, gridNm) {
			this.gridInfo.rowClick(row, gridNm);
		},
		rowDbClick(row, gridNm) {
			this.gridInfo.rowDbClick(row, gridNm);
		},
		goToTable(path) {
			console.log(path);
			this.$router.replace(path);
		},
		editedItem(item, editedStr) {
			if (editedStr === '수정') {
				this.$router.push({ name: 'editor', params: { btnStr: editedStr, item } });
			} else if (editedStr === '추가') {
				this.$router.push({ name: 'editor', params: { btnStr: editedStr } });
			}
		},
		async deleteItem() {
			let username = [];
			for (const item of this.selected) {
				username.push(item.username);
			}
			await this.$axios.post('/ysy/v1/auth/delGridUserInfo', username).then(res => {
				if (res.statusText === 'OK') {
					console.log('ID = ' + res.config.data + '삭제완료');
				} else alert('유저 정보 그리드 삭제 에러');
			});
		},
	},
	computed: {
		rowCnt() {
			// 페이지당 로우 겟수
			return this.gridInfo.rowCnt != undefined ? this.gridInfo.rowCnt : 5;
		},
		pageTotCnt() {
			return this.gridInfo.pageTotCnt != undefined ? this.gridInfo.pageTotCnt : 5;
		},
		gridDense() {
			// 그리드 행 폭 작게 지정여부
			return this.gridInfo.gridDense != undefined ? this.gridInfo.gridDense : false;
		},
		gridKey() {
			let keys = '';
			this.gridInfo.headers.forEach(x => {
				if (x.key != undefined && x.key !== '') {
					keys = x.value;
				}
			});
			console.log('keys = ' + keys);
			return keys;
		},
	},
};
</script>
