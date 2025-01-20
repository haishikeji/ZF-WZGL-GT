package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IGoodCheckMapper;
import com.px.project.web.dto.GoodCheckDto;
import com.px.project.web.entity.GoodCheckModel;
import com.px.project.web.service.IGoodCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Package com.px.project.web.service.impl
 * @ClassName GoodCheckServiceImpl
 * @Description GoodCheckServiceImpl
 * @author 品讯科技
 * @time   2021-03-19 23:03:56
 * @version V 1.0
 */
@Service
public class GoodCheckServiceImpl extends BaseService implements IGoodCheckService<GoodCheckModel> {
    @Autowired
    public IGoodCheckMapper goodCheckMapper;
    private static Logger logger = LoggerFactory.getLogger(GoodCheckServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param goodCheckModel
     * @Description 保存
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(GoodCheckModel goodCheckModel) {
        //更新操作人员
        goodCheckModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        goodCheckModel.setCreateUserCode(TokenUtil.getUserId(goodCheckModel.getToken()));
        goodCheckModel.setCreateUserName(TokenUtil.getUser (goodCheckModel.getToken()).getString ("userName"));

        try {
            String id = UUIDUtil.get32UUID();
            goodCheckModel.setId(id);
            goodCheckModel.setApproveStatus("0"); //待审核
            logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(goodCheckModel));
            if (goodCheckMapper.save(goodCheckModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info("-------------插入GoodCheckModel成功-------------");
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info("插入失败");
            logger.info(e.toString());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(goodCheckMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(goodCheckMapper.logicDeleteByIds(ids) < 1) {
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
     * @param goodCheckModel
     * @Description 修改
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(GoodCheckModel goodCheckModel) {
        //更新操作人员
        goodCheckModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        goodCheckModel.setUpdateUserCode(TokenUtil.getUserId(goodCheckModel.getToken()));
        goodCheckModel.setCreateUserName(TokenUtil.getUser (goodCheckModel.getToken()).getString ("userName"));
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(goodCheckModel));
        try {
            if(goodCheckMapper.update(goodCheckModel) < 1) {
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
    * @param goodId
    * @Description 根据条件查询所有数据
    * @time 2021-03-19 23:03:56
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String goodId) {
        logger.info("id:" + goodId);
        try {
            JSONObject objResult = goodCheckMapper.findById(goodId);
            if (objResult != null) {
                HashMap<String, String> map = new HashMap<>();
                map.put("goodId", goodId);
                List<JSONObject> objDetails = goodCheckMapper.findLogById(map);
                objDetails.forEach(x -> x.put("testUnit", objResult.getString("testUnit")));//检测单位，与父级一致
                objResult.put("logDetail", objDetails);
            }
            return getResult(ReturnMsgEnum.OK, objResult);
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param goodCheckDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 23:03:56
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(GoodCheckDto goodCheckDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(goodCheckDto));
        try {
            page(goodCheckDto);//分页判断
            return getResult(ReturnMsgEnum.OK, goodCheckMapper.findAll(goodCheckDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @param goodCheckDto
     * @return Result
     * @throws
     * @name goodCheckList
     * @author 品讯科技
     * @Description 物资检测管理查询
     * @time 2021-03-28 00:07:10
     */
    @Override
    public Result goodCheckList(GoodCheckDto goodCheckDto) {
        logger.info ("入参:"+JsonUtil.entityConvertToJson(goodCheckDto));
        try {
            page(goodCheckDto);//分页判断
            return getResult(ReturnMsgEnum.OK, goodCheckMapper.goodCheckList(goodCheckDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @param id
     * @return Result
     * @throws
     * @name findLogById
     * @author 品讯科技
     * @Description 查看日志明细
     * @time 2021-03-31 20:49:54
     */
    @Override
    public Result findLogById(String id) {
        logger.info("id:" + id);
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", id);
            return getResult(ReturnMsgEnum.OK, goodCheckMapper.findLogById(map));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
