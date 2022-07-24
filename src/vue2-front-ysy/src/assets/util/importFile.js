/**
 * @author 유지수
 * @create date 2022-07-13
 * @desc   import Files 공통
 */

/**
 * templates(반제품) molecules(부품)
 * molecules 먼저 import해야 templates import시 오류 안남
 * 🔴 작은 부품 먼저 import 해야함 🔴
 */

/** 1_molecules */
import FLogoBtnS from '@/components/1_molecules/buttons/FLogoBtnS.vue';
import FLogoBtnB from '@/components/1_molecules/buttons/FLogoBtnB.vue';
import FThemeChgBtn from '@/components/1_molecules/buttons/FThemeChgBtn.vue';
//import FSummerNote from '@/components/1_molecules/editors/FSummerNote.vue';
//import FTextField from '@/components/1_molecules/textFields/FTextField.vue';
//import FCarousel from '@/components/1_molecules/FCarousel.vue';
//import FDateForm from '@/components/1_molecules/FDateForm.vue';
//import FDialog from '@/components/1_molecules/FDialog.vue';
import FGrid from '@/components/FGrid.vue';
import FOutlinedBtn from '@/components/1_molecules/buttons/FOutlinedBtn.vue';
//, FSummerNote, FTextField, FCarousel, FDateForm, FDialog, FGrid
export { FLogoBtnS, FLogoBtnB, FThemeChgBtn, FGrid, FOutlinedBtn };

/** 2_templates */
import TheHeader from '@/components/2_templates/header/TheHeader.vue';
import TheSystemBar from '@/components/2_templates/header/TheSystemBar.vue';
import TheFooter from '@/components/2_templates/footer/TheFooter.vue';
import TheAddrEdit from '@/components/2_templates/TheAddrEdit.vue';
import TheSearch from '@/components/2_templates/TheSearch.vue';

export { TheHeader, TheSystemBar, TheFooter, TheAddrEdit, TheSearch };

//import '@/mixin/MixinGlobal.js';
//import '@/mixin/UserMixin.js';
//import '@/mixin/YsyUtil.js';

//import '@/plugins/axios.js';
//import '@/plugins/vuetify.js';

//import '@/router/routes/AuthRouter.js';
//import '@/router/routes/CommonRouter';
//import '@/router/routes/ErrorRouter';
//import '@/router/routes/SignRouter';
//import '@/router/index.js';

//import '@/service/AuthService.js';
//import '@/service/MenuService';
//import '@/service/NoticeService';
//import '@/service/UserService';

//import '@/store/modules/AuthStore.js';
//import '@/store/modules/MenuStore';
//import '@/store/modules/NoticeStore';
//import '@/store/modules/UserStore';
//import '@/store/index.js';

//import '@/views/auth/KakaoLogin.vue';
//import '@/views/auth/NaverLogin.vue';
//import '@/views/auth/SignIn.vue';
//import '@/views/auth/SignUp.vue';

//import '@/views/common/Home.vue';
//import '@/views/common/NoticeStore.vue';
//import '@/views/common/NoticeSystem.vue';
//import '@/views/common/Qna.vue';

//import '@/views/contents/Editor.vue';

//import '@/views/error/NotFound.vue';

//import '@/views/manager/calculate/DailyCalculate.vue';
//import '@/views/manager/calculate/MonthlyCalculate.vue';
//import '@/views/manager/calculate/YearlyCalculate.vue';
//import '@/views/manager/menu/MenuBtnMng.vue';
//import '@/views/manager/menu/MenuRoleMng.vue';
//import '@/views/manager/product/ProdMng.vue';
//import '@/views/manager/store/StoreMng.vue';
//import '@/views/manager/user/UserMng.vue';

//import '@/views/user/delivery/DeliveryHistory.vue';
//import '@/views/user/delivery/DeliveryStatus.vue';
//import '@/views/user/reservation/ReservationHistory.vue';
//import '@/views/user/reservation/ReservationStatus.vue';
//import '@/views/user/MyInfo.vue';
