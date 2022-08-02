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
// buttons
import BaseButtonLogoSmall from '@/components/1_molecules/buttons/BaseButtonLogoSmall.vue';
import BaseButtonLogoBig from '@/components/1_molecules/buttons/BaseButtonLogoBig.vue';
import BaseButtonOutlined from '@/components/1_molecules/buttons/BaseButtonOutlined.vue';
import BaseButtonThemeChange from '@/components/1_molecules/buttons/BaseButtonThemeChange.vue';
import BlockButtonGroup from '@/components/1_molecules/buttons/BlockButtonGroup.vue';
export { BaseButtonLogoSmall, BaseButtonLogoBig, BaseButtonOutlined, BaseButtonThemeChange, BlockButtonGroup };

// cards
import BaseCardImg from '@/components/1_molecules/cards/BaseCardImg.vue';
export { BaseCardImg };

// editors
export {};

// pagination
import BasePagination from '@/components/1_molecules/pagination/BasePagination.vue';
export { BasePagination };

// textFields
import BaseTextField from '@/components/1_molecules/textFields/BaseTextField.vue';
export { BaseTextField };

// thumbnail
import BaseThumbnail from '@/components/1_molecules/thumbnail/BaseThumbnail.vue';
export { BaseThumbnail };

import FGrid from '@/components/FGrid.vue';
export { FGrid };

/** 2_templates */
import TheHeader from '@/components/2_templates/header/TheHeader.vue';
import TheSystemBar from '@/components/2_templates/header/TheSystemBar.vue';
import TheFooter from '@/components/2_templates/footer/TheFooter.vue';
import TheAddrEdit from '@/components/2_templates/TheAddrEdit.vue';
import TheSearch from '@/components/2_templates/TheSearch.vue';
import TheDetailInfoVue from '@/components/2_templates/TheDetailInfo.vue';

export { TheHeader, TheSystemBar, TheFooter, TheAddrEdit, TheSearch, TheDetailInfoVue };

//import '@/mixin/MixinGlobal.js';
//import '@/mixin/UserMixin.js';
import YsyUtil from '@/mixin/YsyUtil.js';
export { YsyUtil };

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
import CommonService from '@/service/CommonService.js';
export { CommonService };

//import '@/store/modules/AuthStore.js';
//import '@/store/modules/MenuStore';
//import '@/store/modules/CommunityStore';
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
