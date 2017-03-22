package com.hy.lightning.orm;

import com.hy.lightning.orm.datasource.Configuration;
import com.hy.lightning.orm.datasource.DataSource;

public interface IDataSource {

	/**
	 * 通过xml配置文件加载DataSource
	 * @param xmlPath
	 * @return
	 */
	DataSource loadDataSource(String xmlPath);

	/**
	 * 通过conf加载DataSource
	 * @param conf
	 * @return
	 */
	DataSource loadDataSource(Configuration conf);

}
