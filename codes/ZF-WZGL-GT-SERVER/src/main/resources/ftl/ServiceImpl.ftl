package ${package}.service.impl;

import ${p_package}.common.base.BaseService;
import ${p_package}.common.base.Result;
import ${p_package}.common.base.ReturnMsgEnum;
import ${p_package}.common.utils.JsonUtil;
import ${p_package}.common.utils.TokenUtil;
import ${p_package}.common.utils.UUIDUtil;
import ${package}.dao.I${entity}Mapper;
import ${package}.dto.${entity}Dto;
import ${package}.entity.${entity}Model;
import ${package}.service.I${entity}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package ${package}.service.impl
 * @ClassName ${entity}ServiceImpl
 * @Description ${entity}ServiceImpl
 * @author ${authorName}
 * @time   ${systemDate}
 * @version V 1.0
 */
@Service
public class ${entity}ServiceImpl extends BaseService implements I${entity}Service${r'<'}${entity}Model${r'>'} {
    @Autowired
    public I${entity}Mapper ${controllerNameSpace}Mapper;
    private static Logger logger = LoggerFactory.getLogger(${entity}ServiceImpl.class);

    /**
     * @name save
     * @author ${authorName}
     * @param ${controllerNameSpace}Model
     * @Description 保存
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(${entity}Model ${controllerNameSpace}Model) {
        //更新操作人员
        ${controllerNameSpace}Model.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ${controllerNameSpace}Model.setCreateUserCode(TokenUtil.getUserId(${controllerNameSpace}Model.getToken()));
        ${controllerNameSpace}Model.setCreateUserName(TokenUtil.getUser (${controllerNameSpace}Model.getToken()).getString ("userName"));

        try {
            String id=UUIDUtil.get32UUID ();
            ${controllerNameSpace}Model.set${primaryKey.propertyPrimaryKeyBigName} (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(${controllerNameSpace}Model));
            if(${controllerNameSpace}Mapper.save(${controllerNameSpace}Model) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入${entity}Model成功-------------");
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name deleteByIds
     * @author ${authorName}
     * @param ids
     * @Description 物理删除
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(${controllerNameSpace}Mapper.deleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.info ("删除失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        logger.info ("-------------删除成功-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @name logicDeleteByIds
     * @author ${authorName}
     * @param ids
     * @Description 逻辑删除
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(${controllerNameSpace}Mapper.logicDeleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.info ("删除失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        logger.info ("-------------逻辑删除成功-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @name update
     * @author ${authorName}
     * @param ${controllerNameSpace}Model
     * @Description 修改
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(${entity}Model ${controllerNameSpace}Model) {
        //更新操作人员
        ${controllerNameSpace}Model.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ${controllerNameSpace}Model.setUpdateUserCode(TokenUtil.getUserId(${controllerNameSpace}Model.getToken()));
        ${controllerNameSpace}Model.setCreateUserName(TokenUtil.getUser (${controllerNameSpace}Model.getToken()).getString ("userName"));
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(${controllerNameSpace}Model));
        try {
            if(${controllerNameSpace}Mapper.update(${controllerNameSpace}Model) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info ("修改失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
    * @name findById
    * @author ${authorName}
    * @param id
    * @Description 根据条件查询所有数据
    * @time ${systemDate}
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, ${controllerNameSpace}Mapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author ${authorName}
     * @param ${controllerNameSpace}Dto
     * @Description 根据条件查询所有数据并分页
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(${entity}Dto ${controllerNameSpace}Dto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(${controllerNameSpace}Dto));
        try {
            page(${controllerNameSpace}Dto);//分页判断
            return getResult(ReturnMsgEnum.OK, ${controllerNameSpace}Mapper.findAll(${controllerNameSpace}Dto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
