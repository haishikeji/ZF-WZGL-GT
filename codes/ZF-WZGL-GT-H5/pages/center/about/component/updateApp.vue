<template>
	<view>
		<!-- 检查更新 -->
		<u-popup v-model="visible" mode="center" :mask-close-able="false" border-radius="14">
			<view class="update">
				<view class="flex-n fje">
					<u-icon name="close" color="#999999" size="28" @click="close"></u-icon>
				</view>
				<view class="title">发现新版本，V1.1.0来啦</view>
				<scroll-view scroll-y="true" style="max-height: 300rpx;margin-bottom:66rpx ;">
					<view class="content">页面全部改版</view>
					<view class="content">增加三量管理功能</view>
					<view class="content">修改若干系统bug</view>

				</scroll-view>
				<u-button type="primary" :custom-style="customStyle">立即更新</u-button>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		props: {
			visible: Boolean
		},
		data() {
			return {
				customStyle: {
					width: '544rpx',
					margin: '0 auto',
					height: '98rpx',
					fontSize: '36rpx'
				},
			}
		},
		methods: {
			close() {
				this.$emit('update:visible', false);
			}
		}
	}
</script>

<style scoped lang="scss">
	.update {
		padding: 60rpx 40rpx;
		overflow: hidden;

		.title {
			font-size: 36rpx;
			padding: 60rpx 0;
			color: #333333;
			text-align: center;
			font-weight: 600;
		}
	}
</style>
