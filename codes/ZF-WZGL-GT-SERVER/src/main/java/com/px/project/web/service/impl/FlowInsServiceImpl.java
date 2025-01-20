package com.px.project.web.service.impl;

import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IFlowInsMapper;
import com.px.project.web.dto.FlowInsDto;
import com.px.project.web.entity.FlowInsModel;
import com.px.project.web.service.IFlowInsService;
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
 * @ClassName FlowInsServiceImpl
 * @Description FlowInsServiceImpl
 * @author 品讯科技
 * @time   2021-03-16 08:49:10
 * @version V 1.0
 */
@Service
public class FlowInsServiceImpl extends BaseService implements IFlowInsService<FlowInsModel> {
    @Autowired
    public IFlowInsMapper flowInsMapper;
    private static Logger logger = LoggerFactory.getLogger(FlowInsServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param flowInsModel
     * @Description 保存
     * @time 2021-03-16 08:49:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(FlowInsModel flowInsModel) {
        //更新操作人员
        flowInsModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        flowInsModel.setCreateUserCode(TokenUtil.getUserId(flowInsModel.getToken()));
        flowInsModel.setCreateUserName(TokenUtil.getUser (flowInsModel.getToken()).getString ("userName"));

        try {
            String id=UUIDUtil.get32UUID ();
            flowInsModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(flowInsModel));
            if(flowInsMapper.save(flowInsModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入FlowInsModel成功-------------");
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
     * @time 2021-03-16 08:49:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(flowInsMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-16 08:49:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(flowInsMapper.logicDeleteByIds(ids) < 1) {
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
     * @param flowInsModel
     * @Description 修改
     * @time 2021-03-16 08:49:10
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(FlowInsModel flowInsModel) {
        //更新操作人员
        flowInsModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        flowInsModel.setUpdateUserCode(TokenUtil.getUserId(flowInsModel.getToken()));
        flowInsModel.setCreateUserName(TokenUtil.getUser (flowInsModel.getToken()).getString ("userName"));
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(flowInsModel));
        try {
            if(flowInsMapper.update(flowInsModel) < 1) {
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
    * @time 2021-03-16 08:49:10
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, flowInsMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param flowInsDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-16 08:49:10
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(FlowInsDto flowInsDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(flowInsDto));
        try {
            page(flowInsDto);//分页判断
            return getResult(ReturnMsgEnum.OK, flowInsMapper.findAll(flowInsDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result approveList(String busId) {
        logger.info ("入参:"+busId);
        try {
            return getResult(ReturnMsgEnum.OK, flowInsMapper.approveList(busId));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

}
