package com.eicas.ecms.bo;

import com.eicas.ecms.entity.Ecms;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListAuditBo {

  private   List<Ecms> auditBos;
}
