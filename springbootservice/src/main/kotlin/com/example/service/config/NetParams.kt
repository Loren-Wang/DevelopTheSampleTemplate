package template.springboot.demo.config

/**
 * 功能作用：接口相关参数
 * 创建时间：2019-09-12 上午 10:33:0
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
object NetParams {
    /*****************************************接口地址相关部分 **************************************/
    object Path {
        const val GROUP_DEMO = "/demo"
        const val GROUP_DEMO_FIRST = "/first"
    }

    /*****************************************响应状态码相关部分 **************************************/

    object ResponseStatus {
        //相应状态码：成功
        const val STATUS_CODE_SUCCESS: Int = 200//成功
        const val STATUS_CODE_PARAMS_ERROR: Int = 1000//参数异常
        const val STATUS_CODE_FAIL_UN_KNOW: Int = 1001//未知异常失败
    }
}
