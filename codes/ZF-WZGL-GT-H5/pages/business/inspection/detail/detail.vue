<template>
	<view>
		<u-navbar  back-icon-color="#1677FF" title="检测记录详情"  title-size="36"></u-navbar>
		<view class="info mt-24">
			<view class="comm-detail-text">
				<view>物资编号：</view>
				<view class="right">{{info.goodCode}}</view>
				<view>物资名称：</view>
				<view class="right">{{info.goodName}}</view>
				<view>规格型号：</view>
				<view class="right">{{info.specsModelName}}</view>
				<view>入库时间：</view>
				<view class="right">{{info.inputTime}}</view>
				<view>入库数量：</view>
				<view class="right">{{info.inputNum}}</view>
				<view>入库计量单位：</view>
				<view class="right">{{info.measureUnit}}</view>
			</view>
		</view>
		<z-card :shadow="false" class="mt-24" v-for="(item,index) in list" :key="index">
			<view class="comm-detail-text">
				<view>检测记录日期：</view>
				<view class="right">{{item.checkTime}}</view>
				<view>检测计量单位：</view>
				<view class="right">{{item.testUnit}}</view>
				<view>检测数量：</view>
				<view class="right">{{item.checkNum}}</view>
				<view>检测人：</view>
				<view class="right">{{item.createUserName}}</view>
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
				this.$axios.get('/goodCheck/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.logDetail;
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
