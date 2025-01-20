<template>
	<view>
		<search-com-page title="收支存" subtitle="物资查询" placeholder="物资编号" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<!-- <u-form-item label="物资编号" :border-bottom="false">
						<u-input border height="95" v-model="goodCode" placeholder="请输入物资编号" :clearable="false" />
					</u-form-item> -->
					<u-form-item label="物资名称" :border-bottom="false">
						<u-input border height="95" v-model="goodName" placeholder="请输入物资名称" :clearable="false" />
					</u-form-item>
					<u-form-item label="规格型号" :border-bottom="false">
						<u-input border height="95" v-model="specsModelName" placeholder="请选择规格型号" :clearable="false" />
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>


		<!-- 待审核状态无列表 -->
		<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">

			<z-more>
				<view slot="top" class="comm-detail-text">
					<view>物资编号：</view>
					<view class="right">{{item.goodCode}}</view>
					<view>物资名称：</view>
					<view class="right">{{item.goodName}}</view>
					<view>规格型号：</view>
					<view class="right">{{item.specsModelName}}</view>
				</view>
				<view slot="bottom" class="comm-detail-text">

					<view>类 别：</view>
					<view class="right">{{item.typeName}}</view>
					<view>计量单位：</view>
					<view class="right">{{item.measureUnit}}</view>
					<view>日 期：</view>
					<view class="right">{{item.measureUnit}}</view>
					<view>上期结存：</view>
					<view class="right">{{item.lastStockNum}}</view>
					<view>本期入库：</view>
					<view class="right">{{item.inputNum}}</view>
					<view>本期出库：</view>
					<view class="right">{{item.outputNum}}</view>
					<view>本期结存：</view>
					<view class="right">{{item.stockNum}}</view>
				</view>
			</z-more>

		</z-card>
		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
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
				goodCode: '',
				goodName: '',
				specsModelName: '',
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				list: [],
				form: {

				},
				rules: {

				},
				pageSize: 20,
				currentPage: 1,
				total: 0,
			}
		},
		onLoad() {
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
			searchChange(data) {
				this.goodCode = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/report/checkInSummaryList', {
					"projectId": app.globalData.projectId,
					"goodCode": this.goodCode,
					"goodName": this.goodName,
					"specsModelName": this.specsModelName,
					pageNum: this.currentPage,
					pageSize: this.pageSize,
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					this.total = res.data.total;
					if (this.list.length == this.total) {
						this.status = 'nomore';
					} else {
						this.status = 'loadmore';
					}
				}).catch(err => {
					this.status = 'loadmore';
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			resetForm() {
				this.goodCode = '';
				this.goodName = '';
				this.specsModelName = '';
			}

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
