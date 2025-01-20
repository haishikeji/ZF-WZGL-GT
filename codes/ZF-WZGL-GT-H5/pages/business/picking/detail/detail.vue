<template>
	<view>
		<u-navbar  back-icon-color="#1677FF" title="领料单详情"  title-size="36"></u-navbar>
		<view class="info mt-24">
			<view class="comm-detail-text">
				<view>订单编号：</view>
				<view class="right">{{info.pickOrderNo}}</view>
				<view>申请人：</view>
				<view class="right">{{info.createUserName}}</view>
				<view>申请日期：</view>
				<view class="right">{{info.createTime}}</view>
				<view>站区间：</view>
				<view class="right">{{info.stationIntervalName}}</view>
				<view>专    业：</view>
				<view class="right">{{info.majorName}}</view>
				<view>审核状态：</view>
				<view class="right">
					{{info.approveStatus==0?'待审核':''}}
					{{info.approveStatus==1?'审核中':''}}
					{{info.approveStatus==2?'审核驳回':''}}
					{{info.approveStatus==3?'审核成功':''}}
				</view>
				<view>领料单位：</view>
				<view class="right">{{info.pickCompanyName}}</view>
			</view>
		</view>
		<z-card :shadow="false" class="mt-24"  v-for="(item,index) in list" :key="index">
			<view class="comm-detail-text">
				<view>物资编码：</view>
				<view class="right">{{item.goodCode}}</view>
				<view>物资名称：</view>
				<view class="right">{{item.goodName}}</view>
				<view>规格型号：</view>
				<view class="right">{{item.specsModelName}}</view>
				<view>计量单位：</view>
				<view class="right">{{item.measureUnit}}</view>
				<view>数量：</view>
				<view class="right">{{item.num}}</view>
			</view>
		</z-card>
		<view style="height: 24rpx;">
			
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				info: {},
				list: []
			}
		},
		onLoad(options) {
			this.getData(options.id)
		},
		methods: {
			getData(id) {
				this.$axios.get('/pickOrder/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					
					this.$set(this.info, 'approveStatus', this.info.approveInfo[0].approveStatus)
					this.list = res.data.goodDetail;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
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
	.info{
		padding: 48rpx;
		background: white;
	}
</style>
