<template>
	<view>
		<view :style="{height: height}"></view>
		<view class="bottom-btn">
			<slot></slot>
		</view>
	</view>
	
</template>

<script>
	export default {
		name:"z-bottom-fixed",
		props:{
			height:{
				type:String,
				default:'148rpx'
			}
		},
		data() {
			return {
				
			};
		}
	}
</script>

<style scoped lang="scss">
.bottom-btn {
		position: fixed;
		bottom: 0;
		width: 100vw;
		// height: 148rpx;
		padding: 24rpx;
		background: #FFFFFF;
		z-index: 500;
		box-shadow: 0 -8px 20px -10px rgba(22, 119, 255, 0.20);
	}
</style>
