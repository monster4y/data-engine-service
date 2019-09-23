package cn.com.data.engine.querybuilder.generic;

import cn.com.data.engine.querybuilder.QbQuery;

/** 
 * Immutable class to implement a custom query.
 * @author Jinxw
 */
class QbCustomQuery implements QbQuery
{
	private final String m_sql;
	
	QbCustomQuery(String sql)
	{
		m_sql = sql;
	}
	
	@Override
	public String getQueryString()
	{
		return m_sql;
	}

	@Override
	public int getPlaceholderIndex(String placeholderName)
	{
		throw new IllegalArgumentException("Placeholder doesn't exist");
	}
}