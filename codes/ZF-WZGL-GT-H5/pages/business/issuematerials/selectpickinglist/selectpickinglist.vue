<template>
	<view class="material">
		<!-- <u-navbar  back-icon-color="#1677FF" title="选择物资"  title-size="36"></u-navbar> -->
		<search-com-page title="选择领料单" placeholder="领料单号" @searchChange="searchChange" @submit="searchList"
			@resetForm="resetForm">

			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<u-form-item label="领料申请人" :border-bottom="false">
						<u-input border height="95" v-model="createUserName" placeholder="请输入领料申请人"
							:clearable="false" />
					</u-form-item>
				</u-form>
			</view>

		</search-com-page>
		<z-card :class="[chooseList.indexOf(item.id)>-1?'material_check':'']" class="mt-20" v-for="(item,index) in list"
			:key="index" @click.native="selectMaterial(item.id)">
			<u-row gutter="16">
				<!-- <u-col span="3">
					<view></view>
				</u-col>
				<u-col span="9" text-align="right">
					<view class="detail" @click.stop="lookDetail(item.id)">查看详情</view>
				</u-col> -->
				<u-col span="3" class="mt-20">
					<view>领料单号</view>
				</u-col>
				<u-col span="9" class="mt-20" text-align="right">
					<view class="flex-n fje">{{item.pickOrderNo}}</view>
				</u-col>
				<u-col class="mt-20" span="3">
					<view>站区间</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.stationIntervalName}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>申请人</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.createUserName}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>审核状态</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>
						{{item.approveStatus==0?'待审核':''}}
						{{item.approveStatus==1?'审核中':''}}
						{{item.approveStatus==2?'审核驳回':''}}
						{{item.approveStatus==3?'审核成功':''}}
					</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>审核时间</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.approveTime}}</view>
				</u-col>
			</u-row>
		</z-card>
		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
		<u-toast ref="uToast"></u-toast>
		<u-popup v-model="showDetail" mode="center">
			<z-card title="领料单详情">
				<view class="comm-detail-text detail-info">

					<view>领料单号：</view>
					<view class="right">12034131310000</view>
					<view>仓库：</view>
					<view class="right">12034131310000</view>
					<view>站区间：</view>
					<view class="right">12034131310000</view>
					<view>申请人：</view>
					<view class="right">12034131310000</view>
					<view>审核状态：</view>
					<view class="right">12034131310000</view>
					<view>审核时间：</view>
					<view class="right">12034131310000</view>
				</view>
				<u-divider class="mt-24">物资列表</u-divider>
				<scroll-view scroll-y="true" style="height: 500rpx;">
					<view class="comm-detail-text detail-info ">
						<view>物资编号：</view>
						<view class="right">12034131310000</view>
						<view>物资名称：</view>
						<view class="right">12034131310000</view>
						<view>规格型号：</view>
						<view class="right">12034131310000</view>
						<view>计量单位：</view>
						<view class="right">12034131310000</view>
						<view>数量：</view>
						<view class="right">12034131310000</view>
					</view>

				</scroll-view>
			</z-card>

		</u-popup>

		<z-bottom-fixed v-if="chooseList.length>0">
			<z-button type="primary" @click="submit">确定</z-button>
		</z-bottom-fixed>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				list: [],
				chooseList: [],
				state: 0, //显示的内容控制

				showDetail: false,
				form: {},
				rules: {},
				pickOrderNo: '',
				createUserName: '',
				pageSize: 20,
				currentPage: 1,
				total: 0,
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
			}
		},
		onShow() {
			this.searchList();
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
			lookDetail(id) {
				this.showDetail = true;
			},

			searchChange(data) {
				this.pickOrderNo = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/pickOrder/pickOrderList', {
					"projectId": app.globalData.projectId,
					createUserName: this.createUserName,
					pickOrderNo: this.pickOrderNo,
					 approveStatus:3,
					 queryType: 1,
					pageNum: this.currentPage,
					pageSize: this.pageSize
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					for (var i in this.list) {
						this.$set(this.list[i], 'approveStatus', this.list[i].approveInfo[0]
							.approveStatus)

						this.$set(this.list[i], 'approveTime', this.list[i].approveInfo[0].approveTime)
					}
					// this.setMultiSelected();
					this.total = res.data.total;
					if (this.list.length == this.total) {
						this.status = 'nomore';
					} else {
						this.status = 'loadmore';
					}
				}).catch(err => {
					this.status = 'loadmore';
					// this.$refs.uToast.show({
					// 	title: err.msg,
					// 	type: 'error'
					// })
				})
			},
			selectMaterial(id) {
				console.log(id)
				var num = this.chooseList.indexOf(id);
				console.log(num)
				if (num > -1) {
					this.chooseList.splice(num, 1);
				} else {
					this.chooseList.push(id);
				}
			},

			submit() {
				var list = [];
				for (var i in this.chooseList) {
					for (var j in this.list) {
						if (this.list[j].id == this.chooseList[i]) {
							list.push(this.list[j]);
							break;
						}
					}
				}
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]
				// 触发上一页 upData 函数(并携带参数)
				prevPage.$vm.upData(list)
				uni.navigateBack({
					delta: 1
				})
			},
			resetForm() {
				this.$refs.uForm.resetFields();
			},
		}
	}
</script>
<style>
	page {
		background: #F5F5F5;
	}
</style>
<style scoped lang="scss">
	.material_check {
		border: 1px solid #1677FF;
	}

	.material {
		.item {
			color: #ccc;
		}
	}

	.detail {
		color: #1677FF;
	}

	.detail-info {
		padding: 20rpx;
		border: 1px solid #eee;
		margin-top: 24rpx;
		border-radius: 6rpx;
	}
</style>
