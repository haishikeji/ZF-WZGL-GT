package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IAllotDetailMapper;
import com.px.project.web.dto.AllotDetailDto;
import com.px.project.web.entity.AllotDetailModel;
import com.px.project.web.service.IAllotDetailService;
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
 * @ClassName AllotDetailServiceImpl
 * @Description AllotDetailServiceImpl
 * @author 品讯科技
 * @time   2021-03-19 21:57:34
 * @version V 1.0
 */
@Service
public class AllotDetailServiceImpl extends BaseService implements IAllotDetailService<AllotDetailModel> {
    @Autowired
    public IAllotDetailMapper allotDetailMapper;
    private static Logger logger = LoggerFactory.getLogger(AllotDetailServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param allotDetailModel
     * @Description 保存
     * @time 2021-03-19 21:57:34
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(AllotDetailModel allotDetailModel) {
        //更新操作人员
        allotDetailModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        allotDetailModel.setCreateUserCode(TokenUtil.getUserId(allotDetailModel.getToken()));
        allotDetailModel.setCreateUserName(TokenUtil.getUser (allotDetailModel.getToken()).getString ("userName"));

        String id = UUIDUtil.get32UUID();
        allotDetailModel.setId(id);

        logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(allotDetailModel));
        if (allotDetailMapper.save(allotDetailModel) < 1) {
            return getResult(ReturnMsgEnum.Save_Failed);
        }
        logger.info("-------------插入AllotDetailModel成功-------------");
        return getResult(ReturnMsgEnum.Save_Success, id);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-19 21:57:34
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(allotDetailMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-19 21:57:34
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info("--------------根据ids逻辑删除数据----------");
        logger.info("ids：" + ids);

        if (allotDetailMapper.logicDeleteByIds(ids) < 1) {
            return getResult(ReturnMsgEnum.Delete_Failure);
        }
        logger.info("-------------逻辑删除成功-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param allotDetailModel
     * @Description 修改
     * @time 2021-03-19 21:57:34
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(AllotDetailModel allotDetailModel) {
        //更新操作人员
        allotDetailModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        allotDetailModel.setUpdateUserCode(TokenUtil.getUserId(allotDetailModel.getToken()));
        allotDetailModel.setCreateUserName(TokenUtil.getUser(allotDetailModel.getToken()).getString("userName"));
        logger.info("修改实体对象数据：" + JsonUtil.entityConvertToJson(allotDetailModel));

        if (allotDetailMapper.update(allotDetailModel) < 1) {
            return getResult(ReturnMsgEnum.Update_Failure);
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2021-03-19 21:57:34
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, allotDetailMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param allotDetailDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 21:57:34
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(AllotDetailDto allotDetailDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(allotDetailDto));
        try {
            page(allotDetailDto);//分页判断
            return getResult(ReturnMsgEnum.OK, allotDetailMapper.findAll(allotDetailDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @param allotId
     * @return Result
     * @throws
     * @name allotDetailList
     * @author 品讯科技
     * @Description 根据id查询子表明细
     * @time 2021-03-23 23:29:57
     */
    @Override
    public List<JSONObject> allotDetailList(String allotId) {
        logger.info("入参:" + allotId);
        List<JSONObject> details = allotDetailMapper.findAll(new AllotDetailDto() {
            @Override
            public String getAllotId() {
                return allotId;
            }
        });
        return details;
    }
}
