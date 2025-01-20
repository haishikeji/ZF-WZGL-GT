package com.px.project.web.service.impl;

import com.px.project.common.base.BaseService;
import com.px.project.common.base.Result;
import com.px.project.common.base.ReturnMsgEnum;
import com.px.project.common.exception.RequestException;
import com.px.project.common.utils.JsonUtil;
import com.px.project.common.utils.SequenceService;
import com.px.project.common.utils.StringUtil;
import com.px.project.common.utils.TokenUtil;
import com.px.project.common.utils.UUIDUtil;
import com.px.project.web.dao.IGoodMajorMapper;
import com.px.project.web.dao.IGoodMapper;
import com.px.project.web.dto.GoodDto;
import com.px.project.web.entity.GoodMajorModel;
import com.px.project.web.entity.GoodModel;
import com.px.project.web.service.IGoodService;
import com.px.project.web.service.IGoodTypeService;
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
 * @ClassName GoodServiceImpl
 * @Description GoodServiceImpl
 * @author 品讯科技
 * @time 2021-02-03 21:56:59
 * @version V 1.0
 */
@Service
public class GoodServiceImpl extends BaseService implements IGoodService<GoodModel> {
	@Autowired
	public IGoodMapper goodMapper;
	@Autowired
	public SequenceService sequenceService;
	@Autowired
	public IGoodMajorMapper goodMajorMapper;
	@Autowired
	public IGoodTypeService goodTypeService;
	private static Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);

	/**
	 * @name save
	 * @author 品讯科技
	 * @param goodModel
	 * @Description 保存
	 * @time 2021-02-03 21:56:59
	 * @throws @return Result
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result save(GoodModel goodModel) {
		// 更新操作人员
		goodModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		goodModel.setCreateUserCode(TokenUtil.getUserId(goodModel.getToken()));
		goodModel.setCreateUserName(TokenUtil.getUserLoginName(goodModel.getToken()));

		if (StringUtil.isBlank(goodModel.getGoodType())) {
			throw new RequestException("物资类别不能为空！", ReturnMsgEnum.Parameter_Error);
		}
		// 获取物资编码
		goodModel.setGoodCode(sequenceService.getGoodCode(goodModel.getGoodType()));
		goodModel.getToken();
		String id = UUIDUtil.get32UUID();
		goodModel.setId(id);
		logger.info("保存实体对象数据：" + JsonUtil.entityConvertToJson(goodModel));
		if (goodMapper.save(goodModel) < 1) {
			return getResult(ReturnMsgEnum.Save_Failed);
		}

		String majorIds = goodModel.getMajorIds();

		String[] ms = majorIds.split(",");
		for (String majorid : ms) {
			GoodMajorModel map = new GoodMajorModel();
			map.setMajorId(majorid);
			map.setGoodId(id);
			map.setId(UUIDUtil.get32UUID());
			goodMajorMapper.save(map);
		}

		logger.info("-------------插入GoodModel成功-------------");
		return getResult(ReturnMsgEnum.Save_Success, id);
	}

	// 获取物资编码
	// 后四位为：名称+规格型号
	// 若名称出现过，则用原始名称的编号，若规格型号出现过，则用原始规格型号编号
//	public String getGoodCode(GoodModel goodModel) {
//		String genCode = new String();
//		// 自动生成代码：大类 + 一级分类 + 二级分类 名称 + 规格
//		GoodTypeModel typeModel = goodTypeService.getGoodType(goodModel.getGoodType()); // UUID
//		// 物资类型的长度为5，没有二级三级的规则右侧则补零处理
//		genCode  = String.format("%-5s", typeModel.getTypeCode()).replace(" ", "0");
//		
////		GoodDto dto = new GoodDto();
////		dto.setProjectId(goodModel.getProjectId());
////		dto.setGoodNameMatch(goodModel.getGoodName());
////		// 名称
////		List<JSONObject> goodModels = goodMapper.findAll(dto);
////		if (goodModels.size() > 0) {
////			String otherGoodCode = goodModels.get(0).getString("goodCode");
////			String lastGoodCode = otherGoodCode.substring(otherGoodCode.length() - 7, otherGoodCode.length() - 3);
////			genCode.append(String.format("%04d", Integer.parseInt(lastGoodCode)));
////		} else {
////			long maxNameCount = goodMapper.findAll(new GoodDto()).stream().map(x -> x.getString("goodName")).distinct()
////					.count() + 1;
////			genCode.append(String.format("%04d", maxNameCount));
////		}
////		// 规格
////		dto.setGoodNameMatch(null);
////		dto.setSpecsModelNameMatch(goodModel.getSpecsModelName());
////		goodModels = goodMapper.findAll(dto);
////		if (goodModels.size() > 0) {
////			String otherGoodCode = goodModels.get(0).getString("goodCode");
////			String lastGoodCode = otherGoodCode.substring(otherGoodCode.length() - 3);
////			genCode.append(String.format("%03d", Integer.parseInt(lastGoodCode)));
////		} else {
////			long maxSpecsNameCount = goodMapper.findAll(new GoodDto()).stream().map(x -> x.getString("specsModelName"))
////					.distinct().count() + 1;
////			genCode.append(String.format("%03d", maxSpecsNameCount));
////		}
////		10203 0001001
//		return sequenceService.getNextValue(genCode, 7);
//	}

	/**
	 * @name deleteByIds
	 * @author 品讯科技
	 * @param ids
	 * @Description 物理删除
	 * @time 2021-02-03 21:56:59
	 * @throws @return Result
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result deleteByIds(String ids) {
		logger.info("--------------根据ids删除数据----------");
		logger.info("ids：" + ids);
		try {
			if (goodMapper.deleteByIds(ids) < 1) {
				return getResult(ReturnMsgEnum.Delete_Failure);
			}
		} catch (Exception e) {
			logger.info("删除失败");
			logger.info(e.toString());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
		logger.info("-------------删除成功-------------");
		return getResult(ReturnMsgEnum.Delete_Success);
	}

	/**
	 * @name logicDeleteByIds
	 * @author 品讯科技
	 * @param ids
	 * @Description 逻辑删除
	 * @time 2021-02-03 21:56:59
	 * @throws @return Result
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result logicDeleteByIds(String ids) {
		logger.info("--------------根据ids逻辑删除数据----------");
		logger.info("ids：" + ids);
		try {
			if (goodMapper.deleteByIds(ids) < 1) {
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
	 * @name update
	 * @author 品讯科技
	 * @param goodModel
	 * @Description 修改
	 * @time 2021-02-03 21:56:59
	 * @throws @return Result
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Result update(GoodModel goodModel) {
		// 更新操作人员
		goodModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		goodModel.setUpdateUserCode(TokenUtil.getUserId(goodModel.getToken()));
		goodModel.setUpdateUserName(TokenUtil.getUserLoginName(goodModel.getToken()));
		// 不允许更新物资编码
		goodModel.setGoodCode(null);

		logger.info("修改实体对象数据：" + JsonUtil.entityConvertToJson(goodModel));

		if (goodMapper.update(goodModel) < 1) {
			return getResult(ReturnMsgEnum.Update_Failure);
		}
		
		String majorIds = goodModel.getMajorIds()+"";
		String[] ms = majorIds.split(",");
		if(ms.length>0) {
			goodMajorMapper.deleteByGoodId(goodModel.getId());
			for (String majorid : ms) {
				GoodMajorModel map = new GoodMajorModel();
				map.setMajorId(majorid);
				map.setGoodId(goodModel.getId());
				map.setId(UUIDUtil.get32UUID());
				goodMajorMapper.save(map);
			}
		}
		
		return getResult(ReturnMsgEnum.Update_Success);
	}

	/**
	 * @name findById
	 * @author 品讯科技
	 * @param id
	 * @Description 根据条件查询所有数据
	 * @time 2021-02-03 21:56:59
	 * @throws @return Result
	 */
	@Override
	public Result findById(String id) {
		logger.info("id:" + id);
		try {
			return getResult(ReturnMsgEnum.OK, goodMapper.findById(id));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @name findAll
	 * @author 品讯科技
	 * @param goodDto
	 * @Description 根据条件查询所有数据并分页
	 * @time 2021-02-03 21:56:59
	 * @throws @return Result
	 */
	@Override
	public Result findAll(GoodDto goodDto) {
		logger.info("入参:" + JsonUtil.entityConvertToJson(goodDto));
		try {
			page(goodDto);// 分页判断
			return getResult(ReturnMsgEnum.OK, goodMapper.findAll(goodDto));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}

	/**
	 * @param ids
	 * @return Result
	 * @throws @name findByIds
	 * @author 品讯科技
	 * @Description 查询物资库位分布列表
	 * @time 2021-03-26 16:48:00
	 */
	@Override
	public Result findByIds(String ids) {
		logger.info("ids：" + ids);
		try {
			return getResult(ReturnMsgEnum.OK, goodMapper.findByIds(ids));
		} catch (Exception e) {
			logger.info("查询失败");
			logger.info(e.getMessage());
			return getResult(ReturnMsgEnum.Internal_Server_Error);
		}
	}
}
