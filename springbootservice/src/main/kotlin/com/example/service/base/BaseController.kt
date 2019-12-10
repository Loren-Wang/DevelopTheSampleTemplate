package template.springboot.demo.base

import javabase.lorenwang.dataparse.JdplwJsonUtils
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import template.springboot.demo.bean.BaseResponseBean
import template.springboot.demo.config.NetParams
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest


/**
 * 功能作用：基类控制器
 * 创建时间：2019-09-12 上午 09:51:38
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
open class BaseController {
    protected val TAG = javaClass.name!!

    @Resource
    private lateinit var messageSource: MessageSource

    /**
     * 保留方法（所有子方法都要继承这个方法）
     * @param t
     */
    protected fun <T> base(request: HttpServletRequest, t: T?) {}

    /**
     * 获取文字字符
     * @param code ：对应messages配置的key.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    protected fun getMessage(code: String, defaultMessage: String?): String {
        //这里使用比较方便的方法，不依赖request.
        return messageSource.getMessage(code, null, defaultMessage
                ?: "", LocaleContextHolder.getLocale()) ?: ""
    }
    /**
     * 接口请求争取返回
     * @param obj
     * @param <T>
     * @return
    </T> */
    protected fun <T> responseSuccess(obj: T?): String {
        val baseResponseBean = BaseResponseBean<T>(obj)
        baseResponseBean.stateCode = NetParams.ResponseStatus.STATUS_CODE_SUCCESS
        baseResponseBean.state = getMessage("net_request_statue_success", null)
        return JdplwJsonUtils.toJson(baseResponseBean)
    }

    /**
     * 响应失败，类型是参数错误
     * @return
     */
    protected fun responseErrorForParams(): String {
        val baseResponseBean = BaseResponseBean<Any>()
        baseResponseBean.stateCode = NetParams.ResponseStatus.STATUS_CODE_PARAMS_ERROR
        baseResponseBean.state = getMessage("net_request_statue_params_error", null)
        return JdplwJsonUtils.toJson(baseResponseBean)
    }

    /**
     * 响应失败，类型是参数错误
     * @return
     */
    protected fun responseErrorForParams(stateCode: Int, stateMsg: String): String {
        val baseResponseBean = BaseResponseBean<Any>()
        baseResponseBean.stateCode = stateCode
        baseResponseBean.state = stateMsg
        return JdplwJsonUtils.toJson(baseResponseBean)
    }

    /**
     * 未知原因响应失败
     * @param statusCode
     * @param statusMsg
     * @return
     */
    protected fun responseFailForUnKnow(statusCode: Int?, statusMsg: String?): String {
        val baseResponseBean = BaseResponseBean<Any>()
        baseResponseBean.stateCode = statusCode ?: NetParams.ResponseStatus.STATUS_CODE_FAIL_UN_KNOW
        baseResponseBean.state = if (!(statusMsg == null || statusMsg.trim { it <= ' ' }.isEmpty()))
            statusMsg
        else
            getMessage("net_request_statue_fail_un_know", null)
        return JdplwJsonUtils.toJson(baseResponseBean)
    }


}
