package com.px.project.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.enums.WorkflowEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.common.utils.*;
import com.px.project.web.dao.ICheckMapper;
import com.px.project.web.dto.CheckDto;
import com.px.project.web.dto.WorkflowDto;
import com.px.project.web.entity.CheckDetailModel;
import com.px.project.web.entity.CheckModel;
import com.px.project.web.service.ICheckDetailService;
import com.px.project.web.service.ICheckService;
import com.px.project.web.service.IWorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package com.px.project.web.service.impl
 * @ClassName CheckServiceImpl
 * @Description CheckServiceImpl
 * @author 品讯科技
 * @time   2021-03-19 22:53:23
 * @version V 1.0
 */
@Service
public class CheckServiceImpl extends BaseService implements ICheckService<CheckModel> {
    @Autowired
    public ICheckMapper checkMapper;
    @Autowired
    public ICheckDetailService checkDetailService;
    @Autowired
    private IWorkflowService workflowService;

    private static Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param checkModel
     * @Description 保存
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(CheckModel checkModel) {
        //更新操作人员
        checkModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        checkModel.setCreateUserCode(TokenUtil.getUserId(checkModel.getToken()));
        checkModel.setCreateUserName(TokenUtil.getUser(checkModel.getToken()).getString("userName"));

        String id = UUIDUtil.get32UUID();
        checkModel.setId(id);
        long genOrderNo = new IDNumberUtil(1, 12).nextId();
        checkModel.setCheckNo(Long.toString(genOrderNo));
//        checkModel.setApproveStatus("0"); //待审核
        //保存明细
        for(CheckDetailModel detail :checkModel.getCheckDetail()){
            detail.setToken(checkModel.getToken());
            detail.setCheckId(checkModel.getId());
            checkDetailService.save(detail);
        }

        logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(checkModel));
        if (checkMapper.save(checkModel) < 1) {
            return getResult(ReturnMsgEnum.Save_Failed);
        }
        if("0".equals(checkModel.getApproveStatus())) {
        	//提交工作流
            WorkflowDto wf = new WorkflowDto();
            wf.setToken(checkModel.getToken());
            wf.setBusId(checkModel.getId());
            wf.setWorkflowEnum(WorkflowEnum.Workflow_OrderCheck);
            wf.setRemarks("订货单流程提交审核。");
            wf.setProjectId(checkModel.getProjectId());
			wf.setMajorId(checkModel.getMajorId());
//			wf.setBusNo(checkModel.get);
            boolean wfResult = workflowService.submitWorkflow(wf);
            if (!wfResult) {
                throw new RequestException(ReturnMsgEnum.Commit_FAIL.getMsg(), ReturnMsgEnum.Commit_FAIL);
            }
        }
        
        logger.info("-------------插入CheckModel成功-------------");
        return getResult(ReturnMsgEnum.Save_Success, id);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkMapper.deleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
            checkMapper.deleteDetailByNoCheckIds();
			workflowService.deleteByBusIds(ids);
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
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkMapper.deleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
			workflowService.deleteByBusIds(ids);
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
     * @param checkModel
     * @Description 修改
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(CheckModel checkModel) {
        //更新操作人员
        checkModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        checkModel.setUpdateUserCode(TokenUtil.getUserId(checkModel.getToken()));
        checkModel.setCreateUserName(TokenUtil.getUser (checkModel.getToken()).getString ("userName"));
        logger.info("修改实体对象数据：" + JsonUtil.entityConvertToJson(checkModel));

        //原始明细数据
        List<JSONObject> oldDetails = checkDetailService.checkDetailList(checkModel.getId());

        //原始数据不在本次数据内的，进行逻辑删除
        for (JSONObject detail : oldDetails) {
            if (checkModel.getCheckDetail().stream().filter(x -> x.getId() != null && x.getId().equals(detail.getString("id"))).count() <= 0) {
                checkDetailService.logicDeleteByIds(detail.getString("id"));
                continue;
            }
        }
        //更新明细表
        for (CheckDetailModel detail : checkModel.getCheckDetail()) {
            detail.setToken(checkModel.getToken());

            //id为空的新增明细
            if (StringUtil.isBlank(detail.getId())) {
                detail.setCheckId(checkModel.getId());
                checkDetailService.save(detail);
                continue;
            } else {
                //修改明细
                checkDetailService.update(detail);
            }
        }

        if (checkMapper.update(checkModel) < 1) {
            return getResult(ReturnMsgEnum.Update_Failure);
        }
		 if(checkModel.getApproveStatus().equals("-1")) {
				workflowService.deleteByBusIds(checkModel.getId());
			}
		 
        if("0".equals(checkModel.getApproveStatus())) {
        	//提交工作流
            WorkflowDto wf = new WorkflowDto();
            wf.setToken(checkModel.getToken());
            wf.setBusId(checkModel.getId());
            wf.setWorkflowEnum(WorkflowEnum.Workflow_OrderCheck);
            wf.setRemarks("订货单流程提交审核。");
            wf.setProjectId(checkModel.getProjectId());

			wf.setMajorId(checkModel.getMajorId());
//			wf.setBusNo(checkModel.getHandNo());
            if(!workflowService.checkIsExit(wf) ) {
				boolean wfResult = workflowService.submitWorkflow(wf);
				if (!wfResult) {
					throw new RequestException(ReturnMsgEnum.Commit_FAIL.getMsg(), ReturnMsgEnum.Commit_FAIL);
				}
			}
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2021-03-19 22:53:23
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            JSONObject objResult = checkMapper.findById(id);
            if (objResult != null) {
                List<JSONObject> objDetails = checkDetailService.checkDetailList(id);
                objResult.put("checkDetail", objDetails);
            }
            return getResult(ReturnMsgEnum.OK, objResult);
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
    @Override
    public Result findOnlyById(String id) {
    	logger.info ("id:"+id);
    	try {
    		JSONObject objResult = checkMapper.findById(id);
    		return getResult(ReturnMsgEnum.OK, objResult);
    	}catch (Exception e) {
    		logger.info ("查询失败");
    		logger.info (e.getMessage ());
    		return getResult(ReturnMsgEnum.Internal_Server_Error);
    	}
    }
    @Override
    public Result findPrintDetail(CheckDto checkDto) {
    	String id = checkDto.getId();
    	logger.info ("id:"+id);
    	try {
    		JSONObject objResult = checkMapper.findById(id);
    		
    		//获取点验入库数量
    		//获取发料数量
    		List<JSONObject> listInJson = checkMapper.findIn(checkDto);
    		  Map<String, BigDecimal> inMap = listInJson.stream()
    	                .collect(Collectors.toMap(
    	                        json -> json.getString("goodId"), // Map的键是JSONObject的某个字段值
    	                        json -> new BigDecimal(json.getString("checkNum")) // Map的值是整个JSONObject
    	                ));
    		List<JSONObject> listOutJson = checkMapper.findOut(checkDto);
    		Map<String, BigDecimal> outMap = listOutJson.stream()
	                .collect(Collectors.toMap(
	                        json -> json.getString("goodId"), // Map的键是JSONObject的某个字段值
	                        json -> new BigDecimal(json.getString("sendNum")) // Map的值是整个JSONObject
	                ));
    		
            if (objResult != null) {
                List<JSONObject> objDetails = checkDetailService.checkDetailList(id);
                for (JSONObject jsonObject : objDetails) {
					String goodId = jsonObject.getString("goodId");
					jsonObject.put("outNum", outMap.get(goodId)==null? "0":outMap.get(goodId) );
					jsonObject.put("inNum", inMap.get(goodId)==null? "0":inMap.get(goodId) );
				}
                objResult.put("checkDetail", objDetails);
            }
    		
    		return getResult(ReturnMsgEnum.OK, objResult);
    	}catch (Exception e) {
    		logger.info ("查询失败");
    		logger.info (e.getMessage ());
    		return getResult(ReturnMsgEnum.Internal_Server_Error);
    	}
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param checkDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-03-19 22:53:23
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(CheckDto checkDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(checkDto));
        try {
            page(checkDto);//分页判断
            return getResult(ReturnMsgEnum.OK, checkMapper.findAll(checkDto));
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
     * @name checkStatusList
     * @author 品讯科技
     * @Description 盘点单（物资状态）查看
     * @time 2021-03-26 21:24:31
     */
    @Override
    public Result checkStatusList(String id) {
        logger.info("id:" + id);
        try {
            JSONObject objResult = checkMapper.findById(id);
            if (objResult != null) {
                String projectId = objResult.getString("projectId");
                if (objResult != null) {
                    List<JSONObject> objDetails = checkDetailService.checkStatusDetail(projectId, id);
                    objResult.put("checkDetail", objDetails);

                    JSONObject objSum = checkDetailService.checkStatusDetailSum(projectId, id);
                    //物资数量
                    objResult.put("stockSum", objSum.getString("stockNum"));
                    //盘点数量
                    objResult.put("checkSum", objSum.getString("checkNum"));
                }
            }
            return getResult(ReturnMsgEnum.OK, objResult);
        } catch (Exception e) {
            logger.info("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
