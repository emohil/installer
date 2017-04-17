package com.company.repo.wk.dao;

import com.company.po.wk.Worker;
import com.company.repo.fw.dao.StringIdBaseDao;

public interface WorkerDao extends StringIdBaseDao<Worker> {

    String BEAN_NAME = "workerDao";
}
