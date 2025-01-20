<template>
	<view>
		<u-navbar back-icon-color="#1677FF" :title="(id==''?'新增':'编辑')+'入库'" title-size="36">
			<u-button slot="right" class="mr-30" size="mini" type="primary" @click="submit">保存</u-button>
		</u-navbar>
		<view class="info mt-24">
			<u-form :model="info" ref="uForm" label-width="200rpx" :border-bottom="false" :error-type="['toast']">

				<u-form-item label="编号" prop="handNo">
					<u-input :disabled="id!==''" class="input" input-align="right" placeholder="请输入编号"
						v-model="info.handNo" />
				</u-form-item>
				<u-form-item label="仓库名称" prop="storeName">
					<z-select :border="false" height="60" align="right" size="mini" style="width: 100%;"
						placeholder="请选择仓库名称" v-model="info.storeName" @click="chooseStore">
					</z-select>
				</u-form-item>
				<u-form-item label="专业" prop="majorName">
					<z-select :border="false" height="60" align="right" style="width: 100%;" placeholder="请选择专业"
						v-model="info.majorName" @click="chooseMajor">
					</z-select>
				</u-form-item>
				<u-form-item label="供应商名称" prop="supplierName">
					<u-input class="input" a input-align="right" placeholder="请输入供应商名称" v-model="info.supplierName" />
				</u-form-item>
			</u-form>
		</view>
		<!-- 仓库-->
		<u-select v-model="showStore" label-name='storeName' :default-value="storeDefault" value-name="id"
			:list="storeList" @confirm="confirmStore" :mask-close-able="false">
		</u-select>
		<!-- 供应商 -->
		<u-select v-model="showSupplier" label-name='supplierName' :default-value="supplierDefault" value-name="id"
			:list="supplierList" @confirm="confirmSupplier" :mask-close-able="false">
		</u-select>
		<!-- 专业 -->
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefault" value-name="id"
			:list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>

		<view class="common-infolist-title mt-24">
			<view class="flex-1 title">物资信息</view>
			<u-button size="mini" type="primary" plain shape="circle" @click="addMaterial" v-if="info.storeId">新增物资
			</u-button>
			<u-button style="padding-left: 5px;" size="mini" type="defult" plain shape="circle"
				@click="addOrderMaterial" v-if="info.storeId">导入订货单
			</u-button>
		</view>
		<scroll-view class="scroll" :scroll-y="true">
			<z-card class="mt-24" :shadow="false" v-for="(item,index) in list" :key="index">
				<view class="comm-detail-text">
					<view></view>
					<view class="right">
						<u-icon name="trash" size="40" color="#ff3300" @click="delData(index)"></u-icon>
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
						<u-input type="number" input-align="right" placeholder="请输入数量" v-model="item.num" />
					</view>
					<view class="mt-20">库位：</view>
					<view class="right mt-20">
						<z-select :border="false" height="60" align="right" size="mini" placeholder="请选择库位名称"
							v-model="item.gridName" @click="showGrid=true;gridId=item.gridId;choose=index">
						</z-select>
					</view>
				</view>
			</z-card>
		</scroll-view>
		<u-toast ref="uToast"></u-toast>
		<grid :visible.sync="showGrid" :id="info.storeId" :gridId="gridId" @change="changeGrid"></grid>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				showGrid: false,
				id: '',
				gridId: '',
				choose: -1,
				info: {
					supplierId: '',
					supplierName: '',
					storeId: '',
					storeName: ''
				},
				rules: {
					supplierName: [{
						required: true,
						message: '请选择供应商',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					storeName: [{
						required: true,
						message: '请选择仓库',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],

				},

				showSupplier: false,
				supplierDefault: [0],
				supplierList: [],

				showStore: false,
				storeDefault: [0],
				storeList: [],
				approveStatus: '',

				majorList: [],
				showMajor: false,
				majorDefault: [0],

				list: [],
				hisList: [],
			}
		},
		onLoad(options) {
			console.log(options)
			this.id = options.id;
			if (this.id)
				this.getData(options.id);

		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		onShow() {
			this.$nextTick(() => {
				this.getSupplierList();
				this.getStoreList();
				this.getMajorList();
			})
		},
		methods: {
			getMajorList() {
				this.$axios.post('/major/findList', {}).then(res => {
					this.majorList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			chooseMajor() {
				if (this.id === '') {
					this.showMajor = true;
				}
			},
			confirmMajor(data) {
				this.$set(this.info, 'majorName', data[0].label);
				this.$set(this.info, 'majorId', data[0].value);
				for (var i in this.majorList) {
					if (this.majorList[i].id == data[0].value) {
						this.majorDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			changeGrid(data) {
				console.log(data)
				this.$set(this.list[this.choose], 'gridName', data.gridName)
				this.$set(this.list[this.choose], 'gridId', data.id);
			},
			getSupplierList() {
				this.$axios.post('/supplier/findList', {}).then(res => {
					console.log(res.data.list)
					this.supplierList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			getStoreList() {
				this.$axios.post('/store/storeList', {
					projectId: app.globalData.projectId
				}).then(res => {
					this.storeList = res.data.list;
				}).catch(err => {
					console.log(err.msg)
				})
			},
			delData(index) {
				this.list.splice(index, 1);
			},
			upData(data) {
				console.log(data)

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
				this.$forceUpdate()
			},
			upOrderData(data) {
				console.log(data)
				if (!data) {
					return;
				}
				this.$axios.get('/orderGood/findByIdBalance/' + data[0].id).then(res => {
					var data2 = res.data.goodDetailObj
					console.log(data2)
					for (var i in data2) {
						var num = -1;
						data2[i].orderDetailId = data2[i].id;
						data2[i].num = data2[i].balanceNum;
						for (var j in this.list) {
							if (this.list[j].goodId == data2[i].goodId) {
								num = j;
								break;
							}
						}
						if (num == -1) {
							this.list.push(data2[i])
						}
					}
					console.log(this.list)
					this.info.orderGoodId = res.data.id;
					this.info.orderNo = res.data.orderNo;
					this.info.supplierName = res.data.supplierName;
					this.info.majorId = res.data.majorId;
					this.info.majorName = res.data.majorName;
				}).catch(err => {
					this.$message.error(err.msg)
				})
				// for(var i in data){
				// 	var num=-1;
				// 	for(var j in this.list){
				// 		if(this.list[j].goodId==data[i].goodId){
				// 			num=j;
				// 			break;
				// 		}
				// 	}
				// 	if(num==-1){
				// 		data[i].num=1;
				// 	}
				// }

				// this.list = data;
				// this.$forceUpdate()
			},
			getData(id) {
				this.$axios.get('/checkInput/findById/' + id).then(res => {
					console.log(res.data);
					this.info = res.data;
					this.list = res.data.checkDetail;
					this.hisList = JSON.parse(JSON.stringify(res.data.checkDetail));
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},
			addMaterial() {
				uni.navigateTo({
					url: '../../../common/material/material?type=1&canSelect=true',
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
			addOrderMaterial() {
				uni.navigateTo({
					url: '../material/orderMaterial?type=1&canSelect=true',
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
			chooseSupplier() {
				this.showSupplier = true;
			},
			chooseStore() {
				if (this.id === '') {
					this.showStore = true;
				}
			},
			confirmSupplier(data) {
				console.log(data)
				this.$set(this.info, 'supplierName', data[0].label);
				this.$set(this.info, 'supplierId', data[0].value);
				for (var i in this.supplierList) {
					if (this.supplierList[i].id == data[0].value) {
						this.supplierDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			confirmStore(data) {
				this.$set(this.info, 'storeName', data[0].label);
				this.$set(this.info, 'storeId', data[0].value);
				for (var i in this.storeList) {
					if (this.storeList[i].id == data[0].value) {
						this.storeDefault.splice(0, 1, Number(i));
						break;
					}
				}
			},
			submit() {
				if (this.list.length == 0)
					return this.$refs.uToast.show({
						title: '请选择物资',
						type: 'warning'
					})
				this.$refs.uForm.validate(valid => {
					if (valid) {
						if (this.id) {
							this.editSubmit();
						} else {
							this.addSubmit();
						}
					} else {

					}
				})
			},
			addSubmit() {
				this.approveStatus = "0";
				var info = this.info;
				var checkDetail = [];
				for (var i in this.list) {
					if (!this.list[i].num) {
						return this.$message.warning('数量不能为空')
					}
					checkDetail.push({
						"goodId": this.list[i].goodId,
						"gridId": this.list[i].gridId,
						"orderDetailId": this.list[i].orderDetailId,
						"num": this.list[i].num
					})
					if (!this.list[i].gridId) {
						return this.$refs.uToast.show({
							title: '库位不能为空',
							type: 'warning'
						})
					}
				}
				this.$axios.post('/checkInput/save', {
					"projectId": app.globalData.projectId,
					"storeId": info.storeId,
					"checkDetail": checkDetail,
					"supplierName": info.supplierName,
					"majorId": info.majorId,
					"handNo": info.handNo,
					"orderGoodId": info.orderGoodId,
					"approveStatus": this.approveStatus,
				}).then(res => {
					this.$refs.uToast.show({
						title: '新增点验入库单成功',
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
				var checkDetail = [];
				for (var i in this.list) {
					if (!this.list[i].num) {
						return this.$message.warning('数量不能为空')
					}
					checkDetail.push({
						"goodId": this.list[i].goodId,
						"gridId": this.list[i].gridId,
						"orderDetailId": this.list[i].orderDetailId,
						"num": this.list[i].num
					})
					if (!this.list[i].gridId) {
						return this.$refs.uToast.show({
							title: '库位不能为空',
							type: 'warning'
						})
					}
				}
				for (var i in this.hisList) {
					for (var j in checkDetail) {
						if (this.hisList[i].goodId == checkDetail[j].goodId) {
							checkDetail[j].id = this.hisList[i].id;
						}
					}
				}
				this.$axios.post('/checkInput/update', {
					"id": this.id,
					"storeId": info.storeId,
					"supplierId": info.supplierId,
					"checkDetail": checkDetail,
					"supplierName": info.supplierName,
					"majorId": info.majorId,
					"handNo": info.handNo,
					"orderGoodId": info.orderGoodId,
					"approveStatus": this.approveStatus,
				}).then(res => {
					this.$refs.uToast.show({
						title: '编辑点验入库单成功',
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