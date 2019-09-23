package cn.com.data.engine.querybuilder.generic;

import cn.com.data.engine.querybuilder.QbField;

/**
 * Immutable class to implement all fields for a selected table.
 * @author Jinxw
 */
class QbAllTableFieldImp implements QbField
{
	private final String m_table;
	
	QbAllTableFieldImp(String table)
	{
		m_table = table;
	}
	
	@Override
	public String toString() 
	{
		return QbCommonImp.protectTableName(m_table) + ".*";
	}
}