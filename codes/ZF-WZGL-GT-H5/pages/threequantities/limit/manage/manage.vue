<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'限额'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit">保存</u-button>
		</u-navbar>
		<view class="info mt-24">
			<u-form :model="info" ref="uForm" label-width="200rpx" :border-bottom="false" :error-type="['toast']">


				<u-form-item label="专业" prop="majorName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择专业"
						v-model="info.majorName" @click="chooseMajor">
					</z-select>
				</u-form-item>
				<u-form-item label="区间" prop="stationIntervalName">
					<z-select :border="false" height="60" align="right" size="mini" style="width: 100%;"
						placeholder="请选择区间名称" v-model="info.stationIntervalName" @click="chooseStationInterval">
					</z-select>
				</u-form-item>
			</u-form>
		</view>
		<!-- 供应商 -->
		<!-- <u-select v-model="showSupplier" label-name='supplierName' :default-value="supplierDefault" value-name="id"
			:list="supplierList" @confirm="confirmSupplier" :mask-close-able="false">
		</u-select> -->

		<u-select v-model="showStationInterval" label-name='stationIntervalName' :default-value="stationIntervalDefult"
			value-name="id" :list="stationIntervalList" @confirm="confirmStationInterval" :mask-close-able="false">
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
					<view>限额数量：</view>
					<view class="right">
						<!-- <u-input  decimal-length="4" input-width="160" v-model="item.totalLimit"></u-input> -->
						<u-input type="number" input-align="right" input-width="160" placeholder="请输入限额" v-model="item.totalLimit" />
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
					stationIntervalName: [{
						required: true,
						message: '请选择区间',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					majorName: [{
						required: true,
						message: '请选择专业',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}]
				},
				showArriveDate: false,

				showPreArriveDate: false,

				// showSupplier: false,
				// supplierDefault: [0],
				stationIntervalId: '',
				stationIntervalName: '',
				stationIntervalList: [],
				stationIntervalDefult: [0],
				showStationInterval: false,

				showMajor: false,
				majorDefault: [0],
				majorList: [],
				list: [],
				hisList: [],
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
				this.getStationIntervalList();
				this.getMajorList();
			})
		},
		methods: {
			getStationIntervalList() {
				this.$axios.post('/stationInterval/findList', {
					"belongProjectId": app.globalData.projectId,
				}).then(res => {
					this.stationIntervalList = res.data.list;
				}).catch(err => {
					this.$message.error(err.msg)
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

				for (var i in data) {
					var num = -1;
					for (var j in this.list) {
						if (this.list[j].goodId == data[i].goodId) {
							num = j;
							break;
						}
					}
					if (num == -1) {
						data[i].num = 1;
					}
				}

				this.list = data;
				this.$forceUpdate()
			},
			getData(id) {
				this.$axios.get('/limit/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.limitDetails;
					this.hisList = JSON.parse(JSON.stringify(res.data.limitDetails));
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
			chooseStationInterval() {
				this.showStationInterval = true;
			},
			chooseMajor() {
				this.showMajor = true;
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
			confirmStationInterval(data) {
				this.$set(this.info, 'stationIntervalName', data[0].label);
				this.$set(this.info, 'stationIntervalId', data[0].value);
				for (var i in this.stationIntervalList) {
					if (this.stationIntervalList[i].id == data[0].value) {
						this.stationIntervalDefult.splice(0, 1, Number(i));
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
						"totalLimit": this.list[i].totalLimit
					})
				}
				this.$axios.post('/limit/save', {
					"projectId": app.globalData.projectId,
					"majorId": info.majorId,
					"stationIntervalId": info.stationIntervalId,
					"listLimitDetail": listLimitDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增限额成功',
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
						"totalLimit": this.list[i].totalLimit
					})
				}
				for (var i in this.hisList) {
					for (var j in goodDetail) {
						if (this.hisList[i].goodId == goodDetail[j].goodId) {
							goodDetail[j].id = this.hisList[i].id;
						}
					}
				}
				this.$axios.post('/limit/update', {
					"id": this.id,
					"projectId": app.globalData.projectId,
					"majorId": info.majorId,
					"stationIntervalId": info.stationIntervalId,
					"listLimitDetail": goodDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑限额成功',
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
			resetForm() {
				uni.navigateBack({
					delta: 1
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