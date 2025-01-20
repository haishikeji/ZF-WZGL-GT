<template>
	<view>
		<u-navbar  back-icon-color="#1677FF" title="信息详情"  title-size="36"></u-navbar>
		<!-- 少一个其他的样式 -->
		<view class="mt-24 info">
			<view class="title">{{title}}</view>
			<view class="grid">
				<view class="r1"></view>
				<view class="r1">图纸量</view>
				<view class="r1">概算量</view>
				<view class="r2">物资名称</view>
				<view class="r2">*****</view>
				<view class="r2">*****</view>
				<view class="r1">合计</view>
				<view class="r1">37</view>
				<view class="r1">45</view>
			</view>
		</view>
		<u-button type="primary" :custom-style="customStyle" @click="close">关闭</u-button>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				title:'图纸量超过概算量',
				customStyle: {
						width:'702rpx',
						margin:'30rpx auto',
						height:'98rpx',
						fontSize:'36rpx'
					}
			}
		},
		methods: {
			close(){
				uni.navigateBack({
					delta:1
				})
			}
		}
	}
</script>
<style>
	page{
		background: #f5f5f5;
	}
</style>
<style scoped lang="scss">
.info{
	background: white;
	
	.title{
		margin: 0 24rpx;
		padding: 26rpx 0;
		font-size: 30rpx;
		border-bottom: 1px solid #EEEEEE;
	}
	.grid{
		display: grid;
		padding: 24rpx;
	grid-template-columns: 30% repeat(2,35%);
	>view{
		height: 96rpx;
		line-height: 96rpx;
		font-size: 24rpx;
		color: #999999;
		text-align: center;
	}
	.r1{
		
		background: #f5f5f5;
	}
	.r2{
		
	}
	}
}
</style>
