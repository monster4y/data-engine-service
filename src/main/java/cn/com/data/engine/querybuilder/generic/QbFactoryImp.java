package cn.com.data.engine.querybuilder.generic;

import cn.com.data.engine.querybuilder.QbDelete;
import org.springframework.stereotype.Service;

import cn.com.data.engine.querybuilder.QbFactory;
import cn.com.data.engine.querybuilder.QbField;
import cn.com.data.engine.querybuilder.QbInsert;
import cn.com.data.engine.querybuilder.QbQuery;
import cn.com.data.engine.querybuilder.QbSelect;
import cn.com.data.engine.querybuilder.QbUpdate;

@Service
public class QbFactoryImp implements QbFactory
{
	private static QbField m_allField = new QbAllFieldImp();
	
	@Override
	public QbField newAllField()
	{
		return m_allField;
	}

	@Override
	public QbField newAllField(String table)
	{
		return new QbAllTableFieldImp(table);
	}

	@Override
	public QbField newStdField(String name)
	{
		return new QbStdFieldImp(name);
	}

	@Override
	public QbField newQualifiedField(String table, String name)
	{
		return new QbQualifiedFieldImp(table, name);
	}

	@Override
	public QbField newSum(QbField field, String alias)
	{
		return new QbAggregateFieldImp(field, "SUM", alias);
	}

	@Override
	public QbField newCount(QbField field, String alias) 
	{
		return new QbAggregateFieldImp(field, "COUNT", alias);
	}

	@Override
	public QbField newAvg(QbField field, String alias)
	{
		return new QbAggregateFieldImp(field, "AVG", alias);
	}

	@Override
	public QbField newMin(QbField field, String alias)
	{
		return new QbAggregateFieldImp(field, "MIN", alias);
	}

	@Override
	public QbField newMax(QbField field, String alias)
	{
		return new QbAggregateFieldImp(field, "MAX", alias);
	}

	@Override
	public QbField newCustomField(String custom)
	{
		return new QbCustomFieldImp(custom);
	}

	@Override
	public QbSelect newSelectQuery()
	{
		return new QbSelectImp();
	}

	@Override
	public QbUpdate newUpdateQuery()
	{
		return new QbUpdateImp();
	}

	@Override
	public QbDelete newDeleteQuery()
	{
		return new QbDeleteImp();
	}

	@Override
	public QbInsert newInsertQuery()
	{
		return new QbInsertImp();
	}

	@Override
	public QbQuery newQuery(String sql)
	{
		return new QbCustomQuery(sql);
	}
}