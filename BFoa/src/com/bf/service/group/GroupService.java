package com.bf.service.group;

import com.bf.base.BaseDao;

public interface GroupService extends BaseDao {
   public void deleteGroup(int g_id);
   //修改组
   public void updateGroup(int u_id,int g_id);
}
