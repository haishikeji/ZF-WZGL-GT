<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'定测量'" title-size="36"></u-navbar>
		<u-form :model="info" ref="uForm" label-width="200" label-position="left" :error-type="['toast']">
			<z-card class="info-detail mt-20">
				<view v-if="!info.goodCode">请点击下方进行物资选择</view>
				<u-row gutter="16" v-else>
					<u-col span="3">
						<view>物资编号</view>
					</u-col>
					<u-col span="9" text-align="right">
						<view>{{info.goodCode}}</view>
					</u-col>
					<u-col class="mt-20" span="3">
						<view>物资名称</view>
					</u-col>
					<u-col span="9" text-align="right">
						<view>{{info.goodName}}</view>
					</u-col>
					<u-col class="mt-20" span="3">
						<view>规格型号</view>
					</u-col>
					<u-col span="9" text-align="right">
						<view>{{info.specsModelName}}</view>
					</u-col>
					<u-col span="3" class="mt-20">
						<view>计量单位</view>
					</u-col>
					
					<u-col span="9" text-align="right">
						<view>{{info.measureUnit}}</view>
					</u-col>
					<!-- <u-col class="mt-20" span="3">
						<view>专业</view>
					</u-col>
					<u-col span="3" text-align="right">
						<view>{{info.majorName}}</view>
					</u-col> -->
					<u-col span="12" class="mt-20">
						<view>备注</view>
					</u-col>
					<u-col span="12" class="mt-20">
						<view>{{info.remarks}}</view>
					</u-col>
				</u-row>
			</z-card>
			<view class="form-bg">
				<u-form-item label="专业" prop="majorName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择专业"
						v-model="info.majorName" @click="chooseMajor">
					</z-select>
				</u-form-item>
				<u-form-item :label="item.stationIntervalName" prop="name" v-for="(item,index) in stationScheduleList"
					:key="index">
					<u-input type="number" input-align="right" v-model="item.stationScheduleValue"
						placeholder="请输入数量" />
				</u-form-item>
			</view>
		</u-form>
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefault" value-name="id"
			:list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
		<z-bottom-fixed class="flex-n fac fjb">
			<template v-if="!info.goodCode">
				<z-button @click="chooseMaterial">选择物资</z-button>
			</template>
			<template v-else>
				<view class="flex-n fac fjb">
					<z-button width="339rpx" @click="chooseMaterial">选择物资</z-button>
					<z-button width="339rpx" type="primary" @click="submit">保存</z-button>
				</view>
			</template>
		</z-bottom-fixed>
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
				showMajor: false,
				majorDefault: [0],
				majorList: [],
				id: '',
				info: {},
				rules: {},
				stationScheduleList: []
			}
		},
		onLoad(options) {
			console.log(options)
			this.id = options.id;
			this.getStationSchedule();
			this.getMajorList()

		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		methods: {
			chooseMajor() {
				this.showMajor = true;
			},
			getMajorList() {
				this.$axios.post('/major/findList', {}).then(res => {
					console.log(res.data.list)
					this.majorList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			getStationSchedule() {
				this.$axios.get('/stationSchedule/findStationSchedule/' + app.globalData.projectId).then(res => {
					if (this.id) {
						this.getData(res.data.list);
					} else
						this.stationScheduleList = res.data.list;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			upData(data) {
				console.log(data)
				this.info = data;
				this.$set(this.info, 'unit', data.measureUnit);
				this.$forceUpdate()
			},
			getData(data) {
				this.$axios.get('/threeEstimate/findFixedMeasureById/' + this.id).then(res => {
					this.info = res.data;
					var column = this.info.column;
					var stationScheduleList = []
					for (var i in column) {
						for (var j in data) {
							if (column[i].id == data[j].id) {
								stationScheduleList.push({
									stationScheduleValue: column[i].stationScheduleValue,
									id: column[i].stationScheduleId,
									stationScheduleId: column[i].id,
									stationIntervalName: column[i].stationIntervalName
								})
							}
						}
					}
					this.stationScheduleList = stationScheduleList;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			chooseMaterial() {
				uni.navigateTo({
					url: '../../../common/material/material?type=0',
					success: res => {
						if (this.info.goodId)
							uni.setStorage({
								key: 'materialInfo',
								data: this.info
							})
					},
					fail: () => {},
					complete: () => {}
				});
			},

			submit() {
				if (!this.info.goodCode) {
					return this.$refs.uToast.show({
						title: '请选择物资',
						type: 'warning'
					})
				}
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
				var stationScheduleModels = [];
				for (var i in this.stationScheduleList) {
					stationScheduleModels.push({
						"stationScheduleId": this.stationScheduleList[i].id,
						"stationScheduleValue": this.stationScheduleList[i].stationScheduleValue
					})
				}
				this.$axios.post('/threeEstimate/saveFixedMeasure', {
					"projectId": app.globalData.projectId,
					"stationScheduleModels": stationScheduleModels,
					"goodId": info.goodId,
					"remarks": info.remarks,
					"specsModelName": info.specsModelName,
					"majorId": info.majorId,
					"unit": info.unit
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增定测量成功',
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
			editSubmit() {
				var info = this.info;
				var stationScheduleModels = [];
				for (var i in this.stationScheduleList) {
					stationScheduleModels.push({
						"id": this.stationScheduleList[i].id,
						"stationScheduleId": this.stationScheduleList[i].stationScheduleId,
						"stationScheduleValue": this.stationScheduleList[i].stationScheduleValue
					})
				}
				this.$axios.post('/threeEstimate/updateFixedMeasure', {
					"id": this.id,
					"stationScheduleModels": stationScheduleModels,
					"goodId": info.goodId,
					"remarks": info.remarks,
					"specsModelName": info.specsModelName,
					"majorId": info.majorId,
					"unit": info.unit
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑定测量成功',
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
<style lang="scss" scoped>
	// .u-form-item{
	// 	padding: 0!important;
	// }
	.form-bg {
		background: white;
		padding: 0 24rpx;
		margin-top: 20rpx;
	}

	.info-detail {
		color: #aaa;
	}
</style>
