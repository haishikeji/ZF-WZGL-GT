<template>
	<view>
		<search-com-page title="退货管理" subtitle="退货单查询" placeholder="退货单号" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
			<image slot="topbtn" class="right-btn" src="../../../../common/img/add.png" @click="manageData('')"></image>
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<!-- 	<u-form-item label="退货单号" prop="name" :border-bottom="false">
					<u-input border height="95" v-model="form.name" placeholder="请输入退货单号" :clearable="false" />
				</u-form-item> -->
					<u-form-item label="审核状态" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择审核状态" v-model="approveStatusName"
							@click="chooseApprove">
						</z-select>
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>
		<u-select v-model="showApproveStatus" label-name='codeName' :default-value="approveStatusDefault"
			value-name="codeValue" :list="approveStatusList" @confirm="confirmApprove" :mask-close-able="false">
		</u-select>
		<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">
			<z-more>
				<view slot="top" class="comm-detail-text">
					<view>退货单编号：</view>
					<view class="right">{{item.returnNo}}</view>
					<view>制单人：</view>
					<view class="right">{{item.createUserName}}</view>
					<view>审核状态：</view>
					<view class="right">
						{{item.approveStatus==0?'待审核':''}}
						{{item.approveStatus==1?'审核中':''}}
						{{item.approveStatus==2?'审核驳回':''}}
						{{item.approveStatus==3?'审核成功':''}}
					</view>
				</view>
				<view slot="bottom" class="comm-detail-text">
					<view>创建时间：</view>
					<view class="right">{{item.createTime}}</view>
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
				returnNo: '',
				approveStatus: '',
				approveStatusName: '',
				approveStatusList: [],
				showApproveStatus: false,
				approveStatusDefault: [0],

				form: {},
				rules: {},

				delVisible: false,
				showType: false,
				list: [],

				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				id: '',
				pageSize: 20,
				currentPage: 1,
				total: 0,
			}
		},
		onShow() {
			this.$nextTick(() => {
				this.getApproveStatusList();
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
			getApproveStatusList() {
				this.approveStatusList = uni.getStorageSync('dic').list.filter((item) => item.codeKind == 'approveStatus');
			},
			searchChange(data) {
				this.returnNo = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/returnGood/returnGoodList', {
					"projectId": app.globalData.projectId,
					"returnNo": this.returnNo,
					"approveStatus": this.approveStatus,
					pageNum: this.currentPage,
					pageSize: this.pageSize,
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					this.total = res.data.total;
					for (var i in this.list) {
						this.$set(this.list[i], 'approveStatus', this.list[i].approveInfo[0].approveStatus)
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
			//删除数据
			deleteData() {
				this.$axios.post('/returnGood/logicDeleteByIds', this.id).then(res => {
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
					if (this.approveStatusList[i].id == this.approveStatusName) {
						this.approveStatusDefault.splice(0, 1, Number(i));
						break;
					}
				}
				this.searchList();
			},
			chooseApprove() {
				this.showApproveStatus = true;
			},

			resetForm() {
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
</style>
