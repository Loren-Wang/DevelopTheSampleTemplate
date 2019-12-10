import template.springboot.demo.config.Common

/**
 * 创建时间： 0001/2018/6/1 下午 2:10
 * 创建人：王亮（Loren wang）
 * 功能作用：所有的设置参数信息
 * 思路：
 * 修改人：
 * 修改时间：
 * 备注：
 */
object Setting {
    var RUN_TYPE: Int = Common.RUN_TYPE_PRO//代码运行类型
    var SEND_VERIFICATION_CODE_INTERVAL_TIME_FOR_TYPE: Long = 0//相同类型的验证码请求间隔时间
    var IS_SEND_VERIFICATION_CODE: Boolean = false//是否需要发送短信验证码
    var IS_CHECK_VERIFICATION_CODE: Boolean = false//是否需要检查验证码
    var USER_ACCESS_TOKEN_EFFECTIVITY_TIME: Long = 0//access_token 有效时间
    var DATABASE_TABLE_VERSIONNAME: String? = null//数据库表版本名称
    var DATABASE_TABLE_VERSIONCODE: Long? = null//数据库表版本号
    const val CONTROLLER_DIR_PATH = "template.springboot.demo.controller"


    /**
     * 初始化
     */
    fun init() {
        USER_ACCESS_TOKEN_EFFECTIVITY_TIME = 604800000//access_token 有效时间
        when (RUN_TYPE) {
            Common.RUN_TYPE_TEST -> {
                SEND_VERIFICATION_CODE_INTERVAL_TIME_FOR_TYPE = 10000
                IS_SEND_VERIFICATION_CODE = true
                IS_CHECK_VERIFICATION_CODE = false
            }
            Common.RUN_TYPE_DEV -> {
                SEND_VERIFICATION_CODE_INTERVAL_TIME_FOR_TYPE = 10000
                IS_SEND_VERIFICATION_CODE = false
                IS_CHECK_VERIFICATION_CODE = false
            }
            Common.RUN_TYPE_PRO -> {
                SEND_VERIFICATION_CODE_INTERVAL_TIME_FOR_TYPE = 60000
                IS_SEND_VERIFICATION_CODE = true
                IS_CHECK_VERIFICATION_CODE = true
            }
            else -> {
                SEND_VERIFICATION_CODE_INTERVAL_TIME_FOR_TYPE = 60000
                IS_SEND_VERIFICATION_CODE = true
                IS_CHECK_VERIFICATION_CODE = true
            }
        }
    }

}
