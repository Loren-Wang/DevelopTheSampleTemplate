package template.springboot.demo.base

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * 功能作用：请求数据拦截
 * 创建时间：2019-09-12 上午 10:59:46
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
open class BaseHttpServletRequestWrapper(request: HttpServletRequest, private var requestBody: ByteArray?) : HttpServletRequestWrapper(request) {

    override fun getInputStream(): ServletInputStream {
        if (requestBody == null) {
            requestBody = ByteArray(0)
        }

        val byteArrayInputStream = ByteArrayInputStream(requestBody!!)

        return object : ServletInputStream() {
            override fun isFinished(): Boolean {
                return false
            }

            override fun isReady(): Boolean {
                return false
            }

            override fun setReadListener(readListener: ReadListener) {

            }

            override fun read(): Int {
                return byteArrayInputStream.read()
            }
        }
    }


    /**
     * 重写 getReader()
     */
    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }

}
