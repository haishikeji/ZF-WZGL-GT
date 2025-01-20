<template>
	<view>
		<slot name="top"></slot>
		<view class="do-text" @click="open=true" v-show="!open">展开<u-icon class="ml-10" name="arrow-down" color="#999999" size="24"></u-icon>
</view>
		<slot v-if="open" name="bottom"></slot>
		<view class="do-text"  @click="open=false" v-show="open">收起<u-icon class="ml-10" name="arrow-up" color="#999999" size="24"></u-icon>
		</view>
	</view>
</template>

<script>
	export default {
		name:"z-more",
		data() {
			return {
				open:false
			};
		}
	}
</script>

<style scoped lang="scss">
.do-text{
	font-size: 24rpx;
	width: 100%;
	margin-top: 20rpx;
	color: #999999;
	text-align: right;
}
</style>
