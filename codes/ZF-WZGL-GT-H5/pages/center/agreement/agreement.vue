<template>
	<view>
		<u-navbar  back-icon-color="#1677FF" title="用户协议"  title-size="36"></u-navbar>
		<view class="content" v-html="content"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content:'我是内容'
			}
		},
		methods: {
			
		}
	}
</script>
<style>
	page {
		background: white;
	}
</style>
<style scoped lang="scss">
	.content{
		padding: 40rpx;
	}
</style>