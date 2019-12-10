package template.springboot.demo.bean

/**
 * 功能作用：基础响应集
 * 创建时间：2019-09-12 上午 10:48:42
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
open class BaseResponseBean<T>(var data: T? = null) {
    var stateCode: Int = 0
    var state: String? = null

}
