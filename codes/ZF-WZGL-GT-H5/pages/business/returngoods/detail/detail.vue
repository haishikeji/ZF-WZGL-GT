<template>
	<view>
		<u-navbar  back-icon-color="#1677FF" title="退货单详情"  title-size="36"></u-navbar>
		<view class="info mt-24">
			<view class="comm-detail-text">
				<view>订单编号：</view>
				<view class="right">{{info.returnNo}}</view>
				<view>制单人：</view>
				<view class="right">{{info.createUserName}}</view>
				<view>制单日期：</view>
				<view class="right">{{info.createTime}}</view>
			</view>
		</view>
		<z-card :shadow="false" class="mt-24" v-for="(item,index) in list" :key="index">
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
				<view>入库量：</view>
				<view class="right">{{item.stockNum}}</view>
				<view>库位：</view>
				<view class="right">{{item.gridName}}</view>
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
				this.$axios.get('/returnGood/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
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
