package template.springboot.demo.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import template.springboot.demo.base.BaseController
import template.springboot.demo.config.NetParams
import javax.servlet.http.HttpServletRequest

/**
 * 功能作用：主页相关接口
 * 创建时间：2019-09-12 上午 10:31:41
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
@RestController
@RequestMapping(NetParams.Path.GROUP_DEMO)
@Api(tags = ["Demo"])
open class DemoController : BaseController() {

    @GetMapping(NetParams.Path.GROUP_DEMO_FIRST)
    @ApiOperation(value = "所有数据")
    fun all(request: HttpServletRequest): String {
        super.base(request, null)
        return responseSuccess(null)
    }
}
