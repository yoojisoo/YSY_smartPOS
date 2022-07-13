import TheFooter from '@/components/1_templates/footer/TheFooter.vue';
import TheAddrEdit from '@/components/1_templates/header/TheAddrEdit.vue';
import '@/components/2_molecules/buttons/FImgBtn.vue';
import '@/components/2_molecules/editors/FSummerNote.vue';
import '@/components/2_molecules/textFields/FTextField.vue';
import '@/components/2_molecules/FCarousel.vue';
import '@/components/2_molecules/FDateForm.vue';
import '@/components/2_molecules/FDialog.vue';
import '@/components/2_molecules/FGrid.vue';

import '@/mixin/MixinGlobal.js';
import '@/mixin/UserMixin.js';
import '@/mixin/YsyUtil.js';

import '@/plugins/axios.js';
import '@/plugins/vuetify.js';

import '@/router/routes/AuthRouter.js';
import '@/router/routes/CommonRouter';
import '@/router/routes/ErrorRouter';
import '@/router/routes/SignRouter';
import '@/router/index.js';

import '@/service/AuthService.js';
import '@/service/MenuService';
import '@/service/NoticeService';
import '@/service/UserService';

import '@/store/modules/AuthStore.js';
import '@/store/modules/MenuStore';
import '@/store/modules/NoticeStore';
import '@/store/modules/UserStore';
import '@/store/index.js';

import '@/views/auth/KakaoLogin.vue';
import '@/views/auth/NaverLogin.vue';
import '@/views/auth/SignIn.vue';
import '@/views/auth/SignUp.vue';

import '@/views/common/Home.vue';
import '@/views/common/NoticeStore.vue';
import '@/views/common/NoticeSystem.vue';
import '@/views/common/Qna.vue';

import '@/views/contents/Editor.vue';

import '@/views/error/NotFound.vue';

import '@/views/manager/calculate/DailyCalculate.vue';
import '@/views/manager/calculate/MonthlyCalculate.vue';
import '@/views/manager/calculate/YearlyCalculate.vue';
import '@/views/manager/menu/MenuBtnMng.vue';
import '@/views/manager/menu/MenuRoleMng.vue';
import '@/views/manager/product/ProdMng.vue';
import '@/views/manager/store/StoreMng.vue';
import '@/views/manager/user/UserMng.vue';

import '@/views/user/delivery/DeliveryHistory.vue';
import '@/views/user/delivery/DeliveryStatus.vue';
import '@/views/user/reservation/ReservationHistory.vue';
import '@/views/user/reservation/ReservationStatus.vue';
import '@/views/user/MyInfo.vue';


export default () => {
	return TheFooter, TheAddrEdit;
  };