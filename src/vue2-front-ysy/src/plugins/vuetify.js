import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import colors from 'vuetify/lib/util/colors';

import '@/assets/scss/_style.scss';

Vue.use(Vuetify);

export default new Vuetify({
	// 언어설정
	//lang: {
	//	locales: { ko, en },
	//	current: 'ko',
	//},
	theme: {
		default: 'light',
		themes: {
			light: {
				//primary: '#6667AB',
				primary: colors.deepPurple.lighten1,
			},
			dark: {},
		},
	},
});
