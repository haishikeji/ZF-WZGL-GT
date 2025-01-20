<template>
	<view>
		<u-navbar class="topbar" :is-back="false" title-color="white" title-size="36" title="我的"
			:background="{'background-image': 'linear-gradient(135deg, #168BFF 0%, #1664FF 100%)'}"
			:border-bottom="false"></u-navbar>
		<view class="center-info flex-n fac">
			<u-avatar :src="src" mode="circle" size="120" @click="avatarVisible=true"></u-avatar>
			<view class="ml-24 flex-1">
				<view class="username">{{info.userName}}</view>
				<view class="intro">{{info.postName}}</view>
			</view>
			<u-icon name="arrow-right" color="white" size="28"></u-icon>

		</view>
		<u-cell-group class="center-cell mt-24">
			<u-cell-item title="账号与安全" :title-style="titleStyle" @click="gotoPage(0)">
				<image class="img" slot="icon" src="../../../common/img/center/anquan.png"></image>
			</u-cell-item>
			<u-cell-item title="切换项目" :title-style="titleStyle" @click="gotoPage(1)">
				<image class="img" slot="icon" src="../../../common/img/center/qiehuan.png"></image>
			</u-cell-item>
			<!-- 	<u-cell-item title="物资管理系统" :title-style="titleStyle" value="V1.0.0" @click="gotoPage(2)">
				<image class="img" slot="icon" src="../../../common/img/center/guanyu.png"></image>
			</u-cell-item> -->
		</u-cell-group>

		<u-button type="primary" :custom-style="customStyle" @click="exitVisible=true">退出</u-button>
		<u-tabbar :list="$store.state.tabbar" active-color="#1677FF"></u-tabbar>
		<!-- 查看头像 -->
		<u-mask :show="avatarVisible" @click="avatarVisible = false">
			<view class="warp">
				<view class="rect" @tap.stop></view>
			</view>
		</u-mask>
		<!-- 退出 -->
		<u-modal v-model="exitVisible" show-cancel-button cancel-text="再想想" confirm-text="退出" content="是否退出当前账户"
			@confirm="exitUser"></u-modal>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				src: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/hpic2119_s.jpg',
				customStyle: {
					width: '702rpx',
					margin: '100rpx auto',
					height: '98rpx',
					fontSize: '36rpx'
				},
				titleStyle: {
					'margin-left': '20rpx',
					'font-size': '34rpx'
				},
				avatarVisible: false,
				exitVisible: false,
				info: {}
			};
		},
		onShow() {
			this.getInfo();
		},
		methods: {
			getInfo() {
				uni.getStorage({
					key: 'loginInfo',
					success: (res) => {
						this.info = res.data
					}
				})
			},
			//退出账号
			exitUser() {
				var rememberpass = uni.getStorageSync('rememberpass');
				uni.reLaunch({
					url: '../../login/login',
					success: res => {
						uni.clearStorage();
						if (rememberpass) {
							uni.setStorage({
								key: 'rememberpass',
								data: rememberpass
							})
						}
					},
					fail: () => {},
					complete: () => {}
				});
			},
			gotoPage(index) {
				if (index == 0) {
					uni.navigateTo({
						url: '../safe/safe'
					})
				} else if (index == 1) {
					uni.navigateTo({
						url: '../modifyproject/modifyproject'
					})
				} else {
					uni.navigateTo({
						url: '../about/about'
					})
				}
			}
		}
	}
</script>
<style>
	page {
		background: #F5F5F5;
	}
</style>
<style scoped lang="scss">
	.center-info {
		background-image: linear-gradient(135deg, #168BFF 0%, #1664FF 100%);
		padding: 24rpx;
		color: white;

		.username {
			font-size: 36rpx;
		}

		.intro {
			font-size: 30rpx;
		}
	}

	.center-cell {
		.img {
			width: 42rpx;
			height: 42rpx;
		}
	}

	.warp {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 100%;
	}

	.rect {
		width: 665rpx;
		height: 665rpx;
		background-color: #fff;
		border-radius: 8rpx;
	}
</style>