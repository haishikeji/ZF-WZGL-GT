<template>
	<!-- <search-com-page title="选择物资" diy :type="1" @submit="submit" @resetForm="resetForm">
		<view slot="input" style="width: 100%;background: white;border-radius: 8rpx;">
			<z-select :clearable="false" style="width: 100%;" placeholder="请选择物资类别" v-model="goodTypeName"
				@click="chooseMutil">
			</z-select>
		</view>
		<view slot="search">
			<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
			
				<u-form-item label="物资编号" :border-bottom="false">
					<u-input border height="95" v-model="goodCode" placeholder="请输入物资编号" :clearable="false" />
				</u-form-item>
				<u-form-item label="物资名称" :border-bottom="false">
					<u-input border height="95" v-model="goodName" placeholder="请输入物资名称" :clearable="false" />
				</u-form-item>
	
				<u-form-item label="规格型号" :border-bottom="false">
					<u-input border height="95" v-model="specsModelName" placeholder="请输入规格型号" :clearable="false" />
				</u-form-item>
			</u-form>
		</view>
	
	</search-com-page> -->
	<view>
		<u-navbar back-icon-color="#1677FF" title="盘点单详情" title-size="36"></u-navbar>


		<view class="info mt-24">
			<view class="comm-detail-text">
				<!-- <view>盘点单号：</view>
				<view class="right">{{info.checkNo}}</view>
				<view>盘点日期：</view>
				<view class="right">{{info.checkTime}}</view> -->
				<view>盘点仓库：</view>
				<view class="right">{{info.storeName}}</view>
				<!-- <view>盘点人：</view>
				<view class="right">{{info.createUserName}}</view> -->
			<!-- 	<view>创建时间：</view>
				<view class="right">{{info.createTime}}</view> -->
				<view>盈亏数量：</view>
				<view class="right">{{info.resultNum}}</view>
				<view>审核状态：</view>
				<view class="right">
					{{info.approveStatus==-1?'已保存':''}}
					{{info.approveStatus==0?'待审核':''}}
					{{info.approveStatus==1?'审核中':''}}
					{{info.approveStatus==2?'审核驳回':''}}
					{{info.approveStatus==3?'审核成功':''}}
				</view>
				<!-- <view>审核原因：</view>
				<view class="right">{{info.reason}}</view>
				<view>备 注：</view>
				<view class="right">{{info.remarks}}</view> -->


			</view>
		</view>


		<view class="info mt-24">
			<view class="comm-detail-text">
				<view>
					<u-input border height="95" @change: v-model="goodName" placeholder="请输入物资名称" :clearable="false" />
				</view>
				<view class="right" style="padding-left: 10px;">
					<z-select style="width: 100%;" placeholder="请选择专业" v-model="majorName" @click="chooseMajor">
					</z-select>
				</view>

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
				<view>专业：</view>
				<view class="right">{{item.majorName}}</view>
				<view>库位：</view>
				<view class="right">{{item.gridName}}</view>
				<view>库存数量：</view>
				<view class="right">{{item.stockNum}}</view>
				<view>盘点数量：</view>
				<view class="right">{{item.num}}</view>
				<view>盈亏数量：</view>
				<view class="right">{{item.resultNum}}</view>
			</view>
		</z-card>
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefult" value-name="id"
			:list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
		<view style="height: 24rpx;">

		</view>
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
				info: {},
				showMajor: false,
				majorId: '',
				majorName: '',
				majorList: [],
				majorDefult: [0],
				goodName: '',

				list: []
			}
		},
		watch:{
			goodName(newval,oldval){
				this.getDataList();
				if(newval){
					
				}
			}
		},
		onShow() {
			this.getMajorList()
		},
		onLoad(options) {
			this.getData(options.id)
		},
		methods: {
			chooseMajor() {
				this.showMajor = true;
			},
			getMajorList() {
				this.$axios.post('/major/findList', {
					"projectId": app.globalData.projectId,
				}).then(res => {
					this.majorList = res.data.list;
				}).catch(err => {
					this.$message.error(err.msg)
				})
			},
			confirmMajor(data) {
				console.log(data)
				this.majorName = data[0].label;
				this.majorId = data[0].value;
				for (var i in this.approveStatusList) {
					if (this.majorList[i].id == this.majorId) {
						this.approveStatusDefault.splice(0, 1, Number(i));
						break;
					}
				}
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/checkDetail/findAll', {
					"checkId": this.info.id,
					goodName: this.goodName,
					majorId: this.majorId,
				}).then(res => {
					this.list = res.data.list;
				}).catch(err => {})
			},
			getData(id) {
				this.$axios.get('/check/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					if (this.info.approveInfo.length > 0) {
						this.$set(this.info, 'approveStatus', this.info.approveInfo[0].approveStatus);
						this.$set(this.info, 'reason', res.data.approveInfo[0].remarks);
					}
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