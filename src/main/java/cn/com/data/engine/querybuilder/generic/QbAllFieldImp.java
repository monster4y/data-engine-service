package cn.com.data.engine.querybuilder.generic;

import cn.com.data.engine.querybuilder.QbField;

/**
 * Immutable class to implement all fields.
 * @author Jinxw
 */
class QbAllFieldImp implements QbField
{
	QbAllFieldImp() { }
	
	@Override
	public String toString()
	{
		return "*";
	}
}
