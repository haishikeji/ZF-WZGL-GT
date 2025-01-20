package com.px.project.web.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.enums.WorkflowEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.StringUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IGoodMapper;
import com.px.project.web.dao.ILimitDetailMapper;
import com.px.project.web.dao.ILimitMapper;
import com.px.project.web.dao.IStationIntervalMapper;
import com.px.project.web.dao.IStationScheduleMapper;
import com.px.project.web.dao.IThreeEstimateMapper;
import com.px.project.web.dto.StationIntervalDto;
import com.px.project.web.dto.ThreeEstimateDto;
import com.px.project.web.dto.WorkflowDto;
import com.px.project.web.entity.LimitDetailModel;
import com.px.project.web.entity.LimitModel;
import com.px.project.web.entity.OrderGoodDetailModel;
import com.px.project.web.entity.PlanGoodModel;
import com.px.project.web.entity.StationIntervalModel;
import com.px.project.web.entity.StationScheduleModel;
import com.px.project.web.entity.ThreeEstimateApproveModel;
import com.px.project.web.entity.ThreeEstimateModel;
import com.px.project.web.service.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 品讯科技
 * @version V 1.0
 * @Package com.px.project.web.service.impl
 * @ClassName threeEstimateServiceImpl
 * @Description threeEstimateServiceImpl
 * @time 2021-03-09 22:12:47
 */
@Service
public class ThreeEstimateServiceImpl extends BaseService implements IThreeEstimateService<ThreeEstimateModel> {
	@Autowired
	public IThreeEstimateMapper threeEstimateMapper;

	@Autowired
	public IBudgetUnitService budgetUnitService;

	@Autowired
	public IStationScheduleService stationScheduleService;

	@Autowired
	private IThreeEstimateApproveService threeEstimateApproveService;

	@Autowired
	private IWorkflowService workflowService;
	@Autowired
	private IStationScheduleMapper stationScheduleMapper;
	@Autowired
	private IStationIntervalMapper stationIntervalMapper;
	@Autowired
	private ILimitService limitService;
	@Autowired
	private ILimitDetailMapper limitDetailMapper;
	@Autowired
	private ILimitMapper limitMapper;
	@Autowired
	private IGoodMapper goodMapper;

	private static Logger logger = LoggerFactory.getLogger(ThreeEstimateServiceImpl.class);

	/**
	 * @param threeEstimateModel
	 * @return Result
	 * @throws @name save
	 * @author 品讯科技
	 * @Description 保存
	 * @time 2021-03-09 22:12:47
	 */

	private void dealLimit(List<StationScheduleModel> models, ThreeEstimateModel threeEstimateModel) {
		String userId = TokenUtil.getUserId(threeEstimateModel.getToken());
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String projectId = threeEstimateModel.getProjectId();
		List<String> stationIntervaIdList = models.stream().map(StationScheduleModel::getStationScheduleId)
				.collect(Collectors.toList());
		// 物资，专业，站区间
		List<LimitModel> listLimit = limitMapper.findAllByMajorAndStationIntervaIds(
				StringUtil.getMerageStr(stationIntervaIdList), threeEstimateModel.getMajorId());
		List<String> exitsStationInterva = listLimit.stream().map(LimitModel::getStationIntervalId)
				.collect(Collectors.toList());
		// 判断专业 站区间 数据是否存在
		List<String> needSaveInfo = stationIntervaIdList.stream().filter(s -> !exitsStationInterva.contains(s))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(needSaveInfo)) {
			List<LimitModel> limitList = new ArrayList<LimitModel>();
			for (String siid : needSaveInfo) {
				LimitModel limit = new LimitModel();
				limit.setProjectId(projectId);
				limit.setCreateUserCode(userId);
				limit.setCreateTime(date);
				limit.setMajorId(threeEstimateModel.getMajorId());
				limit.setStationIntervalId(siid);
				limit.setId(UUIDUtil.get32UUID());
				limitList.add(limit);
			}
			limitMapper.batchSave(limitList);
			listLimit.addAll(limitList);
		}
		// 需要整理出一个站区间和limitid的对应关系
		Map<String, String> mapSl = listLimit.stream()
				.collect(Collectors.toMap(LimitModel::getStationIntervalId, LimitModel::getId));
		List<String> ids = listLimit.stream().map(LimitModel::getId).collect(Collectors.toList());
		// 开始获取所有的物资信息
		List<LimitDetailModel> listDetail = limitDetailMapper.findAllByLimitIdAndGoodId(StringUtil.getMerageStr(ids),
				threeEstimateModel.getGoodId());
		// 每次只有一个屋子信息和一个专业信息，归组
//		listDetail.forEach(d-> d.setStationIntervalId(mapSl.get(d.getLimitId())));
		Map<String, LimitDetailModel> mapHashData = listDetail.stream()
				.collect(Collectors.toMap(LimitDetailModel::getLimitId, a -> a));
		List<LimitDetailModel> needSave = new ArrayList<LimitDetailModel>();
		List<String> needDelete = new ArrayList<String>();
		for (StationScheduleModel ssm : models) {
//			获取limitid
			String limitId = mapSl.get(ssm.getStationScheduleId());
//			获取limitetail
			LimitDetailModel ldm = mapHashData.get(limitId);
			// 如果是删除的话无需处理
			if (ssm.getOperateType().equals("delete")) {
				if (ldm != null) {
					needDelete.add(ldm.getId());
				}
				continue;
			}
			BigDecimal limitAmount = ssm.getStationScheduleValue() == null || ssm.getStationScheduleValue() == ""
					? BigDecimal.ZERO
					: new BigDecimal(ssm.getStationScheduleValue());
			if (ldm == null) {
				LimitDetailModel saveModel = new LimitDetailModel();
				saveModel.setGoodId(threeEstimateModel.getGoodId());
				saveModel.setCreateUserCode(userId);
				saveModel.setCreateTime(date);
				saveModel.setLimitId(limitId);
				saveModel.setThreeId(ssm.getId());
				saveModel.setId(UUIDUtil.get32UUID());
				saveModel.setBalance(limitAmount);
				saveModel.setTotalLimit(limitAmount);
				needSave.add(saveModel);
			} else {
				if (ldm.getTotalLimit().compareTo(new BigDecimal(ssm.getStationScheduleValue())) == 0) {
					// 无需处理，减少数据库操作
					continue;
				}
				// 获取原来的总额 - 原来的余额 = 原来的使用量
				BigDecimal overUsed = ldm.getTotalLimit()
						.subtract((ldm.getBalance() == null ? BigDecimal.ZERO : ldm.getBalance()));
//				BigDecimal overUsed =org ==null? BigDecimal.ZERO: (org.getTotalLimit()==null?BigDecimal.ZERO:org.getTotalLimit()).subtract(
//						(org.getBalance() ==null? BigDecimal.ZERO:org.getBalance()));
				// 本次的总额 - 原来的使用两 = 本次的余额
				if (limitAmount.subtract(overUsed).compareTo(BigDecimal.ZERO) < 0) {
					ldm.setBalance(BigDecimal.ZERO);
				} else {
					ldm.setBalance(limitAmount.subtract(overUsed));
				}
				ldm.setTotalLimit(limitAmount);
				needSave.add(ldm);
				needDelete.add(ldm.getId());
			}
		}

		// 删除插入
		if (!CollectionUtils.isEmpty(needDelete)) {
			int delete = limitDetailMapper.deleteByIds(StringUtil.getMerageStr(needDelete));
			logger.info("删除限额明细数据：{}", delete);
		}

		if (!CollectionUtils.isEmpty(needSave)) {
			int save = limitDetailMapper.batchSave(needSave);
			logger.info("新增限额明细数据：{}", save);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result save(ThreeEstimateModel threeEstimateModel) {
		// 更新操作人员
		threeEstimateModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		threeEstimateModel.setCreateUserCode(TokenUtil.getUserId(threeEstimateModel.getToken()));
		threeEstimateModel.setCreateUserName(TokenUtil.getUser(threeEstimateModel.getToken()).getString("userName"));
		String id = UUIDUtil.get32UUID();
		threeEstimateModel.setId(id);

		// ThreeEstimateType: 三量类型 1 概算量 2 图纸量 3 定测量
//		if (threeEstimateModel.getThreeEstimateType().equals(WorkflowEnum.Workflow_BudgetUnit.getCode())) {
//			// 概算量，关联概算单元级联保存
//			List<BudgetUnitModel> models = threeEstimateModel.getBudgetUnitModels();
//			for (BudgetUnitModel model : models) {
//				model.setToken(threeEstimateModel.getToken());
//				model.setThreeEstimateId(id);
//				/*
//				 * //先停用之前存在的动态列，再新增(根据概算单元id，每个项目唯一）
//				 * budgetUnitService.logicDeleteByBudgetUnitId(model.getBudgetUnitId());
//				 */
//				budgetUnitService.save(model);
//			}
//		} else 
		if (threeEstimateModel.getThreeEstimateType().equals(WorkflowEnum.Workflow_BudgetDrawing.getCode())
				|| threeEstimateModel.getThreeEstimateType().equals(WorkflowEnum.Workflow_BudgetFixed.getCode())) {
			// 图纸量、定测量，关联站区间级联保存
			List<StationScheduleModel> models = threeEstimateModel.getStationScheduleModels();
			models.removeIf(s -> StringUtils.isEmpty(s.getStationScheduleValue()));// 清楚所有没设值的数据
			for (StationScheduleModel model : models) {
				model.setToken(threeEstimateModel.getToken());
				model.setThreeEstimateId(id);
				model.setStatus(StringUtils.isEmpty(model.getStatus()) ? "1" : model.getStatus());
				model.setCreateUserCode(TokenUtil.getUserId(threeEstimateModel.getToken()));
				model.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				model.setUpdateUserCode(TokenUtil.getUserId(threeEstimateModel.getToken()));
				model.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				model.setCreateUserName(TokenUtil.getUser(threeEstimateModel.getToken()).getString("userName"));
				String stationScheduleModelId = UUIDUtil.get32UUID();
				model.setId(stationScheduleModelId);
				model.setOperateType("save");
			}
			if (!CollectionUtils.isEmpty(models)) {
				if (stationScheduleMapper.batchSave(models) < 1) {
					throw new RequestException(ReturnMsgEnum.Save_Failed);
				}
				dealLimit(models, threeEstimateModel);
			}
		}

		// 添加状态标记
		ThreeEstimateApproveModel approveModel = new ThreeEstimateApproveModel();
		approveModel.setToken(threeEstimateModel.getToken());
		approveModel.setProjectId(threeEstimateModel.getProjectId()); // 三量按项目标记
		approveModel.setThreeEstimateType(threeEstimateModel.getThreeEstimateType());
		approveModel.setStatus("1"); // 待提交
		threeEstimateApproveService.upsert(approveModel);

		logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(threeEstimateModel));
		
		List<ThreeEstimateModel> list = threeEstimateMapper.findAll(new ThreeEstimateDto() {
			@Override
		    public String getMajorId() {
		    	return threeEstimateModel.getMajorId();
			}
			@Override
			public String getProjectId() {
		        return threeEstimateModel.getProjectId();
		    }
			@Override
		    public String getGoodId() {
		        return threeEstimateModel.getGoodId();
		    }
		});
		if(list.size()>0) {
			throw new RequestException("该专业物资数据已存在，请勿重复添加！",ReturnMsgEnum.Commit_FAIL);
		}
		if (threeEstimateMapper.save(threeEstimateModel) < 1) {
			return getResult(ReturnMsgEnum.Save_Failed);
		}
		logger.info("-------------插入threeEstimateModel成功-------------");
		return getResult(ReturnMsgEnum.Save_Success, id);
	}

	/**
	 * @param ids
	 * @return Result
	 * @throws @name deleteByIds
	 * @author 品讯科技
	 * @Description 物理删除
	 * @time 2021-03-09 22:12:47
	 */
	@Override
	@Transactional(rollbackFor = RequestException.class)
	public Result deleteByIds(String ids) {
		logger.info("--------------根据ids删除数据----------");
		logger.info("ids：" + ids);
		if (threeEstimateMapper.deleteByIds(ids) < 1) {
			throw new RequestException(ReturnMsgEnum.Delete_Failure);
		}
		List<String> strs = stationScheduleMapper.selectIdsByThreeEstimateId(ids);
		String idStations = StringUtil.getMerageStr(strs);
		limitDetailMapper.deleteByThreeIds(idStations);
		stationScheduleMapper.deleteByThreeEstimateIds(ids);

		logger.info("-------------删除成功-------------");
		return getResult(ReturnMsgEnum.Delete_Success);
	}

	/**
	 * @param ids
	 * @return Result
	 * @throws @name logicDeleteByIds
	 * @author 品讯科技
	 * @Description 逻辑删除
	 * @time 2021-03-09 22:12:47
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result logicDeleteByIds(String ids) {
		logger.info("--------------根据ids逻辑删除数据----------");
		logger.info("ids：" + ids);
		try {
			if (threeEstimateMapper.deleteByIds(ids) < 1) {
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
	 * @param threeEstimateModel
	 * @return Result
	 * @throws @name update
	 * @author 品讯科技
	 * @Description 修改
	 * @time 2021-03-09 22:12:47
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result update(ThreeEstimateModel threeEstimateModel) {
		// 更新操作人员
		threeEstimateModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		threeEstimateModel.setUpdateUserCode(TokenUtil.getUserId(threeEstimateModel.getToken()));
		threeEstimateModel.setCreateUserName(TokenUtil.getUser(threeEstimateModel.getToken()).getString("userName"));
		logger.info("修改实体对象数据：" + JsonUtil.entityConvertToJson(threeEstimateModel));

		// ThreeEstimateType: 三量类型 1 概算量 2 图纸量 3 定测量
//		if (threeEstimateModel.getThreeEstimateType().equals(WorkflowEnum.Workflow_BudgetUnit.getCode())) {
//			List<BudgetUnitModel> models = threeEstimateModel.getBudgetUnitModels();
//			for (BudgetUnitModel model : models) {
//				model.setToken(threeEstimateModel.getToken());
//				if (StringUtil.isBlank(model.getId())) {
//					model.setThreeEstimateId(threeEstimateModel.getId());
//					budgetUnitService.save(model);
//				} else {
//					budgetUnitService.update(model);
//				}
//			}
//		} else 
		if (threeEstimateModel.getThreeEstimateType().equals(WorkflowEnum.Workflow_BudgetDrawing.getCode())
				|| threeEstimateModel.getThreeEstimateType().equals(WorkflowEnum.Workflow_BudgetFixed.getCode())) {
			// 图纸量、定测量，关联站区间级联保存
			List<StationScheduleModel> models = threeEstimateModel.getStationScheduleModels();
//			//获取到所有需要新增的数据
			List<StationScheduleModel> needSave = new ArrayList<StationScheduleModel>();
			// 获取到所有需要修改的数据
			List<StationScheduleModel> needUpdate = new ArrayList<StationScheduleModel>();
			// 获取到所有需要删除的数据
			List<StationScheduleModel> needDelete = new ArrayList<StationScheduleModel>();
			List<String> updateIds = new ArrayList<String>();
			List<String> realDelete = new ArrayList<String>();
			for (StationScheduleModel model : models) {
				if (StringUtil.isBlank(model.getId())) {
					if (StringUtils.isEmpty(model.getStationScheduleValue())) {
						continue;
					}
					model.setThreeEstimateId(threeEstimateModel.getId());
					model.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					model.setCreateUserCode(TokenUtil.getUserId(threeEstimateModel.getToken()));
					model.setCreateUserName(TokenUtil.getUser(threeEstimateModel.getToken()).getString("userName"));
					model.setId(UUIDUtil.get32UUID());
					model.setStatus(StringUtils.isEmpty(model.getStatus()) ? "1" : model.getStatus());
					model.setUsedNum(BigDecimal.ZERO);
					model.setOperateType("save");
					needSave.add(model);
				} else {
					if (StringUtils.isEmpty(model.getStationScheduleValue())) {
						model.setOperateType("delete");
						realDelete.add(model.getId());
						needDelete.add(model);
					} else {
						updateIds.add(model.getId());
						model.setOperateType("update");
						needUpdate.add(model);
					}
				}
			}
			Map<String, StationScheduleModel> updateMap = needUpdate.stream()
					.collect(Collectors.toMap(StationScheduleModel::getId, m -> m));
			// 处理数据，获取所有需要修改的，先删除，再插入
			List<StationScheduleModel> list = stationScheduleMapper.findByIds(StringUtil.getMerageStr(updateIds));// 获取到所有需要更新的数据
			list.forEach(m -> {
				StationScheduleModel up = updateMap.get(m.getId());
				m.setStationScheduleValue(up.getStationScheduleValue());
			});
			realDelete.addAll(updateIds);
			stationScheduleMapper.deleteByIds(StringUtil.getMerageStr(realDelete));// 清除
			list.addAll(needSave);// 修改的+新增的 批量插入
			if (!CollectionUtils.isEmpty(list)) {
				if (stationScheduleMapper.batchSave(list) < 1) {
					throw new RequestException(ReturnMsgEnum.Save_Failed);
				}
				List<StationScheduleModel> excuteLimitList = new ArrayList<StationScheduleModel>();
				excuteLimitList.addAll(needUpdate);
				excuteLimitList.addAll(needDelete);
				excuteLimitList.addAll(needSave);
				dealLimit(excuteLimitList, threeEstimateModel);
			}
		}

		if (threeEstimateMapper.update(threeEstimateModel) < 1) {
			return getResult(ReturnMsgEnum.Update_Failure);
		}
		return getResult(ReturnMsgEnum.Update_Success);
	}

	/**
	 * @param id
	 * @return Result
	 * @throws @name findById
	 * @author 品讯科技
	 * @Description 根据条件查询所有数据
	 * @time 2021-03-09 22:12:47
	 */
	@Override
	public Result findById(String id) {
		logger.info("id:" + id);
		try {
			return getResult(ReturnMsgEnum.OK, threeEstimateMapper.findById(id));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param threeEstimateDto
	 * @return Result
	 * @throws @name findAll
	 * @author 品讯科技
	 * @Description 根据条件查询所有数据并分页
	 * @time 2021-03-09 22:12:47
	 */
	@Override
	public Result findAll(ThreeEstimateDto threeEstimateDto) {
		logger.info("入参:" + JsonUtil.entityConvertToJson(threeEstimateDto));
		try {
			page(threeEstimateDto);// 分页判断
			return getResult(ReturnMsgEnum.OK, threeEstimateMapper.findAll(threeEstimateDto));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param threeEstimateDto
	 * @return Result
	 * @throws @name BudgetEstimateList
	 * @author 品讯科技
	 * @Description 根据条件查询概算量数据并分页
	 * @time 2021-03-10 22:59:54
	 */
	@Override
	public Result BudgetEstimateList(ThreeEstimateDto threeEstimateDto) {
		logger.info("入参:" + JsonUtil.entityConvertToJson(threeEstimateDto));
		try {
			// page(threeEstimateDto);//分页判断

			HashMap<String, Object> paramMap = new HashMap<>();
			paramMap.put("projectId", threeEstimateDto.getProjectId());
			paramMap.put("threeEstimateType", threeEstimateDto.getThreeEstimateType());
			paramMap.put("goodCode", threeEstimateDto.getGoodCode());
			paramMap.put("goodName", threeEstimateDto.getGoodName());
			paramMap.put("specsModel", threeEstimateDto.getSpecsModelName());

			return getResult(ReturnMsgEnum.OK, threeEstimateMapper.BudgetEstimateList(paramMap));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param projectId
	 * @return Result
	 * @throws @name sumBudgetEstimateNum
	 * @author 品讯科技
	 * @Description 概算量合计
	 * @time 2021-04-09 22:31:25
	 */
	@Override
	public int sumBudgetEstimateNum(String projectId) {
		logger.info("入参:" + projectId);
		return threeEstimateMapper.sumBudgetEstimateNum(projectId);
	}

	/**
	 * @param threeEstimateDto
	 * @return Result
	 * @throws @name StationScheduleList
	 * @author 品讯科技
	 * @Description 根据条件查询图纸量、定测量（站区间）数据并分页
	 * @time 2021-03-12 13:43:49
	 */
	@Override
	public Result StationScheduleList(ThreeEstimateDto threeEstimateDto) {
		logger.info("入参:" + JsonUtil.entityConvertToJson(threeEstimateDto));
		try {
			// 获取所有专业
			// b_t_three_estimate 获取三量的物资信息
			//
			// page(threeEstimateDto);//分页判断
//
//			HashMap<String, Object> paramMap = new HashMap<>();
//			paramMap.put("projectId", threeEstimateDto.getProjectId());
//			paramMap.put("threeEstimateType", threeEstimateDto.getThreeEstimateType());
//			paramMap.put("goodCode", StringUtils.isEmpty(threeEstimateDto.getGoodCode())? "":threeEstimateDto.getGoodCode()   );
//			paramMap.put("majorId",  StringUtils.isEmpty(threeEstimateDto.getMajorId())? "":threeEstimateDto.getMajorId()  );
//			paramMap.put("goodName", StringUtils.isEmpty(threeEstimateDto.getGoodName())? "":threeEstimateDto.getGoodName()  );
//			paramMap.put("specsModel", StringUtils.isEmpty(threeEstimateDto.getSpecsModelName())? "":threeEstimateDto.getSpecsModelName() );
//			paramMap.put("page", threeEstimateDto.getPageNum());
//			paramMap.put("size", threeEstimateDto.getPageSize());

//			List<JSONObject> list =  threeEstimateMapper.StationScheduleList(paramMap);
//			Integer count = threeEstimateMapper.countData(threeEstimateDto);
			page(threeEstimateDto);// 分页判断
			List<ThreeEstimateDto> list = threeEstimateMapper.findThreeInfo(threeEstimateDto);
			PageInfo pageInfo = new PageInfo<>(list);
			long count = pageInfo.getTotal();
			List<String> threeIds = list.stream().map(ThreeEstimateDto::getId).collect(Collectors.toList());
			String threeIdStrs = StringUtil.getMerageStr(threeIds);
			List<StationScheduleModel> lsitstationSchedule = stationScheduleMapper.selectIdsByteids(threeIdStrs);
			Map<String, List<StationScheduleModel>> stationScheduleMap = lsitstationSchedule.stream()
					.collect(Collectors.groupingBy(StationScheduleModel::getThreeEstimateId));
			List<StationIntervalModel> listStationIntervalModel = stationIntervalMapper
					.findAll(new StationIntervalDto() {
						public String getBelongProjectId() {
							return threeEstimateDto.getProjectId();
						}
					});
			List<JSONObject> listResult = new ArrayList<JSONObject>();
			for (ThreeEstimateDto three : list) {
				List<StationScheduleModel> listSSbyThreeIds = stationScheduleMap.get(three.getId());
				Map<String, String> value = new HashMap<String, String>();
				if (!CollectionUtils.isEmpty(listSSbyThreeIds)) {
					for (StationScheduleModel ss : listSSbyThreeIds) {
						if (StringUtils.isEmpty(ss.getStationScheduleValue())) {
							ss.setStationScheduleValue("");
						}
					}
					value = listSSbyThreeIds.stream().collect(Collectors.toMap(
							StationScheduleModel::getStationScheduleId, StationScheduleModel::getStationScheduleValue));
				}
				String jsonString = JSON.toJSONString(three);
				JSONObject jsonObject = JSON.parseObject(jsonString);

				BigDecimal sum = BigDecimal.ZERO;
				for (StationIntervalModel stationIntervalModel : listStationIntervalModel) {
					String v2 = StringUtils.isEmpty(value.get(stationIntervalModel.getId())) ? "0.00"
							: value.get(stationIntervalModel.getId());
					sum = sum.add(new BigDecimal(v2));
					jsonObject.put(stationIntervalModel.getStationIntervalName(),
							StringUtils.isEmpty(value.get(stationIntervalModel.getId())) ? ""
									: value.get(stationIntervalModel.getId()));
				}
				jsonObject.put("total", sum);
				listResult.add(jsonObject);
			}
			JSONObject data = new JSONObject() {
				{
					put("list", listResult);
					put("total", count);
				}
			};
			return Result.buildResult(ReturnMsgEnum.OK, data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param projectId
	 * @param threeEstimateType
	 * @return Result
	 * @throws @name sumStationScheduleNum
	 * @author 品讯科技
	 * @Description 图纸量、定测量（站区间）合计
	 * @time 2021-04-09 22:39:16
	 */
	@Override
	public int sumStationScheduleNum(String projectId, String threeEstimateType) {
		logger.info("入参:" + projectId);
		return threeEstimateMapper.sumStationScheduleNum(projectId, threeEstimateType);
	}

	/**
	 * @param id
	 * @return Result
	 * @throws @name findBudgetEstimateById
	 * @author 品讯科技
	 * @Description 根据id查询图纸量详情
	 * @time 2021-03-12 16:34:52
	 */
	@Override
	public Result findBudgetDrawingById(String id) {
		logger.info("id:" + id);
		try {
			// 三量类型 1 概算量 2 图纸量 3 定测量
			return getResult(ReturnMsgEnum.OK,
					threeEstimateMapper.findStationSchedule(id, WorkflowEnum.Workflow_BudgetDrawing.getCode()));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param id
	 * @return Result
	 * @throws @name findFixedMeasureById
	 * @author 品讯科技
	 * @Description 根据主键id查看定测量详情
	 * @time 2021-03-12 16:37:09
	 */
	@Override
	public Result findFixedMeasureById(String id) {
		logger.info("id:" + id);
		try {
			// 三量类型 1 概算量 2 图纸量 3 定测量
			return getResult(ReturnMsgEnum.OK,
					threeEstimateMapper.findStationSchedule(id, WorkflowEnum.Workflow_BudgetFixed.getCode()));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param projectId
	 * @return Result
	 * @throws @name commitBudgetUnit
	 * @author 品讯科技
	 * @Description 提交工作流，概算量
	 * @time 2021-04-02 11:48:23
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result commitBudgetUnit(String token, String projectId) {
		WorkflowDto wf = new WorkflowDto();
		wf.setToken(token);
		wf.setWorkflowEnum(WorkflowEnum.Workflow_BudgetUnit);
		wf.setBusId(projectId + wf.getWorkflowEnum().getCode());
		wf.setRemarks("概算量流程提交审核。");
		wf.setProjectId(projectId);
		boolean result = workflowService.submitWorkflow(wf);
		/*
		 * //改变状态 ThreeEstimateApproveModel approveModel = new
		 * ThreeEstimateApproveModel();
		 * approveModel.setToken(threeEstimateModel.getToken());
		 * approveModel.setProjectId(threeEstimateModel.getProjectId()); //三量按项目标记
		 * approveModel.setThreeEstimateType(threeEstimateModel.getThreeEstimateType());
		 * approveModel.setStatus("1"); //待提交
		 * threeEstimateApproveService.upsert(approveModel);
		 */
		return getResult(ReturnMsgEnum.Commit_OK, result);
	}

	/**
	 * @param projectId
	 * @return Result
	 * @throws @name commitBudgetDrawing
	 * @author 品讯科技
	 * @Description 提交工作流，图纸量
	 * @time 2021-04-02 11:48:23
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result commitBudgetDrawing(String token, String projectId) {
		WorkflowDto wf = new WorkflowDto();
		wf.setToken(token);
		wf.setWorkflowEnum(WorkflowEnum.Workflow_BudgetDrawing);
		wf.setBusId(projectId + wf.getWorkflowEnum().getCode());
		wf.setRemarks("图纸量流程提交审核。");
		wf.setProjectId(projectId);
		boolean result = workflowService.submitWorkflow(wf);

		// 需要比较数量 图纸量>概算量，发消息
		// boolean result = workflowService.sendMsg(WorkflowEnum.Workflow_BudgetUnit,
		// "0", projectId);
		return getResult(ReturnMsgEnum.Commit_OK, result);
	}

	/**
	 * @param projectId
	 * @return Result
	 * @throws @name commitFixed
	 * @author 品讯科技
	 * @Description 提交工作流，定测量
	 * @time 2021-04-02 11:48:23
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result commitFixed(String token, String projectId) {
		WorkflowDto wf = new WorkflowDto();
		wf.setToken(token);
		wf.setWorkflowEnum(WorkflowEnum.Workflow_BudgetFixed);
		wf.setBusId(projectId + wf.getWorkflowEnum().getCode());
		wf.setRemarks("定测量流程提交审核。");
		wf.setProjectId(projectId);
		boolean result = workflowService.submitWorkflow(wf);

		// 需要比较数量 定测量>图纸量，发消息
		// boolean result = workflowService.sendMsg(WorkflowEnum.Workflow_BudgetUnit,
		// "0", projectId);
		return getResult(ReturnMsgEnum.Commit_OK, result);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void checkLimit(PlanGoodModel goodModel) {
		String majorId = goodModel.getMajorId();
		List<OrderGoodDetailModel> goodDetail = goodModel.getGoodDetail();// 获取详情
		// 多个区间，
		List<String> stationIntervalIdList = goodDetail.stream().map(OrderGoodDetailModel::getStationIntervalId)
				.collect(Collectors.toList());
		String stationIntervalIds = StringUtil.getMerageStr(stationIntervalIdList);
		// 多个物资
		List<String> goodList = goodDetail.stream().map(OrderGoodDetailModel::getGoodId).collect(Collectors.toList());
		String goodIds = StringUtil.getMerageStr(goodList);

		List<StationScheduleModel> listModel = stationScheduleMapper.findAllByMajorIdAndGoodIds(majorId,
				stationIntervalIds, goodIds);

		List<String> needDelete = new ArrayList<String>();
		List<StationScheduleModel> needUpdate = new ArrayList<StationScheduleModel>();
		Map<String, StationScheduleModel> mapStationScheduleModel = listModel.stream()
				.collect(Collectors.toMap(StationScheduleModel::getUnionKey, t -> t));
//		, CONCAT(g.major_id ,'_',g.good_id ,'_',t.station_schedule_id   )
		for (OrderGoodDetailModel orderGoodDetail : goodDetail) {
			String unionKey = majorId + "_" + orderGoodDetail.getGoodId() + "_"
					+ orderGoodDetail.getStationIntervalId();
			StationScheduleModel model = mapStationScheduleModel.get(unionKey);
			if (model == null) {
				continue;
			}
			if ("delete".equals(orderGoodDetail.getOperateType())) {
				BigDecimal used = (model.getUsedNum() == null ? BigDecimal.ZERO : model.getUsedNum())
						.subtract(new BigDecimal(orderGoodDetail.getNum()));
				model.setUsedNum(used.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : used);
			} else if ("save".equals(orderGoodDetail.getOperateType())) {
				BigDecimal used = (model.getUsedNum() == null ? BigDecimal.ZERO : model.getUsedNum())
						.add(new BigDecimal(orderGoodDetail.getNum()));
				if ((new BigDecimal(model.getStationScheduleValue()).subtract(used)).compareTo(BigDecimal.ZERO) < 0) {
					BigDecimal balance = new BigDecimal(model.getStationScheduleValue()).subtract(
							model.getUsedNum() ==null ? BigDecimal.ZERO :model.getUsedNum());
					getErrMsg(orderGoodDetail.getStationIntervalId(), orderGoodDetail.getGoodId(), balance.toPlainString(), orderGoodDetail.getNum());
				}
				model.setUsedNum(used);
			} else if ("update".equals(orderGoodDetail.getOperateType())) {
				BigDecimal used = (model.getUsedNum() == null ? BigDecimal.ZERO : model.getUsedNum())
						.subtract(new BigDecimal(orderGoodDetail.getOrgNum()))
						.add(new BigDecimal(orderGoodDetail.getNum()));
				if ((new BigDecimal(model.getStationScheduleValue()).subtract(used)).compareTo(BigDecimal.ZERO) < 0) {
					BigDecimal balance = new BigDecimal(model.getStationScheduleValue()).
							subtract(model.getUsedNum()).
							add(new BigDecimal(orderGoodDetail.getOrgNum()));
					getErrMsg(orderGoodDetail.getStationIntervalId(), orderGoodDetail.getGoodId(), balance.toPlainString(), orderGoodDetail.getNum());
				}
				model.setUsedNum(used);
			}
			needDelete.add(model.getId());
			needUpdate.add(model);
		}
		if (!CollectionUtils.isEmpty(needDelete)) {
			int delete = stationScheduleMapper.deleteByIds(StringUtil.getMerageStr(needDelete));
			int save = stationScheduleMapper.batchSave(needUpdate);
			logger.info("删除的数据:{},同步的数据：{}", delete, save);
		}
	}
	
	public void getErrMsg(String stationScheduleId,String goodId ,String balance,String app) {
		JSONObject good = goodMapper.findById(goodId);
		StationIntervalModel stationInterval = stationIntervalMapper.findById(stationScheduleId);
		String msg = "【" +stationInterval.getStationIntervalName()+ "】申请物资（"+ good.getString("goodName")+"）数量["+new BigDecimal(app).setScale(4)+"] 超过定测量余量["+balance+"]，申请失败" ;
		throw new RequestException(msg, ReturnMsgEnum.Save_Failed);
	}
}
