package com.bitskraft.notification.common.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/** created on: 10/12/23 created by: Anil Maharjan */
@Data
@MappedSuperclass
public abstract class AbstractEntity implements Entity {
  @JsonIgnore
  @CreationTimestamp
  @Column(updatable = false)
  private Date createdOn;

  @JsonIgnore @UpdateTimestamp private Date updatedOn;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "last_modified_by")
  private String lastModifiedBy;

  @Column(name = "deleted_by")
  private String deletedBy;

  @Column(name = "is_active")
  private boolean active = true;

  @Column(name = "is_deleted")
  private boolean deleted = false;
}
