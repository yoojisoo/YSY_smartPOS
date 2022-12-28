<template>
	<v-container class="main_layout_container">
		<v-row class="main_layout_row">
			<v-col class="main_layout_col" cols="12" md="10" lg="10" xl="10">
				<v-row justify="center" no-gutters style="height: inherit">
					<v-col cols="12">
						<BlockButtonGroup :info="ButtonGroupInfo" />
						<SummerNote :info="SummerNoteInfo" />
					</v-col>
				</v-row>
			</v-col>
		</v-row>
		<!--<template v-if="isPopup">
			<boardDialog :boardObj="currentRow" :callback="dialogCallback" />
		</template>-->
	</v-container>
</template>
<script>
import { BlockButtonGroup } from '@/assets/import/index.js';
import SummerNote from '@/components/1_molecules/editors/SummerNote.vue';

export default {
	components: {
		BlockButtonGroup,
		SummerNote,
	},
	data() {
		return {
			files: [],
			//버튼 그룹 정보
			ButtonGroupInfo: {
				// callbackSearch : ()=>{  console.log("callbackSearch Click");    },
				callbackSave: () => {
					console.log('callbackSave Click');
					this.saveClick();
				},
				callbackModify: () => {
					console.log('callbackModify Click');
					this.modClick();
				},
				callbackDelete: () => {
					console.log('callbackDelete Click');
					this.delClick();
				},
				// callbackDown   : ()=>{  console.log("callbackDown Click");      },
				// callbackUpload : ()=>{  console.log("callbackUpload Click");    },
			},
			//summerNote info
			SummerNoteInfo: {
				isEdit: true,
				isTitle: true,
				isSubTitle: true,
				orgTitle: '',
				orgSubTitle: '',
				orgContent: '',
				titleCallback: chgTitle => {
					console.log('TitleCallback', chgTitle);
					this.$store.dispatch('adminFreeBoardStore/setTitle', chgTitle);
				},
				subTitleCallback: chgSubTitle => {
					console.log('SubTitleCallback', chgSubTitle);
					this.$store.dispatch('adminFreeBoardStore/setSubTitle', chgSubTitle);
				},
				contentCallback: chgContent => {
					console.log('ContentCallback', chgContent);
					this.$store.dispatch('adminFreeBoardStore/setContent', chgContent);
				},
			},
		};
	},
};
</script>
