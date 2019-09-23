package cn.com.data.engine.querybuilder.generic;

import cn.com.data.engine.querybuilder.QbField;

/**
 * Immutable class to implement custom field.
 * @author Jinxw
 */
class QbCustomFieldImp implements QbField
{
	private final String m_custom;
	
	QbCustomFieldImp(String custom)
	{
		m_custom = custom;
	}

	@Override
	public String toString() 
	{
		return m_custom;
	}
}