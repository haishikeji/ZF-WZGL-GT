<template>
	<view>
		<search-com-page title="盘点管理" subtitle="盘点单查询" placeholder="盘点单号" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
			<image slot="topbtn" class="right-btn" src="../../../../common/img/add.png" @click="manageData('')"></image>
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<!-- 	<u-form-item label="盘点单号" prop="name" :border-bottom="false">
					<u-input border height="95" v-model="form.name" placeholder="请输入盘点单号" :clearable="false" />
				</u-form-item> -->
					<u-form-item label="审核状态" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择审核状态" v-model="approveStatusName"
							@click="chooseApprove">
						</z-select>
					</u-form-item>
					<u-form-item label="日期" prop="date" :border-bottom="false">
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
		<u-calendar v-model="showDate" mode="range" @change="changeDate"></u-calendar>
		<!-- 审核状态 -->
		<u-select v-model="showApproveStatus" label-name='codeName' :default-value="approveStatusDefault"
			value-name="codeValue" :list="approveStatusList" @confirm="confirmApprove" :mask-close-able="false">
		</u-select>
		<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">

			<z-more>
				<view slot="top" class="comm-detail-text">
					<view>盘点单号：</view>
					<view class="right">{{item.checkNo}}</view>
					<view>盘点仓库：</view>
					<view class="right">{{item.storeName}}</view>
					<view>盘点日期：</view>
					<view class="right">{{item.checkTime}}</view>
				</view>
				<view slot="bottom" class="comm-detail-text">
					<view>盈亏状态：</view>
					<view class="right">{{item.resultNum}}</view>
					<view>审核状态：</view>
					<view class="right">
					     {{item.approveStatus==-1?'已保存':''}}
						{{item.approveStatus==0?'待审核':''}}
						{{item.approveStatus==1?'审核中':''}}
						{{item.approveStatus==2?'审核驳回':''}}
						{{item.approveStatus==3?'审核成功':''}}
					</view>
					<view>盘点人：</view>
					<view class="right">{{item.createUserName}}</view>
					<view>创建时间：</view>
					<view class="right">{{item.createTime}}</view>
				</view>
			</z-more>
			<view class="btn-area">
				<u-button size="mini" type="warning" plain shape="circle" @click="lookData(item.id)">查看</u-button>
				<template v-if="item.approveStatus==-1||item.approveStatus==2">
					<u-button class="ml-20" size="mini" type="primary" plain shape="circle"
						@click="manageData(item.id)">编辑</u-button>
				</template>
				<!-- <u-button class="ml-20" size="mini" type="primary" plain shape="circle" @click="lookSheet(item.id)">盘点表
				</u-button> -->
				<template v-if="item.approveStatus==-1||item.approveStatus==2">
					<u-button class="ml-20" size="mini" type="error" plain shape="circle" @click="delVisible=true;id=item.id">删除
					</u-button>
				</template>
			</view>
		</z-card>

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
				checkNo: '',

				date: '',
				dateRange: [],
				showDate: false,

				approveStatus: '',
				approveStatusName: '',
				approveStatusList: [],
				showApproveStatus: false,
				approveStatusDefault: [0],

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
			})
			this.searchList();
		},
		onReady() {
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
			getApproveStatusList() {
				this.approveStatusList = uni.getStorageSync('dic').list.filter((item) => item.codeKind == 'approveStatus');
			},
			searchChange(data) {
				this.checkNo = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/check/checkList', {
					"projectId": app.globalData.projectId,
					"checkNo": this.checkNo,
					"approveStatus": this.approveStatus,
					"beginTime": this.dateRange ? this.dateRange[0] : '',
					"endTime": this.dateRange ? this.dateRange[1] : '',
					pageNum: this.currentPage,
					pageSize: this.pageSize,
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					this.total = res.data.total;
					for (var i in this.list) {
						this.$set(this.list[i], 'approveStatus', this.list[i].approveInfo[0].approveStatus)
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
					url: '../manage/manage?id=' + id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			lookSheet(id){
				uni.navigateTo({
					url: '../inventorysheet/inventorysheet?id='+id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},
			//删除数据
			deleteData() {
				this.$axios.post('/check/logicDeleteByIds', this.id).then(res => {
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
		
			changeDate(e) {
				console.log(e)
				this.dateRange = [e.startDate, e.endDate];
				this.date = e.startDate + '  至  ' + e.endDate;
			},
			chooseApprove() {
				this.showApproveStatus = true;
			},
		
			resetForm() {
				this.date = '';
				this.dateRange = [];
				this.approveStatusName = '';
				this.approveStatus = '';
				this.approveStatusDefault.splice(0, 1, 0);
			},

		}
	}
</script>

<style scoped lang="scss">
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
</style>
