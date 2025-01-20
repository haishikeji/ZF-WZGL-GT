<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'领料单'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit(0)">提交</u-button>
			<u-button slot="right" class="mr-30" size="mini" type="success" @click="submit(-1)">保存</u-button>
		</u-navbar>
		<view class="info mt-24">
			<u-form :model="info" ref="uForm" label-width="200rpx" :border-bottom="false">
				<!-- <u-form-item label="用料站区间" prop="stationIntervalName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择用料站区间"
						v-model="info.stationIntervalName" @click="chooseStationInterval">
					</z-select>
				</u-form-item> -->
				<u-form-item label="区间" prop="planOverDate">
					<view slot="right">
						<uni-card v-if="info.stationIntervalId" style=" display: flex;flex-direction: column;
						justify-content: center;  border: 1px solid blue; float: left;">
							<view class="tag-item">{{info.stationIntervalName}}
								<text v-if="id==''" style="padding-left: 5rpx; font-size: 30rpx; font-weight: bold;"
									@click="removeTag()"> x </text>
							</view>
						</uni-card>
						<uni-card style=" display: flex;flex-direction: column;
								justify-content: center;  float: left;">
							<view v-if="id ==''" class="tag-item2"> <text style=" font-size: 25rpx;  " @click="addTag">
									选择 </text></view>
						</uni-card>
					</view>
				</u-form-item>
				
				<u-form-item label="专业" prop="majorName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择专业"
						v-model="info.majorName" @click="chooseMajor">
					</z-select>
				</u-form-item>
				<u-form-item label="领料团队" prop="pickCompanyName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择领料团队"
						v-model="info.pickCompanyName" @click="choosePickCompany">
					</z-select>
					
				</u-form-item>
				<u-form-item label="领料人" prop="pickUserName">
					<u-input class="input" a input-align="right"  placeholder="请输入领料人" v-model="info.pickUserName" />
				</u-form-item>
			</u-form>
		</view>
		<!-- 用料站区间 -->
		<u-select v-model="showStationInterval" label-name='stationIntervalName' :default-value="stationIntervalDefault"
			value-name="id" :list="stationIntervalList" @confirm="confirmStationInterval" :mask-close-able="false">
		</u-select>
		<!-- 专业 -->
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefault" value-name="id"
			:list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
		<!-- 领料团队 -->
		<u-select v-model="showPickCompany" label-name='pickCompanyName' :default-value="pickCompanyDefault"
			value-name="id" :list="pickCompanyList" @confirm="confirmPickCompany" :mask-close-able="false">
		</u-select>
		<view class="common-infolist-title mt-24">
			<view class="flex-1 title">物资信息</view>
			<u-button size="mini" type="primary" plain shape="circle" @click="addMaterial">新增物资</u-button>
		</view>
		<scroll-view class="scroll" :scroll-y="true">
			<z-card :shadow="false" class="mt-24" v-for="(item,index) in list" :key="index">
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
						<!-- <u-number-box v-model="item.num"></u-number-box> -->
						<!-- <u-input height="40" input-align="right" v-model="info.intro" /> -->
						<u-input type="number" input-align="right" placeholder="请输入数量" v-model="item.num" />
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
					approveStatus:'',
				info: {
					pickCompanyId: '',
					pickCompanyName: '',
					majorId: '',
					majorName: '',
					stationIntervalId: '',
					stationIntervalName: '',
					approveStatus:'',
					pickUserName: '',
				},
				blogPopupTags:[],
				stationIntervalList:[],
				restaurants:[],
				chooseList:[],
				rules: {
					pickCompanyName: [{
						required: true,
						message: '请选择领料团队',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					majorName: [{
						required: true,
						message: '请选择专业',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					// pickUserName : [{
					// 	required: true,
					// 	message: ' ',
					// 	// 可以单个或者同时写两个触发验证方式 
					// 	trigger: ['change', 'blur'],
					// }],
					stationIntervalName: [{
						required: true,
						message: '请选择用料站区间',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}]
				},

				showPickCompany: false,
				pickCompanyDefault: [0],
				pickCompanyList: [],

				showMajor: false,
				majorDefault: [0],
				majorList: [],

				showStationInterval: false,
				stationIntervalDefault: [0],
				stationIntervalList: [],

				hisList: [],
				list: []
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
				this.getPickCompanyList();
				this.getMajorList();
			})
		},
		methods: {
			addTag() {
				uni.navigateTo({
					url: '../blogPopup/blogPopup'
				})
			
			},
			removeTag() {
				this.info.stationIntervalId = '';
				this.info.stationIntervalName = '';
			},
			
			upStationIntervalData(data) {
				console.log(data)
				this.info.stationIntervalId = data[0].id
				this.info.stationIntervalName = data[0].stationIntervalName
			},
			
			
			//站区间列表
			getStationIntervalList() {
				this.$axios.post('/stationInterval/findList', {
					"belongProjectId": app.globalData.projectId,
				}).then(res => {
					this.stationIntervalList = res.data.list;
					var stationIntervalIdsList =  [];
					
						this.stationIntervalList = res.data.list.filter( si => !stationIntervalIdsList.includes(si.id));
						this.restaurants = res.data.list.filter( si => !stationIntervalIdsList.includes(si.id));
					}).catch(err => {
						
					this.restaurants = res.data.list.filter( si => !stationIntervalIdsList.includes(si.id));
				}).catch(err => {
					console.log(err.msg)
				})
			},
			//领料单位列表
			getPickCompanyList() {
				this.$axios.post('/pickCompany/findList', {}).then(res => {
					this.pickCompanyList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			//专业列表
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
				var ids = '';
				for (var i in data) {
					ids += data[i].id + ',';
				}
				if (ids.length > 0) {
					ids = ids.substring(0, ids.length - 1);
					this.getDetailInfo(ids);
				}
				this.$forceUpdate()
			},
			//获取详细信息
			getDetailInfo(ids) {
				this.$axios.post('/good/findByIds', ids).then(res => {
					console.log(res.data)
					var data = res.data.list;
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
					console.log(data)
					this.list = data;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			getData(id) {
				this.$axios.get('/pickOrder/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.goodDetail || [];
					this.hisList = JSON.parse(JSON.stringify(this.list));
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			addMaterial() {
				uni.navigateTo({
					url: '../material/material?type=1&state=0',
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
			choosePickCompany() {
				this.showPickCompany = true;
			},
			chooseStationInterval() {
				this.showStationInterval = true;
			},
			chooseMajor() {
				this.showMajor = true;
			},
			confirmPickCompany(data) {
				console.log(data)
				this.$set(this.info, 'pickCompanyName', data[0].label);
				this.$set(this.info, 'pickCompanyId', data[0].value);
				for (var i in this.pickCompanyList) {
					if (this.pickCompanyList[i].id == data[0].value) {
						this.pickCompanyDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			confirmStationInterval(data) {
				console.log(data)
				this.$set(this.info, 'stationIntervalName', data[0].label);
				this.$set(this.info, 'stationIntervalId', data[0].value);
				for (var i in this.stationIntervalList) {
					if (this.stationIntervalList[i].id == data[0].value) {
						this.stationIntervalDefault.splice(0, 1, Number(i));
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
					goodDetail.push({
						"goodId": this.list[i].goodId,
						"num": this.list[i].num
					})
				}
				this.$axios.post('/pickOrder/save', {
					"projectId": app.globalData.projectId,
					"majorId": info.majorId,
					"pickCompanyId": info.pickCompanyId,
					"stationIntervalId": info.stationIntervalId,
					   "pickUserName": info.pickUserName,
					   "approveStatus": this.approveStatus,
					"goodDetail": goodDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增领料单成功',
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
				console.log(goodDetail)
				this.$axios.post('/pickOrder/update', {
					"id": this.id,
					"majorId": info.majorId,
					"pickCompanyId": info.pickCompanyId,
					"stationIntervalId": info.stationIntervalId,
					"goodDetail": goodDetail,
					   "pickUserName": info.pickUserName,
					   "approveStatus": this.approveStatus,
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑领料单成功',
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
