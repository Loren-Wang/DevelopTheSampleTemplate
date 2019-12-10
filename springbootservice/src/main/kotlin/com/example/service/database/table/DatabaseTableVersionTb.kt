package template.springboot.demo.database.table

import com.fasterxml.jackson.annotation.JsonAutoDetect
import javax.persistence.*

/**
 * 功能作用：数据库表版本
 * 创建时间：2019-10-02 下午 15:45:1
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
@Entity
@Table(name = TableInfoConfig.TableName.DATABASE_TABLE_VERSION)
@org.hibernate.annotations.Table(appliesTo = TableInfoConfig.TableName.DATABASE_TABLE_VERSION, comment = "数据库表版本表")
@JsonAutoDetect
class DatabaseTableVersionTb : BaseTb() {
    /**
     * 版本id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = TableInfoConfig.DatabaseTableVersionColumn.VERSION_ID)
    var id: Long? = null
    /**
     * 版本名称
     */
    @Column(name = TableInfoConfig.DatabaseTableVersionColumn.VERSION_NAME, nullable = false, columnDefinition = "varchar(50) comment '数据库版本名称'")
    var versionName: String? = null
    /**
     * 版本码
     */
    @Column(name = TableInfoConfig.DatabaseTableVersionColumn.VERSION_CODE, nullable = false, columnDefinition = "bigint comment '数据库版本码'")
    var versionCode: Long? = null
}
