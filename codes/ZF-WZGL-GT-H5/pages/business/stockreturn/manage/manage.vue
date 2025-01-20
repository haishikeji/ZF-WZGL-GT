<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'退库单'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit">保存</u-button>
		</u-navbar>
	
		
		<view class="common-infolist-title mt-24">
			<view class="flex-1 title">物资信息</view>
			<u-button size="mini" type="primary" plain shape="circle" @click="addMaterial">增加物资</u-button>
		</view>
		<scroll-view class="scroll " :scroll-y="true">
			<z-card class="mt-24" :shadow="false" v-for="(item,index) in list" :key="index">
				<view class="comm-detail-text">
					<view></view>
					<view class="right">
						<u-icon name="trash" size="40" color="#ff3300" @click="list.splice(index,1)"></u-icon>
					</view>
					<view>物资编码：</view>
					<view class="right">{{item.goodCode}}</view>
					<view>物资名称：</view>
					<view class="right">{{item.goodName}}</view>
					<view>规格型号：</view>
					<view class="right">{{item.specsModelName}}</view>
					<view>计量单位：</view>
					<view class="right">{{item.measureUnit}}</view>
					<view>数量：</view>
					<view class="right">
						<u-number-box input-width="160" v-model="item.num"></u-number-box>
					</view>
					<view>库位：</view>
					<view class="right">{{item.gridName}}</view>
				</view>
			</z-card>
			<view style="height: 24rpx;"></view>
		</scroll-view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				id: '',
				info:{},
				list: []
			}
		},
		onLoad(options) {
			console.log(options)
			this.id = options.id;
			if (this.id)
				this.getData(options.id);

		},
		
		methods: {
			
			upData(data) {
				console.log(data)

			
				var ids = '';
				for (var i in data) {
					ids += data[i].id + ',';
				}
				if (ids.length > 0) {
					ids = ids.substring(0, ids.length - 1);
					this.getDetailInfo(ids);
				}
				this.$forceUpdate()
			},
			//获取详细信息
			getDetailInfo(ids) {
				this.$axios.post('/good/findByIds', ids).then(res => {
					console.log(res.data)
					var data = res.data.list;
					
					for (var i in data) {
						if (!data[i].num) {
							data[i].num = 1
						}
					}
					this.list = data;
				}).catch(err => {
					this.$message.error(err.msg)
				})
			},
			getData(id) {
				this.$axios.get('/stockReturn/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.returnDetail;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			addMaterial() {
				uni.navigateTo({
					url: '../../../common/material/material?type=1&state=0',
					success: res => {
						uni.setStorage({
							key: 'materialList',
							data: this.list
						})
					},
					fail: () => {},
					complete: () => {}
				});
			},
		
			submit() {
				if (this.list.length == 0)
					return this.$refs.uToast.show({
						title: '请选择物资',
						type: 'warning'
					})
				
						if (this.id) {
							this.editSubmit();
						} else {
							this.addSubmit();
						}
				
			},
			addSubmit() {
				var info = this.info;
				var returnDetail = [];
				for (var i in this.list) {
					returnDetail.push({
						"goodId": this.list[i].id,
						"gridId": this.list[i].gridId,
						"num": this.list[i].num
					})
				}
				this.$axios.post('/stockReturn/save', {
					"projectId": app.globalData.projectId,
					"returnDetail":returnDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增退库单成功',
						type: 'success'
					})
					this.resetForm();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			editSubmit() {
				var info = this.info;
				var returnDetail = [];
				for (var i in this.list) {
					returnDetail.push({
						"goodId": this.list[i].id,
						"gridId": this.list[i].gridId,
						"num": this.list[i].num
					})
					if (!this.list[i].gridId) {
						return this.$refs.uToast.show({
							title: '库位不能为空',
							type: 'warning'
						})
					}
				}
				this.$axios.post('/returnDetail/update', {
					"id": this.id,
					 "returnDetail":returnDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑退库单成功',
						type: 'success'
					})
					this.resetForm();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			resetForm() {
				uni.navigateBack({
					delta: 1
				})
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
	.info {
		padding: 0 24rpx;
		background: white;
	}

	.scroll {
		height: calc(100vh - 180rpx);
		
	}

	.date-select {
		position: relative;
		width: 100%;

		.close {
			width: 40rpx;
			margin-left: 20rpx;
		}
	}
</style>
