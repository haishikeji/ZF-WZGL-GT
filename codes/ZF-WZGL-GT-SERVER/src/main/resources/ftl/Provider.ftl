package ${package}.dao.provider;

import ${p_package}.common.base.BaseProvider;
import ${p_package}.common.utils.StringUtil;
import ${package}.dto.${entity}Dto;
import ${package}.entity.${entity}Model;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package ${package}.dao.provider
 * @ClassName ${entity}Provider
 * @Description ${entity}Provider
 * @author ${authorName}
 * @time   ${systemDate}
 * @version V 1.0
 */
public class ${entity}Provider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql=${selectOrInsertSql};
    /**
     * @name save
     * @author ${authorName}
     * @param ${controllerNameSpace}Model
     * @Description 增加
     * @time ${systemDate}
     * @throws
     * @return sql
     */
    public String save(final ${entity}Model ${controllerNameSpace}Model) {
        String sql = new SQL () {
            {
                INSERT_INTO ("${tableName} ");
                VALUES("${primaryKey.columnPrimaryKeyName}", "${r'#'}{${primaryKey.propertyPrimaryKeyName}}");
                <#list list as list>
                if(!StringUtil.isBlank (${controllerNameSpace}Model.get${list.propertyBigName}())){
                    VALUES("${list.columnName}", "${r'#'}{${list.propertyName}}");
                }
                </#list>
            }
        }.toString ();
        return sql;
    }

    /**
     * @name deleteByIds
     * @author ${authorName}
     * @param ids
     * @Description 物理删除
     * @time ${systemDate}
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("${tableName}");
            }
        }.toString();
        sql += batchJoinField("${primaryKey.columnPrimaryKeyName}",ids);
        return sql;
    }


    /**
     * @name logicDeleteByIds
     * @author ${authorName}
     * @param ids
     * @Description 逻辑删除
     * @time ${systemDate}
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("${tableName}");
                SET("isRemoved = '1'");
            }
        }.toString();
        sql += batchJoinField("${primaryKey.columnPrimaryKeyName}",ids);
        return sql;
    }

    /**
     * @name update
     * @author ${authorName}
     * @param ${controllerNameSpace}Model
     * @Description 修改
     * @time ${systemDate}
     * @throws
     * @return sql
     */
    public String update(final ${entity}Model ${controllerNameSpace}Model) {
        String sql = new SQL () {
            {
                UPDATE("${tableName} ");
                <#list list as list>
                <#--if(!StringUtil.isBlank (${controllerNameSpace}Model.get${list.propertyBigName}())){
                    SET("${list.columnName} = ${r'#'}{${list.propertyName}}");
                }-->
                    <#--防止无法修改成空值问题 By：刘志勇 Date：2020-2-26 11:57:55-->
                if(${controllerNameSpace}Model.get${list.propertyBigName}() != null){
                    SET("${list.columnName} = ${r'#'}{${list.propertyName}}");
                }
                </#list>
                WHERE("${primaryKey.columnPrimaryKeyName} = ${r'#'}{${primaryKey.propertyPrimaryKeyName}}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findById
     * @author ${authorName}
     * @param id
     * @Description 根据Id查询数据
     * @time ${systemDate}
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("${tableName} t");
                WHERE("t.${primaryKey.columnPrimaryKeyName} = ${r'#'}{${primaryKey.propertyPrimaryKeyName}}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author ${authorName}
     * @param ${controllerNameSpace}Dto
     * @Description 根据条件查询数据并分页
     * @time ${systemDate}
     * @throws
     * @return sql
     */
    public String findAll(final ${entity}Dto ${controllerNameSpace}Dto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("${tableName} t");
                if(!StringUtil.isBlank (${controllerNameSpace}Dto.get${primaryKey.propertyPrimaryKeyBigName}())){
                    WHERE("t.${primaryKey.columnPrimaryKeyName} = ${r'#'}{${primaryKey.propertyPrimaryKeyName}}");
                }
            <#list list as list>
                if(!StringUtil.isBlank (${controllerNameSpace}Dto.get${list.propertyBigName}())){
                    WHERE("t.${list.columnName} = ${r'#'}{${list.propertyName}}");
                }
            </#list>
            }
        }.toString();
        return sql;
    }

}
