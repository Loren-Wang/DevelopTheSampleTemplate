package template.springboot.demo.database.table

/**
 * 功能作用：数据库表配置类
 * 创建时间：2019-09-12 上午 10:05:24
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
object TableInfoConfig {
    /*********************************表名部分*********************************/

    object TableName {
        /**
         * 数据库表版本
         */
        const val DATABASE_TABLE_VERSION = "db_table_version"
    }

    /**********************************通用部分*********************************/
    object CommonColumn {
        /**
         * 创建时间
         */
        const val CREATE_TIME = "createTime"
        /**
         * 更新时间
         */
        const val UPDATE_TIME = "updateTime"
    }

    /********************************数据库表版本表部分*****************************/
    object DatabaseTableVersionColumn {
        /**
         * 版本id
         */
        const val VERSION_ID = "v_id"
        /**
         * 版本名称
         */
        const val VERSION_NAME = "v_name"
        /**
         * 版本码
         */
        const val VERSION_CODE = "v_code"
    }
}
