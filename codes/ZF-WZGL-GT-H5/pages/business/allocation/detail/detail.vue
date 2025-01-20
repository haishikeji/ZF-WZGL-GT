<template>
	<view>
		<u-navbar back-icon-color="#1677FF" title="调拨单详情" title-size="36"></u-navbar>
		<view class="info mt-24">
			<view class="comm-detail-text">
				<view>调拨单号：</view>
				<view class="right">{{info.allotNo}}</view>
				<view>单据状态：</view>
				<view class="right">
					{{info.approveStatus==0?'待审核':''}}
					{{info.approveStatus==1?'审核中':''}}
					{{info.approveStatus==2?'审核驳回':''}}
					{{info.approveStatus==3?'审核成功':''}}
				</view>
				<view>审核原因：</view>
				<view class="right">{{info.remarks}}</view>
				<view>制单人：</view>
				<view class="right">{{info.createUserName}}</view>
				<view>制单日期：</view>
				<view class="right">{{info.createTime}}</view>

				<view>调拨类型：</view>
				<view class="right">{{info.allotType==1?'调出':'调入'}}</view>
				<template v-if="info.allotType==1">
					<view>调出项目部：</view>
					<view class="right">{{info.projectFromId}}</view>
				</template>
				<template v-else>
					<view>调入项目部：</view>
					<view class="right">{{info.projectToId}}</view>
				</template>
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
				<view>调拨数量：</view>
				<view class="right">{{item.num}}</view>
				<template v-if="info.allotType==2">
					<view>调出仓库：</view>
					<view class="right">{{item.storeNameFrom}}</view>
				</template>
				<template v-else>
					<view>调入仓库：</view>
					<view class="right">{{item.storeNameTo}}</view>
				</template>
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
				this.$axios.get('/allot/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.allotDetail;
					this.$set(this.info, 'approveStatus', res.data.approveInfo[0].approveStatus||'');
					this.$set(this.info, 'remarks', res.data.approveInfo[0].remarks||'');
					
				}).catch(err => {
					// this.$refs.uToast.show({
					// 	title: err.msg,
					// 	type: 'error'
					// })
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
	.info {
		padding: 48rpx;
		background: white;
	}
</style>
