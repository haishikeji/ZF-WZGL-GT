<template>
	<view style="height: 100vh;overflow-y: hidden;">
		<u-tabs class="tabbar" bg-color="#1677FF" active-color="white" inactive-color="#e0e0e0" :list="tabList"
			:is-scroll="false" :current="current" @change="changeTab"></u-tabs>

		<search-com-page title="调拨管理" subtitle="调拨单查询" placeholder="调拨单编号" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
			<image slot="topbtn" class="right-btn" src="../../../../common/img/add.png" @click="manageData('')"></image>
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<!-- 	<u-form-item label="调拨单编号" prop="name" :border-bottom="false">
						<u-input border height="95" v-model="form.name" placeholder="请输入调拨单编号" :clearable="false" />
					</u-form-item> -->
					<u-form-item label="单据状态" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择单据状态" v-model="approveStatusName"
							@click="chooseApprove">
						</z-select>
					</u-form-item>
					<u-form-item label="调出单位" :border-bottom="false" v-if="current==1">
						<z-select style="width: 100%;" placeholder="请选择调出单位" v-model="projectFromName"
							@click="chooseProject">
						</z-select>
					</u-form-item>
					<u-form-item label="调入单位" :border-bottom="false" v-if="current==0">
						<z-select style="width: 100%;" placeholder="请选择调入单位" v-model="projectToName"
							@click="chooseProject">
						</z-select>
					</u-form-item>

					<u-form-item label="日期" :border-bottom="false">
						<view class="date-select flex-n fac">
							<u-input class="input" border height="95" v-model="date" placeholder="请选择日期区间"
								:clearable="false" @click="showDate=true" />
							<image v-if="date" class="close" mode="widthFix"
								src="../../../../common/img/index/close.png" @click="date='';dateRange=[]"></image>
						</view>
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>
		<!-- 单据状态 -->
		<u-select v-model="showApproveStatus" label-name='codeName' :default-value="approveStatusDefault"
			value-name="codeValue" :list="approveStatusList" @confirm="confirmApprove" :mask-close-able="false">
		</u-select>
		<!-- 调入调出单位 -->
		<u-select v-model="showProject" label-name='projectName' :default-value="projectDefault" value-name="id"
			:list="projectList" @confirm="confirmProject" :mask-close-able="false">
		</u-select>
		<u-calendar v-model="showDate" mode="range" @change="changeDate"></u-calendar>
		<scroll-view class="scroll-Y" scroll-y="true" v-if="list.length>0">
			<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">

				<z-more>
					<view slot="top" class="comm-detail-text">
						<view>调拨单号：</view>
						<view class="right">{{item.allotNo}}</view>
						<template v-if="current==1">
							<view>调出单位：</view>
							<view class="right">{{item.projectNameFrom}}</view>
						</template>
						<template v-if="current==0">
							<view>调入单位：</view>
							<view class="right">{{item.projectNameTo}}</view>
						</template>
						<view>审核状态：</view>
						<view class="right">
							{{item.approveStatus==0?'待审核':''}}
							{{item.approveStatus==1?'审核中':''}}
							{{item.approveStatus==2?'审核驳回':''}}
							{{item.approveStatus==3?'审核成功':''}}
						</view>
					</view>
					<view slot="bottom" class="comm-detail-text">
						<view>审核人：</view>
						<view class="right">{{item.approverUserName}}</view>
						<view>审核时间：</view>
						<view class="right">{{item.approveTime}}</view>
					</view>
				</z-more>
				<view class="btn-area">
					<u-button size="mini" type="warning" plain shape="circle" @click="lookData(item.id)">查看详情</u-button>
					<template v-if="item.approveStatus==0||item.approveStatus==2">
						<u-button class="ml-20" size="mini" type="primary" plain shape="circle"
							@click="manageData(item.id)">编辑
						</u-button>
						<u-button class="ml-20" size="mini" type="error" plain shape="circle"
							@click="delVisible=true;id=item.id">删除
						</u-button>
					</template>
				</view>
			</z-card>
		</scroll-view>
		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
		<u-toast ref="uToast"></u-toast>
		<!-- 删除 -->
		<u-modal v-model="delVisible" show-cancel-button content="该操作将删除该订单所有数据,
	确定要删除吗？" @confirm="deleteData"></u-modal>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				allotNo: '',

				approveStatus: '',
				approveStatusName: '',
				approveStatusList: [],
				showApproveStatus: false,
				approveStatusDefault: [0],

				projectFromId: '',
				projectFromName: '',
				projectToId: '',
				projectToName: '',
				projectList: [],
				showProject: false,
				projectDefault: [0],

				date: '',
				dateRange: [],
				showDate: false,

				current: 0,
				tabList: [{
					name: '调拨入库'
				}, {
					name: '调拨出库'
				}],
				list: [],
				form: {},
				rules: {},
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				id: '',

				delVisible: false,
				pageSize: 20,
				currentPage: 1,
				total: 0,

			}
		},
		onShow() {
			this.$nextTick(() => {
				this.getApproveStatusList();
				this.getProjectList();
			})
		},
		onReady() {
			this.getDataList();
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
		methods: {
			changeTab(index) {
				this.current = index;
				this.resetForm();
				this.searchList();
			},
			getProjectList() {
				this.$axios.post('/project/findList', {}).then(res => {
					this.projectList = res.data.list;
					for (var i in this.projectList) {
						if (this.projectList[i].id == app.globalData.projectId) {
							this.projectList.splice(i, 1);
							break;
						}
					}
				}).catch(err => {
					console.log(err.msg)
				})
			},
			getApproveStatusList() {
				this.approveStatusList = uni.getStorageSync('dic').list.filter((item) => item.codeKind == 'approveStatus');
			},
			searchChange(data) {
				this.allotNo = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/allot/allotList', {
					"projectId": app.globalData.projectId,
					"allotNo": this.allotNo,
					"projectFromId": this.current == 1 ? this.projectFromId : '',
					"projectToId": this.current == 0 ? this.projectToId : '',
					"allotType": this.current == 0 ? 2 : 1,
					"approveStatus": this.approveStatus,
					"beginTime": this.dateRange ? this.dateRange[0] : '',
					"endTime": this.dateRange ? this.dateRange[0] : '',
					pageNum: this.currentPage,
					pageSize: this.pageSize,
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					this.total = res.data.total;
					for (var i in this.list) {
						this.$set(this.list[i], 'approveStatus', this.list[i].approveInfo[0].approveStatus);
						this.$set(this.list[i], 'approverUserName', this.list[i].approveInfo[0].approverUserName)
						this.$set(this.list[i], 'approveTime', this.list[i].approveInfo[0].approveTime)
					}
					if (this.list.length == this.total) {
						this.status = 'nomore';
					} else {
						this.status = 'loadmore';
					}
				}).catch(err => {
					this.status = 'loadmore';
				})
			},
			lookData(id) {
				uni.navigateTo({
					url: '../detail/detail?id=' + id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			manageData(id) {
				uni.navigateTo({
					url: '../manage/manage?state='+(this.current==0?2:1)+'&id=' + id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			//删除数据
			deleteData() {
				this.$axios.post('/allot/logicDeleteByIds', this.id).then(res => {
					this.$refs.uToast.show({
						title: '删除成功',
						type: 'success'
					})
					this.searchList();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},

			confirmApprove(data) {
				console.log(data)
				this.approveStatusName = data[0].label;
				this.approveStatus = data[0].value;
				for (var i in this.approveStatusList) {
					if (this.approveStatusList[i].codeValue == this.approveStatus) {
						this.approveStatusDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			confirmProject(data) {
				console.log(data)
				if (this.current == 1) {
					this.projectFromName = data[0].label;
					this.projectFromId = data[0].value;
				} else {
					this.projectToName = data[0].label;
					this.projectToId = data[0].value;
				}

				for (var i in this.projectList) {
					if (this.projectList[i].id == data[0].value) {
						this.projectDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			changeDate(e) {
				console.log(e)
				this.dateRange = [e.startDate, e.endDate];
				this.date = e.startDate + '  至  ' + e.endDate;
			},
			chooseApprove() {
				this.showApproveStatus = true;
			},
			chooseProject() {
				this.showProject = true;
			},
			resetForm() {
				this.date = '';
				this.dateRange = [];

				this.projectFromId = '';
				this.projectFromName = '';
				this.projectToId = '';
				this.projectToName = '';
				this.projectDefault.splice(0, 1, 0);

				this.approveStatusName = '';
				this.approveStatus = '';
				this.approveStatusDefault.splice(0, 1, 0);
			},

		}
	}
</script>

<style scoped lang="scss">
	.scroll-Y {
		height: calc(100vh - 406rpx - var(--status-bar-height));
	}

	.tabbar {
		margin-top: 100rpx;
		margin-bottom: -110rpx;
	}

	.date-select {
		position: relative;
		width: 100%;

		.close {
			position: absolute;
			width: 40rpx;
			right: 20rpx;
			z-index: 100;
		}
	}

	.right-btn {
		width: 38rpx;
		height: 38rpx;
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
