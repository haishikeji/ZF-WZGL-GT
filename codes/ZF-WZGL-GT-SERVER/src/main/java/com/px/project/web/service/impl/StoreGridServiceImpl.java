package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.common.utils.ExcelColumnUtil;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IStoreGridMapper;
import com.px.project.web.dto.StoreGridDto;
import com.px.project.web.entity.StoreGridModel;
import com.px.project.web.service.IStoreGridService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package com.px.project.web.service.impl
 * @ClassName StoreGridServiceImpl
 * @Description StoreGridServiceImpl
 * @author 品讯科技
 * @time   2021-03-04 22:43:20
 * @version V 1.0
 */
@Service
public class StoreGridServiceImpl extends BaseService implements IStoreGridService<StoreGridModel> {
    @Autowired
    public IStoreGridMapper storeGridMapper;
    private static Logger logger = LoggerFactory.getLogger(StoreGridServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param storeGridModel
     * @Description 保存
     * @time 2021-03-04 22:43:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(StoreGridModel storeGridModel) {
        //更新操作人员
        storeGridModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        storeGridModel.setCreateUserCode(TokenUtil.getUserId(storeGridModel.getToken()));
        storeGridModel.setCreateUserName(TokenUtil.getUserLoginName(storeGridModel.getToken()));

        String id = UUIDUtil.get32UUID();
        storeGridModel.setId(id);
        storeGridModel.setStatus("1");//1启用，0停用
        logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(storeGridModel));

        if (storeGridMapper.save(storeGridModel) < 1) {
            return getResult(ReturnMsgEnum.Save_Failed);
        }
        logger.info("-------------插入StoreGridModel成功-------------");
        return getResult(ReturnMsgEnum.Save_Success, id);
    }

    /**
     * @param storeGridDto
     * @return Result
     * @throws
     * @name initStoreGrid
     * @author 品讯科技
     * @Description 初始化仓库表格，根据行数量和列数量自动创建明细
     * @time 2021-03-06 21:08:31
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result initStoreGrid(StoreGridDto storeGridDto) {
        //验证是否初始化过
        List<JSONObject> grids = storeGridMapper.findAll(new StoreGridDto() {
            @Override
            public String getStoreId() {
                return storeGridDto.getStoreId();
            }
        });
        if (grids.size() > 0) {
            throw new RequestException(ReturnMsgEnum.Repeat_Data.getMsg(), ReturnMsgEnum.Repeat_Data);
        }
        //初始化表格
        int rowCount = storeGridDto.getGirdRowsCount();
        String colCountABC = storeGridDto.getGirdColumnsCount();
        int colCount = ExcelColumnUtil.excelColStrToNum(colCountABC, colCountABC.trim().length());
        //遍历创建明细，从1开始
        for (int iRow = 1; iRow <= rowCount; iRow++) {
            for (int iCol = 1; iCol <= colCount; iCol++) {
                StoreGridModel model = new StoreGridModel();
                model.setToken(storeGridDto.getToken());
                model.setStoreId(storeGridDto.getStoreId());
                String colStr = ExcelColumnUtil.excelColIndexToStr(iCol);
                model.setGridColumnNo(colStr);
                model.setGridRowNo(String.valueOf(iRow));
                model.setGridNo(model.getGridColumnNo() + model.getGridRowNo()); //格式：A1，B2
                model.setGridName(model.getGridNo()); //默认名字跟编号一样
                this.save(model);
            }
        }
        return getResult(ReturnMsgEnum.Save_Success);
    }

    /**
     * @param storeGridDto
     * @return Result
     * @throws
     * @name moreStoreGrid
     * @author 品讯科技
     * @Description 增加网格行和列，根据行数量和列数量自动创建明细
     * @time 2021-04-06 21:41:21 （重新开发）
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result moreStoreGrid(StoreGridDto storeGridDto) {
        //增加（到）的表格行和列数量
        int rowCount = storeGridDto.getGirdRowsCount();
        String colCountABC = storeGridDto.getGirdColumnsCount();
        int colCount = ExcelColumnUtil.excelColStrToNum(colCountABC, colCountABC.trim().length());
        //当前仓库最大网格边缘
        StoreGridModel modelMaxGrid = storeGridMapper.findMaxGrid(storeGridDto.getStoreId());
        int storeRow = Integer.parseInt(modelMaxGrid.getGridRowNo());
        int storeCol = ExcelColumnUtil.excelColStrToNum(modelMaxGrid.getGridColumnNo(), modelMaxGrid.getGridColumnNo().trim().length());
        //获取全部数据最大的边界
        /*int maxCol = storeCol > colCount ? storeCol : colCount;
        int maxRow = storeRow > rowCount ? storeRow : colCount;*/

        //格式Address：”列-行“
        List<String> paramGridList = new ArrayList<>();
        List<String> oldGridList = new ArrayList<>();

        //遍历入参表格
        for (int iCol = 1; iCol <= colCount; iCol++) {
            for (int iRow = 1; iRow <= rowCount; iRow++) {
                paramGridList.add(iCol + "-" + iRow);
            }
        }

        //遍历原始数据
        for (int oldCol = 1; oldCol <= storeCol; oldCol++) {
            for (int oldRow = 1; oldRow <= storeRow; oldRow++) {
                oldGridList.add(oldCol + "-" + oldRow);
            }
        }
        //容差判断
        for (String address : paramGridList) {
            if (!oldGridList.contains(address)) {
                //新增
                String[] gridAddress = address.split("-");
                StoreGridModel model = new StoreGridModel();
                model.setToken(storeGridDto.getToken());
                model.setStoreId(storeGridDto.getStoreId());
                String colAbc = ExcelColumnUtil.excelColIndexToStr(Integer.parseInt(gridAddress[0]));
                model.setGridColumnNo(colAbc);
                model.setGridRowNo(gridAddress[1]);
                //格式：A1，B2
                model.setGridNo(model.getGridColumnNo() + model.getGridRowNo());
                this.save(model);
            }
        }

        for (String address : oldGridList) {
            if (!paramGridList.contains(address)) {
                //删除
                String[] gridAddress = address.split("-");
                String colAbc = ExcelColumnUtil.excelColIndexToStr(Integer.parseInt(gridAddress[0]));
                this.logicDeleteByColRow(storeGridDto.getStoreId(), colAbc, gridAddress[1]);
            }
        }

        return getResult(ReturnMsgEnum.Save_Success);
    }

    /**
     * @param storeId
     * @return Result
     * @throws
     * @name gridCount
     * @author 品讯科技
     * @Description 根据仓库id查询 库位网格有多少行列 （最大行，最大列）
     * @time 2021-03-28 22:54:12
     */
    @Override
    public Result gridCount(String storeId) {
        try {
            int maxRow = 0, maxColNum = 0;
            String maxColABC = null;

            JSONObject obj = new JSONObject();
            StoreGridModel model = storeGridMapper.findMaxGrid(storeId);
            if (model != null) {
                //数据扩展起点
                maxColNum = ExcelColumnUtil.excelColStrToNum(model.getGridColumnNo(), model.getGridColumnNo().trim().length());
                maxRow = Integer.parseInt(model.getGridRowNo());

                maxColABC = model.getGridColumnNo();
            }

            obj.put("rowCount", maxRow);
            obj.put("columnCount", maxColNum);
            obj.put("columnABC", maxColABC);

            return getResult(ReturnMsgEnum.OK, obj);
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
    * @name update
    * @author 品讯科技
    * @param storeGridModel
    * @Description 修改
    * @time 2021-03-04 22:43:20
    * @throws
    * @return Result
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(StoreGridModel storeGridModel) {
        //更新操作人员
        storeGridModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        storeGridModel.setUpdateUserCode(TokenUtil.getUserId(storeGridModel.getToken()));
        storeGridModel.setUpdateUserName(TokenUtil.getUserLoginName(storeGridModel.getToken()));

        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(storeGridModel));
        try {
            if(storeGridMapper.update(storeGridModel) < 1) {
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
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-04 22:43:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(storeGridMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-03-04 22:43:20
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(storeGridMapper.logicDeleteByIds(ids) < 1) {
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
     * @name logicDeleteByColRow
     * @author 品讯科技
     * @Description 根据单元格地址逻辑删除
     * @time 2021-04-06 15:53:25
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByColRow(String storeId, String colNo, String rowNo) {
        logger.info("--------------根据单元格地址逻辑删除数据----------");
        logger.info("storeId ：" + storeId + ",rowNo:" + rowNo + ",colNo:" + colNo);
        try {
            if (storeGridMapper.logicDeleteByColRow(storeId, colNo, rowNo) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.info("删除失败");
            logger.info(e.toString());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        logger.info("-------------逻辑删除成功-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2021-03-04 22:43:20
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, storeGridMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param storeGridDto
     * @Description 根据仓库id查询全部表格
     * @time 2021-03-06 21:45:08
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(StoreGridDto storeGridDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(storeGridDto));
        try {
            page(storeGridDto);//分页判断
            return getResult(ReturnMsgEnum.OK, storeGridMapper.findAll(storeGridDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
