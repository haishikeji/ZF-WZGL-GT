<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'订货单'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit">保存</u-button>
		</u-navbar>
		<u-cell-group class="mt-24" v-if="id">
			<u-cell-item title="订单编号" :arrow="false">{{info.orderNo}}</u-cell-item>
			<u-cell-item title="制单人" :arrow="false">{{info.createUserName}}</u-cell-item>
			<u-cell-item title="制单时间" :arrow="false">{{info.createTime}}</u-cell-item>
		</u-cell-group>
		<view class="info mt-24">
			<u-form :model="info" ref="uForm" label-width="200rpx" :border-bottom="false" :error-type="['toast']">
				<u-form-item label="供应商名称" prop="supplierName">
					<z-select :border="false" height="60" align="right" size="mini" style="width: 100%;"
						placeholder="请选择供应商名称" v-model="info.supplierName" @click="chooseSupplier">
					</z-select>
				</u-form-item>
				<u-form-item label="专业" prop="majorName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择专业"
						v-model="info.majorName" @click="chooseMajor">
					</z-select>
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
				<u-form-item label="预计到货日期" prop="preArrivalDate">
					<view class="date-select flex-n fac">
						<u-input class="input" input-align="right" height="60" v-model="info.preArrivalDate"
							placeholder="请选择预计到货日期" :clearable="false" @click="showPreArriveDate=true" />
						<image v-if="info.preArrivalDate" class="close" mode="widthFix"
							src="../../../../common/img/index/close.png" @click="$set(info,'preArrivalDate','')">
						</image>
					</view>
				</u-form-item>
				<u-form-item label="到货地点" prop="arrivalPlace" :border-bottom="false" label-position="top">
					<u-input type="textarea" placeholder="请输入到货地点" v-model="info.arrivalPlace" />
				</u-form-item>
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
			<u-button size="mini" type="primary" plain shape="circle" @click="addMaterial">新增物资</u-button>
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
					<view>数量：</view>
					<view class="right">
						<u-number-box input-width="160" v-model="item.num"></u-number-box>
					</view>
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
					supplierName: [{
						required: true,
						message: '请选择供应商',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					majorName: [{
						required: true,
						message: '请选择专业',
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
					preArrivalDate: [{
						required: true,
						message: '请选择预计到货日期',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}]
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
				console.log(data)

				for(var i in data){
					var num=-1;
					for(var j in this.list){
						if(this.list[j].goodId==data[i].goodId){
							num=j;
							break;
						}
					}
					if(num==-1){
						data[i].num=1;
					}
				}
				
				this.list = data;
				this.$forceUpdate()
			},
			getData(id) {
				this.$axios.get('/orderGood/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.goodDetail;
					this.hisList= JSON.parse(JSON.stringify(res.data.goodDetail));
				}).catch(err => {
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
			submit() {
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
					goodDetail.push({
						"goodId": this.list[i].goodId,
						"num": this.list[i].num
					})
				}
				this.$axios.post('/orderGood/save', {
					"projectId": app.globalData.projectId,
					"supplierId": info.supplierId,
					"majorId": info.majorId,
					"requireArrivalDate": info.requireArrivalDate,
					"arrivalPlace": info.arrivalPlace,
					"preArrivalDate": info.preArrivalDate,
					"goodDetail": goodDetail
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
				for (var i in this.list) {
					goodDetail.push({
						"goodId": this.list[i].goodId,
						"num": this.list[i].num
					})
				}
				for (var i in this.hisList) {
					for (var j in goodDetail) {
						if (this.hisList[i].goodId == goodDetail[j].goodId) {
							goodDetail[j].id = this.hisList[i].id;
						}
					}
				}
				this.$axios.post('/orderGood/update', {
				 "id":this.id,
					"supplierId": info.supplierId,
					"majorId": info.majorId,
					"requireArrivalDate": info.requireArrivalDate,
					"arrivalPlace": info.arrivalPlace,
					"preArrivalDate": info.preArrivalDate,
					"goodDetail": goodDetail
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
</style>
