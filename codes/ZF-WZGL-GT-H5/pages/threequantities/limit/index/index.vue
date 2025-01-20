<template>
	<view>
		<search-com-page title="限额管理" subtitle="限额查询" placeholder="区间/专业名称" @searchChange="searchChange"
			@submit="searchList" @resetForm="resetForm">
<!-- 			<image slot="topbtn" class="right-btn" src="../../../../common/img/add.png" @click="manageData('')"></image> -->
			<view slot="search">
				<u-form :model="form" ref="uForm" :border-bottom="false" label-position="top" :error-type="['border']">
					<u-form-item label="专业" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择专业" v-model="majorName"
							@click="chooseMajor">
						</z-select>
					</u-form-item>
					<u-form-item label="区间" :border-bottom="false">
						<z-select style="width: 100%;" placeholder="请选择区间" v-model="stationIntervalName"
							@click="chooseStationInterval">
						</z-select>
					</u-form-item>
				</u-form>
			</view>
		</search-com-page>
		<u-select v-model="showStationInterval" label-name='stationIntervalName' :default-value="stationIntervalDefult"
			value-name="id" :list="stationIntervalList" @confirm="confirmStationInterval" :mask-close-able="false">
		</u-select>
		
		<u-select v-model="showMajor" label-name='majorName' :default-value="majorDefult"
			value-name="id" :list="majorList" @confirm="confirmMajor" :mask-close-able="false">
		</u-select>
		
		<z-card class="mt-24 list" v-for="(item, index) in list" :key="index">

			<z-more>
				<view slot="top" class="comm-detail-text">
					<view>专业：</view>
					<view class="right">{{item.majorName}}</view>
					<view>区间：</view>
					<view class="right">{{item.stationIntervalName}}</view>
				</view>
				<view slot="bottom" class="comm-detail-text">
					<view>创建时间：</view>
					<view class="right">{{item.createTime}}</view>
				</view>
			</z-more>
			<view class="btn-area">
				<u-button size="mini" type="warning" plain shape="circle" @click="lookData(item.id)">查看详情</u-button>
			
				<u-button v-if="isManage" class="ml-20" size="mini" type="primary" plain shape="circle" @click="manageData(item.id)">编辑
				</u-button>
			</view>
		</z-card>

		<u-loadmore v-if="list.length>0" class="mt-40" :status="status" />
		<view style="height: 60rpx;" v-if="list.length>0"></view>
		<nodata v-if="list.length==0"></nodata>
		<u-toast ref="uToast"></u-toast>
		<!-- 删除 -->
		<u-modal v-model="delVisible" show-cancel-button content="该操作将删除该订单所有数据,
	确定要删除吗？" @confirm="deleteData"></u-modal>
	</view>
</template>

<script>
	const app = getApp({
		allowDefault: true
	});
	export default {
		data() {
			return {
				isManage:false,
				orderNo: '',
				approveStatus: '',
				approveStatusName: '',
				approveStatusList: [],
				ze: false,
				approveStatusDefault: [0],
				list: [],
				form: {},
				rules: {},
				status: 'loadmore', //加载前值为loadmore，加载中为loading，没有数据为nomore
				id: '',
				showApproveStatus: false,
				showMajor: false,
				majorId:'',
				majorName:'',
				majorList: [],
				searchKey:'',
				majorDefult: [0],
				stationIntervalId:'',
				stationIntervalName:'',
				stationIntervalList:[],
				stationIntervalDefult: [0],
				showStationInterval: false,
				
				delVisible: false,
				pageSize: 20,
				currentPage: 1,
				total: 0,

			}
		},
		onShow() {
			
			this.isManage = uni.getStorageSync('isManage');
			this.$nextTick(() => {
				this.getMajorList();
				this.getStationIntervalList();
			})
		},
		onReady() {
			this.getDataList();
			this.getMajorList();
			this.getStationIntervalList();
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
			 getStationIntervalList(){
			            this.$axios.post('/stationInterval/findList',{
			                "belongProjectId": app.globalData.projectId,
			            }).then(res=>{
			                this.stationIntervalList=res.data.list;
			            }).catch(err=>{
			                this.$message.error(err.msg)
			            })
			        },		
			searchChange(data) {
				this.searchKey = data;
				this.searchList();
			},
			searchList() {
				this.list = [];
				this.currentPage = 1;
				this.total = 0;
				this.getDataList();
			},
			getDataList() {
				this.$axios.post('/limit/findAll', {
					"projectId": app.globalData.projectId,
					"stationIntervalId": this.stationIntervalId,
					"majorId": this.majorId,
					"searchKey": this.searchKey,
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
				this.$axios.post('/orderGoodDetail/logicDeleteByIds', this.id).then(res => {
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
			confirmStationInterval(data) {
				console.log(data)
				this.stationIntervalName = data[0].label;
				this.stationIntervalId = data[0].value;
				for (var i in this.stationIntervalList) {
					if (this.stationIntervalList[i].id == this.stationIntervalId) {
						this.approveStatusDefault.splice(0, 1, Number(i));
						break;
					}
				}
				this.searchList();
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
			chooseApprove() {
				this.showApproveStatus = true;
			},
			chooseMajor() {
				this.showMajor = true;
			},
			chooseStationInterval() {
				this.showStationInterval = true;
			},

			resetForm() {
				// this.approveStatusName = '';
				// this.approveStatus = '';
				// this.approveStatusDefault.splice(0, 1, 0);
				
				this.stationIntervalId= '';
				this.stationIntervalName= '';
				this.majorId= '';
				this.majorName= '';
				this.majorDefult.splice(0, 1, 0);
				this.stationIntervalDefult.splice(0, 1, 0);
				
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
</style>
