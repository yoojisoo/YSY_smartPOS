<!--
    xs=600이하 
    sm=600 - 960
    md=960이상

     outlined rounded text  동그란 라인
                                plain 배경없앰.
-->
<template>
	<v-app>
		<v-container fluid class="main_layout_container">
			<v-row no_gutters class="mx-1 pa-0 mt-1">
				<v-col
					v-for="(data, idx) in cardList"
					:key="idx"
					:cols="grid_layout.default"
					:sm="grid_layout.tablet"
					:md="grid_layout.pc"
					class="mt-0 pa-0"
					:class="info && info.bottomMargin ? info.bottomMargin : bottomMargin"
				>
					<!-- <v-card class="pa-3 ma-0 mx-2" outlined >
                            <v-row no-gutters>
                                <v-col cols="8" class="ma-0 pa-0">
                                    <v-card-title class="pl-1 pt-0 ma-0 text-h5" @click="detailMove('content')" v-text="data.title" />
                                    <v-card-subtitle @click="detailMove('content')"  class="pa-0 pr-3">

                                        <v-card-text v-if="data.contentList" class="pa-0">
                                            <div v-for="(content1, idx2) in data.contentList" :key="idx2">
                                                {{content1.title}} : {{content1.text}}
                                            </div>
                                        </v-card-text>
                                        <v-card-text v-else class="pa-0">
                                            <div
                                                class="d-inline-block text-truncate cursor_finger"
                                                style="max-width: 100%;">
                                                {{data.content}}
                                            </div>
                                        </v-card-text>
                                    </v-card-subtitle>
                                </v-col>


                                <v-col cols="4"  class="ma-0 pa-0" style="text-align: center;">
                                    <v-avatar size="110" :tile = "info && info.isTile? info.isTile : true">
                                        <v-img class="cursor_finger" :src="dataList.img? dataList.img[0] : default_img" />
                                    </v-avatar>
                                </v-col>
                            </v-row>
                            <v-row no-gutters v-if="data.isAction">
                                <v-col cols="12">
                                    <v-card-actions>
                                        <span>
                                            <v-icon>mdi-eye </v-icon>
                                            {{info && info.viewCnt ?info.viewCnt : 0}}
                                        </span>
                                        <v-btn @click="detailMove('comment')" icon>
                                            <v-icon>mdi-comment</v-icon>
                                            {{info && info.commentCnt ?info.commentCnt : 0}}
                                        </v-btn>
                                        <v-spacer></v-spacer>
                                        <v-btn icon>
                                            <v-icon>mdi-heart</v-icon>
                                        </v-btn>
                                        <v-btn icon>
                                            <v-icon>mdi-bookmark</v-icon>
                                        </v-btn>
                                        <v-btn icon>
                                            <v-icon>mdi-share-variant</v-icon>
                                        </v-btn>
                                        <v-btn v-if="data.isOwner" icon>
                                            <v-icon>mdi-pencil</v-icon>
                                        </v-btn>
                                    </v-card-actions>
                                </v-col>
                            </v-row>
                        </v-card> -->

					<ImgCard :data="data" />
				</v-col>
			</v-row>
			<v-row no_gutters>
				<v-col cols="12" style="text-align: center">
					<Pagination :paginationInfo="paginationInfo" />
				</v-col>
			</v-row>
		</v-container>
	</v-app>
</template>

<script>
import { ImgCard, Pagination } from '@/assets/util/importFile.js';

export default {
	props: ['info'],
	components: {
		ImgCard,
		Pagination,
	},
	data() {
		return {
			paginationInfo: {
				cardCntInPage: 6, // 한 페이지에 표시 될 카드 수 ⭐
				cardCntInAll: 0, // 전체 카드 수 ⭐
				//totalVisible: 5, // 페이지 버튼 표시 사이즈
				//useCircle: true, // 페이지 버튼 circle로
				updatePage: page => {
					// 페이지 넘어갈때 실행될 함수 ⭐
					this.updatePage(page);
				},
			},
			grid_layout: {
				default: 12,
				tablet: 6,
				pc: 4,
			},
			default_img: 'https://cdn.vuetifyjs.com/images/cards/cooking.png',
			selection: 1,
			bottomMargin: 'mb-3',
			dataList: [], // 초기에 조회된 모든 카드 데이터 리스트
			cardList: [], // 페이징 처리 된 카드 데이터 리스트
		};
	},
	mounted() {
		this.dataInit();
		this.initPage(); // 초기 데이터를 불러온 후 첫페이지 셋팅
	},
	methods: {
		dataInit() {
			for (var i = 0; i < 50; i++) {
				var json = {
					title: 'title' + i,
					content: 'content2',
					writer: 'writer 2',
					count: ' 20',
					commentCnt: '3',
					contentList: [
						{ title: '상품명', text: '상품' + i },
						{ title: '재고 수량', text: i },
						{ title: '판매 수량', text: i + 1 },
					],
					// isAction : false,
					isAction: true,
					viewCnt: 1,
				};
				if (i == 0) {
					json.isOwner = true;
					json.content =
						'1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890';
				}
				this.dataList.push(json);
			}
		},
		initPage() {
			// 처음에 dataInit에서 조회해온 데이터들을 사이즈에 맞게 잘라서 보관
			this.paginationInfo.cardCntInAll = this.dataList.length;
			if (this.paginationInfo.cardCntInAll < this.paginationInfo.cardCntInPage) {
				// 한 페이지에 보여줄 카드 수가 전체 카드 수 초과 (그대로 씀)
				this.cardList = this.dataList;
			} else {
				// 한 페이지에 보여줄 카드 수가 전체 카드 수 이하 (사이즈만큼 잘라서 씀)
				this.cardList = this.dataList.slice(0, this.paginationInfo.cardCntInPage);
			}
		},
		updatePage(page) {
			// Pagination을 눌렀을때 해당 페이지의 데이터를 보관
			let start = (page - 1) * this.paginationInfo.cardCntInPage;
			let end = page * this.paginationInfo.cardCntInPage;
			this.cardList = this.dataList.slice(start, end);
		},
		cardClick() {
			console.log('click');
		},
		test() {
			console.log('test click');
		},
		detailMove(type) {
			console.log('detailMove click = ' + type);
		},
		onScroll() {
			this.scrollInvoked++;
			console.log(this.scrollInvoked);
		},
	},
};
</script>

<style>
.cursor_finger {
	cursor: pointer;
}
</style>
