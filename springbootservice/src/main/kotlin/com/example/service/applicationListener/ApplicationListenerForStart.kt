package template.springboot.demo.applicationListener

import Setting
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import template.springboot.demo.database.repository.DatabaseTableVersionRepository
import template.springboot.demo.database.table.DatabaseTableVersionTb

/**
 * 功能作用：启动监听
 * 创建时间：2019-09-12 上午 10:57:7
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
open class ApplicationListenerForStart : ApplicationListener<ApplicationReadyEvent> {


    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        //初始化全局变量设置
        val runType = event.applicationContext.environment.getProperty("runType")
        Setting.RUN_TYPE = Integer.valueOf(runType!!)
        Setting.DATABASE_TABLE_VERSIONCODE = event.applicationContext.environment.getProperty("database.table.versionCode", Long::class.java)
        Setting.DATABASE_TABLE_VERSIONNAME = event.applicationContext.environment.getProperty("database.table.versionName")
        if (Setting.DATABASE_TABLE_VERSIONCODE == null || Setting.DATABASE_TABLE_VERSIONNAME == null) {
            throw Exception("初始化异常")
        }
        Setting.init()

        //数据库版本更新初始化
        DataBaseTableVersionUpdateOptions.init(event.applicationContext)
    }

    object DataBaseTableVersionUpdateOptions {
        //数据库表版本表操作实例
        private lateinit var databaseTableVersionRepository: DatabaseTableVersionRepository;

        fun init(applicationContext: ConfigurableApplicationContext) {

            //获取数据库表版本表操作实例
            databaseTableVersionRepository = applicationContext.getBean(DatabaseTableVersionRepository::class.java)
            val tableVersion = databaseTableVersionRepository.findDatabaseTableVersionTbByVersionCodeAndVersionName(Setting.DATABASE_TABLE_VERSIONCODE!!, Setting.DATABASE_TABLE_VERSIONNAME!!)
            if (tableVersion == null) {
                when (Setting.DATABASE_TABLE_VERSIONCODE) {
                    100L -> {
                        initVersion(applicationContext)
                    }
                    else -> {

                    }
                }

                //保存版本信息
                val databaseTableVersionTb = DatabaseTableVersionTb()
                databaseTableVersionTb.versionName = Setting.DATABASE_TABLE_VERSIONNAME
                databaseTableVersionTb.versionCode = Setting.DATABASE_TABLE_VERSIONCODE
                databaseTableVersionRepository.save(databaseTableVersionTb)
            }


        }

        /**
         * 初始化初始表版本
         */
        private fun initVersion(applicationContext: ConfigurableApplicationContext) {
        }
    }
}
