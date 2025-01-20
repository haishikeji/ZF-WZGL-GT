<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'退货单'" title-size="36">
			<!-- <view slot="right" class="mr-30" >确定</view> -->
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit">保存</u-button>
		</u-navbar>
		<u-cell-group class="mt-24" v-if="id">
			<u-cell-item title="退货单号" :arrow="false">{{info.returnNo}}</u-cell-item>
			<u-cell-item title="制单人" :arrow="false">{{info.createUserName}}</u-cell-item>
			<u-cell-item title="制单时间" :arrow="false">{{info.createTime}}</u-cell-item>
		</u-cell-group>
		<view class="common-infolist-title mt-24">
			<view class="flex-1 title">物资信息</view>
			<u-button size="mini" type="primary" plain shape="circle" @click="addMaterial">新增物资</u-button>
		</view>
		<scroll-view class="scroll " :scroll-y="true">
			<z-card :shadow="false" class="mt-24" v-for="(item,index) in list" :key="index">
				<view class="comm-detail-text">
					<view></view>
					<view class="right">
						<u-icon name="trash" size="40" color="#ff3300"  @click="list.splice(index,1)"></u-icon>
					</view>
					<view>物资编号：</view>
					<view class="right">{{item.goodCode}}</view>
					<view>物资名称：</view>
					<view class="right">{{item.goodName}}</view>
					<view>规格型号：</view>
					<view class="right">{{item.specsModelName}}</view>
					<view>计量单位：</view>
					<view class="right">{{item.measureUnit}}</view>
					<view>数量：</view>
					<view class="right">
						<u-number-box v-model="item.num"></u-number-box>
						<!-- <u-input height="40" input-align="right" v-model="form.intro" placeholder="输入数量"/> -->
					</view>
					<view>仓库：</view>
					<view class="right">{{item.storeName}}</view>
					<view>库位：</view>
					<view class="right">{{item.gridName}}</view>
				</view>
			</z-card>
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
				info: {
				
				},
				rules: {
					
				},
hisList: [],
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
					var num = -1;
					for (var j in this.list) {
						if (this.list[j].goodId == data[i].goodId) {
							num = j;
							break;
						}
					}
					if (num == -1) {
						data[i].num = 1;
					}
				}
					 this.list = data;
				}).catch(err => {
					this.$message.error(err.msg)
				})
			},
			getData(id) {
				this.$axios.get('/returnGood/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.goodDetail;
					this.hisList = JSON.parse(JSON.stringify(this.list));
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
				var goodDetail = [];
				for (var i in this.list) {
					goodDetail.push({
						"goodId": this.list[i].goodId,
						"gridId": this.list[i].gridId,
						"num": this.list[i].num
					})
				}
				this.$axios.post('/returnGood/save', {
					"projectId": app.globalData.projectId,
					"goodDetail": goodDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增退货单成功',
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
				var goodDetail = [];
				for (var i in this.list) {
					goodDetail.push({
						"goodId": this.list[i].goodId,
						"gridId": this.list[i].gridId,
						"num": this.list[i].num
					})
				}
				for (var i in this.hisList) {
					for (var j in goodDetail) {
						if (this.hisList[i].goodId == goodDetail[j].goodId) {
							goodDetail[j].id = this.hisList[i].id;
						}
					}
				}
				this.$axios.post('/returnGood/update', {
					"id": this.id,
					"goodDetail": goodDetail
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑退货单成功',
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
		height: calc(100vh - 400rpx);
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
