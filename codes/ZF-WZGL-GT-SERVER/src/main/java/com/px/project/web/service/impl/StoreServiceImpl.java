package com.px.project.web.service.impl;

import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IStoreMapper;
import com.px.project.web.dto.StoreDto;
import com.px.project.web.entity.StoreModel;
import com.px.project.web.service.IStoreService;
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
 * @ClassName StoreServiceImpl
 * @Description StoreServiceImpl
 * @author 品讯科技
 * @time   2021-03-02 22:42:20
 * @version V 1.0
 */
@Service
public class StoreServiceImpl extends BaseService  implements IStoreService<StoreModel> {
    @Autowired
    public IStoreMapper storeMapper;
    private static Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param storeModel
     * @Description 保存
     * @time 2021-03-02 22:42:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(StoreModel storeModel) {
        //更新操作人员
        storeModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        storeModel.setCreateUserCode(TokenUtil.getUserId(storeModel.getToken()));
        storeModel.setCreateUserName(TokenUtil.getUserLoginName(storeModel.getToken()));

        try {
            String id=UUIDUtil.get32UUID ();
            storeModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(storeModel));
            if(storeMapper.save(storeModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入StoreModel成功-------------");
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
     * @time 2021-03-02 22:42:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(storeMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-02 22:42:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(storeMapper.logicDeleteByIds(ids) < 1) {
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
     * @param storeModel
     * @Description 修改
     * @time 2021-03-02 22:42:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(StoreModel storeModel) {
        //更新操作人员
        storeModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        storeModel.setUpdateUserCode(TokenUtil.getUserId(storeModel.getToken()));
        storeModel.setUpdateUserName(TokenUtil.getUserLoginName(storeModel.getToken()));

        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(storeModel));
        try {
            if(storeMapper.update(storeModel) < 1) {
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
    * @time 2021-03-02 22:42:20
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, storeMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param storeDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-02 22:42:20
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(StoreDto storeDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(storeDto));
        try {
            page(storeDto);//分页判断
            return getResult(ReturnMsgEnum.OK, storeMapper.findAll(storeDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
