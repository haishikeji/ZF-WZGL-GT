package com.px.project.web.service.impl;

import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.enums.DictionaryEnum;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IStationIntervalMapper;
import com.px.project.web.dto.StationIntervalDto;
import com.px.project.web.entity.StationIntervalModel;
import com.px.project.web.service.IProjectService;
import com.px.project.web.service.IStationIntervalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.px.project.web.service.impl
 * @ClassName StationIntervalServiceImpl
 * @Description StationIntervalServiceImpl
 * @author 品讯科技
 * @time   2021-03-09 08:51:10
 * @version V 1.0
 */
@Service
public class StationIntervalServiceImpl extends BaseService  implements IStationIntervalService<StationIntervalModel> {
    @Autowired
    public IStationIntervalMapper stationIntervalMapper;
    private static Logger logger = LoggerFactory.getLogger(StationIntervalServiceImpl.class);

    @Autowired
    public IProjectService projectService;
    /**
     * @name save
     * @author 品讯科技
     * @param stationIntervalModel
     * @Description 保存
     * @time 2021-03-09 08:51:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(StationIntervalModel stationIntervalModel) {
        //更新操作人员
        stationIntervalModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stationIntervalModel.setCreateUserCode(TokenUtil.getUserId(stationIntervalModel.getToken()));
        stationIntervalModel.setCreateUserName(TokenUtil.getUser (stationIntervalModel.getToken()).getString ("userName"));
        stationIntervalModel.setIsRemoved (DictionaryEnum.User_IsRemoved_Zero.getCode ().toString ());
        //判断项目是否存在
        if (!projectService.projectIsNormal(stationIntervalModel.getBelongProjectId ())) {
            return Result.buildResult(ReturnMsgEnum.Project_IsNot_Normal);
        }
        try {
            String id=UUIDUtil.get32UUID ();
            stationIntervalModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(stationIntervalModel));
            if(stationIntervalMapper.save(stationIntervalModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入StationIntervalModel成功-------------");
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-09 08:51:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(stationIntervalMapper.deleteByIds(ids) < 1) {
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
     * @author 品讯科技
     * @param ids
     * @Description 逻辑删除
     * @time 2021-03-09 08:51:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(stationIntervalMapper.logicDeleteByIds(ids) < 1) {
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
     * @author 品讯科技
     * @param stationIntervalModel
     * @Description 修改
     * @time 2021-03-09 08:51:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(StationIntervalModel stationIntervalModel) {
        //更新操作人员
        stationIntervalModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stationIntervalModel.setUpdateUserCode(TokenUtil.getUserId(stationIntervalModel.getToken()));
        stationIntervalModel.setCreateUserName(TokenUtil.getUser (stationIntervalModel.getToken()).getString ("userName"));
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(stationIntervalModel));
        try {
            if(stationIntervalMapper.update(stationIntervalModel) < 1) {
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
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2021-03-09 08:51:10
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, stationIntervalMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stationIntervalDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-09 08:51:10
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(StationIntervalDto stationIntervalDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(stationIntervalDto));
        try {
            page(stationIntervalDto);//分页判断
            return getResult(ReturnMsgEnum.OK, stationIntervalMapper.findAll(stationIntervalDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
