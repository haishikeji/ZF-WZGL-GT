<template>
	<view>
		<search-com-page title="定测量管理" subtitle="物资查询" placeholder="物资编号" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
			<image slot="topbtn" class="right-btn" src="../../../../common/img/add.png" @click="manageData('')"></image>
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['toast']">
					<!-- 	<u-form-item label="物资编号" prop="name" :border-bottom="false">
						<u-input border height="95" v-model="form.name" placeholder="请输入物资编号" :clearable="false" />
					</u-form-item> -->
					<u-form-item label="物资名称" :border-bottom="false">
						<u-input border height="95" v-model="goodName" placeholder="请输入物资名称" :clearable="false" />
					</u-form-item>
					<u-form-item label="规格型号" prop="intro" :border-bottom="false">
						<u-input border height="95" v-model="specsModel" placeholder="请输入规格型号" :clearable="false" />
					</u-form-item>
					<u-form-item label="专业" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择专业" v-model="majorName"
							@click="chooseMajor">
						</z-select>
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefult"
			value-name="id" :list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
		
		
		<view class="budget_total flex-n fac">
			<!-- <view class="mr-20">
				{{approveStatus==''?'待提交':''}}
				{{approveStatus=='0'?'待审核':''}}
				{{approveStatus=='1'?'审核中':''}}
				{{approveStatus=='2'?'审核驳回':''}}
				{{approveStatus=='3'?'审核成功':''}}
			</view> -->

			总计: {{total}}
		</view>
		<!-- <view class="budget_total">待审核  总计: 254</view> -->
		<!-- <u-empty class="mt-100" text="已提交审核" mode="order"></u-empty> -->

		<!-- 待审核状态无列表 -->
		<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">

			<z-more>
				<view slot="top" class="comm-detail-text">
					<view>物资编号：</view>
					<view class="right">{{item.goodCode}}</view>
					<view>物资名称：</view>
					<view class="right">{{item.goodName}}</view>
					<view>专业名称：</view>
					<view class="right">{{item.majorName}}</view>
					<view>合 计：</view>
					<view class="right">{{item.total}}</view>
				</view>
				<view slot="bottom" class="comm-detail-text">
					<view>单 位：</view>
					<view class="right">{{item.unit}}</view>
					<block v-for="(itm,idx) in stationScheduleList" :key="idx">
						<view>{{itm.stationIntervalName}}：</view>
						<view class="right">{{item[itm.stationIntervalName]}}</view>
					</block>
					<view>备 注：</view>
					<view class="right">{{item.remarks}}</view>
				</view>
			</z-more>

			<view class="btn-area" >
				<u-button v-if = "isManage" size="mini" type="primary" plain shape="circle" @click="manageData(item.id)">编辑</u-button>
				<u-button v-if = "isManage" class="ml-20" size="mini" type="error" plain shape="circle"
					@click="delVisible=true;id=item.id">删除
				</u-button>
			</view>
		</z-card>
		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
		<u-toast ref="uToast"></u-toast>
		<!-- <z-bottom-fixed v-if="approveStatus==''">
			<z-button type="primary" @click="submitVisible=true">提交</z-button>
		</z-bottom-fixed> -->
		<!-- 删除 -->
		<u-modal v-model="delVisible" show-cancel-button content="该操作将删除该订单所有数据,
		确定要删除吗？" @confirm="deleteData"></u-modal>
		<!-- 提交 -->
		<u-modal v-model="submitVisible" show-cancel-button content="该操作将提交该订单所有数据进
行审核流程，确定要提交吗？" @confirm="submitData"></u-modal>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {	
				majorId:'',
				majorName:'',
				majorList: [],
				majorDefult: [0],
				isManage: false,
				
				goodCode: '',
				goodName: '',
				specsModel: '',
				id: '',
				approveStatus: '',
				list: [],
				stationScheduleList: [], //动态列表
				form: {},
				rules: {},
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				total: 0,
				delVisible: false,
				submitVisible: false,
				pageSize: 20,
				showMajor: false,
				currentPage: 1,
			}
		},
		onReachBottom() {
			if (this.list.length == this.total) {
				this.status = 'nomore';
			} else {
				this.status = 'loading';
				this.currentPage++;
				this.getDataList();
			}
		},
		onShow() {
			this.isManage = uni.getStorageSync('isManage');
			this.getDataList();
			this.getState();
			this.getStationSchedule();
			this.getMajorList()
		},

		methods: {
			
		 getMajorList() {
					this.$axios.post('/major/findList', {
					"projectId": app.globalData.projectId,
					}).then(res => {
						this.majorList = res.data.list;
					}).catch(err => {
						this.$message.error(err.msg)
					})
				},
			confirmMajor(data) {
				console.log(data)
				this.majorName = data[0].label;
				this.majorId = data[0].value;
				for (var i in this.approveStatusList) {
					if (this.majorList[i].id == this.majorId) {
						this.approveStatusDefault.splice(0, 1, Number(i));
						break;
					}
				}
				this.searchList();
			},
			chooseMajor() {
				this.showMajor = true;
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			searchChange(data) {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.goodCode = data;
				this.getDataList();
			},
			getStationSchedule() {
				this.$axios.get('/stationSchedule/findStationSchedule/' + app.globalData.projectId).then(res => {
					this.stationScheduleList = res.data.list;
				}).catch(err => {
					this.$message.error(err.msg)
				})
			},
			getState() {
				this.$axios.post('/workflow/threeEstimateStatus', {
					"busId": app.globalData.projectId + '3'
				}).then(res => {
					console.log(res.data)
					this.approveStatus = res.data.approveStatus;
				}).catch(err => {})
			},
			getDataList() {
				uni.showLoading({
					title: '加载中'
				});
				this.$axios.post('/threeEstimate/budgetFixedMeasureList', {
					"projectId": app.globalData.projectId,
					"goodCode": this.goodCode,
					"goodName": this.goodName,
					"majorId": this.majorId,
					"specsModel": this.specsModel,
					pageNum: this.currentPage,
					pageSize: this.pageSize,
				}).then(res => {
					// this.list = res.data.list;
					this.total = res.data.total;
					
						var list = res.data.list;
						this.list = this.list.concat(list);
						if (this.list.length == this.total) {
							this.status = 'nomore';
						} else {
							this.status = 'loadmore';
						}
					uni.hideLoading();
				}).catch(err => {
					this.status = 'loadmore';
					uni.hideLoading();
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			manageData(id) {
				uni.navigateTo({
					url: '../manage/manage?id=' + id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},

			//删除数据
			deleteData() {
				this.$axios.post('/threeEstimate/logicDeleteByIds', this.id).then(res => {
					this.$refs.uToast.show({
						title: '删除成功',
						type: 'success'
					})
					this.getDataList();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			submitData() {
				this.$axios.post('/threeEstimate/commitFixed', {
					"projectId": app.globalData.projectId,
				}).then(res => {
					this.$refs.uToast.show({
						title: '提交成功',
						type: 'success'
					})
					this.getDataList();
					this.getState();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			resetForm() {
				this.goodName = '';
				this.specsModel = '';
			},
		}
	}
</script>

<style scoped lang="scss">
	.right-btn {
		width: 38rpx;
		height: 38rpx;
	}

	.budget_total {
		font-weight: 600;
		font-size: 30rpx;
		color: #333333;
		padding: 0 24rpx;
		margin-top: 40rpx;
	}

	.list {
		position: relative;

		.btn-area {
			position: absolute;
			bottom: 24rpx;
			left: 24rpx;
		}
	}
</style>
