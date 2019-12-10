package template.springboot.demo.database.table

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * 功能作用：基础数据表，所有表中都包含的
 * 创建时间：2019-10-02 下午 15:35:33
 * 创建人：王亮（Loren wang）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
@MappedSuperclass
open class BaseTb {
    /**
     * 创建时间
     */
    @Column(name = TableInfoConfig.CommonColumn.CREATE_TIME, updatable = false, nullable = false, columnDefinition = "datetime comment '通用-创建时间'")
    @CreatedDate
    @CreationTimestamp
    var createTime: Date? = null

    /**
     * 更新时间
     */
    @Column(name = TableInfoConfig.CommonColumn.UPDATE_TIME, nullable = false, columnDefinition = "datetime comment '通用-更新时间'")
    @LastModifiedDate
    @UpdateTimestamp
    var updateTime: Date? = null
}
