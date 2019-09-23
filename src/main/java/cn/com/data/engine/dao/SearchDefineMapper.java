package cn.com.data.engine.dao;

import java.util.List;
import java.util.Map;

import cn.com.data.engine.entity.SearchDefine;
import cn.com.data.engine.entity.SearchDefineWithBLOBs;

public interface SearchDefineMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	int insert(SearchDefineWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	int insertSelective(SearchDefineWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	SearchDefineWithBLOBs selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(SearchDefineWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	int updateByPrimaryKeyWithBLOBs(SearchDefineWithBLOBs record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table search_define
	 * @mbg.generated
	 */
	int updateByPrimaryKey(SearchDefine record);

	List<Map> fetchDefineList();
	
	Map fetchParamsById(Integer id);
	
	Map fetchVueStateById(Integer id);
	
	
}