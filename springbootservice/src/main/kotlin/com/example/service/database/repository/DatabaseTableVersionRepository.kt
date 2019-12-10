package template.springboot.demo.database.repository

import org.springframework.data.repository.CrudRepository
import template.springboot.demo.database.table.DatabaseTableVersionTb

/**
 * 功能作用：数据库表版本表操作
 * 创建时间：2019-09-15 下午 19:45:57
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
interface DatabaseTableVersionRepository : CrudRepository<DatabaseTableVersionTb, Long> {
    fun findDatabaseTableVersionTbByVersionCodeAndVersionName(versionCode: Long, versionName: String): DatabaseTableVersionTb?
}
