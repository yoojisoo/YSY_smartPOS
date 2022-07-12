/**
 * @author 신민경
 * @create date 2022-07-12
 * @desc   Mixin Global
 */

import UserMixin from './UserMixin';
import YsyUtil from './YsyUtil';

const MixinGlobal = {
	mixins: [YsyUtil, UserMixin],
};

export default MixinGlobal;
