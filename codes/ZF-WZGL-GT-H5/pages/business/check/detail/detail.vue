<template>
	<view>
		<u-navbar back-icon-color="#1677FF" title="入库单详情" title-size="36"></u-navbar>
		<view class="info mt-24">
			<view class="comm-detail-text">
				<view>入库单号：</view>
				<view class="right">{{info.inputNo}}</view>
				<view>审核原因：</view>
				<view class="right">{{info.remarks}}</view>
				<view>审核状态：</view>
				<view class="right">
					{{info.approveStatus==0?'待审核':''}}
					{{info.approveStatus==1?'审核中':''}}
					{{info.approveStatus==2?'审核驳回':''}}
					{{info.approveStatus==3?'审核成功':''}}
				</view>
				<view>供货商编号：</view>
				<view class="right">{{info.supplierNo}}</view>
				<view>供应商名称：</view>
				<view class="right">{{info.supplierName}}</view>
			<!-- 	<view>联系人：</view>
				<view class="right">55</view>
				<view>电话：</view>
				<view class="right">55</view> -->
				<view>制单人：</view>
				<view class="right">{{info.createUserName}}</view>
				<view>制单日期：</view>
				<view class="right">{{info.createTime}}</view>
			<!-- 	<view>备注：</view>
				<view class="right">55</view> -->
			</view>
		</view>
		<z-card :shadow="false" class="mt-24" v-for="(item,index) in list" :key="index">
			<view class="comm-detail-text">
				<view>物资编号：</view>
				<view class="right">{{item.goodCode}}</view>
				<view>物资名称：</view>
				<view class="right">{{item.goodName}}</view>
				<view>规格型号：</view>
				<view class="right">{{item.specsModelName}}</view>
				<view>计量单位：</view>
				<view class="right">{{item.measureUnit}}</view>
				<view>入库量：</view>
				<view class="right">{{item.num}}</view>
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
				this.$axios.get('/checkInput/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;

					this.$set(this.info, 'approveStatus', this.info.approveInfo[0].approveStatus);
					this.$set(this.info, 'remarks', res.data.approveInfo[0].remarks)

					this.list = res.data.checkDetail;
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
	.info {
		padding: 48rpx;
		background: white;
	}
</style>
