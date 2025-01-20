<template>
	<view class="content">
		<u-navbar :is-back="false" title="" :background="{background:'#1677FF'}" :border-bottom="false">
			<view class="topbar flex-n fac">
				<image class="img" src="../../../common/img/qiehuan.png" @click="tabItems"></image>
				<view class="text">{{projectName}}</view>
			</view>
		</u-navbar>
		<view class="index-bg-halfcircle"></view>
		<z-card class="card-1" title="三量管理">
			<u-row gutter="16" class="index-operation">
				<!-- <u-col span="4">
					<navigator url="../../threequantities/budget/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-gaisuanliang.png"></image>
							<view class="text">概算量</view>
						</view>
					</navigator>
				</u-col>
				<u-col span="4">
					<navigator url="../../threequantities/drawing/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-tuzhiliang.png"></image>
							<view class="text">图纸量</view>
						</view>
					</navigator>
				</u-col> -->
				<u-col span="4">
					<navigator url="../../threequantities/location/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-dingceliang.png"></image>
							<view class="text">定测量</view>
						</view>
					</navigator>
				</u-col>
				<u-col span="4">
					<navigator url="../../threequantities/limit/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-tuzhiliang.png"></image>
							<view class="text">限额管理</view>
						</view>
					</navigator>
				</u-col>
			</u-row>
		</z-card>
		<z-card class="mt-30" title="业务管理">
			<u-row gutter="16" class="index-operation">
				<u-col span="4">
					<navigator url="../../business/plan/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-diaobo.png"></image>
							<view class="text">申请计划</view>
						</view>
					</navigator>
				</u-col>
				
				
				
				<u-col span="4">
					<navigator url="../../business/ordergoods/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-dinghuo.png"></image>
							<view class="text">订货管理</view>
						</view>
					</navigator>
				</u-col>
				<!-- <u-col span="4">
					<navigator url="../../business/returngoods/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-tuihuo.png"></image>
							<view class="text">退货</view>
						</view>
					</navigator>
				</u-col> -->
				
				<u-col span="4">
					<navigator url="../../business/check/index/index">
					<view class="item">
						<image class="img" src="../../../common/img/index/index-dianyan.png"></image>
						<view class="text">点验入库</view>
					</view>
					</navigator>
				</u-col>
				<u-col span="4">
					<navigator url="../../business/picking/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-lingliao.png"></image>
							<view class="text">领用申请单</view>
						</view>
					</navigator>
				</u-col>
				<u-col span="4">
					<navigator url="../../business/issuematerials/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-faliao.png"></image>
							<view class="text">发料管理</view>
						</view>
					</navigator>
				</u-col>
				<!-- <u-col span="4">
					<navigator url="../../business/allocation/index/index">
					<view class="item">
						<image class="img" src="../../../common/img/index/index-diaobo.png"></image>
						<view class="text">调拨</view>
					</view>
					</navigator>
				</u-col> -->
				<!-- <u-col span="4">
					<navigator url="../../business/stockreturn/index/index">
					<view class="item">
						<image class="img" src="../../../common/img/index/index-tuiku.png"></image>
						<view class="text">退库</view>
					</view>
					</navigator>
				</u-col> -->
				<u-col span="4">
					<navigator url="../../business/straight/index/index">
					<view class="item">
						<image class="img" src="../../../common/img/index/index-zhiruzhichu.png"></image>
						<view class="text">直入直出单</view>
					</view>
					</navigator>
				</u-col>
				<u-col span="4">
					<navigator url="../../business/inventory/index/index">
					<view class="item">
						<image class="img" src="../../../common/img/index/index-pandian.png"></image>
						<view class="text">盘点管理</view>
					</view>
					</navigator>
				</u-col>
				<!-- <u-col span="4">
					<navigator url="../../business/inspection/index/index">
					<view class="item">
						<image class="img" src="../../../common/img/index/index-wuzijiance.png"></image>
						<view class="text">物资检测</view>
					</view>
					</navigator>
				</u-col> -->
			</u-row>
		</z-card>
		<z-card class="mt-30" title="报表管理">
			<u-row gutter="16" class="index-operation">
				<u-col span="4">
					<navigator url="../../report/inventory/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-kucun.png"></image>
							<view class="text">库存清单</view>
						</view>
					</navigator>
				</u-col>
				<u-col span="4">
					<navigator url="../../report/bookkeeping/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-shouzhicun.png"></image>
							<view class="text">收支存</view>
						</view>
					</navigator>
				</u-col>
				<!-- <u-col span="4">
					<navigator url="../../report/supplier/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-gongyingshang.png"></image>
							<view class="text">供应商供货</view>
						</view>
					</navigator>
				</u-col> -->
				<u-col span="4">
					<navigator url="../../report/consumption/index/index">
						<view class="item">
							<image class="img" src="../../../common/img/index/index-wuzihaoliao.png"></image>
							<view class="text">物资耗料</view>
						</view>
					</navigator>
				</u-col>
			</u-row>
		</z-card>
		<view class="mt-30"></view>
		<u-tabbar :list="$store.state.tabbar" active-color="#1677FF"></u-tabbar>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				show: false,
				tabbar: '',
				projectName:''
			}
		},
		onLoad() {

		},
		onShow() {
			this.projectName=app.globalData.projectName;
		},
		methods: {
			tabItems(){
				uni.navigateTo({
					url: '../../center/modifyproject/modifyproject',
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			}
		}
	}
</script>
<style>
	page{
		background: white;
	}
</style>
<style scoped lang="scss">
	.topbar {
		color: white;

		.img {
			width: 43rpx;
			height: 43rpx;
			margin: 0 24rpx;
		}

		.text {
			font-size: 36rpx;
			// font-weight: 600;
		}
	}

	.index-bg-halfcircle {
		height: 180rpx;
		background: #1677FF;
		border-bottom-left-radius: 50vw 60rpx;
		border-bottom-right-radius: 50vw 60rpx;
	}

	.card-1 {
		margin-top: -130rpx !important;
	}

	.index-operation {
		.item {
			text-align: center;

			.img {
				width: 110rpx;
				height: 110rpx;
				margin-top: 24rpx;
			}

			.text {
				font-size: 28rpx;
				padding: 20rpx 0;
			}
		}
	}
</style>
