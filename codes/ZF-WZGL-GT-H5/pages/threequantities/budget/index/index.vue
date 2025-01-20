<template>
	<view>
		<!-- <u-navbar  title="概算量管理" :background="{background:'#1677FF'}" :border-bottom="false"></u-navbar> -->
		<search-com-page title="概算量管理" subtitle="物资查询" placeholder="物资编号" @searchChange="searchChange"
			@submit="getDataList" @resetForm="resetForm">
			<image slot="topbtn" class="right-btn" src="../../../../common/img/add.png" @click="manageData('')"></image>
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['toast']">
					<!-- 	<u-form-item label="物资编号" :border-bottom="false">
						<u-input border height="95" v-model="goodCode" placeholder="请输入物资编号" :clearable="false" />
					</u-form-item> -->
					<u-form-item label="物资名称" :border-bottom="false">
						<u-input border height="95" v-model="goodName" placeholder="请输入物资名称" :clearable="false" />
					</u-form-item>
					<u-form-item label="规格型号" prop="intro" :border-bottom="false">
						<u-input border height="95" v-model="specsModel" placeholder="请输入规格型号" :clearable="false" />
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>

		<view class="budget_total flex-n fac">
			<view class="mr-20">
				{{approveStatus==''?'待提交':''}}
				{{approveStatus=='0'?'待审核':''}}
				{{approveStatus=='1'?'审核中':''}}
				{{approveStatus=='2'?'审核驳回':''}}
				{{approveStatus=='3'?'审核成功':''}}
			</view>

			总计: {{total}}
		</view>
		<!-- <u-empty class="mt-100" text="已提交审核" mode="order"></u-empty> -->

		<!-- 待审核状态无列表 -->
		<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">
			<z-more>
				<view slot="top" class="comm-detail-text">
					<view>物资编号：</view>
					<view class="right">{{item.goodCode}}</view>
					<view>物资名称：</view>
					<view class="right">{{item.goodName}}</view>
					<view>合 计：</view>
					<view class="right">{{item.total}}</view>
				</view>
				<view slot="bottom" class="comm-detail-text">
					<view>单 位：</view>
					<view class="right">{{item.unit}}</view>
					<block v-for="(itm,idx) in budgetUnitList" :key="idx">
						<view>{{itm.unitName}}：</view>
						<view class="right">{{item[itm.unitName]}}</view>
					</block>
					<view>备 注：</view>
					<view class="right">{{item.remarks}}</view>
				</view>
			</z-more>

			<view class="btn-area">
				<u-button size="mini" type="primary" plain shape="circle" @click="manageData(item.id)">编辑</u-button>
				<u-button class="ml-20" size="mini" type="error" plain shape="circle"
					@click="delVisible=true;id=item.id">删除
				</u-button>
			</view>
		</z-card>

		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
		<u-toast ref="uToast"></u-toast>
		<z-bottom-fixed v-if="approveStatus==''">
			<z-button type="primary" @click="submitVisible=true">提交</z-button>
		</z-bottom-fixed>

		<!-- 删除 -->
		<u-modal v-model="delVisible" show-cancel-button content="该操作将删除该订单所有数据,
		确定要删除吗？" @confirm="deleteData"></u-modal>
		<!-- 提交 -->
		<u-modal v-model="submitVisible" show-cancel-button content="该操作将提交该订单所有数据进
行审核流程，确定要提交吗？" @confirm="submitData"></u-modal>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				goodCode: '',
				goodName: '',
				specsModel: '',
				id: '',
				approveStatus: '',
				list: [],
				budgetUnitList: [], //动态概算单元列表
				form: {},
				rules: {},

				total: 0,
				delVisible: false,
				submitVisible: false
			}
		},

		onShow() {
			this.getDataList();
			this.getState();
			this.getBudgetUnit();
		},

		methods: {
			searchChange(data) {
				this.goodCode = data;
				this.getDataList();
			},
			//获取动态概算单元
			getBudgetUnit() {
				this.$axios.get('/budgetUnit/findBudgetUnit/' + app.globalData.projectId).then(res => {
					this.budgetUnitList = res.data.list;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			getState() {
				this.$axios.post('/workflow/threeEstimateStatus', {
					"busId": app.globalData.projectId + '1'
				}).then(res => {
					console.log(res.data)
					this.approveStatus = res.data.approveStatus;
				}).catch(err => {})
			},
			getDataList() {
				this.$axios.post('/threeEstimate/budgetEstimateList', {
					"projectId": app.globalData.projectId,
					"goodCode": this.goodCode,
					"goodName": this.goodName,
					"specsModel": this.specsModel
				}).then(res => {
					this.list = res.data.list;
					this.total = res.data.total;
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			manageData(id) {
				uni.navigateTo({
					url: '../manage/manage?id=' + id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
			},

			//删除数据
			deleteData() {
				this.$axios.post('/threeEstimate/logicDeleteByIds', this.id).then(res => {
					this.$refs.uToast.show({
						title: '删除成功',
						type: 'success'
					})
					this.getDataList();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			submitData() {
				this.$axios.post('/threeEstimate/commitBudgetUnit', {
					"projectId": app.globalData.projectId,
				}).then(res => {
					this.$refs.uToast.show({
						title: '提交成功',
						type: 'success'
					})
					this.getDataList();
					this.getState();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			resetForm() {
				this.goodName = '';
				this.specsModel = '';
			},
		}
	}
</script>

<style scoped lang="scss">
	.right-btn {
		width: 38rpx;
		height: 38rpx;
	}

	.budget_total {
		font-weight: 600;
		font-size: 30rpx;
		color: #333333;
		padding: 0 24rpx;
		margin-top: 40rpx;
	}

	.list {
		position: relative;

		.btn-area {
			position: absolute;
			bottom: 24rpx;
			left: 24rpx;
		}
	}
</style>
