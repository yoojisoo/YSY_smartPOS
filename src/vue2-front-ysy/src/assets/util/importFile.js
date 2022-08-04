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


/** 2_templates */

import TheHeader from '@/components/2_templates/header/TheHeader.vue';
import TheSystemBar from '@/components/2_templates/header/TheSystemBar.vue';
import TheFooter from '@/components/2_templates/footer/TheFooter.vue';
import TheAddrEdit from "@/components/2_templates/TheAddrEdit.vue";//, TheAddrEdit
import TheLeftCondition from "@/components/2_templates/TheLeftCondition.vue";
import TheDetailInfo from "@/components/2_templates/TheDetailInfo.vue";

export { TheHeader, TheSystemBar, TheFooter, TheLeftCondition, TheDetailInfo , TheAddrEdit };

import YsyUtil from '@/mixin/YsyUtil.js';
export { YsyUtil };

import CommonService from '@/service/CommonService.js';
export { CommonService };