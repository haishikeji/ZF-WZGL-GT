package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.utils.IDNumberUtil;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IStockOutputMapper;
import com.px.project.web.dto.StockOutputDto;
import com.px.project.web.entity.StockOutputModel;
import com.px.project.web.service.IStockOutputService;
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
 * @ClassName StockOutputServiceImpl
 * @Description StockOutputServiceImpl
 * @author 品讯科技
 * @time   2021-03-16 13:16:06
 * @version V 1.0
 */
@Service
public class StockOutputServiceImpl extends BaseService implements IStockOutputService<StockOutputModel> {
    @Autowired
    public IStockOutputMapper stockOutputMapper;
    private static Logger logger = LoggerFactory.getLogger(StockOutputServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param stockOutputModel
     * @Description 保存
     * @time 2021-03-16 13:16:06
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(StockOutputModel stockOutputModel) {
        //更新操作人员
        stockOutputModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stockOutputModel.setCreateUserCode(TokenUtil.getUserId(stockOutputModel.getToken()));
        stockOutputModel.setCreateUserName(TokenUtil.getUser(stockOutputModel.getToken()).getString("userName"));

        String id = UUIDUtil.get32UUID();
        stockOutputModel.setId(id);
        long genOrderNo = new IDNumberUtil(1, 12).nextId();
        stockOutputModel.setOutputNo(Long.toString(genOrderNo));

        logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(stockOutputModel));
        if (stockOutputMapper.save(stockOutputModel) < 1) {
            return getResult(ReturnMsgEnum.Save_Failed);
        }
        logger.info("-------------插入StockOutputModel成功-------------");
        return getResult(ReturnMsgEnum.Save_Success, id);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-16 13:16:06
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(stockOutputMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-16 13:16:06
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(stockOutputMapper.logicDeleteByIds(ids) < 1) {
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
     * @param stockOutputModel
     * @Description 修改
     * @time 2021-03-16 13:16:06
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(StockOutputModel stockOutputModel) {
        //更新操作人员
        stockOutputModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stockOutputModel.setUpdateUserCode(TokenUtil.getUserId(stockOutputModel.getToken()));
        stockOutputModel.setCreateUserName(TokenUtil.getUser(stockOutputModel.getToken()).getString("userName"));
        logger.info("修改实体对象数据：" + JsonUtil.entityConvertToJson(stockOutputModel));

        if (stockOutputMapper.update(stockOutputModel) < 1) {
            return getResult(ReturnMsgEnum.Update_Failure);
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2021-03-16 13:16:06
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, stockOutputMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockOutputDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-16 13:16:06
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(StockOutputDto stockOutputDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(stockOutputDto));
        try {
            page(stockOutputDto);//分页判断
            return getResult(ReturnMsgEnum.OK, stockOutputMapper.findAll(stockOutputDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @param goodId
     * @return Result
     * @throws
     * @name getLastTime
     * @author 品讯科技
     * @Description 获取最后出库时间
     * @time 2021-04-05 20:09:37
     */
    @Override
    public String getLastTime(String goodId) {
        return stockOutputMapper.getLastTime(goodId);
    }

    /**
     * @param stockOutputDto
     * @return Result
     * @throws
     * @name sumStockOutNum
     * @author 品讯科技
     * @Description 物资出库数量
     * @time 2021-04-10 20:55:56
     */
    @Override
    public List<JSONObject> sumStockOutNum(StockOutputDto stockOutputDto) {
        return stockOutputMapper.sumStockOutNum(stockOutputDto);
    }
}
