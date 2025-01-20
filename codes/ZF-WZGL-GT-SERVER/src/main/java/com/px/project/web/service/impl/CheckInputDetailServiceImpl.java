package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.ICheckInputDetailMapper;
import com.px.project.web.dto.CheckInputDetailDto;
import com.px.project.web.entity.CheckInputDetailModel;
import com.px.project.web.service.ICheckInputDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Package com.px.project.web.service.impl
 * @ClassName CheckInputDetailServiceImpl
 * @Description CheckInputDetailServiceImpl
 * @author 品讯科技
 * @time   2021-03-16 23:35:17
 * @version V 1.0
 */
@Service
public class CheckInputDetailServiceImpl extends BaseService implements ICheckInputDetailService<CheckInputDetailModel> {
    @Autowired
    public ICheckInputDetailMapper checkInputDetailMapper;
    private static Logger logger = LoggerFactory.getLogger(CheckInputDetailServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param checkInputDetailModel
     * @Description 保存
     * @time 2021-03-16 23:35:17
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(CheckInputDetailModel checkInputDetailModel) {
        //更新操作人员
        checkInputDetailModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        checkInputDetailModel.setCreateUserCode(TokenUtil.getUserId(checkInputDetailModel.getToken()));
        checkInputDetailModel.setCreateUserName(TokenUtil.getUser(checkInputDetailModel.getToken()).getString("userName"));

        String id = UUIDUtil.get32UUID();
        checkInputDetailModel.setId(id);
        logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(checkInputDetailModel));
        if (checkInputDetailMapper.save(checkInputDetailModel) < 1) {
            return getResult(ReturnMsgEnum.Save_Failed);
        }
        logger.info("-------------插入CheckInputDetailModel成功-------------");
        return getResult(ReturnMsgEnum.Save_Success, id);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-16 23:35:17
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkInputDetailMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-16 23:35:17
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info("--------------根据ids逻辑删除数据----------");
        logger.info("ids：" + ids);

        if (checkInputDetailMapper.logicDeleteByIds(ids) < 1) {
            return getResult(ReturnMsgEnum.Delete_Failure);
        }
        logger.info("-------------逻辑删除成功-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param checkInputDetailModel
     * @Description 修改
     * @time 2021-03-16 23:35:17
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(CheckInputDetailModel checkInputDetailModel) {
        //更新操作人员
        checkInputDetailModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        checkInputDetailModel.setUpdateUserCode(TokenUtil.getUserId(checkInputDetailModel.getToken()));
        checkInputDetailModel.setCreateUserName(TokenUtil.getUser(checkInputDetailModel.getToken()).getString("userName"));
        logger.info("修改实体对象数据：" + JsonUtil.entityConvertToJson(checkInputDetailModel));

        if (checkInputDetailMapper.update(checkInputDetailModel) < 1) {
            return getResult(ReturnMsgEnum.Update_Failure);
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2021-03-16 23:35:17
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, checkInputDetailMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param checkInputDetailDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-16 23:35:17
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(CheckInputDetailDto checkInputDetailDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(checkInputDetailDto));
        try {
            page(checkInputDetailDto);//分页判断
            return getResult(ReturnMsgEnum.OK, checkInputDetailMapper.findAll(checkInputDetailDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @param checkInputId
     * @return Result
     * @throws
     * @name checkInputDetailList
     * @author 品讯科技
     * @Description 根据主键id获取子表明细
     * @time 2021-03-22 22:22:09
     */
    @Override
    public List<JSONObject> checkInputDetailList(String checkInputId) {
        logger.info("入参:" + checkInputId);
        List<JSONObject> details = checkInputDetailMapper.findAll(new CheckInputDetailDto() {
            @Override
            public String getCheckInputId() {
                return checkInputId;
            }
        });
        return details;
    }
}
