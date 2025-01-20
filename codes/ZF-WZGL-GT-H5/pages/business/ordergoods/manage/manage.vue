<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'订货单'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit(0)">提交</u-button>
			<u-button slot="right" class="mr-30" size="mini" type="success" @click="submit(-1)">保存</u-button>
		</u-navbar>
		<u-cell-group class="mt-24" v-if="id">
			<u-cell-item title="申请计划编号" :arrow="false">{{info.planHandNo}}</u-cell-item>
			<u-cell-item title="制单人" :arrow="false">{{info.createUserName}}</u-cell-item>
			<u-cell-item title="制单时间" :arrow="false">{{info.createTime}}</u-cell-item>
		</u-cell-group>
		<view class="info mt-24">
			
			<u-form :model="info" ref="uForm" label-width="200rpx" :border-bottom="false" :error-type="['toast']">
				<u-form-item label="编号" prop="handNo">
					<u-input class="input"  input-align="right"  placeholder="请输入编号" v-model="info.handNo" />
				</u-form-item>
				<u-form-item label="供应商名称" prop="supplierName">
					<u-input class="input"  input-align="right"  placeholder="请输入供应商名称" v-model="info.supplierName" />
				</u-form-item>
				<u-form-item label="专业" prop="majorName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择专业"
						v-model="info.majorName" @click="chooseMajor">
					</z-select>
				</u-form-item>
				<u-form-item label="区间" prop="planOverDate">
					<view>
						<uni-card v-for="(tag, index) in tags" :key="index" style=" display: flex;flex-direction: column;
						justify-content: center;  border: 1px solid blue; float: left;">
							<view class="tag-item">{{tag.stationIntervalName}}
								<text style="padding-left: 5rpx; font-size: 30rpx; font-weight: bold;"
									@click="removeTag(index)"> x </text>
							</view>
						</uni-card>
						<!-- <uni-card style=" display: flex;flex-direction: column;
								justify-content: center;  float: left;">
							<view v-if="id ==''" class="tag-item2"> <text style=" font-size: 25rpx;  "
									@click="addTag"> 选择 </text></view>
						</uni-card> -->
					</view>
				</u-form-item>
				<u-form-item label="要求到货日期" prop="requireArrivalDate">
					<view class="date-select flex-n fac">
						<u-input class="input" input-align="right" height="60" v-model="info.requireArrivalDate"
							placeholder="请选择要求到货日期" :clearable="false" @click="showArriveDate=true" />
						<image v-if="info.requireArrivalDate" class="close" mode="widthFix"
							src="../../../../common/img/index/close.png" @click="$set(info,'requireArrivalDate','')">
						</image>
					</view>
				</u-form-item>
				<!-- <u-form-item label="预计到货日期" prop="preArrivalDate">
					<view class="date-select flex-n fac">
						<u-input class="input" input-align="right" height="60" v-model="info.preArrivalDate"
							placeholder="请选择预计到货日期" :clearable="false" @click="showPreArriveDate=true" />
						<image v-if="info.preArrivalDate" class="close" mode="widthFix"
							src="../../../../common/img/index/close.png" @click="$set(info,'preArrivalDate','')">
						</image>
					</view>
				</u-form-item> -->
				<u-form-item label="到货地点" prop="arrivalPlace" :border-bottom="false" label-position="top">
					<u-input type="textarea" input-align="right"  placeholder="请输入到货地点" v-model="info.arrivalPlace" />
				</u-form-item>
				<!-- <u-form-item label="备注" prop="remark" :border-bottom="false" label-position="top">
					<u-input type="textarea" placeholder="请输入备注" v-model="info.remark" />
				</u-form-item> -->
			</u-form>
		</view>
		<!-- 供应商 -->
		<u-select v-model="showSupplier" label-name='supplierName' :default-value="supplierDefault" value-name="id"
			:list="supplierList" @confirm="confirmSupplier" :mask-close-able="false">
		</u-select>
		<!-- 专业 -->
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefault" value-name="id"
			:list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
		<u-calendar v-model="showPreArriveDate" @change="changePreArriveDate"></u-calendar>
		<u-calendar v-model="showArriveDate" @change="changeArriveDate"></u-calendar>
		<view class="common-infolist-title mt-24">
			<view class="flex-1 title">物资信息</view>
			<u-button v-if="!info.planHandNo" size="mini" type="primary" plain shape="circle" @click="addMaterial">选择申请计划</u-button>
		</view>
		<scroll-view class="scroll" :scroll-y="true">
			<z-card class="mt-24" :shadow="false" v-for="(item,index) in list" :key="index">
				<view class="comm-detail-text">
					<view></view>
					<view class="right">
						<u-icon name="trash" size="40" color="#ff3300" @click="list.splice(index,1)"></u-icon>
					</view>
					<view>物资编码：</view>
					<view class="right">{{item.goodCode}}</view>
					<view>物资名称：</view>
					<view class="right">{{item.goodName}}</view>
					<view>规格型号：</view>
					<view class="right">{{item.specsModelName}}</view>
					<view>计量单位：</view>
					<view class="right">{{item.measureUnit}}</view>
					<!-- <view>数量：</view>
					<view class="right">
						<u-number-box input-width="160" v-model="item.num"></u-number-box>
					</view> -->
					<block v-for="(itm,idx) in item.stationIntervalDetails" :key="idx">
						<view>{{itm.stationIntervalName}}：</view>
						<view class="right">
							<u-input type="number" input-align="right" placeholder="请输入数量" v-model="itm.num" />
						</view>
					</block>
				</view>
			</z-card>
		</scroll-view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				id: '',
				tags: [],
				stationIntervalList:[],
				newTag: '',
				approveStatus: '',
				info: {
					supplierId: '',
					supplierName: '',
					majorId: '',
					majorName: '',
					requireArrivalDate: '',
					arrivalPlace: '',
					preArrivalDate: '',
				},
				rules: {
					handNo: [{
						required: true,
						message: '请输入编号',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					majorName: [{
						required: true,
						message: '请选择专业',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					supplierName: [{
						required: true,
						message: '请选择供应商',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					requireArrivalDate: [{
						required: true,
						message: '请选择要求到货日期',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					arrivalPlace: [{
						required: true,
						message: '请输入到货地点',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
				},
				showArriveDate: false,

				showPreArriveDate: false,

				showSupplier: false,
				supplierDefault: [0],
				showMajor: false,
				majorDefault: [0],
				supplierList: [],
				majorList: [],
				list: [],
				hisList:[],
			}
		},
		onLoad(options) {
			console.log(options)
			this.id = options.id;
			if (this.id)
				this.getData(options.id);

		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		onShow() {
			this.$nextTick(() => {
				this.getSupplierList();
				this.getMajorList();
			})
		},
		methods: {
		 // selectPlan(data) {
					
			// 	},
				
			getSelectStationInterval(ids) {
				this.$axios.post('/stationInterval/findList', {
					"belongProjectId": app.globalData.projectId,
				}).then(res => {
					this.tags = res.data.list.filter(s =>ids.includes(s.id) );
					this.reloadGoodsData()
				}).catch(err => {
					console.log(err)
					console.log(err.msg)
				})
			},
			reloadGoodsData(){
				for( var i in this.list){
					var stationIntervalDetails = [];
					var orgstationIntervalDetails = []
					if(this.list[i].stationIntervalDetails){
						orgstationIntervalDetails = 	this.list[i].stationIntervalDetails;
					}
					console.log(this.list[i].stationIntervalDetails)
					for(var j in this.tags){
							var newModel = 	JSON.parse(JSON.stringify(this.tags[j]));
							var isHas = false;
						for(var k in orgstationIntervalDetails){
							var org = JSON.parse(JSON.stringify(orgstationIntervalDetails[k])) ;
							if( org.stationIntervalId === newModel.id ){
								newModel= org
								isHas = true;
							}
						}
						if(!isHas){
							newModel.stationIntervalId = newModel.id;
						}
						stationIntervalDetails.push(newModel);
					}
						this.list[i].stationIntervalDetails = stationIntervalDetails;
					}
			},
			addTag() {
			
				uni.setStorage({
					key: 'blogPopupTags',
					data: this.tags
				})
				uni.navigateTo({
					url: '/pages/blogPopup/blogPopup'
				})
			
			},
			removeTag(index) {
				this.tags.splice(index, 1);
				this.reloadGoodsData()
			},
			
			getSupplierList() {
				this.$axios.post('/supplier/findList', {}).then(res => {
					console.log(res.data.list)
					this.supplierList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			getMajorList() {
				this.$axios.post('/major/findList', {}).then(res => {
					console.log(res.data.list)
					this.majorList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			upData(data) {
				console.log(data[0].id)
				this.$axios.get('/planGood/findBalanceById/'+data[0].id).then(res=>{
					console.log(res)
					this.info.stationIntervalIds=res.data.stationIntervalIds;
					this.getSelectStationInterval(res.data.stationIntervalIds)
					this.list=res.data.goodDetailObj;
					this.info.majorId = res.data.majorId;
					this.info.majorName = res.data.majorName;
					console.log()
					this.info.planNo = res.data.planNo;
					this.info.planHandNo = res.data.planHandNo;
					this.info.planGoodId = res.data.id;
					this.info.arrivalPlace = res.data.arrivalPlace;
					this.info.requireArrivalDate = res.data.planOverDate;
				}).catch(err=>{
					this.$message.error(err.msg)
				})
			},
			getData(id) {
				this.$axios.get('/orderGood/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.info.planHandNo = res.data.handNo;
					this.list = res.data.goodDetailObj;
					this.getSelectStationInterval(res.data.stationIntervalIds)
					this.hisList= JSON.parse(JSON.stringify(res.data.goodDetailObj));
				}).catch(err => {
					console.log(err)
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			addMaterial() {
				uni.navigateTo({
					url: '../material/material',
					success: res => {
						uni.setStorage({
							key: 'materialList',
							data: this.list
						})
					},
					fail: () => {},
					complete: () => {}
				});
			},
			changePreArriveDate(e) {
				this.$set(this.info, 'preArrivalDate', e.result)
			},
			changeArriveDate(e) {
				this.$set(this.info, 'requireArrivalDate', e.result)
			},
			chooseSupplier() {
				this.showSupplier = true;
			},
			chooseMajor() {
				this.showMajor = true;
			},
			confirmSupplier(data) {
				console.log(data)
				this.$set(this.info, 'supplierName', data[0].label);
				this.$set(this.info, 'supplierId', data[0].value);
				for (var i in this.supplierList) {
					if (this.supplierList[i].id == data[0].value) {
						this.supplierDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			confirmMajor(data) {
				this.$set(this.info, 'majorName', data[0].label);
				this.$set(this.info, 'majorId', data[0].value);
				for (var i in this.majorList) {
					if (this.majorList[i].id == data[0].value) {
						this.majorDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			submit(approveStatus) {
				this.approveStatus = approveStatus;
				if (this.list.length == 0)
					return this.$refs.uToast.show({
						title: '请选择物资',
						type: 'warning'
					})
				this.$refs.uForm.validate(valid => {
					if (valid) {
						if (this.id) {
							this.editSubmit();
						} else {
							this.addSubmit();
						}
					} else {

					}
				})
			},
			addSubmit() {
				var info = this.info;
				var goodDetail = [];
				
				for (var i in this.list) {
					var kkk = this.list[i].stationIntervalDetails;
					var mapk = {};
					mapk['goodId']=  this.list[i].goodId  ;
					for (var k in kkk) {
						if(!kkk[k].num ){
							kkk[k].num=0;
						}
						var key = kkk[k].stationIntervalId;
						mapk[key] = kkk[k].num 
					}
					goodDetail.push(mapk)
				}
				this.info.stationIntervalIds = [];
				for(var i  in this.tags){
					this.info.stationIntervalIds.push(this.tags[i].id)
				}
				console.log(goodDetail)
				this.$axios.post('/orderGood/save', {
					"projectId": app.globalData.projectId,
					"handNo": info.handNo,
					"supplierName": info.supplierName,
					"majorId": info.majorId,
					"requireArrivalDate": info.requireArrivalDate,
					"arrivalPlace": info.arrivalPlace,
					"preArrivalDate": info.preArrivalDate,
					"stationIntervalId": info.stationIntervalId,
					"stationIntervalIds": info.stationIntervalIds,
					"approveStatus": this.approveStatus,
					"goodDetailObj": goodDetail,
					 "planGoodId": info.planGoodId,
					"planNo": info.planNo,
				}).then(res => {
					this.$refs.uToast.show({
						title:'新增订货单成功',
						type: 'success'
					})
					this.resetForm();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			editSubmit() {
				var info = this.info;
				var goodDetail = [];
				// for (var i in this.list) {
				// 	goodDetail.push({
				// 		"goodId": this.list[i].goodId,
				// 		"num": this.list[i].num
				// 	})
				// }
				// for (var i in this.hisList) {
				// 	for (var j in goodDetail) {
				// 		if (this.hisList[i].goodId == goodDetail[j].goodId) {
				// 			goodDetail[j].id = this.hisList[i].id;
				// 		}
				// 	}
				// }
				
				for (var i in this.list) {
					var kkk = this.list[i].stationIntervalDetails;
					var mapk = {};
					mapk['goodId']=  this.list[i].goodId  ;
					for (var k in kkk) {
						if(!kkk[k].num ){
							kkk[k].num=0;
						}
						var key = kkk[k].stationIntervalId;
						mapk[key] = kkk[k].num 
					}
					goodDetail.push(mapk)
				}
				this.info.stationIntervalIds = [];
				for(var i  in this.tags){
					this.info.stationIntervalIds.push(this.tags[i].id)
				}
				console.log(goodDetail)
				this.$axios.post('/orderGood/update', {
				 "id":this.id,
				 
				 "projectId": app.globalData.projectId,
				 "handNo": info.handNo,
				 "supplierName": info.supplierName,
				 "majorId": info.majorId,
				 "requireArrivalDate": info.requireArrivalDate,
				 "arrivalPlace": info.arrivalPlace,
				 "preArrivalDate": info.preArrivalDate,
				 "stationIntervalId": info.stationIntervalId,
				 "stationIntervalIds": info.stationIntervalIds,
				 "approveStatus": this.approveStatus,
				 "goodDetailObj": goodDetail,
				  "planGoodId": info.planGoodId,
				 "planNo": info.planNo,
				}).then(res => {
					this.$refs.uToast.show({
						title:'编辑订货单成功',
						type: 'success'
					})
					this.resetForm();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			resetForm(){
				uni.navigateBack({
					delta:1
				})
			}
		}
	}
</script>
<style>
	page {
		background: #F5F5F5;
	}
</style>
<style scoped lang="scss">
	.info {
		padding: 0 24rpx;
		background: white;
	}

	.scroll {
		height: calc(100vh - 400rpx);
	}

	.date-select {
		position: relative;
		width: 100%;

		.close {
			width: 40rpx;
			margin-left: 20rpx;
		}
	}
	
	.tag-item {
		line-height: 40rpx;
		padding-left: 10rpx;
		padding-right: 10rpx;
		margin: 5rpx;
		border-radius: 10rpx;
		background-color: #17BC6D;
		text-align: center;
		font-size: 25rpx;
		font-family: bold;
		color: #fff;
	}
	
	.tag-item2 {
		line-height: 40rpx;
		padding-left: 10rpx;
		padding-right: 10rpx;
		margin: 5rpx;
		border-radius: 10rpx;
		background-color: #2879FE;
		text-align: center;
		font-size: 25rpx;
		font-family: bold;
		color: #fff;
	}
	
	.tag-selected {
		background-color: #f60;
		color: #fff;
	}
</style>
