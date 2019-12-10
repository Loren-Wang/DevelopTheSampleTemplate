package template.springboot.demo.base

import javabase.lorenwang.dataparse.JdplwJsonUtils
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import template.springboot.demo.bean.BaseResponseBean
import template.springboot.demo.utils.LogUtils
import java.io.*
import javax.annotation.Resource
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 功能作用：接口拦截工具
 * 创建时间：2019-09-12 上午 10:57:45
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
@WebFilter
open class BaseControllerFilter : Filter {
    //    @Autowired
//    internal var userRepository: UserRepository? = null
    @Resource
    private val messageSource: MessageSource? = null
    private val notCheckTokenPathList = ArrayList<String>()

    init {
        //初始化不用验证token的接口列表
        notCheckTokenPathList.add("/swagger-ui.html")//swaggerui
        notCheckTokenPathList.add("/swagger-resources")//swaggerui
        notCheckTokenPathList.add("/v2/api-docs")//swaggerui
        notCheckTokenPathList.add("/webjars/.+")//网站相关
        notCheckTokenPathList.add("/swagger-resources/.+")//网站相关
    }

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        LogUtils.instance.logI(javaClass, "初始化筛选器")
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        var req = request as HttpServletRequest
        val rep = response as HttpServletResponse

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        rep.setHeader("Access-Control-Allow-Origin", "*")
        // 允许的访问方法
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH")
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        rep.setHeader("Access-Control-Max-Age", "3600")
        rep.setHeader("Access-Control-Allow-Headers", "token,Origin, X-Requested-With, Content-Type, Accept")
        rep.characterEncoding = "UTF-8"
        rep.contentType = "application/json; charset=utf-8"


        //拦截数据做单独处理
        var outputStream: ByteArrayOutputStream? = null
        try {
            if (req.inputStream != null) {
                outputStream = ByteArrayOutputStream()
                val bytes = ByteArray(1024)
                var length = 0
                while ({ length = req.inputStream.read(bytes); length }() > 0) {
                    outputStream.write(bytes, 0, length)
                    outputStream.flush()
                }
//                val requestData = NetDataTransmitUtils.instance
//                        .decodeData(String(outputStream.toByteArray(), charset("utf-8")))
                val requestData = String(outputStream.toByteArray(), charset("utf-8"))
                LogUtils.instance.logI(javaClass, "请求${req.servletPath}接口，请求数据：${requestData}")
                req = BaseHttpServletRequestWrapper(req, requestData.toByteArray())
            }
        } catch (e: Exception) {
            LogUtils.instance.logE(javaClass, "数据源读取错误")
            return
        } finally {
            if (outputStream != null) {
                outputStream.close()
                outputStream = null
            }
        }

        //部分接口不需要验证token
        val servletPath = req.servletPath
        for (item in notCheckTokenPathList) {
            if (servletPath.matches(Regex(item))) {
                LogUtils.instance.logI(javaClass, "请求的是（$servletPath）接口,不需要验证token!")
                chain.doFilter(req, response)
                return
            }
        }

        chain.doFilter(req, response)

//         //有些web接口会在请求接口之前发生options请求，这个直接通过即可
//         val method = request.method
//         if (method == "OPTIONS") {
//             rep.status = HttpServletResponse.SC_OK
//             return
//         }


//
//        //部分接口不需要验证token
//        if (notCheckTokenPathList.contains(req.servletPath)) {
//            LogUtils.instance.logI(javaClass, "请求的是（" + req.servletPath + "）接口,不需要验证token!")
//            chain.doFilter(req, response)
//            return
//        }
//        //验证token是否正常
//        val objects = UserHelper.instance.checkTokenNormal(userRepository!!, req.getHeader(NetRequestParams.KEY_HEAD_TOKEN))
//        if (objects[0] as Boolean) {
//            LogUtils.instance.logI(javaClass, "token验证成功")
//            chain.doFilter(req, response)
//        } else {
//            rep.status = HttpServletResponse.SC_OK
//            val msg = getMessage(objects[2] as String, null)
//            LogUtils.instance.logI(javaClass, "token验证失败,code:" + objects[1] + ",msg:" + msg)
//            responseData(objects[1] as Int, msg, response)
//        }
    }

    /**
     * 指定响应数据，使用实体转换为流的形式写入到响应体当中
     *
     * @param code 响应code码
     * @param msg  响应消息
     */
    @Synchronized
    private fun responseData(code: Int, msg: String?, response: ServletResponse) {
        val baseResponseBean = BaseResponseBean<Any>()
        baseResponseBean.stateCode = code
        baseResponseBean.state = msg
        var writer: PrintWriter? = null
        var osw: OutputStreamWriter? = null
        try {
            osw = OutputStreamWriter(response.outputStream,
                    "UTF-8")
            writer = PrintWriter(osw, true)
            val jsonStr = JdplwJsonUtils.toJson(baseResponseBean)
            writer.write(jsonStr)
            writer.flush()
            writer.close()
            osw.close()
        } catch (e: UnsupportedEncodingException) {
            LogUtils.instance.logE(javaClass, "过滤器返回信息失败:" + e.message)
        } catch (e: IOException) {
            LogUtils.instance.logE(javaClass, "过滤器返回信息失败:" + e.message)
        } finally {
            writer?.close()
            if (null != osw) {
                try {
                    osw.close()
                } catch (e: IOException) {
                    LogUtils.instance.logE(javaClass, "过滤器返回信息失败:" + e.message)
                }

            }
        }
    }


    /**
     * 获取文字字符
     * @param code ：对应messages配置的key.
     * //     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    protected fun getMessage(code: String, defaultMessage: String?): String? {
        var defaultMessage = defaultMessage
        if (defaultMessage == null) {
            defaultMessage = ""
        }
        //这里使用比较方便的方法，不依赖request.
        val locale = LocaleContextHolder.getLocale()
        return messageSource!!.getMessage(code, null, defaultMessage, locale)
    }

    override fun destroy() {
        LogUtils.instance.logI(javaClass, "销毁筛选器")
    }
}
