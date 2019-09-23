package cn.com.data.engine.querybuilder.generic;

import cn.com.data.engine.querybuilder.QbField;

/**
 * Immutable class to implement a standard un-qualified field.
 * @author Jinxw
 */
class QbStdFieldImp implements QbField
{
	private final String m_fieldName;

	QbStdFieldImp(String field)
	{
		m_fieldName = field;
	}
	
	@Override
	public String toString() 
	{
		return '`' + m_fieldName + '`';
	}
}