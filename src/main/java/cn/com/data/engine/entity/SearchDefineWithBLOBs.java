package cn.com.data.engine.entity;

public class SearchDefineWithBLOBs extends SearchDefine {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column search_define.sqlcontent
     *
     * @mbg.generated
     */
    private String sqlcontent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column search_define.vuestate
     *
     * @mbg.generated
     */
    private String vuestate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column search_define.sqlcontent
     *
     * @return the value of search_define.sqlcontent
     *
     * @mbg.generated
     */
    public String getSqlcontent() {
        return sqlcontent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column search_define.sqlcontent
     *
     * @param sqlcontent the value for search_define.sqlcontent
     *
     * @mbg.generated
     */
    public void setSqlcontent(String sqlcontent) {
        this.sqlcontent = sqlcontent == null ? null : sqlcontent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column search_define.vuestate
     *
     * @return the value of search_define.vuestate
     *
     * @mbg.generated
     */
    public String getVuestate() {
        return vuestate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column search_define.vuestate
     *
     * @param vuestate the value for search_define.vuestate
     *
     * @mbg.generated
     */
    public void setVuestate(String vuestate) {
        this.vuestate = vuestate == null ? null : vuestate.trim();
    }
}