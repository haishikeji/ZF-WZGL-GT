<template>
	<view>
		<search-com-page subtitle="请选择订货单" placeholder="编号" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<u-form-item label="专业" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择专业" v-model="majorName"
							@click="chooseMajor">
						</z-select>
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefult"
			value-name="id" :list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
<z-card :class="[chooseList.indexOf(item.id)>-1?'material_check':'']" class="mt-20" v-for="(item,index) in list"
			:key="index" @click.native="selectMaterial(item.id)">
			<u-row gutter="16">
				
				<u-col span="3" class="mt-20">
					<view>编号:</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.handNo}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>专业:</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.majorName}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>供应商名称:</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.supplierName}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>创建人:</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.createUserName}}</view>
				</u-col>
				<!-- <u-col span="3" class="mt-20">
					<view>合计:</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.count}}</view>
				</u-col>
				<u-col span="3" class="mt-20">
					<view>剩余:</view>
				</u-col>
				<u-col span="9" text-align="right">
					<view>{{item.balanceCount}}</view>
				</u-col> -->

			</u-row>
		</z-card>
		
		<z-bottom-fixed v-if="chooseList.length>0">
			<z-button type="primary" @click="submit">确定</z-button>
		</z-bottom-fixed>
		
		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
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
				chooseList: [],
				 planNo:'',
				 handNo:'',
				orderNo: '',
				approveStatus: '',
				approveStatusName: '',
				approveStatusList: [],
				showApproveStatus: false,
				approveStatusDefault: [0],
				showMajor: false,
				majorId:'',
				majorName:'',
				majorList: [],
				majorDefult: [0],

				list: [],
				form: {},
				rules: {},
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				id: '',

				delVisible: false,
				pageSize: 20,
				currentPage: 1,
				total: 0,

			}
		},
		onShow() {
			this.$nextTick(() => {
				this.getApproveStatusList();
			})
			this.getMajorList()
			this.searchList()
		},
		onReady() {
			this.searchList();
		},
		onReachBottom() {
			if (this.list.length == this.total) {
				this.status = 'nomore';
			} else {
				this.status = 'loading';
				this.currentPage++;
				this.getDataList();
			}
		},
		methods: {
		 getMajorList() {
					this.$axios.post('/major/findList', {
					"projectId": app.globalData.projectId,
					}).then(res => {
						this.majorList = res.data.list;
					}).catch(err => {
						this.$message.error(err.msg)
					})
				},
			selectMaterial(id) {
				console.log(id)
				var num = this.chooseList.indexOf(id);
				console.log(num)
				if (num > -1) {
					this.chooseList.splice(num, 1);
				} else {
					if(this.chooseList.length>0){
						this.$refs.uToast.show({
							title: "只能选择一条申请计划",
							type: 'error'
						})
					}else{
						this.chooseList.push(id);
					}
					
				}
			},
			submit() {
				var list = [];
				for (var i in this.chooseList) {
					for (var j in this.list) {
						if (this.list[j].id == this.chooseList[i]) {
							list.push(this.list[j]);
							break;
						}
					}
				}
				let pages = getCurrentPages();
				let prevPage = pages[pages.length - 2]
				// 触发上一页 upData 函数(并携带参数)
				prevPage.$vm.upOrderData(list)
				uni.navigateBack({
					delta: 1
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
				this.searchList();
			},
			getApproveStatusList() {
				this.approveStatusList = uni.getStorageSync('dic').list.filter((item) => item.codeKind == 'approveStatus');
			},
			searchChange(data) {
				this.handNo = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			chooseMajor() {
				this.showMajor = true;
			},
			getDataList() {
				this.$axios.post('/orderGood/orderGoodList', {
					"projectId": app.globalData.projectId,
					 "planNo": this.planNo,
					 "handNo": this.handNo,
					 "majorId": this.majorId,
                "operateType": '1',
					"approveStatus": '3',
					pageNum: this.currentPage,
					pageSize: this.pageSize,
				}).then(res => {
					var list = res.data.list;
					this.list = this.list.concat(list);
					this.total = res.data.total;
					for (var i in this.list) {
						this.$set(this.list[i], 'approveStatus', this.list[i].approveInfo[0].approveStatus)
					}
					if (this.list.length == this.total) {
						this.status = 'nomore';
					} else {
						this.status = 'loadmore';
					}
				}).catch(err => {
					this.status = 'loadmore';
				})
			},
			lookData(id) {
				uni.navigateTo({
					url: '../detail/detail?id=' + id,
					success: res => {},
					fail: () => {},
					complete: () => {}
				});
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
				this.$axios.post('/planGood/logicDeleteByIds', this.id).then(res => {
					this.$refs.uToast.show({
						title: '删除成功',
						type: 'success'
					})
					this.searchList();
				}).catch(err => {
					this.$refs.uToast.show({
						title: err.msg,
						type: 'error'
					})
				})
			},

			confirmApprove(data) {
				console.log(data)
				this.approveStatusName = data[0].label;
				this.approveStatus = data[0].value;
				for (var i in this.approveStatusList) {
					if (this.approveStatusList[i].codeValue == this.approveStatus) {
						this.approveStatusDefault.splice(0, 1, Number(i));
						break;
					}
				}
				this.searchList();
			},
			chooseApprove() {
				this.showApproveStatus = true;
			},

			resetForm() {
				this.handNo = '';
				this.approveStatusName = '';
				this.approveStatus = '';
				this.majorId= '',
				this.majorName= '',
				this.approveStatusDefault.splice(0, 1, 0);
				this.majorDefult.splice(0, 1, 0);
				
			},

		}
	}
</script>

<style scoped lang="scss">
	.right-btn {
		width: 38rpx;
		height: 38rpx;
	}

	.list {
		position: relative;

		.btn-area {
			position: absolute;
			bottom: 24rpx;
			left: 24rpx;
		}
	}
	
	.material_check {
		border: 1px solid #1677FF;
	}
	
	.material {
		.item {
			color: #ccc;
		}
	}
	
	.detail {
		color: #1677FF;
	}
	
	.detail-info {
		padding: 20rpx;
		border: 1px solid #eee;
		margin-top: 24rpx;
		border-radius: 6rpx;
	}
</style>
