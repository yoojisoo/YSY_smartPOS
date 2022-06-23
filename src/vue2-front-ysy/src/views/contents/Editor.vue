<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-form @submit.prevent="editorInfo">
				<v-container fluid pa-0 ma-0>
					<v-row justify="center" no-gutters>
						<v-col cols="12" md="8" lg="8" xl="8">
							<v-row justify="center" no-gutters style="height: inherit">
								<v-col cols="12">
									<!-- <pageHistory :pageNameKo="pageNameKo" /> -->
								</v-col>
								<v-col cols="12">
									<v-row justify="center">
										<v-col cols="auto" align-self="center">
											<span class="h3 pb-0"> {{ title }} </span>
										</v-col>
										<v-spacer/>
										<v-col cols="auto" align-self="center">
											<v-btn
												outlined
												color="indigo"
												@click="saveEditor"
											>
												등록
											</v-btn>
										</v-col>
									</v-row>
									<v-divider class="my-5"/>
								</v-col>
								<v-col cols="12" align-self="center">
									<v-text-field
										label="제목을 입력하세요."
										single-line
										outlined
										clearable
										required
										hide-details
										class="pb-3"
									></v-text-field>
								</v-col>
								<v-col cols="12">
									<summer-note />
								</v-col>
							</v-row>
						<v-col>
							<p>{{this.$route.params}}</p>
							<p>{{this.$route.params.btnStr}}</p>
							<p>{{ this.getUserId }}</p>
						</v-col>
						</v-col>
					</v-row>
				</v-container>
			</v-form>
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
import summerNote from '@/components/SummerNote.vue'
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/footer/TheFooter.vue';
import pageHistory from '@/components/PageHistory.vue';
import { mapActions, mapGetters } from 'vuex';

const authStore = 'authStore';

export default {
  	components: {
		summerNote,
	  	mainSystemBar,
	  	mainHeader,
	  	mainFooter,
	  	pageHistory
	},
	data() {
		return {
			pageName: 'editor',
			title: '글쓰기',
		};
	},
	methods: {
		editorInfo() {

		},
		saveEditor() {
			console.log('');
		},
	},
	computed: {
		...mapGetters(authStore, ['getUserId']),
	},
	created() {
		console.log(this.$route.params.btnSt); 
	}
}
</script>

<style>
	.v-text-field legend{
		display: none;	
	}
	.v-text-field label{
		font-size: 15px;	
		font-weight: 400;
	}
</style>