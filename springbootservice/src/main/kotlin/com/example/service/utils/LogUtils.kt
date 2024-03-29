package template.springboot.demo.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

/**
 * 功能作用：日志打印工具
 * 创建时间：2019-09-12 上午 11:00:35
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
open class LogUtils {

    //日志控制器记录
    private val logControllerMap = ConcurrentHashMap<Class<*>, Logger>()

    /**
     * 打印debug日志
     *
     * @param cls 打印日志的class类
     * @param msg 日志内容
     */
    fun logD(cls: Class<*>, msg: String) {
        val logger = getLogger(cls)
        synchronized(logControllerMap) {
            logger?.debug(msg)
        }
    }

    /**
     * 打印debug日志
     *
     * @param cls 打印日志的class类
     * @param msg 日志内容
     */
    fun logI(cls: Class<*>, msg: String) {
        val logger = getLogger(cls)
        synchronized(logControllerMap) {
            logger?.info(msg)
        }
    }

    /**
     * 打印error日志
     *
     * @param cls 打印日志的class类
     * @param msg 日志内容
     */
    fun logE(cls: Class<*>, msg: String) {
        val logger = getLogger(cls)
        synchronized(logControllerMap) {
            logger?.error(msg)
        }
    }

    /**
     * 获取logger
     *
     * @param cls class
     * @return cls相应的logger
     */
    private fun getLogger(cls: Class<*>): Logger? {
        synchronized(logControllerMap) {
            var logger: Logger? = logControllerMap[cls]
            if (logger == null && IS_SHOW_LOG) {
                logger = LoggerFactory.getLogger(cls)
                logControllerMap[cls] = logger
            }
            return logger
        }
    }

    companion object {
        const val IS_SHOW_LOG = true//是否显示log日志
        private var logUtils: LogUtils? = null

        val instance: LogUtils
            get() {
                if (logUtils == null) {
                    logUtils = LogUtils()
                }
                return logUtils as LogUtils
            }
    }

}
