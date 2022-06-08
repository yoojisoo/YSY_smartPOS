<template>
	<v-container fluid>
		<!-- 
          *****설명
          :page.sync="page"              -> 원하는 page를 선택했을때, 자동적으로 대상 page로 전환
          :items-per-page="itemsPerPage" -> 한 page에서 보여줄 list의 개 수
          hide-default-footer            -> 기본 페이지네이션 숨김
          @page-count                    -> 페이지 갯수 지정
       -->
		<v-data-table
			:headers="headers"
			:items="userInfo"
			:page.sync="page"
			:items-per-page="itemsPerPage"
			hide-default-footer
			single-select
			item-key="username"
			@page-count="pageCount = $event"
			@click:row="rowClick"
		>
			<template #item.regDt="{ item }">
				<dateForm :dateVal="item.regDt" :gubun="gubun" />
			</template>
		</v-data-table>

		<template>
			<!-- 
                *****설명
                pagination              -> 페이지 넘김 아이콘
                :length                 -> 총 페이지의 사이즈
                v-model="page"          -> 활성화 된 목록(버튼)
                :total-visible          -> ppagination에 표시할 page의 개수를 지정. 초과시 ... 표시
                next-icon과 prev-icon   -> pagination 아이콘 지정
                v-text-fiel             -> 입력한 숫자대로 row 갯수 조절
             -->
			<v-pagination v-model="page" :length="pageCount" :total-visible="totalVisible" circle>
			</v-pagination>
			<v-text-field
				:value="itemsPerPage"
				label="Items per page"
				type="number"
				min="-1"
				max="15"
				@input="itemsPerPage = parseInt($event, 10)"
			></v-text-field>
		</template>
	</v-container>
</template>

<script>
import dateForm from '@/components/DateForm.vue';
export default {
	props: ['headers', 'userInfo'],
	components: {
		dateForm,
	},
	data: () => ({
		page: 1,
		pageCount: 0,
		itemsPerPage: 10,
		totalVisible: 5,
		gubun: '',
	}),
	methods: {
		/** grid click event */
		rowClick(item, row) {
			console.log(item);
			console.log(row);
			row.select(true);
			this.selectedRow = item.username;
			console.log('item.username ----------------> ' + item.username);
		},
	},
};
</script>
<style></style>
