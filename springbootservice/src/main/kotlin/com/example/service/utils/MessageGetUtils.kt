package template.springboot.demo.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.i18n.LocaleContextHolder
import javax.annotation.Resource

/**
 * 创建时间： 0006/2018/6/6 下午 4:24
 * 创建人：王亮（Loren wang）
 * 功能作用：消息获取单例
 * 功能方法：
 * 思路：
 * 修改人：
 * 修改时间：
 * 备注：
 */
open class MessageGetUtils {

    @Resource
    @Autowired
    private var context: ConfigurableApplicationContext? = null

    fun init(context: ConfigurableApplicationContext) {
        this.context = context
    }

    /**
     * 获取文字字符
     * @param code ：对应messages配置的key.
     * //     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    @JvmOverloads
    fun getMessage(code: String, defaultMessage: String? = null): String? {
        var defaultMessage = defaultMessage
        if (defaultMessage == null) {
            defaultMessage = ""
        }
        //这里使用比较方便的方法，不依赖request.
        val locale = LocaleContextHolder.getLocale()
        return context!!.getMessage(code, null, defaultMessage, locale)
    }

    companion object {
        private var messageGetUtils: MessageGetUtils? = null
        val instance: MessageGetUtils
            get() {
                if (messageGetUtils == null) {
                    messageGetUtils = MessageGetUtils()
                }
                return messageGetUtils as MessageGetUtils
            }
    }
}
/**
 * 获取文字字符
 * @param code ：对应messages配置的key.
 * @return
 */
