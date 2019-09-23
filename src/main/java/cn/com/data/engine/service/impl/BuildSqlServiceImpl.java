/**
 * 
 */
package cn.com.data.engine.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.com.data.engine.querybuilder.QbFactory;
import cn.com.data.engine.querybuilder.QbField;
import cn.com.data.engine.querybuilder.QbSelect;
import cn.com.data.engine.querybuilder.QbWhere;
import cn.com.data.engine.service.BuildSqlService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.data.engine.dao.SearchDefineMapper;
import cn.com.data.engine.dao.SearchExecuteMapper;
import cn.com.data.engine.entity.SearchDefineWithBLOBs;
import cn.com.data.engine.entity.SearchExecute;

/**
 * @author jinxw
 *
 */

@Service
public class BuildSqlServiceImpl implements BuildSqlService {

	@Autowired
    QbFactory qbFactory;
	@Autowired
	SearchDefineMapper searchDefineMapper;
	@Autowired
	SearchExecuteMapper searchExecuteMapper;

	@Override
	public String buildSql(JSONObject json) {
		JSONObject mainInfo = json.getJSONObject("mainTBInfo");
		JSONArray joinInfo = json.getJSONArray("joinInfo");
		JSONArray searchInfo = json.getJSONArray("searchInfo");
		JSONArray resultInfo = json.getJSONArray("resultInfo");

		QbSelect select = qbFactory.newSelectQuery();
		select.from(mainInfo.getString("name"));
		if(joinInfo!=null) {
			joinInfo.forEach((joinobj) -> {
				JSONObject jsob = (JSONObject) joinobj;
				select.join(jsob.getString("tbname"), qbFactory.newStdField(mainInfo.getString("joinfield")),
						qbFactory.newQualifiedField(jsob.get("tbname").toString(), jsob.get("joinfield").toString()));
			});
		}
		

		List<QbField> groupList = new ArrayList<>();
		List<QbField> resultList = new ArrayList<>();
		List<QbField> orderList = new ArrayList<>();
		resultInfo.forEach((resultobj) -> {
			JSONObject jsob = (JSONObject) resultobj;
			QbField resultField = qbFactory
					.newQualifiedField(StringUtils.isNotBlank(ObjectUtils.toString(jsob.get("jointbname"), ""))
							? String.valueOf(jsob.get("jointbname"))
							: String.valueOf(jsob.get("maintbname")), String.valueOf(jsob.get("resultfield")));
			QbField groupresultField = buildGroupFunc(qbFactory, resultField, jsob.getString("grouped"));
			resultList.add(groupresultField);
			if(jsob.getString("grouped")!=null) {
				groupList.add(resultField);
			}
			if(jsob.getString("ordered")!=null) {
				orderList.add(resultField);	
			}
			
		});
		select.select(resultList.toArray(new QbField[groupList.size()]))
				.groupBy(groupList.toArray(new QbField[groupList.size()]))
				.orderBy(QbSelect.QbOrderBy.ASC, orderList.toArray(new QbField[orderList.size()]));
		QbWhere swhere = select.where();
		searchInfo.forEach((searchobj) -> {
			JSONObject jsob = (JSONObject) searchobj;
			QbField field = qbFactory.newQualifiedField(StringUtils.isNotBlank(ObjectUtils.toString(jsob.get("jointbname")))
					? String.valueOf(jsob.get("jointbname"))
					: String.valueOf(jsob.get("maintbname")), String.valueOf(jsob.get("searchfield")));
			swhere.where(field, getOperater(jsob.getString("operater")), "1");
		});
		return select.getQueryString();
	}
	
//	private String buildSimpleSql(JSONObject params) {
//		String tbname = params.getString("tbname");
//		List fieldlist = params.getJSONArray("fieldlist");
//		
//		return "";
//	}

	private QbField buildGroupFunc(QbFactory fac, QbField source, String type) {
		if(type==null) {
			return source;
		}
		QbField result = null;
		switch (type) {
		case "min":
			result = fac.newMin(source, "minny");
			break;
		case "max":
			result = fac.newMax(source, "maxxy");
			break;
		case "sum":
			result = fac.newSum(source, "summy");
			break;
		case "count":
			result = fac.newCount(source, "countty");
			break;
		default:
			result = source;
		}
		return result;
	}

	private QbWhere.QbWhereOperator getOperater(String type) {
		return QbWhere.QbWhereOperator.getQbWhereOperator(type);
	}

	@Override
	public List<Map> getQueryDefineList() {
		return searchDefineMapper.fetchDefineList();
	}

	@Override
	public JSONObject addDefine(SearchDefineWithBLOBs searchdefine) {
		searchDefineMapper.insertSelective(searchdefine);
		return null;
	}
	
	@Override
	public JSONObject updateDefine(SearchDefineWithBLOBs searchdefine) {
		searchDefineMapper.updateByPrimaryKeySelective(searchdefine);
		return null;
	}

	@Override
	public List<Map> getQueryExecuteList() {
		return searchExecuteMapper.fetchExecuteList();
	}

	@Override
	public JSONObject addExecute(SearchExecute searchexecute) {
		searchExecuteMapper.insertSelective(searchexecute);
		return null;
	}
	
	
	@Override
	public JSONObject updateExecute(SearchExecute searchexecute) {
		searchExecuteMapper.updateByPrimaryKeySelective(searchexecute);
		return null;
	}

	@Override
	public String getParams(Integer id) {
		Map result = searchDefineMapper.fetchParamsById(id);
		return ObjectUtils.toString(result.get("params"));
	}

	@Override
	public String getVuestate(Integer id) {
		Map result = searchDefineMapper.fetchVueStateById(id);
		return ObjectUtils.toString(result.get("vuestate"));
	}

	@Override
	@Transactional
	public String buildApi(Map params) {
		JSONObject paramJson = JSONObject.parseObject(JSON.toJSONString(params));
		String sql = buildSql(paramJson);
		SearchDefineWithBLOBs searchDefine = new SearchDefineWithBLOBs();
		searchDefine.setDsid(paramJson.getInteger("dsid"));
		String name ="define_"+UUID.randomUUID();
		searchDefine.setName(name);
		searchDefine.setParams(paramJson.getString("params"));
		searchDefine.setSqlcontent(sql);
		params.put("sqlcontent", sql);
		params.put("name", name);
		params.put("dsId", paramJson.getInteger("dsid"));
		searchDefine.setVuestate(JSON.toJSONString(params));
		searchDefineMapper.insertSelective(searchDefine);
//		SearchExecute searchEx = new SearchExecute();
//		searchEx.setDefineid(searchDefine.getId());
//		searchEx.setDsid(paramJson.getInteger("dsid"));
//		searchEx.setName("api_"+UUID.randomUUID());
//		searchExecuteMapper.insertSelective(searchEx);
		return searchDefine.getId().toString();
	}

	@Override
	public SearchExecute getExecuteById(Integer id) {
		return searchExecuteMapper.selectByPrimaryKey(id);
	}

	@Override
	public JSONObject deleteDefine(Integer id) {
		JSONObject result = new JSONObject();
		searchDefineMapper.deleteByPrimaryKey(id);
		result.put("status", "ok");
		return result;
	}

	@Override
	public JSONObject deleteExecute(Integer id) {
		JSONObject result = new JSONObject();
		searchExecuteMapper.deleteByPrimaryKey(id);
		result.put("status", "ok");
		return result;
	}


}
