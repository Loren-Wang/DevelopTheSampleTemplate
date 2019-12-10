package template.springboot.demo.database.helper

/**
 * 功能作用：banner轮播图帮助类
 * 创建时间：2019-09-12 上午 10:37:18
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
class DemoHelper private constructor() {
    private val TAG = javaClass.name!!

    companion object {
        private var demoHelper: DemoHelper? = null

        val instance: DemoHelper
            get() {
                if (demoHelper == null) {
                    synchronized(DemoHelper::class.java) {
                        if (demoHelper == null) {
                            demoHelper = DemoHelper()
                        }
                    }
                }
                return demoHelper as DemoHelper
            }
    }

}
