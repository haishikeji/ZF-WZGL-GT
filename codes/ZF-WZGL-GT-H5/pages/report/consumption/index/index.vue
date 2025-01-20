<template>
	<view>
		<search-com-page title="物资耗料" placeholder='请输入领料单位' @searchChange="searchChange" @submit="searchList" @resetForm="resetForm">
	
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">


					<u-form-item label="日期" prop="date" :border-bottom="false">
						<view class="date-select flex-n fac">
							<u-input class="input" border height="95" v-model="date" placeholder="请选择日期"
								:clearable="false" @click="showDate=true" />
							<image v-if="date" class="close" mode="widthFix"
								src="../../../../common/img/index/close.png" @click="date='';dateRange=[]"></image>
						</view>
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>
		<u-calendar v-model="showDate" mode="range" @change="changeDate"></u-calendar>
			<view class="budget_total">总计: {{total}}</view>


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

					<view>计量单位：</view>
					<view class="right">{{item.measureUnit}}</view>
					<view>领料单位：</view>
					<view class="right">{{item.pickCompanyName}}</view>
					<view>使用区间：</view>
					<view class="right">{{item.stationIntervalName}}</view>
					<view>数 量：</view>
					<view class="right">{{item.num}}</view>
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
				pickCompanyName: '',
				date: '',
				dateRange: [],
				list: [],
				form: {},
				rules: {},
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				showDate: false,
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
			searchChange(data){
				this.pickCompanyName=data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/report/goodsSummaryList', {
					"projectId": app.globalData.projectId,
					"pickCompanyName": this.pickCompanyName,
					"beginTime": this.dateRange ? this.dateRange[0] : '',
					"endTime": this.dateRange ? this.dateRange[1] : '',
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
			changeDate(e) {
				console.log(e)
				this.dateRange = [e.startDate, e.endDate];
				this.date = e.startDate + '  至  ' + e.endDate;
			},
		
			resetForm() {
				this.pickCompanyName = '';
				this.date = '';
				this.dateRange = [];
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
.select-info{
	position: relative;
	width: 100%;
	
	.close {
		position: absolute;
		width: 40rpx;
		right: 60rpx;
		z-index: 100;
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
