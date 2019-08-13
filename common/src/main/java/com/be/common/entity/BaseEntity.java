package com.be.common.entity;

import com.be.common.annotation.Dic;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sun.rmi.runtime.Log;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

/**
 * Entity基类 用于业务entity的继承
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    public BaseEntity() {
        this.init();
    }

    private void init() {
        for (Field filed : this.getClass().getDeclaredFields()) {
            Annotation[] annotations = filed.getAnnotations();
            if (annotations == null || annotations.length < 1) {
                continue;
            }
            Dic dicAnnotation = (Dic) Arrays.stream(annotations).filter(a -> a instanceof Dic).distinct();
            String name = filed.getName();
           /* if (anns[0] instanceof Dic) {
                FiledAnnoAge filedAnnoAge = (FiledAnnoAge) anns[0];//注解的值
                String name = filed.getName();
                int age = filed.getInt(annoBean);//实际的值
            }
            //MyName注解分析
            if (anns[0] instanceof FiledAnnoName) {
                FiledAnnoName filedAnnoName = (FiledAnnoName) anns[0];
                String name = filedAnnoName.value();
                String fileName = (String) filed.get(annoBean);
                String filedName = filed.getName();
                Log.e("filedName_NAME", filedName);
                Log.e("Name", name + fileName + "");

            }*/
        }
    }

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    private Date editTime;

    /**
     * 创建人
     */
    @CreatedBy
    private String createBy;

    /**
     * 修改人时间
     */
    @LastModifiedBy
    private String editor;

    /**
     * 删除标识
     */
    private int isDelete;

    @PrePersist
    protected void onCreate() {
        isDelete = 0;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
